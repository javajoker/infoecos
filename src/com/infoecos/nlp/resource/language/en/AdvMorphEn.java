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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;

public class AdvMorphEn extends AdjMorphEn {
	private static Map<String, String[]> IrregularAds = new HashMap<String, String[]>();

	public static final String[][] CommonIrregularAds = new String[][] {
			{ "well", "better", "best" }, { "ill", "worse", "worst" },
			{ "badly", "worse", "worst" }, { "little", "less", "least" },
			{ "much", "more", "most" },
			{ "far", "farther,further", "farthest,furthest" },
			{ "late", "later,latter", "latest,last" }, };

	static {
		for (String[] civ : CommonIrregularAds) {
			IrregularAds.put(civ[0], new String[] { civ[1], civ[2] });
		}
	}

	@Override
	public FormOfWord[] getFormOfWords(String key) {
		List<FormOfWord> fows = new ArrayList<FormOfWord>();
		String[] keys = MorphEn.getPossibleMorphosis(key, new String[][] {
				{ "y", "ier" }, { "e", "er" }, }, "er");
		Word w = Word.getWord(key, getPartOfSpeech());

		if (IrregularAds.containsKey(key)) {
			String forms[] = IrregularAds.get(key), comparativeDegree = forms[0], superlativeDegree = forms[1];
			for (String c : comparativeDegree.split(","))
				fows.add(new FormOfWordEn(w, c, InflectionEn.ComparativeDegree));
			for (String s : superlativeDegree.split(","))
				fows.add(new FormOfWordEn(w, s, InflectionEn.SuperlativeDegree));
		} else {
			for (String s : keys) {
				fows.add(new FormOfWordEn(w, s, InflectionEn.ComparativeDegree));
			}
			keys = MorphEn.getPossibleMorphosis(key, new String[][] {
					{ "y", "iest" }, { "e", "est" }, }, "est");
			for (String s : keys) {
				fows.add(new FormOfWordEn(w, s, InflectionEn.SuperlativeDegree));
			}
		}

		return (FormOfWord[]) fows.toArray(new FormOfWord[fows.size()]);
	}

	@Override
	public PartOfSpeech getPartOfSpeech() {
		return PartOfSpeech.Adverb;
	}
}
