/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심 알고리즘은 위상정렬입니다.
	- 위상정렬 알고리즘을 사용하기 전에 전체 가중치의 총합인 sum을 구함으로써 만약 이것이 0이 되지 않으면 주어진 조건을 만족하지 않아 -1을 반환합니다.
	- 총합을 구하는 과정에서 degree를 업데이트 해줌으로써 각 노드마다 연결된 간선의 갯수를 지정합니다.
	- 이후 위상정렬 알고리즘을 적용해 먼저 degree가 1 즉 연결된 간선의 갯수가 1인 노드를 큐에 넣어줍니다.
	- 그리고 큐에서 하나씩 빼면서 방문체크를 수행하고 방문하지 않은 것에 대해서 answer값을 누적해주면서 degree를 하나씩 줄이면서 total 배열의 값을 업데이트해줍니다.
	- 마지막으로 while 문을 빠져나오게 되면서 정답을 도출합니다.

시간 복잡도
    - 초기화 및 데이터 준비 : O(N+E)
    - 위상 정렬 알고리즘 : O(N+E)
    - 전체 시간 복잡도 : O(N+E)

실행 시간
	- 232.96ms(java)

*/
import java.util.*;
class Solution {
    boolean[] visit;
    int[] degree;
    ArrayList<Integer>[] list;
    long[] total;
    long answer = 0;
    public long solution(int[] a, int[][] edges) {
        degree = new int[a.length];
        list = new ArrayList[a.length];
        visit = new boolean[a.length];
        total = new long[a.length];
        long sum=0;
        for(int i=0; i<a.length; i++){
            sum+=a[i];
            total[i]=a[i];
            list[i] = new ArrayList<>();
        }
        if(sum !=0){
            return -1;
        }
        for(int i=0; i<edges.length; i++){
            int from = edges[i][0];
            int to = edges[i][1];
            degree[from]++;
            degree[to]++;
            list[from].add(to);
            list[to].add(from);
        }
        topology();

        return answer;
    }

    public void topology(){
        Queue<Integer> que = new LinkedList<>();
        for(int i=0; i<degree.length; i++){
            if(degree[i]==1){
                que.add(i);
            }
        }
        while(!que.isEmpty()){
            int cur = que.poll();
            visit[cur]=true;
            for(int i=0; i<list[cur].size(); i++){
                int next = list[cur].get(i);
                if(!visit[next]){
                    total[next]+=total[cur];
                    answer += Math.abs(total[cur]);
                    total[cur]=0;
                    degree[next]--;
                    if(degree[next]==1){
                        que.add(next);
                    }
                }
            }
        }
    }
}