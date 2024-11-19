/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 보고 바로 유니온 파인드 알고리즘을 떠올렸습니다.
	- 그리고 전형적인 유니온 파인드 알고리즘을 구현하여 parent 배열 즉 각 노드의 부모 노드를 찾았습니다.
	- 이후 1번 노드와 다른 노드들을 비교하여 부모 노드가 다른 노드가 나오면 출력하도록 구현하였습니다.

시간 복잡도
	- Union-Find 초기화 : O(N)
	- Union 연산 : O(α(N))
	- Find 연산 : O(α(N))
	- 전체 시간 복잡도 : O(α(N) + α(N) + N) = O(N)

실행 시간
	- 100ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] parent;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        parent = new int[N+1];
        for(int i=1; i<=N; i++) {
            parent[i]=i;
        }
        for(int i=0; i<N-2; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a,b);
        }
        for(int i=2; i<=N; i++) {
            if(find(1) != find(i)) {
                System.out.println(1+" "+i);
                break;
            }
        }
    }
    public static void union(int a,int b) {
        int parent_a = find(a);
        int parent_b = find(b);
        if(parent_a !=parent_b) {
            if(parent_a < parent_b) {
                parent[parent_b]=parent_a;
            }else {
                parent[parent_a]=parent_b;
            }
        }
    }
    public static int find(int a) {
        if(a==parent[a])
            return a;
        return parent[a] = find(parent[a]);
    }
}
