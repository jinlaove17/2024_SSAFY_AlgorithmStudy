/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에서 사용된 알고리즘은 크루스칼과 유니온 파인드 알고리즘입니다.
	- 핵심은 가능한 많은 전선을 기부하는 것입니다. 반대로 말하면 내가 가지고 있는 전선 중의 최소값으로 모든 간선을 연결하는 것입니다.
	- 이에 따라 크루스칼 알고리즘을 사용하여 최소 스패닝 트리를 구한 뒤, 전체 전선의 합에서 최소 스패닝 트리의 간선의 합을 빼주면 됩니다.
	- 단 모든 간선이 연결이 되어야하므로 모든 간선이 연결이 되는지에 사용한 것이 유니온 파인드입니다.
	- 크루스칼을 돌면서 유니온 파인드 알고리즘으로 각각의 최상위 노드를 확인해 최상위가 다르다면 합쳐주고 cnt를 증가시키면서 마지막에 cnt가 N-1이 아니면 모든 간선이 연결된 것이 아니므로 -1을 출력합니다.

시간 복잡도
	- 입력 및 초기화 : O(N^2) (N : 전선의 갯수)
    - 크루스칼 알고리즘(우선 순위 큐 정렬 & 유니온 파인드)
        - 크루스칼 알고리즘 : O((N^2) * log(N^2)) (N^2 : 간선의 갯수)
        - 유니온 파인드 : O(α(N)) (N : 전선의 갯수)
    - 전체 시간복잡도: O((N^2) * log(N^2))

실행 시간
    - 시간 복잡도 : 112ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int total=0;
    static PriorityQueue<Node> pq;
    static int[] parent;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        pq = new PriorityQueue<>();
        parent = new int[N];
        for(int i=0; i<N; i++) {
            String str = br.readLine().trim();
            parent[i]=i;
            for(int j=0; j<N; j++) {
                char ch = str.charAt(j);
                if(ch=='0') {
                    pq.add(new Node(i,j,0));
                }else if(ch >='a' && ch <='z') {
                    pq.add(new Node(i,j,ch-'a'+1));
                    total+=ch-'a'+1;
                }else if(ch>='A' && ch <='Z') {
                    pq.add(new Node(i,j,ch-'A'+27));
                    total+=ch-'A'+27;
                }
            }
        }
        int cnt=0;
        int sum=0;
        while(!pq.isEmpty()) {
            Node node = pq.poll();
            int a = node.from;
            int b = node.to;
            if(a==b || node.cost==0)
                continue;
            if(find(a) != find(b)) {
                union(a,b);
                cnt++;
                sum+=node.cost;
            }

        }
        if(cnt!=N-1) {
            System.out.println(-1);
            return;
        }
        System.out.println(total-sum);
    }
    public static int find(int a) {
        if(a == parent[a]) {
            return a;
        }
        return parent[a] = find(parent[a]);
    }
    public static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if(rootA != rootB) {
            parent[Math.max(rootA, rootB)]=Math.min(rootA, rootB);
        }
    }
    static class Node implements Comparable<Node>{
        int from;
        int to;
        int cost;

        public Node(int from, int to, int cost) {
            this.from=from;
            this.to=to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return this.cost - o.cost;
        }
    }
}
