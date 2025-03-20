/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 가장 적은 충격량으로 도달해야 하므로, 거리보다는 충격량을 고려해야 한다. 따라서 우선순위 큐 기반의 bfs 알고리즘을 떠올리게 되었다.
	  방향 때문에 이미 방문했던 칸을 추후에 방문하는 경우가 존재할 수 있기 때문에 각 칸에 도달했을 때의 k 값을 추가해 3차원 배열로 방문체크를 수행했다.
	  매번 최소힙에서 충격량이 작은 Node를 꺼내면서 목표 지점에 도착했을 때 누적 충격량을 반환하면 문제에서 원하는 답을 도출할 수 있다.

시간 복잡도
	- 삽입/삭제에 logN이 걸리는 우선순위 큐를 사용해 N x N 격자를 순회하므로 최악의 경우 O(N^2logN)의 시간이 걸린다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_BOARD_SIZE = 100;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

struct Node
{
	int r;
	int c;
	int k;
	int impact;
};

struct compare
{
	bool operator ()(const Node& a, const Node& b)
	{
		return a.impact > b.impact;
	}
};

int n, m;
int sr, sc, er, ec;
int board[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1];
bool isVisited[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1][3]; // isVisited[r][c][k]
vector<int> moveTypes[3] = {
	{ 0, 1, 2, 3 },
	{ 0, 1 },
	{ 2, 3 }
};

int bfs();

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;
	cin >> sr >> sc >> er >> ec;

	for (int r = 1; r <= n; ++r)
	{
		for (int c = 1; c <= m; ++c)
		{
			cin >> board[r][c];
		}
	}

	cout << bfs() << '\n';
}

int bfs()
{
	priority_queue<Node, vector<Node>, compare> q;
	q.push(Node{ sr, sc, 1, board[sr][sc] });

	while (!q.empty())
	{
		Node cur = q.top();
		q.pop();

		if ((cur.r == er) && (cur.c == ec))
		{
			return cur.impact;
		}

		int k = cur.k % 3;
		for (int dir : moveTypes[k])
		{
			int nr = cur.r + DELTA[dir][0];
			int nc = cur.c + DELTA[dir][1];
			if ((nr < 1) || (nr > n) || (nc < 1) || (nc > m) || (board[nr][nc] == -1) || (isVisited[nr][nc][k]))
			{
				continue;
			}

			isVisited[nr][nc][k] = true;
			q.push(Node{ nr, nc, cur.k + 1, cur.impact + board[nr][nc] });
		}
	}

	return -1;
}
