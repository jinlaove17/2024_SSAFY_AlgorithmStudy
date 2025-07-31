/**
 * 14698 전생했더니 슬라임 연구자였던 건에 대하여 (Hard)
 * https://www.acmicpc.net/problem/14698
 *
 * @author minchae
 * @date 2025. 7. 31.
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static final int MOD = 1_000_000_007;
	static int N;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int T = Integer.parseInt(br.readLine());
        
        while (T-- > 0) {
        	int N = Integer.parseInt(br.readLine());
        	
        	PriorityQueue<Long> pq = new PriorityQueue<>();
        	
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	for (int i = 0; i < N; i++) {
        		pq.add(Long.parseLong(st.nextToken()));
        	}
        	
        	long result = 1;
        	
        	while (pq.size() > 1) {
        	    long mul = pq.poll() * pq.poll();
        		
        		result *= (mul % MOD);
        		result %= MOD;
        		
        		pq.add(mul);
        	}
        	
        	System.out.println(result);
        }
        
	}

}
