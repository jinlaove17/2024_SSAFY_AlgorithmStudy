#include <iostream>
#include <vector>
#include <queue>
using namespace std;
using pii = pair<int, int>;
using vi = vector<int>;
const int MAX = 53;

int N;
int ret = 0;
vi adj[MAX];
bool vst[MAX];
int cap[MAX][MAX];
int flow[MAX][MAX];

int findMaxFlow(int source, int sink){
    int totalFlow = 0;

    // 증가 경로 없을 때까지 반복, BFS
    while(true){
        vector<int> parent(MAX, -1); 
        parent[source] = source;
        queue<int>q;
        q.push(source);

        while(!q.empty() && parent[sink] == -1){
            int cur = q.front();
            q.pop();
            for(int nxt : adj[cur]){
                if(cap[cur][nxt] - flow[cur][nxt] > 0 && parent[nxt] == -1){
                    parent[nxt] = cur;
                    q.push(nxt);
                    if(nxt == sink) break; 
                }
            }
        }

        if(parent[sink] == -1) break;

        int amount = 1e9;
        int v = sink;
        while(v != source){
            int u = parent[v];
            amount = min(amount, cap[u][v] - flow[u][v]);
            v = u;
        }

        v = sink;
        while(v != source){
            int u = parent[v];
            flow[u][v] += amount;
            flow[v][u] -= amount;
            v = u;
        }
        totalFlow += amount;
    }

    return totalFlow;
}

int changeChar2Int(char c){
    int ret = 0;
    if(c >= 'a') ret = c-'a'+26;
    else ret = c-'A';
    return ret;
}

int main() {
    cin.tie(0)->sync_with_stdio(0);
    cin >> N;
    for(int i=0; i<N; ++i){
        int w;
        char a, b;
        cin >> a >> b >> w;

        int aidx = changeChar2Int(a);
        int bidx = changeChar2Int(b);
        adj[aidx].push_back(bidx);
        adj[bidx].push_back(aidx);
        cap[aidx][bidx] += w;
        cap[bidx][aidx] += w;
    }
    cout << findMaxFlow(0, 25) << "\n";
    return 0;
}
