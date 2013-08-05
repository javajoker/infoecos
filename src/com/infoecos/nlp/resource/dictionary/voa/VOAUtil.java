package com.infoecos.nlp.resource.dictionary.voa;

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

public class VOAUtil {
	private static String DB_NAME = "infoecos", TBL_NAME = "voa";

	private static List<DictionaryWord> createDictionaryWord(String key,
			String pos, String meaning) {
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();

		PartOfSpeech _pos;
		if (pos.equals("ad."))
			_pos = PartOfSpeech.Adverb;
		else if (pos.equals("prep."))
			_pos = PartOfSpeech.Preposition;
		else if (pos.equals("pro."))
			_pos = PartOfSpeech.Pronoun;
		else if (pos.equals("conj."))
			_pos = PartOfSpeech.Conjunction;
		else if (pos.equals("n."))
			_pos = PartOfSpeech.Noun;
		else if (pos.equals("v."))
			_pos = PartOfSpeech.Verb;
		else
			_pos = PartOfSpeech.NA;

		words.add(new DictionaryWord(Word.getWord(key, _pos), meaning));
		if (pos.equals("ad."))
			words.add(new DictionaryWord(Word.getWord(key, PartOfSpeech.Adjective),
					meaning));

		return words;
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
				words.addAll(createDictionaryWord(rs.getString("key")
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
				words.addAll(createDictionaryWord(key, rs.getString("pos")
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
