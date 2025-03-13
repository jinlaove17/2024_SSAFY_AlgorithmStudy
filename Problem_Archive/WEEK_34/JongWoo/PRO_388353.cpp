/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 지게차의 경우 n * n 맵을 순회하며, request에 해당하는 칸마다 bfs 탐색을 시작하여 맵의 범위를 벗어나는지 체크한다.
      이때, 곧바로 맵을 바꾸면 추후 탐색에 영향을 미치므로 범위를 벗어나는 곳의 좌표를 저장해 놓은 다음 모든 맵을 순회했을 때,
      빈 칸으로 바꿔준다.
      크레인은 그냥 n * n 맵을 순회하여 해당하는 칸을 빈 칸으로 바꿔주면 된다.
      위와 같은 과정을 거치면 답을 도출할 수 있다.

시간 복잡도
	- 매 요청마다 n x n의 맵을 순회하며 지게차의 경우, O(n^2), 크레인의 경우 O(1)의 시간을 사용해,
      시뮬레이션 하므로 O(n^4)의 시간 복잡도를 갖는다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 5.97ms
*/

#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <memory.h>

using namespace std;

const int MAX_SIZE = 50;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

int n, m;
bool isVisited[MAX_SIZE][MAX_SIZE];

int useForkLift(vector<string>& storage, char target);
bool bfs(vector<string>& storage, int sr, int sc);
int useCrane(vector<string>& storage, char target);

int solution(vector<string> storage, vector<string> requests)
{
    int answer = 0;

    n = static_cast<int>(storage.size());
    m = static_cast<int>(storage[0].size());
    answer = n * m;

    for (const string& req : requests)
    {
        if (req.length() == 1)
        {
            answer -= useForkLift(storage, req[0]);
        }
        else
        {
            answer -= useCrane(storage, req[0]);
        }

        // cout << "====================\n";
        // for (int r = 0; r < n; ++r)
        // {
        //     for (int c = 0; c < m; ++c)
        //     {
        //         cout << storage[r][c];
        //     }
        //     cout << "\n";
        // }
    }

    return answer;
}

int useForkLift(vector<string>& storage, char target)
{
    int ret = 0;
    vector<pair<int, int>> v;

    for (int r = 0; r < n; ++r)
    {
        for (int c = 0; c < m; ++c)
        {
            if (storage[r][c] == target)
            {
                if (bfs(storage, r, c))
                {
                    v.emplace_back(r, c);
                    ++ret;
                }
            }
        }
    }

    for (const pair<int, int>& pos : v)
    {
        storage[pos.first][pos.second] = ' ';
    }

    return ret;
}

bool bfs(vector<string>& storage, int sr, int sc)
{
    queue<pair<int, int>> q;
    bool isVisited[MAX_SIZE][MAX_SIZE] = {};

    isVisited[sr][sc] = true;
    q.emplace(sr, sc);

    while (!q.empty())
    {
        int r = q.front().first;
        int c = q.front().second;
        q.pop();

        for (int dir = 0; dir < DELTA_COUNT; ++dir)
        {
            int nr = r + DELTA[dir][0];
            int nc = c + DELTA[dir][1];

            if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= m))
            {
                return true;
            }
            else if ((storage[nr][nc] != ' ') || (isVisited[nr][nc]))
            {
                continue;
            }

            isVisited[nr][nc] = true;
            q.emplace(nr, nc);
        }
    }

    return false;
}

int useCrane(vector<string>& storage, char target)
{
    int ret = 0;

    for (int r = 0; r < n; ++r)
    {
        for (int c = 0; c < m; ++c)
        {
            if (storage[r][c] == target)
            {
                storage[r][c] = ' ';
                ++ret;
            }
        }
    }

    return ret;
}
