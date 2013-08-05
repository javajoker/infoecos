package com.infoecos.ning.nlp.language;

public enum Language {
	NA, EN, CHN;

	public static Language get(String language) {
		language = language.trim().toLowerCase();
		for (Language l : values()) {
			if (l.name().toLowerCase().equals(language))
				return l;
		}
		return NA;
	}
}