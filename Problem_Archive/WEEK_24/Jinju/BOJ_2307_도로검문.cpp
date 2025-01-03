#include <iostream>
#include <queue>
#include <vector>
using namespace std;
using ll = long long;
using pii = pair<int, int>;
using vi = vector<int>;
using vpii = vector<pii>;
const int MAX = 5000 * 10000 + 1;

/* 문제 접근
    문제에서 원하는 것은 최단 경로로 갔을 때의 시간을 기준으로
    그 기준에서 얼마나 최대로 지연시킬 수 있는지를 봐야한다.

    여기서 여러 간선을 막을(제외하고) 시나리오를 생각해보면,
    최단 경로에 속하지 않은 간선을 막는 경우, 그냥 최단 경로를 이용해버리면?
    지연 시간 차이가 0이 되기 때문에 아무리 막아도 소용이 없다.
    => 즉, 최단 경로 안에 속한 간선들을 하나씩 지우면서 최대 지연 시간을 찾아야 한다.
    => 최단 경로를 역추적하면서 찾아주자

    알고리즘: 다익스트라 + 경로 역추적
    시간 복잡도: O(E^2 * logE), 간선 delete 작업으로 E만큼 더 곱해야 한다.
    소요 시간: 88ms
 */

int N, M;
pii init = {0,0 };
vector<vpii> edges(1001);
int path[1001]; // 정점 별 이전 정점 역추적하는 배열

int dijkstra(int start, int end, pii edge) {
    vector<int> dist(N+1, MAX); // dist MAX로 초기화
    priority_queue<pii, vector<pii>, greater<pii>> PQ;

    dist[start] = 0;
    PQ.push({0, start}); // 가중치, curNode

    while(!PQ.empty()) {
        int noww = PQ.top().first; //now weight
        int nown = PQ.top().second; // now node
        PQ.pop();
        if (noww > dist[nown]) continue; // 최단거리 아닐 때 통과

        for(auto &nxt: edges[nown]) {
            int nxtn = nxt.first;
            int nxtw = nxt.second;

            pii newEdge1 = make_pair(nown, nxtn);
            pii newEdge2 = make_pair(nxtn, nown);

            if(edge == newEdge1 || edge == newEdge2) continue; // 제외 간선일 때 통과

            if (dist[nxtn] > noww + nxtw) {
                dist[nxtn] = noww + nxtw;
                PQ.push(make_pair(dist[nxtn], nxtn));

                if (edge == init) path[nxtn] = nown; // 최단 경로 역추적
            }

        }
    }
    return dist[end];
}

int deleteEdges() {
    int minDist = dijkstra(1, N, init);
    int maxDist = 0; // 초기화

    // 현재 i 간선부터 path[i] 로 역추적하면서 edges 하나씩 지워보기
    for(int i=N; i !=0 ; i = path[i]) {
        maxDist = max(maxDist, dijkstra(1, N, {i, path[i]}));
    }

    if (maxDist == MAX) return -1;
    else return maxDist-minDist;
}

int main() {
    cin.tie(0)->sync_with_stdio(0);
    cin >> N >> M;
    for(int i=0; i<M; ++i) {
        int a, b, t;
        cin >> a >> b >> t;
        edges[a].emplace_back(b, t);
        edges[b].emplace_back(a, t);
    }
    cout << deleteEdges() << "\n";
    return 0;
}
