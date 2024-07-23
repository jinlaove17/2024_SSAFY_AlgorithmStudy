import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for(int t=1; t<=T; t++) {
            int N = Integer.parseInt(br.readLine().trim());
            char[][] map = new char[N][N];
            int[] width = new int[N];
            int[] height = new int[N];
            for(int i=0; i<N; i++) {
                String str = br.readLine();
                for(int j=0; j<N; j++) {
                    map[i][j]=str.charAt(j);
                    if(map[i][j]=='B') {
                        width[i]++;
                        height[j]++;
                    }
                }
            }

            int result=0;
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    int sum = width[i]+height[j];
                    if(sum==0)
                        continue;
                    if(map[i][j]=='B' && (sum-1) %2 ==1) {
                        result++;
                        continue;
                    }
                    if(map[i][j]=='W' && sum %2 ==1) {
                        result++;
                        continue;
                    }
                }
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }
}