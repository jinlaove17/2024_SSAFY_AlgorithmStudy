/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심 알고리즘은 다익스트라입니다.
	- Node class를 정의하면서 목적지 좌표와 가중치를 저장합니다.
    - 문제의 핵심은 g와 h를 지나는 최단 경로를 찾는 것입니다.
    - 다익스타를 통해서 s에서 g,h를 잇는 간선을 지나면서 목적지에 도달하는 것이 g, h를 고려하지 않은 최단 거리와 동일해야하는 것입니다.
    - 따라서 s->g->h->t와 s->h->g->t의 최단 거리를 구하고 s->t의 최단 거리와 비교하여 같으면 결과에 추가합니다.

시간 복잡도
    - 초기화 및 데이터 준비 : O(N+M+T)
        - 리스트 초기화 : O(N+M)
        - 타겟 입력 처리 : O(T)
    - 다익스트라 알고리즘 : O((N+M)*logN*T)
    - 결과 정렬 : O(T*logT)
    - 전체 시간 복잡도 : O((N+M)*logN*T + T*logT+ N+M+T) = O((N+M)*logN*T)

실행 시간
	- 4580ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int TEST_CASE;
    static int N, M, T;
    static int crossValue;
    static int[] distance;
    static int[] target;
    static int INF = Integer.MAX_VALUE;
    static ArrayList<Node>[] list;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        TEST_CASE = Integer.parseInt(st.nextToken());
        for(int tc=0; tc<TEST_CASE; tc++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            T = Integer.parseInt(st.nextToken());
            list = new ArrayList[N+1];
            for(int i=0; i<N+1; i++) {
                list[i] = new ArrayList<>();
            }
            st = new StringTokenizer(br.readLine().trim());
            int start = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            for(int m=0; m<M; m++) {
                st = new StringTokenizer(br.readLine().trim());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                list[a].add(new Node(b,weight));
                list[b].add(new Node(a,weight));
            }
            target = new int[T];
            for(int i=0; i<T; i++) {
                target[i] = Integer.parseInt(br.readLine().trim());
            }
            ArrayList<Integer> result = new ArrayList<>();
            for(int t : target) {
                long result1 = dijkstra(start,g)+dijkstra(g,h)+dijkstra(h,t);
                long result2 = dijkstra(start,h)+dijkstra(h,g)+dijkstra(g,t);
                long result3 = dijkstra(start,t);
                if(Math.min(result1, result2) == result3) {
                    result.add(t);
                }
            }
            Collections.sort(result);
            String str="";
            for(int i : result) {
                str+=i+" ";
            }
            sb.append(str.trim()+"\n");
        }
        System.out.println(sb.toString());
    }

    public static int dijkstra(int start, int end) {
        distance = new int[N+1];
        Arrays.fill(distance, INF);
        PriorityQueue<Node> que = new PriorityQueue<>();
        que.add(new Node(start, 0));
        distance[start]=0;
        while(!que.isEmpty()) {
            Node cur = que.poll();
            int cur_index = cur.end;
            int cur_cost = cur.cost;
            for(Node next : list[cur_index]) {
                int next_cost = distance[cur_index] + next.cost;
                if(next_cost < distance[next.end]) {
                    distance[next.end] = next_cost;
                    que.add(new Node(next.end, next_cost));
                }

            }
        }
        return distance[end];
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
