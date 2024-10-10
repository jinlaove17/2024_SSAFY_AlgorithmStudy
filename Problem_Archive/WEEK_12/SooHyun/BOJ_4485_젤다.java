/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 링크는 잃는 금액을 최소로 해야하므로 dfs보다 bfs를 사용하는 것이 올바르다고 생각했습니다.
	- 해당 위치의 좌표값과 잃는 링크 갯수를 저장하는 Node 클래스를 생성하고 정렬 조건을 잃는 링크가 작은 순으로 오름차순 정렬을 하고자 했습니다.
	- 그리고 visit 2차원 배열을 만들어서 방문 체크를 할 필요가 없었습니다.
	- 초기위치에 대해서 우선 순위 큐에 좌표값과 잃는 링크의 갯수를 저장하고 꺼낸 값이 (N-1,N-1)가 가장 먼저 도달하게 되는 것이 도달하는 경우의 수 중 링크를 잃는 갯수가 최소가 되는 것이였습니다.

시간 복잡도
	- 우선순위 큐를 사용한 BFS 탐색: 최악의 경우 N*N개의 좌표를 모두 확인해야하므로 O(N ^ 2)입니다.
	- Priority Queue에 삽입/삭제: add와 poll 시 log의 시간복잡도를 가지는데 최악의 경우 O(log N^2) 즉 O(log N)입니다.
	- 최종 시간복잡도는 : O(N ^ 2 * log N)입니다.

실행 시간
	- 212ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static final String RESULT_STR = "Problem ";
    static int N;
    static int[][] board;
    static int[][] result;
    static boolean[][] visit;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int num=1;
        while(true) {
            N = Integer.parseInt(br.readLine().trim());
            if(N==0)
                break;
            board = new int[N][N];
            result = new int[N][N];
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=0; j<N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    result[i][j] = Integer.MAX_VALUE;
                }
            }
            int cost = bfs();
            sb.append(RESULT_STR+ num+": "+cost+"\n");
            num++;
        }
        System.out.println(sb.toString());
    }
    public static int bfs() {
        PriorityQueue<Node> que = new PriorityQueue<>();
        que.add(new Node(0,0,board[0][0]));
        result[0][0] = board[0][0];
        while(!que.isEmpty()) {
            Node node = que.poll(); // que에 맨 앞에 위치한 값은 현재 방문한 것 중 가중치가 최소인 것이다.
            int cur_x = node.x;
            int cur_y = node.y;
            int cur_cost = node.cost;
            if(cur_x ==N-1 && cur_y==N-1) {
                return cur_cost;
            }
            for(int i=0; i<4; i++) {
                int nx = cur_x+dx[i];
                int ny = cur_y+dy[i];
                if(!range(nx,ny))
                    continue;
                if(cur_cost+board[nx][ny] < result[nx][ny]) { // 현재 링크값보다 작은 값인 경우 갱신해줍니다.
                    result[nx][ny]=cur_cost+board[nx][ny];
                    que.add(new Node(nx, ny, result[nx][ny]));
                }
            }
        }
        return -1;

    }
    public static boolean range(int x, int y) {
        if(x>=0 && x<N && y>=0 && y<N)
            return true;
        return false;
    }
    static class Node implements Comparable<Node>{ // 좌표 값과 해당 위치에서 잃는 링크의 갯수를 저장하는 클래스를 선언합니다.
        int x;
        int y;
        int cost;

        public Node(int x, int y, int cost) {
            this.x=x;
            this.y=y;
            this.cost=cost;
        }

        @Override
        public int compareTo(Node o) { // 우선순위 큐에 넣었을 때 가중치가 작은 것으로 오름차순 정렬되게 했습니다.
            // TODO Auto-generated method stub
            return this.cost-o.cost;
        }
    }
}
