#include <iostream>
 
using namespace std;
 
const int SIZE = 100;
 
char ladder[SIZE][SIZE];
int dy[] = { -1, 1 };
 
bool Simulate(int sx, int sy, int ex, int ey);
 
int main(int argc, char** argv)
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    int T = 10;
 
    for (int test_case = 1; test_case <= T; ++test_case)
    {
        int n = 0;
 
        cin >> n;
 
        // 도착 지점
        int ex = 0, ey = 0;
 
        for (int i = 0; i < SIZE; ++i)
        {
            for (int j = 0; j < SIZE; ++j)
            {
                cin >> ladder[i][j];
 
                if (ladder[i][j] == '2')
                {
                    ex = i, ey = j;
                }
            }
        }
 
        for (int i = 0; i < SIZE; ++i)
        {
            if (ladder[0][i] == '1')
            {
                if (Simulate(0, i, ex, ey))
                {
                    cout << '#' << n << ' ' << i << '\n';
                    break;
                }
            }
        }
    }
 
    return 0;
}
 
bool Simulate(int sx, int sy, int ex, int ey)
{
    int x = sx, y = sy;
 
    while (x < SIZE - 1)
    {
        for (int i = 0; i < 2; ++i)
        {
            int ny = y + dy[i];
 
            if ((ny < 0) || (ny >= SIZE) || (ladder[x][ny] == '0'))
            {
                continue;
            }
 
            y = ny;
 
            while (true)
            {
                ny += dy[i];
 
                if ((ny < 0) || (ny >= SIZE) || (ladder[x][ny] == '0'))
                {
                    break;
                }
 
                y = ny;
            }
 
            break;
        }
 
        ++x;
    }
 
    return (x == ex) && (y == ey);
}
