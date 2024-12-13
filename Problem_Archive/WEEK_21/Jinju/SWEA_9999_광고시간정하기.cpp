#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
using vi = vector<int>;
int T, L, N;
int ret = 0;
 
int main() {
    cin.tie(0)->sync_with_stdio(0);
    cin >> T;
 
    for (int tc = 1; tc <= T; ++tc) {
        cin >> L >> N;
        vi st(N+1), ed(N+1), preSum(N+1);
        ret = 0;
 
        for(int i=1; i<=N; ++i) cin >> st[i] >> ed[i];
        for(int i=1; i<=N; ++i) {
            preSum[i] = ed[i] - st[i];
            preSum[i] += preSum[i-1]; //이전 누적합
        }
         
        for(int i=1; i<=N; ++i) {
            int range = st[i] + L;
            int pos = upper_bound(st.begin(), st.end(), range) - st.begin();
            pos--;
            int tmpSum = preSum[pos-1] - preSum[i-1];
            tmpSum += min(ed[pos]-st[pos], range - st[pos]);
 
            ret = max(ret, tmpSum);
        }
        cout << "#" << tc << " " << ret << "\n";
    }
    return 0;
}
