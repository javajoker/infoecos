package com.infoecos.nlp.resource.dictionary;

import com.infoecos.nlp.resource.language.Word;

public class DictionaryWord {
	public static final DictionaryWord Skip = new DictionaryWord(Word.Skip, "");

	private Word word;
	private String meaning = "";

	public DictionaryWord(Word word, String meaning) {
		this.word = word;
		this.meaning = meaning;
	}

	public Word getWord() {
		return word;
	}

	public String getMeaning() {
		return meaning;
	}

	@Override
	public String toString() {
		return String.format("%s %s", word, meaning);
	}

}
