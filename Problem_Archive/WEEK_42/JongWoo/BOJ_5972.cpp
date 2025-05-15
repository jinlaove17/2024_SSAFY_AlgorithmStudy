/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽어보면 전형적인 다익스트라 유형이라는 것을 알 수 있다.
	  문제에 "헛간은 하나 이상의 길로 연결되어 있을 수도 있습니다."라는 문구가 있는데 사실 두 정점 사이에 간선을 여러개 연결하더라도
	  우선순위 큐에는 여러개가 들어갈 수 있지만, 결국 최솟값이 실행될 것이기 때문에 크게 문제될 것은 없었다.
	  이 문장을 읽고 인접 행렬 방식을 고려해보려 했지만 N이 최대 50,000으로 큰 값이었기 때문에 연결 리스트 방식을 사용해 전형적인 다익스트라 알고리즘을 적용하여 해결하였다.
	  

시간 복잡도
	- 전형적인 다익스트라를 적용하면 되므로 O(ElogV)의 시간 복잡도를 갖는다.

실행 시간
	- 24ms
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_COUNT = 50'000;

int n, m;
vector<pair<int, int>> adjList[MAX_COUNT + 1];
int minDistances[MAX_COUNT + 1];

int dijkstra(int st);

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

	cout << dijkstra(1) << '\n';
}

int dijkstra(int st)
{
	priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minHeap;

	for (int i = 1; i <= n; ++i)
	{
		minDistances[i] = INF;
	}
	minDistances[st] = 0;
	minHeap.emplace(minDistances[st], st);

	while (!minHeap.empty())
	{
		int cost = minHeap.top().first;
		int cur = minHeap.top().second;
		minHeap.pop();

		if (cur == m)
		{
			break;
		}
		else if (cost != minDistances[cur])
		{
			continue;
		}

		for (const auto& p : adjList[cur])
		{
			int toNxt = p.first;
			int nxt = p.second;

			if (minDistances[nxt] > minDistances[cur] + toNxt)
			{
				minDistances[nxt] = minDistances[cur] + toNxt;
				minHeap.emplace(minDistances[nxt], nxt);
			}
		}
	}

	return minDistances[n];
}
