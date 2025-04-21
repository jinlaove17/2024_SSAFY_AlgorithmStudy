/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 전체 가중치에서 최소 비용으로 모든 길을 연결했을 때의 가중치 합을 뺀 값이 문제에서 요구하는 '절약할 수 있는 최대 액수'가 된다.
	  즉, 최소 비용으로 모든 길을 연결한다. => MST 알고리즘을 떠올릴 수 있었다.
	  크루스칼은 전형적인 Union-Find에 경로 압축을 적용한 알고리즘을 사용하여 해결하였다.
	  이때, parents 배열은 음수로 설정하여, 절댓값이 더 큰, 즉 집합의 크기가 더 큰 곳에 확장하는 방식을 사용하였다.

시간 복잡도
	- 모든 간선을 정렬하는데 O(ElogE)의 시간이 걸리며, 경로 압축을 사용한 Union-Find 알고리즘을 사용하는데 O(α(N))이 걸리므로 O(ElogE)의 시간 복잡도를 갖는다.

실행 시간
	- 168ms
*/

#include <iostream>
#include <algorithm>

using namespace std;

struct Edge
{
	int from;
	int to;
	int cost;
};

const int MAX_HOUSE = 200'000;
const int MAX_ROAD = 200'000;

int m, n;
Edge edges[MAX_ROAD];
int parents[MAX_HOUSE];

int getParent(int a);
bool setUnion(int a, int b);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	while (true)
	{
		cin >> m >> n;
		if ((m == 0) && (n == 0))
		{
			break;
		}

		int totalCost = 0;

		for (int i = 0; i < n; ++i)
		{
			cin >> edges[i].from >> edges[i].to >> edges[i].cost;
			totalCost += edges[i].cost;
		}

		for (int i = 0; i < m; ++i)
		{
			parents[i] = -1;
		}

		sort(edges, edges + n,
			[](const Edge& a, const Edge& b)
			{
				return a.cost < b.cost;
			});

		int selected = 0;
		for (const Edge& edge : edges)
		{
			if (setUnion(edge.from, edge.to))
			{
				totalCost -= edge.cost;
				if (++selected == m - 1)
				{
					break;
				}
			}
		}

		cout << totalCost << '\n';
	}
}

int getParent(int a)
{
	if (parents[a] < 0)
	{
		return a;
	}

	return parents[a] = getParent(parents[a]);
}

bool setUnion(int a, int b)
{
	int aRoot = getParent(a);
	int bRoot = getParent(b);

	if (aRoot != bRoot)
	{
		if (parents[aRoot] < parents[bRoot])
		{
			parents[aRoot] += parents[bRoot];
			parents[bRoot] = aRoot;
		}
		else
		{
			parents[bRoot] += parents[aRoot];
			parents[aRoot] = bRoot;
		}

		return true;
	}

	return false;
}
