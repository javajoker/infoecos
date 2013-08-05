package com.infoecos.nlp.nl.parser;

import java.util.Arrays;

import com.infoecos.nlp.nl.languages.Grammar;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.util.graph.Direction;
import com.infoecos.util.graph.Link;
import com.infoecos.util.graph.LinkType;
import com.infoecos.util.graph.NodeObject;
import com.infoecos.util.graph.NotLinkableException;
import com.infoecos.util.graph.TreeNode;

public class WordInfoObject implements NodeObject {
	private int offset = 0;
	private FormOfWord fow = null;
	private PartOfSpeech[] changedPartOfSpeech = {};

	public WordInfoObject(FormOfWord fow, int offset) {
		this(fow, offset, new PartOfSpeech[0]);
	}

	public WordInfoObject(FormOfWord fow, int offset,
			PartOfSpeech[] changedPartOfSpeech) {
		this.fow = fow;
		this.offset = offset;
		this.changedPartOfSpeech = changedPartOfSpeech;
	}

	public int getOffset() {
		return offset;
	}

	public FormOfWord getFormOfWord() {
		return fow;
	}

	public PartOfSpeech matchPartOfSpeech(PartOfSpeech[] targetPoses) {
		for (PartOfSpeech tp : targetPoses) {
			if (PartOfSpeech.ANY.equals(tp))
				return PartOfSpeech.ANY;

			if (changedPartOfSpeech.length == 0) {
				if (tp.equals(fow.getPartOfSpeech()))
					return tp;
			} else {
				for (PartOfSpeech p : changedPartOfSpeech)
					if (tp.equals(p))
						return tp;
			}
		}

		return null;
	}

	public PartOfSpeech[] getChangedPartOfSpeech() {
		return changedPartOfSpeech;
	}

	public void setChangedPartOfSpeech(PartOfSpeech[] changedPartOfSpeech) {
		this.changedPartOfSpeech = changedPartOfSpeech;
	}

	public LinkType getLinkType(TreeNode node, Direction direction)
			throws Exception {

		Grammar grammar = Grammar.getGrammar(fow);
		if (grammar == null)
			throw new NotLinkableException();

		for (PartOfSpeech pos : grammar.getEscapePartOfSpeech()) {
			if (pos.equals(fow.getPartOfSpeech()))
				throw new NotLinkableException();
		}

		// // same language only
		// FormOfWord fow2 = ((WordInfoObject)
		// node.getObject()).getFormOfWord();
		// if (!(Language.NA.equals(fow2.getLanguage()) || fow.getLanguage()
		// .equals(fow2.getLanguage())))
		// throw new NotLinkableException();

		return grammar.getType(fow, node, direction);
	}

	@Override
	public void applyLink(Link link) throws Exception {
		// if (!PartOfSpeech.NA.equals(changedPartOfSpeech))
		// throw new NotLinkableException();

		LinkType type = getLinkType((TreeNode) link.getEndNode(),
				link.getDirection());
		int cardinal = 0;
		for (Link l : link.getStartNode().getLinks()) {
			if (type.equals(l.getType()))
				++cardinal;
		}
		if (cardinal >= type.getMaxCardinal())
			throw new NotLinkableException();

		if (type.getNewPartOfSpeech().length > 0
				&& changedPartOfSpeech.length > 0)
			throw new NotLinkableException();

		link.setType(type);
		// System.out.println(link);
		// System.out.println(type.getNewPartOfSpeech());
		changedPartOfSpeech = type.getNewPartOfSpeech();
	}

	@Override
	public void removeLink(Link link) {
		LinkType type = link.getType();
		if (type != null && type.getNewPartOfSpeech().length > 0) {
			changedPartOfSpeech = new PartOfSpeech[0];
		}
	}

	@Override
	public NodeObject clone() throws CloneNotSupportedException {
		return new WordInfoObject(fow, offset, changedPartOfSpeech);
	}

	@Override
	public String toString() {
		return String.format("%s%s", fow.toString(),
				Arrays.toString(changedPartOfSpeech));
	}
}
