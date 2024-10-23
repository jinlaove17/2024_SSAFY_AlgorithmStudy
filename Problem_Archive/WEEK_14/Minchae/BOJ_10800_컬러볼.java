/**
 * 10800 컬러볼
 * https://www.acmicpc.net/problem/10800
 * 
 * @author minchae
 * @date 2024. 10. 23.
 * 
 * 문제 풀이
 * 	- 공의 크기를 기준으로 오름차순 정렬
 * 	- 맨 처음에 있는 공을 제외하고는 다른 공을 잡을 수 있음
 * 		- 이때 같은 색을 가지는 공의 합 저장
 * 		- 잡을 수 있는 모든 공의 크기 합에서 같은 색을 가지는 공의 합을 뺌
 * 
 * 시간 복잡도
 * 정렬 -> O(NlogN)
 * 잡을 수 있는 공 확인 -> O(N)
 * => O(NlogN)
 * 
 * 실행 시간
 * 1092 ms
 * */

import java.io.*;
import java.util.*;

public class Main2 {
	
	static class Ball implements Comparable<Ball> {
		int n;
		int c;
		int s;
		
		public Ball(int n, int c, int s) {
			this.n = n;
			this.c = c;
			this.s = s;
		}

		@Override
		public int compareTo(Ball o) {
			return Integer.compare(this.s, o.s);
		}
	}
	
	static int N;
	
	static Ball[] balls;
	static int[] colors; // 색깔별로 공의 크기 합 저장 -> 누적합
	static int[] answer; // 잡을 수 있는 공의 합

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		balls = new Ball[N];
		colors = new int[N + 1];
		answer = new int[N];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int c = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			
			balls[i] = new Ball(i, c, s);
		}
		
		Arrays.sort(balls);
		
		catchBall();
		
		StringBuilder sb = new StringBuilder();
		
		for (int sum : answer) {
			sb.append(sum).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	private static void catchBall() {
		int idx = 0;
		int sum = 0;
		
		for (int i = 0; i < N; i++) {
			Ball cur = balls[i];
			
			while (cur.s > balls[idx].s) {
				sum += balls[idx].s;
				colors[balls[idx].c] += balls[idx].s; // idx번의 공의 색깔 배열에 크기 더함
				
				idx++;
			}
			
			answer[cur.n] = sum - colors[cur.c]; // 현재까지의 합에서 같은 색인 공의 크기 합을 뺌
		}
	}

}
