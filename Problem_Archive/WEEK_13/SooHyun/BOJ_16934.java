/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 확인했을 때, 트라이 알괴리즘을 활용해야겠다고 판단했습니다.
	- 문제는 같은 닉네임이 주어질 때 닉네임+x(단 x>=2)인 경우를 어떻게 처리할지가 관건이였습니다.
	- 이를 해결하기 위해서 HashMap을 활용해서 key값으로 닉네임을 받고 value로 해당 닉네임이 몇건이 들어왔는지 카운트를 진행했습니다.
	- 닉네임을 입력받을 때 Trie 알고리즘의 search 함수를 활용해서 만약 한 글자에 대해 다음 글자가 자식으로 존재하지 않으면 그 즉시에 종료하는 것으로 별칭을 지정할 수 있게 되었습니다.
	- 이후 Trie 알고리즘의 insert함수를 적용시켜 각 문자에 대한 자식을 설정해두면서 문제를 해결했습니다.

시간 복잡도
	- Search 함수 : 닉네임의 길이를 N이라고 하면 O(N)의 시간복잡도를 가집니다.
	- Insert 함수 : 마찬가지로 문자열의 각 글자에 대해 처리하므로 문자열의 길이가 N인 경우 O(N)의 시간복잡도를 가집니다.
	- 외부 반복문 즉 K개의 닉네임에 대해 처리하고 싶은 경우 O(K)의 시간복잡도를 가집니다.
	- 전체 시간 복잡도 : O(N * K)

실행 시간
	- 1248ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static String[] nicknames;
    static String[] result;
    static String str;
    static StringBuilder sb;
    static Trie trie = new Trie();
    static HashMap<String, Integer> map = new HashMap<>();
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        nicknames = new String[N];
        result = new String[N];

        for(int i=0; i<N; i++) {
            sb = new StringBuilder();
            nicknames[i] = br.readLine().trim();
            map.put(nicknames[i], map.getOrDefault(nicknames[i], 0)+1);
            boolean flag = trie.search(nicknames[i]);

            str = sb.toString();
            if(map.get(nicknames[i]) >=2) {
                str+=Integer.toString(map.get(nicknames[i]));
            }
            System.out.println(str);

            trie.insert(nicknames[i]);


        }
        //System.out.println(sb.toString());
    }

    static class Node{
        Map<Character, Node> child = new HashMap<>();
        boolean endOfWord;

    }

    static class Trie{
        Node root = new Node();
        void insert(String str) {
            Node node = this.root;
            for(int i=0; i<str.length(); i++) {
                char ch = str.charAt(i);
                node = node.child.computeIfAbsent(ch, key -> new Node());
            }
            node.endOfWord=true;
        }

        boolean search(String str) {
            Node node = this.root;
            for(int i=0; i<str.length(); i++) {
                char ch = str.charAt(i);
                sb.append(Character.toString(ch));

                node = node.child.getOrDefault(ch, null);
                if(node== null) {
                    return false;
                }
            }
            return node.endOfWord;
        }
    }
}