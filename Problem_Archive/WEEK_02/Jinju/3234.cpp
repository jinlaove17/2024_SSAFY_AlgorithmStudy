#include<iostream>
#include<cstring>
using namespace std;
const int MAX = 10;
 
int N, ret, maxW;
int weight[MAX];
bool used[MAX];
int expo[MAX] = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512 };
int fact[MAX] = { 0, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880 };
 
void dfs(int cnt, int left, int right) {
 
    if (cnt == N) { // 모든 무게추를 올린 경우
        ret++;
        return;
    }
 
    if (maxW - left <= left) {
        // 남은 추를 어떻게 올려도 상관 없는 경우 
        ret += expo[N - cnt] * fact[N - cnt];
        return;
    }
 
    for (int i = 0; i < N; i++) {
 
        if (used[i]) continue;
 
        used[i] = true;
        dfs(cnt + 1, left + weight[i], right);
 
        if (right + weight[i] <= left) {
            dfs(cnt + 1, left, right + weight[i]);
        }
             
        used[i] = false;
    }
}
 
int main() {
    cin.tie(0);
    cout.tie(0);
    ios_base::sync_with_stdio(0);
 
    int T; 
    cin >> T;
 
    for (int t = 1; t <= T; t++) {
 
        ret = 0;
        maxW = 0;
        memset(used, false, sizeof(used));
 
        cin >> N;
        for (int i = 0; i < N; i++) {
            cin >> weight[i];
            maxW += weight[i];
        }
         
        dfs(0, 0, 0);
        cout << "#" << t << " " << ret << "\n";
    }
    return 0;
}
