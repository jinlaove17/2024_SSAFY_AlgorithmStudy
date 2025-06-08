import java.io.*;
import java.util.*;

public class Main {
    static int N, L, R, X;
    static int[] level;
    static int answer=0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        level = new int[N];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<N; i++) {
            level[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(level);
        find(0,0,0,Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println(answer);
    }

    public static void find(int idx, int cnt, int sum, int max, int min) {
        if(cnt >=2) {
            if(sum >= L && sum <= R && max - min >= X) {
                answer++;
            }
        }
        for(int i=idx; i<N; i++) {
            find(i+1, cnt+1, sum+level[i], Math.max(max, level[i]), Math.min(min, level[i]));
        }
    }
}
