#include <iostream>
#include <cstring>
using namespace std;
constexpr size_t MAX_NUM = 1001;

int startR[MAX_NUM] = { 0 };
int endR[MAX_NUM] = { 0 };
int cnt[201] = { 0 }; // 복도에서 겹치는 수 count

int maxNum() {
    int num = 0;
    for (int i = 0; i < 200; i++) {
        if (cnt[i] > num) num = cnt[i];
    }
    return num;
}

int main() {
    ios_base::sync_with_stdio(0);
    cin.tie(0);

    int T, N, start, end;
    cin >> T;

    for (int t = 1; t <= T; t++) {
        cin >> N;
        memset(cnt, 0, sizeof(cnt));

        for (int i = 0; i < N; i++) {
            cin >> start >> end;
            if (start > end) {
                startR[i] = (end / 2) + (end % 2);
                endR[i] = (start / 2) + (start % 2);
            }
            else {
                startR[i] = (start / 2) + (start % 2);
                endR[i] = (end / 2) + (end % 2);
            }
        }

        for (int i = 0; i < N; i++) {
            int curStart, curEnd;
            curStart = startR[i];
            curEnd = endR[i];
            for (int j = curStart; j <= curEnd; j++) {
                cnt[j] += 1;
            }
        }
        int ret = maxNum();
        cout << '#' << t << ' ' << ret << "\n";
    }
}

