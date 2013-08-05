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
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;

public class AuxMorphEn extends MorphEn {
	private static List<FormOfWord> Auxiliaries = new ArrayList<FormOfWord>();

	public static final String[] CommonAuxiliaries = { "be", "have", "do",
			"can", "may", "must", "will", "shall", };

	public static void registerAuxiliaries(String aux) {
		Word w = Word.getWord(aux, PartOfSpeech.Reserved);
		Auxiliaries.add(new FormOfWordEn(w));
	}

	static {
		for (String aux : CommonAuxiliaries) {
			registerAuxiliaries(aux);
		}
	}

	@Override
	public FormOfWord[] getFormOfWords(String key) {
		return new FormOfWord[0];
	}

	@Override
	public FormOfWord[] getReservedWords() {
		return (FormOfWord[]) Auxiliaries.toArray(new FormOfWord[Auxiliaries
				.size()]);
	}

	@Override
	public PartOfSpeech getPartOfSpeech() {
		return PartOfSpeech.Reserved;
	}
}
