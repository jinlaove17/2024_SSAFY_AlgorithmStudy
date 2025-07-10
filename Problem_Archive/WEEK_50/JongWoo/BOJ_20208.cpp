/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 맵의 크기와 민트 초코 우유의 개수가 최대 10이기 때문에 일일이 따져볼 수 있지 않을까? 하는 생각이 먼저 들었다.
	  무작정 돌리면 최악의 경우 10 * 10!이지만, 이동할 때마다 체력을 고려해야 하기 때문에 가지치기가 많이 된다.
	  우선 민트 초코 우유를 모두 다 가져가능 경우부터 따져보며 dfs 탐색을 수행해 해당 깊이에 다다르면 그 즉시 탐색을 종료하도록 구현하였다.
	  이때 매개변수로 현재 진우의 좌표를 놓고, 다음 위치로는 맨해튼 거리 공식을 사용하여 거리를 계산해 남은 체력과 비교하였다.
	  위 과정을 거치면 답을 도출할 수 있다.

시간 복잡도
	- 맵의 크기와 민트 초코 우유의 개수가 최대 10으로, 민트 초코를 최대로 가져가는 경우부터 순서를 정해 따져보는 완전 탐색을 수행할 경우에
	  최악의 경우 10 * 10!이 되지만, 다음 민트 초코의 위치로 이동할 때의 거리와 체력에 따라 탐색이 급격히 제한되기 때문에 감당이 가능한 정도가 된다.
	  따라서 시간 복잡도는 O(N * N!)이라고 할 수 있지만, 저 값에 훨씬 못미칠 것이다.

실행 시간
	- 140ms
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_BOARD_SIZE = 10;
const int MAX_MILK_COUNT = 10;
const int HOUSE = 1;
const int MINCO_MILK = 2;

int n, m, h;
vector<pair<int, int>> milks;
bool isVisited[MAX_MILK_COUNT];
int answer;

void dfs(int maxDepth, int depth, int sr, int sc, int r, int c, int drink, int health);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m >> h;
	
	int jr = 0, jc = 0;

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < n; ++c)
		{
			int cell = 0;
			cin >> cell;
			switch (cell)
			{
			case HOUSE:
				jr = r;
				jc = c;
				break;
			case MINCO_MILK:
				milks.emplace_back(r, c);
				break;
			}
		}
	}

	for (int selected = milks.size(); selected > 0; --selected)
	{
		dfs(selected, 0, jr, jc, jr, jc, 0, m);
		if (answer == selected)
		{
			break;
		}
	}

	cout << answer << '\n';
}

void dfs(int maxDepth, int depth, int sr, int sc, int r, int c, int drink, int health)
{
	if (depth == maxDepth)
	{
		// 현재 위치에서 다시 집으로 돌아가야 하므로
		int dist = abs(sr - r) + abs(sc - c);
		if (dist <= health)
		{
			answer = maxDepth;
		}

		return;
	}

	for (int i = 0; i < milks.size(); ++i)
	{
		if (isVisited[i])
		{
			continue;
		}

		int dist = abs(milks[i].first - r) + abs(milks[i].second - c);
		if (dist <= health)
		{
			isVisited[i] = true;
			dfs(maxDepth, depth + 1, sr, sc, milks[i].first, milks[i].second, drink + 1, health - dist + h);
			isVisited[i] = false;
		}
	}
}
