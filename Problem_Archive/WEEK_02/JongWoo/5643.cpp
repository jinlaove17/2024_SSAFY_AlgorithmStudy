#include <iostream>
#include <queue>
#include <cstring>

using namespace std;

const int MAX = 502;

int n, m;
int adjMat[MAX][MAX];
int inDegrees[MAX];

int TopologySort();

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int T = 0;

	cin >> T;

	for (int test_case = 1; test_case <= T; ++test_case)
	{
		cin >> n;
		cin >> m;

		for (int i = 0; i < m; ++i)
		{
			int a = 0, b = 0;

			cin >> a >> b;
			adjMat[a][b] = 1;
			++inDegrees[b];
		}

		cout << '#' << test_case << ' ' << TopologySort() << '\n';

		for (int i = 1; i <= n; ++i)
		{
			memset(adjMat[i], 0, sizeof(adjMat[i]));
		}

		memset(inDegrees, 0, sizeof(inDegrees));
	}
}

int TopologySort()
{
	int ret = 0;
	queue<int> q;

	for (int i = 1; i <= n; ++i)
	{
		if (inDegrees[i] == 0)
		{
			q.push(i);
		}
	}

	while (!q.empty())
	{
		int cur = q.front();

		q.pop();

		for (int i = 1; i <= n; ++i)
		{
			if (adjMat[cur][i] == 1)
			{
				// 나보다 키가 큰 사람은 나보다 키가 작은 사람보다 키가 크므로 adjMat에 기록한다.
				for (int j = 1; j <= n; ++j)
				{
					if (adjMat[j][cur] == 1)
					{
						adjMat[j][i] = 1;
					}
				}

				--inDegrees[i];

				if (inDegrees[i] == 0)
				{
					q.push(i);
				}
			}
		}
	}

	int sum[MAX] = {};

	// 진입 차수 계산
	for (int i = 1; i <= n; ++i)
	{
		for (int j = 1; j <= n; ++j)
		{
			if (adjMat[j][i] == 1)
			{
				++sum[i];
			}
		}
	}

	// 진출 차수 계산
	for (int i = 1; i <= n; ++i)
	{
		for (int j = 1; j <= n; ++j)
		{
			if (adjMat[i][j] == 1)
			{
				++sum[i];
			}
		}

		if (sum[i] == n - 1)
		{
			++ret;
		}
	}

	return ret;
}
