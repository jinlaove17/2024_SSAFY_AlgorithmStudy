#include <iostream>
#include <algorithm>
using namespace std;

// 문제 접근
// 매 i번째 turn 마다 + or - 를 선택해줘야한다.
// 이 선택에 대해서 최종적으로 가능한 max volumn을 찾아야하는데,
// 처음에는 매 턴을 기준으로 생각하다가, 매번 최대값을 유지해줘야하는 것으로 착각했다.
// 그러나 매 턴 최대값으로 dp 테이블을 유지하는 것은 dp가 아니라 그리디적인 생각이다.
// 매 턴 가능한 경우의 max를 구하는 것이므로, 반례를 금방 구할 수 있다.
//   => 만약, 계속 볼륨을 줄이다가 마지막에 한계에 딱 맞는 숫자가 오면 그걸 택하는 경우가 최적이다.
// 따라서, dp의 기본인 상태(state)와 전이(transition)을 잘 생각해봐야한다.
// 우리는 가능한 volume의 상태를 dp table의 field로 놓고 volume 상태의 전이(+/-)를 체크해주기만 하면 된다.

int N, S, M;
int arr[51];
bool dp[51][1001]; // dp[i][j] : i번째에서 j 볼륨이 가능한지 여부
int ret = -1;

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cin >> N >> S >> M;

    for (int i = 0; i < N; i++) {
        cin >> arr[i];
    }

    // 초기 상태: S에서 - 혹은 + 했을 때 가능한지 체크
    dp[0][S] = true;

    // DP 갱신: i번째 음원을 처리하는데 가능한 볼륨을 갱신
    for (int i = 0; i < N; i++) {
        for (int j = 0; j <= M; j++) {
            if (dp[i][j]) {
                if (j - arr[i] >= 0) dp[i+1][j - arr[i]] = true;
                if (j + arr[i] <= M) dp[i+1][j + arr[i]] = true;
            }
        }
    }

    // max volumn
    for (int j = M; j >= 0; j--) {
        if (dp[N][j]) {
            ret = j;
            break;
        }
    }

    cout << ret << "\n";
    return 0;
}
