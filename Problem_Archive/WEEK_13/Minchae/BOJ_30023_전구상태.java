/**
 * 30023 전구 상태 바꾸기
 * https://www.acmicpc.net/problem/30023
 * 
 * @author minchae
 * @date 2024. 10. 17.
 * 
 * 문제 풀이
 * 	- 모든 색을 빨 or 초 or 파란색으로 만들어보기
 *  - for문을 0부터 (N - 2)까지 진행 -> 3개가 연속해서 바뀌기 때문에 -2까지 진행하는 것
 * 
 * 시간 복잡도
 * 1. 세 개의 색깔에 대해 반복
 * 2. 각 색깔 별로 (N - 2)번 반복
 * => 3 * O(N)
 * 
 * 실행 시간
 * 208 ms
 * */

import java.io.*;
import java.util.*;

public class BOJ_30023 {
	
	static char[] colors = {'R', 'G', 'B'};
	static HashMap<Character, Character> change; // 바꿔야 할 색깔 저장
	
	static int N;
	static char[] bulb;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		bulb = br.readLine().toCharArray();
		
		initMap();
		
		int answer = Integer.MAX_VALUE;
		
		// 최소 횟수 구하기
		for (int i = 0; i < 3; i++) {
			answer = Math.min(answer, changeBulb(colors[i]));
		}

		System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
	}
	
	private static void initMap() {
		change = new HashMap<>();
		
		change.put('R', 'G');
		change.put('G', 'B');
		change.put('B', 'R');
	}
	
	// 특정 색깔로 바꿔보기
	private static int changeBulb(char color) {
		char[] copy = Arrays.copyOf(bulb, N); // 색깔 배열 복사해서 사용
		int cnt = 0;
		
		for (int i = 0; i < N - 2; i++) {
			// 바꿔야 할 색깔이 아닌 경우
			while (copy[i] != color) {
				// 연속한 세 전구의 상태를 바꿈
				copy[i] = change.get(copy[i]);
				copy[i + 1] = change.get(copy[i + 1]);
				copy[i + 2] = change.get(copy[i + 2]);
				
				cnt++; // 바꾼 횟수 증가
			}
		}
		
		// 색깔을 다 바꿨는데 모든 색깔이 일치하지 않는 경우  max value 반환
		for (char c : copy) {
			if (c != color) {
				return Integer.MAX_VALUE;
			}
		}
		
		return cnt;
	}

}
