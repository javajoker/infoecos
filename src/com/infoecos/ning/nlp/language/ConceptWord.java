package com.infoecos.ning.nlp.language;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

public class ConceptWord implements Serializable, Externalizable {

	protected Word word;

	protected SemanticConcept concept;

	public ConceptWord(Word word, SemanticConcept concept) {
		this.word = word;
		this.concept = concept;
	}

	public Word getWord() {
		return word;
	}

	public SemanticConcept getConcept() {
		return concept;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new ConceptWord(word, (SemanticConcept) concept.clone());
	}

}
