/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽으면서 완전 탐색(BFS)과 DP 중에 하나를 사용해야겠다는 생각이 들었는데, 테스트 케이스2와 같이 항상 우하단으로 내려가는 것이 최적의 답은 아니기 때문에 완전 탐색을 사용하게 되었다.
	- 완전 탐색 과정에서 이미 방문한 칸을 더 적은 비용으로 다시 방문할 수 있기 때문에 bool 2차원 배열이 아닌 해당 칸에 도달했을 때 최소 누적값을 저장하는 int 2차원 배열로 구현하였다.

시간 복잡도
	- 테스트 케이스의 수 tc만큼 매번 n * n 맵을 재방문 가능하게 탐색하므로 O(tc * n^2)의 시간 복잡도를 갖는다.
*/

#include <iostream>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_BOARD_SIZE = 125;
const int DIR_COUNT = 4;
const int DELTA[][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

int n;
int board[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1];
int minCost[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1];

void BFS(int sr, int sc);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int tc = 1;

	while (true)
	{
		cin >> n;

		if (n == 0)
		{
			break;
		}

		for (int r = 0; r < n; ++r)
		{
			for (int c = 0; c < n; ++c)
			{
				cin >> board[r][c];
			}
		}

		BFS(0, 0);
		cout << "Problem " << tc++ << ": " << minCost[n - 1][n - 1] << '\n';
	}
}

void BFS(int sr, int sc)
{
	queue<pair<int, int>> q;

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			minCost[r][c] = INF;
		}
	}

	minCost[sr][sc] = board[sr][sc];
	q.emplace(sr, sc);

	while (!q.empty())
	{
		int r = q.front().first;
		int c = q.front().second;

		q.pop();

		for (int dir = 0; dir < DIR_COUNT; ++dir)
		{
			int nr = r + DELTA[dir][0];
			int nc = c + DELTA[dir][1];

			if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n))
			{
				continue;
			}

			if (minCost[nr][nc] > minCost[r][c] + board[nr][nc])
			{
				minCost[nr][nc] = minCost[r][c] + board[nr][nc];
				q.emplace(nr, nc);
			}
		}
	}
}
