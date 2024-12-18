/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심은 주어진 gi 이하의 수 중에서 가장 큰 수를 할당하는 것입니다.
	- 단순히 매번 gi가 주어졌을 때 gi이하의 수까지 반복문을 돌릴 경우에 시간 초과가 발생할 것이라고 판단했습니다.
	- 따라서, gi 이하의 수 중에서 가장 큰 수를 할당하는 것을 어떻게 구현할지 고민해야 했습니다.
	- 이를 위해 Union-Find 알고리즘을 사용하여 gi 이하의 수 중에서 가장 큰 수를 할당하는 방법을 사용했습니다.
	- parent = new int[G+1]로 초기화하고, parent[i] = i로 초기화하여 gi번째가 초기 상태에서 gi번째를 가르키게 합니다.
	- 이후 같은 숫자 혹은 이하의 숫자가 들어왔을 결우 find 함수를 통해 가장 큰 수를 할당하고, union 함수를 통해 gi 이하의 수 중에서 가장 큰 수를 할당합니다.
	- 만약 find가 0인 경우에는 할당할 수 있는 게이트가 없으므로 break를 통해 반복문을 종료합니다.

시간 복잡도
	- 초기화 : O(G)
	- find와 union 연산 : O(P*α(G))
    - 전체 시간복잡도: O(G) + O(P*α(G))

실행 시간
	- 224ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int G;
    static int P;
    static int[] parent;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        G = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine().trim());
        P = Integer.parseInt(st.nextToken());
        parent = new int[G+1];
        for(int i=1; i<=G; i++) {
            parent[i]=i;
        }
        int result=0;
        for(int i=0; i<P; i++) {
            int idx = Integer.parseInt(br.readLine().trim());
            if(find(idx)==0)
                break;
            result++;
            union(find(idx), find(idx)-1);
        }
        System.out.println(result);

    }

    public static void union(int a, int b) {
        int parent_a = find(a);
        int parent_b = find(b);
        parent[a]=b;
    }

    public static int find(int a) {
        if(parent[a]==a) {
            return a;
        }
        return parent[a] = find(parent[a]);
    }

}
