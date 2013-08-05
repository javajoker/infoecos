package com.infoecos.nlp.resource.corpus.wordfrequency;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.infoecos.nlp.resource.corpus.CorpusAdapter;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;

/**
 * http://www.wordfrequency.info/uses.asp
 * 
 * @author DCH
 */
public class WordFrequencyInfo implements CorpusAdapter {
	private Set<Word> words = null;

	public void load(InputStream is) throws Exception {
		words = new HashSet<Word>();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		int frequency = 1;
		words = new HashSet<Word>();
		while ((line = br.readLine()) != null) {
			String data[] = line.split(","), word = data[1], sPos = data[2];
			try {
				frequency = Integer.parseInt(data[3]);
			} catch (Exception e) {
			}
			PartOfSpeech pos = PartOfSpeech.NA;
			if ("d".equals(sPos)) {
				pos = PartOfSpeech.Determiner;
			} else if ("e".equals(sPos)) {
				// there (be) ...
				pos = PartOfSpeech.Pronoun;
			} else if ("c".equals(sPos)) {
				pos = PartOfSpeech.Conjunction;
			} else if ("a".equals(sPos)) {
				// possessive / article adj
				pos = PartOfSpeech.Adjective;
			} else if ("n".equals(sPos)) {
				pos = PartOfSpeech.Noun;
			} else if ("m".equals(sPos)) {
				pos = PartOfSpeech.Numberal;
			} else if ("j".equals(sPos)) {
				pos = PartOfSpeech.Adjective;
			} else if ("i".equals(sPos)) {
				pos = PartOfSpeech.Preposition;
			} else if ("v".equals(sPos)) {
				pos = PartOfSpeech.Verb;
			} else if ("u".equals(sPos)) {
				pos = PartOfSpeech.Interjection;
			} else if ("t".equals(sPos)) {
				// to do ...
				pos = PartOfSpeech.Reserved2;
			} else if ("r".equals(sPos)) {
				pos = PartOfSpeech.Adverb;
			} else if ("p".equals(sPos)) {
				pos = PartOfSpeech.Pronoun;
			} else if ("x".equals(sPos)) {
				// not ...
				pos = PartOfSpeech.Adverb;
			}
			words.add(Word.getWord(word, pos, frequency));
		}
		br.close();
	}

	@Override
	public Word[] getWords() {
		return (Word[]) words.toArray(new Word[words.size()]);
	}

	@Override
	public String getName() {
		return "wordfrequency.en";
	}

	@Override
	public Language getLanguage() {
		return Language.EN;
	}

	@Override
	public void initialize() throws Exception {
		load(WordFrequencyInfo.class.getResourceAsStream("top5000.csv"));
	}
}
