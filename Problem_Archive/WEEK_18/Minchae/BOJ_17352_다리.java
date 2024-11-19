/**
 * 17352 여러분의 다리가 되어 드리겠습니다!
 * https://www.acmicpc.net/problem/17352
 *
 * @author minchae
 * @date 2024. 11. 18.
 *
 * 문제 풀이
 *  - 보자마자 union-find 사용해야 될 것 느낌이었다
 *  - 그래서 N-2만큼 연결된 섬들을 입력받을 때 union으로 섬들을 연결해줌
 *  - 다 끝났다면 1번섬을 기준으로 연결되지 않은 섬을 찾음 -> 찾았다면 출력 후에 바로 종료
 *  
 * 시간복잡도
 * 입력 받고 섬 연결 : O((N - 2) * α(N))
 * 연결 확인 : O(α(N))
 *
 * 실행 시간
 * 456 ms
 * */

import java.util.*;
import java.io.*;

public class Main {
	
	static int N;
	static int[] parent;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        
        parent = new int[N + 1];
        
        for (int i = 1; i < N; i++) {
        	parent[i] = i;
        }
        
        for (int i = 0; i < N - 2; i++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	
        	union(a, b);
        }
        
        // 다리가 연결되어 있는지 확인
        for (int i = 2; i <= N; i++) {
        	if (find(1) != find(i)) {
        		System.out.println(1 + " " + i);
        		return;
        	}
        }
	}
	
	private static int find(int x) {
		if (parent[x] == x) {
			return x;
		}
		
		return parent[x] = find(parent[x]);
	}
	
	private static boolean union(int x, int y) {
		int rootX = find(x);
		int rootY = find(y);
		
		if (rootX == rootY) {
			return false;
		}
		
		if (rootX < rootY) {
			parent[rootY] = rootX;
		} else {
			parent[rootX] = rootY;
		}
		
		return true;
	}

}
