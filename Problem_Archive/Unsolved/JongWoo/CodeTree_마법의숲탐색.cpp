/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제가 조금 복잡하지만, 차근차근 구현하면 엣지 케이스를 상세히 고민하지 않아도 코드를 짤 수 있는 문제였다.
	  그러나, 테스트케이스가 워낙 많아서 한 번 삐끗하면 다시 처음부터 실행 과정을 그려봐야 하고 이 과정에서 멘붕이 올 수 있다.
	  구현력을 기를 수 있는 좋은 문제였다.

시간 복잡도
	- k개의 골렘에 대해 최대 n개의 행을 내려가면서, bfs 탐색을 수행하므로 (k * n) * (n^2)의 시간이 걸린다.
	  따라서 O(kn^3)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <queue>

using namespace std;

const int MAX_BOARD_SIZE = 70;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

struct Golem
{
	int r;
	int c;
	int dir;
};

struct Fairy
{
	int r;
	int c;
	int golemIndex;
};

int n, m, k;
int board[MAX_BOARD_SIZE + 3][MAX_BOARD_SIZE + 1]; // 행(3 ~ 72), 열(1 ~ 70)

int bfs(const Fairy& fairy);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m >> k;
	n += 2;

	int answer = 0, golemIndex = 1;
	for (int i = 0; i < k; ++i)
	{
		Golem golem = { 1 };
		cin >> golem.c >> golem.dir;
		int sr  = 1, sc = golem.c;
		board[sr - 1][sc] = board[sr][sc - 1] = board[sr][sc] = board[sr][sc + 1] = board[sr + 1][sc] = golemIndex;

		while (golem.r + 2 <= n)
		{
			if ((board[golem.r + 1][golem.c - 1] == 0) &&
				(board[golem.r + 2][golem.c] == 0) &&
				(board[golem.r + 1][golem.c + 1] == 0))
			{
				board[golem.r - 1][golem.c] = 0;
				board[golem.r][golem.c - 1] = 0;
				board[golem.r][golem.c + 1] = 0;
				board[golem.r + 1][golem.c - 1] = golemIndex;
				board[golem.r + 2][golem.c] = golemIndex;
				board[golem.r + 1][golem.c + 1] = golemIndex;
				++golem.r;
			}
			else if ((golem.c - 2 >= 1) &&
				((board[golem.r - 1][golem.c - 1] == 0) &&
				(board[golem.r][golem.c - 2] == 0) &&
				(board[golem.r + 1][golem.c - 1] == 0) &&
				(board[golem.r + 1][golem.c - 2] == 0) && 
				(board[golem.r + 2][golem.c - 1] == 0)))
			{
				board[golem.r - 1][golem.c] = 0;
				board[golem.r][golem.c - 1] = 0;
				board[golem.r][golem.c] = 0;
				board[golem.r][golem.c + 1] = 0;
				board[golem.r + 1][golem.c] = 0;

				int dr = 1, dc = -1;
				board[golem.r - 1 + dr][golem.c + dc] = golemIndex;
				board[golem.r + dr][golem.c - 1 + dc] = golemIndex;
				board[golem.r + dr][golem.c + dc] = golemIndex;
				board[golem.r + dr][golem.c + 1 + dc] = golemIndex;
				board[golem.r + 1 + dr][golem.c + dc] = golemIndex;
				golem.r += dr;
				golem.c += dc;
				golem.dir = (golem.dir - 1 + DELTA_COUNT) % DELTA_COUNT;
			}
			else if ((golem.c + 2 <= m) &&
				((board[golem.r - 1][golem.c + 1] == 0) &&
				(board[golem.r][golem.c + 2] == 0) &&
				(board[golem.r + 1][golem.c + 1] == 0) &&
				(board[golem.r + 1][golem.c + 2] == 0) &&
				(board[golem.r + 2][golem.c + 1] == 0)))
			{
				board[golem.r - 1][golem.c] = 0;
				board[golem.r][golem.c - 1] = 0;
				board[golem.r][golem.c] = 0;
				board[golem.r][golem.c + 1] = 0;
				board[golem.r + 1][golem.c] = 0;

				int dr = 1, dc = 1;
				board[golem.r - 1 + dr][golem.c + dc] = golemIndex;
				board[golem.r + dr][golem.c - 1 + dc] = golemIndex;
				board[golem.r + dr][golem.c + dc] = golemIndex;
				board[golem.r + dr][golem.c + 1 + dc] = golemIndex;
				board[golem.r + 1 + dr][golem.c + dc] = golemIndex;
				golem.r += dr;
				golem.c += dc;
				golem.dir = (golem.dir + 1) % DELTA_COUNT;
			}
			else
			{
				break;
			}
		}

		// 골렘의 몸 일부가 숲을 벗어난 경우, 숲을 비운다.
		if (golem.r - 1 <= 2)
		{
			for (int r = 0; r <= n; ++r)
			{
				for (int c = 1; c <= m; ++c)
				{
					board[r][c] = 0;
				}
			}

			golemIndex = 1;
		}
		// 정령을 최대한 남쪽으로 이동시킨다.
		else
		{
			board[sr - 1][sc] = board[sr][sc - 1] = board[sr][sc] = board[sr][sc + 1] = board[sr + 1][sc] = 0;

			switch (golem.dir)
			{
			case 0:
				board[golem.r - 1][golem.c] = -board[golem.r - 1][golem.c];
				break;
			case 1:
				board[golem.r][golem.c + 1] = -board[golem.r][golem.c + 1];
				break;
			case 2:
				board[golem.r + 1][golem.c] = -board[golem.r + 1][golem.c];
				break;
			case 3:
				board[golem.r][golem.c - 1] = -board[golem.r][golem.c - 1];
				break;
			}

			Fairy fairy = { golem.r, golem.c, board[golem.r][golem.c] };
			answer += bfs(fairy);
			cout << answer << "\n";
			++golemIndex;
		}
	}

	cout << answer << '\n';
}

int bfs(const Fairy& fairy)
{
	int ret = 0;
	queue<pair<int, int>> q;
	bool isVisited[MAX_BOARD_SIZE + 3][MAX_BOARD_SIZE + 1] = {};

	isVisited[fairy.r][fairy.c] = true;
	q.emplace(fairy.r, fairy.c);

	while (!q.empty())
	{
		int r = q.front().first;
		int c = q.front().second;
		q.pop();
		ret = max(ret, r - 2);

		for (int dir = 0; dir < DELTA_COUNT; ++dir)
		{
			int nr = r + DELTA[dir][0];
			int nc = c + DELTA[dir][1];

			if ((nr < 3) || (nr > n) || (nc < 1) || (nc > m) || (board[nr][nc] == 0) || (isVisited[nr][nc]))
			{
				continue;
			}

			if ((abs(board[nr][nc]) == abs(board[r][c])) || (board[r][c] < 0))
			{
				isVisited[nr][nc] = true;
				q.emplace(nr, nc);
			}
		}
	}
	
	return ret;
}
