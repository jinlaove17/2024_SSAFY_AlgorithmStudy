import java.util.*;

// 플로이드 워셜로 (n <= 200) 모든 노드 간 최단거리 구하기
// 각자의 최단거리 vs S - (환승 노드) -> A, B까지의 각각 거리 비교하기
// 범위나 시간이 좀 더 빡빡하다면 다익스트라로 풀어도 될것 같음

class Solution {
    public int solution(int n, int s, int a, int b, int[][] fares) {
        final int INF = 200_000_001;
        int[][] dist = new int[201][201];
        int ret = INF;
        
        // 1. dist 배열 초기화
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        
        for (int[] f : fares) {
            int u = f[0], v = f[1], c = f[2];
            dist[u][v] = dist[v][u] = Math.min(dist[u][v], c);
        }
        
        // 2. FW 계산
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        
        // 3. 최종 min 계산
        for (int k = 1; k <= n; k++) {
            int cost = dist[s][k] + dist[k][a] + dist[k][b];
            ret = Math.min(ret, cost);
        }

        return ret;
    }
}
