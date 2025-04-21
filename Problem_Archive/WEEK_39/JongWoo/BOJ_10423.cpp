/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에는 다익스트라를 사용해야 될 것 같다는 생각이 들었는데, 각 정점에서 인접한 간선을 선택할 때마다 최소 간선을 선택해야하기 때문에
	  다익스트라로는 해결이 어려웠다. 그래서 각 정점에서 최소 간선을 선택하며 나아가는 프림(PRIM) 알고리즘을 떠올릴 수 있었고
	  시작 정점만 여러 개인 프림 알고리즘으로 문제를 해결할 수 있었다.

시간 복잡도
	- 시작 정점이 여러 개인 프림 알고리즘이므로, O(ElogV)의 시간 복잡도를 갖는다.

실행 시간
	- 28ms
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int MAX_CITY = 1'000;

int n, m, k;
vector<pair<int, int>> adjList[MAX_CITY + 1];
bool isSelected[MAX_CITY + 1];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m >> k;

	vector<int> st;
	for (int i = 0; i < k; ++i)
	{
		int v = 0;
		cin >> v;
		st.push_back(v);
	}

	for (int i = 0; i < m; ++i)
	{
		int from = 0, to = 0, w = 0;
		cin >> from >> to >> w;
		adjList[from].emplace_back(w, to);
		adjList[to].emplace_back(w, from);
	}

	priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minHeap;
	for (int num : st)
	{
		isSelected[num] = true;

		for (const auto& p : adjList[num])
		{
			minHeap.emplace(p.first, p.second);
		}
	}

	int answer = 0, selected = k;
	while (!minHeap.empty())
	{
		int w = minHeap.top().first;
		int cur = minHeap.top().second;
		minHeap.pop();

		if (isSelected[cur])
		{
			continue;
		}

		isSelected[cur] = true;
		answer += w;

		if (++selected == n)
		{
			break;
		}

		for (const auto& p : adjList[cur])
		{
			if (!isSelected[p.second])
			{
				minHeap.emplace(p.first, p.second);
			}
		}
	}

	cout << answer << '\n';
}
