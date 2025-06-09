/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- (0, 0)칸부터 (n - 1, m - 1)칸까지 살펴보면서, 각 칸을 어떻게 처리할 것인지 따져보았다.
	  각 칸을 세가지 방법으로 처리할 수 있는데, 첫 번째는 1x1칸으로 나누기, 두 번째는 세로 그룹에 포함시키기, 마지막은 가로 그룹에 포함시키기이다.
	  n과 m이 최대 4이므로 완전 탐색으로 답을 도출해도 될 것이라고 판단하였다.
	  dfs 탐색을 이용해 현재 깊이값으로부터 (r, c)의 좌표값을 추출하여 위 세가지 조건을 각각 따져보았다.
	  이때 주의할 점은 현재 칸을 1x1로 나누지 않는다면 값을 누적해야 하는데 0 -> n * m - 1로 좌상단에서 우하단으로 나아가기 때문에
	  현재 칸을 그룹에 포함시킬 것이라면 10씩 곱해주면서 자릿수를 맞춰주어야 한다는 것이다.
	  또한 복구할 때는 r + 1부터 n까지, c + 1부터 m까지로 하면 안되고 현재 깊이에서 방문한 범위까지만 복구해주어야 한다.
	  왜냐하면 이전에 세로나 가로 그룹으로 방문처리가 된 칸이 현재 깊이에서 복구되어 방문체크가 없어질 수 있기 때문이다.
	  이러한 점들이 어려웠고, 시간도 꽤나 오래 걸렸던 문제였다.
	  
시간 복잡도
	- 각 칸에서 3가지 경우를 따져보는데 1x1로 하는 경우, 현재 칸부터 끝 행까지, 끝 열까지 따져보는데 각각 n, m번 반복을 수행한다.
	  단, 이미 방문한 칸은 재방문하지 않으므로 훨씬 적은 시간이 걸릴 것이다.
	  따라서 매 깊이마다 방문 여부에 따라 n * m칸을 방문해야 하므로 O(2^(n * m) * (n + m))의 시간이 걸린다.

실행 시간
	- 0ms
*/

#include <iostream>

using namespace std;

const int MAX_BOARD_SIZE = 4;

int n, m;
int board[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
bool isVisited[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
int answer;

void dfs(int depth, int total);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;
	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < m; ++c)
		{
			char num = 0;
			cin >> num;
			board[r][c] = num - '0';
		}
	}

	dfs(0, 0);
	cout << answer << '\n';
}

void dfs(int depth, int total)
{
	if (depth == m * n)
	{
		answer = max(answer, total);
		return;
	}

	int r = depth / m;
	int c = depth % m;

	// 이미 방문한 칸인 경우
	if (isVisited[r][c])
	{
		dfs(depth + 1, total);
	}
	else
	{
		isVisited[r][c] = true;

		// 현재 칸을 1 x 1 칸으로 처리하는 경우
		dfs(depth + 1, total + board[r][c]);

		// 현재 칸을 세로 그룹에 포함시킬 경우
		int acc = board[r][c];
		int nr = r + 1;

		for (; nr < n; ++nr)
		{
			if (isVisited[nr][c])
			{
				break;
			}

			isVisited[nr][c] = true;
			acc = 10 * acc + board[nr][c];
			dfs(depth + 1, total + acc);
		}

		// 복구
		--nr;
		for (; nr > r; --nr)
		{
			isVisited[nr][c] = false;
		}

		// 현재 칸을 가로 그룹에 포함시킬 경우
		acc = board[r][c];
		int nc = c + 1;

		for (; nc < m; ++nc)
		{
			if (isVisited[r][nc])
			{
				break;
			}

			isVisited[r][nc] = true;
			acc = 10 * acc + board[r][nc];
			dfs(depth + 1, total + acc);
		}

		// 복구
		--nc;
		for (; nc > c; --nc)
		{
			isVisited[r][nc] = false;
		}

		isVisited[r][c] = false;
	}
}
