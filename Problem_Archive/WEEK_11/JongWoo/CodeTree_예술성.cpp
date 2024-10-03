/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 각 그룹의 크기를 구해야하므로 BFS를 가장 먼저 떠올렸다. 이때 BFS 탐색을 하면서 인접한 다른 그룹과 맞닿은 면의 개수를 같이 계산하고 싶었는데,
	  아직 탐색을 하지 않은 그룹이 정해지지 않았기 때문에 불가능했다.
	  그래서 맞닿은 면을 계산하는 것은 각 그룹의 좌표를 vector에 저장하고 다른 그룹의 좌표와 비교했을 때 맨해튼 거리가 1이 나온다면 맞닿은 것으로 판별하였다.
	  십자 모양 회전은 하나의 값을 tmp에 저장해두고 인덱스에 값에 오프셋을 적절히 조절하면서 돌려주면 되고, 나머지 정사각형에 대한 또한 전형적인 회전이므로 큰 어려움 없이 해결할 수 있었다.

시간 복잡도
	- N * N 맵을 순회하며 BFS 탐색으로 그룹을 나누고, 각 그룹 간의 좌표를 비교하여 맞닿은 면을 비교하고, 맵을 회전하는 알고리즘들은 O(N^2)의 시간 복잡도를 갖는다.
	- 따라서 모든 시간 복잡도를 더하더라도 O(N^2)의 시간 복잡도를 갖는다.

실행 시간
	- 11ms(C++)

개선 방법
	- 이 문제의 해설에서는 DFS 탐색을 통해 각 그룹의 개수를 구하고, N * N 크기의 배열에 해당 위치가 어떤 그룹에 속하는지를 저장한다.
	- 즉, 색상 외에 그룹의 인덱스를 별도로 지정해준다는 것이 주목할만하다.
	- 또한 맞닿은 면을 계산할 때는 각 칸을 순회하며 인접한 칸이 다른 그룹일 경우 값을 증가시키는 아이디어를 살펴볼 수 있었다.
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int MAX_BOARD_SIZE = 29 + 1;
const int DELTA[][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

struct ColorGroup
{
	int color;
	vector<pair<int, int>> positions;
};

int n;
int board[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
bool isVisited[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
vector<ColorGroup> groups;

void BFS(int sr, int sc, int color);
int CalculateScore();
void Rotate();
void PartiallyRotate(int r, int c, int size);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			cin >> board[r][c];
		}
	}

	int totalScore = 0;

	for (int i = 0; i < 4; ++i)
	{
		for (int r = 0; r < n; ++r)
		{
			for (int c = 0; c < n; ++c)
			{
				if (isVisited[r][c])
				{
					continue;
				}

				BFS(r, c, board[r][c]);
			}
		}

		totalScore += CalculateScore();

		if (i < 3)
		{
			Rotate();
			memset(isVisited, false, sizeof(isVisited));
			groups.clear();
		}
	}

	cout << totalScore << '\n';
}

void BFS(int sr, int sc, int color)
{
	ColorGroup cg;
	queue<pair<int, int>> q;

	cg.color = color;
	isVisited[sr][sc] = true;
	q.emplace(sr, sc);

	while (!q.empty())
	{
		int r = q.front().first;
		int c = q.front().second;

		q.pop();
		cg.positions.emplace_back(r, c);

		for (int dir = 0; dir < 4; ++dir)
		{
			int nr = r + DELTA[dir][0];
			int nc = c + DELTA[dir][1];

			if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n))
			{
				continue;
			}

			if ((board[nr][nc] != color) || (isVisited[nr][nc]))
			{
				continue;
			}

			isVisited[nr][nc] = true;
			q.emplace(nr, nc);
		}
	}

	groups.push_back(cg);
}

int CalculateScore()
{
	int ret = 0;

	for (int i = 0, size = static_cast<int>(groups.size()); i < size - 1; ++i)
	{
		for (int j = i + 1; j < size; ++j)
		{
			// 맞닿아 있는 변의 수를 계산한다.
			// (맨해튼 거리가 1차이나면 맞닿은 변임)
			int adjCount = 0;

			for (const auto& pos1 : groups[i].positions)
			{
				for (const auto& pos2 : groups[j].positions)
				{
					int mDist = abs(pos1.first - pos2.first) + abs(pos1.second - pos2.second);

					if (mDist == 1)
					{
						++adjCount;
					}
				}
			}

			ret += static_cast<int>(groups[i].positions.size() + groups[j].positions.size()) * groups[i].color * groups[j].color * adjCount;
		}
	}

	return ret;
}

void Rotate()
{
	int half = n / 2;

	// 1. 십자 모양 반시계 방향 회전
	for (int i = 1; i <= half; ++i)
	{
		int tmp = board[half - i][half];

		board[half - i][half] = board[half][half + i];
		board[half][half + i] = board[half + i][half];
		board[half + i][half] = board[half][half - i];
		board[half][half - i] = tmp;
	}

	// 2. 십자 모양을 제외한 4개의 정사각형에 대해 개별적으로 시계 방향 회전
	for (int r = 0; r < n; r += half + 1)
	{
		for (int c = 0; c < n; c += half + 1)
		{
			PartiallyRotate(r, c, half);
		}
	}
}

void PartiallyRotate(int sr, int sc, int size)
{
	int tmp[MAX_BOARD_SIZE / 2][MAX_BOARD_SIZE / 2] = {};

	for (int r = 0; r < size; ++r)
	{
		for (int c = 0; c < size; ++c)
		{
			tmp[r][c] = board[sr + r][sc + c];
		}
	}

	for (int r = 0; r < size; ++r)
	{
		for (int c = 0; c < size; ++c)
		{
			board[sr + c][sc + (size - 1 - r)] = tmp[r][c];
		}
	}
}
