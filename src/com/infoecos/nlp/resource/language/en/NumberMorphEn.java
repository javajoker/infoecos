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

public class NumberMorphEn extends MorphEn {
	private static List<FormOfWord> Numbers = new ArrayList<FormOfWord>();

	private static String[][] CommonCardinals = { { "zero", "0" },
			{ "one", "1" }, { "two", "2" }, { "three", "3" }, { "four", "4" },
			{ "five", "5" }, { "six", "6" }, { "seven", "7" },
			{ "eight", "8" }, { "nine", "9" }, { "ten", "10" },
			{ "eleven", "11" }, { "twelve", "12" }, { "thirteen", "13" },
			{ "fourteen", "14" }, { "fifteen", "15" }, { "sixteen", "16" },
			{ "seventeen", "17" }, { "eighteen", "18" }, { "nineteen", "19" },
			{ "twenty", "20" }, { "thirty", "30" }, { "forty", "40" },
			{ "fifty", "50" }, { "sixty", "60" }, { "seventy", "70" },
			{ "eighty", "80" }, { "ninety", "90" }, { "hundred", "100" },
			{ "thousand", "1000" }, { "million", "1000000" },
			{ "billion", "1000000000000,1000000000" },
			{ "trillion", "1000000000000000000,1000000000000" },
			{ "point", "." }, };

	private static String[][] CommonOrdinals = { { "1st", "1" },
			{ "2nd", "2" }, { "3rd", "3" }, { "4th", "4" }, { "5th", "5" },
			{ "6th", "6" }, { "7th", "7" }, { "8th", "8" }, { "9th", "9" },
			{ "0th", "0" },

			{ "first", "1" }, { "second", "2" }, { "third", "3" },
			{ "fourth", "4" }, { "fifth", "5" }, { "sixth", "6" },
			{ "seventh", "7" }, { "eighth", "8" }, { "ninth", "9" },
			{ "tenth", "10" }, { "eleventh", "11" }, { "twelfth", "12" },
			{ "thirteenth", "13" }, { "fourteenth", "14" },
			{ "fifteenth", "15" }, { "sixteenth", "16" },
			{ "seventeenth", "17" }, { "eighteenth", "18" },
			{ "nineteenth", "19" }, { "twenty", "20" }, { "thirtieth", "30" },
			{ "fortieth", "40" }, { "fiftieth", "50" }, { "sixtieth", "60" },
			{ "seventieth", "70" }, { "eightieth", "80" },
			{ "ninetieth", "90" }, { "hundredth", "100" },
			{ "thousandth", "1000" }, { "millionth", "1000000" },
			{ "billionth", "1000000000000,1000000000" },
			{ "trillionth", "1000000000000000000,1000000000000" }, };

	public static void registerCardinal(String word, String number) {
		Word w = Word.getWord(number, PartOfSpeech.Numberal);
		Numbers.add(new FormOfWordEn(w, word, InflectionEn.Cardinal));
	}

	public static void registerOrdinal(String word, String number) {
		Word w = Word.getWord(number, PartOfSpeech.Numberal);
		Numbers.add(new FormOfWordEn(w, word, InflectionEn.Ordinal));
	}

	static {
		for (String[] cin : CommonCardinals) {
			registerCardinal(cin[0], cin[1]);
		}
		for (String[] cin : CommonOrdinals) {
			registerOrdinal(cin[0], cin[1]);
		}
	}

	@Override
	public FormOfWord[] getFormOfWords(String key) {
		return new FormOfWord[0];
	}

	@Override
	public FormOfWord[] getReservedWords() {
		return (FormOfWord[]) Numbers.toArray(new FormOfWord[Numbers.size()]);
	}

	@Override
	public PartOfSpeech getPartOfSpeech() {
		return PartOfSpeech.Numberal;
	}
}
