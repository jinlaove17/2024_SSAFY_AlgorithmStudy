/*
문제 접근 아이디어 및 알고리즘
	- 각 위치와 방향별로 레이저가 도달할 때까지 설치해야 하는 거울의 최소 개수를 저장하기 위해 3차원 `cost` 배열을 사용합니다.
	- BFS를 활용해 최소 거울 설치 횟수 계산을 수행했습니다.
	- 우선순위 큐(`PriorityQueue`)를 사용하여 현재까지 설치한 거울의 개수가 가장 적은 상태를 우선적으로 탐색합니다.
	- 현재 상태의 비용이 이미 기록된 비용보다 크면 무시하고 만약 방향이 변경되면 거울 설치가 필요하므로 cost를 증가시켜줍니다.
	- 이후 새로운 비용이 기존 3차원 배열에 저장된 비용보다 작으면 업데이트 후에 큐에 추가합니다.

시간 복잡도 : O(W*H*4*log(W*H*4))=O(N²*log N)
	- 모든 좌표와 방향에 대해 상태를 관리하므로 W*H*4개의 상태가 존재합니다. 각 상태는 우선순위 큐에 삽입 및 삭제되는데 이는 log(W*H*4)의 시간 복잡도를 가집니다. (우선 순위 큐에 삽입 삭제 시 소요되는 시간은 GPT의 도움을 받아서 알아냈습니다.)
	- 모든 가능한 상태를 탐색하므로 총 시간 복잡도는 O(N²*log N)입니다.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    static int start_X;
    static int start_Y;
    static int end_X;
    static int end_Y;
    static char[][] board;
    static int[][][] cost;
    static int W, H;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        board = new char[H][W];
        cost = new int[H][W][4];
        List<int[]> list = new ArrayList<>();
        for(int i=0; i<H; i++) {
            String str = br.readLine().trim();
            for(int j=0; j<W; j++) {
                board[i][j] = str.charAt(j);
                for(int d=0; d<4; d++) {
                    cost[i][j][d] = Integer.MAX_VALUE;
                }
                if(board[i][j]=='C') {
                    list.add(new int[] {i,j});
                }
            }
        }
        start_X = list.get(0)[0];
        start_Y = list.get(0)[1];
        end_X = list.get(1)[0];
        end_Y = list.get(1)[1];

        int result =bfs(start_X,start_Y);
        System.out.println(result);
    }

    public static int bfs(int x, int y) {
        PriorityQueue<Info> que = new PriorityQueue<>();
        for(int i=0; i<4; i++) {
            cost[x][y][i] = 0;
            que.add(new Info(x,y,i,0));
        }

        int cnt=0;
        while(!que.isEmpty()) {
            Info info = que.poll();
            int current_X = info.x;
            int current_Y = info.y;
            int current_dir = info.dir;
            int current_cost = info.cost;
            if(current_X == end_X && current_Y == end_Y) {
                return info.cost;
            }
            if(current_cost > cost[current_X][current_Y][current_dir])
                continue;
            for(int i=0; i<4; i++) {
                int nx = current_X+dx[i];
                int ny = current_Y+dy[i];
                if(!range(nx,ny) || board[nx][ny]=='*')
                    continue;
                int new_cost = current_cost;
                if(i != current_dir) {
                    new_cost++;
                }
                if(cost[nx][ny][i] > new_cost) {
                    cost[nx][ny][i]=new_cost;
                    que.add(new Info(nx,ny,i,new_cost));
                }

            }
        }
        return 0;
    }

    public static boolean range(int x, int y) {
        if(x>=0 && x< H && y>=0 && y<W)
            return true;
        return false;
    }
    static class Info implements Comparable<Info>{
        int x;
        int y;
        int dir;
        int cost;

        public Info(int x, int y, int dir, int cost) {
            this.x=x;
            this.y=y;
            this.dir=dir;
            this.cost=cost;
        }

        @Override
        public int compareTo(Info o) {
            // TODO Auto-generated method stub
            return this.cost-o.cost;
        }
    }
}
