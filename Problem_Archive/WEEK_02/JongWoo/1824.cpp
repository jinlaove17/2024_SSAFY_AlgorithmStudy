#include <iostream>
 
using namespace std;
 
int r, c;
int dx[] = { -1, 1, 0, 0 };
int dy[] = { 0, 0, -1, 1 };
char board[20][20];
bool isVisited[20][20][16][4]; // [x][y][mem][dir]
 
void Simulate(int x, int y, int dir, int mem, bool& isStoppable);
bool IsIn(int x, int y);
 
int main(int argc, char** argv)
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    int T = 0;
 
    cin >> T;
 
    for (int test_case = 1; test_case <= T; ++test_case)
    {
        cin >> r >> c;
 
        bool isStoppable = false;
 
        for (int i = 0; i < r; ++i)
        {
            for (int j = 0; j < c; ++j)
            {
                cin >> board[i][j];
 
                if (board[i][j] == '@')
                {
                    isStoppable = true;
                }
 
                for (int k = 0; k < 16; ++k)
                {
                    for (int l = 0; l < 4; ++l)
                    {
                        isVisited[i][j][k][l] = false;
                    }
                }
            }
        }
 
        if (isStoppable)
        {
            isStoppable = false;
            Simulate(0, 0, 3, 0, isStoppable);
        }
 
        cout << '#' << test_case << ' ' << ((isStoppable) ? "YES\n" : "NO\n");
    }
 
    return 0;
}
 
void Simulate(int x, int y, int dir, int mem, bool& isStoppable)
{
    if (isStoppable)
    {
        return;
    }
 
    if (!IsIn(x, y))
    {
        switch (dir)
        {
        case 0:
            x += r;
            break;
        case 1:
            x -= r;
            break;
        case 2:
            y += c;
            break;
        case 3:
            y -= c;
            break;
        }
    }
 
    if (isVisited[x][y][mem][dir])
    {
        return;
    }
 
    isVisited[x][y][mem][dir] = true;
 
    switch (board[x][y])
    {
    case '<':
        dir = 2;
        break;
    case '>':
        dir = 3;
        break;
    case '^':
        dir = 0;
        break;
    case 'v':
        dir = 1;
        break;
    case '_':
        dir = (mem == 0) ? 3 : 2;
        break;
    case '|':
        dir = (mem == 0) ? 1 : 0;
        break;
    case '?':
        for (int i = 0; i < 4; ++i)
        {
            Simulate(x + dx[dir], y + dy[dir], dir, mem, isStoppable);
            dir = (dir + 1) % 4;
        }
        return; // switch문 내에서 재귀호출을 했으므로 break가 아닌 return을 해주어야 함
    case '.':
        break;
    case '@':
        isStoppable = true;
        return; // 프로그램의 실행을 정지한다.
    case '+':
        mem = (mem + 1) % 16;
        break;
    case '-':
        mem = (mem - 1 + 16) % 16;
        break;
    default: // 0 ~ 9
        mem = board[x][y] - '0';
        break;
    }
 
    Simulate(x + dx[dir], y + dy[dir], dir, mem, isStoppable);
}
 
bool IsIn(int x, int y)
{
    return (0 <= x) && (x < r) && (0 <= y) && (y < c);
}
