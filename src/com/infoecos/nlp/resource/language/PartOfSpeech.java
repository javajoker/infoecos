package com.infoecos.nlp.resource.language;

public enum PartOfSpeech {
	// word
	/**
	 * Skip tokens. E.g., spaces
	 */
	Skip,
	/**
	 * Not Applicable, or unknown
	 */
	NA,
	/**
	 * Declare any 'part of speech'
	 */
	ANY,
	/**
	 * Appositive
	 */
	Appositive,
	/**
	 * Number, either Cardinal or Ordinal
	 */
	Numberal,
	/**
	 * Common part of speeches
	 */
	Conjunction, Determiner, Adjective, Adverb, Preposition, Abbreviation, Noun, Pronoun, Verb, Interjection,
	/**
	 * Punctuation
	 * 
	 * @Stop (period / question mark / exclamatory mark / ...)
	 * @Pause (comma / ...)
	 * @NA (pipe / colon / ...)
	 */
	Punctuation,
	/**
	 * Reserved words, special part of speech for different languages.
	 * 
	 * E.g. auxiliary and link verb in English
	 */
	Reserved, Reserved2, Reserved3, Reserved4, Reserved5,
	/**
	 * Sentence
	 */
	Sentence, // Interrogative, Exclamatory,
	;

	@Override
	public String toString() {
		if (this.equals(Noun))
			return "n";
		if (this.equals(Pronoun))
			return "pron";
		if (this.equals(Verb))
			return "v";
		if (this.equals(Adjective))
			return "a";
		if (this.equals(Adverb))
			return "ad";
		if (this.equals(Conjunction))
			return "conj";
		if (this.equals(Preposition))
			return "prep";
		if (this.equals(Interjection))
			return "int";
		if (this.equals(Abbreviation))
			return "abbr";

		return super.toString();
	}
}
