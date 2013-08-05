package com.infoecos.nlp.nl;

import java.util.List;

import com.infoecos.eco.EcoModel;
import com.infoecos.info.InfoModel;
import com.infoecos.nlp.nl.languages.Grammar;
import com.infoecos.nlp.nl.languages.TokenizerHelper;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.WordLib;

public interface NLAdapter {
	Language getLanguage();

	TokenizerHelper getTokenizerHelper();

	Grammar getGrammar();

	void setWordLib(WordLib wordlib) throws Exception;

	WordLib getWordLib();

	void loadLanguageModel() throws Exception;

	List<InfoModel> parseText(List<InfoModel> infoModels,
			List<EcoModel> ecoModels, String text) throws Exception;

	String toString(List<InfoModel> textModel) throws Exception;

	NLAdapter getSpeechLibrary();
}
