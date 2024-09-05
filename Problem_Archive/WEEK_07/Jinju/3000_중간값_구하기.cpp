#include <iostream>
#include <queue>
#include <vector>
using namespace std;
using ll = long long;
 
const int MOD = 20171109;
int T, N, A;
 
int main() {
    cin.tie(0);
    ios::sync_with_stdio(0);
    cin >> T;
 
    for (int t = 1; t <= T; ++t) {
        cin >> N >> A;
 
        priority_queue<int> maxH;
        priority_queue<int, vector<int>, greater<int>> minH;
        maxH.push(A);
 
        ll ret = 0;
 
        for (int i = 0; i < N; ++i) {
            int l, r;
            cin >> l >> r;
 
            // 새 숫자를 적절한 힙에 삽입
            if (maxH.empty() || l <= maxH.top()) {
                maxH.push(l);
            }
            else {
                minH.push(l);
            }
 
            if (maxH.empty() || r <= maxH.top()) {
                maxH.push(r);
            }
            else {
                minH.push(r);
            }
 
            // 힙 크기 조정
            if (maxH.size() > minH.size() + 1) {
                minH.push(maxH.top());
                maxH.pop();
            }
            else if (minH.size() > maxH.size()) {
                maxH.push(minH.top());
                minH.pop();
            }
 
            ret = (ret + maxH.top()) % MOD;
        }
 
        // 최대 힙의 루트가 중간값
        cout << "#" << t << " " << ret << "\n";
    }
    return 0;
}
