/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 전형적인 다익스트라 알고리즘 문제입니다. Info라는 객체를 선언하고 도착지와 가중치를 저장합니다. 그리고 가중치로 오름차순 정렬을 가능하게 합니다.
    - 이후에는 다익스타라를 그대로 수행하면 됩니다. 방문 배열과 가중치의 최소값을 저장하기 위한 두 개의 배열을 선언하고 다익스트라 알고리즘을 짠 find함수를 수행합니다.
    - 마지막에 도착지에 대한 가중치 값을 출력합니다.

시간 복잡도
    - 입력 처리 : O(M)
    - 다익스트라 알고리즘(find 함수) : O((N + M) * logN)
    - 전체 시간복잡도 : O((N + M) * logN)

실행 시간
   - 392ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static List<Info>[] list;
    static final int MAX = 50000001;
    static int[] dijkstra;
    static boolean[] visit;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+1];
        dijkstra = new int[N+1];
        visit = new boolean[N+1];
        Arrays.fill(dijkstra, MAX);
        for(int i=0; i<=N; i++) {
            list[i] = new ArrayList<>();
        }
        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            list[s].add(new Info(e,c));
            list[e].add(new Info(s,c));
        }
        find();
        System.out.println(dijkstra[N]);
    }

    public static void find() {
        PriorityQueue<Info> que = new PriorityQueue<>();
        que.add(new Info(1,0));
        dijkstra[1]=0;
        while(!que.isEmpty()){
            Info info = que.poll();

            if(!visit[info.end])
                visit[info.end] = true;
            else
                continue;
            for(int i=0; i<list[info.end].size(); i++){
                Info next = list[info.end].get(i);
                if(dijkstra[next.end] > dijkstra[info.end] + next.cost) {
                    dijkstra[next.end] = dijkstra[info.end] + next.cost;
                    que.add(new Info(next.end, dijkstra[next.end]));
                }
            }
        }
    }

    static class Info implements Comparable<Info>{
        int end;
        int cost;

        public Info(int end, int cost) {
            this.end =end;
            this.cost = cost;
        }


        @Override
        public int compareTo(Info o) {
            return this.cost- o.cost;
        }
    }
}