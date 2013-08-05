package com.infoecos.nlp.nl.languages.en;

import com.infoecos.nlp.nl.languages.Grammar;
import com.infoecos.nlp.nl.parser.WordInfoObject;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.util.graph.Direction;
import com.infoecos.util.graph.LinkType;
import com.infoecos.util.graph.NotLinkableException;
import com.infoecos.util.graph.TreeNode;

public class GrammarEn extends Grammar {

	public GrammarEn() {
		partOfSpeechPriority = new PartOfSpeech[][] {
				new PartOfSpeech[] { PartOfSpeech.Reserved3 },
				new PartOfSpeech[] { PartOfSpeech.Conjunction,
						PartOfSpeech.Punctuation },
				new PartOfSpeech[] { PartOfSpeech.Reserved2,
						PartOfSpeech.Preposition },
				new PartOfSpeech[] { PartOfSpeech.Adverb },
				new PartOfSpeech[] { PartOfSpeech.Adjective },
				new PartOfSpeech[] { PartOfSpeech.Pronoun, PartOfSpeech.Noun },
				new PartOfSpeech[] { PartOfSpeech.Verb }, };

		escapePartOfSpeech = new PartOfSpeech[] { PartOfSpeech.Skip,
				PartOfSpeech.NA, PartOfSpeech.Interjection,
				PartOfSpeech.Punctuation, PartOfSpeech.Reserved, };

		allLinkTypes = new LinkType[] {
				new LinkType(PartOfSpeech.Verb, PartOfSpeech.Noun,
						new PartOfSpeech[] { PartOfSpeech.Sentence },
						Direction.LeftSub),
				new LinkType(PartOfSpeech.Verb, PartOfSpeech.Noun, 2,
						Direction.RightSub),
				// new LinkType(PartOfSpeech.Sentence, PartOfSpeech.Stop,
				// Direction.RightSub),
				// new LinkType(PartOfSpeech.Verb, PartOfSpeech.Stop,
				// PartOfSpeech.Sentence, Direction.RightSub),

				new LinkType(
						PartOfSpeech.Preposition,
						PartOfSpeech.Noun,
						new PartOfSpeech[] { PartOfSpeech.Adjective, PartOfSpeech.Adverb },
						Direction.RightSub),

				// to do ...
				new LinkType(PartOfSpeech.Reserved2, PartOfSpeech.Verb,
						new PartOfSpeech[] { PartOfSpeech.Noun,
								PartOfSpeech.Adjective, PartOfSpeech.Adverb },
						Direction.RightSub),
				// sub clause
				new LinkType(PartOfSpeech.Reserved3, PartOfSpeech.Sentence,
						Direction.RightSub),

				new LinkType(PartOfSpeech.Noun, PartOfSpeech.Adjective,
						Integer.MAX_VALUE, Direction.LeftSub, true),
				new LinkType(PartOfSpeech.Noun, PartOfSpeech.Adjective,
						Integer.MAX_VALUE, Direction.RightSub, false, true),
				new LinkType(PartOfSpeech.Verb, PartOfSpeech.Adverb,
						Integer.MAX_VALUE),
				new LinkType(PartOfSpeech.Adjective, PartOfSpeech.Adverb,
						Integer.MAX_VALUE, Direction.LeftSub, true),

				new LinkType(PartOfSpeech.Noun, PartOfSpeech.Noun,
						Direction.LeftSub, true),
				new LinkType(PartOfSpeech.Verb, PartOfSpeech.Verb,
						Direction.LeftSub, true),
				new LinkType(PartOfSpeech.Adverb, PartOfSpeech.Adverb,
						Direction.LeftSub, true),

				new LinkType(PartOfSpeech.Conjunction, PartOfSpeech.ANY,
						new PartOfSpeech[] { PartOfSpeech.Appositive },
						Direction.RightSub), };

		initialize();
	}

	@Override
	protected LinkType getSpecialLinkType(FormOfWord fow, TreeNode node,
			Direction direction) throws NotLinkableException {
		WordInfoObject subObj = (WordInfoObject) node.getObject(), appositiveObj;
		if (PartOfSpeech.Conjunction.equals(subObj.getFormOfWord()
				.getPartOfSpeech()) && node.getLinks().length > 0) {
			appositiveObj = (WordInfoObject) node.getLinks()[0].getEndNode()
					.getObject();
			PartOfSpeech pos = fow.getPartOfSpeech();
			boolean matched = false;
			if (appositiveObj.getChangedPartOfSpeech().length > 0) {
				for (PartOfSpeech p : appositiveObj.getChangedPartOfSpeech()) {
					if (pos.equals(p)) {
						matched = true;
						break;
					}
				}
			}
			if (!matched)
				matched = pos.equals(appositiveObj.getFormOfWord()
						.getPartOfSpeech());
			if (matched)
				return new LinkType(pos, PartOfSpeech.Appositive,
						Direction.Appositive);
		}
		throw new NotLinkableException();
	}

	@Override
	public Language getLanguage() {
		return Language.EN;
	}
}
