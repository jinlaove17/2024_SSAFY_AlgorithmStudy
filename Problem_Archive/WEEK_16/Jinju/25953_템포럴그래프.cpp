#include <iostream>
#include <vector>
using namespace std;
using ll = long long;
const int INF = 10000 * 1000 + 1;
vector<vector<ll>> cost;

/*
  문제 접근
  1. 처음에는 시간마다 edge가 바뀌는 그래프 최단 경로라고 생각할 수 있다.
  2. 그러나 우리에겐 시간 T가 주어지고, T 시간(turn) 마다 내가 위치한 정점에서 go or not to go를 선택해야 한다.
   => 즉, 최단 시간은 전혀 고려 안해도 된다.
   => 주어진 T시간 안에서 (시간은 T 이하기만 하면 상관없음) 최단 경로 길이를 구하는 것이다.
  3. 무조건 주어진 시간 T를 다 돌면서 최단 길이를 찾아야 한다는 생각으로 이어져야한다.
  4. 3번을 구현하기 위해서 T만큼 반복문을 돌면서 DP 테이블을 갱신한다. 
      이전 값과 갱신되는 값을 비교하면서 min(이전 cost, 갱신 cost)를 통해 
      현재 시간 t에 해당하는 min cost를 계속 저장/갱신 하면 된다.

  >>> 다익스트라 등 다른 최단 경로 알고리즘으로 접근했을 때 터지는 이유?
  - 시간마다 간선 정보가 바뀌면서 너무 많은 정보가 큐에 저장될 수 있는 가능성
  - 시간 흐름을 보장하면서 간선을 선택해야 하는 문제
      우리는 최대 T번 움직일 수 있고, 그마저도 시간 순서가 보장이 되어야 한다.
      큐에 전부 담고 시간 순서를 맞추면서 최단 경로 길이까지 갱신하려면 이전 상태(state)나 시간별 상태를 처리하기 힘들다.
      이렇게 되면 불필요한 정보가 큐에 너무 많이 담기고 불필요한 탐색을 지속하게 된다.
      해당 정보는 2차원 DP 배열으로 더 쉽게 저장하고 관리할 수 있다.

  시간복잡도: O(T * (N+M))
  알고리즘: 시간 별로 최소 경로 길이를 저장하는 DP 테이블 채우기
  실행시간: 280ms (C++)
   
*/

void fillDp(int n, int t) {
    for (int i = 0; i < n; i++) {
        cost[t][i] = cost[t-1][i];
    }
}

int main() {
    cin.tie(0);
    ios_base::sync_with_stdio(0);

    int n, t, m, s, e;
    ll u, v, w;
    cin >> n >> t >> m >> s >> e;

    cost.assign(t+1, vector<ll>(n, INF));
    cost[0][s] = 0;

    for (int i = 1; i <= t; i++) {
        fillDp(n, i);
        for (int j = 0; j < m; j++) {
            cin >> u >> v >> w;
            cost[i][u] = min(cost[i][u], cost[i-1][v] + w);
            cost[i][v] = min(cost[i][v], cost[i-1][u] + w);
        }
    }
    cout << ((cost[t][e] >= INF) ? -1 : cost[t][e]);
    return 0;
}
