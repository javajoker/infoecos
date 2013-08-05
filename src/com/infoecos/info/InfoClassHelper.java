package com.infoecos.info;

import java.util.List;

import com.infoecos.util.graph.Node;

public class InfoClassHelper extends Node {
	public InfoClass clazz;
	public List<InfoProperty> properties;

	public InfoClassHelper(InfoClass clazz) {
		this.clazz = clazz;
	}

	public boolean hasProperty(InfoProperty property) {
		return false;
	}

	public boolean hasProperties(InfoProperty[] property) {
		return false;
	}

	public InfoProperty[] getProperties(InfoModel model) {
		return null;
	}
}
