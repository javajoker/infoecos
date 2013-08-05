package com.infoecos.tools.step7;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.infoecos.eco.EcoModel;
import com.infoecos.eco.EcoUtil;
import com.infoecos.info.InfoModel;
import com.infoecos.info.InfoUtil;
import com.infoecos.nlp.nl.NLAdapter;
import com.infoecos.nlp.nl.NLManager;
import com.infoecos.voice.Voice;

public class Step7 {
	private String infoModelUriIn, ecoModelUri, voiceIn;
	private NLAdapter toLanguage = null;
	private List<InfoModel> infoModels = null;
	private List<EcoModel> ecoModels = null;

	public Step7(String infoModelUriIn, String ecoModelUri, String toLanguage,
			String voiceIn) throws Exception {
		this.infoModelUriIn = infoModelUriIn;
		this.ecoModelUri = ecoModelUri;
		this.voiceIn = voiceIn;

		this.toLanguage = NLManager.getLanguageInstance(toLanguage);
	}

	private void importModels() throws Exception {
		infoModels = InfoUtil.importInfoSet(infoModelUriIn);
		ecoModels = EcoUtil.importEcoSet(ecoModelUri);
	}

	private void saveEcoModels() throws Exception {
		EcoUtil.saveEcos(ecoModels, ecoModelUri);
	}

	private void speak() throws Exception {
		StringBuilder speechText = new StringBuilder();
		InputStream in = new FileInputStream(voiceIn);
		in.close();

		List<InfoModel> textModel = null;
		for (NLAdapter language : NLManager.getLanguages()) {
			try {
				textModel = language.getSpeechLibrary().parseText(infoModels,
						ecoModels, speechText.toString());
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
		Step7 processor = new Step7(args[0], args[1], args[2], args[3]);
		processor.importModels();
		processor.speak();
		processor.saveEcoModels();
	}

	private static void help() {
		System.out
				.println("Usage:\n\tStep6 infoModelUriIn ecoModelUri toLanguage voiceIn");
	}
}
