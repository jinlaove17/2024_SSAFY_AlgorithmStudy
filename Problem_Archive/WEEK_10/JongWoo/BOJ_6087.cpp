/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제를 처음에 읽었을 때 단순히 BFS 탐색으로 시작점에서 도착점까지 갈 경우 답을 도출할 수 있는지 고민했는데,
      거리가 더 짧지만 꼬불꼬불하게 꺾어서 가는 경우는 거리는 멀지만 최소한으로 꺾어서 가는 경우가 존재할 경우 답이 될 수 없으므로 답이 될 수 없다.
    - 그렇기 때문에 각 칸에 도착했을 때의 방향을 별도로 관리하고, 몇 개의 거울을 사용하여 도착했는지를 저장해야겠다는 생각이 들었다.
    - 즉, 이미 방문한 위치에 더 적은 거울로 방문하는 경우가 존재할 수 있다고 판단한 것이다.
    - 이는 W X H X DIR 크기의 3차원 배열로 관리하고, Node 구조체를 통해 탐색을 진행하였다.
    - 만약 다음 이동 방향이 현재 방향과 꺾인 방향이라면 거울의 개수를 1씩 늘려가며 최종적으로 minCounts[er][ec][0] ~ minCounts[er][ec][4] 값 중 최솟값을 택하면 된다.

시간 복잡도
    - 최악의 경우 W * H 맵을 DIR번 순회하므로 O(W * H * DIR)의 시간 복잡도를 갖는다.

실행 시간
    - 0ms(C++)
*/

#include <iostream>
#include <queue>

using namespace std;

const int INF = 0x3fffffff;
const int MAX_SIZE = 100 + 1;
const int DIR = 4;
const int DELTA[][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

struct Node
{
    int r;
    int c;
    int dir;
};

int w, h;
char board[MAX_SIZE][MAX_SIZE];
int minCounts[MAX_SIZE][MAX_SIZE][DIR];

void BFS(int sr, int sc);

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> w >> h;

    int sr = -1, sc = -1;
    int er = -1, ec = -1;

    for (int r = 0; r < h; ++r)
    {
        for (int c = 0; c < w; ++c)
        {
            cin >> board[r][c];
            minCounts[r][c][0] = minCounts[r][c][1] = minCounts[r][c][2] = minCounts[r][c][3] = INF;

            if (board[r][c] == 'C')
            {
                if (sr + sc == -2)
                {
                    sr = r;
                    sc = c;
                }
                else
                {
                    er = r;
                    ec = c;
                }
            }
        }
    }

    BFS(sr, sc);

    int answer = INF;

    for (int dir = 0; dir < DIR; ++dir)
    {
        answer = min(answer, minCounts[er][ec][dir]);
    }

    cout << answer << '\n';
}

void BFS(int sr, int sc)
{
    queue<Node> q;

    for (int dir = 0; dir < DIR; ++dir)
    {
        minCounts[sr][sc][dir] = 0;
        q.push(Node{ sr, sc, dir });
    }

    while (!q.empty())
    {
        Node node = q.front();

        q.pop();

        for (int dir = 0; dir < DIR; ++dir)
        {
            int nr = node.r + DELTA[dir][0];
            int nc = node.c + DELTA[dir][1];

            if ((nr < 0) || (nr >= h) || (nc < 0) || (nc >= w))
            {
                continue;
            }

            if (board[nr][nc] == '*')
            {
                continue;
            }

            int mirrorCnt = 0;

            if ((node.dir == 0) || (node.dir == 1))
            {
                if ((dir == 2) || (dir == 3))
                {
                    ++mirrorCnt;
                }
            }
            else if ((node.dir == 2) || (node.dir == 3))
            {
                if ((dir == 0) || (dir == 1))
                {
                    ++mirrorCnt;
                }
            }

            if (minCounts[nr][nc][dir] > minCounts[node.r][node.c][node.dir] + mirrorCnt)
            {
                minCounts[nr][nc][dir] = minCounts[node.r][node.c][node.dir] + mirrorCnt;
                q.push(Node{ nr, nc, dir });
            }
        }
    }
}
