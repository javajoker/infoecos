package com.infoecos.nlp.resource.dictionary.wiktionary;

import java.util.ArrayList;
import java.util.List;

import com.infoecos.nlp.resource.dictionary.DictionaryAdapter;
import com.infoecos.nlp.resource.dictionary.DictionaryWord;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;

public class WiktionaryEn implements DictionaryAdapter {
	private static String TBLNAME = "wiktionary";

	private DictionaryWord[] dictWords = null;
//	private List<Word> words = null;
//
//	private List<Word> load(String posName, PartOfSpeech pos) throws Exception {
//		List<Word> words = new ArrayList<Word>();
//		for (String key : DictionaryUtil.getKeys(TBLNAME,
//				String.format("WHERE `pos`='%s'", posName))) {
//			if (key.split("\\s+").length > 1)
//				continue;
//			words.add(Word.getWord(key, pos));
//		}
//		return words;
//	}

	@Override
	public void initialize() throws Exception {
//		words = new ArrayList<Word>();
//		// words.addAll(load("num", PartOfSpeech.Number));
//		// words.addAll(load("det", PartOfSpeech.NA));
//		// words.addAll(load("abbr", PartOfSpeech.Abbreviation));
//		words.addAll(load("noun", PartOfSpeech.Noun));
//		words.addAll(load("verb", PartOfSpeech.Verb));
//		words.addAll(load("adv", PartOfSpeech.Adv));
//		words.addAll(load("adj", PartOfSpeech.Adj));
//		words.addAll(load("pron", PartOfSpeech.Pronoun));
//		// words.addAll(load("prep", PartOfSpeech.Preposition));
//		for (String key : new String[] { "about", "above", "across", "after",
//				"against", "along", "alongside", "around", "as", "as well as",
//				"at", "before", "behind", "between", "beyond", "by", "cross",
//				"during", "for", "from", "in", "inside", "like", "near", "of",
//				"on", "opposite", "out", "outside", "over", "round", "sans",
//				"save", "than", "through", "till", "to", "under", "until",
//				"upon", "upto", "via", "with", "within", "without" }) {
//			words.add(Word.getWord(key, PartOfSpeech.Preposition));
//		}
//		// words.addAll(load("conj", PartOfSpeech.Conjunction));
//		for (String key : new String[] { "and", "or", "xor", "as", "but",
//				"cause", "coz" }) {
//			words.add(Word.getWord(key, PartOfSpeech.Conjunction));
//		}
//
//		// words.addAll(load("interj", PartOfSpeech.Interjection));
//
//		words.add(Word.getWord("to", PartOfSpeech.Reserved2));

		dictWords = WiktionaryUtil.getAllWords();
	}

	@Override
	public DictionaryWord[] getWords(String key) throws Exception {
		return WiktionaryUtil.getWords(key);
	}

	@Override
	public DictionaryWord[] getAllDictionaryWords() {
		return dictWords;
	}

	@Override
	public String getName() {
		return "wiktionary.en";
	}

	@Override
	public Language getFromLanguage() {
		return Language.EN;
	}

	@Override
	public Language getToLanguage() {
		return Language.EN;
	}
}
