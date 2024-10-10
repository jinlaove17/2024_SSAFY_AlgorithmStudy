/*
    문제 접근
    네트워크 안에서 1번 슈퍼 컴퓨터를 기준으로 최단 거리를 구해야 겠다고 생각
    최단거리? 다익스트라를 바로 떠올림 (N = 1000) 이므로 충분히 가능
    그러나 단순 최단거리를 구하면 안되고, 경로 역추적도 진행해야함

    "원래의 네트워크에서 통신하는데 걸리는 최소 시간보다 커져서는 안 된다." 라는 조건은
    최소한 시간이 최단 거리를 만족해야 한다는 의미로 해석할 수 있다.

    알고리즘 : 다익스트라와 경로 역추적

    시간 복잡도: O(M log N) + O(N) 인데 M 범위 어디감?

    실행 시간: 450 ms

*/


import java.util.*;
import java.io.*;

public class Main {

    public static int n, m;
    public static int cnt;
    public static int[] dist;
    public static int[] conn;
    public static int INF = (int)1e9;
    public static class Edge implements Comparable<Edge>{
        int node;
        int d;
        public Edge(int n, int d){
            this.node = n;
            this.d = d;
        }

        @Override
        public int compareTo(Edge o) {
            return this.d - o.d;
        }
    }

    public static ArrayList<Edge>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        StringBuilder sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        graph = new ArrayList[n+1]; // 그래프 정보(edges) 담는 인접 리스트
        dist = new int[n+1]; // 최단거리 저장하는 dist 배열
        conn = new int[n+1]; // 최단거리 경로(커넥션) 역추적을 위한 배열

        for(int i=0; i<n+1; i++){
            graph[i] = new ArrayList<Edge>();
        }

        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            graph[a].add(new Edge(b, d));
            graph[b].add(new Edge(a, d));
        }

        // 초기화 후 다익스트라
        Arrays.fill(dist, INF);
        dijkstra();

        // for 문 돌면서 연결 되어있는지 확인하고 카운팅
        for(int i=2; i<=n; i++){
            if(conn[i] == 0) continue;
            cnt++;
            sb.append(i+ " " + conn[i]+"\n");
        }

        System.out.println(cnt);
        System.out.println(sb.toString());
    }

    public static void dijkstra(){
        // 초기화 후 첫 Edge 추가
        dist[1] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(1, 0));

        while (!pq.isEmpty()){
            Edge edge = pq.poll();

            if(edge.d > dist[edge.node]) continue;

            for(Edge e: graph[edge.node]){
                if(dist[e.node] > e.d + edge.d){
                    dist[e.node]  = e.d + edge.d;
                    conn[e.node] = edge.node;
                    pq.add(new Edge(e.node, dist[e.node]));
                }
            }
        }
    }
}
