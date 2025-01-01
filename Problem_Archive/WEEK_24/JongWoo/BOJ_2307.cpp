/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 설명이 굉장히 상세히 나와 있어서 조건을 살펴보면 1번 정점에서 N번 정점까지의 최단거리를 먼저 구해야겠다는 생각이 든다.
	  따라서 다익스트라 알고리즘을 수행하여 1번 정점에서 다른 모든 정점까지의 최단 거리를 구한다.
	  이제 한 개의 간선을 제거하여 1번 정점에서 N번 정점에 가는 시간을 최대로 만들어야 하는데, 매번 M개의 간선을 1개씩 제거해보면서 다익스트라 알고리즘을
	  수행한다면 최단 거리로 이동했을 때 사용된 간선들이 아닐 때는 무의미한 연산을 하게 된다. 왜냐하면 그대로 최단거리에 사용된 간선들이 사용될 것이기 때문이다.
	  따라서 최단거리로 이동했을 때, 간선을 복원할 수 있도록 부모의 인덱스를 별도로 저장해두고 이를 이용해 최단거리에 사용된 간선들을 한 개씩 제거해보면서
	  다익스트라 알고리즘을 사용하면 문제에서 요구하는 답을 도출할 수 있을 것이다.

시간 복잡도
	- 우선 처음에 다익스트라를 사용해 1번 정점에서 다른 모든 정점까지의 최단 거리를 구하는데 ElogV의 시간이 걸린다.
	  그 다음, 경로를 복원하고 사용된 간선의 개수(K)만큼 사용된 간선을 한개씩 제거해보면서 다익스트라 알고리즘을 수행해야 하기 때문에 K * ElogV의 시간이 걸린다.
	  따라서 총 시간 복잡도는 O(K * ElogV)가 된다.

실행 시간
	- 96ms
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_VERTEX_COUNT = 1'000;

int n, m;
vector<pair<int, int>> adjList[MAX_VERTEX_COUNT + 1];
int minCostList[MAX_VERTEX_COUNT + 1];
int parentList[MAX_VERTEX_COUNT + 1];
vector<pair<int, int>> candiList;

void Dijkstra(int st, int edgeIndex);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int i = 0; i < m; ++i)
	{
		int from = 0, to = 0, cost = 0;
		cin >> from >> to >> cost;
		adjList[from].emplace_back(cost, to);
		adjList[to].emplace_back(cost, from);
	}

	Dijkstra(1, -1);
	int optCost = minCostList[n];

	int cur = n;
	while (true)
	{
		int pre = parentList[cur];
		if (pre == -1)
		{
			break;
		}

		candiList.emplace_back(pre, cur);
		cur = pre;
	}

	int answer = 0;
	for (int i = 0; i < candiList.size(); ++i)
	{
		Dijkstra(1, i);
		
		if (minCostList[n] == INF)
		{
			answer = -1;
			break;
		}

		answer = max(answer, minCostList[n] - optCost);
	}

	cout << answer << '\n';
}

void Dijkstra(int st, int edgeIndex)
{
	priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minHeap;

	for (int i = 1; i <= n; ++i)
	{
		minCostList[i] = INF;
		parentList[i] = -1;
	}

	minCostList[st] = 0;
	minHeap.emplace(minCostList[st], st);

	while (!minHeap.empty())
	{
		int cost = minHeap.top().first;
		int cur = minHeap.top().second;
		minHeap.pop();

		if (cost != minCostList[cur])
		{
			continue;
		}

		for (const auto& p : adjList[cur])
		{
			int toNextCost = p.first;
			int nxt = p.second;

			if (edgeIndex != -1)
			{
				if ((cur == candiList[edgeIndex].first) && (nxt == candiList[edgeIndex].second))
				{
					continue;
				}
			}

			if (minCostList[nxt] > minCostList[cur] + toNextCost)
			{
				minCostList[nxt] = minCostList[cur] + toNextCost;
				minHeap.emplace(minCostList[nxt], nxt);
				parentList[nxt] = cur;
			}
		}
	}
}
