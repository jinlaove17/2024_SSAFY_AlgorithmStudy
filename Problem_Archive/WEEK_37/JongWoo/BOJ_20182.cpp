/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽어보니 전형적인 다익스트라 알고리즘을 조금만 변형해서 풀 수 있을 것 같다는 생각이 들었다.
	  이 문제는 최소 거리를 구하는 것이 아닌 지나온 간선의 가중치가 최소가 되도록 해야되기 때문에
	  1차원 배열로는 해당 정점에 도착했을 때 지나온 간선의 최소 가중치를 저장하도록 하였고,
	  인접 정점을 탐색할 때는 max(현재까지의 최소 가중치, 간선의 가중치)로 값을 갱신해 나가면서
	  현재까지의 누적 가중치에 간선의 가중치를 더한 값이 c보다 작을 때만 이동하도록 하여 문제를 해결할 수 있었다.

시간 복잡도
	- 일반 다익스트라 알고리즘에 인접한 정점을 추가하는 로직만 조금 바뀌므로 O(NlogN)의 시간 복잡도를 갖는다.

실행 시간
	- 252ms
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_VERTEX_COUNT = 100'000;

struct Node
{
	int index;
	int maxCost;
	int totalCost;
};

struct compare
{
	bool operator()(const Node& a, const Node& b)
	{
		if (a.maxCost == b.maxCost)
		{
			return a.totalCost > b.totalCost;
		}

		return a.maxCost > b.maxCost;
	}
};

int n, m, st, en, c;
vector<pair<int, int>> adjList[MAX_VERTEX_COUNT + 1];
int minCosts[MAX_VERTEX_COUNT + 1];

int dijkstra();

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m >> st >> en >> c;
	for (int i = 0; i < m; ++i)
	{
		int from = 0, to = 0, cost = 0;
		cin >> from >> to >> cost;
		adjList[from].emplace_back(cost, to);
		adjList[to].emplace_back(cost, from);
	}

	cout << dijkstra() << '\n';
}

int dijkstra()
{
	priority_queue<Node, vector<Node>, compare> minHeap;

	for (int i = 1; i <= n; ++i)
	{
		minCosts[i] = INF;
	}
	minCosts[st] = 0;
	minHeap.push(Node{ st, 0, 0 });

	while (!minHeap.empty())
	{
		Node cur = minHeap.top();
		minHeap.pop();

		if (cur.index == en)
		{
			return cur.maxCost;
		}

		for (const auto& p : adjList[cur.index])
		{
			int cost = p.first;
			int nxt = p.second;
			int greaterCost = max(minCosts[cur.index], cost);

			if (minCosts[nxt] > greaterCost)
			{
				int totalCost = cur.totalCost + cost;
				if (totalCost <= c)
				{
					minCosts[nxt] = greaterCost;
					minHeap.push(Node{ nxt, greaterCost, cur.totalCost + greaterCost });
				}
			}
		}
	}

	return -1;
}
