/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 네 전형적인 MSP 크루스칼 알고리즘을 활용해서 푸는 문제입니다.
    - 문제에서 도출해야하는 것은 최대한 절약할 수 있는 금액입니다. 거꾸로 말하면 최소로 트리를 만들어라입니다. 따라서 전체 비용에서 최소로 만들어진 트리의 비용을 빼면되는 문제입니다.
    - 크루스칼을 사용하기 위해 우선순위 큐를 선언하고 Info 클래스를 선언합니다. Info 클래스는 Comaparable 인터페이스를 상속받아 가중치를 기준으로 오름차순 정렬되게 합니다.
    - 이후 parent 배열을 활용해 union-find 알고리즘을 활용합니다. 최소의 가중치를 갖는 트리를 만들고 그 상황에서 비용을 계산합니다.
    - 마지막으로 전체 가중치의 총합에서 만든 트리의 가중치의 총합을 뺴면 답을 도출할수있습니다.

시간 복잡도
    - 입력 및 초기화: O(N + M)
    - 간선 삽입(우선순위 큐) : O(N * log N)
    - 크루스칼 알고리즘(Union-Find) : O(N * log M)
    - 전체 시간복잡도 : O(N + M +  N * log N + N * log M) = O(N * log N)

실행 시간
   - 920ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int M, N;
    static int[] parent;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while(true) {
            PriorityQueue<Info> pq = new PriorityQueue<>();
            st = new StringTokenizer(br.readLine().trim());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            if(M == 0 && N==0) {
                break;
            }
            parent = new int[M];
            for(int i=0; i<M; i++) {
                parent[i] = i;
            }
            int sum=0;
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                sum+=cost;
                pq.add(new Info(x, y, cost));
            }
            int min_value=0;
            while(!pq.isEmpty()) {
                Info info = pq.poll();
                int x = info.start;
                int y = info.end;
                if(find(x) != find(y)) {
                    union(x, y);
                    min_value+=info.cost;
                }
            }
            sb.append(sum-min_value+"\n");
        }
        System.out.println(sb.toString());
    }

    public static int find(int x) {
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
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Info o) {
            // TODO Auto-generated method stub
            return this.cost - o.cost;
        }
    }

}
