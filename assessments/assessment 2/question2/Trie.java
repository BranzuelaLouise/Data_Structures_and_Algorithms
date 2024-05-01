package question2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class Trie {

	public TrieNode root;

	public Trie() {
		this.root = new TrieNode();
	}

	public boolean add(String element) {
		TrieNode current = root;
		for (char c : element.toCharArray()) {
			TrieNode node = current.getChild(c);
			if (node == null) {
				node = new TrieNode();
				current.addChild(c, node);
			}
			current = node;
		}
		current.setWord(element);
		return true;
	}

	public boolean remove(String element) {
		if (contains(element)) {
			List<TrieNode> traversed = new ArrayList<TrieNode>();
			TrieNode current = root;
			for (char c : element.toCharArray()) {
				TrieNode node = current.getChild(c);
				if (node != null) {
					traversed.add(current);
					current = node;
				}
			}
			if (element.equals(current.getWord())) {
				current.setWord(null);
			}
			for (int x = traversed.size() - 1; x > 0; x--) {
				TrieNode previousNode = traversed.get(x);
				Character c = element.charAt(x);
				TrieNode node = previousNode.getChild(c);
				if (node.getChildrenSize() == 0 && node.getWord() == null) {
					previousNode.removeChild(c);
				}
			}

			return true;
		}

		return false;
	}

	public boolean contains(String element) {
		TrieNode current = root;
		boolean found = true;
		for (char c : element.toCharArray()) {
			TrieNode node = current.getChild(c);
			if (node != null) {
				current = node;
			} else {
				found = false;
			}
		}
		return found && element.equals(current.getWord());
	}

	public boolean removeAll(String prefix) {
		if (startsWith(prefix)) {
			List<TrieNode> traversed = new ArrayList<TrieNode>();
			TrieNode current = root;
			for (char c : prefix.toCharArray()) {
				TrieNode node = current.getChild(c);
				if (node != null) {
					traversed.add(current);
					current = node;
				}
			}

			int lastIndex = prefix.toCharArray().length - 1;
			TrieNode lastNode = traversed.get(lastIndex);
			Character lastChar = prefix.charAt(lastIndex);
			TrieNode removeNode = lastNode.getChild(lastChar);
			lastNode.removeChild(lastChar);
			removeNode = null;

			if (traversed.size() > 1) {
				for (int x = traversed.size() - 2; x > 0; x--) {
					TrieNode previousNode = traversed.get(x);
					Character c = prefix.charAt(x);
					TrieNode node = previousNode.getChild(c);
					System.out.println(c);
					if (node.getChildrenSize() == 0 && node.getWord() == null) {
						previousNode.removeChild(c);
					}
				}
			}

			return true;
		}

		return false;
	}

	public boolean startsWith(String prefix) {
		TrieNode current = root;
		boolean found = true;
		for (char c : prefix.toCharArray()) {
			TrieNode node = current.getChild(c);
			if (node != null) {
				current = node;
			} else {
				found = false;
			}
		}
		return found;
	}

	private Set<String> getNodeString(TrieNode node) {
		Set<String> words = new HashSet<String>();
		HashMap<Character, TrieNode> children = node.getChildren();
		StringBuilder builder = new StringBuilder();
		for (Entry<Character, TrieNode> entry : children.entrySet()) {
			if (entry.getValue().getWord() != null) {
				words.add(entry.getValue().getWord());
			}

			if (entry.getValue().getChildrenSize() > 0) {
				words.addAll(getNodeString(entry.getValue()));
			}
		}
		return words;
	}

	public Set<String> suggestions(String prefix) {
		Set<String> suggestions = new HashSet<String>();
		if (startsWith(prefix)) {
			TrieNode current = root;
			for (char c : prefix.toCharArray()) {
				TrieNode node = current.getChild(c);
				if (node != null) {
					current = node;
				}
			}

			if (current.getWord() != null) {
				suggestions.add(current.getWord());
			}

			suggestions.addAll(getNodeString(current));

		}
		return suggestions;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder(50);
		root.print(buffer, "Root", "", "");
		return buffer.toString();
	}

	public class TrieNode {
		private String word;
		private HashMap<Character, TrieNode> children;

		public TrieNode() {
			this.children = new HashMap<Character, TrieNode>();
		}

		public void addChild(Character key, TrieNode child) {
			children.put(key, child);
		}

		public void removeChild(Character key) {
			if (children.containsKey(key)) {
				children.remove(key);
			}
		}

		public TrieNode getChild(Character key) {
			return children.get(key);
		}

		public void setWord(String word) {
			this.word = word;
		}

		public String getWord() {
			return word;
		}

		public int getChildrenSize() {
			return children.size();
		}

		public HashMap<Character, TrieNode> getChildren() {
			return children;
		}

		public void removeAllChildren() {
			children = new HashMap<Character, TrieNode>();
		}

		public void print(StringBuilder buffer, String l, String prefix, String childrenPrefix) {
			buffer.append(prefix);
			buffer.append(l);
			buffer.append('\n');
			for (Entry<Character, TrieNode> entry : children.entrySet()) {
				String w = "";
				if (entry.getValue().getWord() != null)
					w = " (" + entry.getValue().getWord() + ") ";
				if (entry.getValue().getChildrenSize() > 0) {
					entry.getValue().print(buffer, entry.getKey().toString() + w, childrenPrefix + "├── ",
							childrenPrefix + "│   ");
				} else {
					entry.getValue().print(buffer, entry.getKey().toString() + w, childrenPrefix + "└── ",
							childrenPrefix + " ");
				}
			}
		}
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.add("there");
		trie.add("the");
		trie.add("thereis");
		trie.add("tea");
		trie.add("then");

		System.out.println("Before remove\n" + trie.toString());
		System.out.println("------------------");

		System.out.println(trie.suggestions("there"));
		System.out.println("------------------");

		trie.removeAll("there");
		System.out.println("After removeAll\n" + trie.toString());
	}

}
