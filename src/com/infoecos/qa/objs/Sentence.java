package com.infoecos.qa.objs;

public class Sentence {
	private Object speaker, subject, object;

	public Sentence() {
		// TODO Auto-generated constructor stub
	}

	public Sentence(Word word) {
		this();
		try {
			pushToSentence(word);
		} catch (NewSentenceException e) {
			e.printStackTrace();
		}
	}

	public void pushToSentence(Word word) throws NewSentenceException {
		// TODO Auto-generated method stub

	}

	public void rollback() {
		// TODO Auto-generated method stub

	}
}
