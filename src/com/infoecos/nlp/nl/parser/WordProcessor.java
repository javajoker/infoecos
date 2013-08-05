package com.infoecos.nlp.nl.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infoecos.nlp.nl.NLAdapter;
import com.infoecos.nlp.nl.NLManager;
import com.infoecos.nlp.nl.parser.misc.NLProcessorModelingOnStop;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.util.graph.Link;
import com.infoecos.util.graph.TreeNode;

public class WordProcessor {
	// private static PartOfSpeech[] priority = { PartOfSpeech.Abbreviation, };
	private static Map<String, TreeNode[]> tokensCache = new HashMap<String, TreeNode[]>();

	public static TreeNode[] parseNextToken(String text, int offset,
			SemanticProcessor runtime) {
		String key = String.format("%d|%s", offset, text);
		if (tokensCache.containsKey(key))
			return tokensCache.get(key);

		List<TreeNode> tokenNodes = new ArrayList<TreeNode>();
		TreeNode currentNode = null;

		for (NLAdapter nl : NLManager.getLanguages()) {
			for (FormOfWord fow : nl.getTokenizerHelper().getNextFormOfWords(text,
					offset)) {
				// // for dictionary learning
				// if (PartOfSpeech.Preposition.equals(fow.getPartOfSpeech())) {
				// currentNode = new TreeNode();
				// currentNode.setObject(new WordInfoObject(fow));
				// return new TreeNode[] { currentNode };
				// }
				// if (PartOfSpeech.Conjunction.equals(fow.getPartOfSpeech())) {
				// currentNode = new TreeNode();
				// currentNode.setObject(new WordInfoObject(fow));
				// return new TreeNode[] { currentNode };
				// }

				if (PartOfSpeech.Numberal.equals(fow.getPartOfSpeech())) {
					try {
						if (!PartOfSpeech.Numberal.equals(fow.getPartOfSpeech())) {
							currentNode = new TreeNode();
							currentNode.setObject(new WordInfoObject(
									(FormOfWord) fow.clone(), offset));
							tokenNodes.add(currentNode);
						}

						fow.setPartOfSpeech2(PartOfSpeech.Adjective);
						currentNode = new TreeNode();
						currentNode.setObject(new WordInfoObject(
								(FormOfWord) fow.clone(), offset));
						tokenNodes.add(currentNode);

						fow.setPartOfSpeech2(PartOfSpeech.Noun);
						currentNode = new TreeNode();
						currentNode.setObject(new WordInfoObject(
								(FormOfWord) fow.clone(), offset));
						tokenNodes.add(currentNode);
					} catch (CloneNotSupportedException e) {
					}
				} else if (PartOfSpeech.Abbreviation.equals(fow
						.getPartOfSpeech())) {
					currentNode = getAbbreviation(fow, runtime);
					if (currentNode == null) {
						fow.setPartOfSpeech2(PartOfSpeech.NA);
						currentNode = new TreeNode();
						currentNode.setObject(new WordInfoObject(fow, offset));
					}
					tokenNodes.add(currentNode);
				} else {
					currentNode = new TreeNode();
					currentNode.setObject(new WordInfoObject(fow, offset));
					tokenNodes.add(currentNode);
				}
			}
		}

		TreeNode[] ret = (TreeNode[]) tokenNodes
				.toArray(new TreeNode[tokenNodes.size()]);
		if (tokensCache.size() > 1000)
			tokenNodes.clear();
		tokensCache.put(key, ret);
		return ret;
	}

	private static NLProcessor sp = new NLProcessorModelingOnStop();

	private static TreeNode getAbbreviation(FormOfWord fow,
			SemanticProcessor runtime) {
		TreeNode node = null;
		NLElement[] eles = sp.parseNextElement(fow.getWord().getKey(), 0,
				runtime, new PartOfSpeech[] { PartOfSpeech.ANY });
		if (eles.length == 0)
			return null;

		// use first result only
		node = eles[0].getModel();

		emptyWordForm(node);
		((WordInfoObject) node.getObject()).getFormOfWord().setWordForm(
				fow.getWordForm());
		return node;
	}

	private static void emptyWordForm(TreeNode node) {
		((WordInfoObject) node.getObject()).getFormOfWord().setWordForm("");
		for (Link link : node.getLinks())
			emptyWordForm((TreeNode) link.getEndNode());
	}
}
