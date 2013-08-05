package com.infoecos.nlp.nl.parser;

import com.infoecos.util.graph.TreeNode;

public class NLElement {
	private String token;
	private TreeNode model;
	private float score;

	public NLElement(String token, TreeNode model) {
		this.token = token;
		this.model = model;
	}

	public String getToken() {
		return token;
	}

	public TreeNode getModel() {
		return model;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return String.format("Element: %s - %s", token, model);
	}
}
