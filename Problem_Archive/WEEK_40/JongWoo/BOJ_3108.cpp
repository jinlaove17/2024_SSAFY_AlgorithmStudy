/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에 이 문제를 읽었을 때, N*N 배열에 직접 사각형을 그려서 BFS 탐색을 수행하여 그룹의 개수를 계산하면 되지 않을까하는 생각이 들었다.
	  그러나, 막상 각 칸을 1로 채워서 그려보면 점을 기준으로 한게 아니라 칸을 기준으로 했기 때문에 그래프 탐색이 원치 않는 결과를 가져온다.
	  즉, 변을 공유하는 사각형들을 어떻게 구분하면 좋을지에 대한 고민을 하면 된다.
	  그래서 서로소 집합을 쓰면 어떨까 하는 생각이 들어 Union-Find 알고리즘을 적용해보게 되었다.
	  먼저 사각형은 -500 ~ 500의 범위를 갖는데, 간단히 배열의 인덱스로 표현하기 위해 모든 좌표에 500을 더해 원점부터 1사분면만 사용하도록 했다.
	  그 다음에는 각 사각형을 입력받고, 처음 그려질 경우 해당 칸을 사각형의 번호로 채우고, 두 번째부터는 서로소 집합으로 mergeSet 함수를 호출하여
	  같은 그룹에 속하는 사각형들을 계산하였다.
	  이후 모든 칸을 순회하며 빈 칸이 아니라면 BFS 탐색을 진행하고, 인접 칸과 같은 서로소 집합이면 해당 칸을 탐색하는 방식으로 구현하였다.

시간 복잡도
	- N개의 사각형을 그리는데 O(N * (x2 - x1 + y2 - y1))의 시간이 걸리며 모든 칸을 순회하며 BFS을 수행하는데 O(N^2)의 시간이 걸린다.
	  최종적으로 O(N^2)의 시간 복잡도를 갖는다.

실행 시간
	- 8ms
*/

#include <iostream>
#include <queue>

using namespace std;

const int MAX_BOARD_SIZE = 1'000;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

int n;
int board[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1];
bool isVisited[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1];
int parents[(MAX_BOARD_SIZE + 1) * (MAX_BOARD_SIZE + 1)];

void bfs(int sr, int sc);
int findSet(int a);
bool mergeSet(int a, int b);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int i = 0; i < (MAX_BOARD_SIZE + 1) * (MAX_BOARD_SIZE + 1); ++i)
	{
		parents[i] = -1;
	}

	int rectNum = 1;

	for (int i = 0; i < n; ++i)
	{
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		cin >> x1 >> y1 >> x2 >> y2;
		x1 += 500;
		y1 += 500;
		x2 += 500;
		y2 += 500;
		
		for (int y = y1; y <= y2; ++y)
		{
			if (board[y][x1])
			{
				mergeSet(board[y][x1], rectNum);
			}
			else
			{
				board[y][x1] = rectNum;
			}

			if (board[y][x2])
			{
				mergeSet(board[y][x2], rectNum);
			}
			else
			{
				board[y][x2] = rectNum;
			}
		}

		for (int x = x1 + 1; x <= x2 - 1; ++x)
		{
			if (board[y1][x])
			{
				mergeSet(board[y1][x], rectNum);
			}
			else
			{
				board[y1][x] = rectNum;
			}

			if (board[y2][x])
			{
				mergeSet(board[y2][x], rectNum);
			}
			else
			{
				board[y2][x] = rectNum;
			}
		}

		++rectNum;
	}

	int answer = 0;

	for (int r = 0; r <= MAX_BOARD_SIZE; ++r)
	{
		for (int c = 0; c < MAX_BOARD_SIZE; ++c)
		{
			if ((board[r][c]) && (!isVisited[r][c]))
			{
				bfs(r, c);
				++answer;
			}
		}
	}

	answer -= (board[500][500] ? 1 : 0);
	cout << answer << '\n';
}

void bfs(int sr, int sc)
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

			// 1. 범위를 벗어난 경우
			if ((nr < 0) || (nr > MAX_BOARD_SIZE) || (nc < 0) || (nc > MAX_BOARD_SIZE))
			{
				continue;
			}

			// 2. 빈 칸 혹은 공유하는 변이 아니거나, 이미 방문한 칸인 경우
			if ((board[nr][nc] == 0) || (findSet(board[r][c]) != findSet(board[nr][nc])) || (isVisited[nr][nc]))
			{
				continue;
			}

			isVisited[nr][nc] = true;
			q.emplace(nr, nc);
		}
	}
}

int findSet(int a)
{
	if (parents[a] < 0)
	{
		return a;
	}

	return parents[a] = findSet(parents[a]);
}

bool mergeSet(int a, int b)
{
	int aRoot = findSet(a);
	int bRoot = findSet(b);

	if (aRoot == bRoot)
	{
		return false;
	}

	if (parents[aRoot] <= parents[bRoot])
	{
		parents[aRoot] += parents[bRoot];
		parents[bRoot] = aRoot;
	}
	else
	{
		parents[bRoot] += parents[aRoot];
		parents[aRoot] = bRoot;
	}

	return true;
}
