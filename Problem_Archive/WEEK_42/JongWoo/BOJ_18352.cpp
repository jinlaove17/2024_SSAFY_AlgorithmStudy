/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 가중치가 없는 그래프에서 최단거리를 구할 때는 bfs를 사용하면 된다.
	  x의 위치에서 bfs 탐색을 시작해 다른 노드까지의 거리를 구하고, 배열에 저장된 값이 k인 노드에 대해 출력하고 없다면 -1을 출력해주면 된다.
	  

시간 복잡도
	- 최악의 경우 N이 30만일 때를 고려해야 하는데, 단순히 bfs로 각 노드를 방문하면 되므로 O(N)의 시간이 걸린다.

실행 시간
	- 264ms
*/

#include <iostream>
#include <queue>
#include <algorithm>

using namespace std;

const int MAX_COUNT = 10'000;

int n, m;
int times[MAX_COUNT];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;
	for (int i = 0; i < n; ++i)
	{
		cin >> times[i];
	}
	sort(times, times + n, greater<int>());

	priority_queue<int, vector<int>, greater<int>> minHeap;
	while (minHeap.size() < m)
	{
		minHeap.push(0);
	}

	for (int i = 0; i < n; ++i)
	{
		minHeap.push(minHeap.top() + times[i]);
		minHeap.pop();
	}

	while (minHeap.size() != 1)
	{
		minHeap.pop();
	}

	cout << minHeap.top() << '\n';
}
