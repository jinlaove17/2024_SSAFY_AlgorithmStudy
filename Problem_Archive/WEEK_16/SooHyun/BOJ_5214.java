/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 읽었을 때 2차원 배열을 통해 간선의 정보를 표시하고 bfs를 돌리는 방식을 떠올렸습니다.
	- 그랬더니 메모리 초과 이슈가 발생해서 발상의 전환을 했습니다.
	- 하이퍼튜브 또한 하나의 역으로 간주를 하여 주어진 간선의 갯수 N번 이후 N+1번부터 총 M 개의 역 즉 N+M개의 역이 존재한다고 생각을 했습니다.
	- ArrayList[] 배열을 선언하고 N+M+1개의 크기만큼 할당을 진행했습니다.
	- 입력을 받으면서 입력으로 받은 역들에 간선의 정보를 하이퍼튜브의 번호를 추가하고 그 반대 또한 수행을 했습니다.
	- 이렇게 N+m+1번째 하이퍼튜브에 연결되어 있는 역들의 정보가 나오게 됩니다.
	- 그 후 bfs를 통해 최단 경로를 찾는 로직을 수행했습니다. 여기서 핵심은 큐에 들어온 역의 번호가 N보다 큰 것 즉 하이퍼튜브이면 count를 증가시키지 않고 N보다 작거나 같은 값이면 count를 증가시킵니다.
    - N번째 역이 bfs를 통해 나오게 되면 그는 최단 경로이므로 결과를 도출합니다.

시간 복잡도
	- 연결 추가 : O(M*K)
	- BFS : O(N + M + M*K) => 최악의 경우 모든 노드 N+M를 확인하고 모든 이웃을 탐색하는 M*K
	- 전체 시간 복잡도 : O(N + M + M*K)


실행 시간
	- 820ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, K, M;
    static ArrayList<Integer>[] list;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+M+1];
        for(int i=0; i<list.length; i++) {
            list[i] = new ArrayList<>();
        }
        for(int m=0; m<M; m++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int i=0; i<K; i++) {
                int point = Integer.parseInt(st.nextToken());
                list[point].add(N+m+1);
                list[N+m+1].add(point);
            }
        }
        int result = bfs();
        System.out.println(result);

    }
    public static int bfs() {
        Queue<int[]> que = new LinkedList<>();
        boolean[] visit = new boolean[N+M+1];
        que.add(new int[] {1,1});
        visit[1]=true;
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int x = arr[0];
            int count = arr[1];
            if(x==N) {
                return count;
            }
            for(int nx : list[x]) {
                if(visit[nx])
                    continue;
                visit[nx]=true;
                if(nx>N) {
                    que.add(new int[] {nx,count});
                }else {
                    que.add(new int[] {nx,count+1});
                }
            }
        }
        return -1;
    }

}
