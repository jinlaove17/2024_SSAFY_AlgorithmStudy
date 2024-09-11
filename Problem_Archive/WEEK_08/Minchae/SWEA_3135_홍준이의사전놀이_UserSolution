import java.util.*;

public class UserSolution {
	
	static class Node {
		HashMap<Character, Node> child; // 각 노드의 자식 노드들을 저장
		boolean end; // 해당 노드가 단어의 마지막인지 확인
		int cnt; // 현재 노드까지 일치하는 단어 개수
		
		public Node() {
			child = new HashMap<>();
			end = false;
		}
	}
	
	Node root; // 무조건 루트 노드를 가짐 -> 루트 노드의 자식이 단어의 첫 번째임
	
	public void init() {
		root = new Node();
	}
	
	public void insert(int buffer_size, String buf) {
		Node node = this.root;
		
		for (int i = 0; i < buffer_size; i++) {
			char c = buf.charAt(i);
			
			// 자식 노드가 없으면 새롭게 노드 추가, 있다면 해당 자식 노드로 갱신
			node = node.child.computeIfAbsent(c, key -> new Node());
			node.cnt++;
		}
		
		node.end = true; // 마지막 글자 -> 단어의 끝 표시
	}
	
	public int query(int buffer_size, String buf) {
		Node node = this.root;
		
		for (int i = 0; i < buffer_size; i++) {
			char c = buf.charAt(i);
			
			if (node.child.containsKey(c)) {
				node = node.child.get(c); // 자식 노드에 c가 있으면 계속 탐색
			} else {
				return 0;
			}
		}
		
		return node.cnt;
	}
}
