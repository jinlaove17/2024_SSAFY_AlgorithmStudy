/*
문제 접근 아이디어 및 알고리즘 판단 사유
  - N개의 정점을 N - 1개의 선으로 잇는다 => 트리 구조임을 알 수 있으며 간선을 하나 제거했기 때문에 두 개의 트리가 존재한다고 생각하였다.
    따라서 1번 정점을 기준으로 DFS 탐색을 진행하여 방문체크를 수행하고, 다시 2번 정점부터 N번 정점까지 순회하며 방문체크가 되지 않은 정점을 잇는 방식으로
    답을 도출할 수 있었다.
  
시간 복잡도
  - 최악의 경우 N이 30만이므로, 한 번의 DFS로 299,999의 노드를 탐색한 다음 1번 노드와 나머지 1개의 노드를 연결해야 하므로 O(N)의 시간복잡돌르 갖는다.
  
실행 시간
  - 136ms(C++)
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_ISLAND_COUNT = 300'000;

int n;
vector<int> adjList[MAX_ISLAND_COUNT + 1];
bool isVisited[MAX_ISLAND_COUNT + 1];

void DFS(int cur);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int i = 0; i < n - 2; ++i)
	{
		int from = 0, to = 0;

		cin >> from >> to;
		adjList[from].push_back(to);
		adjList[to].push_back(from);
	}

	DFS(1);

	for (int i = 2; i <= n; ++i)
	{
		if (!isVisited[i])
		{
			cout << 1 << ' ' << i << '\n';
			break;
		}
	}
}

void DFS(int cur)
{
	isVisited[cur] = true;

	for (int nxt : adjList[cur])
	{
		if (isVisited[nxt])
		{
			continue;
		}

		DFS(nxt);
	}
}
