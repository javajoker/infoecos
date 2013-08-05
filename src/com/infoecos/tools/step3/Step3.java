package com.infoecos.tools.step3;

import java.util.List;

import com.infoecos.info.InfoModel;
import com.infoecos.info.InfoUtil;

public class Step3 {
	private String infoModelUriStandard, infoModelUriIn;
	private List<InfoModel> infoModelsStandard = null, infoModels2 = null;

	public Step3(String infoModelUriStandard, String infoModelUriIn) {
		this.infoModelUriStandard = infoModelUriStandard;
		this.infoModelUriIn = infoModelUriIn;
	}

	private void importInfoModels() throws Exception {
		infoModelsStandard = InfoUtil.importInfoSet(infoModelUriStandard);
		infoModels2 = InfoUtil.importInfoSet(infoModelUriIn);
	}

	private void saveInfoModels() throws Exception {
		InfoUtil.saveInfos(infoModelsStandard, infoModelUriStandard);
	}

	private void mergeInfoModels() throws Exception {
		InfoUtil.mergeInfoModels(infoModelsStandard, infoModels2);
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			help();
			System.exit(1);
		}
		Step3 processor = new Step3(args[0], args[1]);
		processor.importInfoModels();
		processor.mergeInfoModels();
		processor.saveInfoModels();
	}

	private static void help() {
		System.out
				.println("Usage:\n\tStep3 infoModelUriStandard infoModelUriIn");
	}
}
