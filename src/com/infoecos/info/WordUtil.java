package com.infoecos.info;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WordUtil {
	public static void saveWords(List<InfoWord> words, String infoWordUriOut)
			throws Exception {
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
				infoWordUriOut));

		for (InfoWord word : words)
			word.writeExternal(out);

		out.flush();
		out.close();
	}

	public static List<InfoWord> importWordSet(String infoWordUriIn)
			throws Exception {
		List<InfoWord> words = new ArrayList<InfoWord>();
		InfoWord word = null;
		ObjectInput in = new ObjectInputStream(new FileInputStream(
				infoWordUriIn));

		while ((word = (InfoWord) in.readObject()) != null)
			words.add(word);

		in.close();

		return words;
	}
}
