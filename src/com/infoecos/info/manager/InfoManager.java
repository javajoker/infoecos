package com.infoecos.info.manager;

import java.io.File;

import com.infoecos.info.InfoClassHelper;
import com.infoecos.info.InfoModel;
import com.infoecos.info.InfoProperty;

public class InfoManager {
	private static InfoManager instance = null;

	public static InfoManager getInstance() {
		if (instance == null)
			instance = new InfoManager();
		return instance;
	}

	public void loadModel(File archivedFile) {
	}

	public void archive(File archive, InfoModel model) {
	}

	public void loadProperty(File archivedFile) {
	}

	public void archive(File archive, InfoProperty property) {
	}

	public void loadClassHelper(File archivedFile) {
	}

	public void archive(File archive, InfoClassHelper clazz) {
	}
}
