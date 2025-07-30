/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에서 요구하는 것은 간단하지만, 좌표가 100,000 x 100,000이고, 최악의 경우에 유저의 수가 10만명이기 때문에 완전탐색을 절대 아니겠다는 생각이 들었다.
	  아무리 가지치기를 잘해도 도저히 감당이 안되는 크기이기 때문에 다른 방법을 고민하다가 각 유저의 상, 하, 좌, 우, 원 위치까지의 거리를 구하고,
	  다음 유저에서는 이전 유저까지의 거리를 누적합처럼 계산해가면 되지 않을까? 하는 생각이 들어서 DP 알고리즘을 떠올릴 수 있었다.
	  매번 다음 유저의 상, 하, 좌, 우, 원 위치와 이전 유저의 상, 하, 좌, 우, 원 위치의 거리를 비교하면서 최솟값을 택하도록 설정했다.
	  즉, dp[i][j]는 i번째 유저의 위치에서 DELTA[j] 만큼 떨어진 위치까지의 거리를 뜻한다.
	  위 과정을 거치면 마지막 유저까지의 거리 dp[n - 1][j] 중 최솟값을 택하면 된다.

시간 복잡도
	- 유저의 수 N만큼 매번 5개의 방향와 이전 방향을 따져보므로 O(N * 5 * 5), O(N)의 시간 복잡도를 갖는다.

실행 시간
	- 24ms
*/

#include <iostream>

using namespace std;

const long long INF = 1e10 + 1;
const int MAX_USER_COUNT = 100'000;
const int MAX_BOARD_SIZE = 100'000;
const int DELTA_COUNT = 5;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 }, { 0, 0 } };

int n, sr, sc;
long long dp[MAX_USER_COUNT][DELTA_COUNT];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;
	cin >> sr >> sc;

	for (int i = 0; i < n; ++i)
	{
		for (int j = 0; j < DELTA_COUNT; ++j)
		{
			dp[i][j] = INF;
		}
	}

	int ur = 0, uc = 0;
	cin >> ur >> uc;

	for (int dir = 0; dir < DELTA_COUNT; ++dir)
	{
		int nur = ur + DELTA[dir][0];
		int nuc = uc + DELTA[dir][1];

		if ((nur < 0) || (nur > MAX_BOARD_SIZE) || (nuc < 0) || (nuc > MAX_BOARD_SIZE))
		{
			continue;
		}

		dp[0][dir] = abs(nur - sr) + abs(nuc - sc);
	}

	int pur = ur, puc = uc;

	for (int i = 1; i < n; ++i)
	{
		cin >> ur >> uc;

		for (int curDir = 0; curDir < DELTA_COUNT; ++curDir)
		{
			int nur = ur + DELTA[curDir][0];
			int nuc = uc + DELTA[curDir][1];

			if ((nur < 0) || (nur > MAX_BOARD_SIZE) || (nuc < 0) || (nuc > MAX_BOARD_SIZE))
			{
				continue;
			}

			for (int prevDir = 0; prevDir < DELTA_COUNT; ++prevDir)
			{
				if (dp[i - 1][prevDir] == INF)
				{
					continue;
				}

				int npur = pur + DELTA[prevDir][0];
				int npuc = puc + DELTA[prevDir][1];
				int dist = abs(nur - npur) + abs(nuc - npuc);

				dp[i][curDir] = min(dp[i][curDir], dp[i - 1][prevDir] + dist);
			}
		}

		pur = ur;
		puc = uc;
	}

	long long answer = INF;

	for (int i = 0; i < DELTA_COUNT; ++i)
	{
		answer = min(answer, dp[n - 1][i]);
	}

	cout << answer << '\n';
}
