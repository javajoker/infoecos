package com.infoecos.ning.nlp.nl.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsParser {
	public class Token {
		private String key;
		private float weight;

		public Token(String key, float weight) {
			this.key = key;
			this.weight = weight;
		}

		public String getKey() {
			return key;
		}

		public float getWeight() {
			return weight;
		}
	}

	public class SparseItem {
		private int x, y;
		private List<Token> tokens;

		private SparseItem(int x, int y, List<Token> tokens) {
			this.x = x;
			this.y = y;
			this.tokens = tokens;
		}

		public SparseItem(String key, List<Token> tokens) {
			String[] k = key.split("x", 2);
			this.x = Integer.parseInt(k[0]);
			this.y = Integer.parseInt(k[1]);
			this.tokens = tokens;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public List<Token> getTokens() {
			return tokens;
		}
	}

	public class Sparse {
		public class key {
			String key;
			float keyFrequency;
			String[] tokens;
			float[] bondWeight;
		}

		private List<String> tokens;
		private Map<String, List<Token>> sparse;

		public Sparse() {
			tokens = new ArrayList<String>();
			sparse = new HashMap<String, List<Token>>();
		}

		private String getSparseItemKey(int x, int y) {
			return String.format("%dx%d", x, y);
		}

		public SparseItem getItem(int x, int y) {
			String key = getSparseItemKey(x, y);
			if (sparse.containsKey(key))
				return new SparseItem(x, y, sparse.get(key));
			return null;
		}

		public SparseItem[] getItems(int x) {
			List<SparseItem> items = new ArrayList<SparseItem>();
			for (String key : sparse.keySet()) {
				if (x == Integer.parseInt(key.split("x", 2)[0]))
					items.add(new SparseItem(key, sparse.get(key)));
			}
			return (SparseItem[]) items.toArray(new SparseItem[items.size()]);
		}

		public void addKey(int offset, key key) {
			int size = offset + key.tokens.length;
			for (int i = tokens.size(), j = key.tokens.length
					- (size - tokens.size()); i < size; ++i, ++j)
				tokens.add(key.tokens[j]);

			if (key.bondWeight.length == 0) {
				addToken(offset, offset, new Token(key.key, key.keyFrequency));
				return;
			}
			for (int x = offset + 1, i = 0; x < size; ++x, ++i) {
				addToken(offset, x, new Token(key.key, key.keyFrequency
						* key.bondWeight[i]));
			}
		}

		private void addToken(int x, int y, Token token) {
			String k = getSparseItemKey(x, y);
			List<Token> tokens;
			if (!sparse.containsKey(k))
				tokens = new ArrayList<Token>();
			tokens = sparse.get(k);
			tokens.add(token);
			sparse.put(k, tokens);

			if (x != y)
				sparse.put(getSparseItemKey(y, x), tokens);

		}
	}
}
