#include <iostream>
#include <cstring>
#include <cmath>
using namespace std;
using ll = long long;
 
ll base9(const string &N) {
    bool flg = false;
    int st = 0;
    ll ret = 0;
    ll idx = 0;
 
    // 음수
    if (N[0] == '-') {
        flg = true;
        st = 1;
    }
 
    // 9진수 변환
    for (int i = N.length() - 1; i >= st; --i) {
        int cur = N[i] - '0';
 
        // 4보다 큰 숫자 1씩 빼줌
        if (cur > 4) cur -= 1;
        ret += cur * static_cast<ll>(pow(9, idx++));
    }
    return flg ? -ret : ret;
}
 
int main() {
    int T;
    cin >> T;
 
    for (int t = 1; t<= T; ++t) {
        string A, B;
        cin >> A >> B;
 
        ll A9 = base9(A);
        ll B9 = base9(B);
        ll ret;
 
        if (A9 < 0 && B9 > 0) ret = B9 - A9 - 1; // 0층 빼기
        else ret = B9 - A9;
        cout << "#" << t << " " << ret << "\n";
    }
    return 0;
}
