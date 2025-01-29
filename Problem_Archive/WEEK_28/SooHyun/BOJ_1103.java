/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 이 문제를 처음 봤을 때 단순히 DFS를 통해서 풀 수 있는 문제라고 생각하고 단순히 DFS와 백트래킹을 통해 구현했으나 시간 초과가 발생했습니다.
    - 이에 따라 문제를 다시 읽어보았고 최대로 움직일 수 있는 경우는 무한대 즉 특정 경로가 사이클을 일으키는 경우라고 생각을 해 flag라는 boolean 변수를 통해서 특정 경로를 탐색하면서 이미 방문한 곳을 또 방문하게 되면 그 경우에 사이클이라 판단해 dfs를 종료하는 조건을 추가했습니다.
    - 또한 문제의 핵심은 최대한 많이 움직이는 것입니다. 즉 특정 지점에 대해서 어떤 경로를 통해 도달을 하는데 만약 다른 경로를 통해서 특정 구간에 도달하는 횟수가 더 많은 것이 존재하면 굳이 탐샐을 이어나갈 필요가 없다고 판단했습니다.
    - 이를 위해 dp라는 이차원 배열 즉 몇 번만에 해당 위치에 도달하는지를 저장하는 목적으로 배열을 선언하고 위에 말한 것을 체크하는 조건을 구현했습니다.
    - 이렇게 구현을 하고 나서는 flag라는 boolean 변수를 통해서 사이클을 판단하고 최대로 움직일 수 있는 경우를 구현했습니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N * M) (N : 행의 개수, M : 열의 개수)
    - DFS 탐색 : O(N * M) (최악의 경우 모든 칸을 탐색)
    - 전체 시간복잡도: O(N * M)

실행 시간
   - 120ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static char[][] board;
    static boolean[][] visit;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    static int[][] dp;
    static int max = Integer.MIN_VALUE;
    static boolean flag = false;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new char[N][M];
        visit = new boolean[N][M];
        dp = new int[N][M];
        for(int i=0; i<N; i++) {
            String str = br.readLine().trim();
            for(int j=0; j<str.length(); j++) {
                char ch = str.charAt(j);
                board[i][j]=ch;
            }
        }
        visit[0][0]=true;
        dfs(0,0,1);
        if(flag) {
            max=-1;
        }
        System.out.println(max);
    }

    public static void dfs(int x, int y, int cnt) {
        if(cnt > max) {
            max = cnt;
        }
        dp[x][y] = cnt;
        for(int i=0; i<4; i++) {
            int num = board[x][y] - '0';
            int nx = x+(dx[i]*num);
            int ny = y+(dy[i]*num);
            if(!range(nx,ny) || board[nx][ny]=='H')
                continue;
            if(visit[nx][ny]) {
                flag=true;
                return;
            }
            if(dp[nx][ny] > cnt)
                continue;
            visit[nx][ny]=true;
            dfs(nx,ny,cnt+1);
            visit[nx][ny]=false;
        }
    }
    public static boolean range(int x, int y) {
        if(x <0 || x>=N || y<0 || y>=M)
            return false;
        return true;
    }
}
