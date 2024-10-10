/**
 * 2629 양팔저울
 * https://www.acmicpc.net/problem/2629
 * 
 * 문제 접근 아이디어 및 알고리즘 판단 사유
 * 	- dp로 품
 *  	- N까지 추 무게 그대로, 하나 추가, 반대쪽에 하나 추가 -> 세 가지 경우로 나눠서 재귀 호출 
 * 
 * 실행 시간
 * 104 ms
 * */

import java.io.*;
import java.util.*;

public class BOJ_2629 {
	
	// 추의 최대 개수는 30, 최대 무게 500g -> 15000이 만들 수 있는 최대 무게
	static final int MAX = 15000;
	
	static int N;
	static int[] weight;
	static boolean[][] dp; // 주어진 추로 만들 수 있는 무게 위치에 true 저장

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		weight = new int[N];
		dp = new boolean[N + 1][MAX + 1];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for (int i = 0; i < N; i++) {
			weight[i] = Integer.parseInt(st.nextToken());
		}
		
		solve(0, 0);
		
		int M = Integer.parseInt(br.readLine());
		
		st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < M; i++) {
			int num = Integer.parseInt(st.nextToken());
			
			if (num > MAX) { // 구슬의 무게가 추로 만들 수 있는 최대 무게를 초과하는 경우 N 출력
				sb.append("N ");
			} else {
				sb.append(dp[N][num] ? "Y " : "N ");
			}
		}

		System.out.println(sb.toString());
	}
	
	private static void solve(int idx, int num) {
		if (dp[idx][num]) {
			return;
		}
		
		dp[idx][num] = true;
		
		if (idx == N) {
			return;
		}
		
		solve(idx + 1, num); // 추의 무게 그대로
		solve(idx + 1, num + weight[idx]); // 추 하나 추가
		solve(idx + 1, Math.abs(num - weight[idx])); // 반대쪽 저울에 추를 하나 올리는 것
	}

}
