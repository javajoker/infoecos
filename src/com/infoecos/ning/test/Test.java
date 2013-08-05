package com.infoecos.ning.test;

import com.infoecos.ning.nlp.language.ifc.KnowledgeBase;
import com.infoecos.ning.nlp.nl.Parser;
import com.infoecos.ning.nlp.nl.parser.SimpleParser;

public class Test {
	public static void main(String[] args) {
		String text = "this is just a test";
		Parser parser = new SimpleParser(KnowledgeBase.DEFAULT);
		parser.parse(text, 0);
	}
}
