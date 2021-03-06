package com.infoecos.qa.model;

import java.util.Stack;

import com.infoecos.nlp.resource.language.Word;
import com.infoecos.util.graph.TreeNode;

public class NLModeling {

	/**
	 * 1. unknown word
	 * 
	 * 2. statistics, weight upgrade of graph link
	 * 
	 * 3. when to archive
	 * 
	 * 4. how to 
	 */

	private Word[] getNextToken(String text, int offset) {
		// order by probability (statistics score) descend
		return null;
	}

	private void s() {

	}

	private void processor(String text, int offset) {
		Stack<TreeNode> nodes = new Stack<TreeNode>();
		for (Word w : getNextToken(text, offset)) {

			pushToStack(w);
			if (isUnknownWord(w)) {
				nextRoundProcessor(text, offset + w.getKey().length());
			} else {

				assemblePrefixModel();
				assembleSuffixModel();

				updateLinkScoreToWordNode();
				calculateOptionalLinksWithScore();
			}
		}
	}
}
