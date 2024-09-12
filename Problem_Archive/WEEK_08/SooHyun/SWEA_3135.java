import java.util.HashMap;

public class UserSolution {
    Node root;// 트리의 루트 노드
    public void init() {// 루트 노드 초기화
        this.root = new Node();
    }

    public void insert(int buffer_size, String buf) {
        Node node = this.root; // 루트 노드부터 시작

        for(int i=0; i<buffer_size; i++) {
            node = node.child.computeIfAbsent(buf.charAt(i), key -> new Node()); // 현재 문자의 자식 노드가 없으면 새 노드를 생성하고 있으면 해당 노드를 사용
            node.cnt++; // 문자열의 개수 증가
        }
        node.endOfWord=true; // 단어의 끝을 표시
    }

    public int query(int buffer_size, String buf) {
        Node node = this.root;

        for(int i=0; i<buffer_size; i++) {
            node = node.child.getOrDefault(buf.charAt(i), null); // 현재 문자의 자식 노드로 이동 없으면 null 반환
            if(node == null) // 해당 문자가 없으면 검색 실패
                return 0;
        }
        return node.cnt; // 마지막 노드에서 해당 문자열을 거친 횟수 반환
    }
}

class Node{
    HashMap<Character, Node> child; // Character에 매핑된 Node
    int cnt; // 현재 노드를 거치는 문자열의 개수
    boolean endOfWord; // 단어의 끝인지 여부
    public Node(){
        this.child = new HashMap<>();
        this.cnt=0;
        endOfWord=false;// 처음에는 단어의 끝이 아님
    }
}