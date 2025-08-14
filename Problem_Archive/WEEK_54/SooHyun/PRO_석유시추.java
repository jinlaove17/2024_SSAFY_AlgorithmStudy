import java.util.*;
class Solution {
    Map<Integer, Integer> map;
    int[][] arr;
    boolean[][] visit;
    int idx=1;
    int n, m;
    int[] dx = {-1,0,1,0};
    int[] dy = {0,-1,0,1};
    public int solution(int[][] land) {
        int answer = 0;
        n = land.length;
        m = land[0].length;
        arr = new int[n][m];
        visit = new boolean[n][m];
        map = new HashMap<>();
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if(land[i][j]==1 && !visit[i][j]){
                    bfs(i,j,land);
                    idx++;
                }
            }
        }
        for(int i=0; i<m; i++) {
            List<Integer> list = new ArrayList<>();
            for(int j=0; j<n; j++) {
                if(arr[j][i] >0 && !list.contains(arr[j][i]))
                    list.add(arr[j][i]);
            }
            int temp=0;
            for(int n : list){
                temp+=map.get(n);
            }
            answer = Math.max(answer, temp);
        }
        return answer;
    }

    public void bfs(int x, int y, int[][] land) {
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[]{x,y});
        arr[x][y]=idx;
        visit[x][y]=true;
        int result=1;
        while(!que.isEmpty()){
            int[] current = que.poll();
            int current_x = current[0];
            int current_y = current[1];
            for(int i=0; i<4; i++) {
                int nx = current_x+dx[i];
                int ny = current_y+dy[i];
                if(!range(nx,ny))
                    continue;
                if(visit[nx][ny] || land[nx][ny]==0)
                    continue;
                que.add(new int[]{nx, ny});
                arr[nx][ny] = idx;
                visit[nx][ny]=true;
                result++;
            }
        }
        map.put(idx, result);
    }

    public boolean range(int x, int y) {
        if(x <0 || x>=n || y<0 || y>=m)
            return false;
        return true;
    }
}