package com.infoecos.nlp.nl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.infoecos.info.InfoModel;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.WordManager;

public class NLManager {
	private static Map<Language, NLAdapter> languages = new HashMap<Language, NLAdapter>();
	static {
		Properties prop = new Properties();
		try {
			prop.load(NLManager.class.getResourceAsStream("/languages.prop"));
			String settings[], key, clazz, wordLibId;
			NLAdapter language;
			for (Object k : prop.keySet()) {
				try {
					key = (String) k;
					settings = prop.getProperty(key).split("\\|");
					clazz = settings[0].trim();
					wordLibId = settings[1].trim();
					language = (NLAdapter) Class.forName(clazz).newInstance();
					language.setWordLib(WordManager
							.getInstance(wordLibId));
					language.loadLanguageModel();

					languages.put(Language.get(key), language);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static NLAdapter getLanguageInstance(String language)
			throws Exception {
		for (NLAdapter lang : languages.values()) {
			if (lang.getLanguage().toString().equalsIgnoreCase(language))
				return lang;
		}
		throw new NullPointerException();
	}

	public static NLAdapter getLanguageInstance(Language language)
			throws Exception {
		return languages.get(language);
	}

	public static Collection<NLAdapter> getLanguages() {
		return languages.values();
	}

	public static String toString(List<InfoModel> textModel,
			NLAdapter toLanguage) throws Exception {
		if (textModel == null || textModel.isEmpty())
			return "";

		return toLanguage.toString(textModel);
	}
}
