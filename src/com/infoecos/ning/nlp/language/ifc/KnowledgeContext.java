package com.infoecos.ning.nlp.language.ifc;

public interface KnowledgeContext {
	void setRuntime(LiveRuntime runtime);

	LiveRuntime getRuntime();

	KnowledgeBase getKnowledgeBase();
}
