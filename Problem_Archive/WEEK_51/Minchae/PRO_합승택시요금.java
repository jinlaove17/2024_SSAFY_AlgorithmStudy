class Solution {
    static int INF = 99999999;
	
	public static int solution(int n, int s, int a, int b, int[][] fares) {
		int[][] map = new int[n + 1][n + 1];
		
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				map[i][j] = i == j ? 0 : INF;
			}
		}
		
		for (int[] fare : fares) {
			map[fare[0]][fare[1]] = fare[2];
			map[fare[1]][fare[0]] = fare[2];
		}
		
		// 모든 경로에 대해 최단 경로 구하기
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					map[i][j] = Math.min(map[i][j], map[i][k] + map[k][j]);
				}
			}
		}
		
		int answer = Integer.MAX_VALUE;
		
		for (int i = 1; i <= n; i++) {
			answer = Math.min(answer, map[s][i] + map[i][a] + map[i][b]);
		}
		
		return answer;
	}
}
