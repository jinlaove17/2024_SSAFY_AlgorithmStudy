/**
 * 22251 빌런 호석
 * https://www.acmicpc.net/problem/22251
 *
 * @author minchae
 * @date 2025. 1. 30.
 *
 * 문제 풀이
 * - 문제 풀 때 뭔가 정해진 그림 같은 게 있는 경우 그걸 미리 저장하는 배열을 만듦
 *   그래서 0부터 9까지 켜져있는 불의 위치를 표시한 배열을 만들어 놓았다.
 * - 먼저 현재 층수인 X를 한 자릿수로 나누기 -> 디스플레이에 표시되는 숫자로 변경
 * - 1부처 N까지 확인하면서 해당 층으로 숫자 반전시키기 -> 반전되는 개수가 P를 초과하는 경우 변경할 수 없음
 *
 * 시간 복잡도
 * O(N * K)
 *
 * 실행 시간
 * 220 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static int[][] light = {
			{1, 1, 1, 0, 1, 1, 1}, // 0
			{0, 0, 1, 0, 0, 0, 1}, // 1
			{0, 1, 1, 1, 1, 1, 0}, // 2
			{0, 1, 1, 1, 0, 1, 1}, // 3
			{1, 0, 1, 1, 0, 0, 1}, // 4
			{1, 1, 0, 1, 0, 1, 1}, // 5
			{1, 1, 0, 1, 1, 1, 1}, // 6
			{0, 1, 1, 0, 0, 0, 1}, // 7
			{1, 1, 1, 1, 1, 1, 1}, // 8
			{1, 1, 1, 1, 0, 1, 1}, // 9
	};
	
	static int N, K, P, X;
	static int[] num;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		num = change(X); // 현재 층을 디스플레이에 표시되는 숫자로 나타내기

		System.out.println(check());
	}
	
	// 한 자리씩 나누기
	private static int[] change(int x) {
		int[] result = new int[K];
		
		for (int i = K - 1; i >= 0; i--) {
			result[i] = x % 10;
			x /= 10;
		}
		
		return result;
	}
	
	// 1 ~ N층까지 확인 -> 반전시킬 수 있는 숫자인지 확인하고 경우의 수 계산
	private static int check() {
		int cnt = 0;
		
		for (int i = 1; i <= N; i++) {
			// 현재 층과 같은 층은 넘어감
			if (i == X) {
				continue;
			}
			
			if (reverse(i)) {
				cnt++;
			}
		}
		
		return cnt;
	}
	
	// 최대 P개까지 반전시키면서 숫자를 반전시킬 수 있는지 확인
	private static boolean reverse(int cur) {
		int[] target = change(cur); // 바꿀 층을 한 자리씩 나눠서 디스플레이에 표시되는 숫자로 변경
		
		int cnt = 0;
		
		for (int i = 0; i < K; i++) {
			for (int j = 0; j < 7; j++) {
				// 서로 켜진 위치가 다른 경우 개수 증가
				if (light[num[i]][j] != light[target[i]][j]) {
					cnt++;
					
					// 반전시킨 개수가 P를 초과하는 경우 반전시킬 수 없는 것
					if (cnt > P) {
						return false;
					}
				}
			}
		}
		
		return true;
	}

}
