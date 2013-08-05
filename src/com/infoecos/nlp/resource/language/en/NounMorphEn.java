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

public class NounMorphEn extends MorphEn {

	private static List<FormOfWord> IrregularNs = new ArrayList<FormOfWord>();
	public static final String[][] CommonIrregularNs = new String[][] {
			{ "child", "children" }, { "foot", "feet" }, { "tooth", "teeth" },
			{ "mouse", "mice" }, { "goose", "geese" }, { "ox", "oxen" },
			{ "louse", "lice" }, { "crisis", "crises" },
			{ "german", "germans" }, { "analysis", "analyses" },
			{ "oasis", "oases" }, { "parenthesis", "parentheses" },
			{ "axis", "axes" }, { "ellipsis", "ellipses" },
			{ "hypothesis", "hypotheses" }, { "synopsis", "synopses" },
			{ "erratum", "errata" }, { "addendum", "addenda" },
			{ "medium", "media" },

			{ "hero", "heroes" }, { "tomato", "tomatoes" },
			{ "potato", "potatoes" }, { "negro", "negroes" },

			{ "roof", "roofs" }, { "reef", "reefs" }, { "chief", "chiefs" },
			{ "cliff", "cliffs" }, { "grief", "griefs" }, { "turf", "turfs" },
			{ "belief", "beliefs" }, { "gulf", "gulfs" },
			{ "dwarf", "dwarfs" }, { "safe", "safes" },
			{ "sheriff", "sheriffs" }, { "tariff", "tariffs" }, };

	public static void registerIrregularN(String n, String pl) {
		Word w = Word.getWord(n, PartOfSpeech.Noun);
		IrregularNs.add(new FormOfWordEn(w, pl, InflectionEn.Plural));
		// possessive
		String[] keys = MorphEn.getPossibleMorphosis(pl, new String[][] { {
				"s", "s'" }, }, "'s");
		for (String s : keys) {
			IrregularNs.add(new FormOfWordEn(w, s, InflectionEn.Plural
					| InflectionEn.Possessive));
		}
	}

	static {
		for (String[] cin : CommonIrregularNs) {
			registerIrregularN(cin[0], cin[1]);
		}
	}

	@Override
	public FormOfWord[] getFormOfWords(String key) {
		Word w = Word.getWord(key, PartOfSpeech.Noun);

		List<FormOfWord> fows = new ArrayList<FormOfWord>();
		fows.add(new FormOfWordEn(w));

		String[] keys = MorphEn.getPossibleMorphosis(key, new String[][] {
				{ "y", "ies" }, { "f", "ves" }, { "fe", "ves" },
				{ "s", "ses" }, { "x", "xes" }, { "man", "men" },
				{ "x", "xes" }, }, "s");
		for (String s : keys) {
			fows.add(new FormOfWordEn(w, s, InflectionEn.Plural));
		}

		// possessive
		keys = MorphEn.getPossibleMorphosis(key, new String[][] {
				{ "y", "ies'" }, { "f", "ves'" }, { "fe", "ves'" },
				{ "s", "ses'" }, { "x", "xes'" }, { "man", "men's" }, }, "s'");
		for (String s : keys) {
			fows.add(new FormOfWordEn(w, s, InflectionEn.Plural
					| InflectionEn.Possessive));
		}
		keys = MorphEn.getPossibleMorphosis(key,
				new String[][] { { "s", "s'" }, }, "'s");
		for (String s : keys) {
			fows.add(new FormOfWordEn(w, s, InflectionEn.Possessive));
		}

		// special cases, abbreviations, C.P.A -> C.P.A's
		int len = key.length();
		if (len == 1) {
			fows.add(new FormOfWordEn(w, key + "'s", InflectionEn.Plural));
		} else {
			int sum = 0, idx = -1;
			while ((idx = key.indexOf('.', idx + 1)) > 0) {
				sum++;
			}
			if (sum > 1)
				fows.add(new FormOfWordEn(w, key + "'s", InflectionEn.Plural));
			else if (key.charAt(len - 1) == '.')
				fows.add(new FormOfWordEn(w, key.substring(0, len - 1) + "s.",
						InflectionEn.Plural));
		}

		// special cases in compound ad
		// E.g., cross-eyed, flat-chested
		keys = MorphEn.getPossibleMorphosis(key, new String[][] {
				{ "y", "ied" }, { "e", "ed" }, }, "ed");
		for (String s : keys) {
			fows.add(new FormOfWordEn(w, s, InflectionEn.Nouned));
		}

		return (FormOfWord[]) fows.toArray(new FormOfWord[fows.size()]);
	}

	@Override
	public FormOfWord[] getReservedWords() {
		return (FormOfWord[]) IrregularNs.toArray(new FormOfWord[IrregularNs
				.size()]);
	}

	@Override
	public PartOfSpeech getPartOfSpeech() {
		return PartOfSpeech.Noun;
	}
}
