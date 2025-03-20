/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 bfs 탐색으로 각 육지가 어떤 섬에 속하는지 구분하고, 이 과정에서 바다와 맞닿은 칸들은 set에 추가한다.
	  이제 1번 섬부터 순회하며 추가한 칸들에서 bfs 탐색을 수행하여 다른 섬에 도달했을 때마다 최솟값을 갱신해준다.
	  위 과정을 거치면 답을 도출할 수 있다.

시간 복잡도
	- bfs 탐색을 통해 O(N^2)의 시간동안 섬의 인덱스를 부여하고, 이 과정에서 바다와 맞닿은 부분이 있는 칸들을 set에 저장한 다음,
	  set에 있는 원소마다 순회하면서 인덱스가 다른 섬에 도달하기 위해 bfs 탐색을 수행하여 최종적으로 O(N^3)의 가까운 시간이 걸린다.

실행 시간
	- 4ms
*/

#include <iostream>
#include <set>
#include <queue>
#include <cstring>

using namespace std;

const int MAX_BOARD_SIZE = 100;
const int MAX_ISLAND_COUNT = (10'000 / 2 + 1) + 1;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

struct Node
{
	int r;
	int c;
	int len;
};

int n;
int board[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
bool isVisited[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
int islandIndex, minLength;
set<pair<int, int>> edgeGrounds[MAX_ISLAND_COUNT];

void nameToIsland(int sr, int sc, int islandIndex);
void findClosestIsland(int sr, int sc, int islandIndex);

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
			board[r][c] = -board[r][c]; // 섬 번호의 편의를 위해 육지 영역을 -1로 취급
		}
	}

	islandIndex = 1;
	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			if ((board[r][c] == -1) && (!isVisited[r][c]))
			{
				nameToIsland(r, c, islandIndex++);
			}
		}
	}

	minLength = 10'001;
	for (int i = 1; i < islandIndex; ++i)
	{
		for (const auto& p : edgeGrounds[i])
		{
			findClosestIsland(p.first, p.second, i);
		}
	}

	cout << minLength << '\n';
}

void nameToIsland(int sr, int sc, int islandIndex)
{
	queue<pair<int, int>> q;

	isVisited[sr][sc] = true;
	q.emplace(sr, sc);

	while (!q.empty())
	{
		int r = q.front().first;
		int c = q.front().second;
		q.pop();
		board[r][c] = islandIndex;

		for (int dir = 0; dir < DELTA_COUNT; ++dir)
		{
			int nr = r + DELTA[dir][0];
			int nc = c + DELTA[dir][1];

			if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n) || (isVisited[nr][nc]))
			{
				continue;
			}
			else if (board[nr][nc] != -1)
			{
				if (board[nr][nc] == 0)
				{
					edgeGrounds[islandIndex].emplace(r, c);
				}

				continue;
			}

			isVisited[nr][nc] = true;
			q.emplace(nr, nc);
		}
	}
}

void findClosestIsland(int sr, int sc, int islandIndex)
{
	queue<Node> q;

	memset(isVisited, false, sizeof(isVisited));
	isVisited[sr][sc] = true;
	q.push(Node{ sr, sc, 0 });

	while (!q.empty())
	{
		Node cur = q.front();
		q.pop();

		for (int dir = 0; dir < DELTA_COUNT; ++dir)
		{
			int nr = cur.r + DELTA[dir][0];
			int nc = cur.c + DELTA[dir][1];

			if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n) || (isVisited[nr][nc]))
			{
				continue;
			}
			else if (board[nr][nc] == 0)
			{
				if (cur.len + 1 < minLength)
				{
					isVisited[nr][nc] = true;
					q.push(Node{ nr, nc, cur.len + 1 });
				}
			}
			else if (board[nr][nc] != islandIndex)
			{
				minLength = min(minLength, cur.len);
				break;
			}
		}
	}
}
