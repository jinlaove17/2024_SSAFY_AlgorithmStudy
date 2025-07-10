/**
 * 20208 진우의 민트초코우유
 * https://www.acmicpc.net/problem/20208
 *
 * @author minchae
 * @date 2025. 7. 10.
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static class Pair {
		int x;
		int y;
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	static int N, M, H;
	
	static int[][] map; // 진우의 집은 1, 민트초코우유는 2, 빈 땅은 0
	static boolean[] visited;
	
	static Pair start;
	static ArrayList<Pair> milk;
	
	static int answer;
	
	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        
        map = new int[N][N];
        visited = new boolean[10];
        milk = new ArrayList<>();

        for (int i = 0; i < N; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	for (int j = 0; j < N; j++) {
        		map[i][j] = Integer.parseInt(st.nextToken());
        		
        		if (map[i][j] == 1) {
        			start = new Pair(i, j);
        		}
        		
        		if (map[i][j] == 2) {
        			milk.add(new Pair(i, j));
        		}
        	}
        }
        
        move(start, 0, M);
        
        System.out.println(answer);
	}
	
	private static void move(Pair cur, int eat, int remain) {
		// 집에 갈 수 있는 경우
		if (remain >= getDistance(cur, start)) {
			answer = Math.max(answer, eat);
		}
		
		for (int i = 0; i < milk.size(); i++) {
			if (visited[i]) {
				continue;
			}
			
			visited[i] = true;
			
			int dist = getDistance(cur, milk.get(i));
			
			// 우유 마실 수 있는 경우
			if (remain >= dist) {
				move(milk.get(i), eat + 1, remain - dist + H);
			}
			
			visited[i] = false;
		}
	}
	
	private static int getDistance(Pair a, Pair b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

}
