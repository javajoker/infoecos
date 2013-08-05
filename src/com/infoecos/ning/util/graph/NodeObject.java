package com.infoecos.ning.util.graph;

public interface NodeObject {
	void applyLink(Link link) throws Exception;

	void removeLink(Link link);

	NodeObject clone() throws CloneNotSupportedException;
}
