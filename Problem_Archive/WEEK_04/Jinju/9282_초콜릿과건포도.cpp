#include <iostream>
#include <cstring>
using namespace std;
const int MAX = 1000000000;

int T, N, M;
int ret = 0;
int dp[51][51][51][51];
int grid[51][51];
int preS[51][51];

// (x,y) 부터 (goalx, goaly) 까지의 2차원 누적합 구하고 dp에 저장
int cutDP(int x, int y, int goalx, int goaly) {

    //조각이 1개일 때 리턴
    if (x == goalx && y == goaly) return 0;

    //dp 테이블 채워져 있다면 값을 리턴
    if (dp[x][y][goalx][goaly] != -1) {
        return dp[x][y][goalx][goaly];
    }

    //(x, y)부터 (goalx, goaly)까지 누적합
    int cnt = preS[goalx][goaly] - preS[x-1][goaly] - preS[goalx][y-1] + preS[x-1][y-1];

    int tmp;
    int subP = MAX;

    //가로 cut
    for (int i = x; i < goalx; i++) {
        tmp = cutDP(x, y, i, goaly) + cutDP(i+1, y, goalx, goaly);
        subP = min(subP, tmp+cnt);
    }

    //세로 cut
    for (int j = y; j < goaly; j++) {
        tmp = cutDP(x, y, goalx, j) + cutDP(x, j+1, goalx, goaly);
        subP = min(subP, tmp+cnt);
    }

    //dp 테이블에 저장 후 리턴
    dp[x][y][goalx][goaly] = subP;
    return dp[x][y][goalx][goaly];

}

int main() {
    cin.tie(0);
    ios_base::sync_with_stdio(false);

    cin >> T;

    for (int t = 1; t <= T; t++) {
        //최소값 저장하는 dp배열 초기화
        memset(dp, -1, sizeof(dp));

        cin >> N >> M;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                cin >> grid[i][j];
            }
        }

        //누적합 구하기
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                preS[i][j] = preS[i-1][j] + preS[i][j-1] - preS[i-1][j-1] + grid[i][j];
            }
        }

        ret = cutDP(1, 1, N, M);
        cout << "#" << t << " " << ret << '\n';
    }

    return 0;
}
