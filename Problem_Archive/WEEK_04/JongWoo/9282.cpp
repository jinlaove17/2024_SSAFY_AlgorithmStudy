#include <iostream>
#include <cstring>

using namespace std;

const int INF = 0x3fffffff;
const int MAX_SIZE = 52;

int n, m;
int board[MAX_SIZE][MAX_SIZE];
int total[MAX_SIZE][MAX_SIZE]; // 누적합
int dp[MAX_SIZE][MAX_SIZE][MAX_SIZE][MAX_SIZE]; // dp[r][c][w][h] (r, c)에서 w x h 크기의 블록을 만들 때 지불해야 하는 건포도의 최소 양

int Divide(int rowSize, int colSize, int r, int c);

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int T = 0;

    cin >> T;

    for (int test_case = 1; test_case <= T; ++test_case)
    {
        cin >> n >> m;

        for (int i = 1; i <= n; ++i)
        {
            for (int j = 1; j <= m; ++j)
            {
                cin >> board[i][j];
            }
        }

        memset(dp, 0, sizeof(dp));

        for (int i = 1; i <= n; ++i)
        {
            for (int j = 1; j <= m; ++j)
            {
                total[i][j] = total[i - 1][j] + total[i][j - 1] - total[i - 1][j - 1] + board[i][j];
            }
        }

        cout << '#' << test_case << ' ' << Divide(n, m, 1, 1) << '\n';
    }
}

int Divide(int rowSize, int colSize, int r, int c)
{
    if ((rowSize == 1) && (colSize == 1))
    {
        return 0;
    }
    else if (dp[r][c][rowSize][colSize] == 0)
    {
        int ret = INF;
        int cost = total[r + rowSize - 1][c + colSize - 1] - (total[r + rowSize - 1][c - 1] + total[r - 1][c + colSize - 1]) + total[r - 1][c - 1];

        // 1. 행 단위로 자르기
        for (int i = 1; i < rowSize; ++i)
        {
            ret = min(ret, Divide(i, colSize, r, c) + Divide(rowSize - i, colSize, r + i, c) + cost);
        }

        // 2. 열 단위로 자르기
        for (int i = 1; i < colSize; ++i)
        {
            ret = min(ret, Divide(rowSize, i, r, c) + Divide(rowSize, colSize - i, r, c + i) + cost);
        }

        dp[r][c][rowSize][colSize] = ret;
    }

    return dp[r][c][rowSize][colSize];
}
