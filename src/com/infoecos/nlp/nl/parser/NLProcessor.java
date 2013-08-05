package com.infoecos.nlp.nl.parser;

import java.util.List;

import com.infoecos.nlp.nl.languages.Grammar;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.util.graph.Link;
import com.infoecos.util.graph.TreeNode;

public abstract class NLProcessor {
	public static final PartOfSpeech[] POS_SENTENCE = new PartOfSpeech[] {
			PartOfSpeech.Sentence, PartOfSpeech.Verb };

	public NLElement[] parseNextElement(String text, int offset,
			SemanticProcessor runtime) {
		return parseNextElement(text, offset, runtime, POS_SENTENCE);
	}

	public abstract NLElement[] parseNextElement(String text, int offset,
			SemanticProcessor runtime, PartOfSpeech[] poses);

	public static FormOfWord getNodeFormOfWord(TreeNode node) {
		return ((WordInfoObject) node.getObject()).getFormOfWord();
	}

	protected Grammar getGrammar(TreeNode node) {
		return Grammar.getGrammar(getNodeFormOfWord(node));
	}

	protected boolean isStopWord(List<TreeNode> rootNodeList, TreeNode nextNode) {
		// stopword will always be the end of left sub
		if (rootNodeList.isEmpty())
			return false;

		TreeNode lastNode = rootNodeList.get(rootNodeList.size() - 1);
		FormOfWord lastFow = getNodeFormOfWord(lastNode), nextFow = getNodeFormOfWord(nextNode);
		try {
			if (Grammar.getGrammar(lastFow).getPartOfSpeechPriority(lastFow) > Grammar
					.getGrammar(nextFow).getPartOfSpeechPriority(nextFow))
				return true;
		} catch (Exception e) {
		}

		if (lastFow.getPartOfSpeech().equals(nextFow.getPartOfSpeech())) {
			// handle same/different entity (-ies), e.g., school student
			return false;
		}
		return false;
	}

	protected boolean isSuperNode(TreeNode supNode, TreeNode subNode) {
		for (Link l = null; (l = subNode.getParentLink()) != null;) {
			subNode = (TreeNode) l.getStartNode();
			if (subNode.equals(supNode))
				return true;
		}
		return false;
	}
}
