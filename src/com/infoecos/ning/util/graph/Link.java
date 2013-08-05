package com.infoecos.ning.util.graph;

public class Link {
	private Node startNode = null;
	private Node endNode = null;
	private LinkType type = null;
	private Weight weight = Weight.NA;
	private Direction direction = Direction.NA;

	public Link(Node startNode, Node endNode) {
		this(startNode, endNode, Direction.NA);
	}

	public Link(Node startNode, Node endNode, Direction direction) {
		this(startNode, endNode, direction, null);
	}

	public Link(Node startNode, Node endNode, Direction direction, LinkType type) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.direction = direction;
		this.type = type;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	public LinkType getType() {
		return type;
	}

	public void setType(LinkType type) {
		this.type = type;
	}

	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public Node getOtherNode(Node node) throws Exception {
		if (node == null)
			throw new NullPointerException();

		if (node.equals(startNode))
			return endNode;
		else
			return startNode;
	}

	@Override
	public String toString() {
		return String.format("%s >>(%s)>> %s", startNode, direction, endNode);
	}
}
