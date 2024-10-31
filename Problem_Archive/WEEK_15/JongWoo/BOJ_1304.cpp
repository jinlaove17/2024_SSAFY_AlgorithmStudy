/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 각 그룹의 도시의 수를 균등하게 나누기 위해서는 N의 약수의 수로 나누어야 된다고 생각해서 N의 약수를 계산하였다.
	  입력으로 주어지는 간선의 경우에는 번호가 큰 곳에서 작은 곳으로 가는 간선만 의미가 있는 간선이라고 생각하였는데,
	  그 이유는 모든 도시는 고속도로로 연결이 되어있기 때문에 도시 간의 연결을 A에 속한 도시에서 B로 속한 도시로만 이동이 가능한 단방향으로 설정하기 위해서
	  같은 그룹에 속한 도시들은 연속적으로 배치되어야 하기 때문이다.
	  따라서 번호가 작은 곳에서 큰 곳으로 이동하는 간선은 고려할 필요 없이 약수의 값만큼 도시들을 그룹으로 묶어서 간선에 연결된 도시가 같은 그룹인지를 판단하면 된다.
	  위 과정을 거쳤다면 n을 그룹 내 도시의 수로 나눈 값이 최종적인 답이 된다.

시간 복잡도
	- N의 약수의 개수가 K개 일 때, 매번 N + M의 확인 작업을 거치므로 K * (N + M)의 연산이 수행된다.
	  따라서 O(N + M)의 시간 복잡도를 갖는다고 생각하였다.

실행 시간
	- 0ms(C++)

삽질했던 내용
	- 'A에 속해 있는 어떤 도시에서 B에 속해 있는 어떤 도시로 가는 경로가 있다면,
	   B에 속해 있는 어떤 도시에서 A에 속해 있는 어떤 도시로 가는 경로는 없어야 한다.'
	   위 조건을 다른 그룹에 속해 있는 도시를 거치지 않고 직접적으로 가는 경우로 이해를 했는데
	   A에 속해 있는 어떤 도시에서 B가 아닌 다른 그룹에 속해 있는 도시를 거쳐 B에 속해 있는 도시에 도달하는 경우도 도달이 가능한 경우로 처리해야
	   답을 구할 수 있기 때문에 난항을 겪었다.
*/

#include <iostream>
#include <vector>
#include <cstring>

using namespace std;

const int MAX_CITY_COUNT = 3'000;

int n, m;
int group[MAX_CITY_COUNT + 1];
vector<int> adjList[MAX_CITY_COUNT + 1];
vector<int> divisors;

bool Check(int offset);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int i = 0; i < m; ++i)
	{
		int from = 0, to = 0;

		cin >> from >> to;

		if (from > to)
		{
			adjList[from].push_back(to);
		}
	}

	for (int i = 1; i < n; ++i)
	{
		if (n % i == 0)
		{
			divisors.push_back(i);
		}
	}

	int answer = 1;

	for (int div : divisors)
	{
		if (Check(div))
		{
			answer = n / div;
			break;
		}

		memset(group, 0, sizeof(group));
	}

	cout << answer << '\n';
}

bool Check(int offset)
{
	int groupIndex = 1;

	for (int st = 1; st <= n; st += offset)
	{
		for (int cur = st, en = st + offset; cur < en; ++cur)
		{
			group[cur] = groupIndex;

			for (int nxt : adjList[cur])
			{
				if (group[nxt] != group[st])
				{
					return false;
				}
			}
		}

		++groupIndex;
	}

	return true;
}
