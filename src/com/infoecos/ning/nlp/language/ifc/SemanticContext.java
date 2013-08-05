package com.infoecos.ning.nlp.language.ifc;

import java.util.List;

import com.infoecos.ning.nlp.language.SemanticConcept;
import com.infoecos.ning.nlp.language.Word;

public interface SemanticContext {
	
	public class concept {
		private SemanticConcept concept;
		private double probability;

		public concept(SemanticConcept concept, double probability) {
			this.concept = concept;
			this.probability = probability;
		}

		public SemanticConcept getConcept() {
			return concept;
		}

		public double getProbability() {
			return probability;
		}
	}

	List<concept> getSemanticCandidates(Word word, KnowledgeContext context);
}
