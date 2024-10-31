/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽어보면 MST임을 곧바로 알아차릴 수 있다.
	  하지만, 평소에 풀었던 2차원 공간이 아닌 3차원 공간의 MST이므로 좌표가 3개이며 간선 또한 주어지지 않으므로 간선을 직접 만들어야 하는데
	  행성의 수가 최악의 경우 10만이기 때문에 완전 그래프를 만드는 것을 불가능하다고 생각했다.
	  간선의 비용은 min(|x1-x2|, |y1-y2|, |z1-z2|)이기 때문에 각 속성(x, y, z)별로 정렬을 수행했을 때 인접한 속성을 가진 행성과의 거리가 최소 비용이 될 것이다.
	  따라서 x, y, z별로 행성을 정렬한 뒤 현재 행성에서 다음 행성 간의 간선을 만들어 준다.
	  이렇게 되면 n - 1개의 간선이 x, y, z별로 만들어 지고 이때는 반드시 연결 그래프임이 보장됨을 알 수 있다.
	  사실 10만개에 대해 간선을 구하는 작업을 어떻게 하는지가 이 문제의 핵심이었고 그 다음에는 크루스칼을 사용하여 MST를 구하면 답을 도출할 수 있다.

시간 복잡도
	- 각 행성을 x, y, z를 기준으로 정렬하기 위해 3번의 정렬을 수행하므로 3 * nlogn의 시간이 소요되고, 간선을 만드는데 3 * n이 시간이 소요된다.
	  이후 간선들을 cost를 기준으로 정렬하는데 nlogn의 시간이 걸리고 마지막으로 경로 압축을 적용한 Union-Find 알고리즘을 수행하는데 O(α(N))의 시간이 걸린다.
	  따라서 O(nlogn)의 시간 복잡도를 갖는다.

실행 시간
	- 96ms(C++)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX_PLANET_COUNT = 100'000;
const int SORTED_X = 0;
const int SORTED_Y = 1;
const int SORTED_Z = 2;
const int SORTED = 3;

struct Planet
{
	int x;
	int y;
	int z;
	int index;
};

struct Edge
{
	int a;
	int b;
	int cost;

	bool operator <(const Edge& rhs) const
	{
		return cost < rhs.cost;
	}
};

int n;
Planet sortedPlanet[SORTED][MAX_PLANET_COUNT + 1];
vector<Edge> edges;
int parents[MAX_PLANET_COUNT + 1];

void MakeSet();
bool UnionSet(int a, int b);
int FindSet(int a);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int i = 0; i < n; ++i)
	{
		int x = 0, y = 0, z = 0;

		cin >> x >> y >> z;

		for (int j = 0; j < SORTED; ++j)
		{
			sortedPlanet[j][i].x = x;
			sortedPlanet[j][i].y = y;
			sortedPlanet[j][i].z = z;
			sortedPlanet[j][i].index = i;
		}
	}

	sort(sortedPlanet[SORTED_X], sortedPlanet[SORTED_X] + n,
		[](const Planet& a, const Planet& b) {
			if (a.x == b.x)
			{
				return a.index < b.index;
			}

			return a.x < b.x;
		});

	sort(sortedPlanet[SORTED_Y], sortedPlanet[SORTED_Y] + n,
		[](const Planet& a, const Planet& b) {
			if (a.y == b.y)
			{
				return a.index < b.index;
			}

			return a.y < b.y;
		});

	sort(sortedPlanet[SORTED_Z], sortedPlanet[SORTED_Z] + n,
		[](const Planet& a, const Planet& b) {
			if (a.z == b.z)
			{
				return a.index < b.index;
			}

			return a.z < b.z;
		});

	for (int i = 0; i < n - 1; ++i)
	{
		for (int j = 0; j < SORTED; ++j)
		{
			int diff = 0;

			switch (j)
			{
			case SORTED_X:
				diff = sortedPlanet[j][i + 1].x - sortedPlanet[j][i].x;
				break;
			case SORTED_Y:
				diff = sortedPlanet[j][i + 1].y - sortedPlanet[j][i].y;
				break;
			case SORTED_Z:
				diff = sortedPlanet[j][i + 1].z - sortedPlanet[j][i].z;
				break;
			}
			
			edges.push_back(Edge{ sortedPlanet[j][i].index, sortedPlanet[j][i + 1].index, diff });
		}
	}

	long long answer = 0;
	int selected = 0;

	MakeSet();
	sort(edges.begin(), edges.end());

	for (const Edge& edge : edges)
	{
		if (UnionSet(edge.a, edge.b))
		{
			answer += edge.cost;

			if (++selected == n - 1)
			{
				break;
			}
		}
	}

	cout << answer << '\n';
}

void MakeSet()
{
	for (int i = 0; i < n; ++i)
	{
		parents[i] = -1;
	}
}

bool UnionSet(int a, int b)
{
	int aSet = FindSet(a);
	int bSet = FindSet(b);

	if (aSet == bSet)
	{
		return false;
	}

	parents[aSet] += parents[bSet];
	parents[bSet] = aSet;

	return true;
}

int FindSet(int a)
{
	if (parents[a] < 0)
	{
		return a;
	}

	return parents[a] = FindSet(parents[a]);
}
