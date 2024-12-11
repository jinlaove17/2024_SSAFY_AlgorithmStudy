/**
 * 19535 ㄷㄷㄷㅈ
 * https://www.acmicpc.net/problem/19535
 * 
 * @author minchae
 * @date 2024. 12. 11.
 * 
 * 문제 풀이
 *  - ㄷ의 개수는 (노드1 연결된 노드 개수 - 1) * (노드2에 연결된 개수 - 1)
 *  - ㅈ의 개수는 연결된 간선의 개수가 3개 이상인 것중에서 3개를 선택하면 'ㅈ'모양 만족 -> nC3
 *  
 * 시간복잡도
 * 그래프 생성 O(N)
 * D, G 계산 O(N + E)
 * 간선 개수가 (N - 1)이기 때문에 최종 시간복잡도는 O(N)
 *
 * 실행 시간
 * 652 ms
 */

import java.io.*;
import java.util.*;

public class Main {

	static int N;
	static ArrayList<Integer>[] list;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		list = new ArrayList[N + 1];
		
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < N - 1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			
			list[u].add(v);
			list[v].add(u);
		}
		
		long D = 0;
		long G = 0;
		
		boolean[] visited = new boolean[N + 1];
		
		for (int i = 1; i <= N; i++) {
			long a = list[i].size();
			
			if (a >= 3) {
				G += (a * (a - 1) * (a - 2)) / 6;
			}
			
			visited[i] = true;
			
			for (int next : list[i]) {
				// 이미 'ㅈ'모양으로 만들어진 경우 넘어감
				if (visited[next]) {
					continue;
				}
				
				long b = list[next].size();
				
				D += (a - 1) * (b - 1);
			}
		}

		if (D > G * 3) {
			System.out.println("D");
		} else if (D < G * 3) {
			System.out.println("G");
		} else {
			System.out.println("DUDUDUNGA");
		}
	}

}
