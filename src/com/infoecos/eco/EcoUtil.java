package com.infoecos.eco;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EcoUtil {
	public static void saveEcos(List<EcoModel> ecos, String ecoModelUriOut)
			throws Exception {
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
				ecoModelUriOut));

		for (EcoModel eco : ecos)
			eco.writeExternal(out);
	}

	public static List<EcoModel> importEcoSet(String ecoModelUriIn)
			throws Exception {
		List<EcoModel> ecos = new ArrayList<EcoModel>();
		EcoModel eco = null;
		ObjectInput in = new ObjectInputStream(new FileInputStream(
				ecoModelUriIn));

		while ((eco = (EcoModel) in.readObject()) != null)
			ecos.add(eco);

		return ecos;
	}
}
