/**
 * 21278 호석이 두 마리 치킨
 * https://www.acmicpc.net/problem/21278
 *
 * @author minchae
 * @date 2025. 1. 20.
 *
 * 문제 풀이
 * - 일단 모든 건물 간의 최단 경로를 구함 -> 플로이드 워셜 이용
 * - "모든 건물에서 가장 가까운 치킨집까지 왕복하는 최단 시간의 총합"을 최소화할 수 있는 건물 2개를 고르기
 *   -> 번호가 작은 건물부터 선택해서 최단 시간이 되는지 확인하면 됨
 *  - BFS 사용해도 되지만 모든 건물 간의 거리를 모두 구해놓고 치킨집을 찾는 게 더 효율적이라고 생각. 그래서 플로이드 이용
 *
 * 시간 복잡도
 * O(N^3)
 *
 * 실행 시간
 * 200 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static final int INF = 987654321;
	
	static int N, M;
	static int[][] map; // 같은 도로가 중복되어 주어지는 경우는 없기 때문에 배열 이용
	
	static int num1, num2, answer;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        map = new int[N + 1][N + 1];
        
        for (int i = 1; i <= N; i++) {
        	Arrays.fill(map[i], INF);
        	map[i][i] = 0; // 자기 자신으로 가는 길의 값은 0
        }
        
        for (int i = 0; i < M; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int A = Integer.parseInt(st.nextToken());
        	int B = Integer.parseInt(st.nextToken());
        	
        	map[A][B] = 1;
        	map[B][A] = 1;
        }
        
        floyd();
        
        select();
        
        System.out.println(num1 + " " + num2 + " " + (answer * 2));
	}
	
	private static void floyd() {
		for (int k = 1; k <= N; k++) {
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					// 값 갱신
					map[i][j] = Math.min(map[i][j], map[i][k] + map[k][j]);
				}
			}
		}
	}
	
	// 건물 두 개 선택해서 치킨집 열어보기
	private static void select() {
		answer = INF;
		
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (i == j) {
					continue;
				}
				
				int sum = 0;
				
				for (int k = 1; k <= N; k++) {
					if (i == k || j == k) {
						continue;
					}
					
					// 둘 중에 최솟값 더하기
					sum += Math.min(map[i][k], map[k][j]);
				}
				
				// 최솟값 발견한 경우
				if (sum < answer) {
					answer = sum;
					num1 = i;
					num2 = j;
				}
			}
		}
	}

}
