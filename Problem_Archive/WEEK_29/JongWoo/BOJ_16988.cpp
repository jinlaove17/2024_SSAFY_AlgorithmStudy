/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- n이 최대 20이기 때문에 빈 칸(0) 중 2곳을 선택해 모든 경우를 살펴보는 완전 탐색이 가능하다고 판단하였다.
	  우선, 2곳을 선택하는 것은 2중 for문으로 2차원 배열을 1차원 배열로 축소하여 수행하였으며 빈 칸이 아닐 경우에
	  선택된 두 곳을 기준으로 상, 하, 좌, 우 칸이 상대의 돌(2)인 곳으로 최대 8번의 BFS 탐색을 수행하였다.
	  BFS 탐색을 수행하면서 한 번이라도 빈 칸을 만난다면 탐색은 모두 수행하되 반환 값이 0이 되며, 빈 칸을 한 번도 만나지 않은 경우에는
	  조건에 부합하는 경우이므로 탐색한 노드의 개수를 반환하도록 구현하였다.
	  위 과정을 반복하여 합이 최대가 되는 경우를 구해 답을 도출할 수 있었다.

시간 복잡도
	- 착수할 두 칸을 찾는데 O(N^2)의 시간이 걸리며, 빈 칸일 경우 최대 8번의 BFS 탐색을 통해 최대 N * M의 맵을 순회한다.
	  따라서 최종적인 시간 복잡도는 O(N^3)이 된다.

실행 시간
	- 140ms
*/

#include <iostream>
#include <queue>
#include <cstring>

using namespace std;

const int MAX_BOARD_SIZE = 20;
const int DELTA_COUNT = 4;
const int DELTA[4][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

int n, m;
int board[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
bool isVisited[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

int bfs(int sr, int sc);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < m; ++c)
		{
			cin >> board[r][c];
		}
	}

	int answer = 0;

	for (int i = 0; i < n * m - 1; ++i)
	{
		int r1 = i / m;
		int c1 = i % m;

		if (board[r1][c1] != 0)
		{
			continue;
		}

		board[r1][c1] = 1;

		for (int j = i + 1; j < n * m; ++j)
		{
			int r2 = j / m;
			int c2 = j % m;

			if (board[r2][c2] != 0)
			{
				continue;
			}

			int sum = 0;

			board[r2][c2] = 1;

			for (int dir = 0; dir < DELTA_COUNT; ++dir)
			{
				int nr1 = r1 + DELTA[dir][0];
				int nc1 = c1 + DELTA[dir][1];

				if ((nr1 < 0) || (nr1 >= n) || (nc1 < 0) || (nc1 >= m) || (isVisited[nr1][nc1]) || (board[nr1][nc1] != 2))
				{
					continue;
				}

				sum += bfs(nr1, nc1);
			}

			for (int dir = 0; dir < DELTA_COUNT; ++dir)
			{
				int nr2 = r2 + DELTA[dir][0];
				int nc2 = c2 + DELTA[dir][1];

				if ((nr2 < 0) || (nr2 >= n) || (nc2 < 0) || (nc2 >= m) || (isVisited[nr2][nc2]) || (board[nr2][nc2] != 2))
				{
					continue;
				}

				sum += bfs(nr2, nc2);
			}

			answer = max(answer, sum);
			board[r2][c2] = 0;
			memset(isVisited, false, sizeof(isVisited));
		}

		board[r1][c1] = 0;
	}

	cout << answer << '\n';
}

int bfs(int sr, int sc)
{
	int cnt = 0;
	bool isBlocked = true;
	queue<pair<int, int>> q;

	isVisited[sr][sc] = true;
	q.emplace(sr, sc);

	while (!q.empty())
	{
		int r = q.front().first;
		int c = q.front().second;
		q.pop();
		++cnt;

		for (int dir = 0; dir < DELTA_COUNT; ++dir)
		{
			int nr = r + DELTA[dir][0];
			int nc = c + DELTA[dir][1];

			if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= m) || (isVisited[nr][nc]))
			{
				continue;
			}

			if (board[nr][nc] == 0)
			{
				isBlocked = false;
			}
			else if (board[nr][nc] != 2)
			{
				continue;
			}

			isVisited[nr][nc] = true;
			q.emplace(nr, nc);
		}
	}

	return isBlocked ? cnt : 0;
}
