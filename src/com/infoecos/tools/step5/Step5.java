package com.infoecos.tools.step5;

import java.util.List;

import com.infoecos.eco.EcoModel;
import com.infoecos.eco.EcoUtil;
import com.infoecos.info.InfoModel;
import com.infoecos.info.InfoUtil;
import com.infoecos.nlp.nl.NLAdapter;
import com.infoecos.nlp.nl.NLManager;

public class Step5 {
	private String infoModelUriIn, ecoModelUri;
	private NLAdapter toLanguage = null;
	private List<InfoModel> infoModels = null;
	private List<EcoModel> ecoModels = null;

	public Step5(String infoModelUriIn, String ecoModelUri, String toLanguage)
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

	private String translate(String text) throws Exception {
		List<InfoModel> textModel = null;
		for (NLAdapter language : NLManager.getLanguages()) {
			try {
				textModel = language.parseText(infoModels, ecoModels, text);
				break;
			} catch (Exception e) {
			}
		}

		return NLManager.toString(textModel, toLanguage);
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			help();
			System.exit(1);
		}
		Step5 processor = new Step5(args[0], args[1], args[2]);
		processor.importModels();
		System.out.println(processor.translate(args[3]));
		processor.saveEcoModels();
	}

	private static void help() {
		System.out
				.println("Usage:\n\tStep5 infoModelUriIn ecoModelUri toLanguage text");
	}
}
