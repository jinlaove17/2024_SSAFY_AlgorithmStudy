import java.util.*;

class Solution {
    static class Pair {
		int x;
		int y;
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N, M;
	static char[][] map;
	static boolean[][] visited;
	
	public int[] solution(String[] maps) {
		N = maps.length;
		M = maps[0].length();
        
        map = new char[N][M];
		visited = new boolean[N][M];
		
		for (int i = 0; i < N; i++) {
			map[i] = maps[i].toCharArray();
		}
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 'X' || visited[i][j]) {
					continue;
				}
				
				int result = bfs(i, j);
				list.add(result);
			}
		}
		
		if (list.isEmpty()){
            return new int[] {-1};
        }
		
        Collections.sort(list);
        
        int[] answer = new int[list.size()];
        
        for (int i = 0; i < list.size(); i++) {
        	answer[i] = list.get(i);
        }
		
        return answer;
    }
	
	private static int bfs(int x, int y) {
		int result = map[x][y] - '0';
		
		Queue<Pair> q = new LinkedList<>();
		
		q.add(new Pair(x, y));
		visited[x][y] = true;
		
		while (!q.isEmpty()) {
			Pair cur = q.poll();
			
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (!isRange(nx, ny) || visited[nx][ny] || map[nx][ny] == 'X') {
					continue;
				}
				
				q.add(new Pair(nx, ny));
                visited[nx][ny] = true;
                
				result += map[nx][ny] - '0';
			}
		}
		
		return result;
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}
}
