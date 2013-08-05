package com.infoecos.nlp.resource;

import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.infoecos.nlp.nl.languages.Tokenizer;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Inflection;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;

public class ResourceLocator {
	public static void main(String[] args) {
		Tokenizer tk = new Tokenizer("can't-  re-scued all-in-one");
		System.out.println(Arrays.toString(tk.nextToken(0)));
	}

	public static void main2(String[] args) {

		Pattern pSplit = Pattern.compile(String.format(
				"([%s]\\s+)|([%s])|(\\s+)|([^\\s\\w%s]+)", "-", "-", "-"));
		String text = "*(*asdf- asdfasq^&-as@!df-12 31@#@";
		text = "-dd ";
		int offset = 0;

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

		while (!tokens.isEmpty()) {
			String t = tokens.pop();
			t = t.replaceAll("\\s+", "");
			System.out.println(t);
			t = t.replaceAll("-", "");
			System.out.println(t);
		}
	}

	public static void main1(String[] args) {
		String text = "122,123,121sts";
		Pattern pNumber = Pattern.compile("\\d+(?:,\\d{3})*(\\.\\d+)?");
		Pattern pNumberOrdinal = Pattern
				.compile("^(?:1st|2nd|3rd|\\dth)(s)?\\b");

		Matcher m = pNumber.matcher(text);
		FormOfWord fow = null;
		if (m.find()) {
			if (null == m.group(1)) {
				Matcher m2 = pNumberOrdinal.matcher(text.substring(m.group()
						.length() - 1));
				if (m2.find()) {
					fow = new FormOfWord(Word.getWord(m.group(),
							PartOfSpeech.Numberal), m.group()
							+ m2.group().substring(1), Inflection.Ordinal);
					System.out.println(fow);
					return;
				}
			}
			fow = new FormOfWord(
					Word.getWord(m.group(), PartOfSpeech.Numberal), m.group(),
					Inflection.Cardinal);
		}
		System.out.println(fow);
	}
}
