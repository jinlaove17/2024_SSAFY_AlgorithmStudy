#include <iostream>
#include <cmath>
using namespace std;
int T, N, M;
 
int main(void) {
    cin.tie(0);
    ios::sync_with_stdio(0);
    cin >> T;
 
    for (int t = 1; t <= T; t++) {
        cin >> N >> M;
        int mask = (1 << N) - 1;
        int ret = M & mask; //& 연산으로 마지막 자리 외에 다 삭제
        cout << "#" << t << " " << ((ret == mask) ? "ON\n": "OFF\n");
    }
    return 0;
}
