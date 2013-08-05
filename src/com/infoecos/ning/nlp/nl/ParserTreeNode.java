package com.infoecos.ning.nlp.nl;

import com.infoecos.ning.nlp.language.SemanticConcept;
import com.infoecos.ning.nlp.language.WordForm;
import com.infoecos.ning.util.graph.Link;
import com.infoecos.ning.util.graph.NodeObject;
import com.infoecos.ning.util.graph.TreeNode;

public class ParserTreeNode extends TreeNode {
	public static class candidate {
		private double score;
		private SemanticConcept concept;
		private WordForm word;

		public candidate(double score, SemanticConcept concept, WordForm word) {
			this.score = score;
			this.concept = concept;
			this.word = word;
		}

		public double getScore() {
			return score;
		}

		public SemanticConcept getConcept() {
			return concept;
		}

		public WordForm getWord() {
			return word;
		}
	}

	public static class CandidateObject implements NodeObject {
		private candidate candidate;

		public CandidateObject() {
		}

		public CandidateObject(candidate candidate) {
			this.candidate = candidate;
		}

		public candidate getCandidate() {
			return candidate;
		}

		public void setCandidate(candidate candidate) {
			this.candidate = candidate;
		}

		@Override
		public void applyLink(Link link) throws Exception {
			// TODO Auto-generated method stub
		}

		@Override
		public void removeLink(Link link) {
			// TODO Auto-generated method stub
		}

		@Override
		public CandidateObject clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return (CandidateObject) super.clone();
		}
	}

	public ParserTreeNode() {
		super();
	}

	public ParserTreeNode(candidate candidate) {
		setObject(new CandidateObject(candidate));
	}
}
