/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽고 직접 그림을 그리며 아이디어를 떠올리게 되었다. 우선, 이름(문자열)로 주어지는 것을
	  그래프의 노드로 표현하기 위해, 문자열<->정수로 변환하는 해시맵 2개를 각각 선언하였다.
	  그 다음에는 그래프를 인접 리스트 방식으로 표현하는데 x, y와 같이 주어지면 y에서 x로 연결하였다.
	  위와 같이 그래프가 만들어지면 진입 차수가 0인 노드가 나오기 때문에 루트 노드를 쉽게 판별할 수 있고,
	  위상절렬을 수행해 인접한 노드의 진입 차수가 0이 될 때마다 큐에 추가하면서 해당 노드의 인덱스를 저장해 자식을 구분했다.
	  위 과정을 거치면 문제에서 원하는 답을 도출할 수 있다.

시간 복잡도
	- 초기 이름을 정렬하는데 O(nlogn)의 시간이 걸리며, 위상 정렬을 통해 n개의 노드를 순회하는데 O(n)의 시간이 걸린다.
	  마지막으로 각 노드에 대해 자식 노드를 사전순으로 출력해야 하므로 n개의 노드를 정렬하는데 O(n^2logn)의 시간이 걸린다.
	  최종적으로 O(n^2logn)의 시간이 걸린다.

실행 시간
	- 116ms
*/

#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <unordered_map>
#include <algorithm>

using namespace std;

const int MAX_COUNT = 1'000;

int n, m;
string nameList[MAX_COUNT];
unordered_map<string, int> s2i;
unordered_map<int, string> i2s;
vector<int> adjList[MAX_COUNT];
vector<int> children[MAX_COUNT];
int inDegrees[MAX_COUNT];

void topologySort();

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;
	for (int i = 0; i < n; ++i)
	{
		cin >> nameList[i];
	}

	sort(nameList, nameList + n);
	for (int i = 0; i < n; ++i)
	{
		s2i[nameList[i]] = i;
		i2s[i] = nameList[i];
	}

	cin >> m;
	for (int i = 0; i < m; ++i)
	{
		string x, y;
		cin >> x >> y;
		adjList[s2i[y]].push_back(s2i[x]);
		++inDegrees[s2i[x]];
	}

	topologySort();
}

void topologySort()
{
	queue<int> q;
	vector<string> rootNames;
	for (int i = 0; i < n; ++i)
	{
		if (inDegrees[i] == 0)
		{
			rootNames.push_back(i2s[i]);
			q.push(i);
		}
	}

	cout << rootNames.size() << '\n';
	for (const string& r : rootNames)
	{
		cout << r << ' ';
	}
	cout << '\n';

	while (!q.empty())
	{
		int cur = q.front();
		q.pop();

		for (int nxt : adjList[cur])
		{
			if (--inDegrees[nxt] == 0)
			{
				q.push(nxt);
				children[cur].push_back(nxt);
			}
		}
	}

	for (int i = 0; i < n; ++i)
	{
		sort(children[i].begin(), children[i].end());

		cout << i2s[i] << ' ' << children[i].size() << ' ';
		for (int c : children[i])
		{
			cout << i2s[c] << ' ';
		}
		cout << '\n';
	}
}
