/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에서 게임판의 칸의 갯수가 100개로 고정되어 있었습니다. 따라서 board = new int[101]로 초기화했습니다.
	- 그리고 board에 뱀이나 사다리 칸인 경우에 이동해야하는 칸의 번호를 저장하고 그렇지 않으면 자기 자신의 번호를 저장시킵니다.
	- 이미 bfs를 통해서 전형적인 최단 경로 풀이법을 적용해서 정답을 도출했습니다.

시간 복잡도
	- 초기화 : O(N + M)
	- BFS : 최악의 경우 게임판 모든 칸에 대해서 6개의 주사위를 모두 굴리는 경우여서 O(600)입니다. 그러나 100개의 칸이 고정되어 있어 이 또한 상수 O(1)입니다.
	- 전체 시간 복잡도 : O(N + M)


실행 시간
	- 104ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] board;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[101];
        for(int i=1; i<=100; i++) {
            board[i]=i;
        }
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            board[from]=to;
            //System.out.println(from+" "+to);
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            board[from]=to;
            //System.out.println(from+" "+to);
        }
        int result = bfs();
        System.out.println(result);
    }

    public static int bfs() {
        Queue<int[]> que = new LinkedList<>();
        boolean[] visit = new boolean[101];
        visit[1]=true;
        que.add(new int[] {1,0});
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int current_x = arr[0];
            int cnt = arr[1];
            if(current_x==100) {
                return cnt;
            }
            for(int i=1; i<=6; i++) {
                int nx = current_x+i;
                if(nx >100)
                    continue;
                int point = board[nx];
                if(point >100)
                    continue;
                if(visit[nx] || visit[point])
                    continue;
                visit[nx]=true;
                visit[point]=true;
                que.add(new int[] {point,cnt+1});
            }
        }
        return -1;
    }

}
