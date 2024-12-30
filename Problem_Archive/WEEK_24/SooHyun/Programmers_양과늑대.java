/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심은 dfs를 활용한 탐색입니다.
	- 방문 체크를 위한 boolean 배열을 하나 선언하고 root 즉 idx가 0인 것부터 dfs 탐색을 시작합니다.
	- 방문한 idx의 info 값이 0이면 양의 수를 증가시키고 그렇지 않으면 늑대의 수를 증가시킵니다.
	- 양인 경우에는 answer 값을 갱신해주며, 기저 조건으로 양의 수가 늑대의 수보다 적어지면 그 즉시 dfs를 return 시킵니다.

시간 복잡도
    - DFS
        - 노드 탐색 : 최악의 경우 모든 노드를 한 번씩 방문하므로 O(N)
        - 간선 탐색 : O(E)
    - 전체 시간복잡도: O(N+E)

실행 시간
	- 1.31ms(java)

*/
import java.util.*;
class Solution {
    boolean[] visit;
    int answer = 0;
    public int solution(int[] info, int[][] edges) {
        visit = new boolean[info.length];
        dfs(0,0,0, info, edges);
        return answer;
    }

    public void dfs(int idx, int sheepCount, int wolfCount, int[] info, int[][] edges){
        visit[idx] = true;
        if(info[idx] ==0){
            sheepCount++;
            if(sheepCount > answer){
                answer = sheepCount;
            }
        }else{
            wolfCount++;
        }
        if(sheepCount <= wolfCount)
            return;
        for(int[] edge : edges){
            int from = edge[0];
            int to = edge[1];
            if(visit[from] && !visit[to]){
                dfs(to, sheepCount, wolfCount, info, edges);
                visit[to]=false;
            }
        }
    }
}