import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static int[] dx = {1,1,-1,-1};
    static int[] dy = {1,-1,-1,1};
    static int result=0;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            int N = Integer.parseInt(br.readLine().trim());
            int[][] board = new int[N][N];
            boolean[][] visit = new boolean[N][N];
            result=0;
            boolean[] dessert = new boolean[101];
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=0; j<N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    visit[i][j]=true;
                    dessert[board[i][j]]=true;
                    int start_x=i;
                    int start_y=j;
                    dfs(1,i,j,start_x,start_y,0,board, visit, dessert);
                    visit[i][j]=false;
                    dessert[board[i][j]]=false;
                }
            }
            if(result==0) {
                result=-1;
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }
    public static void dfs(int cnt, int current_x, int current_y, int start_x, int start_y, int dir, int[][]board, boolean[][]visit, boolean[] dessert) {
        for(int i=dir; i<4; i++) {
            int next_x = current_x+dx[i];
            int next_y = current_y+dy[i];
            if(!range(next_x,next_y, board))
                continue;
            if(next_x==start_x && next_y==start_y && cnt >2) {
                result = Math.max(result, cnt);
                return;
            }
            if(!visit[next_x][next_y] && !dessert[board[next_x][next_y]]) {
                visit[next_x][next_y]=true;
                dessert[board[next_x][next_y]]=true;
                dfs(cnt+1, next_x, next_y,start_x,start_y,i, board,visit, dessert);
                visit[next_x][next_y]=false;
                dessert[board[next_x][next_y]]=false;
            }
        }
    }
    public static boolean range(int x, int y, int[][] board) {
        if(x>=0 && x<board.length && y>=0 && y<board.length)
            return true;
        return false;
    }
}