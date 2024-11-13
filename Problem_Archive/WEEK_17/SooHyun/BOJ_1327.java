/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심은 해당 상황에서 가능한 뒤집기를 전부 수행하는 것입니다.
	- String 타입에는 reverse() 메소드가 존재하지 않으므로 StringBuilder를 이용해 reverse() 메소드를 사용해야 합니다.
	- 입력을 question이라는 문자열로 입력을 받고 정답을 answer로 설정합니다.
	- 이후 bfs 알고리즘을 통해 해당 상황에서 발생할 수 있는 뒤집기를 전부 수행합니다.
	- 만약 뒤집기를 통해서 중복되는 값이 나올 수 있으므로 Set을 이용해 중복을 제거합니다.
	- bfs를 통해 최단 횟수를 리턴하고 만약 bfs로 답을 도출하지 못하면 -1을 리턴합니다.

시간 복잡도
	- 초기화 : O(N)
	- BFS : 최악의 경우 N개의 위치에서 가능한 모든 경우의 뒤집기를 수행 => 각 노드에서 최대 O(N-K+1)의 경우를 탐색하며 각각의 문자열 조작은 O(K) 복잡도를 가집니다. 따라서 각 단계에서 O(N * K) 시간이 소요될 수 있습니다.
	- 전체 시간 복잡도 : O(N*N)


실행 시간
	- 600s(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static int N, K;
    static String answer="";
    static String question="";
    static Set<String> set = new HashSet<>();
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine().trim());
        for(int i=1; i<=N; i++) {
            answer+=Integer.toString(i);
            question +=st.nextToken();
        }
//		System.out.println(answer);
//		System.out.println(question);
//		StringBuilder sb = new StringBuilder(question.substring(10));
//		System.out.println(sb.reverse().toString());
        int result = bfs();
        System.out.println(result);
    }

    public static int bfs() {
        Queue<Info> que = new LinkedList<>();
        que.add(new Info(question, 0));
        set.add(question);
        while(!que.isEmpty()) {
            Info info = que.poll();
            String current_str = info.str;
            int current_count = info.count;
            if(answer.equals(current_str)) {
                return current_count;
            }
            for(int i=0; i<=N-K; i++) {
                StringBuilder sb = new StringBuilder(current_str.substring(i,i+K));
                String str = current_str.substring(0,i)+sb.reverse().toString();
                if(i+K <N) {
                    str+=current_str.substring(i+K);
                }
                if(!set.contains(str)) {
                    set.add(str);
                    que.add(new Info(str, current_count+1));
                }
            }
        }
        return -1;
    }

    static class Info{
        String str;
        int count;
        public Info(String str, int count) {
            this.str=str;
            this.count=count;
        }
    }
}
