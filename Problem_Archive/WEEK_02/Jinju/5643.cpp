#include <iostream>
#include <vector>
 
using namespace std;
int T;
const int INF = 250000;
int graph[501][501] = { INF, };
 
void init() {
    for (int i = 0; i < 501; i++) {
        for (int j = 0; j < 501; j++) {
            graph[i][j] = INF;
        }
    }
}
 
void FW(int N) {
    for (int r=1; r <= N; r++) {
        for (int p =1; p <= N; p++) {
            for (int q =1; q <= N; q++) {
                if (graph[p][q] > graph[p][r] + graph[r][q]) {
                    graph[p][q] = graph[p][r] + graph[r][q];
                }
            }
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
        cin >> N;
        cin >> M;
        init();
 
        for (int m = 0; m < M; m++) {
            int a, b;
            cin >> a >> b;
            graph[a][b] = 1;
        }
 
        FW(N);
        vector<int> ret(N+1);
 
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (graph[i][j] < INF) {
                    ret[i] += 1;
                    ret[j] += 1;
                } 
            }
        }
 
        int cnt = 0;
        for (int i = 1; i <= N; i++) {
            if (ret[i] == N - 1) cnt++;
        }
        cout << "#" << (t + 1) << " " << cnt << "\n";
    }
    return 0;
}
