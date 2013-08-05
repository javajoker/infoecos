package com.infoecos.nlp.resource.dictionary.voa;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.infoecos.nlp.resource.dictionary.DictionaryAdapter;
import com.infoecos.nlp.resource.dictionary.DictionaryWord;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;

public class VOA implements DictionaryAdapter {
	private List<DictionaryWord> dictWords = null;

	public void load(InputStream is) throws Exception {
		dictWords = new ArrayList<DictionaryWord>();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = br.readLine()) != null) {
			String data[] = line.substring(1, line.length() - 1).split("\",\"",
					3), word = data[0], sPos = data[1], meaning = data[2]
					.replaceAll("\"\"", "\"");
			PartOfSpeech pos = PartOfSpeech.NA;
			if ("ad.".equals(sPos)) {
				pos = PartOfSpeech.Adverb;
			} else if ("v.".equals(sPos)) {
				pos = PartOfSpeech.Verb;
			} else if ("prep.".equals(sPos)) {
				pos = PartOfSpeech.Preposition;
			} else if ("n.".equals(sPos)) {
				pos = PartOfSpeech.Noun;
			} else if ("conj.".equals(sPos)) {
				pos = PartOfSpeech.Conjunction;
			} else if ("pro.".equals(sPos)) {
				pos = PartOfSpeech.Pronoun;
			}

			if (!Word.doHaveWord(word, pos)) {
				continue;
			}
			dictWords.add(new DictionaryWord(Word.getWord(word, pos), meaning));
		}
		br.close();
	}

	@Override
	public void initialize() throws Exception {
		load(VOA.class.getResourceAsStream("voa.csv"));
	}

	@Override
	public DictionaryWord[] getWords(String key) throws Exception {
		return VOAUtil.getWords(key);
	}

	@Override
	public DictionaryWord[] getAllDictionaryWords() {
		return (DictionaryWord[]) dictWords
				.toArray(new DictionaryWord[dictWords.size()]);
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