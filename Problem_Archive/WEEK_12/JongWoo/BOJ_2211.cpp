/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- MST로 시작하는 삽질을 거쳐, 슈퍼 컴퓨터인 1번 정점에서 다른 모든 정점까지의 최단 거리를 구하고, 이때 현재 정점에 도달하게 한 이전 정점을 기억하여
	  역추적을 통해 선택 된 간선을 구할 수 있도록 구현하였다.

시간 복잡도
	- 다익스트라로 1번 정점에서 다른 모든 정점까지의 최단거리를 구하는 알고리즘에서 가장 많은 시간이 소요되므로 시간 복잡도는 O(NlogN)이 된다.

실행 시간
	- 60ms(C++)

삽질했던 내용이나 코드
	- 처음에 1번 "최소 개수의 회선만을 복구해야 한다."라는 조건을 읽고, MST를 써야겠구나 생각했는데,
	  2번에 "슈퍼컴퓨터가 다른 컴퓨터들과 통신하는데 걸리는 최소 시간이, 원래의 네트워크에서 통신하는데 걸리는 최소 시간보다 커져서는 안 된다."라는 조건을 읽었을 때는
	  다익스트라 알고리즘도 고려하게 되었다.
	- 두 알고리즘을 합쳐서 구현하기에는 접점이 너무 없는 것 같아서 먼저 MST(크루스칼)를 사용해서 정렬 기준을 cost 기준 -> 1번 정점 포함 -> 출발 정점 오름차순 -> 도착 정점 오름차순으로
	  설정하고 구현을 해봤는데 테스트 케이스에서는 잘 동작하나, 만약 간선 정보가 1 2 1, 1 3 2, 2 3 2와 같이 주어졌을 때 총 비용은 3이 들어 최소가 되지만,
	  1번에서 2번으로 향하는 간선을 택했다면 3번까지의 가중치는 3이되어 1에서 곧바로 이동하는 2보다 커지므로 2번 조간에 위배되게 된다.
	  즉, 이 간단한 반례 하나만으로 MST로는 이 문제를 해결할 수 없다는 것을 뒤늦게야 깨닫게 된 것이다.
	- 그렇다면, 정렬 기준을 1번 정점 포함 -> cost 기준 -> ... 으로 바꾼다면 어떻게 될까? 이번에는 위 예시는 통과하지만 주어진 테스트 케이스에서
	  1번 정점에서 2번, 3번 정점으로 가는 가중치를 9,999로 바꾼다면 (1, 4), (2, 4), (3, 4)라는 답을 도출하지 못하고 (1, 2), (1, 3), (2, 4)라는 답을 도출하게 되어
	  이번에도 조건을 만족하지 못하게 된다.
	- 여기까지 해보고 MST는 도저히 안된다는 것을 인지하고서야 다익스트라로 알고리즘을 바꿀 수 있었다.
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_VERTEX_COUNT = 1'000;

int n, m;
int pre[MAX_VERTEX_COUNT + 1];
int minDist[MAX_VERTEX_COUNT + 1];
vector<pair<int, int>> adjList[MAX_VERTEX_COUNT + 1];

void Dijkstra(int st);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int i = 0; i < m; ++i)
	{
		int a = 0, b = 0, cost = 0;

		cin >> a >> b >> cost;
		adjList[a].emplace_back(cost, b);
		adjList[b].emplace_back(cost, a);
	}

	Dijkstra(1);

	vector<pair<int, int>> restored;
	bool isVisited[MAX_VERTEX_COUNT + 1] = {};

	for (int i = n; i >= 1; --i)
	{
		int cur = i;

		while ((pre[cur] != 0) && (!isVisited[cur]))
		{
			restored.emplace_back(pre[cur], cur);
			isVisited[cur] = true;
			cur = pre[cur];
		}
	}

	cout << restored.size() << '\n';

	for (const auto& edge : restored)
	{
		cout << edge.first << ' ' << edge.second << '\n';
	}
}

void Dijkstra(int st)
{
	priority_queue<pair<int, int>> q; // <비용, 정점의 인덱스>

	for (int i = 1; i <= n; ++i)
	{
		minDist[i] = INF;
	}

	minDist[st] = 0;
	q.emplace(minDist[st], st);

	while (!q.empty())
	{
		int cost = q.top().first;
		int cur = q.top().second;

		q.pop();

		if (minDist[cur] != cost)
		{
			continue;
		}

		for (const auto& p : adjList[cur])
		{
			cost = p.first;

			int nxt = p.second;

			if (minDist[nxt] > minDist[cur] + cost)
			{
				minDist[nxt] = minDist[cur] + cost;
				pre[nxt] = cur;
				q.emplace(minDist[nxt], nxt);
			}
		}
	}
}
