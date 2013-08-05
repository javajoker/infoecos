package com.infoecos.nlp.nl.languages;

import java.util.ArrayList;
import java.util.List;

import com.infoecos.nlp.nl.NLAdapter;
import com.infoecos.nlp.nl.NLManager;
import com.infoecos.nlp.resource.language.FormOfWord;

public class Tokenizer {
	protected String text;

	protected static List<TokenizerHelper> tokenizers;
	static {
		tokenizers = new ArrayList<TokenizerHelper>();
		for (NLAdapter nl : NLManager.getLanguages()) {
			tokenizers.add(nl.getTokenizerHelper());
		}
	}

	public Tokenizer(String text) {
		this.text = text;
	}

	public FormOfWord[] nextToken(int offset) {
		List<FormOfWord> fows = new ArrayList<FormOfWord>();
		for (TokenizerHelper th : tokenizers) {
			for (FormOfWord fow : th.getNextFormOfWords(text, offset))
				fows.add(fow);
		}
		return (FormOfWord[]) fows.toArray(new FormOfWord[fows.size()]);
	}
}
