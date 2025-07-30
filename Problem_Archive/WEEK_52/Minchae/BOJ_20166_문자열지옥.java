import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static class Node {
		int x;
		int y;
		int len;
		String str;
		
		public Node(int x, int y, int len, String str) {
			this.x = x;
			this.y = y;
			this.len = len;
			this.str = str;
		}
	}
	
	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };
	
	static int N, M, K;
	static char[][] map;
	
	static HashMap<String, Integer> hm = new HashMap<>();
	static int maxLen = -1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new char[N][M];
		
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}
		
		String[] key = new String[K];
		
		for (int i = 0; i < K; i++) {
			String favorite = br.readLine();
			
			key[i] = favorite;
			hm.put(favorite, 0);
			maxLen = Math.max(maxLen, favorite.length());
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				makeStr(i, j);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (String s : key) {
			sb.append(hm.get(s) + "\n");
		}
		
		System.out.println(sb.toString());
	}
	
	private static void makeStr(int x, int y) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(x, y, 1, map[x][y] + ""));
		
		while (!q.isEmpty()) {
			Node cur = q.poll();
			
			if (cur.len > maxLen) {
				continue;
			}
			
			if (hm.containsKey(cur.str)) {
				hm.put(cur.str, hm.get(cur.str) + 1);
			}
			
			for (int i = 0; i < 8; i++) {
				int nx = (cur.x + dx[i]) % N;
				int ny = (cur.y + dy[i]) % M;
				
				if (nx < 0) {
					nx += N;
				}
				
				if (ny < 0) {
					ny += M;
				}
				
				q.add(new Node(nx, ny, cur.len + 1, cur.str + map[nx][ny]));
			}
		}
	}

}
