import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    static int N;
    static int K;
    static int[] house;
    static int[] distance;
    static boolean[] visit;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            house = new int[N];
            distance = new int[N-1];
            st = new StringTokenizer(br.readLine().trim());
            for(int i=0; i<N; i++) {
                house[i] = Integer.parseInt(st.nextToken());
            }
            for(int i=0; i<N-1; i++) {
                distance[i] = house[i+1]-house[i];
            }
            int result=0;
            Arrays.sort(distance);
            for(int i=0; i<N-K; i++) {
                result+=distance[i];
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }

}