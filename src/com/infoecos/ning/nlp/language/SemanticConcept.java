package com.infoecos.ning.nlp.language;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SemanticConcept implements Serializable, Externalizable {

	public class PV {
		private SemanticConcept property, value;

		public SemanticConcept getProperty() {
			return property;
		}

		public void setProperty(SemanticConcept property) {
			this.property = property;
		}

		public SemanticConcept getValue() {
			return value;
		}

		public void setValue(SemanticConcept value) {
			this.value = value;
		}
	}

	protected List<PV> pvs;

	public SemanticConcept(Word word) {
		this(new ArrayList<PV>());
	}

	public SemanticConcept(List<PV> pvs) {
		this.pvs = pvs;
	}

	public List<PV> getPvs() {
		return pvs;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
