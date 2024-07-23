import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    static int[] dx = {0,0,-1};
    static int[] dy = {-1,1,0};
    static final int N = 100;
    static final int T = 10;
    static int answer = 0;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for(int t=0; t<T; t++) {
            int test_case = Integer.parseInt(br.readLine());
            int[][] ladder = new int[N][N];
            boolean[][] visit = new boolean[N][N];
            int start_X=-1;
            int start_Y=-1;
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j=0; j<N; j++) {
                    ladder[i][j] =Integer.parseInt(st.nextToken());
                    if(ladder[i][j]==2) {
                        start_X=i;
                        start_Y=j;
                    }
                }
            }
            visit[start_X][start_Y]=true;
            dfs(start_X, start_Y, ladder, visit);
            sb.append("#"+test_case+" "+answer+"\n");
        }
        System.out.println(sb.toString());
    }
    public static void dfs(int x, int y, int[][] ladder, boolean[][] visit) {
        while(true) {
            if(x==0) {
                answer=y;
                break;
            }
            for(int i=0; i<3; i++) {
                int nx = x+dx[i];
                int ny = y+dy[i];
                if(nx <0 || nx >=N || ny <0 || ny>=N || visit[nx][ny] || ladder[nx][ny] ==0) {
                    continue;
                }
                visit[nx][ny]=true;
                x=nx;
                y=ny;
            }
        }
    }
}