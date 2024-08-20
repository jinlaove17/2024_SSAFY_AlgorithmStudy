#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <cstring>
 
using namespace std;
int ret = 0;
int breath[101] = { 0, };
int maxB = 0;
 
void bfs(int start, vector<vector<int>> &graph) {
    queue<pair<int, int>> Q; // (노드, 깊이)
 
    Q.push({start, 0 });
    breath[start] = 0;
 
    while (!Q.empty()) {
        int node = Q.front().first;
        int bth = Q.front().second;
        Q.pop();
 
        // 현재 노드의 깊이 마킹 및 최대 깊이 갱신
        breath[node] = bth;
        maxB = max(maxB, bth);
 
        for (auto next : graph[node]) {
            if (breath[next] == 0 && next != start) { // 아직 방문하지 않은 노드
                Q.push({ next, bth + 1 });
                breath[next] = bth + 1; // 방문 표시
            }
        }
    }
}
 
int main(void) {
    cin.tie(0);
    ios::sync_with_stdio(0);
 
    int T = 10;
    for (int t = 1; t <= T; t++) {
        int L, S;
 
        //초기화
        vector<vector<int>> graph(101);
        memset(breath, 0, sizeof(breath));
        ret = 0;
        maxB = 0;
         
        cin >> L >> S;
        for (int i = 0; i < L / 2; i++) {
            int from, to;
            cin >> from >> to;
            graph[from].push_back(to); //단방향 간선
        }
 
        bfs(S, graph);
         
        for (int i = 0; i < 101; i++) {
            if (breath[i] == maxB) ret = max(ret, i);
        }
 
        cout << "#" << t << " " << ret << "\n";
    }
}
