package com.infoecos.nlp.resource.dictionary;

import com.infoecos.nlp.resource.language.Language;

public interface DictionaryAdapter {

	String getName();

	Language getFromLanguage();

	Language getToLanguage();

	void initialize() throws Exception;

	DictionaryWord[] getWords(String key) throws Exception;

	DictionaryWord[] getAllDictionaryWords();
}
