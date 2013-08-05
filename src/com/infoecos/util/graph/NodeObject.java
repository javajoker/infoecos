package com.infoecos.util.graph;

public interface NodeObject {
	void applyLink(Link link) throws Exception;

	void removeLink(Link link);

	NodeObject clone() throws CloneNotSupportedException;
}
