package com.infoecos.info.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infoecos.info.InfoAction;
import com.infoecos.info.InfoClass;
import com.infoecos.info.InfoClassHelper;
import com.infoecos.info.InfoProperty;

public class InfoModelingManager {
	public void mergeAbstractProperty(String propertyId) {
	}

	public InfoProperty createAbstractProperty() {
		return null;
	}

	public void sentenceModeling(InfoClass host, InfoAction action,
			InfoClass object, InfoClass object2) {
		InfoManager manager = InfoManager.getInstance();

		// TBD, check property
		// TBD, counter ++
		InfoProperty property = createAbstractProperty();
		property.isPassive = false;
		property.canDo = action;
		property.classes.add(new InfoClassHelper(host));
		manager.archive(InfoConsts.archiveFile, property);

		property = createAbstractProperty();
		property.isPassive = true;
		property.canDo = action;
		property.classes.add(new InfoClassHelper(object));
		manager.archive(InfoConsts.archiveFile, property);

		property = createAbstractProperty();
		property.isPassive = true;
		property.canDo = action;
		property.classes.add(new InfoClassHelper(object2));
		manager.archive(InfoConsts.archiveFile, property);
	}

	public void propertyModeling(InfoProperty[] properties) {
		Map<List<InfoClass>, List<InfoProperty>> classes = new HashMap<List<InfoClass>, List<InfoProperty>>();
		for (InfoProperty prop : properties) {
			List<InfoProperty> props = null;
			if (classes.containsKey(prop.classes))
				props = classes.get(prop.classes);
			else
				props = new ArrayList<InfoProperty>();
			props.add(prop);
		}
		// TBD, counter ++
		for (List<InfoProperty> props : classes.values()) {
			InfoClassHelper classHelper = new InfoClassHelper(InfoClass.NA);
			classHelper.properties = props;
			InfoManager.getInstance().archive(InfoConsts.archiveFile,
					classHelper);
		}
	}

	public void classModeling(InfoClassHelper[] classHelpers) {
		// TBD, adjust class hierarchy, merge
	}
}
