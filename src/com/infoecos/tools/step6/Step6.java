package com.infoecos.tools.step6;

import java.util.List;

import com.infoecos.eco.EcoModel;
import com.infoecos.eco.EcoUtil;
import com.infoecos.info.InfoModel;
import com.infoecos.info.InfoUtil;
import com.infoecos.nlp.nl.NLAdapter;
import com.infoecos.nlp.nl.NLManager;
import com.infoecos.voice.Voice;

public class Step6 {
	private String infoModelUriIn, ecoModelUri;
	private NLAdapter toLanguage = null;
	private List<InfoModel> infoModels = null;
	private List<EcoModel> ecoModels = null;

	public Step6(String infoModelUriIn, String ecoModelUri, String toLanguage)
			throws Exception {
		this.infoModelUriIn = infoModelUriIn;
		this.ecoModelUri = ecoModelUri;

		this.toLanguage = NLManager.getLanguageInstance(toLanguage);
	}

	private void importModels() throws Exception {
		infoModels = InfoUtil.importInfoSet(infoModelUriIn);
		ecoModels = EcoUtil.importEcoSet(ecoModelUri);
	}

	private void saveEcoModels() throws Exception {
		EcoUtil.saveEcos(ecoModels, ecoModelUri);
	}

	private void speak(String text) throws Exception {
		List<InfoModel> textModel = null;
		for (NLAdapter language : NLManager.getLanguages()) {
			try {
				textModel = language.parseText(infoModels, ecoModels, text);
				break;
			} catch (Exception e) {
			}
		}

		String toText = NLManager.toString(textModel, toLanguage);
		Voice.speak(toText, toLanguage);
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			help();
			System.exit(1);
		}
		Step6 processor = new Step6(args[0], args[1], args[2]);
		processor.importModels();
		processor.speak(args[3]);
		processor.saveEcoModels();
	}

	private static void help() {
		System.out
				.println("Usage:\n\tStep6 infoModelUriIn ecoModelUri toLanguage text");
	}
}
