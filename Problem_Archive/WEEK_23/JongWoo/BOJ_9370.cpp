/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 최단 거리라는 단어가 언급되고, 반드시 지나야 하는 간선이 주어졌을 때 답이 될 수 있는 정점의 인덱스를 구하는 문제였다.
	  단순히 시작 정점에서 다익스트라 알고리즘을 통해 다른 모든 정점의 최단거리를 구하는 것은 반드시 지나야 하는 간선을 사용하지 않고도 최단거리가 될 수 있기 때문에
	  한 번의 다익스트라를 사용해서는 문제를 해결할 수 없고 g와 h에서도 다익스트라를 적용한 다음 s->g->h->c 혹은 s->h->g->c의 경로로 이동했을 때의 거리가
	  시작 정점에서 구한 최단거리와 같으면 가능한 목적지가 된다는 것을 알 수 있다.

시간 복잡도
	- 각 테스트 케이스마다 시작 교차로 s, 교차로 g, 교차로 h에서 각각 다익스트라 알고리즘을 사용하여 다른 모든 교차로까지의 최단 거리를 구하는데 3 * NlogM의 시간이 걸리며,
	  목적지 후보 교차로들을 순회하며, 거리가 일치하는지 확인해야하므로 t의 시간이 걸린다.
	  따라서 총 시간 복잡도는 O(NlogM * t)가 된다.

실행 시간
	- 32ms

삽질했던 내용
	- 시작 교차로에서 다익스트라 알고리즘을 적용하여 다른 모든 교차로까지의 최단 거리를 구할 때, 현재 교차로에 도달하기 위해 이전에 방문했던 교차로를
	  prevList 배열에 저장하고, 최종적으로 목적지 후보 교차로들을 순회하면서 경로를 복원했을 때, g와 h 혹은 h와 g 간선을 이용했다면 해당 교차로를 저장하는 방식으로
	  구현을 했는데, 이와 같이 구현하는 것은 시작 교차로에서 이동 시 반드시 g와 h 혹은 h와 g를 거쳐가야만 최단거리가 될 때만 가능하다는 문제가 있었다.
	  즉, 같은 가중치합으로 이동하지만 g와 h 혹은 h와 g를 거치지 않고 이동할 수 있는 최단 경로가 있을 경우에는 위 알고리즘은 답을 도출할 수 없는 것이다.
	  이와 같은 문제를 겪고, 시작 교차로 외에도 g와 h에서도 다른 모든 교차로 간의 최단 거리가 필요함을 깨달아서 3번의 다익스트라를 사용하게 되었다.
*/

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

const int INF = 987'654'321;
const int MAX_VERTEX_COUNT = 2'000;
const int MAX_CANDIDATE_COUNT = 100;

int n, m, t; // n: 교차로(정점)의 수, m: 도로(간선)의 수, t: 목적지 후보의 수
int s, g, h; // s: 출발지, g, h: 지나는 교차로 쌍
vector<pair<int, int>> adjList[MAX_VERTEX_COUNT + 1];
int candidateList[MAX_CANDIDATE_COUNT];
int minCostLists[3][MAX_VERTEX_COUNT + 1];

void Dijkstra(int st, int* minCostList);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int T = 0;
	cin >> T;
	for (int tc = 1; tc <= T; ++tc)
	{
		cin >> n >> m >> t;
		cin >> s >> g >> h;

		for (int i = 1; i <= n; ++i)
		{
			adjList[i].clear();
		}

		int usedEdgeCost = 0;

		for (int i = 0; i < m; ++i)
		{
			int from = 0, to = 0, cost = 0;
			cin >> from >> to >> cost;
			adjList[from].emplace_back(cost, to);
			adjList[to].emplace_back(cost, from);

			// g-h 사이의 간선은 가중치를 저장해둔다.
			if (((from == g) && (to == h)) || ((from == h) && (to == g)))
			{
				usedEdgeCost = cost;
			}
		}

		for (int i = 0; i < t; ++i)
		{
			cin >> candidateList[i];
		}

		Dijkstra(s, minCostLists[0]);
		Dijkstra(g, minCostLists[1]);
		Dijkstra(h, minCostLists[2]);

		sort(candidateList, candidateList + t);
		for (int i = 0; i < t; ++i)
		{
			int s2c = minCostLists[0][candidateList[i]]; // s to candi
			int s2g2h2c = minCostLists[0][g] + usedEdgeCost + minCostLists[2][candidateList[i]]; // s to g -> g to h -> h to candi
			int s2h2g2c = minCostLists[0][h] + usedEdgeCost + minCostLists[1][candidateList[i]]; // s to h -> h to g -> g to candi

			// 그래프가 s - g - h - candi와 같이 주어진다고 하더라도, 한 경우는 usedEdgeCost가 두 번 더해지지만 다른 하나는 반드시 올바른 경우가 됨
			if ((s2c == s2g2h2c) || (s2c == s2h2g2c))
			{
				cout << candidateList[i] << ' ';
			}
		}
		cout << '\n';
	}
}

void Dijkstra(int st, int* minCostList)
{
	priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minHeap; // <cost, vertex>

	for (int i = 1; i <= n; ++i)
	{
		minCostList[i] = INF;
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
			int adjCost = p.first;
			int nxt = p.second;

			if (minCostList[nxt] > minCostList[cur] + adjCost)
			{
				minCostList[nxt] = minCostList[cur] + adjCost;
				minHeap.emplace(minCostList[nxt], nxt);
			}
		}
	}
}
