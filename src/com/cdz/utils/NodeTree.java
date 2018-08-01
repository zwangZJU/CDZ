package utils;

import java.util.List;

public class NodeTree {
	private Node rootNode = new Node('E');
	private static NodeTree nodeTree = null;

	public static Node getNoteTree(String[] keyword) {
		synchronized (NodeTree.class) {
			if (nodeTree == null) {
				nodeTree = new NodeTree();
				nodeTree.createTree(keyword);
			}
		}
		return nodeTree.rootNode;
	}

	private void createTree(String[] keyword) {
		for (String str : keyword) {
			char[] chars = str.toCharArray();
			if (chars.length > 0)
				insertNode(rootNode, chars, 0);
		}
	}

	private void insertNode(Node node, char[] cs, int index) {
		Node n = findNode(node, cs[index]);
		if (n == null) {
			n = new Node(cs[index]);
			node.nodes.add(n);
		}

		if (index == (cs.length - 1)) {
			n.flag = 1;
		}
		index++;
		if (index < cs.length) {
			insertNode(n, cs, index);
		}
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
