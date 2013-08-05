package com.infoecos.nlp.resource.language.en;

import java.util.HashSet;
import java.util.Set;

import com.infoecos.nlp.resource.language.Morph;

public abstract class MorphEn implements Morph {
	protected static String[] getPossibleMorphosis(String key,
			String[][] suffixes, String emptySuffix) {
		Set<String> keys = new HashSet<String>();
		for (String[] s : suffixes) {
			if (key.endsWith(s[0])) {
				keys.add(key.substring(0, key.length() - s[0].length()) + s[1]);
			}
		}

		for (String s : emptySuffix.split(",")) {
			keys.add(key + s);
			// hard code here
			char lastChar = key.charAt(key.length() - 1);
			switch (lastChar) {
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
				break;
			default:
				keys.add(key + lastChar + s);
			}
		}
		return (String[]) keys.toArray(new String[keys.size()]);
	}
}
