import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int T, N;
    static final long MOD = 1000000007;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        T = Integer.parseInt(st.nextToken());
        for(int t=0; t<T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            PriorityQueue<Long> pq = new PriorityQueue<>();
            N = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine().trim());
            for(int i=0; i<N; i++) {
                pq.add(Long.parseLong(st.nextToken()));
            }
            long result=1;
            while(pq.size()>1) {
                long e1 = pq.poll();
                long e2 = pq.poll();
                result *= (e1*e2) % MOD;
                result %= MOD;
                pq.add(e1*e2);
            }
            sb.append(result+"\n");
        }
        System.out.println(sb.toString());
    }

}
