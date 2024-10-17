/*
  문제 접근
  이 문제를 잘 풀려면 뇌를 뒤집는 과정이 필요하다
  보통 최단거리를 구하려고하면.. 일단 다익스트라와 같은 최단거리 알고리즘을 돌리고 생각하려는 습관이 있다.
  근데 일단 돌리면 안된다.

  우리는 가능한 최소 비용을 내고 싶다.
  그리고 K개의 간선 비용은 무료로 해준다 => 즉, K개 간선까지는 얼마의 간선이 들어오든 0원이라고 생각하면 된다.
  이거에 꽂히면 다익스트라를 돌리면서 계속 K개 만큼 비싼걸 빼주고 그 중에서 최소를 찾으려는 미친짓을 계속하게 된다.

  이걸 극복하려면 생각의 전환을 해야하는데, 바로 최소 금액 P원이 가능한 지를 결정해주는 결정 문제로 바꾸는 것이다.
  만약 P원을 최소 금액으로 내려면?
    - P보다 비싼 간선이 최대 K개까지만 있는 최단 경로일 경우 가능하다.
    - 해당하는 여부를 가중치 1로 둔다 (P보다 비쌀 때 가중치 +1, 아니면 0)
    - 마지막 다익스트라를 다 돌고 났을 때, 도착 정점에서 dist[N] <= K를 만족하면
    - 해당 Price는 우리가 낼 수 있는 Price이다.
    
    => 이 Price를 이분 탐색을 통해 범위를 좁혀서 최소 Price를 찾으면 된다

  알고리즘: 다익스트라 + 이분 탐색(Price 결정 후 다익스트라를 돌려서 가능 여부 판별)

  시간 복잡도: O(log(maxCost)×PlogN) 이분 탐색 * 다익스트라 만큼의 시간 복잡도이다

  실행 시간: 4ms
  
*/

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#define INF 1e7+1
using namespace std;
using ll = long long;
using pi = pair<int, int>;

int N, P, K;
int ret = -1;
vector<pi> adj[1001];

bool dijkstra(int start, int price){
    vector<ll> dist(N+1, INF);
    priority_queue<pi, vector<pi>, greater<pi>> PQ;
    dist[start] = 0;

    PQ.push({0, start});

    while(!PQ.empty()){
        int curDist = PQ.top().first;
        int curNode = PQ.top().second;
        PQ.pop();

        if(curDist > dist[curNode]) continue;

        for(auto &edge: adj[curNode]){
            int nxtNode = edge.first;
            int nxtDist = curDist + (edge.second > price) ;

            if(nxtDist < dist[nxtNode]){
                dist[nxtNode] = nxtDist;
                PQ.push({nxtDist, nxtNode});
            }
        }
    }
    return dist[N] <= K;
}

int main() {
    cin.tie(0)->ios::sync_with_stdio(0);
    int maxCost = 0;

    cin >> N >> P >> K;
    for(int i=0; i<P; i++){
        int a, b, w;
        cin >> a >> b >> w;
        adj[a].push_back({b, w});
        adj[b].push_back({a, w});
        maxCost = max(maxCost, w);
    }

    // 최소 price 이분 탐색(mid)로 찾기
    int l = 0;
    int r = maxCost;
    while(l <= r){
        int mid = (l+r)/2;
        if (dijkstra(1, mid)){
            ret = mid;
            r = mid-1;
        }
        else{
            l = mid+1;
        }
    }
    cout << ret;
    return 0;
}
