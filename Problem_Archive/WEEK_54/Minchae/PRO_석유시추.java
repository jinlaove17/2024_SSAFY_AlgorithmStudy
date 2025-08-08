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
    
    static int n, m;
    
    static boolean[][] visited;
    static int[] arr; // 각 열마다 뽑을 수 있는 석유량 저장
    
    public int solution(int[][] land) {
        n = land.length;
        m = land[0].length;
        
        visited = new boolean[n][m];
        arr = new int[m];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visited[i][j] && land[i][j] == 1) {
                    bfs(i, j, land);
                }
            }
        }
        
        int answer = 0;
        
        for  (int value : arr) {
            answer = Math.max(answer, value);
        }
        
        return answer;
    }
    
    private static void bfs(int x, int y, int[][] land) {
        Queue<Pair> q = new LinkedList<>();
        HashSet<Integer> hs = new HashSet<>(); // 탐색 칸의 열 저장
        
        q.add(new Pair(x, y));
        visited[x][y] = true;
        
        int cnt = 1;
        
        while (!q.isEmpty()) {
            Pair cur = q.poll();
            
            hs.add(cur.y);
            
            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                
                if (!isRange(nx, ny) || visited[nx][ny] || land[nx][ny] == 0) {
                    continue;
                }
                
                q.add(new Pair(nx, ny));
                visited[nx][ny] = true;
                
                cnt++;
            }
        }
        
        // 각 열마다 뽑을 수 있는 석유 저장
        for (int col : hs) {
            arr[col] += cnt;
        }
    }
    
    private static boolean isRange(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }
    
}
