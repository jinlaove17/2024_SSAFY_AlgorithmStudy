import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static int n, m;
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());
 
        for (int tc = 1; tc <= t; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
 
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
 
            int bit = (1 << n) - 1;
 
            sb.append("#").append(tc).append(" ").append(((m & bit) == bit) ? "ON" : "OFF").append("\n");
        }
 
        System.out.println(sb);
    }
}
