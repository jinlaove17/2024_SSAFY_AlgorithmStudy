/*
문제 접근 아이디어 및 알고리즘 판단 사유
  - N이 50이고, 최악의 경우 매 번 +, - 분기가 모두 가능하게 되어 2^N의 시간이 소요되기 때문에 가지치기를 하더라도 완전 탐색은 불가능하다고 생각하였다.
    그래서 DP를 떠올리게 되었고 처음에는 dp 테이블을 dp[50][2]으로 설정하여 dp[i][j]를 1번 곡부터 시작해 i번 곡을 +(0), -(1)로 연주했을 때의 최댓값을
    저장하도록 설정했는데, 항상 최대가 되는 경우만 따라가다보면 완곡이 가능함에도 범위 m을 벗어나 -1을 출력하는 문제에 맞닥뜨렸다.
    따라서 dp 테이블의 정보가 부족함을 느끼고 dp[i][j]를 1번부터 i번 곡까지 연주했을 때 j만큼의 볼륨으로 연주가 가능한지를 확인하는 용도로 사용하였다.
    위 과정을 거쳐 m부터 시작해 0까지 순회하며 dp[N][i]가 참일 때의 i 값을 출력하면 답을 도출할 수 있다.
    또한 주의할 점은 답이 0이 될 수도 있기 때문에 초기 값을 0이 아닌 적당히 큰 수로 설정해주어야 한다.
  
시간 복잡도
  - 최악의 경우에는 곡의 개수 N이 50이고 가용 볼륨 M이 1,000이 된다. 그렇게 크지 않은 값이기 때문에 O(N^2)의 시간으로 문제를 해결할 수 있었다.
  
실행 시간
  - 0ms(C++)
*/

#include <iostream>

using namespace std;

const int INF = 987'654'321;
const int MAX_SONG_COUNT = 50;
const int MAX_VOLUME = 1'000;

int n, s, m;
int volumes[MAX_SONG_COUNT + 1];
int dp[MAX_SONG_COUNT + 1][MAX_VOLUME + 1]; // dp[i][j]: 시작 볼륨부터 시작해 i번째 곡을 볼륨 j로 칠 수 있는지

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> s >> m;
	
	for (int i = 1; i <= n; ++i)
	{
		cin >> volumes[i];
	}

	if (s + volumes[1] <= m)
	{
		dp[1][s + volumes[1]] = 1;
	}

	if (s - volumes[1] >= 0)
	{
		dp[1][s - volumes[1]] = 1;
	}

	for (int i = 2; i <= n; ++i)
	{
		for (int j = 0; j <= m; ++j)
		{
			if (dp[i - 1][j] == 0)
			{
				continue;
			}

			if (j + volumes[i] <= m)
			{
				dp[i][j + volumes[i]] = 1;
			}

			if (j - volumes[i] >= 0)
			{
				dp[i][j - volumes[i]] = 1;
			}
		}
	}
	
	int answer = INF;

	for (int i = m; i >= 0; --i)
	{
		if (dp[n][i] == 1)
		{
			answer = i;
			break;
		}
	}

	cout << ((answer == INF) ? -1 : answer) << '\n';
}
