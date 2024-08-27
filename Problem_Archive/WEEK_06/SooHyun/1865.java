import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static final int MULTIPLY=100;
    static int N;
    static double[][] person;
    static boolean[] visit;

    static double result;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            N = Integer.parseInt(br.readLine().trim());
            person = new double[N][N];
            visit = new boolean[N];

            result=0.0;
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=0; j<N; j++) {
                    person[i][j] = Integer.parseInt(st.nextToken())*1.0/100;
                }
            }
            dfs(0, N,1.0);
            String formattedResult = String.format("#%d %.6f%n", t, result * 100);
            sb.append(formattedResult);
        }
        System.out.println(sb.toString());
    }
    public static void dfs(int depth, int N, double cnt) {
        if(cnt <= result)
            return;
        if(depth == N) {
            result = Math.max(result, cnt);
            return;
        }
        for(int i=0; i<N; i++) {
            if(visit[i])
                continue;
            visit[i]=true;
            dfs(depth+1, N, cnt*person[depth][i]);
            visit[i]=false;
        }
    }
}