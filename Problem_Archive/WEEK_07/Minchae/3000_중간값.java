/**
 * D4 3000. 중간값 구하기
 * 109,108 kb  1,408 ms
 * 
 * @author minchae
 * @date 2024. 8. 30.
 * */

import java.util.*;
import java.io.*;

public class Solution {
	
	static int N, A;
	static PriorityQueue<Integer> minHeap;
	static PriorityQueue<Integer> maxHeap;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			
			minHeap = new PriorityQueue<>(); // 중간값보다 큰 값을 저장
			maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // 중간값을 포함해 작은 수을 저장
			
			maxHeap.add(A); // 최대 힙의 루트에 중간값이 저장되게 함
			
			int answer = 0;
			
			while (N-- > 0) {
				st = new StringTokenizer(br.readLine());
				
				int X = Integer.parseInt(st.nextToken());
				int Y = Integer.parseInt(st.nextToken());
				
				// 일단 값을 넣음
				minHeap.add(X);
				maxHeap.add(Y);
				
				if (!minHeap.isEmpty() && !maxHeap.isEmpty()) {
					// min에는 중간값보다 큰 값만 저장되어야 하는데 작은 경우 swap
					if (maxHeap.peek() >= minHeap.peek()) {
						// 값 바꾸기
						int tmp = maxHeap.poll();
						
						maxHeap.add(minHeap.poll());
						minHeap.add(tmp);
					}
				}
				
				answer  = (answer + maxHeap.peek()) % 20171109;
			}
			
			System.out.println("#" + t + " " + answer);
		}
	}

}
