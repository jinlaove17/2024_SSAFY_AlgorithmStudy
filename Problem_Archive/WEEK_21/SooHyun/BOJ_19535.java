/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에서 사용된 알고리즘은 2가지입니다. 첫째 그래프, 둘째 조합론입니다.
	- ㄷ이 만들어지는 경우는 어떠한 2가지 노드가 연결이 된 상태에서 각각의 노드에 앞서 연결이 되어있는 노드를 제외한 것중 1개 이상의 노드가 존재해야합니다.
	- ㅈ가 만들어지는 경우는 하나의 노드에 연결된 노드가 3개 이상인것 입니다.
	- 이를 위해 graph라는 명을 가진 List를 선언해 각각의 노드에 연결된 또 다른 노드의 인덱스 번호를 저장합니다.
	- 이후 N번의 반복분을 돌면서 먼저 ㅈ가 만들어지는지 확인합니다. 그리고 방문 체크를 해주면서 이후 ㄷ가 만들어지는지 확인합니다.
	- ㅈ의 갯수는 해당노드에 연결된 노드의 갯수를 cnt라고 하면 cntC3이고 이를 수식으로 나타내면 cnt*(cnt-1)*(cnt-2)/6입니다.
    - ㄷ의 갯수는 해당노드에 연결된 노드의 갯수를 len1이라고 하고 연결된 노드의 연결이 된 갯수를 len2라고 하면 len1-1 * len2-1입니다.
	- 최종적으로 값을 비교해 정답을 도출합니다.

시간 복잡도
	- 그래프 생성 : O(N)
	- 노드 계산 : O(N+M) => 여기서 M = N-1
	- 전체 시간 복잡도 : O(N) + O(N+M) = O(N)

실행 시간
	- 820ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static List<Integer>[] graph;
    static boolean[] visit;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        graph = new ArrayList[N+1];
        visit = new boolean[N+1];
        for(int i=1; i<=N; i++) {
            graph[i] = new ArrayList<>();
        }
        for(int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            graph[from].add(to);
            graph[to].add(from);
        }
        long D=0;
        long G=0;
        for(int i=1; i<=N; i++) {
            long len1 = graph[i].size();
            if(len1 >=3) {
                G+=(len1*(len1-1)*(len1-2))/6;
            }
            visit[i]=true;
            for(int node : graph[i]) {
                long len2 = graph[node].size();
                if(!visit[node]) {
                    D+=(len1-1)*(len2-1);
                }
            }
        }
        if(D > 3*G) {
            System.out.println("D");
        }else if(D < 3*G) {
            System.out.println("G");
        }else {
            System.out.println("DUDUDUNGA");
        }
    }

}
