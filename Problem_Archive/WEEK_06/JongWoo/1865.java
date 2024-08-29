import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static final double DIV = 1000000.0;
     
    static int n;
    static int[][] percents;
    static boolean[] isSelected;
    static double answer;
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());
 
        for (int tc = 1; tc <= t; ++tc) {
            n = Integer.parseInt(br.readLine().trim());
            percents = new int[n][n];
            isSelected = new boolean[n];
 
            for (int r = 0; r < n; ++r) {
                StringTokenizer st = new StringTokenizer(br.readLine().trim());
 
                for (int c = 0; c < n; ++c) {
                    percents[r][c] = Integer.parseInt(st.nextToken());
                }
            }
 
            answer = 0.0;
            dfs(0, 1.0);
            sb.append("#").append(tc).append(" ").append(String.format("%.6f\n", Math.round(100.0 * answer * DIV) / DIV));
        }
 
        System.out.println(sb);
    }
 
    private static void dfs(int depth, double per) {
        if (depth == n) {
            answer = Math.max(answer, per);
            return;
        }
        else if ((answer >= 100.0) || (per <= answer)) {
            return;
        }
         
        for (int i = 0; i < n; ++i) {
            if (isSelected[i]) {
                continue;
            }
 
            if (percents[depth][i] == 0) {
                continue;
            }
             
            isSelected[i] = true;
            dfs(depth + 1, per * (percents[depth][i] / 100.0));
            isSelected[i] = false;
        }
    }
}
