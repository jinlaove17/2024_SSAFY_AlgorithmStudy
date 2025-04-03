/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 비상 비상 코드에 사탄 들렸어요. 또 부분 정답(14/16)이 떴어요.
    - 문제는 다익스트라 문제입니다. 다익스트라를 활용하여 A에서 B까지 가는 경로를 구하는 문제입니다.
    - 따라서 다익스트라를 쓰기 위해서 각 노드까지의 최소 최대 간선 비용 저장을 위한 dijskra 배열을 선언하고 Node클래스의 List 배열을 선언해 그래프로 활용합니다.
    - Node의 각 객체는 도착 노드, 비용, 최대 비용을 저장합니다.
    - 핵심 함수는 finde로 다익스트라를 사용하기 위해 우선순위 큐를 선언하고 시작 노드 A를 넣어줍니다.
    - 이후 큐에서 하나씩 꺼내면서 조건을 비교합니다.
        1. 방문한 지점이거나 도착지이면 continue
        2. 방문하지 않은 지점이면 visit 배열을 true로 바꿔주고 해당 노드의 자식들을 탐색합니다.
        3. 자식 노드에 대해서 방문하지 않았고 간선 자체 비용과 누적 비용이 모두 C보다 작거나 같으면 해당 노드의 비용을 구합니다.
        4. 이제 현재까지 경로 중 가장 큰 간선값을 갱신하고 해당 노드에 대해서 더 작은 최대 간선값이 나왔을 때만 다익스라 배열의 값을 갱신하고 큐에 넣습니다.
    - 마지막으로 다익스트라 배열의 B의 값이 Integer.MAX_VALUE가 아니면 result에 저장하고 출력합니다.

시간 복잡도
    - 입력 처리 : O(N + M)
    - 다익스트라 알고리즘 : O(M*logN)
    - 전체 시간복잡도 : O(N+ M + M*logN) (N은 노드의 개수, M은 간선의 개수)

실행 시간
   - 912ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M, A, B, C;
    static List<Node>[] list;
    static int[] dijskra;
    static int result = -1;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        dijskra = new int[N+1];
        list = new ArrayList[N+1];
        for(int i=0; i<=N; i++){
            list[i] = new ArrayList<>();
        }
        Arrays.fill(dijskra, Integer.MAX_VALUE);
        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine().trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            list[a].add(new Node(b,cost));
            list[b].add(new Node(a,cost));
        }
        find();
        System.out.println(result);
    }

    public static void find() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] visit = new boolean[N+1];
        dijskra[A] = 0;
        pq.add(new Node(A,0, Integer.MIN_VALUE));
        while(!pq.isEmpty()) {
            Node node = pq.poll();
            if(visit[node.end] || node.end == B){
                continue;
            }
            visit[node.end] = true;
            for(int i=0; i<list[node.end].size(); i++){
                Node next = list[node.end].get(i);
                if(visit[next.end] || next.cost > C)
                    continue;
                int total_cost = node.cost + next.cost;
                if(total_cost > C)
                    continue;
                int cur_max = node.max;
                if(cur_max < next.cost) {
                    cur_max = next.cost;;
                }
                if(dijskra[next.end] > cur_max) {
                    dijskra[next.end] = cur_max;
                }
                pq.add(new Node(next.end, total_cost,cur_max));
            }
        }
        if(dijskra[B] !=Integer.MAX_VALUE){
            result = dijskra[B];
        }
    }
    static class Node implements Comparable<Node>{
        int end;
        int cost;
        int max;

        public Node(int end, int cost) {
            this.end = end;
            this.cost = cost;
        }

        public Node(int end, int cost, int max){
            this.end = end;
            this.cost = cost;
            this.max = max;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }
}