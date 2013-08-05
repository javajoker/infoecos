package com.infoecos.nlp.nl.languages.en;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.infoecos.eco.EcoModel;
import com.infoecos.info.InfoModel;
import com.infoecos.nlp.nl.NLAdapter;
import com.infoecos.nlp.nl.languages.Grammar;
import com.infoecos.nlp.nl.languages.TokenizerHelper;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.WordLib;
import com.infoecos.nlp.resource.language.en.WordLibEn;

public class English implements NLAdapter {
	private TokenizerHelper tokenizer = new TokenizerHelperEn();
	private Grammar grammar = new GrammarEn();
	private WordLib wordlib = null;

	@Override
	public Language getLanguage() {
		return Language.EN;
	}

	@Override
	public TokenizerHelper getTokenizerHelper() {
		return tokenizer;
	}

	@Override
	public Grammar getGrammar() {
		return grammar;
	}

	@Override
	public void loadLanguageModel() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<InfoModel> parseText(List<InfoModel> infoModels,
			List<EcoModel> ecoModels, String text) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(List<InfoModel> textModel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NLAdapter getSpeechLibrary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWordLib(WordLib wordlib) throws Exception {
		// TODO merge dictionary to dictionary model

		tokenizer.setWordLib(wordlib);
		this.wordlib = wordlib;
	}

	@Override
	public WordLib getWordLib() {
		// TODO Auto-generated method stub
		return wordlib;
	}

	public static void main(String[] args) throws Exception {
		TokenizerHelper en = new TokenizerHelperEn();
		en.setWordLib(new WordLibEn());
		Set<String> keys = new HashSet<String>();
		for (FormOfWord fow : en.getFormOfWords())
			for (String k : fow.getWordForm().toLowerCase().split("\\W+"))
				keys.add(k);
		System.out.println(keys);
	}
}
