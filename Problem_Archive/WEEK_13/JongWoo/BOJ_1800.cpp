/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에는 DFS를 사용해 완전 탐색을 하고, 기저(정점 N에 도착한 경우)에서 방문한 비용 중 k + 1번째 값을 찾았는데 이는 시간 초과가 발생했다.
	  그래서 다른 방법을 계속해서 고민해봤는데 도무지 방법이 떠오르지 않아서 알고리즘 분류를 참고하게 되었는데, 다익스트라와 이분 탐색이라는 정보를 보게 되었다.
	  하지만, 이 문제에서 최단 거리는 쓸모 없고 이분 탐색은 전혀 생각지도 못했기 때문에 분류를 봐도 감조차 오지 않았다.
	  결과적으로 구글링을 통해 다른 사람의 풀이를 참고하여 해결할 수 있었다.
	  다익스트라와 이분 탐색을 사용한 풀이와 방문 체크를 2차원 배열로 하는 다익스트라 풀이가 있었는데 이분 탐색은 생각지도 못했기 때문에 후자의 풀이로 해결할 수 있었다.
	  이 풀이는 정점, 비용, 현재까지 사용한 무료 케이블의 수를 저장하는 Edge 구조체를 정의하고 최소 힙을 통해 비용이 적은 간선부터 뽑아 각 정점에 도달하는 최대 비용을 저장한다.
	  최대 비용을 저장하는 이유는 결과적으로 정점 N에 도착했다면 k번의 무료 케이블을 사용한 것이기 때문에 문제에서 원하는 그 다음 최대 비용을 알아야 하기 때문이다.
	  매번 탐색을 진행하면서 무료 케이블을 사용하는 경우와 사용하지 않는 경우를 최소 힙에 추가한다는 아이디어와,
	  2차원 배열을 이용해 minCosts[i][j]: i번째 정점에 j번 무료 케이블을 사용해 도착했을 때의 최소 비용을 저장한다는 아이디어가 굉장히 핵심이었다.
	  즉, 각 정점에 도달하는 최소 비용을 구하고 최종적으로 정점 N에 도착했을 때의 최소 비용이 지불해야 할 최대 비용이라는 것이다.
	  이 문제... 발상이 굉장히 어려운 문제였다. 꼭 나중에 다시 풀어봐야겠다.

시간 복잡도
	- 일반적인 다익스트라에 각 탐색마다 2개의 간선이 추가되므로 O(2ElogV)의 시간 복잡도를 갖으므로, 최종적으로 O(ElogV)의 시간 복잡도를 갖는다.

실행 시간
	- 4ms(C++)
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int INF = 0x3f3f3f3f;
const int MAX_STUDENT_COUNT = 1'000;
const int MAX_FREE_CABLE_COUNT = 999;

struct Edge
{
	int vertex;
	int cost;
	int freeCableCnt;

	bool operator <(const Edge& rhs) const
	{
		// 최소 힙
		return cost > rhs.cost;
	}
};

int n, p, k;
int minCosts[MAX_STUDENT_COUNT + 1][MAX_FREE_CABLE_COUNT + 1];
vector<pair<int, int>> adjList[MAX_STUDENT_COUNT + 1]; // <cost, vertex>

int Dijkstra(int st);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> p >> k;

	for (int i = 0; i < p; ++i)
	{
		int a = 0, b = 0, cost = 0;

		cin >> a >> b >> cost;
		adjList[a].emplace_back(cost, b);
		adjList[b].emplace_back(cost, a);
	}

	cout << Dijkstra(1) << '\n';
}

int Dijkstra(int st)
{
	int ret = INF;
	priority_queue<Edge> minHeap;

	for (int i = 1; i <= n; ++i)
	{
		for (int j = 0; j <= k; ++j)
		{
			minCosts[i][j] = INF;
		}
	}

	minHeap.push(Edge{ st, 0, 0 });

	while (!minHeap.empty())
	{
		Edge edge = minHeap.top();

		minHeap.pop();

		if (edge.cost > minCosts[edge.vertex][edge.freeCableCnt])
		{
			continue;
		}

		// 매번 비용이 작은 간선부터 처리하므로, 최초로 n번 정점에 도착했다면 가장 적은 비용으로 도착한 것임.
		if (edge.vertex == n)
		{
			// ret = minCosts[n][edge.freeCableCnt];
			// 위 코드는 간선이 1개일 경우, 처음에 넣은 간선의 minCosts[i][j] 값이 갱신되지 않았기 때문에
			// INF를 반환하는 문제가 발생한다. 따라서 edge.cost를 사용했다.
			ret = edge.cost;
			break;
		}

		for (const auto& p : adjList[edge.vertex])
		{
			int cost = p.first;
			int nextVertex = p.second;
			int greaterCost = max(edge.cost, cost);

			if (minCosts[nextVertex][edge.freeCableCnt] > greaterCost)
			{
				minCosts[nextVertex][edge.freeCableCnt] = greaterCost;
				minHeap.push(Edge{ nextVertex, greaterCost, edge.freeCableCnt });
			}

			if (edge.freeCableCnt < k)
			{
				if (minCosts[nextVertex][edge.freeCableCnt + 1] > edge.cost)
				{
					minCosts[nextVertex][edge.freeCableCnt + 1] = edge.cost;
					minHeap.push(Edge{ nextVertex, edge.cost, edge.freeCableCnt + 1 });
				}
			}
		}
	}

	return (ret == INF) ? -1 : ret;
}
