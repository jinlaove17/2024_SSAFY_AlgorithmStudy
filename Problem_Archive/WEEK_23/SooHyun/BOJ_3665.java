/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심 알고리즘은 위상정렬입니다. (이번 주에 위상정렬이 많네요...)
	- 처음 문제를 봤었을 때 어떻게 할지 감이 안잡혔습니다...
	- 그래서 프로그래머스를 먼저 풀었고 거기서 사용한 위상정렬에서 감을 얻어 왠지 이렇게하면 되지 않을까하며 적용해봤습니다.
	- 처음 순위를 받게 되면서 1등인 경우 degree를 1로, 2등인 경우 degree를 2로... N등인 경우 degree를 N으로 지정해줍니다.
    - 또한 boolean형 2차원 배열 edges를 통해 A가 B보다 순위가 높은 경우 그 값을 true로 할당합니다.
    - 이후 순위가 바뀌는 경우 change 함수를 통해 edges의 값과 degree값을 업데이트 해줍니다.
    - 자 이제 위상정렬 알고리즘을 사용합니다.
    - 처음 degree가 1인 것 즉 1등부터 큐에 저장을 하고 while문을 들어가기 전에 만약 큐에 들어간 값이 없으면 IMPOSSIBLE을, 큐에 들어간 값이 2개 이상 즉 같은 순위값을 가지고 있어 누가 이겼는지 모르는 경우이므로 ?를 반환합니다.
    - 그 단계를 지나면 count 즉 순위가 지정된 값들을 저장하기 위한 변수를 선언하고 while문을 돌면서 방문체크, StringBuilder에 값 저장, count 갯수 증가를 수행합니다.
    - 이후 edges를 순회하면서 해당 노드보다 후순위 즉 true인 것에 모든 노드의 degree를 하나 감소시키고 감소시킨 상태가 1인 경우 다음 탐색 대상으로 판단해 다시 que에 넣어줍니다.
    - 이런 과정을 통해 while문을 빠져나오게 되면 다시 count가 전체 노드의 갯수가 되었는지 확인하고 아니라면 IMPOSSIBLE을, 맞다면 StringBuilder에 저장된 값을 반환합니다.
    - 이렇게 위상정렬 알고리즘을 통해 문제를 해결할 수 있습니다.

시간 복잡도
    - 초기화 및 데이터 준비 : O(N^2)
    - 순위 변경(change 함수) : O(M)
    - 위상 정렬 알고리즘 : O(N^2)
    - 전체 시간 복잡도 : O(N^2 + M + N^2) = O(N^2)

실행 시간
	- 396ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int TEST_CASE;
    static int N, M;
    static int[] degree;
    static int[] rank;
    static boolean[][] edges;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        TEST_CASE = Integer.parseInt(st.nextToken());
        for(int tc=0; tc<TEST_CASE; tc++) {
            N = Integer.parseInt(br.readLine().trim());
            degree = new int[N+1];
            edges = new boolean[N+1][N+1];
            rank = new int[N+1];
            st = new StringTokenizer(br.readLine().trim());
            for(int i=1; i<=N; i++) {
                rank[i] = Integer.parseInt(st.nextToken());
                degree[rank[i]]=i;

            }
            for(int i=1; i<=N; i++) {
                int from = rank[i];
                for(int j=i+1; j<=N; j++) {
                    int to = rank[j];
                    edges[to][from]=true;
                }
            }
//			for(int i=1; i<=N; i++) {
//				for(int j=1; j<=N; j++) {
//					System.out.print(edges[i][j]+" ");
//				}
//				System.out.println();
//			}
            M = Integer.parseInt(br.readLine().trim());
            for(int i=0; i<M; i++) {
                st = new StringTokenizer(br.readLine().trim());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                change(from,to);
            }
//			for(int i=1; i<=N; i++) {
//				System.out.print(degree[i]+" ");
//			}
            System.out.println(topology());
        }
    }

    public static void change(int from, int to) {
        if(!edges[from][to]) {
            edges[from][to]=true;
            edges[to][from]=false;
            degree[from]++;
            degree[to]--;
        }else {
            edges[from][to]=false;
            edges[to][from]=true;
            degree[from]--;
            degree[to]++;
        }
    }

    public static String topology() {
        Queue<Integer> que = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        boolean[] visit = new boolean[N+1];
        int count = 0;
        for(int i=1; i<=N; i++) {
            if(degree[i]==1) {
                que.add(i);
            }
        }
        if(que.size()==0) {
            return "IMPOSSIBLE";
        }
        if(que.size()>1) {
            return "?";
        }
        while(!que.isEmpty()) {
            if(que.size()==0) {
                return "IMPOSSIBLE";
            }
            if(que.size()>1) {
                return "?";
            }
            int cur = que.poll();
            visit[cur]=true;
            count++;
            sb.append(cur+" ");
            for(int i=1; i<=N; i++) {
                if(edges[i][cur]) {
                    degree[i]--;
                    if(degree[i]==1 && !visit[i]) {
                        que.add(i);
                    }
                }
            }
        }
        if (count != N) {
            return "IMPOSSIBLE";
        }
        return sb.toString().trim();
    }
}
