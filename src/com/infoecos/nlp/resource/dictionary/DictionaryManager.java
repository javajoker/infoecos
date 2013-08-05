package com.infoecos.nlp.resource.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.infoecos.nlp.resource.corpus.CorpusManager;
import com.infoecos.nlp.resource.language.Language;

public class DictionaryManager {
	private static Map<String, DictionaryAdapter> dictionaries = null;

	public static void initialize() throws Exception {
		if (null != dictionaries)
			return;

		CorpusManager.initialize();
		dictionaries = new HashMap<String, DictionaryAdapter>();

		Properties prop = new Properties();
		try {
			prop.load(DictionaryManager.class
					.getResourceAsStream("/dictionaries.prop"));
			String key, clazz;
			DictionaryAdapter dictionary;
			for (Object k : prop.keySet()) {
				try {
					key = (String) k;
					clazz = prop.getProperty(key);
					dictionary = (DictionaryAdapter) Class.forName(clazz)
							.newInstance();
					dictionaries.put(key, dictionary);

					dictionary.initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DictionaryAdapter getInstance(String name) throws Exception {
		if (null == dictionaries)
			initialize();
		return dictionaries.get(name);
	}

	public static DictionaryAdapter[] getInstance(Language fromLanguage,
			Language toLanguage) throws Exception {
		if (null == dictionaries)
			initialize();

		List<DictionaryAdapter> ds = new ArrayList<DictionaryAdapter>();
		for (DictionaryAdapter d : dictionaries.values())
			if (fromLanguage.equals(d.getFromLanguage())
					&& toLanguage.equals(d.getToLanguage()))
				ds.add(d);
		return (DictionaryAdapter[]) ds
				.toArray(new DictionaryAdapter[ds.size()]);
	}

	public static DictionaryAdapter[] getFromInstance(Language language)
			throws Exception {
		if (null == dictionaries)
			initialize();

		List<DictionaryAdapter> ds = new ArrayList<DictionaryAdapter>();
		for (DictionaryAdapter d : dictionaries.values())
			if (language.equals(d.getFromLanguage()))
				ds.add(d);
		return (DictionaryAdapter[]) ds
				.toArray(new DictionaryAdapter[ds.size()]);
	}

	public static DictionaryAdapter[] getToInstance(Language language)
			throws Exception {
		if (null == dictionaries)
			initialize();

		List<DictionaryAdapter> ds = new ArrayList<DictionaryAdapter>();
		for (DictionaryAdapter d : dictionaries.values())
			if (language.equals(d.getToLanguage()))
				ds.add(d);
		return (DictionaryAdapter[]) ds
				.toArray(new DictionaryAdapter[ds.size()]);
	}
}
