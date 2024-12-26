#include <iostream>
#include <vector>
#include <cmath>
using namespace std;
using ll = long long;
using vi = vector<int>;
using vll = vector<ll>;

bool vst[300001];
ll ret = 0;

/* 문제 접근
  친절하게 트리 구조를 탐색하면서 각 노드의 값을 0으로 만드는 과정을 보여주었다.
  그래서 해당 과정을 따라서 구현해보기로 시도했음 (tree 안에서 DFS 탐색 해주면서 해당 처리 진행하기)
  다만, 트리 탐색 과정에서 다루는 수의 범위가 커질 위험이 있으므로 해당 부분은 long long으로 처리해주었다.
  -> DFS를 이용해 리프 노드부터 부모(prev, 자신 상위 노드) 값을 전달하며, 절댓값 이동 횟수를 누적하기
  -> 일단 0을 root라고 생각하고, 0부터 dfs를 돌린다
  -> 탐색중인 노드의 가중치를 일단 0으로 만든다고 생각하고(양수라면 빼고, 음수라면 더하는 행동) 
      >> 가중치 만큼 계속 ret에 더해준다. 
  -> 이 행동을 반복하면서 마지막에 tree[0] != 0 이면 불가능한 경우이므로 -1 출력

  알고리즘: 트리 탐색 (DFS)
  시간 복잡도: O(N) dfs 탐색 기준
  실행 시간: C++ 기준 최대 100ms 정도
*/

void dfs(vll &tree, vector<vi> &graph, int prev){
    vst[prev]=true;

    for(int i=0; i<graph[prev].size(); ++i){
        if(vst[graph[prev][i]]) continue;

        dfs(tree, graph, graph[prev][i]);
        ret += abs(tree[graph[prev][i]]);
        tree[prev] += tree[graph[prev][i]];
    }
}

ll solution(vector<int> a, vector<vector<int>> edges) {
    vll tree(a.begin(),a.end()); // copy
    vector<vi> graph(a.size()); // size 대로 그래프 제작
    int SIZE = edges.size();

    for(int i=0; i<SIZE; ++i){
        graph[edges[i][0]].push_back(edges[i][1]);
        graph[edges[i][1]].push_back(edges[i][0]);
    }

    dfs(tree, graph, 0);

    if(tree[0] != 0) return -1;
    else return ret;
}
