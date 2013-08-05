package com.infoecos.nlp.resource.dictionary.wiktionary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.infoecos.nlp.resource.dictionary.DictionaryWord;
import com.infoecos.nlp.resource.language.PartOfSpeech;
import com.infoecos.nlp.resource.language.Word;
import com.infoecos.util.db.DBUtil;

public class WiktionaryUtil {
	private static String DB_NAME = "infoecos", TBL_NAME = "wiktionary";

	public static DictionaryWord createDictionaryWord(String key, String pos,
			String meaning) {

		PartOfSpeech _pos;
		if (pos.equals("adv") || pos.equals("adverb"))
			_pos = PartOfSpeech.Adverb;
		else if (pos.equals("adj") || pos.equals("adjective"))
			_pos = PartOfSpeech.Adjective;
		else if (pos.equals("prep") || pos.equals("preposition"))
			_pos = PartOfSpeech.Preposition;
		else if (pos.equals("pron") || pos.equals("pronoun"))
			_pos = PartOfSpeech.Pronoun;
		else if (pos.equals("con") || pos.equals("conj"))
			_pos = PartOfSpeech.Conjunction;
		else if (pos.equals("noun"))
			_pos = PartOfSpeech.Noun;
		else if (pos.equals("verb"))
			_pos = PartOfSpeech.Verb;
		// else if (pos.equals("abbr") || pos.equals("abbreviation"))
		// _pos = PartOfSpeech.Abbreviation;
		// else if (pos.equals("det"))
		// _pos = PartOfSpeech.NA;
		else
			_pos = PartOfSpeech.NA;

		return new DictionaryWord(Word.getWord(key, _pos), meaning);
	}

	public static DictionaryWord[] getAllWords() {
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();
		Connection conn = DBUtil.getConnection(DB_NAME);
		if (conn == null)
			throw new NullPointerException();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format(
					"SELECT `id`, `key`, `pos`, `mean` FROM %s", TBL_NAME));
			while (rs.next()) {
				words.add(createDictionaryWord(rs.getString("key")
						.toLowerCase(), rs.getString("pos").toLowerCase(), rs
						.getString("mean").toLowerCase()));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.releaseConnection(conn);
		}

		return (DictionaryWord[]) words
				.toArray(new DictionaryWord[words.size()]);
	}

	public static DictionaryWord[] getWords(String key) {
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();
		Connection conn = DBUtil.getConnection(DB_NAME);
		if (conn == null)
			throw new NullPointerException();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(String.format(
					"SELECT `id`, `pos`, `mean` FROM %s WHERE `key`=?",
					TBL_NAME));
			stmt.setString(1, key);
			rs = stmt.executeQuery();
			while (rs.next()) {
				words.add(createDictionaryWord(key, rs.getString("pos")
						.toLowerCase(), rs.getString("mean").toLowerCase()));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.releaseConnection(conn);
		}

		return (DictionaryWord[]) words
				.toArray(new DictionaryWord[words.size()]);
	}
}
