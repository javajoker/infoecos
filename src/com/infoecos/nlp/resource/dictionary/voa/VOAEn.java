package com.infoecos.nlp.resource.dictionary.voa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.infoecos.nlp.resource.dictionary.DictionaryAdapter;
import com.infoecos.nlp.resource.dictionary.DictionaryWord;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.PartOfSpeech;

public class VOAEn implements DictionaryAdapter {
	private DictionaryWord[] dictWords = null;

	@Override
	public void initialize() throws Exception {
		dictWords = VOAUtil.getAllWords();

		Map<String, Set<PartOfSpeech>> ws = new HashMap<String, Set<PartOfSpeech>>();
		Set<PartOfSpeech> ps = null;
		for (DictionaryWord w : dictWords) {
			if (!ws.containsKey(w.getWord().getKey())) {
				ps = new HashSet<PartOfSpeech>();
				ws.put(w.getWord().getKey(), ps);
			}
			ps = ws.get(w.getWord().getKey());
			if (!ps.contains(w.getWord().getPartOfSpeech()))
				ps.add(w.getWord().getPartOfSpeech());
		}
	}

	@Override
	public DictionaryWord[] getWords(String key) throws Exception {
		return VOAUtil.getWords(key);
	}

	@Override
	public DictionaryWord[] getAllDictionaryWords() {
		return dictWords;
	}

	@Override
	public String getName() {
		return "voa.en";
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
