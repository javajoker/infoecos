package com.infoecos.ning.nlp.nl.parser;

import java.util.List;

import com.infoecos.ning.nlp.language.ifc.KnowledgeContext;
import com.infoecos.ning.nlp.language.ifc.SemanticContext.concept;
import com.infoecos.ning.nlp.nl.ParserRuntime;

public class SimpleParserRuntime implements ParserRuntime {

	private double energy = 0, runtimeEnergy = 0;

	@Override
	public void setCellEnergy(double energy) {
		this.energy = runtimeEnergy = energy;
	}

	@Override
	public double consumeEnergy(double energy) {
		runtimeEnergy -= energy;
		return runtimeEnergy;
	}

	@Override
	public double reverseEnergy(double energy) {
		runtimeEnergy += energy;
		return runtimeEnergy;
	}

	@Override
	public double getRuntimeEnergy() {
		return runtimeEnergy;
	}

	@Override
	public void resetEnergy() {
		runtimeEnergy = energy;
	}

	@Override
	public void setKnowledgeContext(KnowledgeContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public KnowledgeContext getKnowledgeContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<concept> getSemanticCandidates(String text, int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}
