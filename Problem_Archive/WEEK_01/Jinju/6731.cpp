#include <iostream>
#include <cstring>
using namespace std;

const int MAX = 1001;
int T, N, ret;
char grid[MAX][MAX];
int row[MAX];
int col[MAX];

void init() {
    memset(row, 0, sizeof(row));
    memset(col, 0, sizeof(col));
    ret = 0;
}

void count() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            int cnt = row[i] + col[j];
            if (grid[i][j] == 'B') cnt--; // 겹치는거 하나 빼기
            if (cnt % 2 == 1) ret++;
        }
    }
}


int main(void) {
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(0);

    cin >> T;
    for (int t = 1; t <= T; t++) {
        cin >> N;
        init();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cin >> grid[i][j];
                if (grid[i][j] == 'B') {
                    row[i]++;
                    col[j]++;
                }
            }
        }
        count();
        cout << "#" << t << " " << ret << endl;
    }
    return 0;
}
