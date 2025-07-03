/**
 * 21773 가희와 프로세스 1
 * https://www.acmicpc.net/problem/21773
 *
 * @author minchae
 * @date 2025. 7. 3.
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static class Process implements Comparable<Process> {
		int id;
		int time;
		int priority;
		
		public Process(int id, int time, int priority) {
			this.id = id;
			this.time = time;
			this.priority = priority;
		}

		@Override
		public int compareTo(Process o) {
			if (this.priority != o.priority) {
				return o.priority - this.priority;
			}
			
			return this.id - o.id;
		}
	}
	
	static int T, n;
	static PriorityQueue<Process> pq;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        T = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        
        pq = new PriorityQueue<>();
        
        for (int i = 0; i < n; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int A = Integer.parseInt(st.nextToken());
        	int B = Integer.parseInt(st.nextToken());
        	int C = Integer.parseInt(st.nextToken());
        	
        	pq.add(new Process(A, B, C));
        }

        StringBuilder sb = new StringBuilder();
        
        while (T-- > 0) {
        	if (pq.isEmpty()) {
        		break;
        	}
        	
        	Process cur = pq.poll();
        	
        	sb.append(cur.id).append("\n");
        	
        	cur.priority--; // 나머지 증가시키는 대신 현재 프로세스 감소
        	cur.time--;
        	
        	if (cur.time > 0) {
        		pq.add(cur);
        	}
        }
        
        System.out.println(sb.toString());
	}

}
