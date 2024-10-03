
/**
 * 14725 개미굴
 * https://www.acmicpc.net/problem/14725
 * 
 * @author minchae
 * @date 2024. 10. 4.
 * */

import java.util.*;
import java.io.*;

public class Main {

	static class Trie {
		Node node;

		Trie() {
			node = new Node();
		}

	}

	static class Node {
		Map<String, Node> map;

		Node() {
			map = new TreeMap<String, Node>();
		}

		public void print(int depth) {
			for (String s : map.keySet()) {
				for (int i = 0; i < depth; i++) {
					System.out.print("--");
				}
				
				System.out.println(s);
				
				map.get(s).print(depth + 1);
			}
		}
	}
	
	static int N;
	static Trie root;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		root = new Trie();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int k = Integer.parseInt(st.nextToken());
			
			Node node = root.node;
			
			for (int j = 0; j < k; j++) {
				node = node.map.computeIfAbsent(st.nextToken(), s -> new Node());
			}
		}
		
		root.node.print(0);
	}

}
