package com.infoecos.nlp.nl.languages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.WordLib;

public abstract class TokenizerHelper {
	protected Map<String, List<FormOfWord>> words = null;
	protected WordLib wordlib = null;

	public TokenizerHelper() {
		loadLanguageModel();
	}

	protected String standardization(String key) {
		return key;
	}

	public void registerWord(FormOfWord fow) {
		String key = standardization(fow.getWordForm());

		List<FormOfWord> fows = null;
		if (words.containsKey(key)) {
			fows = words.get(key);
		} else {
			fows = new ArrayList<FormOfWord>();
			words.put(key, fows);
		}
		fows.add(fow);
	}

	public FormOfWord[] getFormOfWords() throws Exception {
		return wordlib.getFormOfWords();
	}

	public void setWordLib(WordLib wordlib) throws Exception {
		if (null == wordlib || wordlib.equals(this.wordlib))
			return;

		this.wordlib = wordlib;

		words = new HashMap<String, List<FormOfWord>>();
		for (FormOfWord wordForm : wordlib.getFormOfWords()) {
			registerWord(wordForm);
		}
	}

	protected void loadLanguageModel() {
		// TODO Auto-generated method stub
	}

	public abstract FormOfWord[] getNextFormOfWords(String text, int offset);
}
