package com.infoecos.nlp.resource.language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public abstract class WordLib {
	public abstract Language getLanguage();

	public void initialize() throws Exception {
		registerMorphs();
		initializeWords();
	}

	protected abstract void initializeWords() throws Exception;

	protected Map<String, Word> words = null;
	protected Set<String> wfKeys = null;
	protected List<FormOfWord> wordForms = null;
	protected List<Morph> morphs = null;

	protected abstract Class<Morph>[] getMorphClasses();

	protected void registerMorphs() {
		if (null != morphs)
			return;

		morphs = new ArrayList<Morph>();
		wfKeys = new HashSet<String>();
		wordForms = new ArrayList<FormOfWord>();
		for (Class<Morph> morphClass : getMorphClasses()) {
			try {
				morphs.add(morphClass.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (Morph morph : morphs) {
			for (FormOfWord fow : morph.getReservedWords()) {
				registerWord(fow);
			}
		}
	}

	protected void registerWord(FormOfWord fow) {
		if (wfKeys.contains(fow.toString()))
			return;
		wfKeys.add(fow.toString());
		wordForms.add(fow);
	}

	public FormOfWord[] getFormOfWords() throws Exception {
		if (null == wordForms)
			initialize();

		return (FormOfWord[]) wordForms
				.toArray(new FormOfWord[wordForms.size()]);
	}

	protected FormOfWord getPrototype(Word word) {
		return new FormOfWord(word);
	}

	private void addWordFrom(Word word) {
		for (Morph morph : morphs) {
			if (morph.getPartOfSpeech().equals(word.getPartOfSpeech())) {
				for (FormOfWord fow : morph.getFormOfWords(word.getKey())) {
					registerWord(fow);
				}
			}
		}
		// prototype
		registerWord(getPrototype(word));
	}

	protected void addWord(String[] keys, PartOfSpeech pos) {
		if (words == null)
			words = new HashMap<String, Word>();

		for (String key : keys) {
			String id = String.format("[%s]%s", pos, key);
			if (words.containsKey(id))
				continue;

			Word w = Word.getWord(key, pos);
			words.put(id, w);
			addWordFrom(w);
		}
	}

	protected void addWord(Word word) {
		if (words == null)
			words = new HashMap<String, Word>();

		String id = String.format("[%s]%s", word.getPartOfSpeech(),
				word.getKey());
		if (words.containsKey(id))
			return;

		words.put(id, word);
		addWordFrom(word);
	}

	public Word[] getAllWords() throws Exception {
		if (null == words)
			initialize();

		return (Word[]) words.values().toArray(new Word[words.size()]);
	}
}
