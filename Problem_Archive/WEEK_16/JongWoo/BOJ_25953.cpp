/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 시작 정점에서 각 시간마다 최대 한 개의 정점을 택하면서 도착 정점까지의 최단 거리를 구해야 되기 때문에 다익스트라 알고리즘을 가장 먼저 떠올렸다.
	  우선 인접리스트 방식으로 그래프를 표현했는데, 이때 간선은 (to, cost, time)을 저장하는 Edge 구조체를 만들어 사용하였다.
	  다익스트라 알고리즘은 우선순위 큐를 가중치를 기준으로 최소힙으로 만들어 사용하였다.
	  다른 점은 dist check를 2차원으로 확장시켜 minDistance[i][j]의 값을 시간 T가 i일 때, 정점 j에 도달하는 최단 거리로 정의했다는 점과,
	  (to, cost, lastTime)을 저장하는 Node 구조체를 사용해 다음 탐색은 lastTime보다 시간이 더 큰 간선만 택하도록 구현했다는 점이다.


시간 복잡도
	- 최악의 경우 정점의 개수 N이 10,000, 시간 t가 1,000, 간선의 개수 m이 1,000이므로 O(t * nlogm)의 시간 복잡도를 갖는다고 생각하였다.

실행 시간
	- 384ms(C++)

삽질했던 내용
	- 테스트 케이스에서 간선이 번호가 작은 정점에서 큰 정점으로만 주어지기 때문에 en도 항상 st보다 클 것이라고 가정하고 간선을 단방향으로 표현했는데,
	  항상 그런 것은 아니었다.
	  또한 초기 방문체크를 minDistance[0][st] = 0;으로 해서 1초부터 시작해야 한다는 점을 망각했다.
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_VERTEX_COUNT = 10'000;
const int MAX_TIME = 1'000;

struct Edge
{
	int to;
	int cost;
	int t;
};

struct Node
{
	int to;
	int cost;
	int lastTime;

	bool operator >(const Node& rhs) const
	{
		return cost > rhs.cost;
	}
};

int n, t, m, st, en;
int minDistance[MAX_TIME + 1][MAX_VERTEX_COUNT];
vector<Edge> adjList[MAX_VERTEX_COUNT];

int Dijkstra();

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> t >> m;
	cin >> st >> en;

	for (int curTime = 1; curTime <= t; ++curTime)
	{
		for (int i = 0; i < m; ++i)
		{
			int from = 0, to = 0, cost = 0;

			cin >> from >> to >> cost;
			adjList[from].push_back(Edge{ to, cost, curTime });
			adjList[to].push_back(Edge{ from, cost, curTime });
		}
	}

	cout << Dijkstra() << '\n';
}

int Dijkstra()
{
	int ret = -1;
	priority_queue<Node, vector<Node>, greater<Node>> minHeap;

	for (int i = 1; i <= t; ++i)
	{
		for (int j = 0; j < n; ++j)
		{
			if (j == st)
			{
				continue;
			}

			minDistance[i][j] = INF;
		}
	}

	minHeap.push(Node{ st, minDistance[1][st], 0 });

	while (!minHeap.empty())
	{
		Node cur = minHeap.top();

		minHeap.pop();

		if (minDistance[cur.lastTime][cur.to] != cur.cost)
		{
			continue;
		}
		else if (cur.to == en)
		{
			ret = minDistance[cur.lastTime][cur.to];
			break;
		}

		for (const Edge& e : adjList[cur.to])
		{
			if (cur.lastTime >= e.t)
			{
				continue;
			}

			if (minDistance[e.t][e.to] > minDistance[cur.lastTime][cur.to] + e.cost)
			{
				minDistance[e.t][e.to] = minDistance[cur.lastTime][cur.to] + e.cost;
				minHeap.push(Node{ e.to, minDistance[e.t][e.to], e.t });
			}
		}
	}

	return ret;
}
