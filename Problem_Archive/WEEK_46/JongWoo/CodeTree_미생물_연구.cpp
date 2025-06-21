#include <iostream>
#include <vector>
#include <queue>
#include <cstring>
#include <algorithm>

using namespace std;

const int INF = 987'654'321;
const int MAX_BOARD_SIZE = 15;
const int MAX_TEST_COUNT = 50;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

struct Grid
{
	int minR;
	int minC;
	int maxR;
	int maxC;
};

struct Group
{
	int id;
	int size;
	Grid grid;

	bool operator <(const Group& rhs) const
	{
		if (size == rhs.size)
		{
			return id < rhs.id;
		}

		return size > rhs.size;
	}
};

int n, q;
int board[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
int tempBoard[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
int isVisited[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
bool isAdjacent[MAX_TEST_COUNT + 1][MAX_TEST_COUNT + 1];
int freq[MAX_TEST_COUNT + 1];
vector<Group> groups;

void bfs(int sr, int sc);
void relocateCellGroup(const Group& group);
void calculate(int sr, int sc);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> q;

	for (int tc = 1; tc <= q; ++tc)
	{
		int r1 = 0, c1 = 0, r2 = 0, c2 = 0;
		cin >> c1 >> r1 >> c2 >> r2;

		// 새로운 미생물 추가하기
		for (int r = n - r2; r < n - r1; ++r)
		{
			for (int c = c1; c < c2; ++c)
			{
				board[r][c] = tc;
			}
		}

		// 매 실험시 필요한 데이터 초기화 작업
		memset(tempBoard, 0, sizeof(tempBoard));
		memset(isVisited, false, sizeof(isVisited));
		memset(freq, 0, sizeof(freq));
		groups.clear();

		// 각 미생물을 좌하단부터 크기순으로 옮기기
		for (int r = 0; r < n; ++r)
		{
			for (int c = 0; c < n; ++c)
			{
				if ((board[r][c] > 0) && (!isVisited[r][c]))
				{
					bfs(r, c);
				}
			}
		}

		sort(groups.begin(), groups.end());

		for (const Group& group : groups)
		{
			if (freq[group.id] >= 2)
			{
				continue;
			}

			relocateCellGroup(group);
		}

		//cout << "============================\n";

		for (int r = 0; r < n; ++r)
		{
			for (int c = 0; c < n; ++c)
			{
				board[r][c] = tempBoard[r][c];
				//cout << board[r][c] << ' ';
			}

			//cout << '\n';
		}

		memset(isVisited, false, sizeof(isVisited));
		memset(isAdjacent, false, sizeof(isAdjacent));

		for (int r = 0; r < n; ++r)
		{
			for (int c = 0; c < n; ++c)
			{
				if ((board[r][c] > 0) && (!isVisited[r][c]))
				{
					calculate(r, c);
				}
			}
		}

		int answer = 0;
		int groupSize = static_cast<int>(groups.size());
		for (int i = 0; i < groupSize - 1; ++i)
		{
			for (int j = i + 1; j < groupSize; ++j)
			{
				if (isAdjacent[groups[i].id][groups[j].id])
				{
					answer += groups[i].size * groups[j].size;
				}
			}
		}

		cout << answer << '\n';
	}

	return 0;
}

void bfs(int sr, int sc)
{
	if (++freq[board[sr][sc]] >= 2)
	{
		return;
	}

	Group group = { board[sr][sc], 0, { INF, INF, -1, -1 } };
	queue<pair<int, int>> q;
	isVisited[sr][sc] = true;
	q.emplace(sr, sc);

	while (!q.empty())
	{
		int r = q.front().first;
		int c = q.front().second;
		q.pop();

		group.grid.minR = min(group.grid.minR, r);
		group.grid.minC = min(group.grid.minC, c);
		group.grid.maxR = max(group.grid.maxR, r);
		group.grid.maxC = max(group.grid.maxC, c);
		++group.size;

		for (int dir = 0; dir < DELTA_COUNT; ++dir)
		{
			int nr = r + DELTA[dir][0];
			int nc = c + DELTA[dir][1];
			if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n))
			{
				continue;
			}
			if ((isVisited[nr][nc]) || (board[nr][nc] != board[sr][sc]))
			{
				continue;
			}

			isVisited[nr][nc] = true;
			q.emplace(nr, nc);
		}
	}

	groups.push_back(group);
}

void relocateCellGroup(const Group& group)
{
	int h = group.grid.maxR - group.grid.minR + 1;
	int w = group.grid.maxC - group.grid.minC + 1;

	for (int nc = 0; nc <= n - w; ++nc)
	{
		for (int nr = n - h; nr >= 0; --nr)
		{
			bool isMovable = true;

			for (int r = 0; r < h; ++r)
			{
				for (int c = 0; c < w; ++c)
				{
					if ((nr + r >= n) || (nc + c >= n) ||
						(board[group.grid.minR + r][group.grid.minC + c] == group.id) && (tempBoard[nr + r][nc + c] > 0))
					{
						isMovable = false;
						break;
					}
				}

				if (!isMovable)
				{
					break;
				}
			}

			if (isMovable)
			{
				for (int r = 0; r < h; ++r)
				{
					for (int c = 0; c < w; ++c)
					{
						if (board[group.grid.minR + r][group.grid.minC + c] != group.id)
						{
							continue;
						}

						tempBoard[nr + r][nc + c] = group.id;
					}
				}

				return;
			}
		}
	}
}

void calculate(int sr, int sc)
{
	queue<pair<int, int>> q;
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
			if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n))
			{
				continue;
			}
			if (isVisited[nr][nc])
			{
				continue;
			}

			if (board[nr][nc] != board[sr][sc])
			{
				isAdjacent[board[nr][nc]][board[sr][sc]] = true;
				isAdjacent[board[sr][sc]][board[nr][nc]] = true;
			}
			else
			{
				isVisited[nr][nc] = true;
				q.emplace(nr, nc);
			}
		}
	}
}
