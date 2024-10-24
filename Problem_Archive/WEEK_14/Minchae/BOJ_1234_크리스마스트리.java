/**
 * 1234 크리스마스 트리
 * https://www.acmicpc.net/problem/1234
 * 
 * @author minchae
 * @date 2024. 10. 24.
 * 
 * 문제 풀이
 * 	- 빨, 초, 파 장남감을 1개 or 2개 or 3개 사용하는 경우를 나누어서 계산
 *  - 레벨 10까지의 경우의 수를 미리 구해놓음
 *  - 특정 레벨이 세 가지의 장남감을 사용한다면
 *  	(해당 레벨 경우의 수 / 장남감 1 개수 경우의 수 * 장남감 2 개수 경우의 수 * 장남감 3 개수 경우의 수)로 계산
 * 
 * 시간 복잡도
 *  calc : O(N)
 *  solve : O(7^N)
 *  comb : O(1)
 *  => O(7^N)
 * 
 * 실행 시간
 * 264 ms
 * */

import java.util.*;
import java.io.*;

public class Main {

	static int N, red, green, blue;
	
	static long[] num; // 경우의 수 미리 계산한 값 저장
	static long answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		red = Integer.parseInt(st.nextToken());
		green = Integer.parseInt(st.nextToken());
		blue = Integer.parseInt(st.nextToken());
		
		num = new long[11];
		
		for (int i = 1; i <= 10; i++) {
			num[i] = calc(i);
		}

		solve(1, 0, 0, 0, 1);

		System.out.println(answer);
	}
	
	// 경우의 수 미리 계산
	private static int calc(int n) {
		int result = 1;
		
		for (int i = 1; i <= n; i++) {
			result *= i;
		}
		
		return result;
	}

	// N 레벨까지 장남감을 놓음
	private static void solve(int depth, int r, int g, int b, long cur) {
		// 장남감을 다 쓴 경우 종료
		if (r > red || g > green || b > blue) {
			return;
		}

		// 목표 레벨까지 도달한 경우 현재까지의 경우의 수를 더해줌
		if (depth == N + 1) {
			answer += cur;
			return;
		}

		// 한 가지 색깔
		solve(depth + 1, r + depth, g, b, cur);
		solve(depth + 1, r, g + depth, b, cur);
		solve(depth + 1, r, g, b + depth, cur);

		// 두 가지 색깔
		if (depth % 2 == 0) {
			int cnt = depth / 2; // 색마다 칠해지는 개수
			long tmp = comb(depth, cnt, 0);
			
			solve(depth + 1, r + cnt, g + cnt, b, cur * tmp);
			solve(depth + 1, r, g + cnt, b + cnt, cur * tmp);
			solve(depth + 1, r + cnt, g, b + cnt, cur * tmp);
		}

		// 세 가지 색깔
		if (depth % 3 == 0) {
			int cnt = depth / 3;
			long tmp = comb(depth, cnt, 1);
			
			solve(depth + 1, r + cnt, g + cnt, b + cnt, cur * tmp);
		}
	}
	
	// 해당 레벨의 경우의 수에 사용하는 장난감 개수의 경우의 수를 나눠줌
	private static long comb(int depth, int cnt, int type) {
		if (type == 0) {
			return num[depth] / (num[cnt] * num[cnt]);
		} else {
			return num[depth] / (num[cnt] * num[cnt] * num[cnt]);
		}
	}

}
