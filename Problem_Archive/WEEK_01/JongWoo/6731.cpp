#include <iostream>
#include <cstring>
 
using namespace std;
 
const int MAX = 1'000;
 
int n;
char board[1'002][1'002];
int row[MAX]; // row[n]: n행에 있는 검은 돌의 개수
int col[MAX]; // col[n]: n열에 있는 검은 돌의 개수
 
int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    int T = 0;
 
    cin >> T;
 
    for (int test_case = 1; test_case <= T; ++test_case)
    {
        cin >> n;
 
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                cin >> board[i][j];
 
                if (board[i][j] == 'B')
                {
                    ++row[i];
                    ++col[j];
                }
            }
        }
 
        int answer = 0;
 
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                int cnt = row[i] + col[j];
 
                if (board[i][j] == 'B')
                {
                    --cnt;
                }
 
                if (cnt % 2 == 1)
                {
                    ++answer;
                }
            }
        }
 
        cout << '#' << test_case << ' ' << answer << '\n';
        memset(row, 0, sizeof(row));
        memset(col, 0, sizeof(col));
    }
}
