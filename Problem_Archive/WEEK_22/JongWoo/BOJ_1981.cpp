/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- (최대 - 최소)의 값이 가장 작게 이동할 때를 찾는 것이기 때문에 답이 될 수 있는 범위는 (0 ~ MaxVal - MinVal)이 된다.
	  diff를 0부터 1씩 증가시키면서 O(N)에 확인할수도 있지만, 이분탐색을 사용해서 O(logN)에 좀 더 빠르게 확인할 수 있다.
	  diff의 범위를 이분 탐색으로 설정했다면, 기준이 되는 값을 설정한다. 이 값은 MaxVal - MinVal이 diff가 되도록 minVal부터 MaxVal - diff까지 1씩 증가하며 순회한다.
	  이제 bfs 탐색을 수행하는데 탐색 전에 isVisited의 값을 범위 안에 있는 칸은 false로, 나머지 칸은 true로 만들고 탐색을 수행했을 때 (n - 1, n - 1)에 도달할 수 있다면,
	  true를 그렇지 않다면 false를 반환하여 이분 탐색의 범위를 재조정하면서 답을 도출해낼 수 있다.

시간 복잡도
	- 이분 탐색으로 0 ~ maxVal 범위의 diff 값을 조정하는데 O(log(MaxVal - MinVal))의 시간이 걸리며, 매번 MaxVal - MinVal만큼 for문을 순회하며 BFS 탐색을 수행하는데
	  약 O(MaxVal - MinVal)의 시간이 걸린다. n은 최대 100, MaxVal과 MinVal은 0 ~ 200 사이의 값을 가진다.
	  내부 for문의 경우는 이분 탐색의 연산수 만큼 수행되므로 따라서 최종적인 시간 복잡도는 O(N^2 * logN)이 된다.
    
실행 시간
	- 16ms

삽질했던 내용
	- 첫 번째 삽질,
	  처음에는 diff의 값을 0부터 MaxVal - MinVal까지 1씩 올려가면서, BFS 탐색을 수행하였다.
	  이때 방문체크는 isVisited bool배열이 아닌 minDiff int배열로 선언하여 minDiff[r][c]를 (r, c)에 도착했을 때의 최소 차이로 설정하였다.
	  큐에는 매번 Node라는 구조체가 들어가는데, 이 구조체는 좌표(r, c)와 현재까지 도달했을 때 지나온 숫자의 최댓값(MaxVal)과 최솟값(MinVal)을 저장하도록 하였다.
	  이렇게 탐색을 진행하다가 (n - 1, n - 1)에 도착하면 true를 반환하고 그 즉시 답을 출력하는 방식으로 구현을 했는데, 이와 같은 방식은 치명적인 문제가 존재했다.
	  5 5 5 0 0
	  5 0 5 0 0
	  5 0 4 0 0
	  5 0 5 5 8
	  5 5 7 0 5
	  만약 격자판이 위와 같이 주어진다면, diff가 3일 때 오른쪽부터 출발하여 [5, 5, 5, 4, 5, 5]에서 더이상 탐색을 진행하지 못한다. (8로 이동할 경우 diff가 4가 되므로)
	  그러나, 아래쪽부터 출발하여 [5, 5, 5, 5, 5, 5, 7, 5, 5, 8, 5]로 이동한다면, diff가 3으로 (n - 1, n - 1)에 도달할 수 있음에도 (3, 2)의 minDiff가 1로 돼있기 때문에
	  해당 칸으로 이동할 수 없다. 위와 같이 매번 최댓값과 최솟값의 차로 탐색하는 것이 (n - 1, n - 1)에 도달할 때도 해당 조건을 보장한다고 할 수 없으며 이로인한 탐색의 기록이
	  다른 탐색을 하지 못하게 만든다.

	  두 번째 삽질,
	  bfs 함수에서 범위 내에 있는 칸은 isVisited의 값을 false로, 범위 내에 없는 칸은 isVisited를 true로 만들어서 false인 칸만 탐색했을 때 (n, n)에 도달할 수 있는지
	  판단하였는데 애초에 (0, 0)이나 (n - 1, n - 1)칸의 값이 범위를 벗어났다면 bfs 탐색을 수행하면 안된다.
	  이를 놓치고, 매번 (0, 0)이나 (n - 1, n - 1)의 isVisited가 true일때도 탐색을 수행하는 실수를 했었다.
*/

#include <iostream>
#include <queue>

using namespace std;

const int MAX_SIZE = 100;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

int n;
int board[MAX_SIZE][MAX_SIZE];
bool isVisited[MAX_SIZE][MAX_SIZE];

bool bfs(int minVal, int diff);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;
	
	int maxVal = 0, minVal = 200;

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			cin >> board[r][c];
			maxVal = max(maxVal, board[r][c]);
			minVal = min(minVal, board[r][c]);
		}
	}

	int answer = 0;

	// 차이는 최소 0부터 최대 maxVal - minVal까지 가능하다.
	int st = 0, en = maxVal - minVal;

	while (st <= en)
	{
		int md = st + (en - st) / 2;
		bool isPossible = false;

		for (int i = minVal; i <= maxVal - md; ++i)
		{
			if (bfs(i, md))
			{
				isPossible = true;
				break;
			}
		}

		if (isPossible)
		{
			answer = md;
			en = md - 1;
		}
		else
		{
			st = md + 1;
		}
	}

	cout << answer << '\n';
}

bool bfs(int minVal, int diff)
{
	int maxVal = minVal + diff;

	// 시작 지점과 도착 지점이 범위를 벗어나는 경우 탐색을 수행할 필요 없다.
	if ((board[0][0] < minVal) || (board[0][0] > maxVal) || (board[n - 1][n - 1] < minVal) || (board[n - 1][n - 1] > maxVal))
	{
		return false;
	}

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			if ((minVal <= board[r][c]) && (board[r][c] <= maxVal))
			{
				isVisited[r][c] = false;
			}
			else
			{
				isVisited[r][c] = true;
			}
		}
	}

	queue<pair<int, int>> q;
	isVisited[0][0] = true;
	q.emplace(0, 0);

	while (!q.empty())
	{
		int r = q.front().first;
		int c = q.front().second;
		q.pop();

		if ((r == n - 1) && (c == n - 1))
		{
			return true;
		}

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

			isVisited[nr][nc] = true;
			q.emplace(nr, nc);
		}
	}

	return false;
}
