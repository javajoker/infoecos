package com.infoecos.nlp.resource.language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WordManager {

	private static Map<String, WordLib> wordLibs = null;

	public static void initialize() throws Exception {
		if (null != wordLibs)
			return;

		wordLibs = new HashMap<String, WordLib>();
		Properties prop = new Properties();
		try {
			prop.load(WordManager.class.getResourceAsStream("/wordlibs.prop"));
			String settings[], key, clazz;
			WordLib wordlib;
			for (Object k : prop.keySet()) {
				try {
					key = (String) k;
					settings = prop.getProperty(key).split("\\|");
					clazz = settings[0].trim();
					wordlib = (WordLib) Class.forName(clazz).newInstance();
					wordlib.initialize();

					wordLibs.put(key, wordlib);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WordLib getInstance(String name) throws Exception {
		if (null == wordLibs)
			initialize();
		return wordLibs.get(name);
	}

	public static WordLib[] getInstance(Language language) throws Exception {
		if (null == wordLibs)
			initialize();

		List<WordLib> ws = new ArrayList<WordLib>();
		for (WordLib w : wordLibs.values())
			if (language.equals(w.getLanguage()))
				ws.add(w);
		return (WordLib[]) ws.toArray(new WordLib[ws.size()]);
	}
}
