package com.infoecos.ning.nlp.language;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WordForm implements Serializable, Externalizable {
	protected Word word;

	protected String wordForm;
	protected List<Inflection> inflections;

	public WordForm(Word word) {
		this(word, word.getKey(), new ArrayList<Inflection>());
	}

	public WordForm(Word word, String wordForm, List<Inflection> inflections) {
		this.word = word;
		this.wordForm = wordForm;
		this.inflections = inflections;
	}

	public Word getWord() {
		return word;
	}

	public String getWordForm() {
		return wordForm;
	}

	public List<Inflection> getInflections() {
		return inflections;
	}

	public PartOfSpeech getPartOfSpeech() {
		PartOfSpeech pos;
		for (int i = inflections.size(); i >= 0; --i) {
			if (!PartOfSpeech.NA.equals(pos = inflections.get(i)
					.getTransferedPartOfSpeech()))
				return pos;
		}
		return word.getPartOfSpeech();
	}

	public void addInflection(Inflection inflection) {
		inflections.add(inflection);
	}

	@Override
	public String toString() {
		return String.format("%s|%s%s(%s)", wordForm, word.toString(),
				getInflectionString(), getPartOfSpeech());
	}

	private String getInflectionString() {
		StringBuilder s = new StringBuilder();
		for (Inflection i : inflections)
			if (!PartOfSpeech.NA.equals(i))
				s.append(":" + i.toString());
		return s.toString();
	}

	public Language getLanguage() {
		return Language.NA;
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
	public Object clone() throws CloneNotSupportedException {
		WordForm fow = new WordForm(word, wordForm, inflections);
		return fow;
	}

	public static String getString(WordForm wordForm) {
		return String.format("0x%4x:%s", wordForm.getInflections(),
				wordForm.getWord());
	}
}
