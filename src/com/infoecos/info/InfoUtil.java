package com.infoecos.info;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class InfoUtil {

	public static void saveInfos(List<InfoModel> infos, String infoModelUriOut)
			throws Exception {
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
				infoModelUriOut));

		for (InfoModel info : infos)
			info.writeExternal(out);
	}

	public static List<InfoModel> importInfoSet(String infoModelUriIn)
			throws Exception {
		List<InfoModel> infos = new ArrayList<InfoModel>();
		InfoModel info = null;
		ObjectInput in = new ObjectInputStream(new FileInputStream(
				infoModelUriIn));

		while ((info = (InfoModel) in.readObject()) != null)
			infos.add(info);

		return infos;
	}

	public static void mergeInfoModels(List<InfoModel> infoModelsStandard,
			List<InfoModel> infoModels2) throws Exception {
		// TODO Auto-generated method stub

	}
}
