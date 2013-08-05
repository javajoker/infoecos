package com.infoecos.util.graph;

import java.util.Stack;

public class Node {
	protected Stack<Link> parentLinks = new Stack<Link>();
	protected Stack<Link> links = new Stack<Link>();
	protected NodeObject object = null;

	public void addParentLink(Link parentLink) {
		if (!parentLinks.contains(parentLink))
			parentLinks.push(parentLink);
	}

	public Link[] getParentLinks() {
		return (Link[]) parentLinks.toArray(new Link[parentLinks.size()]);
	}

	public void removeParentLink(Link link) {
		parentLinks.remove(link);
	}

	public Link[] getLinks() {
		return (Link[]) links.toArray(new Link[links.size()]);
	}

	public void setObject(NodeObject object) {
		this.object = object;
	}

	public NodeObject getObject() {
		return object;
	}

	public void pushSubLink(Link link) throws Exception {
		if (!this.equals(link.getStartNode()))
			throw new NotLinkableException();

		object.applyLink(link);

		Node endNode = link.getEndNode();
		endNode.addParentLink(link);
		links.push(link);
	}

	public Link popSubLink() {
		Link link = links.pop();
		Node endNode = link.getEndNode();
		endNode.removeParentLink(link);

		object.removeLink(link);

		return link;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Node node = new Node();
		node.object = this.object.clone();

		for (Link l : parentLinks) {
			node.parentLinks.add(new Link(l.getStartNode(), node));
		}
		for (Link l : links) {
			node.links.add(new Link(node, (Node) l.getEndNode().clone()));
		}

		return node;
	}
}
