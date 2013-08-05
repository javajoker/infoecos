package com.infoecos.util.graph;

import com.infoecos.nlp.nl.parser.WordInfoObject;

public class TreeNode extends Node {
	public void setParentLink(Link parentLink) {
		parentLinks.clear();
		if (parentLink != null)
			parentLinks.push(parentLink);
	}

	public Link getParentLink() {
		if (parentLinks.isEmpty())
			return null;
		return parentLinks.peek();
	}

	@Override
	public void pushSubLink(Link link) throws Exception {
		if (!this.equals(link.getStartNode()))
			throw new NotLinkableException();

		TreeNode endNode = (TreeNode) link.getEndNode();
		if (endNode.getParentLink() != null
				&& !this.equals(endNode.getParentLink().getStartNode()))
			throw new MultipleParentNodeException();

		object.applyLink(link);

		endNode.setParentLink(link);
		links.push(link);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		TreeNode node = new TreeNode();
		node.object = this.object.clone();

		for (Link l : parentLinks) {
			node.parentLinks.add(new Link((TreeNode) l.getStartNode(), node, l
					.getDirection(), l.getType()));
		}
		for (Link l : links) {
			node.links.add(new Link(node, (TreeNode) l.getEndNode().clone(), l
					.getDirection(), l.getType()));
		}

		return node;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		verbose(this, sb);
		return sb.toString();
	}

	private void verbose(TreeNode node, StringBuilder sb) {
		sb.append(String.format("(%s\n", (WordInfoObject) node.getObject()));
		for (Link link : node.getLinks()) {
			verbose((TreeNode) link.getEndNode(), sb);
		}
		sb.append(")");
	}
}
