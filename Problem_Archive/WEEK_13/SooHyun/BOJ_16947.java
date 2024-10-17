/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심은 어떻게 사이클을 발견하는 것인가 입니다.
	- is_cycle이라는 사이클 탐색 함수를 선언했습니다.
	- start = 시작 위치, prev = 바로 직전의 노드, current = 현 노드
	- cycle[current] = true로 설정해, 현재 노드가 방문되었음을 기록합니다.
    - !cycle[next] 즉 다음 노드가 방문되지 않았을 때 dfs 재귀 호출을 실행합니다.
    - prev != next && next == start 즉 만약 방문한 노드가 start와 같고 이전 노드가 아닌 다른 노드라면 사이클이 형성된 것으로 재귀 호출을 종료합니다.
    - 이와 같은 경우가 아닌 즉 사이클이 아닌 경우 cycle[current] = false로 설정해 원복합니다.
    - 사이클이 완성된 이후에는 사이클에 포함되지 않은 노드에 대해서 bfs 함수를 호출해 가장 먼저 사이클에 도달하는 경우를 반환합니다.

시간 복잡도
	- is_cycle 함수 : DFS 방식으로 그래프를 탐색합니다. DFS는 각 노드를 한 번 방문하며 각 노드에서 인접한 모든 노드를 순차적으로 탐색합니다. N개의 노드와 M개의 간선을 가지고 있다고 가정할 때 DFS의 시간 복잡도는 O(N + M)입니다.
	- 외부 반복문 : O(N)
	- 전체 시간 복잡도 : O(N * (N + M))

실행 시간
	- 656ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] result;
    static boolean[] visit;
    static boolean[] cycle;
    static ArrayList<Integer>[] graph;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        result = new int[N+1];
        cycle = new boolean[N+1];
        graph = new ArrayList[N+1];
        for(int i=1; i<=N; i++) {
            graph[i] = new ArrayList<>();
        }
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            graph[n1].add(n2);
            graph[n2].add(n1);
        }
        for(int i=1; i<=N; i++) {
            if(is_cycle(i,i,i))
                break;
        }
        for(int i=1; i<=N; i++) {
            if(!cycle[i]) {
                result[i]=bfs(i);
            }
            sb.append(result[i]+" ");
        }
        System.out.println(sb.toString().trim());
    }

    public static int bfs(int station) {
        Queue<Node> que = new LinkedList<>();
        visit = new boolean[N+1];
        visit[station]=true;
        que.add(new Node(station,0));
        while(!que.isEmpty()) {
            Node node = que.poll();
            int idx = node.idx;
            int cost = node.cost;
            if(cycle[idx])
                return cost;
            for(int n : graph[idx]) {
                if(!visit[n]) {
                    visit[n]=true;
                    que.add(new Node(n,cost+1));
                }
            }
        }
        return -1;
    }
    public static boolean is_cycle(int start, int prev, int current) {
        cycle[current]=true;
        for(int next : graph[current]) {
            if(!cycle[next]) {
                if(is_cycle(start, current, next)) {
                    return true;
                }
            }else if(prev != next && next == start) {
                return true;
            }
        }

        cycle[current]=false;
        return false;
    }

    static class Node{
        int idx;
        int cost;

        public Node(int idx, int cost) {
            this.idx=idx;
            this.cost=cost;
        }
    }
}
