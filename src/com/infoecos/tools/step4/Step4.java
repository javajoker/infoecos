package com.infoecos.tools.step4;

import java.util.List;

import com.infoecos.eco.EcoModel;
import com.infoecos.eco.EcoUtil;
import com.infoecos.info.InfoModel;
import com.infoecos.info.InfoUtil;
import com.infoecos.nlp.nl.NLAdapter;
import com.infoecos.nlp.nl.NLManager;

public class Step4 {
	private String infoModelUriIn, ecoModelUri;
	private List<InfoModel> infoModels = null;
	private List<EcoModel> ecoModels = null;

	public Step4(String infoModelUriIn, String ecoModelUri) {
		this.infoModelUriIn = infoModelUriIn;
		this.ecoModelUri = ecoModelUri;
	}

	private void importModels() throws Exception {
		infoModels = InfoUtil.importInfoSet(infoModelUriIn);
		ecoModels = EcoUtil.importEcoSet(ecoModelUri);
	}

	private void saveEcoModels() throws Exception {
		EcoUtil.saveEcos(ecoModels, ecoModelUri);
	}

	private void parseText(String text) throws Exception {
		List<InfoModel> textModel = null;
		for (NLAdapter language : NLManager.getLanguages()) {
			try {
				textModel = language.parseText(infoModels, ecoModels, text);
				break;
			} catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			help();
			System.exit(1);
		}
		Step4 processor = new Step4(args[0], args[1]);
		processor.importModels();
		processor.parseText(args[2]);
		processor.saveEcoModels();
	}

	private static void help() {
		System.out.println("Usage:\n\tStep4 infoModelUriIn ecoModelUri text");
	}
}
