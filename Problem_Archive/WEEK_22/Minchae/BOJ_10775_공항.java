/**
 * 10775 공항
 * https://www.acmicpc.net/problem/10775
 * 
 * @author minchae
 * @date 2024. 12. 16.
 *
 * 문제 풀이
 *  - 게이트에 비행기를 배치하는 것 -> 그리디 적용
 *    -> 이미 도킹되어 있다면 숫자가 작은 게이트에 도킹
 *  - 그런데 도킹할 수 있는 게이트를 다 탐색한다면 시간 초과 발생할 것
 *    -> 현재 게이트보다 작은 게이트 중 도킹되지 않은 게이트 번호를 부모로 가지는 배열 만듦
 *       유니온파인드 이용 -> 도킹할 게이트 번호와 그 전 게이트 번호를 union 해서 도킹할 게이트를 저장함
 *
 * 시간 복잡도
 * O(P⋅α(G))
 *
 * 실행 시간
 * 220 ms
* */

import java.io.*;

public class Main {
	
	static int[] parent;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int G = Integer.parseInt(br.readLine());
        int P = Integer.parseInt(br.readLine());
        
        parent = new int[G + 1];
        
        for (int i = 1; i <= G; i++) {
        	parent[i] = i;
        }
        
        int answer = 0;
        
        for (int i = 0; i < P; i++) {
        	int g = Integer.parseInt(br.readLine()); // 도착하는 비행기
        	int gate = find(g); // 도킹할 수 있는 게이트
        	
        	// 0은 도킹할 수 있는 게이트가 없는 경우 (비행기가 다 있는 경우)
        	if (gate == 0) {
        		break;
        	}
        	
        	answer++;
        	
        	union(gate, gate - 1); // 다음 비행기가 들어올 때 도킹할 게이트를 저장
        }

        System.out.println(answer);
	}
	
	private static int find(int x) {
		if (x == parent[x]) {
			return x;
		}
		
		return parent[x] = find(parent[x]);
	}

	private static void union(int x, int y) {
		int rootX = find(x);
		int rootY = find(y);
		
		if (rootX < rootY) {
			parent[rootY] = rootX;
		} else {
			parent[rootX] = rootY;
		}
	}
}
