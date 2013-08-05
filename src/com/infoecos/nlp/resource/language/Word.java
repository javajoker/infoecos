package com.infoecos.nlp.resource.language;

import java.util.HashMap;
import java.util.Map;

public class Word {
	public static final Word Skip = new Word("", PartOfSpeech.Skip);
	private static final Map<String, Map<PartOfSpeech, Word>> words = new HashMap<String, Map<PartOfSpeech, Word>>();

	public static Word getWord(String key, PartOfSpeech pos, int frequency) {
		Word w = getWord(key, pos);
		w.frequency = frequency;
		return w;
	}

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
	private int frequency = 1;

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

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	@Override
	public String toString() {
		return String.format("%s:%s", pos, key);
	}
}
