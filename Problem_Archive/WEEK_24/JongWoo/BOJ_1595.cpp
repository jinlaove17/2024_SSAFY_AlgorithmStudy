/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 가장 거리가 먼 두 정점은 가장 바깥쪽에 있는, 즉 진입차수가 1인(양방향 그래프이므로) 정점 쌍이 되어야 한다는 생각이 들었다.
	  따라서 인접 리스트 방식으로 그래프를 표현할 때 진입차수도 같이 계산해준 다음, 진입차수가 1인 정점들에 대해 DFS 탐색을 수행하여
	  매 깊이마다 가중치를 누적하며 최댓값을 구하면 문제에서 원하는 답을 도출할 수 있다.

시간 복잡도
	- 1번 정점부터 시작해 가장 큰 정점 번호까지 순회하며 진입차수가 1인 정점을 출발지로 설정한 후 DFS 탐색을 진행한다.
	  진입차수가 1인 정점이 K개 일 때, DFS 탐색으로 매번 N개의 정점을 순회하므로 O(K * N)의 시간 복잡도를 갖는다.

실행 시간
	- 100ms

삽질했던 내용
	- 삽질한 내용은 아니지만, 이 문제는 사실 트리의 지름을 구하는 문제인걸 알았으나 임의의 정점에서 그래프 탐색을 통해 가장 먼 정점을 구하고,
	  해당 정점에서 다시 그래프 탐색을 통해 가장 먼 정점을 구했을 때, 두 정점 사이의 거리가 최장 거리가 되는데 이 알고리즘을 증명할 수 없었기 때문에
	  해당 방법으로 풀지 않았다. 그래서 증명을 구글링을 좀 찾아봤다.
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_CITY_COUNT = 10'000;

vector<pair<int, int>> adjList[MAX_CITY_COUNT + 1];
int inDegreeList[MAX_CITY_COUNT + 1];
int maxCityIndex, answer;

void dfs(int cur, int pre, int sum);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int from = 0, to = 0, dist = 0;
	while (cin >> from >> to >> dist)
	{
		adjList[from].emplace_back(dist, to);
		adjList[to].emplace_back(dist, from);
		++inDegreeList[from];
		++inDegreeList[to];
		maxCityIndex = max(maxCityIndex, max(from, to));
	}

	for (int i = 1; i <= maxCityIndex; ++i)
	{
		if (inDegreeList[i] == 1)
		{
			dfs(i, -1, 0);
		}
	}

	cout << answer << '\n';
}

void dfs(int cur, int pre, int sum)
{
	answer = max(answer, sum);

	for (const auto& p : adjList[cur])
	{
		int dist = p.first;
		int nxt = p.second;

		if (nxt == pre)
		{
			continue;
		}

		dfs(nxt, cur, sum + dist);
	}
}
