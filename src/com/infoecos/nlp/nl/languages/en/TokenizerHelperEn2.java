package com.infoecos.nlp.nl.languages.en;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.infoecos.nlp.nl.languages.TokenizerHelper;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Inflection;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;
import com.infoecos.nlp.resource.language.en.FormOfWordEn;

public class TokenizerHelperEn2 extends TokenizerHelper {
	private static String[] punctuations = { "...", ".", ";", "!", "?", ",",
			"/", "-", ":", "\"", "'", "[", "]", "{", "}", "(", ")", "<", ">",
			"+", "*", "^", "=", "&", "|", "_", "\\", "#", "`", "~", "@" };
	private static String[] stopPunctuations = { "...", ".", ";", "!", "?", };
	private static String[] linkPunctuations = { ",", "/", "-" };
	private static String[] lbrPunctuations = { "{", "[", "(" };
	private static String[] rbrPunctuations = { "}", "]", ")" };

	public TokenizerHelperEn2() {
		super();
	}

	@Override
	protected String standardization(String key) {
		if (key.length() <= 1)
			return key.toLowerCase();
		String low = key.substring(0, 1).toLowerCase() + key.substring(1);
		if (low.equals(low.toLowerCase()))
			key = key.toLowerCase();
		return key;
	}

	protected Pattern pNumber = Pattern.compile("^\\d+(?:,\\d{3})*(\\.\\d+)?");
	protected Pattern pNumberOrdinal = Pattern
			.compile("^(?:1st|2nd|3rd|\\dth)(s)?\\b");

	protected FormOfWord getNumber(String text) {
		Matcher m = pNumber.matcher(text);
		if (m.find()) {
			if (null == m.group(1)) {
				Matcher m2 = pNumberOrdinal.matcher(text.substring(m.group()
						.length() - 1));
				if (m2.find())
					return new FormOfWordEn(Word.getWord(m.group(),
							PartOfSpeech.Numberal), m.group()
							+ m2.group().substring(1), Inflection.Ordinal);
			}
			return new FormOfWordEn(Word.getWord(m.group(),
					PartOfSpeech.Numberal), m.group(), Inflection.Cardinal);
		}
		return null;
	}

	@Override
	public FormOfWord[] getNextFormOfWords(String text, int offset) {
		text = text.substring(offset);

		Map<String, FormOfWord> fows = new HashMap<String, FormOfWord>();
		FormOfWord wordForm = getNumber(text);
		if (null != wordForm)
			fows.put(FormOfWord.getString(wordForm), wordForm);
		for (String[] key : getNextKeys(text)) {
			if ("".equals(key[1]))
				return new FormOfWord[] { new FormOfWordEn(Word.Skip, key[0],
						Inflection.NA) };
			if (words.containsKey(standardization(key[1]))) {
				for (FormOfWord fow : words.get(standardization(key[1]))) {
					// quick merge
					FormOfWord wf = new FormOfWordEn(fow.getWord(), key[0],
							fow.getInflection());
					fows.put(FormOfWord.getString(wf), wf);
				}
			}
			// if (WordEN.isNameable(key[1]))
			// fows.add(new FormOfWordEn(key[0], key[1],
			// Inflection.Name, PartOfSpeech.Pronoun));

			if (fows.size() == 0) {
				char ch = key[1].charAt(0);
				PartOfSpeech pos = ((ch >= 'A' && ch <= 'Z')
						|| (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) ? PartOfSpeech.NA
						: PartOfSpeech.Punctuation;
				int inflection = Inflection.NA;
				for (String stop : stopPunctuations) {
					if (key[1].equals(stop))
						inflection |= Inflection.Stop;
				}
				for (String link : linkPunctuations) {
					if (key[1].equals(link))
						inflection |= Inflection.Link;
				}
				for (String link : lbrPunctuations) {
					if (key[1].equals(link))
						inflection |= Inflection.LeftBracket;
				}
				for (String link : rbrPunctuations) {
					if (key[1].equals(link))
						inflection |= Inflection.RightBracket;
				}
				return new FormOfWord[] { new FormOfWordEn(Word.getWord(key[1],
						pos), key[0], inflection) };
			}
		}
		return (FormOfWord[]) fows.values().toArray(
				new FormOfWord[fows.values().size()]);
	}

	private static Pattern ptnSkip = Pattern.compile("\\s+");
	private static Pattern ptnNot = Pattern.compile("^(n't)\\b",
			Pattern.CASE_INSENSITIVE);

	protected String[][] getNextKeys(String text) {
		if (null == text || "".equals(text))
			return new String[0][];

		List<String[]> keys = new ArrayList<String[]>();

		Matcher m = ptnSkip.matcher(text);
		int start = 0, end = 0;
		boolean matched = false;
		String key = "", beginSpace = "";
		StringBuilder sbPrototype = new StringBuilder(), sbToken = new StringBuilder();
		if (m.find()) {
			end = m.start();
			if (end == 0) {
				beginSpace = m.group();
				sbToken.append(beginSpace);
				start = m.end();
			}
		}

		do {
			if (m.find(start)) {
				end = m.start();
				matched = true;
			} else {
				end = text.length();
				if (start == end)
					break;
			}
			key = standardization(text.substring(start, end));

			for (int i = start, j = 0; i < end; ++i, ++j) {
				char ch = key.charAt(j);
				if (!((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9'))) {
					// special case, n't
					if (i > 0 && ch == '\'') {
						if (ptnNot.matcher(text.substring(i - 1)).find()) {
							if (words.containsKey(standardization(sbPrototype
									.substring(0, sbPrototype.length() - 1))))
								keys.add(new String[] {
										sbToken.substring(0,
												sbToken.length() - 1),
										sbPrototype.substring(0,
												sbPrototype.length() - 1) });
						}
					}
					if (sbPrototype.length() > 0) {
						if (words.containsKey(standardization(sbPrototype
								.toString())))
							keys.add(new String[] { sbToken.toString(),
									sbPrototype.toString() });
						else if (sbPrototype.toString().matches(
								"\\d+(,\\d+)*(\\.\\d+)?"))
							keys.add(new String[] { sbToken.toString(),
									sbPrototype.toString() });
					}

					sbToken.append(text.charAt(i));
					if ('-' != ch) {
						sbPrototype.append(ch);
						if (words.containsKey(standardization(sbPrototype
								.toString())))
							keys.add(new String[] { sbToken.toString(),
									sbPrototype.toString() });
					}

					// if (WordEN.isNameable(sbToken.toString())) {
					// keys.add(new String[] { sbToken.toString(),
					// sbPrototype.toString() });
					// }
				} else {
					sbPrototype.append(ch);
					sbToken.append(text.charAt(i));
				}
			}

			if (!key.endsWith("-") && !key.endsWith("�C"))
				break;

			// deal with hyphen
			if (matched) {
				start = m.end();
				sbToken.append(m.group());
			}
		} while (true);

		if (words.containsKey(standardization(sbPrototype.toString())))
			keys.add(new String[] { sbToken.toString(), sbPrototype.toString() });

		if (keys.size() == 0) {
			String txt = text.substring(beginSpace.length());
			for (String p : punctuations)
				if (txt.startsWith(p))
					return new String[][] { { beginSpace + p, p } };
			return new String[][] { { sbToken.toString(),
					sbPrototype.toString() } };
		}

		return (String[][]) keys.toArray(new String[keys.size()][]);
	}
}
