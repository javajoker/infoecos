/**
 * Description:  
 * Copyright:   Copyright (c) 2009 
 * Company:     infoecos.com 
 * 
 * Created on 2009-7-11
 * @author  dch
 * @version 1.0
 */
package com.infoecos.nlp.resource.language.en;

import java.util.ArrayList;
import java.util.List;

import com.infoecos.nlp.resource.language.FormOfWord;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;

public class VerbMorphEn extends MorphEn {
	private static List<FormOfWord> IrregularVs = new ArrayList<FormOfWord>();

	public static final String[][] CommonIrregularVs = new String[][] {
			{ "abide", "abode,abided", "abode,abided" },
			{ "am", "was", "been" }, { "are", "were", "been" },
			{ "can", "could", "could" }, { "arise", "arose", "arisen" },
			{ "awake", "awoke", "awaked,awoken" },
			{ "be", "was,were", "been" }, { "bear", "bore", "borne" },
			{ "beat", "beat", "beaten" }, { "become", "became", "become" },
			{ "befall", "befell", "befallen" },
			{ "beget", "begot", "begotten" }, { "begin", "began", "begun" },
			{ "behold", "beheld", "beheld" }, { "bend", "bent", "bent" },
			{ "bereave", "bereaved,bereft", "bereaved,bereft" },
			{ "beseech", "besought", "besought" },
			{ "beset", "beset", "beset" },
			{ "bet", "bet,betted", "bet,betted" },
			{ "betake", "betook", "betaken" },
			{ "bethink", "bethought", "bethought" },
			{ "bid", "bade,bid", "bidden,bid" }, { "bind", "bound", "bound" },
			{ "bite", "bit", "bitten,bit" }, { "bleed", "bled", "bled" },
			{ "blend", "blended,blent", "blended,blent" },
			{ "bless", "blessed,blest", "blessed,blest" },
			{ "blow", "blew", "blown" }, { "break", "broke", "broken" },
			{ "breed", "bred", "bred" }, { "bring", "brought", "brought" },
			{ "broadcast", "broadcast,broadcasted", "broadcast,broadcasted" },
			{ "build", "built", "built" },
			{ "burn", "burnt,burned", "burnt,burned" },
			{ "burst", "burst", "burst" }, { "buy", "bought", "bought" },
			{ "cast", "cast", "cast" }, { "catch", "caught", "caught" },
			{ "chide", "chided,chid", "chided,chidden" },
			{ "choose", "chose", "chosen" },
			{ "cleave", "clove,cleft", "cloven,cleft" },
			{ "cling", "clung", "clung" },
			{ "clothe", "clothed,clad", "clothed,clad" },
			{ "come", "came", "come" }, { "cost", "cost", "cost" },
			{ "creep", "crept", "crept" }, { "crow", "crowed,crew", "crowed" },
			{ "cut", "cut", "cut" }, { "dare", "dared,durst", "dared" },
			{ "deal", "dealt", "dealt" }, { "dig", "dug", "dug" },
			{ "dive", "dived,dove", "dived" }, { "do", "did", "done" },
			{ "draw", "drew", "drawn" },
			{ "dream", "dreamt,dreamed", "dreamt,dreamed" },
			{ "drink", "drank", "drunk" }, { "drive", "drove", "driven" },
			{ "dwell", "dwelt", "dwelt" }, { "eat", "ate", "eaten" },
			{ "fall", "fell", "fallen" }, { "feed", "fed", "fed" },
			{ "feel", "felt", "felt" }, { "fight", "fought", "fought" },
			{ "find", "found", "found" }, { "flee", "fled", "fled" },
			{ "fling", "flung", "flung" }, { "fly", "flew", "flown" },
			{ "forbear", "forbore", "forborne" },
			{ "forbid", "forbade,forbad", "forbidden" },
			{ "forecast", "forecast,forecasted", "forecast,forecasted" },
			{ "foreknow", "foreknew", "foreknown" },
			{ "foresee", "foresaw", "foreseen" },
			{ "foretell", "foretold", "foretold" },
			{ "forget", "forgot", "forgotten" },
			{ "forgive", "forgave", "forgiven" },
			{ "forsake", "forsook", "forsaken" },
			{ "forswear", "forswore", "forsworn" },
			{ "freeze", "froze", "frozen" },
			{ "gainsay", "gainsaid", "gainsaid" },
			{ "get", "got", "got,gotten" },
			{ "gild", "gilded,gilt", "gilded" },
			{ "gird", "girded,girt", "girded,girt" },
			{ "give", "gave", "given" }, { "go", "went", "gone" },
			{ "grave", "graved", "graven,graved" },
			{ "grind", "ground", "ground" }, { "grow", "grew", "grown" },
			{ "hamstring", "hamstringed,hamstrung", "hamstringed,hamstrung" },
			{ "hang", "hung,hanged", "hung,hanged" }, { "have", "had", "had" },
			{ "hear", "heard", "heard" },
			{ "heave", "heaved,hove", "heaved,hove" },
			{ "hew", "hewed", "hewed,hewn" }, { "hide", "hid", "hidden" },
			{ "hit", "hit", "hit" }, { "hold", "held", "held" },
			{ "hurt", "hurt", "hurt" }, { "inlay", "inlaid", "inlaid" },
			{ "is", "was", "been" }, { "keep", "kept", "kept" },
			{ "kneel", "knelt", "knelt" },
			{ "knit", "knitted,knit", "knitted,knit" },
			{ "know", "knew", "known" }, { "lade", "laded", "laden" },
			{ "lay", "laid", "laid" }, { "lead", "led", "led" },
			{ "lean", "leant,leaned", "leant,leaned" },
			{ "leap", "leapt,leaped", "leapt,leaped" },
			{ "learn", "learnt,learned", "learnt,learned" },
			{ "leave", "left", "left" }, { "lend", "lent", "lent" },
			{ "let", "let", "let" }, { "lie", "lay", "lain" },
			{ "light", "lit,lighted", "lit,lighted" },
			{ "lose", "lost", "lost" }, { "make", "made", "made" },
			{ "mean", "meant", "meant" }, { "meet", "met", "met" },
			{ "melt", "melted", "melted,molten" },
			{ "miscast", "miscast", "miscast" },
			{ "misdeal", "misdealt", "misdealt" },
			{ "misgive", "misgave", "misgiven" },
			{ "mislay", "mislaid", "mislaid" },
			{ "mislead", "misled", "misled" },
			{ "misspell", "misspelt", "misspelt" },
			{ "misspend", "misspent", "misspent" },
			{ "mistake", "mistook", "mistaken" },
			{ "misunderstand", "misunderstood", "misunderstood" },
			{ "mow", "mowed", "mown,mowed" }, { "outbid", "outbid", "outbid" },
			{ "outdo", "outdid", "outdone" },
			{ "outgo", "outwent", "outgone" },
			{ "outgrow", "outgrew", "outgrown" },
			{ "outride", "outrode", "outridden" },
			{ "outrun", "outran", "outrun" },
			{ "outshine", "outshone", "outshone" },
			{ "overbear", "overbore", "overborne" },
			{ "overcast", "overcast", "overcast" },
			{ "overcome", "overcame", "overcome" },
			{ "overdo", "overdid", "overdone" },
			{ "overhang", "overhung", "overhung" },
			{ "overhear", "overheard", "overheard" },
			{ "overlay", "overlaid", "overlaid" },
			{ "overleap", "overleapt,overleaped", "overleapt,overleaped" },
			{ "overlie", "overlay", "overlain" },
			{ "override", "overrode", "overridden" },
			{ "overrun", "overran", "overrun" },
			{ "oversee", "oversaw", "overseen" },
			{ "overshoot", "overshot", "overshot" },
			{ "oversleep", "overslept", "overslept" },
			{ "overtake", "overtook", "overtaken" },
			{ "overthrow", "overthrew", "overthrown" },
			{ "partake", "partook", "partaken" }, { "pay", "paid", "paid" },
			{ "prove", "proved", "proved,proven" }, { "put", "put", "put" },
			{ "quit", "quitted,quit", "quitted,quit" },
			{ "read", "read", "read" }, { "rebind", "rebound", "rebound" },
			{ "rebuild", "rebuilt", "rebuilt" },
			{ "recast", "recast", "recast" }, { "redo", "redid", "redone" },
			{ "relay", "relaid", "relaid" }, { "remake", "remade", "remade" },
			{ "rend", "rent", "rent" }, { "repay", "repaid", "repaid" },
			{ "rerun", "reran", "rerun" }, { "reset", "reset", "reset" },
			{ "retell", "retold", "retold" },
			{ "rewrite", "rewrote", "rewritten" },
			{ "rid", "rid,ridded", "rid,ridded" },
			{ "ride", "rode", "ridden" }, { "ring", "rang", "rung" },
			{ "rise", "rose", "risen" }, { "rive", "rived", "riven,rived" },
			{ "run", "ran", "run" }, { "saw", "sawed", "sawn,sawed" },
			{ "say", "said", "said" }, { "see", "saw", "seen" },
			{ "seek", "sought", "sought" }, { "sell", "sold", "sold" },
			{ "send", "sent", "sent" }, { "set", "set", "set" },
			{ "sew", "sewed", "sewn,sewed" }, { "shake", "shook", "shaken" },
			{ "shave", "shaved", "shaved,shaven" },
			{ "shear", "sheared", "sheared,shorn" },
			{ "shed", "shed", "shed" }, { "shine", "shone", "shone" },
			{ "shoe", "shod", "shod" }, { "shoot", "shot", "shot" },
			{ "show", "showed", "shown,showed" },
			{ "shrink", "shrank,shrunk", "shrunk,shrunken" },
			{ "shrive", "shrove,shrived", "shriven,shrived" },
			{ "shut", "shut", "shut" }, { "sing", "sang", "sung" },
			{ "sink", "sank", "sunk,sunken" }, { "sit", "sat", "sat" },
			{ "slay", "slew", "slain" }, { "sleep", "slept", "slept" },
			{ "slide", "slid", "slid" }, { "sling", "slung", "slung" },
			{ "slink", "slunk", "slunk" }, { "slit", "slit", "slit" },
			{ "smell", "smelt,smelled", "smelt,smelled" },
			{ "smite", "smote", "smitten" }, { "sow", "sowed", "sown,sowed" },
			{ "speak", "spoke", "spoken" },
			{ "speed", "sped,speeded", "sped,speeded" },
			{ "spell", "spelt,spelled", "spelt,spelled" },
			{ "spend", "spent", "spent" },
			{ "spill", "spilt,spilled", "spilt,spilled" },
			{ "spin", "spun,span", "spun" }, { "spit", "spat", "spat" },
			{ "split", "split", "split" },
			{ "spoil", "spoilt,spoiled", "spoilt,spoiled" },
			{ "spread", "spread", "spread" }, { "spring", "sprang", "sprung" },
			{ "stand", "stood", "stood" },
			{ "stave", "staved,stove", "staved,stove" },
			{ "steal", "stole", "stolen" }, { "stick", "stuck", "stuck" },
			{ "sting", "stung", "stung" }, { "stink", "stank,stunk", "stunk" },
			{ "strew", "strewed", "strewn,strewed" },
			{ "stride", "strode", "stridden" },
			{ "strike", "struck", "struck,stricken" },
			{ "string", "strung", "strung" },
			{ "strive", "strove", "striven" }, { "swear", "swore", "sworn" },
			{ "sweep", "swept", "swept" },
			{ "swell", "swelled", "swollen,swelled" },
			{ "swim", "swam", "swum" }, { "swing", "swung", "swung" },
			{ "take", "took", "taken" }, { "teach", "taught", "taught" },
			{ "tear", "tore", "torn" }, { "tell", "told", "told" },
			{ "think", "thought", "thought" },
			{ "thrive", "throve,thrived", "thriven,thrived" },
			{ "throw", "threw", "thrown" }, { "thrust", "thrust", "thrust" },
			{ "tread", "trod", "trodden,trod" },
			{ "unbend", "unbent", "unbent" },
			{ "unbind", "unbound", "unbound" },
			{ "underbid", "underbid", "underbid" },
			{ "undergo", "underwent", "undergone" },
			{ "understand", "understood", "understood" },
			{ "undertake", "undertook", "undertaken" },
			{ "undo", "undid", "undone" }, { "upset", "upset", "upset" },
			{ "wake", "woke,waked", "woken,waked" },
			{ "waylay", "waylaid", "waylaid" }, { "wear", "wore", "worn" },
			{ "weave", "wove", "woven" }, { "weep", "wept", "wept" },
			{ "win", "won", "won" }, { "wind", "wound", "wound" },
			{ "withdraw", "withdrew", "withdrawn" },
			{ "withhold", "withheld", "withheld" },
			{ "withstand", "withstood", "withstood" },
			{ "work", "worked,wrought", "worked,wrought" },
			{ "wring", "wrung", "wrung" }, { "write", "wrote", "written" }, };

	public static void registerIrregularV(String v, String past,
			String pastParticiple) {
		Word w = Word.getWord(v, PartOfSpeech.Verb);
		for (String p : past.split(",")) {
			IrregularVs.add(new FormOfWordEn(w, p, InflectionEn.Past));
		}
		for (String pp : pastParticiple.split(",")) {
			IrregularVs
					.add(new FormOfWordEn(w, pp, InflectionEn.PastParticiple));
		}
	}

	static {
		for (String[] civ : CommonIrregularVs) {
			registerIrregularV(civ[0], civ[1], civ[2]);
		}
	}

	@Override
	public FormOfWord[] getFormOfWords(String key) {
		Word w = Word.getWord(key, PartOfSpeech.Verb);

		List<FormOfWord> fows = new ArrayList<FormOfWord>();
		String[] keys = MorphEn.getPossibleMorphosis(key, new String[][] {
				{ "y", "ies" }, { "f", "ves" }, { "fe", "ves" },
				{ "s", "ses" }, { "o", "oes" }, { "x", "xes" },
				{ "ch", "ches" }, }, "s");
		for (String s : keys) {
			fows.add(new FormOfWordEn(w, s, InflectionEn.ThirdPersonSingular));
		}
		keys = MorphEn.getPossibleMorphosis(key, new String[][] {
				{ "y", "ied" }, { "e", "ed" }, }, "ed");
		for (String s : keys) {
			fows.add(new FormOfWordEn(w, s, InflectionEn.Past
					| InflectionEn.PastParticiple));
		}
		keys = MorphEn.getPossibleMorphosis(key, new String[][] {
				{ "ie", "ying" }, { "c", "cking" }, { "e", "ing" }, }, "ing");
		for (String s : keys) {
			fows.add(new FormOfWordEn(w, s, InflectionEn.Gerund
					| InflectionEn.PresentParticiple));
		}

		// // special case, possessive
		// keys = MorphEN.getPossibleMorphosis(key, new String[][] {
		// { "ie", "ying's" }, { "c", "cking's" }, { "e", "ing's" }, },
		// "ing's");
		// for (String s : keys) {
		// fows.add(new FormOfWordEn(w, s, Inflection.Gerund
		// | Inflection.Possessive));
		// }
//		keys = MorphEN.getPossibleMorphosis(key,
//				new String[][] { { "e", "ion" }, }, "ion");
//		for (String s : keys) {
//			fows.add(new FormOfWordEn(w, s, InflectionEn.Gerund));
//		}

		return (FormOfWord[]) fows.toArray(new FormOfWord[fows.size()]);
	}

	@Override
	public FormOfWord[] getReservedWords() {
		return (FormOfWord[]) IrregularVs.toArray(new FormOfWord[IrregularVs
				.size()]);
	}

	@Override
	public PartOfSpeech getPartOfSpeech() {
		return PartOfSpeech.Verb;
	}
}
