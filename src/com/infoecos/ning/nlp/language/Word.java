package com.infoecos.ning.nlp.language;

import java.util.HashMap;
import java.util.Map;

public class Word {
	public static final Word Skip = new Word("", PartOfSpeech.Skip);

	private static final Map<String, Map<PartOfSpeech, Word>> words = new HashMap<String, Map<PartOfSpeech, Word>>();

	public static boolean doHaveWord(String key, PartOfSpeech pos) {
		if (!words.containsKey(key))
			return false;
		return words.get(key).containsKey(pos);
	}

	public static Word getWord(String key, PartOfSpeech pos) {
		Map<PartOfSpeech, Word> ws = null;
		if (!words.containsKey(key)) {
			ws = new HashMap<PartOfSpeech, Word>();
			words.put(key, ws);
		} else {
			ws = words.get(key);
		}
		Word w = null;
		if (!ws.containsKey(pos)) {
			w = new Word(key, pos);
			ws.put(pos, w);
		} else {
			w = ws.get(pos);
		}
		return w;
	}

	private String key;
	private PartOfSpeech pos = PartOfSpeech.NA;

	// visible in package only
	Word(String key, PartOfSpeech pos) {
		this.key = key;
		this.pos = pos;
	}

	public String getKey() {
		return key;
	}

	public PartOfSpeech getPartOfSpeech() {
		return pos;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", pos, key);
	}
}
