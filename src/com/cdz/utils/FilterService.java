package utils;


public class FilterService {
	private Node node = null;

	public FilterService(String[] keywords) {
		node = NodeTree.getNoteTree(keywords);
	}

	public String filter(String str, String replacement) {
		return new DFAWordFilter(node).filterKeyWord$Script(str, replacement);
	}

	public String filterKeyWord(String str) {
		return new DFAWordFilter(node).filterKeyWord(str);
	}
	public String filter(String str) {
		return filter(str, "*");
	}
}
