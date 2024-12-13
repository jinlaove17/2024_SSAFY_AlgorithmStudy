#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
using vi = vector<int>;
int T, L, N;
int ret = 0;

/* 문제 접근
  처음에는 구간이 정렬되어서 들어오는 걸 보고 sweeping을 통해 구간을 쓸어주듯이 슬라이딩 윈도우로 푸는 줄 알았다.
  그러나 시도했을 때 바로 TLE 나서 큰 범위를 줄이고자 이분 탐색을 써야겠다는 생각을 했다.
  구체적으로 구현을 어떻게 해야할지 어려워서 이미 푼 사람의 도움을 받아서 풀었다.

  1. 누적합을 이용해 O(1) 만에 특정 구간 합 계산하기
  2. upper_bound를 사용해 탐색 범위를 제한할 수 있다.
   -> upper_bound 를 통해 L 범위 내 마지막 포함 가능 구간을 빠르게 찾을 수 있다.
   -> O(log N)에 해당 범위를 결정할 수 있다. (시간 복잡도)
  부분 구간을 조정해야 하는 일이 생길 때: 마지막 포함 구간의 길이가 L을 초과할 경우, 초과한 부분을 제외한 길이만 합산해야 함
  min(ed[pos]-st[pos], range-st[pos]) 를 사용해 이 부분을 처리한다.
*/
 
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
