package com.infoecos.ning.util.graph;

import java.util.Arrays;

import com.infoecos.nlp.resource.language.PartOfSpeech;

public class LinkType {

	private int maxCardinal;
	private Direction direction;
	private boolean forbidRightSub;
	private boolean forceNewPart;

	public LinkType(PartOfSpeech startPartOfSpeech, PartOfSpeech endPartOfSpeech) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0], 1,
				Direction.NA);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, int maxCardinal) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0],
				maxCardinal, Direction.NA);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, Direction direction) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0], 1,
				direction);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, int maxCardinal, Direction direction) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0],
				maxCardinal, direction);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, 1,
				Direction.NA);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			int maxCardinal) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, maxCardinal,
				Direction.NA);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			Direction direction) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, 1, direction,
				false);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, boolean forbidRightSub) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0], 1,
				Direction.NA, forbidRightSub);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, int maxCardinal,
			boolean forbidRightSub) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0],
				maxCardinal, Direction.NA, forbidRightSub);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, Direction direction,
			boolean forbidRightSub) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0], 1,
				direction, forbidRightSub);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, int maxCardinal, Direction direction,
			boolean forbidRightSub) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0],
				maxCardinal, direction, forbidRightSub);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			boolean forbidRightSub) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, 1,
				Direction.NA, forbidRightSub);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			int maxCardinal, boolean forbidRightSub) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, maxCardinal,
				Direction.NA, forbidRightSub);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			Direction direction, boolean forbidRightSub) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, 1, direction,
				forbidRightSub);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, boolean forbidRightSub,
			boolean forceNewPart) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0], 1,
				Direction.NA, forbidRightSub, forceNewPart);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, int maxCardinal,
			boolean forbidRightSub, boolean forceNewPart) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0],
				maxCardinal, Direction.NA, forbidRightSub, forceNewPart);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, Direction direction,
			boolean forbidRightSub, boolean forceNewPart) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0], 1,
				direction, forbidRightSub, forceNewPart);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, int maxCardinal, Direction direction,
			boolean forbidRightSub, boolean forceNewPart) {
		this(startPartOfSpeech, endPartOfSpeech, new PartOfSpeech[0],
				maxCardinal, direction, forbidRightSub, forceNewPart);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			boolean forbidRightSub, boolean forceNewPart) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, 1,
				Direction.NA, forbidRightSub, forceNewPart);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			int maxCardinal, boolean forbidRightSub, boolean forceNewPart) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, maxCardinal,
				Direction.NA, forbidRightSub, forceNewPart);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			Direction direction, boolean forbidRightSub, boolean forceNewPart) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, 1, direction,
				forbidRightSub, forceNewPart);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			int maxCardinal, Direction direction) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, maxCardinal,
				direction, false);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			int maxCardinal, Direction direction, boolean forbidRightSub) {
		this(startPartOfSpeech, endPartOfSpeech, newPartOfSpeech, maxCardinal,
				direction, forbidRightSub, false);
	}

	public LinkType(PartOfSpeech startPartOfSpeech,
			PartOfSpeech endPartOfSpeech, PartOfSpeech[] newPartOfSpeech,
			int maxCardinal, Direction direction, boolean forbidRightSub,
			boolean forceNewPart) {
		this.startPartOfSpeech = startPartOfSpeech;
		this.endPartOfSpeech = endPartOfSpeech;
		this.newPartOfSpeech = newPartOfSpeech;
		this.maxCardinal = maxCardinal;
		this.direction = direction;
		this.forbidRightSub = forbidRightSub;
		this.forceNewPart = forceNewPart;
	}

	public PartOfSpeech getStartPartOfSpeech() {
		return startPartOfSpeech;
	}

	public PartOfSpeech getEndPartOfSpeech() {
		return endPartOfSpeech;
	}

	public PartOfSpeech[] getNewPartOfSpeech() {
		return newPartOfSpeech;
	}

	public int getMaxCardinal() {
		return maxCardinal;
	}

	public Direction getDirection() {
		return direction;
	}

	public boolean isForbidRightSub() {
		return forbidRightSub;
	}

	public boolean isForceNewPart() {
		return forceNewPart;
	}

	@Override
	public String toString() {
		return String.format("%s>%s(%s)", startPartOfSpeech, endPartOfSpeech,
				Arrays.toString(newPartOfSpeech));
	}
}
