/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽고나서, 뭔가 최대 공약수와 최소 공배수를 사용해서 수학적으로 풀어야 하는 문제라는 생각이 들었다.
	  그래서 꽤 많은 시간을 들여 고민했는데도 진전이 없어 알고리즘 분류를 봤는데 그래프 탐색이라는 키워드가 있었다.
	  전혀 생각지도 못한 키워드가 있었고 이를 알고도 어떻게 접근해야 할 지 감이 오질 않아서 결국 다른 사람의 풀이를 참고하여 문제를 해결할 수 있었다.
	  주어진 입력 값을 인접리스트로 표현하여, a b p q가 주어지면 a를 기준으로 Edge(b, p, q), b를 기준으로 Edge(a, q, p)와 같은 간선이 만들어지는 것이다.
	  그래프를 표현함과 동시에 p와 q의 최소 공배수를 구하고, 모든 간선의 최소 공배수를 곱해 공통 공배수를 계산해준다.
	  이제 임의로 0번 정점의 값을 공통 공배수 값으로 설정한 후, DFS 탐색을 수행하며 인접 정점에 대한 값을 계산한다, 이때는 인접 정점이 기준이 되므로 dp[cur] * q / p와 같이 계산해야 한다.
	  탐색을 모두 수행한 다음에는 합이 최소가 되는 경우를 구해야 하므로, 이 값들의 최대 공약수를 구한다. 이때 총 질량은 0보다 커야하므로 값이 0인 정점은 건너 뛰어야한다.
	  최대 공약수를 구했다면, 각 정점의 값을 최대 공약수로 나눈 값을 출력하면 답을 도출할 수 있다.

시간 복잡도
	- DFS 탐색을 통해 N개의 정점을 순회하는데 N, N개의 정점을 순회하며 최대 공약수를 계산하는데 NlogN의 시간이 걸린다.
	  따라서 O(NlogN)의 시간복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_INGREDIENT_COUNT = 11;

struct Edge
{
	int to;
	int p;
	int q;
};

int n;
vector<Edge> adjList[MAX_INGREDIENT_COUNT];
long long dp[MAX_INGREDIENT_COUNT];

long long getGCD(long long a, long long b);
long long getLCM(long long a, long long b);
void dfs(int cur, int pre);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	cin >> n;

	// 전체 재료의 최소공배수
	long long lcm = 1;
	for (int i = 0; i < n - 1; ++i)
	{
		int from = 0, to = 0, p = 0, q = 0;
		cin >> from >> to >> p >> q;
		adjList[from].push_back(Edge{ to, p, q });
		adjList[to].push_back(Edge{ from, q, p });
		lcm *= getLCM(p, q);
	}

	// 임의로 0번 정점에서 시작
	int st = 0;
	dp[st] = lcm;
	dfs(st, -1);

	// 질량을 모두 더한 값이 최소가 되어야 하므로 최대 공약수를 구해 나눠준다.
	// 이때, 총 질량은 0보다 커야하므로 dp[i]의 값이 0일 때는 건너 뛴다.
	long long gcd = dp[st];
	for (int i = 1; i < n; ++i)
	{
		if (dp[i] == 0)
		{
			continue;
		}

		gcd = getGCD(gcd, dp[i]);
	}

	for (int i = 0; i < n; ++i)
	{
		cout << dp[i] / gcd << ' ';
	}
	cout << '\n';
}

long long getGCD(long long a, long long b)
{
	while (b > 0)
	{
		long long tmp = a % b;
		a = b;
		b = tmp;
	}

	return a;
}

long long getLCM(long long a, long long b)
{
	return a * b / getGCD(a, b);
}

void dfs(int cur, int pre)
{
	for (const Edge& edge : adjList[cur])
	{
		if (edge.to == pre)
		{
			continue;
		}

		dp[edge.to] = dp[cur] * edge.q / edge.p;
		dfs(edge.to, cur);
	}
}
