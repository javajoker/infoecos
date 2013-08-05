/**
 * Description:  
 * Copyright:   Copyright (c) 2009 
 * Company:     infoecos.com 
 * 
 * Created on 2009-7-11
 * @author  dch
 * @version 1.0
 */
package com.infoecos.nlp.resource.language.en;

import java.util.ArrayList;
import java.util.List;

import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Inflection;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;

public class AbbrMorphEn extends MorphEn {
	private static List<FormOfWord> Abbrs = new ArrayList<FormOfWord>();

	public static final String[][] CommonAbbrs = new String[][] {
			{ "n't", "not" }, { "'s", "is" }, { "'m", "am" }, { "'re", "are" },
			{ "'s", "was" }, { "'re", "were" }, { "'s", "has" },
			{ "'ve", "have" }, { "'d", "had" }, { "'ll", "will" },
			{ "'d", "would" },

			{ "ain't", "be not" }, { "can't", "can not" },
			{ "shan't", "shall not" }, { "won't", "will not" },

			{ "an", "a" },

			{ "sb", "somebody" }, { "sth", "something" },

			{ "are", "be" }, { "am", "be" }, { "is", "be" }, { "has", "have" }, };

	public static void registerAbbreviation(String abbr, String prototype) {
		Word w = Word.getWord(prototype, PartOfSpeech.Abbreviation);
		Abbrs.add(new FormOfWordEn(w, abbr, Inflection.NA));
	}

	static {
		for (String[] cin : CommonAbbrs) {
			registerAbbreviation(cin[0], cin[1]);
		}
	}

	@Override
	public FormOfWord[] getFormOfWords(String key) {
		return new FormOfWord[0];
	}

	@Override
	public FormOfWord[] getReservedWords() {
		return (FormOfWord[]) Abbrs.toArray(new FormOfWord[Abbrs.size()]);
	}

	@Override
	public PartOfSpeech getPartOfSpeech() {
		return PartOfSpeech.Abbreviation;
	}
}
