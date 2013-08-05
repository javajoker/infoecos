package com.infoecos.ning.nlp.language.ifc;

import java.util.List;

import com.infoecos.ning.nlp.language.WordForm;

public interface TextContext {

	class token {
		private WordForm word;
		private double probability;

		public token(WordForm word, double probability) {
			this.word = word;
			this.probability = probability;
		}

		public WordForm getWord() {
			return word;
		}

		public double getProbability() {
			return probability;
		}
	}

	List<token> getTokenCandidates(String text, int offset);
}
