/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- n이 최대 10으로 굉장히 작기도 하고, 각 구역을 이분화 해야하기 때문에 비트 마스킹을 떠올리게 되었다.
	  비트 마스킹으로 1부터 (1 << n) - 1까지 순회하며 각 비트열이 0이냐 1이냐로 나눈 다음, 각 노드에서 bfs 탐색을 수행하여 연결 여부를 판단하였다.
	  위 과정을 거치면 쉽게 답을 도출할 수 있다.

시간 복잡도
	- 비트 마스킹을 통해 N개의 구역을 이분화 하는데 최악의 경우 2^10번 연산이 수행되며 구역을 나누고 연결 여부를 판단하기 위해 bfs 탐색을 수행하는데 n번 연산이 수행된다.
	  따라서 최종적인 시간 복잡도는 O(n * 2^n)가 된다.
	  지수 시간복잡도를 갖지만 n이 10으로 굉장히 작기 때문에 충분히 시간안에 해결할 수 있다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <vector>
#include <queue>
#include <cstring>

using namespace std;

const int INF = 987'654'321;
const int MAX_AREA_COUNT = 10;

int n;
int populationList[MAX_AREA_COUNT];
vector<int> adjList[MAX_AREA_COUNT];
int teamList[MAX_AREA_COUNT];
bool isVisited[MAX_AREA_COUNT];

pair<int, int> bfs(int st);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int i = 0; i < n; ++i)
	{
		cin >> populationList[i];
	}

	for (int i = 0; i < n; ++i)
	{
		int cnt = 0;
		cin >> cnt;

		for (int j = 0; j < cnt; ++j)
		{
			int num = 0;
			cin >> num;
			adjList[i].push_back(num - 1); // 0-based index
		}
	}

	int answer = INF;
	for (int brute = 1; brute < (1 << n) - 1; ++brute)
	{
		int st1 = 0, st2 = 0;
		for (int i = 0; i < n; ++i)
		{
			if (brute & (1 << i))
			{
				teamList[i] = 1;
				st1 = i;
			}
			else
			{
				teamList[i] = 0;
				st2 = i;
			}
		}

		pair<int, int> ret1 = bfs(st1);
		pair<int, int> ret2 = bfs(st2);
		if (ret1.first + ret2.first == n)
		{
			answer = min(answer, abs(ret1.second - ret2.second));
		}

		memset(isVisited, false, sizeof(isVisited));
	}

	cout << ((answer == INF) ? -1 : answer) << '\n';
}

pair<int, int> bfs(int st)
{
	pair<int, int> ret = {}; // <방문한 노드의 개수, 방문한 노드의 인구수 합>
	queue<int> q;

	isVisited[st] = true;
	q.push(st);

	while (!q.empty())
	{
		int cur = q.front();
		q.pop();

		++ret.first;
		ret.second += populationList[cur];

		for (int nxt : adjList[cur])
		{
			if ((isVisited[nxt]) || (teamList[nxt] != teamList[st]))
			{
				continue;
			}

			isVisited[nxt] = true;
			q.push(nxt);
		}
	}

	return ret;
}
