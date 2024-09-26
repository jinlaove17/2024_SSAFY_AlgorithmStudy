/*
문제 접근 아이디어 및 알고리즘
	- 해당 문제의 핵심은 8방 탐색 시 모두 지뢰가 없는 칸을 우선적으로 터트리는 것이 핵심이였습니다.
	- 지뢰가 아닌 칸 주변에 지뢰가 몇개인지 파악하기 위해 count 이차원 배열을 할당해 8방 탐색을 진행했습니다.
	- 후에 주변에 지뢰가 존재하지 않는 즉 count가 0인 칸부터 우선적으로 선택을 하면 주변 8방 또한 자동으로 클릭이 되는 것을 확인합니다.
	- 이후 지뢰가 아닌 칸 중 클릭되지 않았고 주변에 적어도 1개의 지뢰가 존재하는 경우 개별적으로 터져야됨으로써 구현을 마칩니다.

시간 복잡도 : O(N^2)
	- 지뢰 주변 개수 세기: 모든 칸에 대해 8방향 탐색 O(N^2)
	- BFS 탐색 : 최악의 조건 각 칸을 1번씩 방문 O(N^2)
	- 남은 칸 클릭: 모든 칸을 한 번씩 확인 O(N^2)
	=> 전체 : O(N^2)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    static int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
    static int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
    static char[][] board;
    static int[][] count;
    static boolean[][] visited;
    static int N;
    static int result;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine().trim());
        for(int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine().trim());
            result = 0;
            board = new char[N][N];
            count = new int[N][N];
            visited = new boolean[N][N];
            for(int i = 0; i < N; i++) {
                String str = br.readLine().trim();
                for(int j = 0; j < N; j++) {
                    board[i][j] = str.charAt(j);
                }
            }
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(board[i][j] == '.') {
                        countBomb(i, j);
                    }
                }
            }
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(board[i][j] == '.' && count[i][j] == 0 && !visited[i][j]) {
                        result++;
                        bfs(i, j);
                    }
                }
            }
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(board[i][j] == '.' && !visited[i][j]) {
                        result++;
                        visited[i][j] = true;
                    }
                }
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }

    public static void countBomb(int x, int y) {
        for(int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(range(nx, ny)) {
                if(board[nx][ny] == '*') {
                    count[x][y]++;
                }
            }
        }
    }

    public static void bfs(int i, int j) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {i, j});
        visited[i][j] = true;
        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            if(count[x][y] == 0) {
                for(int d = 0; d < 8; d++) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];
                    if(range(nx, ny) && board[nx][ny] == '.' && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        if(count[nx][ny] == 0) {
                            queue.add(new int[] {nx, ny});
                        }
                    }
                }
            }
        }
    }

    public static boolean range(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}
