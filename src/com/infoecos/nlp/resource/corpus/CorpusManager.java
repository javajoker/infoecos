package com.infoecos.nlp.resource.corpus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.infoecos.nlp.resource.language.Language;

public class CorpusManager {
	private static Map<String, CorpusAdapter> corpus = null;

	public static void initialize() throws Exception {
		if (null != corpus)
			return;

		corpus = new HashMap<String, CorpusAdapter>();
		Properties prop = new Properties();
		try {
			prop.load(CorpusManager.class.getResourceAsStream("/corpus.prop"));
			String key, clazz;
			CorpusAdapter corp;
			for (Object k : prop.keySet()) {
				try {
					key = (String) k;
					clazz = prop.getProperty(key);
					corp = (CorpusAdapter) Class.forName(clazz).newInstance();
					corpus.put(key, corp);

					corp.initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static CorpusAdapter getInstance(String name) throws Exception {
		if (null == corpus)
			initialize();
		return corpus.get(name);
	}

	public static CorpusAdapter[] getInstance(Language language)
			throws Exception {
		if (null == corpus)
			initialize();

		List<CorpusAdapter> cs = new ArrayList<CorpusAdapter>();
		for (CorpusAdapter c : corpus.values())
			if (language.equals(c.getLanguage()))
				cs.add(c);
		return (CorpusAdapter[]) cs.toArray(new CorpusAdapter[cs.size()]);
	}
}
