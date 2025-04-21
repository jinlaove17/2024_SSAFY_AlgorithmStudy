/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 이번 주에 최소 스패닝 트리(크루스칼) 문제가 많네요 ㅎㅎ.
    - 이번 문제의 핵심은 발전소가 정해져 있다는 것입니다. 그에 따라 발전소로 정해진 정점을 어떻게 처리할 지가 문제의 관건이였습니다.
    - 해당 정점이 발전소이면 이것이 발전소다라고 판단하는 요소가 필요했습니다. 이에 저는 parent 배열을 사용해 발전소인 정점에 대해서는 -1의 값을 할당함으로써 발전소임을 명명했습니다.
    - 그 이후에는 전형적인 크루스칼 알고리즘을 활용합니다. 우선순위 큐를 선언하고 Info 클래스를 선언하면서 cost 즉 가중치를 기준으로 오름차순으로 정렬하게 합니다.
    - 이후 union-find 알고리즘을 활용해 연결된 정점에 대해서는 union을 해주고 cost를 누적합니다. 여기서 핵심은 find함수에서 parent가 -1 즉 발전소이면 -1이라는 값을 반환해주는 일반적인 find 함수에 로직을 하나 추가해줍니다.
    - 이제 union-find를 수행하면서 최소값을 갱신해주면서 답을 도출합니다.

시간 복잡도
    - 입력 및 초기화: O(N + M + K)
    - 간선 삽입(우선순위 큐) : O(M * log M)
    - 크루스칼 알고리즘(Union-Find) : O(M * log N)
    - 전체 시간복잡도 : O(N + M * log M + M * log N) = O(M * log M)

실행 시간
   - 440ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N, M, K;
    static int[] parent;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        PriorityQueue<Info> pq = new PriorityQueue<>();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        parent = new int[N+1];
        for(int i=0; i<=N; i++) {
            parent[i] = i;
        }
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<K; i++) {
            int n = Integer.parseInt(st.nextToken());
            parent[n]=-1;
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            pq.add(new Info(x,y,cost));
        }
        int result=0;
        while(!pq.isEmpty()) {
            Info info = pq.poll();
            int x = info.start;
            int y = info.end;
            int cost = info.cost;
            if(find(x) != find(y)) {
                union(x, y);
                result += cost;
            }
        }
        System.out.println(result);
    }

    public static int find(int x) {
        if(parent[x] == -1)
            return -1;
        if(x == parent[x])
            return x;
        return parent[x] = find(parent[x]);
    }

    public static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if(rootA != rootB) {
            parent[Math.max(rootA, rootB)] = Math.min(rootA, rootB);
        }
    }
    static class Info implements Comparable<Info>{
        int start;
        int end;
        int cost;

        public Info(int start, int end, int cost) {
            this.start=start;
            this.end=end;
            this.cost=cost;
        }

        @Override
        public int compareTo(Info o) {
            // TODO Auto-generated method stub
            return this.cost - o.cost;
        }
    }
}
