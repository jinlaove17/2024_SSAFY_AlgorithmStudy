import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Solution {
    static HashSet<String> set = new HashSet<>();
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            String[][] board = new String[4][4];
            set.clear();
            for(int i=0; i<4; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=0; j<4; j++) {
                    board[i][j] = st.nextToken();
                }
            }
            for(int i=0; i<4; i++) {
                for(int j=0; j<4; j++) {
                    dfs(i,j,0,7,"",board);
                }
            }
            sb.append("#"+t+" "+set.size()+"\n");
        }
        System.out.println(sb.toString());
    }

    public static void dfs(int x, int y, int depth, int N, String str, String[][] board) {
        if(depth == N) {
            set.add(str);
            return;
        }
        str+=board[x][y];
        for(int i=0; i<4; i++) {
            int nx = x+dx[i];
            int ny= y+dy[i];
            if(!range(nx,ny,4))
                continue;
            dfs(nx,ny,depth+1,N, str, board);
        }
    }

    public static boolean range(int x, int y, int N) {
        if(x <0 || x >=N || y <0 || y>= N)
            return false;
        return true;
    }
}