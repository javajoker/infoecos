package com.infoecos.qa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.infoecos.qa.objs.NewSentenceException;
import com.infoecos.qa.objs.Sentence;
import com.infoecos.qa.objs.Word;

public class NLProcessor {
	private Map<String, Set<Word>> wordCache = new HashMap<String, Set<Word>>();

	public void input(String text) {
		List<Sentence> sentences = new ArrayList<Sentence>();
		Sentence sentence = new Sentence();
		for (int offset = 0, len = text.length(); offset < len;) {
			for (Word word : getPossibleWords(text, offset)) {
				try {
					sentence.pushToSentence(word);
				} catch (NewSentenceException e) {
					sentences.add(sentence);
					sentence = new Sentence(word);
				} catch (Exception e) {
					sentence.rollback();
				}
			}
		}
	}

	private List<Word> getPossibleWords(String text, int offset) {
		text = text.substring(offset);
		List<Word> words = new ArrayList<Word>();
		words.addAll(lookupDictionary(text));
		words.addAll(getHotWordOnWeb(text));
		for (Word word : words) {
			Set<Word> wSet = null;
			String token = word.getToken();
			if (wordCache.containsKey(token)) {
				wSet = wordCache.get(token);
			} else {
				wSet = new HashSet<Word>();
				wordCache.put(token, wSet);
			}
			wSet.add(word);
		}
		return words;
	}

	private List<Word> lookupDictionary(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Word> getHotWordOnWeb(String text) {
		// TODO Auto-generated method stub
		return null;
	}
}
