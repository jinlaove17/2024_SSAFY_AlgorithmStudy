import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
    static int R, C;
    static char[][] presents;
    static final int[] dx = {-1,1,0,0};
    static final int[] dy = {0,0,-1,1};
    static boolean[] visit;
    static int result;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            presents = new char[R][C];
            result=0;
            for(int i=0; i<R; i++) {
                String str = br.readLine().trim();
                for(int j=0; j<C; j++) {
                    presents[i][j] = str.charAt(j);
                }
            }
            visit=new boolean[26];
            visit[presents[0][0]-'A']=true;
            dfs(0,0,1);
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }

    public static void dfs(int x, int y, int cnt) {
        result = Math.max(cnt, result);
        for(int i=0; i<4; i++) {
            int nx = x+dx[i];
            int ny = y+dy[i];
            if(!range(nx,ny) || visit[presents[nx][ny] -'A']) {
                continue;
            }
            visit[presents[nx][ny] -'A']=true;
            dfs(nx,ny,cnt+1);
            visit[presents[nx][ny] -'A']=false;
        }
    }

    public static boolean range(int x, int y) {
        if(x>=0 && x < R && y>=0 && y<C)
            return true;
        return false;
    }
}