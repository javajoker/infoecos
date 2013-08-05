package com.infoecos.ning.nlp.nl;

import java.util.Stack;

import com.infoecos.ning.nlp.language.ifc.KnowledgeBase;
import com.infoecos.ning.nlp.language.ifc.KnowledgeContext;
import com.infoecos.ning.nlp.language.ifc.LanguageContext;
import com.infoecos.ning.nlp.language.ifc.SemanticContext;
import com.infoecos.ning.nlp.language.ifc.TextContext;
import com.infoecos.ning.nlp.nl.parser.SimpleParser;

public interface Parser {
	Parser DEFAULT = new SimpleParser(KnowledgeBase.DEFAULT);

	class context {
		private SemanticContext semantic;
		private TextContext text;
		private LanguageContext language;
		private KnowledgeContext knowledge;

		public context(SemanticContext semantic, TextContext text,
				LanguageContext language, KnowledgeContext knowledge) {
			this.semantic = semantic;
			this.text = text;
			this.language = language;
			this.knowledge = knowledge;
		}

		public SemanticContext getSemantic() {
			return semantic;
		}

		public TextContext getText() {
			return text;
		}

		public LanguageContext getLanguage() {
			return language;
		}

		public KnowledgeContext getKnowledge() {
			return knowledge;
		}
	}

	class languageModel {
		private Stack<ParserTreeNode> model;

		public languageModel() {
			model = new Stack<ParserTreeNode>();
		}

		public Stack<ParserTreeNode> getModel() {
			return model;
		}
	}

	void setRuntime(ParserRuntime runtime);

	ParserRuntime getRuntime();

	void setContext(context context);

	context getContext();

	languageModel parse(String text, int offset);
}
