package com.infoecos.nlp.nl.parser.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.infoecos.nlp.nl.languages.Grammar;
import com.infoecos.nlp.nl.parser.NLElement;
import com.infoecos.nlp.nl.parser.NLProcessor;
import com.infoecos.nlp.nl.parser.SemanticProcessor;
import com.infoecos.nlp.nl.parser.WordInfoObject;
import com.infoecos.nlp.nl.parser.WordProcessor;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.util.graph.Direction;
import com.infoecos.util.graph.Link;
import com.infoecos.util.graph.Node;
import com.infoecos.util.graph.TreeNode;

public class NLProcessorModelingOnStop extends NLProcessor {

	private class NLRuntime {
		String text;
		List<TreeNode> rootNodeList = new ArrayList<TreeNode>();
		Map<Integer, Integer> possibleTokens = new HashMap<Integer, Integer>();
		Map<Integer, Integer> tokenActions = new HashMap<Integer, Integer>();
		Stack<Link> actionStack = new Stack<Link>();
		Map<String, NLElement> elements = new HashMap<String, NLElement>();
		Map<Integer, TreeNode> phrases = new HashMap<Integer, TreeNode>();
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

	private int getOffset(TreeNode node) {
		TreeNode n = null;
		for (Link l : node.getLinks()) {
			if (Direction.LeftSub.equals(l.getDirection()))
				n = (TreeNode) l.getEndNode();
		}
		if (null != n)
			return getOffset(n);
		else
			return ((WordInfoObject) node.getObject()).getOffset();
	}

	private void archivePhrase(NLRuntime nlRuntime) {
		TreeNode phrase = nlRuntime.rootNodeList.get(nlRuntime.rootNodeList
				.size() - 1);
		if (oneWayPhrase(phrase, nlRuntime)) {
			int offset = getOffset(phrase);
			nlRuntime.phrases.put(offset, phrase);
			for (int i = nlRuntime.actionStack.size(); i > nlRuntime.tokenActions
					.get(offset); --i)
				nlRuntime.actionStack.pop();
		}
	}

	private boolean oneWayPhrase(TreeNode node, NLRuntime nlRuntime) {
		if (nlRuntime.possibleTokens.get(((WordInfoObject) node.getObject())
				.getOffset()) > 1)
			return false;

		for (Link link : node.getLinks()) {
			if (!oneWayPhrase((TreeNode) link.getEndNode(), nlRuntime))
				return false;
		}
		return true;
	}

	private void elementParse(NLRuntime nlRuntime, int offset) {
		if (offset > nlRuntime.text.length())
			return;

		if (nlRuntime.phrases.containsKey(offset)) {
			// previous phrase try
			buildElementSuffixModel(nlRuntime);
			archivePhrase(nlRuntime);

			TreeNode phraseNode = nlRuntime.phrases.get(offset);
			nlRuntime.rootNodeList.add(phraseNode);

			StringBuilder buf = new StringBuilder();
			getNodeText(phraseNode, buf);

			int actionSize = nlRuntime.actionStack.size();
			nlRuntime.tokenActions.put(offset, actionSize);

			elementParse(nlRuntime, offset + buf.length());

			for (int i = nlRuntime.actionStack.size(); i > actionSize; --i)
				popAction(nlRuntime);
			nlRuntime.rootNodeList.remove(phraseNode);

			return;
		}
		TreeNode[] nodes = WordProcessor.parseNextToken(nlRuntime.text, offset,
				nlRuntime.runtime);
		nlRuntime.possibleTokens.put(offset, nodes.length);

		if (nodes.length == 0) {
			// last sentence
			buildElementSuffixModel(nlRuntime);
			archivePhrase(nlRuntime);

			buildInverseClause(nlRuntime);
			return;
		}

		for (TreeNode nextNode : nodes) {
			FormOfWord fow = getNodeFormOfWord(nextNode);
			Grammar grammar = Grammar.getGrammar(fow);

			if (isStopWord(nlRuntime.rootNodeList, nextNode)) {
				buildElementSuffixModel(nlRuntime);
				archivePhrase(nlRuntime);
			}

			if (isElementEnd(nlRuntime, nextNode)) {
				buildInverseClause(nlRuntime, nextNode);
				continue;
			}
			// System.out.println(nextNode);

			int actionSize = nlRuntime.actionStack.size();
			nlRuntime.tokenActions.put(offset, actionSize);

			nlRuntime.rootNodeList.add(nextNode);
			int tokenLen = fow.getWordForm().length();
			offset += tokenLen;

			if (grammar != null && grammar.isEscapeForm(fow)) {
				// unknown token, skip token
			} else {
				// build up prefix model
				buildElementPrefixModel(nlRuntime);
			}
			elementParse(nlRuntime, offset);

			// rollback
			offset -= tokenLen;
//			if (offset == 49) {
//				System.out.println(nlRuntime.rootNodeList);
//				System.out.println(nlRuntime.actionStack.size());
//				System.out.println(actionSize);
//			}
			for (int i = nlRuntime.actionStack.size(); i > actionSize; --i)
				popAction(nlRuntime);
			nlRuntime.rootNodeList.remove(nextNode);
		}
	}

	private void buildElementPrefixModel(NLRuntime nlRuntime) {
		int lastIndex = nlRuntime.rootNodeList.size() - 1;
		TreeNode nextNode = nlRuntime.rootNodeList.get(lastIndex);
		try {
			while (lastIndex > 0) {
				--lastIndex;
				Link link = new Link(nextNode,
						nlRuntime.rootNodeList.get(lastIndex),
						Direction.LeftSub);
				nextNode.pushSubLink(link);
				nlRuntime.actionStack.push(link);
				nlRuntime.rootNodeList.remove(lastIndex);
			}
		} catch (Exception e) {
			// break on link exceptions
		}
	}

	private Node getLastSubNode(Node node) {
		Node lastSubNode = null;
		for (Link link : node.getLinks()) {
			if (Direction.RightSub.equals(link.getDirection()))
				lastSubNode = link.getEndNode();
		}
		if (null != lastSubNode)
			return getLastSubNode(lastSubNode);
		else
			return node;
	}

	private void buildElementSuffixModel(NLRuntime nlRuntime) {
		int size = nlRuntime.rootNodeList.size();
		if (size < 2)
			return;

		TreeNode currentNode = nlRuntime.rootNodeList.get(size - 1), lastNode = (TreeNode) getLastSubNode(nlRuntime.rootNodeList
				.get(size - 2));

		Link l = null;
		while (true) {
			try {
				Link link = new Link(lastNode, currentNode, Direction.RightSub);
				lastNode.pushSubLink(link);
				nlRuntime.actionStack.push(link);
				// System.out.println(link);
				nlRuntime.rootNodeList.remove(currentNode);

				if (nlRuntime.rootNodeList.contains(lastNode))
					buildElementSuffixModel(nlRuntime);
				break;
			} catch (Exception e) {
			}
			if ((l = lastNode.getParentLink()) == null)
				break;
			lastNode = (TreeNode) l.getStartNode();
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

	private void getNodeText(TreeNode node, StringBuilder buffer) {
		Stack<TreeNode> left = new Stack<TreeNode>();
		List<TreeNode> right = new ArrayList<TreeNode>();
		for (Link link : node.getLinks()) {
			if (Direction.RightSub.equals(link.getDirection()))
				right.add((TreeNode) link.getEndNode());
			else if (Direction.LeftSub.equals(link.getDirection()))
				left.push((TreeNode) link.getEndNode());
		}

		while (!left.isEmpty())
			getNodeText(left.pop(), buffer);
		buffer.append(((WordInfoObject) node.getObject()).getFormOfWord()
				.getWordForm());
		for (TreeNode n : right)
			getNodeText(n, buffer);
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
			getNodeText(root, token);

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

	private void buildInverseClause(NLRuntime nlRuntime) {
		buildInverseClause(nlRuntime, null);
	}

	private void buildInverseClause(NLRuntime nlRuntime, TreeNode stopNode) {
		List<TreeNode> nodeList = nlRuntime.rootNodeList;
		if (nodeList.size() <= 1) {
			if (nodeList.size() == 1)
				saveElement(nlRuntime, stopNode);
			return;
		}

		TreeNode lastNode = nodeList.remove(nodeList.size() - 1);
		nodeList.add(0, lastNode);

		int actionSize = nlRuntime.actionStack.size();
		buildElementSuffixModel(nlRuntime);
		saveElement(nlRuntime, stopNode);

		for (int i = nlRuntime.actionStack.size(); i > actionSize; --i)
			popAction(nlRuntime);

		// rollback
		nodeList.remove(0);
		nodeList.add(lastNode);
	}
}
