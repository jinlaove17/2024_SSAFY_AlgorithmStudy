import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CollectionCertStoreParameters;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution {
    static final int MOD = 20171109;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            int N = Integer.parseInt(st.nextToken());
            int A = Integer.parseInt(st.nextToken());
            PriorityQueue<Integer> que1 = new PriorityQueue<>();
            PriorityQueue<Integer> que2 = new PriorityQueue<>(Collections.reverseOrder());
            int sum=0;
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                int n1 = Integer.parseInt(st.nextToken());
                int n2 = Integer.parseInt(st.nextToken());
                if(n1 > A) {
                    que1.add(n1);
                }else {
                    que2.add(n1);
                }
                if(n2 > A) {
                    que1.add(n2);
                }else {
                    que2.add(n2);
                }

                if(que1.size() > que2.size()) {
                    int num = que1.poll();
                    que2.add(A);
                    A=num;
                }else if(que1.size() < que2.size()) {
                    int num = que2.poll();
                    que1.add(A);
                    A=num;
                }
                sum=(sum+A%MOD)%MOD;
            }
            sb.append("#"+t+" "+sum+"\n");
        }
        System.out.println(sb.toString());
    }

}
