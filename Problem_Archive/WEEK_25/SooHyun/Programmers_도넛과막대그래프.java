/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 풀기 전에 어떤 방식으로 풀어야하는지 감이 잡히지 않았습니다.
	- 처음에는 모든 노드를 탐색하면서 각 노드에서 도넛, 막대, 8자 모양이 가능한지를 판별하려고 했으나 너무 복잡하다고 생각했습니다.
	- 이에 따라 각 모양이 나오는 규칙이 있다고 판단해서 예제와 각각의 도넛, 막대, 8자의 예시의 경우 그리고 출발점의 특징을 분석했습니다.
	- 첫째, 출발점의 경우 해당 노드로 들어오는 것이 없으며 문제의 조건에 의해 나가는 간선이 최소 2개라고 생각했습니다.(문제 조건 : 가능한 도넛, 막대, 8자 그래프의 총 가짓수가 2개 이상이다.)
	- 그리고 출발노드에서 나가는 경우의 수가 전체 도넛, 막대, 8자 그래프 총합의 갯수라는 것을 알았고 총합을 변수에 저장해뒀습니다.
	- 막대 그래프의 경우 구성하고 있는 노드 중 가장 최상단에 놓인 것은 들어오는 것만 존재하고 나가는 것이 존재하지 않는 경우입니다. 이를 통해 막대 그래프의 갯수를 count합니다.
	- 8자 그래프의 경우 하나의 노드가 반드시 2개의 노드로 들어오고 2개의 노드로 나가는 경우입니다. 이를 통해 8자 그래프의 갯수를 count합니다.
	- 그리고 도넛의 경우 전체 총합에서 막대 그래프 갯수 + 8자 그래프 갯수를 뺀 것이 도넛 그래프의 갯수입니다.
	- 이런 방식으로 문제를 해결하였습니다.

시간 복잡도
	- 초기화 및 데이터 분류 : O(N+L)
	    - 최대 노드 인덱스 탐색 : O(L) (L : edges 배열의 길이)
	    - input, output 리스트 초기화 : O(N)
	    - input, output 간선 추가 : O(L) (L : edges 배열의 길이)
	    - 시작노드 탐색 : O(N)
    - 시작 노드 간선 제거 : O(N)
    - 막대 그래프 갯수 계산 : O(N)
    - 8자 그래프 갯수 계산 : O(N)
    - 전체 시간복잡도: O(N+L) (L : edges 배열의 길이)

실행 시간
	- 6793.91ms(java)

삽질한 부분
    - 만약 i 번째 노드가 전체에서 존재하지 않는 경우를 생각하지 못했습니다. i번째 노드가 존재하지 않더라도 input, output 연결리스로 만들어져 있기에 output의 갯수가 0이여서 막대 그래프의 갯수로 count됩니다.
    - 이를 해결하기 위해 set을 통해 존재하는 노드만을 저장하고 이를 통해 count를 하였습니다.
*/
import java.util.*;
class Solution {
    List<Integer>[] input;
    List<Integer>[] output;
    boolean[] visit;
    int start=0;
    int N=0;
    Set<Integer> set;
    int totalCount=0;
    public int[] solution(int[][] edges) {
        int[] answer = new int[4];
        init(edges);
        removeEdge();
        answer[0]=start;
        answer[2] = countLine();
        answer[3] = countEight();
        answer[1] = totalCount - (answer[2] + answer[3]);
        return answer;
    }

    public void init(int[][] edges){
        set = new HashSet<>();
        for(int[] edge : edges){
            set.add(edge[0]);
            set.add(edge[1]);
            N = Math.max(edge[0],N);
            N = Math.max(edge[1],N);
        }
        input = new ArrayList[N+1];
        output = new ArrayList[N+1];
        visit = new boolean[N+1];
        for(int i=0; i<=N; i++){
            input[i] = new ArrayList<>();
            output[i] = new ArrayList<>();
        }
        for(int[] edge : edges){
            int from = edge[0];
            int to = edge[1];
            output[from].add(to);
            input[to].add(from);
        }
        for(int i=1; i<=N; i++){
            if((input[i].isEmpty() || input[i].size()==0) && output[i].size() >=2){
                start=i;
                totalCount = output[start].size();
                break;
            }
        }
        visit[start]=true;
    }

    public void removeEdge(){
        for(int i=1; i<=N; i++){
            if(i==start)
                continue;
            if(!input[i].isEmpty() && input[i].contains(start)){
                input[i].remove(Integer.valueOf(start));
                output[start].remove(Integer.valueOf(i));
            }
        }

    }

    public int countLine(){
        int count =0;
        for(int i=1; i<=N; i++){
            if(i==start)
                continue;
            if(!set.contains(i))
                continue;
            if(!visit[i] && (output[i].isEmpty() || output[i].size() == 0)){
                visit[i] = true;
                count++;
            }
        }
        return count;
    }

    public int countEight(){
        int count=0;
        for(int i=1; i<=N; i++){
            if(!set.contains(i))
                continue;
            if(!visit[i] && input[i].size()==2 && output[i].size()==2){
                visit[i] = true;
                count++;
            }
        }
        return count;
    }
}