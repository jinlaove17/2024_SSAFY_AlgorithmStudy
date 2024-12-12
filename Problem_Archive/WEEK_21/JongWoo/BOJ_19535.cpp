/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- D트리는 차수가 2이상인 정점에 대해 인접한 정점들 중 차수가 2이상인 정점이 존재한다면, 최소 4개의 정점이 연결된 상태라는 것임을 깨닫는 것이 중요했다.
	  이것을 파악했다면, 두 정점의 차수에서 두 정점이 연결된 간선 1개를 뺀 값의 곱만큼 경우의 수가 존재함을 알 수 있다.
	  G트리는 차수가 3이상이어야 하고, 차수의 개수 중 3개를 택하면 4개의 정점이 연결된 상태임을 알 수 있기 때문에 조합의 값을 구해야겠다고 판단할 수 있다.
	  위 과정을 거치면 답을 도출할 수 있다. 이때, N이 최대 30만이기 때문에 int의 범위를 벗어나므로 long long 자료형을 사용해야 한다.

시간 복잡도
	- D트리의 개수를 계산하기 위해서 모든 정점 n개를 순회하며 차수가 2이상인 정점에 대해, 인접한 정점 중 차수가 2이상인 정점을 찾는데 2 * N의 시간이 걸릴 것이고,
	  G트리의 개수를 계산하기 위해서 메모이제이션 방식으로 nCr을 계산해야하므로 N * R의 시간이 걸릴 것이다.
	  따라서 O(N)의 시간 복잡도를 갖는다.

실행 시간
	- 140ms

삽질했던 내용
	- 처음에 D트리의 개수를 구할 때, DFS 탐색을 수행하여 이전에 방문하지 않은 정점이면 계속해서 탐색을 이어나가며 깊이가 4가 되었을 때마다 카운트를 하는 방식으로 수행하고,
	  중복된 경우를 제외하기 위해 2로 나누는 방식으로 구현했었는데 시간초과에 맞닥뜨렸다.
	  또한, G트리의 개수를 구할 때는 각 정점의 차수를 3으로 나누면 된다고 생각했는데, 하나의 정점에서 4개 이상의 간선이 있다면 4C3이 되기 때문에 차수C3이라는 것을 깨닫게 되었다.
	  조합의 값을 계산해야 한다고 생각했을 때는 재귀를 사용한 탑-다운 방식으로 구현했는데, N이 최대 30만이기 때문에 시간 초과가 발생했다.
	  따라서 반복문을 사용한 바텀-업 방식으로 구현하여 문제를 해결할 수 있었다.
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_VERTEX_COUNT = 300'000;

int n;
vector<int> adjList[MAX_VERTEX_COUNT + 1];
long long dTreeCnt, gTreeCnt;
long long combi[MAX_VERTEX_COUNT + 1][4]; // combi[n][r]: nCr

void CalculateCombination(int n, int r);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	int maxSize = 0;

	for (int i = 0; i < n - 1; ++i)
	{
		int from = 0, to = 0;

		cin >> from >> to;
		adjList[from].push_back(to);
		adjList[to].push_back(from);

		int fromSize = static_cast<int>(adjList[from].size());

		if (fromSize > maxSize)
		{
			maxSize = fromSize;
		}

		int toSize = static_cast<int>(adjList[to].size());

		if (toSize > maxSize)
		{
			maxSize = toSize;
		}
	}

	CalculateCombination(maxSize, 3);

	// D-트리의 개수를 구한다.
	for (int i = 1; i < n; ++i)
	{
		int iSize = static_cast<int>(adjList[i].size());

		if (iSize < 2)
		{
			continue;
		}

		for (int j = 0; j < iSize; ++j)
		{
			if (i > adjList[i][j])
			{
				continue;
			}

			int jSize = static_cast<int>(adjList[adjList[i][j]].size());

			if (jSize < 2)
			{
				continue;
			}

			dTreeCnt += (iSize - 1) * (jSize - 1);
		}
	}

	// G-트리의 개수를 구한다.
	for (int i = 1; i <= n; ++i)
	{
		gTreeCnt += combi[adjList[i].size()][3];
	}

	if (dTreeCnt > 3 * gTreeCnt)
	{
		cout << "D\n";
	}
	else if (dTreeCnt < 3 * gTreeCnt)
	{
		cout << "G\n";
	}
	else if (dTreeCnt == 3 * gTreeCnt)
	{
		cout << "DUDUDUNGA\n";
	}
}

void CalculateCombination(int n, int r)
{
	for (int i = 0; i <= n; ++i)
	{
		int en = min(i, r);

		for (int j = 0; j <= en; ++j)
		{
			if ((i == j) || (j == 0))
			{
				combi[i][j] = 1;
			}
			else
			{
				combi[i][j] = combi[i - 1][j - 1] + combi[i - 1][j];
			}
		}
	}
}
