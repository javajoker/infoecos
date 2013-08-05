package com.infoecos.info;

import java.util.ArrayList;
import java.util.List;

import com.infoecos.util.graph.Link;
import com.infoecos.util.graph.Node;

public class InfoProperty extends Node {
	private long id;
	public List<InfoClassHelper> classes = new ArrayList<InfoClassHelper>();
	public InfoAction canDo;
	public boolean isPassive;

	public InfoProperty(long id) {
		this.id = id;
	}

	public InfoClassHelper[] getClasses() {
		List<InfoClassHelper> clazzes = new ArrayList<InfoClassHelper>();
		clazzes.addAll(classes);
		for (Link link : getParentLinks()) {
			for (InfoClassHelper clazz : ((InfoProperty) link.getStartNode())
					.getClasses())
				clazzes.add(clazz);
		}
		return (InfoClassHelper[]) clazzes.toArray(new InfoClassHelper[clazzes
				.size()]);
	}

	public long getId() {
		return id;
	}

	public boolean hasClass(InfoClassHelper clazz) {
		return false;
	}

	public Restriction[] getRestrictions() {
		return null;
	}
}
