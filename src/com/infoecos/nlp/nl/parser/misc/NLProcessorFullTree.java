package com.infoecos.nlp.nl.parser.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.infoecos.nlp.nl.NLManager;
import com.infoecos.nlp.nl.languages.Grammar;
import com.infoecos.nlp.nl.parser.NLElement;
import com.infoecos.nlp.nl.parser.NLProcessor;
import com.infoecos.nlp.nl.parser.SemanticProcessor;
import com.infoecos.nlp.nl.parser.WordInfoObject;
import com.infoecos.nlp.nl.parser.WordProcessor;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.util.graph.Direction;
import com.infoecos.util.graph.Link;
import com.infoecos.util.graph.TreeNode;

public class NLProcessorFullTree extends NLProcessor {

	private class NLRuntime {
		String text;
		List<TreeNode> rootNodeList = new ArrayList<TreeNode>();
		Stack<TreeNode> nodeStack = new Stack<TreeNode>();
		Stack<Link> actionStack = new Stack<Link>();
		Map<String, NLElement> elements = new HashMap<String, NLElement>();
		SemanticProcessor runtime;
		PartOfSpeech[] poses;

		public NLRuntime(String text, SemanticProcessor runtime,
				PartOfSpeech[] poses) {
			this.text = text;
			this.runtime = runtime;
			this.poses = poses;
		}

		public NLElement[] getElements() {
			return (NLElement[]) elements.values().toArray(
					new NLElement[elements.values().size()]);
		}
	}

	@Override
	public NLElement[] parseNextElement(String text, int offset,
			SemanticProcessor runtime, PartOfSpeech[] poses) {
		NLRuntime nlRuntime = new NLRuntime(text, runtime, poses);
		elementParse(nlRuntime, offset);

		return nlRuntime.getElements();
	}

	private boolean isElementEnd(NLRuntime nlRuntime, TreeNode nextNode) {
		int size = nlRuntime.rootNodeList.size();
		if (size == 0)
			return false;

		if (nextNode != null) {
			WordInfoObject wobj = (WordInfoObject) nextNode.getObject();
			// for (PartOfSpeech pos : wobj.getChangedPartOfSpeech())
			// if (PartOfSpeech.Stop.equals(pos))
			// return true;

			if (PartOfSpeech.Stop
					.equals(wobj.getFormOfWord().getPartOfSpeech()))
				return true;
		}

		// TreeNode node = null;
		// for (int i = size - 1; i >= 0; --i) {
		// node = rootNodeList.get(i);
		// pos = ((WordInfoObject) node.getObject()).getChangedPartOfSpeech();
		// if (matchPartOfSpeech(pos, poses))
		// return true;
		// }

		return false;
	}

	private void elementParse(NLRuntime nlRuntime, int offset) {

		TreeNode[] nodes = WordProcessor.parseNextToken(nlRuntime.text, offset,
				nlRuntime.runtime);

		if (nodes.length == 0) {
			// buildInverseClause(text, offset, poses, elements, nodeStack,
			// rootNodeList, actionStack);
			saveElement(nlRuntime);
			return;
		}

		for (TreeNode nextNode : nodes) {
			if (isElementEnd(nlRuntime, nextNode)) {
				// buildInverseClause(text, offset, poses, elements, nodeStack,
				// rootNodeList, actionStack);
				saveElement(nlRuntime, nextNode);
				continue;
			}
			// System.out.println(nextNode);

			FormOfWord fow = ((WordInfoObject) nextNode.getObject())
					.getFormOfWord();
			int tokenLen = fow.getWordForm().length();
			nlRuntime.nodeStack.push(nextNode);
			offset += tokenLen;

			boolean skipped = false;
			try {
				Language lang = fow.getLanguage();
				Grammar grammar = NLManager.getLanguageInstance(lang)
						.getGrammar();
				skipped = grammar.isEscapeForm(fow);
			} catch (Exception e) {
			}

			if (skipped) {
				elementParse(nlRuntime, offset);
			} else {
				nlRuntime.rootNodeList.add(nextNode);
				buildElementModel(nlRuntime, offset);
				nlRuntime.rootNodeList.remove(nextNode);
			}

			offset -= tokenLen;
			nlRuntime.nodeStack.pop();
		}
	}

	private void saveElement(NLRuntime nlRuntime) {
		saveElement(nlRuntime, null);
	}

	private void saveElement(NLRuntime nlRuntime, TreeNode stopNode) {

		if (nlRuntime.rootNodeList.size() != 1)
			return;

		TreeNode root = nlRuntime.rootNodeList.get(0);
		if (((WordInfoObject) root.getObject())
				.matchPartOfSpeech(nlRuntime.poses) == null)
			return;

		String strVal = root.toString();
		if (nlRuntime.elements.containsKey(strVal))
			return;

		try {
			StringBuilder token = new StringBuilder();
			for (TreeNode node : nlRuntime.nodeStack)
				token.append(((WordInfoObject) node.getObject())
						.getFormOfWord().getWordForm());

			if (stopNode != null)
				token.append(((WordInfoObject) stopNode.getObject())
						.getFormOfWord().getWordForm());
			NLElement ele = new NLElement(token.toString(),
					(TreeNode) root.clone());

			// System.out.println(ele);
			nlRuntime.elements.put(strVal, ele);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	private void buildInverseClause(NLRuntime nlRuntime, int offset) {

		if (nlRuntime.rootNodeList.size() <= 1)
			return;

		TreeNode lastNode = nlRuntime.rootNodeList.get(0), parentNode;
		Link link = null;

		// if (!(!
		// PartOfSpeech.Sentence.equals(((WordInfoObject)
		// currentNode.getObject()).getChangedPartOfSpeech()) &&
		// PartOfSpeech.Sentence.equals(((WordInfoObject)
		// currentNode.getObject()).getChangedPartOfSpeech())))
		// return;

		parentNode = nlRuntime.nodeStack.peek();
		if (parentNode != null) {
			try {
				link = new Link(parentNode, lastNode, Direction.Inverse);
				parentNode.pushSubLink(link);
				nlRuntime.actionStack.push(link);
				// System.out.println(link);
				nlRuntime.rootNodeList.remove(0);
			} catch (Exception e) {
			}
		}
	}

	private void popAction(NLRuntime nlRuntime) {
		Link link = nlRuntime.actionStack.pop();
		// System.out.println("pop: " + link);
		TreeNode node = (TreeNode) link.getStartNode();
		while (node.getParentLink() != null)
			node = (TreeNode) node.getParentLink().getStartNode();
		int index = nlRuntime.rootNodeList.indexOf(node);

		if (Direction.Inverse.equals(link.getDirection())) {
			nlRuntime.rootNodeList.add(0, (TreeNode) link.getEndNode());
		} else if (Direction.RightSub.equals(link.getDirection())) {
			nlRuntime.rootNodeList.add(index + 1, (TreeNode) link.getEndNode());
		} else {
			nlRuntime.rootNodeList.add(index, (TreeNode) link.getEndNode());
		}
		link.getStartNode().popSubLink();
	}

	private void buildElementModel(NLRuntime nlRuntime, int offset) {

		// if (rootNodeList.isEmpty()) {
		// elementParse(text, offset, poses, elements, nodeStack,
		// rootNodeList, actionStack);
		// return;
		// }

		int actionSize = nlRuntime.actionStack.size();
		buildElementPrefixModel(nlRuntime, offset);

		// try on all suffix
		while (nlRuntime.actionStack.size() > actionSize) {
			buildElementSuffixModel(nlRuntime, offset);
			popAction(nlRuntime);
		}

		buildElementSuffixModel(nlRuntime, offset);
	}

	private void buildElementPrefixModel(NLRuntime nlRuntime, int offset) {
		int lastIndex = nlRuntime.rootNodeList.size() - 1;
		TreeNode currentNode = nlRuntime.rootNodeList.get(lastIndex);
		try {
			while (lastIndex > 0) {
				--lastIndex;
				Link link = new Link(currentNode,
						nlRuntime.rootNodeList.get(lastIndex),
						Direction.LeftSub);
				currentNode.pushSubLink(link);
				nlRuntime.actionStack.push(link);
				// System.out.println(link);
				nlRuntime.rootNodeList.remove(lastIndex);
			}
		} catch (Exception e) {
			// break on link exceptions
		}
	}

	private void buildElementSuffixModel(NLRuntime nlRuntime, int offset) {

		// if (rootNodeList.isEmpty()) {
		// elementParse(text, offset, poses, elements, nodeStack,
		// rootNodeList, actionStack);
		// return;
		// }

		int lastIndex = nlRuntime.rootNodeList.size() - 1;
		TreeNode currentNode = nlRuntime.rootNodeList.get(lastIndex);

		TreeNode node = null;
		Link l = null;
		for (lastIndex = nlRuntime.nodeStack.indexOf(currentNode) - 1; lastIndex >= 0; --lastIndex) {
			node = nlRuntime.nodeStack.get(lastIndex);
			boolean subElement = false;
			while ((l = node.getParentLink()) != null) {
				node = (TreeNode) l.getStartNode();
				if (node.equals(currentNode)) {
					subElement = true;
					break;
				}
			}
			if (!subElement) {
				break;
			}
		}

		try {
			node = nlRuntime.nodeStack.get(lastIndex);
			do {
				try {
					Link link = new Link(node, currentNode, Direction.RightSub);
					node.pushSubLink(link);
					nlRuntime.actionStack.push(link);
					// System.out.println(link);
					nlRuntime.rootNodeList.remove(currentNode);

					WordInfoObject wobj = (WordInfoObject) node.getObject();
					if (wobj.getChangedPartOfSpeech().length > 0)
						buildElementSuffixModel(nlRuntime, offset);
					else
						elementParse(nlRuntime, offset);

					popAction(nlRuntime);

					// FIXME: remove the following code to do full parse on
					// suffix modeling
					break;
				} catch (Exception e) {
				}
				if ((l = node.getParentLink()) == null)
					break;
				node = (TreeNode) l.getStartNode();
			} while (true);
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		elementParse(nlRuntime, offset);
	}
}
