#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;
 
int T, N, K;
 
int main() {
    cin.tie(0);
    ios::sync_with_stdio(0);
 
    cin >> T;
    for (int t = 1; t <= T; ++t) {
        cin >> N >> K;
        vector<int> arr(N);
        vector<int> diff(N-1);
 
        for (int i = 0; i < N; ++i) {
            cin >> arr[i];
 
            if (i==0) continue;
            else diff[i-1] = arr[i] - arr[i-1];
        }
 
        sort(diff.begin(), diff.end());
        int ret = 0;
        for(int i=0; i < N-K; ++i) {
            ret += diff[i];
        }
        cout << "#" << t << " " << ret << "\n";
    }
}
