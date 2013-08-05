package com.infoecos.nlp.resource.language;



public interface Morph {
	PartOfSpeech getPartOfSpeech();

	FormOfWord[] getReservedWords();

	FormOfWord[] getFormOfWords(String key);
}
