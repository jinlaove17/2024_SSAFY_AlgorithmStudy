/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 순환선을 찾기 위해 사이클을 판별해야 하는데 이는 DFS를 활용하여 이미 방문한 정점으로 돌아오는 경우를 식별할 수 있을 것이라는 생각이 들었다.
	  매번 이전 정점(pre)이 아니면서, 방문하지 않은 정점으로 탐색을 진행한다. 만약 

시간 복잡도
	- N개의 정점을 순회하며 사이클을 찾기 위해 최악의 경우 N개의 정점을 DFS로 순회하므로 O(N),
	  사이클에 속하는 정점들을 시작으로 사이클에 속하지 않은 정점까지의 거리를 BFS로 순회하므로 O(N)
	  최종적으로 O(N)의 시간 복잡도를 갖는다.
	  
실행 시간
	- 0ms(C++)

개선 방법
	- 사이클이 아닌 정점들에 대해 거리를 구할 때는 사이클의 정점을 시작 정점으로 BFS 탐색을 하는 것보다 사이클이 아닌 정점을 시작 정점으로 해서 사이클을 만날 때까지의 거리를 구하는 것이 보다 효율적일 것이다.
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int MAX_VERTEX_COUNT = 3'000;

int n, to;
int dist[MAX_VERTEX_COUNT + 1];
bool isVisited[MAX_VERTEX_COUNT + 1];
vector<int> adjList[MAX_VERTEX_COUNT + 1];

void FindCycle(int cur, int pre);
void BFS(int st);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int i = 1; i <= n; ++i)
	{
		int a = 0, b = 0;

		cin >> a >> b;
		adjList[a].push_back(b);
		adjList[b].push_back(a);
		dist[i] = -1;
	}

	FindCycle(1, 0);

	for (int i = 1; i <= n; ++i)
	{
		if (dist[i] == 0)
		{
			BFS(i);
		}
	}

	for (int i = 1; i <= n; ++i)
	{
		cout << dist[i] << ' ';
	}
}

void FindCycle(int cur, int pre)
{
	for (int nxt : adjList[cur])
	{
		if (nxt == pre)
		{
			continue;
		}

		if (isVisited[nxt])
		{
			to = nxt;
			break;
		}

		isVisited[nxt] = true;
		FindCycle(nxt, cur);

		if (to != 0)
		{
			break;
		}
	}

	if (to != 0)
	{
		dist[cur] = 0;
	}

	if (cur == to)
	{
		to = 0;
	}
}

void BFS(int st)
{
	queue<int> q;
	int level = 1;

	q.push(st);

	while (!q.empty())
	{
		int qSize = static_cast<int>(q.size());

		for (int i = 0; i < qSize; ++i)
		{
			int cur = q.front();

			q.pop();

			for (int nxt : adjList[cur])
			{
				if (dist[nxt] != -1)
				{
					continue;
				}

				dist[nxt] = level;
				q.push(nxt);
			}
		}

		++level;
	}
}
