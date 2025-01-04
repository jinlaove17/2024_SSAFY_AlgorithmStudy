/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- dfs, 최소 힙을 이용한 탐색, 위상 정렬 등 다양한 고민을 해봤지만 결국 해결하지 못했던 문제였다.
      결과적으로 dfs를 사용하는 것은 맞지만, 매번 인접 리스트를 만들어서 현재 정점에서 인접한 정점으로만 탐색을 하는 것이 아니라
      매 정점에서 모든 간선을 살펴보고 부모 정점이 방문되고 다음 정점이 방문되지 않은 정점이라면 방문 하는 식으로
      모든 상태 공간 트리를 살펴보는 탐색을 전혀 생각치도 못한 풀이였다.
      양의 수, 늑대의 수, 부모 정점의 방문 여부 등 고려되는 조건이 많으므로 가지치기가 많이 일어나서 지수 시간이 걸리는 시간 복잡도를 가져도
      답을 도출할 수 있게 된다.
      앞으로는 우선 인접 행렬, 인접 리스트를 구성하고 그래프 문제를 해결하려는 습관을 고쳐야겠다는 생각이 들었다.

시간 복잡도
	- 이진트리 구조에서 매 노드에서 가능한 모든 상태를 살펴보는데 최악의 경우 16개의 상태가 존재한다.
      따라서 O(2^16)의 시간 복잡도를 갖지만, 양의 수와 늑대의 수, 부모 노드의 방문 여부의 조건식을 통해서 굉장히 많은 가지치기가 일어나서
      O(2^N)보다는 적은 시간 복잡도를 가질 것이다.

실행 시간
	- 가장 많은 시간이 소요된 테스트에서 0.22ms
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_VERTEX_COUNT = 17;

bool isVisited[MAX_VERTEX_COUNT];

void dfs(const vector<int>& info, const vector<vector<int>>& edges, int cur, int sheepCnt, int wolfCnt, int& answer);

int solution(vector<int> info, vector<vector<int>> edges)
{
    int answer = 0;

    isVisited[0] = true;
    dfs(info, edges, 0, 0, 0, answer);

    return answer;
}

void dfs(const vector<int>& info, const vector<vector<int>>& edges, int cur, int sheepCnt, int wolfCnt, int& answer)
{
    if (info[cur] == 0)
    {
        answer = max(answer, ++sheepCnt);
    }
    else
    {
        if (++wolfCnt >= sheepCnt)
        {
            return;
        }
    }

    for (const vector<int>& e : edges)
    {
        int from = e[0];
        int to = e[1];

        if ((isVisited[from]) && (!isVisited[to]))
        {
            isVisited[to] = true;
            dfs(info, edges, to, sheepCnt, wolfCnt, answer);
            isVisited[to] = false;
        }
    }
}
