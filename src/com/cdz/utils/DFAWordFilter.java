package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 敏感词过滤
 */
public final class DFAWordFilter {
	private Node rootNode = null;

	public DFAWordFilter(Node node) {
		this.rootNode = node;
	}

	private String content = null;
	/* 存在的敏感词 */
	private Map<String, Integer> putKeyword = new HashMap<String, Integer>();
	private List<String> word = new ArrayList<String>();
	private int i = 0;

	/**
	 * 检索出：屏蔽的敏感词
	 */
	private void searchWord() {
		char[] chars = content.toCharArray();
		Node node = rootNode;
		while (i < chars.length) {
			node = findNode(node, chars[i]);
			if (node == null) {
				node = rootNode;
				i = i - word.size();
				word.clear();
			} else if (node.flag == 1) {
				word.add(String.valueOf(chars[i]));
				StringBuffer sb = new StringBuffer();
				for (String str : word) {
					sb.append(str);
				}
				putKeyword.put(sb.toString(), null);
				i = i - word.size() + 1;
				word.clear();
				node = rootNode;
			} else {
				word.add(String.valueOf(chars[i]));
			}
			i++;
		}
	}

	private boolean searchWord(char[] chars) {
		Node node = rootNode;
		while (i < chars.length) {
			node = findNode(node, chars[i]);
			if (node == null) {
				node = rootNode;
				i = i - word.size();
				word.clear();
			} else if (node.flag == 1) {
				word.add(String.valueOf(chars[i]));
				StringBuffer sb = new StringBuffer();
				for (String str : word) {
					sb.append(str);
				}
				word.clear();
				return true;
			} else {
				word.add(String.valueOf(chars[i]));
			}
			i++;
		}
		return false;
	}

	/**
	 * 过滤敏感词，并对正则表达式语法处理
	 * 
	 * @param str
	 * @param replacement
	 * @return
	 */
	public String filterKeyWord$Script(String str, String replacement) {
		this.content = str;
		searchWord();
		if (replacement == null) {
			replacement = "*";
		}
		for (String key : putKeyword.keySet()) {
			key = RegexScriptUtils.script(key);
			str = str.replaceAll(key, replacement);
		}
		content = null;
		return str;
	}
	
	public String filterKeyWord(String str) {
		this.content = str;
		String keyword="";
		searchWord();
		for (String key : putKeyword.keySet()) {
			key = RegexScriptUtils.script(key);
			//str = str.replaceAll(key, replacement);
			keyword+=key+",";
		}
		content = null;
		return keyword;
	}

	/**
	 * 是否存在敏感词
	 * 
	 * @param content
	 * @param key
	 * @return
	 */
	public boolean isExistsKey(String content, String key) {
		return searchWord(content.toCharArray());
	}

	private Node findNode(Node node, char c) {
		List<Node> nodes = node.nodes;
		Node rn = null;
		for (Node n : nodes) {
			if (n.c == c) {
				rn = n;
				break;
			}
		}
		return rn;
	}
}
