package com.infoecos.tools.step2;

import java.util.List;

import com.infoecos.info.InfoModel;
import com.infoecos.info.InfoUtil;
import com.infoecos.info.WordUtil;
import com.infoecos.nlp.nl.languages.WordModel;

public class Step2 {
	private String wordModelUriIn, infoModelUriOut;
	private List<WordModel> words;
	private List<InfoModel> infoModels = null;

	public Step2(String wordModelUriIn, String infoModelUriOut) {
		this.wordModelUriIn = wordModelUriIn;
		this.infoModelUriOut = infoModelUriOut;
	}

	private void importWordSet() throws Exception {
		words = WordUtil.importWordSet(wordModelUriIn);
	}

	private void buildInfoModels() {
		// TODO Auto-generated method stub

	}

	private void saveInfoModels() throws Exception {
		InfoUtil.saveInfos(infoModels, infoModelUriOut);
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			help();
			System.exit(1);
		}
		Step2 processor = new Step2(args[0], args[1]);
		processor.importWordSet();
		processor.buildInfoModels();
		processor.saveInfoModels();
	}

	private static void help() {
		System.out.println("Usage:\n\tStep2 wordModelUriIn infoModelUriOut");
	}
}