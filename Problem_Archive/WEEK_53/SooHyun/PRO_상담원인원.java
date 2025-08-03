import java.util.*;
class Solution {
    List<Info>[] list;
    int[][] wait;
    public int solution(int k, int n, int[][] reqs) {
        int answer = 0;
        list = new ArrayList[reqs.length + 1];
        wait = new int[k+1][n-k+2];
        for(int i=0; i<=reqs.length; i++){
            list[i] = new ArrayList<>();
        }
        for(int[] req : reqs){
            list[req[2]].add(new Info(req[0], req[0] + req[1]));
        }
        for(int i=1; i<=k ; i++) {
            for(int j=1; j<=n-k+1; j++){
                PriorityQueue<Integer> pq = new PriorityQueue<>();
                for(Info info : list[i]) {
                    if(pq.size() <j) {
                        pq.add(info.end);
                        continue;
                    }
                    int endTime = pq.poll();
                    if(endTime > info.start) {
                        wait[i][j] += endTime - info.start;
                        pq.add(endTime + info.end - info.start);
                    }else{
                        pq.add(info.end);
                    }
                }
            }
        }
        return dfs(1,k, n-k+1);
    }

    public int dfs(int depth, int k, int total) {
        if(depth > k) {
            return 0;
        }
        int result = Integer.MAX_VALUE;
        for(int i=1; i<=total; i++) {
            result = Math.min(result, dfs(depth+1, k, total -i+1) + wait[depth][i]);
        }
        return result;
    }

    class Info{
        int start;
        int end;

        public Info(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
}