package com.infoecos.ning.nlp.learner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.infoecos.ning.nlp.resource.ResourceLocator;

public class GetStopWord {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						ResourceLocator
								.getSource("/resource/corpus/cn/199801%28人民日报语料%29.txt"),
						"gbk"));
		Set<Character> first = new HashSet<Character>(), last = new HashSet<Character>();
		String line;
		while ((line = br.readLine()) != null) {
			for (String t : line.split("\\s+")) {
				if (t.isEmpty())
					continue;
				t = t.substring(0, t.indexOf('/'));
				if (t.length() == 1)
					continue;
				first.add(t.charAt(0));
				last.add(t.charAt(t.length() - 1));
			}
		}

		br.close();

		System.out.println(first.size());
		System.out.println(last.size());

		br = new BufferedReader(
				new InputStreamReader(
						ResourceLocator
								.getSource("/resource/corpus/cn/199801%28人民日报语料%29.txt"),
						"gbk"));
		while ((line = br.readLine()) != null) {
			for (String t : line.split("\\s+")) {
				if (t.isEmpty())
					continue;
				t = t.substring(0, t.indexOf('/'));
				if (t.length() == 1)
					continue;
				for (int i = 1; i < t.length() - 1; ++i) {
					first.remove(t.charAt(i));
					last.remove(t.charAt(i));
				}
			}
		}

		br.close();

		System.out.println(first.size());
		System.out.println(last.size());

		System.out.println(first);
	}
}
