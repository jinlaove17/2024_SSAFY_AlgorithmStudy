#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
using ll = long long;
 
const int INF = (int)2e9;
int T;
int ret = INF;
bool vst[401];
 
//최대 노드 개수 400개
vector<pair<int, int>> graph[401];
 
void dfs(int node, int dist, int start) {
 
    // 이전에 간 적 없는 노드 방문 처리
    vst[node] = true;
 
    for (int i = 0; i < graph[node].size(); i++) {
        int nxt = graph[node][i].first;
        int curDist = graph[node][i].second;
 
        if (!vst[nxt]) {
            // 전체 도로 길이 합보다 누적 dist가 더 클 때 가지치기
            if (dist + curDist < ret) dfs(nxt, dist + curDist, start);
        } else {
            if (nxt == start) ret = min(ret, dist + curDist);
        }
    }
}
 
int main(void) {
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(0);
 
    cin >> T;
    for (int t = 0; t < T; t++) {
        int N, M;
        cin >> N >> M;
 
        // 초기화
        for (int i = 0; i < 401; i++) {
            graph[i].clear();
            vst[i] = false;
        }
        ret = INF;
 
        //입력 받기
        for (int m = 0; m < M; m++) {
            int s, e, c;
            cin >> s >> e >> c;
            graph[s].push_back({ e, c });
 
            // 출발 지점과 도착 지점이 같은 경우
            // 바로 사이클에 해당하므로 ret을 갱신하여 빠르게 처리
            if (s == e) ret = min(ret, c);
        }
 
        // 모든 노드에서 dfs 탐색 시작
        for (int i = 1; i <= N; i++) {
            fill(vst, vst + 401, false);
            dfs(i, 0, i);
        }
        cout << "#" << (t+1) << " " << (ret == INF ? -1 : ret) << "\n";
    }
    return 0;
}
