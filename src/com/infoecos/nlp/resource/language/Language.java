package com.infoecos.nlp.resource.language;

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