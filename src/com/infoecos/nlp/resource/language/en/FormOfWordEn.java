package com.infoecos.nlp.resource.language.en;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;

public class FormOfWordEn extends FormOfWord {
	public FormOfWordEn(Word word) {
		super(word);
	}

	public FormOfWordEn(Word word, String wordForm, int inflection) {
		super(word, wordForm, inflection);
	}

	@Override
	public PartOfSpeech getPartOfSpeech() {
		if (PartOfSpeech.Pronoun.equals(word.getPartOfSpeech()))
			partOfSpeech = PartOfSpeech.Noun;

		if ((inflection & 0x100) > 0) {
			// verb
			if ((inflection & 0xFF & InflectionEn.Gerund) > 0)
				partOfSpeech = PartOfSpeech.Noun;
			else if ((inflection & 0xFF & InflectionEn.PastParticiple) > 0)
				partOfSpeech = PartOfSpeech.Adjective;
		} else if ((inflection & 0x200) > 0) {
			// ad
		} else if ((inflection & 0x400) > 0) {
			// number
			if ((inflection & 0xFF & InflectionEn.Cardinal) > 0)
				;
			if ((inflection & 0xFF & InflectionEn.Ordinal) > 0)
				partOfSpeech = PartOfSpeech.Adverb;
		} else {
			if ((inflection & 0xFF & InflectionEn.Nouned) > 0)
				partOfSpeech = PartOfSpeech.Adjective;
		}
		if ((inflection & 0xFFFF & InflectionEn.Possessive) > 0)
			partOfSpeech = PartOfSpeech.Adjective;

		return (PartOfSpeech.NA.equals(partOfSpeech)) ? word.getPartOfSpeech()
				: partOfSpeech;
	}

	@Override
	public String toString() {
		return String.format("%s|%s%s(%s)", wordForm, word.toString(),
				InflectionEn.getString(inflection), getPartOfSpeech());
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
	public Language getLanguage() {
		return Language.EN;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		FormOfWordEn fow = new FormOfWordEn(word, wordForm, inflection);
		fow.partOfSpeech = partOfSpeech;
		return fow;
	}
}
