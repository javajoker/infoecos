package com.infoecos.nlp.resource.language.en;

import java.util.HashMap;

import com.infoecos.nlp.resource.corpus.CorpusAdapter;
import com.infoecos.nlp.resource.corpus.CorpusManager;
import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.Language;
import com.infoecos.nlp.resource.language.Morph;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;
import com.infoecos.nlp.resource.language.WordLib;

public class WordLibEn extends WordLib {
	@Override
	public Language getLanguage() {
		return Language.EN;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<Morph>[] getMorphClasses() {
		return new Class[] { NounMorphEn.class, VerbMorphEn.class,
				AdjMorphEn.class, AdvMorphEn.class, AbbrMorphEn.class,
				NumberMorphEn.class,
		// AuxMorphEN.class
		};
	}

	@Override
	protected FormOfWord getPrototype(Word word) {
		return new FormOfWordEn(word);
	}

	@Override
	protected void initializeWords() throws Exception {
		if (null != words)
			return;

		words = new HashMap<String, Word>();
		for (CorpusAdapter c : CorpusManager.getInstance(getLanguage())) {
			for (Word w : c.getWords())
				addWord(w);
		}

		addWord(new String[] { "pardon", }, PartOfSpeech.Interjection);

		addWord(new String[] { "germ", "sorrow", "percent", "unhappiness",
				"traveller", "preposition", "observance", "autumn", "sickness",
				"nitrogen", "daytime", "excellence", "verb", "creator",
				"enjoyment", "god", "farm", "lawmaking", "manufacture",
				"burial", "reptile", "jewel", "navy", "probability",
				"waterway", "emerald", "sunrise", "sadness", "disbelief",
				"parliament", }, PartOfSpeech.Noun);
		addWord(new String[] { "oneself", }, PartOfSpeech.Pronoun);

		addWord(new String[] { "unload", "postpone", "repay", "obey", "spy",
				"farm", "nominate", }, PartOfSpeech.Verb);

		addWord(new String[] { "yearly", }, PartOfSpeech.Adverb);
		addWord(new String[] { "unplanned", "untrue", "probable", "unborn",
				"unseen", "unjust", "unclear", "colorless", },
				PartOfSpeech.Adjective);

		// for (DictionaryWord w : VOAUtil.getAllWords())
		// addWord(w.getWord());
		// for (DictionaryWord w : WiktionaryUtil.getAllWords())
		// addWord(w.getWord());
		//
		// // to do ...
		// addWord(new String[] { "to" }, PartOfSpeech.Reserved2);
		// // it is ... that ...
		// addWord(new String[] { "that" }, PartOfSpeech.Reserved3);
		// // clauses guide word
		// addWord(new String[] { "who", "whom", "whose", "which", "that", "as",
		// "when", " where", "why" }, PartOfSpeech.Reserved3);
		//
		// addWord(new String[] { "farm", "nose", "eye", "finger", "sight",
		// "prep", "newspaper", "mouth", "verb", "strength", "finger",
		// "stomach", "sorrow", "wing", "statement", "round", "action",
		// "promise", "other", "house", "germ", "newspaper", "relate",
		// "pleasure", "preposition", "ear", "eye", "leg", "ear", "leg",
		// "hand", "repay", "complete", "second", "weight", "hand",
		// "prepare",
		//
		// "unload", "legislative", "weaken", "cannot", "disagree",
		// "regain", "dishonor" }, PartOfSpeech.Verb);
		//
		// addWord(new String[] { "payment", "height", "description", "sunrise",
		// "oxygen", "decision", "writer", "entertainment", "writing",
		// "virus", "tissue", "truth", "teacher", "atom", "cell",
		// "celebration", "reptile", "injury", "permission",
		// "representation", "addition", "death", "shortage", "one",
		// "sale", "measurement", "similarity", "organization",
		// "happiness", "tobacco", "republic", "driver", "singer",
		// "examination", "length", "worker", "traveller", "feeling",
		// "ruler", "safety", "communication", "choice", "friendship",
		// "economics", "creator", "activity", "decision", "probability",
		// "product", "difficulty", "argument", "owner", "investment",
		// "agreement", "ability", "element", "belief", "explanation",
		// "possibility", "human", "leader", "approval", "pro",
		// "competition", "division", "existence", "cent", "excellence",
		// "solution", "cigarette", "enjoyment", "organization",
		// "creation", "destruction", "nitrogen", "growth", "activity",
		// "thought", "punishment", "marriage", "expression", "loss",
		// "winner", "cell", "ownership", "ability", "payment",
		// "connection", "success", "possession", "speaker", "bacteria",
		// "consideration",
		//
		// "receiver", "executive", "coolness", "sadness", "lawlessness",
		// "kindness", "unhappiness", "willingness", "importance",
		// "observance", "assistance", "production", "recognition",
		// "restriction", "reduction", "preparation", "assistant",
		// "settlement", "improvement", "nutrient", "responsibility",
		// "prisoner", "voter", "daytime", "disbelief", "waterway",
		// "warmth", "proposal", "burial", "mixture", "guilt", "sunlight",
		// "lawmaking" }, PartOfSpeech.Noun);
		//
		// addWord(new String[] { "the", "medical", "active", "underwater",
		// "distant", "unborn", "unknown", "gent", "violent", "daily",
		// "scientific", "unusual", "representative", "birth", "probable",
		// "yearly", "violent", "emerald", "central", "prime", "unseen",
		// "musical", "civil", "angry", "national", "industrial", "else",
		// "electronic", "religious", "unofficial", "proof", "cruel",
		// "dangerous", "political", "swear", "friendly", "pleasant",
		// "powerful", "diamond", "nearby", "arm", "armed", "valuable",
		//
		// "executive", "harmless", "colorless", "truthful", "peaceful",
		// "helpful", "harmful", "useful", "unplanned", "uneven",
		// "unjust", "economic", "diplomatic", "unpleasant", "untrue",
		// "unclear", "unable" }, PartOfSpeech.Adjective);
		//
		// addWord(new String[] { "possibly", "none", "apart", "post",
		// "somewhere",
		//
		// "badly", "sometimes" }, PartOfSpeech.Adverb);
		//
		// addWord(new String[] { "into", "onto", "inside", "outside", "within"
		// },
		// PartOfSpeech.Preposition);
		//
		// addWord(new String[] { "anything", "everything", "everyone",
		// "something", "someone", "somebody", "those", "its", "oneself",
		// "her", "itself", "him", "his", "self", "their" },
		// PartOfSpeech.Pronoun);
	}
}
