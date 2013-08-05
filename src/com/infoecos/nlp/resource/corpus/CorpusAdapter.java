package com.infoecos.nlp.resource.corpus;

import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.Word;

public interface CorpusAdapter {

	String getName();

	Language getLanguage();

	void initialize() throws Exception;

	Word[] getWords();
}
