package com.infoecos.info;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.infoecos.nlp.resource.language.Word;

public class InfoWord implements Externalizable {
	private Word word;
	private InfoModel model;

	/**
	 * Word/meaning frequency based on corpus
	 */
	protected float favorRate = 1.0f;

	public InfoWord() {
		this(null);
	}

	public InfoWord(Word word) {
		this(word, null);
	}

	public InfoWord(Word word, InfoModel model) {
		this.word = word;
		this.model = model;
	}

	public Word getWord() {
		return word;
	}

	public InfoModel getModel() {
		return model;
	}

	public void setModel(InfoModel model) {
		this.model = model;
	}

	public float getFavorRate() {
		return favorRate;
	}

	public void setFavorRate(float favorRate) {
		this.favorRate = favorRate;
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

}
