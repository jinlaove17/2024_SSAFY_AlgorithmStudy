/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 수비는 해당 칸의 도미노를 세워주기만 하면 되기 때문에 공격 로직만 구현하면 되는 문제였다.
      처음에는 공격된 칸의 방향으로 나아가면서 이전 도미노들이 현재 칸에 닿는지를 판별하면서 구현했는데 잘 풀리지 않았다.
      그래서 간단하게 이번 턴에 쓰러질 도미노를 체크할 2차원 bool 배열을 하나 더 선언한 뒤, 공격된 칸부터 길이만큼 flag를 바꿔주면서
      쓰러지지 않은 도미노를 만날 때까지 이동하여 이번턴에 쓰러진 도미노의 개수를 구할 수 있었다.
      위 과정을 거치면 답을 도출할 수 있다.

시간 복잡도
	- 맵이 최대 100 x 100이고 r번 시뮬레이션하며 maX(n, m)번 확인을 해야하므로 O(N^2)의 시간 복잡도를 갖는다.

실행 시간
	- 4ms
*/

#include <iostream>

using namespace std;

const int MAX_BOARD_SIZE = 100;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

int n, m, r, answer;
int board[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1];
char boardStatus[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1];

void Attack(int x, int y, char dir);
void Defense(int x, int y);

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> n >> m >> r;

    for (int i = 1; i <= n; ++i)
    {
        for (int j = 1; j <= m; ++j)
        {
            cin >> board[i][j];
            boardStatus[i][j] = 'S';
        }
    }

    for (int i = 0; i < r; ++i)
    {
        // 공격수 처리
        int x = 0, y = 0;
        char dir = 0;
        cin >> x >> y >> dir;
        Attack(x, y, dir);

        // 수비수 처리
        cin >> x >> y;
        Defense(x, y);
    }

    cout << answer << '\n';
    for (int i = 1; i <= n; ++i)
    {
        for (int j = 1; j <= m; ++j)
        {
            cout << boardStatus[i][j] << ' ';
        }
        cout << '\n';
    }
}

void Attack(int x, int y, char dir)
{
    // 이미 넘어진 격자의 도미노를 공격수가 넘어뜨리려 할 때에는 아무 일도 일어나지 않는다.
    if (boardStatus[x][y] == 'F')
    {
        return;
    }

    int d = -1;
    switch (dir)
    {
    case 'E':
        d = 0;
        break;
    case 'W':
        d = 1;
        break;
    case 'S':
        d = 2;
        break;
    case 'N':
        d = 3;
        break;
    }

    bool check[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1] = {};
    int cx = x, cy = y, sum = 0;
    check[cx][cy] = true;

    while (check[cx][cy])
    {
        if (boardStatus[cx][cy] == 'S')
        {
            boardStatus[cx][cy] = 'F';
            ++sum;

            for (int i = 1; i < board[cx][cy]; ++i)
            {
                int nx = cx + DELTA[d][0] * i;
                int ny = cy + DELTA[d][1] * i;

                if ((nx < 1) || (nx > n) || (ny < 1) || (ny > m))
                {
                    break;
                }

                check[nx][ny] = true;
            }
        }

        cx += DELTA[d][0];
        cy += DELTA[d][1];
    }

    answer += sum;
}

void Defense(int x, int y)
{
    // 만약 넘어지지 않은 도미노를 수비수가 세우려고 할 때에도 아무 일도 일어나지 않는다.
    if (boardStatus[x][y] == 'S')
    {
        return;
    }

    boardStatus[x][y] = 'S';
}
