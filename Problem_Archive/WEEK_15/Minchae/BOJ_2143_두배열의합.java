/**
 * 2143 두 배열의 합
 * https://www.acmicpc.net/problem/2143
 * 
 * @author minchae
 * @date 2024. 10. 30.
 * 
 * 문제 풀이
 * 	- A배열 크기만큼 돌면서 부분 배열 구하기 : 맵 사용 -> 부분 배열의 합을 key, 그 합을 가지는 개수를 value
 *  - B배열 돌면서 부분 배열 구하기 -> B배열의 합, (T - B배열의 합)을 맵에서 찾음 => 그 개수를 정답에 포함 시킴
 * 
 * 시간 복잡도
 * O(n^2 + m^2)
 * 
 * 실행 시간
 * 408 ms
 * */

import java.io.*;
import java.util.*;

public class BOJ_2143 {
	
	static int T;
	static int n, m;
	static int[] A, B;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		n = Integer.parseInt(br.readLine());
		A = new int[n];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for (int i = 0; i < n; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		
		m = Integer.parseInt(br.readLine());
		B = new int[m];
		
		st = new StringTokenizer(br.readLine());
		
		for (int i = 0; i < m; i++) {
			B[i] = Integer.parseInt(st.nextToken());
		}

		HashMap<Integer, Integer> cntMap = new HashMap<>();
		
		// 배열 A에서 부분 배열의 합을 구함
		for (int i = 0; i < n; i++) {
			int sum = 0;
			
			for (int j = i; j < n; j++) {
				sum += A[j];
				cntMap.put(sum, cntMap.getOrDefault(sum, 0) + 1);	
			}
		}
		
		long answer = 0; // T가 되는 경우의 수
		
		// 배열 B에서 부분 배열의 합을 구함
		for (int i = 0; i < m; i++) {
			int sum = 0;
			
			for (int j = i; j < m; j++) {
				sum += B[j];
				
				// T에서 합을 뺀 값이 맵에 있는 경우를 확인
				if (cntMap.containsKey(T - sum)) {
					answer += cntMap.get(T - sum);
				}
			}
		}
		
		System.out.println(answer);
	}

}
