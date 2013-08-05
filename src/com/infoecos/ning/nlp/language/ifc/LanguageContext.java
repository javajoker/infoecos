package com.infoecos.ning.nlp.language.ifc;

import java.util.List;

import com.infoecos.ning.nlp.language.Phrase;

public interface LanguageContext {

	class phrase {
		private Phrase phrase;
		private double probability;

		public phrase(Phrase phrase, double probability) {
			this.phrase = phrase;
			this.probability = probability;
		}

		public Phrase getPhrase() {
			return phrase;
		}

		public double getProbability() {
			return probability;
		}
	}

	List<phrase> getPhraseCandidates(List<phrase> model);
}
