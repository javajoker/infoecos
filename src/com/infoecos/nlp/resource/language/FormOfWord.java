package com.infoecos.nlp.resource.language;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;


public class FormOfWord implements Serializable, Externalizable {
	protected Word word;

	protected String wordForm = null;
	protected int inflection = Inflection.NA;
	protected PartOfSpeech partOfSpeech = PartOfSpeech.NA;

	/**
	 * * word frequency
	 * 
	 * Key - 1.0
	 * 
	 * Inflection - 0.9
	 * 
	 * Wrong spelling - 0.5
	 */
	protected float favorRate = 1.0f;

	public FormOfWord(Word word) {
		this.word = word;
		this.wordForm = word.getKey();
		this.inflection = Inflection.NA;
	}

	public FormOfWord(Word word, String wordForm, int inflection) {
		this.word = word;
		this.wordForm = wordForm;
		this.inflection = inflection;
	}

	public Word getWord() {
		return word;
	}

	public String getWordForm() {
		return wordForm;
	}

	public void setWordForm(String wordForm) {
		this.wordForm = wordForm;
	}

	public int getInflection() {
		return inflection;
	}

	public float getFavorRate() {
		return favorRate;
	}

	public void setFavorRate(float favorRate) {
		this.favorRate = favorRate;
	}

	public PartOfSpeech getPartOfSpeech() {
		return (PartOfSpeech.NA.equals(partOfSpeech)) ? word.getPartOfSpeech()
				: partOfSpeech;
	}

	public void setPartOfSpeech2(PartOfSpeech partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public void addInflection(int inflection) {
		this.inflection |= inflection;
	}

	public void removeInflection(int inflection) {
		this.inflection &= ~inflection;
	}

	public boolean isInflection(int inflection) {
		return (this.inflection & inflection) > 0;
	}

	@Override
	public String toString() {
		return String.format("%s|%s%s(%s)", wordForm, word.toString(),
				Inflection.getString(inflection), getPartOfSpeech());
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
		FormOfWord fow = new FormOfWord(word, wordForm, inflection);
		fow.partOfSpeech = partOfSpeech;
		return fow;
	}

	public static String getString(FormOfWord wordForm) {
		return String.format("0x%4x:%s", wordForm.getInflection(),
				wordForm.getWord());
	}
}
