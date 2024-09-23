/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에서 요구하는 내용을 직관적으로 구현할 수 있는 문제로, 매번 2차원 맵을 순회하면서 BFS 탐색을 통해 크기가 가장 큰 블록 그룹을 찾아야겠다고 생각했다.
	  이는 BlockGroup 구조체를 정의하고, 해당 그룹에 사용된 일반 블록과 무지개 블록의 좌표를 저장하는 동적 배열을 각각 선언하였다.
	  무지개 블록의 경우 개수만 세주면 되지만, 이번 BFS 탐색에서 방문체크를 했기 때문에 다음 탐색에서 무지개 블록을 이용할 수 있도록 방문 체크를 다시 false로 만들어주어야 한다.
	  전체 블록 개수 -> 무지개 블록 개수 -> 행 -> 열이므로, 일반 블록은 오름차순으로 정렬해줄 필요가 있다.
	  이제 전체 블록의 개수가 2이상인 그룹을 우선순위 큐에 넣어, 4가지 조건에 따라 우선순위가 가장 높은 블럭의 좌표들을 모두 빈 칸(-2)으로 만들어주고 중력과 회전을 적용한다.
	  90도 회전은 인덱스만 적절히 설정해주면 되지만, 중력의 경우에는 중간 중간 검은 블럭의 유무때문에 조금 까다로웠지만 이전처럼 tmp 배열을 사용해 당기는 방식을 채택하였다.
	  위 과정을 거치면 답을 도출할 수 있다.

시간 복잡도
	- 조건을 만족하는 블록 그룹이 없어 더이상 진행할 수 없을 때까지 시뮬레이션의 횟수: K
	- 매번 2차원 배열을 순회하며 힙에 원소를 추가: N^2logN
	- 따라서 O(K * N^2logN)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms(C++)

삽질했던 내용이나 코드
	- 처음에 중력을 적용하는 것에 대해서 움직이지 않는 검은 블럭(-1)의 유무를 고려하지 않고 구현하여 한동안 답을 도출하지 못했다.
*/

#include <iostream>
#include <vector>
#include <queue>
#include <cstring>
#include <algorithm>

using namespace std;

const int MAX_SIZE = 20 + 1;
const int DELTA[][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

struct BlockGroup
{
	vector<pair<int, int>> normalPos;
	vector<pair<int, int>> rainbowPos;

	void clear()
	{
		normalPos.clear();
		rainbowPos.clear();
	}

	bool operator <(const BlockGroup& rhs) const
	{
		int blockCountA = normalPos.size() + rainbowPos.size();
		int blockCountB = rhs.normalPos.size() + rhs.rainbowPos.size();

		if (blockCountA == blockCountB)
		{
			int rainbowCountA = rainbowPos.size();
			int rainbowCountB = rhs.rainbowPos.size();

			if (rainbowCountA == rainbowCountB)
			{
				if (normalPos[0].first == rhs.normalPos[0].first)
				{
					return normalPos[0].second < rhs.normalPos[0].second;
				}

				return normalPos[0].first < rhs.normalPos[0].first;
			}

			return rainbowCountA < rainbowCountB;
		}

		return blockCountA < blockCountB;
	}
};

int n, m;
int board[MAX_SIZE][MAX_SIZE];
int tmpBoard[MAX_SIZE][MAX_SIZE];
bool isVisited[MAX_SIZE][MAX_SIZE];
BlockGroup bg;

void BFS(int sr, int sc);
void UseGravity();
void RotateCCW();

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			cin >> board[r][c];
		}
	}

	int answer = 0;

	while (true)
	{
		priority_queue<BlockGroup> pq;

		for (int r = 0; r < n; ++r)
		{
			for (int c = 0; c < n; ++c)
			{
				if ((1 <= board[r][c]) && (board[r][c] <= m) && (!isVisited[r][c]))
				{
					BFS(r, c);

					int blockCount = bg.normalPos.size() + bg.rainbowPos.size();

					if (blockCount >= 2)
					{
						pq.push(bg);
					}

					for (const auto& p : bg.rainbowPos)
					{
						isVisited[p.first][p.second] = false;
					}
				}
			}
		}

		if (pq.empty())
		{
			break;
		}

		for (const auto& p : pq.top().normalPos)
		{
			board[p.first][p.second] = -2;
		}

		for (const auto& p : pq.top().rainbowPos)
		{
			board[p.first][p.second] = -2;
		}

		int blockCount = pq.top().normalPos.size() + pq.top().rainbowPos.size();

		answer += blockCount * blockCount;

		UseGravity();
		RotateCCW();
		UseGravity();
		memset(isVisited, false, sizeof(isVisited));
	}

	cout << answer << '\n';
}

void BFS(int sr, int sc)
{
	bg.clear();

	queue<pair<int, int>> q;
	int color = board[sr][sc];

	isVisited[sr][sc] = true;
	q.emplace(sr, sc);

	while (!q.empty())
	{
		int r = q.front().first;
		int c = q.front().second;

		q.pop();

		if (board[r][c] == 0)
		{
			bg.rainbowPos.emplace_back(r, c);
		}
		else
		{
			bg.normalPos.emplace_back(r, c);
		}

		for (int dir = 0; dir < 4; ++dir)
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

			if ((board[nr][nc] != 0) && (board[nr][nc] != color))
			{
				continue;
			}

			isVisited[nr][nc] = true;
			q.emplace(nr, nc);
		}
	}

	sort(bg.normalPos.begin(), bg.normalPos.end());
}

void UseGravity()
{
	int tmp[MAX_SIZE] = {};

	for (int c = 0; c < n; ++c)
	{
		int cnt = 0;

		for (int r = 0; r < n; ++r)
		{
			if (board[r][c] == -2)
			{
				continue;
			}
			else if (board[r][c] == -1)
			{
				for (int i = cnt - 1, pr = r - 1; i >= 0; --i, --pr)
				{
					board[pr][c] = tmp[i];
				}

				cnt = 0;
			}
			else
			{
				tmp[cnt++] = board[r][c];
				board[r][c] = -2;
			}
		}

		for (int i = cnt - 1, pr = n - 1; i >= 0; --i, --pr)
		{
			board[pr][c] = tmp[i];
		}
	}
}

void RotateCCW()
{
	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			tmpBoard[r][c] = board[r][c];
		}
	}

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			board[n - 1 - c][r] = tmpBoard[r][c];
		}
	}
}
