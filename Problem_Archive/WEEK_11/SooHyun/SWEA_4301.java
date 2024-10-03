/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제는 일반적인 4방 탐색이다. 조금 다른 것은 주변 1칸에 대해서가 아닌 2칸에 대해서
	- 솔직히 이게 왜 D4이고 정답률이 왜 50% 이하인지 모르겠다.
	- 일반적인 4방 탐색 알고리즘을 통해 풀면 충분히 해결 가능하다.
시간 복잡도
	- 모든 행과 열에 대해서 탐색을 진행하므로 시간 복잡도는 O(N * N)이다.

실행 시간
	- 158ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static int[][] board;
    static int N;
    static int M;
    static int[] dx = {0,2,0,-2};
    static int[] dy = {-2,0,2,0};
    static int result;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            board = new int[N][M];
            result=0;
            for(int i=0; i<N; i++) {
                for(int j=0; j<M; j++) {
                    solve(i,j);
                }
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }

    public static void solve(int x, int y) {
        boolean flag=true;
        for(int i=0; i<4; i++) {
            int nx = x+dx[i];
            int ny = y+dy[i];
            if(!range(nx,ny))
                continue;
            if(board[nx][ny]==1) {
                flag=false;
                break;
            }
        }
        if(flag) {
            board[x][y]=1;
            result++;
        }

    }

    public static boolean range(int x, int y) {
        if(x>=0 && x<N && y>=0 && y<M)
            return true;
        return false;
    }
}
