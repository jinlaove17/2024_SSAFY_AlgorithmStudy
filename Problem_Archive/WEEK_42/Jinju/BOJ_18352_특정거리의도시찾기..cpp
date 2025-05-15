// BFS를 통한 최단 거리 갱신 후, 특정 dist[i] 값에 해당하는 것만 배열에 담아서 출력한다.

#include <bits/stdc++.h>
#define INF 2147483647
using namespace std;
using ll = long long;

queue<pair<int, int>> Q;
vector<pair<int, int>> graph[300001];
int visited[300001];
int dist[300001];
int N, M, K, X;

void BFS(int start){
    dist[start] = 0;

    Q.push({0, start});
    while(!Q.empty()){
        int node = Q.front().second;
        int d = Q.front().first;
        Q.pop();

        for(int i=0; i < graph[node].size(); i++){
            int new_node = graph[node][i].first;
            int new_d = graph[node][i].second;
            if(visited[new_node] == 0){
                if(dist[new_node] > dist[node] + new_d){
                    dist[new_node] = dist[node] + new_d;
                    Q.push({dist[new_node], new_node});
                }
            }
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin >> N >> M >> K >> X;
    for(int i =0; i < M; i++){
        int u, v;
        cin >> u >> v;
        graph[u].push_back({v, 1}); //단방향 간선
    }

    for(int i =1; i <= N; i++) dist[i] = INF;
    BFS(X);

    vector<int> ret;
    for(int i =1; i <= N; i++){
        if(dist[i] == K){
            ret.push_back(i);
        }
    }

    if (ret.size() == 0) cout << -1;
    else{
        sort(ret.begin(), ret.end());
        for (int i=0; i < ret.size(); i++){
            cout << ret[i] << "\n";
        }
    }
    return 0;
}
