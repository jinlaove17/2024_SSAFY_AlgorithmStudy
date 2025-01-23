/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- n이 최대 100이기 때문에, 매장을 지을 건물 2개를 조합으로 선택한 다음 base condition에서 bfs 탐색을 진행하는 완전 탐색으로 문제를 해결할 수 있으리라 생각했다.
	  bfs 탐색은 선택된 두 건물에서 시작해 모든 노드를 탐색하며 두 치킨집 중 더 가까운 거리를 기록한다.
	  최종적으로 왕복값을 구해야 하므로 2를 곱한 값들을 누적하면 답을 도출할 수 있다.

시간 복잡도
	- 조합을 통해 2개의 건물을 선택하고, 두 번의 bfs 탐색을 통해 각 건물에서 치킨집까지 최소 거리를 계산하므로 O(nC2 * n)의 시간 복잡도를 갖는다.

실행 시간
	- 48ms
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_BUILDING_COUNT = 100;

int n, m;
int selected[2], answers[3], minDistList[MAX_BUILDING_COUNT + 1];
vector<int> adjList[MAX_BUILDING_COUNT + 1];

void combination(int depth, int num);
void bfs(int st);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int i = 0; i < m; ++i)
	{
		int from = 0, to = 0;
		cin >> from >> to;
		adjList[from].push_back(to);
		adjList[to].push_back(from);
	}

	answers[2] = INF;
	combination(0, 1);
	cout << answers[0] << ' ' << answers[1] << ' ' << answers[2] << '\n';
}

void combination(int depth, int num)
{
	if (depth == 2)
	{
		for (int i = 1; i <= n; ++i)
		{
			minDistList[i] = INF;
		}

		bfs(selected[0]);
		bfs(selected[1]);

		int total = 0;
		for (int i = 1; i <= n; ++i)
		{
			total += 2 * minDistList[i];
		}

		if (answers[2] > total)
		{
			answers[0] = selected[0];
			answers[1] = selected[1];
			answers[2] = total;
		}

		return;
	}

	for (int i = num; i <= n; ++i)
	{
		selected[depth] = i;
		combination(depth + 1, i + 1);
	}
}

void bfs(int st)
{
	queue<int> q;

	minDistList[st] = 0;
	q.push(st);

	while (!q.empty())
	{
		int cur = q.front();
		q.pop();

		for (int nxt : adjList[cur])
		{
			if (minDistList[nxt] > minDistList[cur] + 1)
			{
				minDistList[nxt] = minDistList[cur] + 1;
				q.push(nxt);
			}
		}
	}
}
