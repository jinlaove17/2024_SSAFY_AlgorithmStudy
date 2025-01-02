/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심 알고리즘은 dfs입니다.
	- 해당 문제를 풀 때 가장 어려웠던 것은 입력을 받는 부분이였습니다. 입력을 받는 부분에서 종료 조건이 따로 명시되어 있지 않아서 while 문을 통해 String str을 받고 그것이 비어있으면 즉 isEmpty()인 경우에 while 문을 빠져나오게 했습니다.
	- Node class를 정의하면서 목적지 좌표와 가중치를 저장합니다.
    - 만약 아무런 입력이 없는 경우는 end==-1 혹은 list[1]이 비어있는 경우에 0을 출력하고 종료합니다.
    - 1번째 노드부터 탐색을 시작하고 방문체크를 해주면서 dfs를 통해 max와 num의 값을 갱신합니다.
    - 그렇게 1에서 가장 먼 노드 num을 찾은 후에 다시 방문 배열을 전부 false로 초기화하면서 max 또한 초기화를 진행하고 다시 dfs를 통해 max를 찾습니다.

시간 복잡도
    - 초기화 및 데이터 준비 : O(N+M)
        - 리스트 초기화 : O(N)
        - 입력 처리 및 간선 저장 : O(M)
    - DFS
        - 첫 번째 DFS : O(N+M)
        - 두 번째 DFS : O(N+M)
    - 전체 시간 복잡도 : O(N+M) + O(N+M) + O(N+M) = O(N+M)

실행 시간
	- 144ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static ArrayList<Node>[] list;
    static boolean[] check;
    static int start=1;
    static int end=-1;
    static int max=0;
    static int num=-1;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        start=1;
        end=-1;
        list = new ArrayList[10001];
        for (int i = 0; i <= 10000; i++) {
            list[i] = new ArrayList<>();
        }
        while(true) {
            String str = br.readLine();
            if (str == null || str.trim().isEmpty())
                break;
            st = new StringTokenizer(str.trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            list[a].add(new Node(b,cost));
            list[b].add(new Node(a,cost));
            end = Math.max(end, a);
            end = Math.max(end, b);
        }
        if (end == -1 || list[1].isEmpty()) {
            System.out.println(0);
            return;
        }
        check = new boolean[10001];
        check[1]=true;
        dfs(1,0);
//		System.out.println(num);
        max=-1;
        Arrays.fill(check,false);
        check[num]=true;
        dfs(num,0);
        System.out.println(max);
    }

    public static void dfs(int idx, int cost) {
        if (list[idx] == null || list[idx].isEmpty()) {
            return;
        }
        for(Node node : list[idx]) {
            if(check[node.end])
                continue;
            check[node.end]=true;
            if(max < cost+node.cost) {
                num = node.end;
                max = cost+node.cost;
            }
            dfs(node.end, cost+node.cost);
        }
    }
    static class Node implements Comparable<Node>{
        int end;
        int cost;

        public Node(int end, int cost) {
            this.end=end;
            this.cost=cost;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return this.cost-o.cost;
        }
    }
}
