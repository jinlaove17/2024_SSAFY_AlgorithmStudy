/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 이 문제는 지난 주 주사위 고르기 문제와 유사한 방향이라고 생각했습니다. 이에 따라 dfs 탐색과 백트래킹을 사용하고자 했습니다.
    - 초기화 과정에서 각 인구 수를 저장히기 위한 populations 배열, 각 구역의 연결 정보를 저장하기 위한 연결 리스트 배열를 선언하고 이를 통해 초기 세팅을 했습니다.
    - 이후 정답을 도출하기 위해 findcase 함수를 사용했습니다.
    - 기저 조건으로 N-1에 도달하게 되면 check가 true인 것을 A구역, false인 것을 B구역이라고 간주를 하고 조건인 같은 구역 내 선거구는 모두 연결되었는지 확인하는 작업을 수행합니다.
    - 이를 isConnect 함수를 통해 구현했고 dfs를 통해 특정 시작점에서 연결된 다른 노드들의 값을 전부 true로 바꿔줍니다.
    - 결과적으로 특정 구역에 대해 연결된 노드들이 모두 true라면 해당 구역은 연결되어있다고 판단하고 이를 통해 인구 수를 계산하고 최소값을 갱신합니다.
    - 만약 answer가 여전히 Intger.MAX_VALUE라면 즉 갱신되지 않았다면 -1을 출력하도록 했습니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N + E) (N : 구역의 개수, E : 각 구역의 연결 정보)
    - findCase 함수 : O(2^N * (N+E)) (N : 구역의 개수, E : 각 구역의 연결 정보)
    - isConnect 함수 : O(E)
    - 전체 시간복잡도: O(2^N * (N+E))

실행 시간
   - 128ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] populations;
    static List<Integer>[] list;
    static boolean[] check;
    static int cnt=0;
    static int answer=Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        populations = new int[N+1];
        list = new ArrayList[N+1];
        check = new boolean[N+1];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=1; i<=N; i++) {
            list[i] = new ArrayList<>();
            populations[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int num = Integer.parseInt(st.nextToken());
            for(int j=0; j<num; j++) {
                list[i].add(Integer.parseInt(st.nextToken()));
            }
        }
        findCase(1);
        if(answer == Integer.MAX_VALUE)
            answer=-1;
        System.out.println(answer);
    }

    public static void findCase(int depth) {
        if(depth == N+1) {
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();
            for(int i=1; i<=N; i++) {
                if(check[i])
                    list1.add(i);
                else
                    list2.add(i);
            }
            if (!list1.isEmpty() && !list2.isEmpty() && isConnect(list1) && isConnect(list2)) {
                int population1 = 0, population2 = 0;
                for (int node : list1) population1 += populations[node];
                for (int node : list2) population2 += populations[node];
                answer = Math.min(answer, Math.abs(population1 - population2));
            }
            return;
        }
        check[depth]=true;
        findCase(depth+1);
        check[depth]=false;
        findCase(depth+1);
    }

    public static boolean isConnect(List<Integer> tlist) {
        if (tlist.isEmpty()) return false;
        boolean[] visited = new boolean[N + 1];
        dfs(tlist.get(0), tlist, visited);

        for (int node : tlist) {
            if (!visited[node]) return false;
        }
        return true;
    }

    public static void dfs(int current, List<Integer> tlist, boolean[] visited) {
        visited[current] = true;
        for (int next : list[current]) {
            if (!visited[next] && tlist.contains(next)) {
                dfs(next, tlist, visited);
            }
        }
    }
}
