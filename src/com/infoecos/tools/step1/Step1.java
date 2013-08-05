package com.infoecos.tools.step1;

import java.util.ArrayList;
import java.util.List;

import com.infoecos.info.InfoWord;
import com.infoecos.info.WordUtil;
import com.infoecos.nlp.nl.NLAdapter;
import com.infoecos.nlp.nl.NLManager;
import com.infoecos.nlp.nl.parser.NLElement;
import com.infoecos.nlp.nl.parser.NLProcessor;
import com.infoecos.nlp.resource.dictionary.DictionaryAdapter;
import com.infoecos.nlp.resource.dictionary.DictionaryManager;
import com.infoecos.nlp.resource.dictionary.DictionaryWord;
import com.infoecos.nlp.resource.language.PartOfSpeech;

public class Step1 {
	private NLAdapter nl;
	private DictionaryAdapter dictionary;
	private String infoWordUriOut;
	private List<InfoWord> words;

	public Step1(String language, String dictionaryName, String infoWordUriOut)
			throws Exception {
		nl = NLManager.getLanguageInstance(language);
		dictionary = DictionaryManager.getInstance(dictionaryName);
		this.infoWordUriOut = infoWordUriOut;

		// Set<String> keys = new HashSet<String>(), mkeys = new
		// HashSet<String>(), empty = new HashSet<String>();
		// for (FormOfWord fow : nl.getTokenizer().getFormOfWords()) {
		// keys.add(fow.getWordForm().toLowerCase().trim());
		// }
		// for (DictionaryWord w : dictionary.getAllDictionaryWords()) {
		// if (PartOfSpeech.NA.equals(w.getWord().getPartOfSpeech()))
		// continue;
		// String mean = w.getMeaning().toLowerCase();
		// for (String k : mean.split("[^a-z]+"))
		// mkeys.add(k);
		// }
		// for (String k : mkeys) {
		// if (!keys.contains(k))
		// empty.add(k);
		// }
		// System.out.println(empty);
	}

	private void buildWordNLWord() throws Exception {
		words = new ArrayList<InfoWord>();
		NLProcessor nlp = new NLProcessor();
		for (DictionaryWord dw : dictionary.getAllDictionaryWords()) {
			if (PartOfSpeech.NA.equals(dw.getWord().getPartOfSpeech()))
				continue;

			System.out
					.println(String.format("%s [%s] %s", dw.getWord().getKey(),
							dw.getWord().getPartOfSpeech(), dw.getMeaning()));
			InfoWord w = new InfoWord(dw.getWord());
			words.add(w);

			int offset = 0;
			String mean = dw.getMeaning();
			System.out.println(mean);
			for (NLElement ele : nlp.parseNextElement(mean, offset,
					new PartOfSpeech[] { dw.getWord().getPartOfSpeech() }))

				System.out.println(ele);

			// InfoWord wm = new InfoWord(w.getId());
			// for (String m : w.getMeaning().split(";\\s*")) {
			// Word = new MeaningfulInfoWord();
			// m = m.trim().replaceAll("\\[", "").replaceAll("\\]", "")
			// .replaceAll("\\s+", " ");
			// int offset = 0;
			// System.out.println(m);
			// for (NLElement ele : nlp.parseNextElement(m, offset,
			// new PartOfSpeech[] { w.getWord().getPartOfSpeech() }))
			// System.out.println(ele);
			//
			// wm.addWord(Word);
			// }
			// words.add(wm);
			// break;
		}
	}

	private void saveWords() throws Exception {
		WordUtil.saveWords(words, infoWordUriOut);
	}

	public static void main(String[] args) throws Exception {
		args = new String[] { "en", "voa.en", "c:/infoecos/data/en" };
		if (args.length < 3) {
			help();
			System.exit(1);
		}
		Step1 processor = new Step1(args[0], args[1], args[2]);
		processor.buildWordNLWord();
		processor.saveWords();

		// WordUtil.importWordSet(args[2]);
	}

	private static void help() {
		System.out
				.println("Usage:\n\tStep1 language dictionary infoWordUriOut");
	}
}
