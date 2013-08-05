package com.infoecos.ning.nlp.nl;

import java.util.List;

import com.infoecos.ning.nlp.language.ifc.KnowledgeContext;
import com.infoecos.ning.nlp.language.ifc.SemanticContext.concept;

public interface ParserRuntime {
	void setCellEnergy(double energy);

	double consumeEnergy(double energy);

	double reverseEnergy(double energy);

	double getRuntimeEnergy();

	void resetEnergy();

	void setKnowledgeContext(KnowledgeContext context);

	KnowledgeContext getKnowledgeContext();

	List<concept> getSemanticCandidates(String text, int offset);
}
