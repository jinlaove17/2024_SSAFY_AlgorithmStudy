/**
 * 3665 최종 순위
 * https://www.acmicpc.net/problem/3665
 * 
 * @author minchae
 * @date 2024. 12. 23.
 *
 * 문제 풀이
 *  - 위상정렬 같은 느낌이 들었음
 *  - 일단 맨 처음에 등수를 입력받을 때 진입차수 배열에 값을 넣어줌, 맵에 연결되었다는 표시 해주기
 *  - 일단 위상정렬 하기 전에 진입차수가 0인 것을 큐에 넣어줌
 *  - 오류 검출을 위해 while문(큐가 빌 때까지)이 아닌 for문 사용(정점의 개수만큼)
 *
 * 시간 복잡도
 * O(T⋅(n^2 + m))
 *
 * 실행 시간
 * 596 ms
 **/

import java.util.*;
import java.io.*;

public class Main {
	
	static int n;
	
	static int[][] map;
	static int[] indegree;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int T = Integer.parseInt(br.readLine());
        
        while (T-- > 0) {
        	n = Integer.parseInt(br.readLine());
        	
        	map = new int[n + 1][n + 1];
        	indegree = new int[n + 1];
        	
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	for (int i = 0; i < n; i++) {
        		int t = Integer.parseInt(st.nextToken());
        		
        		indegree[t] = i;
        		
        		for (int j = 1; j <= n; j++) {
        			// 등수가 높은 경우에만 1 저장
        			if (j != t && map[j][t] == 0) {
        				map[t][j] = 1;
        			}
        		}
        	}
        	
        	int m = Integer.parseInt(br.readLine());
        	
        	for (int i = 0; i < m; i++) {
        		st = new StringTokenizer(br.readLine());
        		
        		int a = Integer.parseInt(st.nextToken());
        		int b = Integer.parseInt(st.nextToken());
        		
        		change(a, b);
        	}
        	
        	System.out.println(sort());
        }
        
	}
	
	// 등수 변경
	private static void change(int a, int b) {
		if (map[a][b] == 1) { // a가 더 높은 경우에 b와 서로 등수 바꿈
			map[a][b] = 0;
			map[b][a] = 1;
			
			indegree[a]++;
			indegree[b]--;
		} else {
			map[a][b] = 1;
			map[b][a] = 0;
			
			indegree[a]--;
			indegree[b]++;
		}
	}
	
	private static String sort() {
		Queue<Integer> q = new LinkedList<>();
		
		for (int i = 1; i <= n; i++) {
			// 진입 차수가 0이면 큐에 삽입
			if (indegree[i] == 0) {
				q.add(i);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 1; i <= n; i++) {
			// 더이상 순위를 정할 수 없는 경우
			if (q.isEmpty()) {
				return "IMPOSSIBLE";
			}
			
			// 확실한 순위를 찾을 수 없는 경우 -> 진입 차수가 0인 팀이 동시에 여러 개라면 찾을 수 없음
			if (q.size() > 1) {
				return "?";
			}
			
			int cur = q.poll();
			
			sb.append(cur).append(" ");
			
			for (int j = 1; j <= n; j++) {
				if (map[cur][j] == 1) {
					map[cur][j] = 0;
					indegree[j]--;
					
					if (indegree[j] == 0) {
						q.add(j);
					}
				}
			}
		}
		
		return sb.toString();
	}

}
