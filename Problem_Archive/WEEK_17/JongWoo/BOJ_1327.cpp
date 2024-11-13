/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- i번째 위치에서 k개를 뒤집어 새로운 배열을 만들며 오름차순이 될 때까지 진행할 때 1번의 횟수씩 증가하므로 BFS를 사용하여
	  최초로 오름차순이 된 경우 답이 된다고 생각하였다.
	  방문 체크의 경우에는 string을 key 값으로 하는 해시맵을 사용하였다.

시간 복잡도
	- 시작 상태에서 출발해 매번 n - k + 1번 원소 k개를 뒤집은 배열을 만들며 BFS 탐색을 진행하므로 O(N^2)의 시간 복잡도를 갖는다.

실행 시간
	- 40ms(C++)
*/

#include <iostream>
#include <string>
#include <queue>
#include <unordered_map>
#include <algorithm>

using namespace std;

int k, n, answer;
unordered_map<string, int> minCnt;

int BFS(const string& st, const string& target);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> k;

	string st(n, '0');
	string target(n, '0');

	for (int i = 0; i < n; ++i)
	{
		int num = 0;

		cin >> num;
		st[i] = num + '0';
		target[i] = i + 1 + '0';
	}

	cout << BFS(st, target) << '\n';
}

int BFS(const string& st, const string& target)
{
	queue<pair<string, int>> q;

	minCnt[st] = 0;
	q.emplace(st, minCnt[st]);

	while (!q.empty())
	{
		pair<string, int> cur = q.front();

		q.pop();

		if (cur.first == target)
		{
			return cur.second;
		}

		for (int i = 0; i <= n - k; ++i)
		{
			string reversed = cur.first.substr(i, k);

			reverse(reversed.begin(), reversed.end());
			
			string nxt = cur.first.substr(0, i) + reversed + cur.first.substr(i + k);

			if ((minCnt.find(nxt) == minCnt.end()) || (minCnt[nxt] > cur.second + 1))
			{
				minCnt[nxt] = cur.second + 1;
				q.emplace(nxt, minCnt[nxt]);
			}
		}
	}

	return -1;
}
