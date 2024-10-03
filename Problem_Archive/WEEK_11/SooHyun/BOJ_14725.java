/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 풀 때 트라이 알고리즘를 사용하자라고 생각했었다.
	- 그런데 삽입만 발생하는 구조에서 삭제에 대해서는 고려할 필요없었기에 트라이 알고리즘을 살짝 변형해서 필요한 부분만 사용하고자 했습니다.
	- 또한 입력으로 주어지는 것은 문자가 아닌 문자열이였고 문자열에 대한 계층을 구하는 것이였기에 일반적인 트라이에서 사용되는 char가 아닌 String으로 구성했습니다.
	- 자식을 HashMap<String, Node>로 관리하여 각 노드에서 어떤 자식이 있는지 빠르게 확인하고 계층 구조를 재귀적으로 탐색하며 출력할 수 있도록 하였습니다.

시간 복잡도
	- N개의 개미가 각각 K개의 문자열을 가지고 노드를 삽입에는 입에는 O(N * K)의 시간 복잡도가 발생합니다.
	- 또한 정렬 하는 작업에서는 O(KlogK)의 시간 복잡도가 추가적으로 소요됩니다. (정렬은 어떻게 되는지 로직을 몰라서 지피티에서 물어봤다.)
	- 따라서 전체 시간 복잡도는 O(N * K * logK)입니다.

실행 시간
	- 196ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;


public class Main {
    static final String STR="--";
    static int N;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine().trim());
        Node root = new Node();// 트리의 루트 노드 생성
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int K = Integer.parseInt(st.nextToken());
            Node cur = root; // 현재 노드를 루트로 초기화
            for(int k=0; k<K; k++) {
                String word = st.nextToken();
                if(!cur.child.containsKey(word)) {// 자식 노드에 해당 문자열이 없으면 새로 추가
                    cur.child.put(word, new Node());
                }
                cur = cur.child.get(word); // 현재 노드를 자식 노드로 이동
            }
        }
        search(root,"");
    }

    public static void search(Node root, String s) {
        Object[] arr = root.child.keySet().toArray();
        Arrays.sort(arr); // 자식 노드들의 키 값을 배열로 변환한 후 정렬

        for(Object str : arr) {
            System.out.println(s+str);
            search(root.child.get(str), s+STR);
        }
    }

    static class Node{
        HashMap<String, Node> child;

        public Node() {
            child = new HashMap<>();
        }
    }
}
