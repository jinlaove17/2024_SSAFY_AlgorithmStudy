import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int A = Integer.parseInt(st.nextToken()) - 1;
            int B = Integer.parseInt(st.nextToken()) - 1;
            int[] ai = new int[N];
            int[] bi = new int[M];
            st = new StringTokenizer(br.readLine().trim());
            for (int i = 0; i < N; i++) {
                ai[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine().trim());
            for (int i = 0; i < M; i++) {
                bi[i] = Integer.parseInt(st.nextToken());
            }
            int[][] customer = new int[K][4];
            st = new StringTokenizer(br.readLine().trim());
            for (int i = 0; i < K; i++) {
                customer[i][0] = Integer.parseInt(st.nextToken());
                customer[i][1] = i + 1;
            }

            int[] wait1 = new int[N];
            for (int i = 0; i < K; i++) {
                int idx = 0;
                for (int j = 0; j < N; j++) {
                    if (wait1[j] <= customer[i][0]) {
                        idx = j;
                        break;
                    }
                    if (wait1[j] < wait1[idx]) {
                        idx = j;
                    }
                }
                customer[i][2] = idx;
                if (wait1[idx] < customer[i][0]) {
                    wait1[idx] = customer[i][0] + ai[idx];
                } else {
                    wait1[idx] += ai[idx];
                }
                customer[i][0] = wait1[idx];
            }

            Arrays.sort(customer, (o1, o2) -> {
                if (o1[0] == o2[0]) {
                    return o1[2] - o2[2];
                }
                return o1[0] - o2[0];
            });

            int[] wait2 = new int[M];
            int result = 0;
            for (int i = 0; i < K; i++) {
                int idx = 0;
                for (int j = 0; j < M; j++) {
                    if (wait2[j] <= customer[i][0]) {
                        idx = j;
                        break;
                    }
                    if (wait2[j] < wait2[idx]) {
                        idx = j;
                    }
                }
                customer[i][3] = idx;
                if (wait2[idx] < customer[i][0]) {
                    wait2[idx] = customer[i][0] + bi[idx];
                } else {
                    wait2[idx] += bi[idx];
                }
                customer[i][0] = wait2[idx];

                if (customer[i][2] == A && customer[i][3] == B) {
                    result += customer[i][1];
                }
            }
            if(result==0) {
                result=-1;
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }
}