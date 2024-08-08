#include <iostream>
#include <unordered_set>
 
using namespace std;
 
const int N = 4;
 
char board[N][N];
unordered_set<int> us;
int dx[] = { -1, 1, 0, 0 };
int dy[] = { 0, 0, -1, 1 };
 
void DFS(int depth, int x, int y, int bit);
 
int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    int T = 0;
 
    cin >> T;
 
    for (int test_case = 1; test_case <= T; ++test_case)
    {
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < N; ++j)
            {
                cin >> board[i][j];
            }
        }
 
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < N; ++j)
            {
                DFS(0, i, j, 0);
            }
        }
 
        cout << '#' << test_case << ' ' << us.size() << '\n';
        us.clear();
    }
}
 
void DFS(int depth, int x, int y, int bit)
{
    if (depth >= 7)
    {
        us.insert(bit);
        return;
    }
 
    for (int i = 0; i < 4; ++i)
    {
        int nx = x + dx[i];
        int ny = y + dy[i];
 
        if ((nx < 0) || (nx >= N) || (ny < 0) || (ny >= N))
        {
            continue;
        }
 
        int tmp = bit, num = board[nx][ny] - '0';
 
        tmp |= (num << 4 * (depth));
        DFS(depth + 1, nx, ny, tmp);
    }
}
