package com.infoecos.ning.nlp.nl.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.infoecos.ning.nlp.language.Word;
import com.infoecos.ning.nlp.language.ifc.KnowledgeBase;
import com.infoecos.ning.nlp.language.ifc.SemanticContext;
import com.infoecos.ning.nlp.language.ifc.TextContext;
import com.infoecos.ning.nlp.nl.Parser;
import com.infoecos.ning.nlp.nl.ParserRuntime;
import com.infoecos.ning.nlp.nl.ParserTreeNode;
import com.infoecos.ning.nlp.nl.ParserTreeNode.candidate;
import com.infoecos.ning.nlp.nl.parser.StatisticsParser.Sparse.SparseItem;

public class SimpleParser implements Parser {
	protected ParserRuntime runtime = null;
	protected KnowledgeBase knowledgeBase;
	protected context context = null;

	protected Map<Word, List<SemanticContext.concept>> cacheConcepts;

	public SimpleParser(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;

		cacheConcepts = new HashMap<Word, List<SemanticContext.concept>>();

		setRuntime(new SimpleParserRuntime());
	}

	@Override
	public void setContext(context context) {
		if (null == context)
			return;

		this.context = context;
	}

	@Override
	public context getContext() {
		if (null == context) {
			context = new context(new SimpleSemanticContext(),
					new SimpleTextContext(), new SimpleLanguageContext(),
					new SimpleKnowledgeContext());
		}

		return context;
	}

	@Override
	public languageModel parse(String text, int offset) {
		context context = getContext();
		ParserRuntime runtime = getRuntime();
		runtime.resetEnergy();

		languageModel model = new languageModel();

		parse(text, offset, runtime, context, model);

		return model;
	}

	/**
	 * @param text
	 * @param offset
	 * @param runtime
	 * @param context
	 * @param model
	 * @return false if runtime energy exhausted
	 */
	protected boolean parse(String text, int offset, ParserRuntime runtime,
			context context, languageModel model) {
		double score1, score2;
		
//		It is too difficult a job for me to finish in so short a time
		
		List<ParserTreeNode.candidate> candidates = new ArrayList<ParserTreeNode.candidate>();

		for (TextContext.token token : context.getText().getTokenCandidates(
				text, offset)) {
			score1 = token.getProbability();
			Word w = token.getWord().getWord();
			if (!cacheConcepts.containsKey(w))
				cacheConcepts.put(w, context.getSemantic()
						.getSemanticCandidates(w, context.getKnowledge()));
			for (SemanticContext.concept concept : cacheConcepts.get(w)) {
				score2 = concept.getProbability();
				candidates.add(new candidate(score1 * score2, concept
						.getConcept(), token.getWord()));
			}
		}

		Collections.sort(candidates,
				Collections.reverseOrder(new Comparator<candidate>() {
					@Override
					public int compare(candidate o1, candidate o2) {
						// no need to consider 'equal'
						return (o1.getScore() < o2.getScore()) ? -1 : 1;
					}
				}));
		for (candidate c : candidates) {
			double energy = 1 - c.getScore();
			runtime.consumeEnergy(energy);

			if (isStopWord(c, model))
				refactor(model);

			model.getModel().push(new ParserTreeNode(c));

			if (!parse(text, offset + c.getWord().getWordForm().length(),
					runtime, context, model))
				return false;
			runtime.reverseEnergy(energy);
		}

		return true;
	}

	// 的确良
	// 的确良好
	// 的确良好极了

	// 好看的确好看
	// 好看的确实好看
	// 好看的确实在好看
	// 好看的确实在书桌上
	// 好看的却是在书桌上
	private void refactor(languageModel model) {
		// TODO Auto-generated method stub

	}

	public boolean isStopWord(candidate c, languageModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRuntime(ParserRuntime runtime) {
		this.runtime = runtime;
	}

	@Override
	public ParserRuntime getRuntime() {
		return runtime;
	}
}
