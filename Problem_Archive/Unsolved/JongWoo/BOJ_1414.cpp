/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제는 기부 할 수 있는 랜선 길이의 최댓값을 구하는 문제지만, 이를 다르게 해석하면 다솜이가 최소 길이로 모든 컴퓨터를 연결하고
	  총합에서 해당 값을 빼면 최대가 된다는 것을 알 수 있다.
	  모든 컴퓨터가 연결 되어야 한다는 문제의 조건에서 MST를 떠올릴 수 있는 힌트가 되었다고 생각한다.
	  주의할 점이 두 가지 있는데 이 문제는 오랜만에 프림 알고리즘을 사용해서 풀어봤는데, 가중치가 단방향으로 주어지지만 임의의 정점에서 시작하려면
	  a->b, b->a의 간선 중 가중치가 더 작은 간선으로 맞추어 양방향으로 저장해줘야 한다는 점이다.
	  다른 한 가지는 n이 1일 때는 이미 모든 컴퓨터가 연결된 것이므로 0->0으로 향하는 간선의 가중치를 반환해 주면 된다는 점이다.
	  위 두 가지를 고려하면 어려움 없이 문제의 답을 도출할 수 있을 것이다.

시간 복잡도
	- n개의 정점을 방문하면서, 매번 인접한 정점을 최소힙에 추가해야 하므로 O(nlogn)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int MAX_VERTEX_COUNT = 50;

int n, sum;
int adjMat[MAX_VERTEX_COUNT][MAX_VERTEX_COUNT];
bool isSelected[MAX_VERTEX_COUNT];

int prim();

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;
	for (int i = 0; i < n; ++i)
	{
		for (int j = 0; j < n; ++j)
		{
			char c = 0;
			cin >> c;
			if (c == '0')
			{
				continue;
			}

			int weight = 0;
			if (isupper(c))
			{
				weight = c - 38;
			}
			else
			{
				weight = c - 96;
			}
			sum += weight;

			if (adjMat[i][j] == 0)
			{
				adjMat[i][j] = adjMat[j][i] = weight;
			}
			else
			{
				adjMat[i][j] = adjMat[j][i] = min(adjMat[i][j], weight);
			}
		}
	}

	if (n == 1)
	{
		cout << adjMat[0][0] << '\n';
	}
	else
	{
		int mstResult = prim();
		cout << ((mstResult == 0) ? -1 : sum - mstResult) << '\n';
	}
}

int prim()
{
	int ret = 0, cnt = 0;
	priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minHeap;

	minHeap.emplace(0, 0);
	while (!minHeap.empty())
	{
		int weight = minHeap.top().first;
		int cur = minHeap.top().second;
		minHeap.pop();

		if (isSelected[cur])
		{
			continue;
		}

		isSelected[cur] = true;
		ret += weight;

		if (++cnt == n)
		{
			break;
		}

		for (int nxt = 0; nxt < n; ++nxt)
		{
			if ((adjMat[cur][nxt]) && (!isSelected[nxt]))
			{
				minHeap.emplace(adjMat[cur][nxt], nxt);
			}
		}
	}

	return (cnt == n) ? ret : 0;
}
