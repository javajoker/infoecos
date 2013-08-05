package com.infoecos.nlp.nl.languages.en;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.infoecos.nlp.nl.languages.TokenizerHelper;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Inflection;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;
import com.infoecos.nlp.resource.language.en.FormOfWordEn;

public class TokenizerHelperEn extends TokenizerHelper {

	public TokenizerHelperEn() {
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

	private static String stopPunctuations = "\\.\\.\\.|[\\.;!?]",
			linkPunctuations = "[,/-]", lbrPunctuations = "[{\\[\\(]",
			rbrPunctuations = "[}\\])]";

	private String getPuncuation(String text, String punctuations) {
		Matcher m = Pattern.compile(String.format("^%s", punctuations))
				.matcher(text);
		if (m.find())
			return m.group();
		return null;
	}

	private FormOfWord getPunctuation(String text) {
		String p = getPuncuation(text, stopPunctuations);
		if (null != p)
			return new FormOfWordEn(Word.getWord(p.trim(),
					PartOfSpeech.Punctuation), p, Inflection.Stop);
		p = getPuncuation(text, linkPunctuations);
		if (null != p)
			return new FormOfWordEn(Word.getWord(p.trim(),
					PartOfSpeech.Punctuation), p, Inflection.Link);
		p = getPuncuation(text, lbrPunctuations);
		if (null != p)
			return new FormOfWordEn(Word.getWord(p.trim(),
					PartOfSpeech.Punctuation), p, Inflection.LeftBracket);
		p = getPuncuation(text, rbrPunctuations);
		if (null != p)
			return new FormOfWordEn(Word.getWord(p.trim(),
					PartOfSpeech.Punctuation), p, Inflection.RightBracket);
		if (!text.split("\\w", 2)[0].isEmpty())
			return new FormOfWordEn(Word.getWord("" + text.charAt(0),
					PartOfSpeech.Punctuation), "" + text.charAt(0),
					Inflection.NA);

		return null;
	}

	private static String hyphenPtn = "-";
	private Pattern pSplit = Pattern.compile(String.format(
			"([%s]\\s+)|([%s])|(\\s+)|([^\\s\\w%s]+)", hyphenPtn, hyphenPtn,
			hyphenPtn));
	private Pattern pPunctuation = Pattern.compile("^.+?(\\W*)$");

	private List<String[]> matchedKey(String token, Set<String> ks,
			String tokStr) {
		List<String[]> keys = new ArrayList<String[]>();
		token = standardization(token);
		Matcher m = pPunctuation.matcher(token);
		m.find();
		int l = m.group(1) == null ? 0 : m.group(1).length();
		for (int i = token.length(), j = 0, k = tokStr.lastIndexOf(token
				.charAt(token.length() - 1)) + 1; j <= l; --i, ++j, --k) {
			String key = token.substring(0, i);
			if (words.containsKey(key) && !ks.contains(key))
				keys.add(new String[] { key, tokStr.substring(0, k) });
		}
		return keys;
	}

	@Override
	public FormOfWord[] getNextFormOfWords(String text, int offset) {
		text = text.substring(offset);

		List<FormOfWord> fows = new ArrayList<FormOfWord>();
		FormOfWord wordForm = getNumber(text);
		if (null != wordForm)
			fows.add(wordForm);
		wordForm = getPunctuation(text);
		if (null != wordForm)
			fows.add(wordForm);

		offset = 0;

		Stack<String> tokens = new Stack<String>();
		StringBuilder token = new StringBuilder();
		Matcher m = pSplit.matcher(text);
		boolean first = true, found = false;
		while (m.find()) {
			found = true;
			token.append(text.substring(offset, m.end()));
			offset = m.end();
			if (null != m.group(3)) {
				tokens.push(token.toString());
				found = true;
				break;
			} else if (null != m.group(1) || null != m.group(2)) {
				if (first) {
					first = false;
					tokens.push(token.toString());
				}
			} else if (null != m.group(4)) {
				tokens.push(token.toString());
			}
		}
		if (!found)
			tokens.push(text);

		Set<String> ks = new HashSet<String>();
		List<String[]> keys = new ArrayList<String[]>();
		while (!tokens.isEmpty()) {
			String tk = tokens.pop(), t;
			t = tk.replaceAll("\\s+", "");
			keys.addAll(matchedKey(t, ks, tk));
			t = t.replaceAll(String.format("[%s]", hyphenPtn), "");
			keys.addAll(matchedKey(t, ks, tk));
		}

		Set<String> fowStrs = new HashSet<String>();
		for (String[] k : keys) {
			if (fowStrs.contains(k[0]))
				continue;
			fowStrs.add(k[0]);
			for (FormOfWord fow : words.get(k[0])) {
				fows.add(new FormOfWordEn(fow.getWord(), k[1], fow
						.getInflection()));
			}
		}
		if (fows.size() == 0)
			fows.add(new FormOfWordEn(Word.getWord(text.split("\\W", 2)[0],
					PartOfSpeech.NA)));

		return (FormOfWord[]) fows.toArray(new FormOfWord[fows.size()]);
	}
}
