/**
 * 1304 지역
 * https://www.acmicpc.net/problem/1304
 * 
 * @author minchae
 * @date 2024. 10. 30.
 * 
 * 문제 풀이
 * 	- 도시 A보다 도시 B를 연결하는 일반 도로가 주어질 때 B가 더 크다면 생각할 필요가 없음
 * 		B가 더 작은 경우에만 확인하면 됨
 * 		-> A와 B가 다른 지역일 때 B가 더 크다면 A에서 B로 가는 경로 있음
 * 		   하지만 B에서 A로 가는 경로가 있으면 안되기 때문에 A가 더 클 때만 확인하는 것
 * 	- 1부터 N까지 돌면서 i로 나누어 떨어지는 경우 지역을 만들 수 있는지 확인
 * 	- 같은 개수로 만들어지는 경우 현재 도시에서 다른 일반 도로로 연결되는 도시 확인
 * 		연결되는데 다른 지역인 경우 지역끼리 경로가 생기는 것이기 때문에 false 반환
 * 
 * 시간 복잡도
 * O(N + M)
 * 
 * 실행 시간
 * 116 ms
 * */

import java.io.*;
import java.util.*;

public class Main {
	
	static int N, M;
	static ArrayList<Integer>[] list;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N + 1];
		
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int S = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			
			// 시작 도시가 더 큰 경우에만 저장
			// 끝 도시가 더 큰 경우는 볼 필요 없음
			if (S > E) {
				list[S].add(E); // 일반 도로 추가	
			}
		}
		
		int answer = 1;
		
		// 같은 수의 도시로 지역을 나누려면 N의 약수를 구하면 됨
		for (int i = 1; i < N; i++) {
			if (N % i == 0 && check(i)) {
				answer = N / i;
				break;
			}
		}

		System.out.println(answer);
	}
	
	private static boolean check(int size) {
		int[] visited = new int[N + 1];
		int idx = 1; // 현재 지역 번호
		
		// 지역 나누기
		for (int start = 1; start <= N; start += size) {
			// 해당 지역 확인
			for (int i = start; i < start + size; i++) {
				visited[i] = idx;
				
				// 연결된 일반 도로 확인
				for (int next : list[i]) {
					// 다른 지역이라면 경로가 존재하는 것 -> false 반환
					if (visited[start] != visited[next]) {
						return false;
					}
				}
			}
			
			idx++;
		}
		
		return true;
	}

}
