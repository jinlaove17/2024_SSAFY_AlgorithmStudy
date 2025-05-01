/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 방의 개수, 가장 넓은 방의 넓이, 하나의 벽을 제거하여 얻을 수 있는 가장 넓은 방의 크기 등 총 3가지 조건에 대한 답을 구해야 하는 그래프 탐색 문제였다.
	  우선, 방을 구분하고 넓이를 구해야 하기 때문에 BFS 알고리즘을 사용해서 1, 2번에 대한 답을 도출할 수 있었다.
	  이때, 인풋으로 주어지는 값이 일반적인 그래프 문제와는 다르게 벽의 상태를 비트로 준다는 점이 독특했다.
	  그래서 그래프 탐색 시에 위로 갈 경우는 위쪽 칸에서 아래로 막힌 벽이 있는지를 확인하는 방식 등으로 방향에 맞게 이동 가능한지를 따져봐야 한다.
	  이후 모든 방이 나누어졌다면 모든 칸을 순회하며 인접한 칸이 막혀있는 방향으로 다른 방인지를 확인해서 다른 방일 경우 두 방의 크기를 합치면서
	  최댓값을 구하면 3번째 문제에 대한 답 또한 도출할 수 있게 된다.

시간 복잡도
	- 방을 계산하는데 BFS 탐색으로 M*N 맵을 순회하므로 O(N^2)의 시간이 걸리며, 이후 모든 칸을 순회하며 벽을 허물었을 때의 방의 크기 최대값을 구하는데
	  M*N 맵을 순회하므로 O(N^2)의 시간이 걸린다. 최종적으로 O(N^2)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <queue>

using namespace std;

const int MAX_BOARD_SIZE = 50;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
const int UP = 0;
const int DOWN = 1;
const int LEFT = 2;
const int RIGHT = 3;
const int WEST = 0b0001; // 1
const int NORTH = 0b0010; // 2
const int EAST = 0b0100; // 4
const int SOUTH = 0b1000; // 8

int n, m;
int walls[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
int rooms[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
int roomSizes[MAX_BOARD_SIZE * MAX_BOARD_SIZE + 1]; // roomNum: 1 ~ 2,500
int maxRoomSizes[2]; // 0: 가장 넓은 방의 넓이, 1: 하나의 벽을 제거하여 얻을 수 있는 가장 넓은 방의 크기

void createRoom(int sr, int sc, int roomNum);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int r = 0; r < m; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			cin >> walls[r][c];
		}
	}

	int roomNum = 0;

	for (int r = 0; r < m; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			if (rooms[r][c] == 0)
			{
				createRoom(r, c, ++roomNum);
			}
		}
	}

	for (int r = 0; r < m; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			for (int dir = UP; dir < DELTA_COUNT; ++dir)
			{
				int nr = r + DELTA[dir][0];
				int nc = c + DELTA[dir][1];

				// 1. 범위를 벗어난 경우
				if ((nr < 0) || (nr >= m) || (nc < 0) || (nc >= n))
				{
					continue;
				}

				// 2. 같은 방인 경우
				if (rooms[r][c] == rooms[nr][nc])
				{
					continue;
				}

				// 3. 벽이 없는 경우
				if (((dir == UP) && !(walls[nr][nc] & SOUTH)) ||
					((dir == DOWN) && !(walls[nr][nc] & NORTH)) ||
					((dir == LEFT) && !(walls[nr][nc] & EAST)) ||
					((dir == RIGHT) && !(walls[nr][nc] & WEST)))
				{
					continue;
				}

				maxRoomSizes[1] = max(maxRoomSizes[1], roomSizes[rooms[r][c]] + roomSizes[rooms[nr][nc]]);
			}
		}
	}

	cout << roomNum << '\n';
	cout << maxRoomSizes[0] << '\n';
	cout << maxRoomSizes[1] << '\n';
}

void createRoom(int sr, int sc, int roomNum)
{
	queue<pair<int, int>> q;

	rooms[sr][sc] = roomNum;
	q.emplace(sr, sc);

	while (!q.empty())
	{
		int r = q.front().first;
		int c = q.front().second;
		q.pop();
		++roomSizes[roomNum];

		for (int dir = UP; dir < DELTA_COUNT; ++dir)
		{
			int nr = r + DELTA[dir][0];
			int nc = c + DELTA[dir][1];

			// 1. 범위를 벗어난 경우
			if ((nr < 0) || (nr >= m) || (nc < 0) || (nc >= n))
			{
				continue;
			}

			// 2. 이미 방문한 경우
			if (rooms[nr][nc] != 0)
			{
				continue;
			}

			// 3. 벽에 막힌 경우
			if (((dir == UP) && (walls[nr][nc] & SOUTH)) ||
				((dir == DOWN) && (walls[nr][nc] & NORTH)) ||
				((dir == LEFT) && (walls[nr][nc] & EAST)) ||
				((dir == RIGHT) && (walls[nr][nc] & WEST)))
			{
				continue;
			}

			rooms[nr][nc] = roomNum;
			q.emplace(nr, nc);
		}
	}

	maxRoomSizes[0] = max(maxRoomSizes[0], roomSizes[roomNum]);
}
