package com.infoecos.nlp.nl.languages;

import java.util.HashMap;
import java.util.Map;

import com.infoecos.nlp.nl.NLManager;
import com.infoecos.nlp.nl.parser.WordInfoObject;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.util.graph.Direction;
import com.infoecos.util.graph.Link;
import com.infoecos.util.graph.LinkType;
import com.infoecos.util.graph.NotLinkableException;
import com.infoecos.util.graph.TreeNode;

public abstract class Grammar {
	public static Grammar getGrammar(FormOfWord fow) {
		try {
			return NLManager.getLanguageInstance(fow.getLanguage())
					.getGrammar();
		} catch (Exception e) {
			return null;
		}
	}

	protected PartOfSpeech escapePartOfSpeech[] = new PartOfSpeech[0];
	protected PartOfSpeech partOfSpeechPriority[][] = new PartOfSpeech[0][];
	protected LinkType[] allLinkTypes = new LinkType[0];

	private Map<PartOfSpeech, Integer> posPriorityMap = new HashMap<PartOfSpeech, Integer>();

	protected void initialize() {
		for (int priority = 0; priority < partOfSpeechPriority.length; ++priority) {
			for (PartOfSpeech pos : partOfSpeechPriority[priority])
				posPriorityMap.put(pos, priority);
		}
	}

	public int getPartOfSpeechPriority(FormOfWord fow) {
		return getPartOfSpeechPriority(fow.getPartOfSpeech());
	}

	public int getPartOfSpeechPriority(PartOfSpeech pos) {
		if (posPriorityMap.containsKey(pos))
			return posPriorityMap.get(pos);

		return -1;
	}

	public abstract Language getLanguage();

	protected abstract LinkType getSpecialLinkType(FormOfWord fow,
			TreeNode node, Direction direction) throws NotLinkableException;

	public LinkType getType(FormOfWord fow, TreeNode node, Direction direction)
			throws NotLinkableException {

		WordInfoObject subObj = ((WordInfoObject) node.getObject());

		PartOfSpeech pos = null;
		if ((pos = subObj.matchPartOfSpeech(escapePartOfSpeech)) != null) {
			throw new NotLinkableException();
			// return new LinkType(fow.getPartOfSpeech(), pos);
		}

		for (LinkType type : allLinkTypes) {
			if (!Direction.NA.equals(type.getDirection())
					&& !direction.equals(type.getDirection()))
				continue;
			if (!fow.getPartOfSpeech().equals(type.getStartPartOfSpeech()))
				continue;

			if (type.isForceNewPart()
					&& subObj.getChangedPartOfSpeech().length == 0)
				continue;

			if (type.isForbidRightSub()) {
				boolean hasRightSub = false;
				for (Link link : node.getLinks()) {
					if (Direction.RightSub
							.equals(link.getType().getDirection())) {
						hasRightSub = true;
						break;
					}
				}
				if (hasRightSub)
					continue;
			}
			if (subObj.matchPartOfSpeech(new PartOfSpeech[] { type
					.getEndPartOfSpeech() }) != null)
				return type;
		}

		return getSpecialLinkType(fow, node, direction);
	}

	public PartOfSpeech[] getEscapePartOfSpeech() {
		return escapePartOfSpeech;
	}

	public boolean isEscapeForm(FormOfWord fow) {
		for (PartOfSpeech pos : getEscapePartOfSpeech()) {
			if (pos.equals(fow.getPartOfSpeech())) {
				return true;
			}
		}
		return false;
	}
}