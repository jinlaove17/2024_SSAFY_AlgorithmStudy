/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽으면 DP 냄새가 나는데, 점화식이 잘 떠오르지 않아 어떻게 할지 고민하다 '연속적으로 먹는다'는 것에 집중하다보니 투 포인터를 떠올릴 수 있었다.
    그러나 투 포인터만으로는 뭔가 정보가 부족했고, 값을 계속해서 메모이제이션 하면서 해야되지 않을까 하는 생각에 DP도 조금 곁들였다.
    누적합이 k를 넘지 않을 때까지 en 포인터를 움직이고 st 이전까지 구한 최댓값에 현재 구간의 만족도를 더한 값을 구하며 값을 갱신해 나가면 답을 도출할 수 있다.

시간 복잡도
	- 길이가 N인 배열을 최대 2번 순회하므로 2 * N의 시간이 걸린다. 따라서 O(N)의 시간 복잡도를 갖는다.

실행 시간
	- 12ms
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_FEED_COUNT = 100'000;

int n, k;
int satisfactions[MAX_FEED_COUNT + 2];
long long dp[MAX_FEED_COUNT + 2]; // dp[i]: i번까지 이동했을 때 축적된 최대 탈피 에너지

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> k;
	for (int i = 1; i <= n; ++i)
	{
		cin >> satisfactions[i];
	}

	long long sum = 0;
	long long maxSatisfaction = 0;
	for (int st = 1, en = 0; st <= n; ++st)
	{
		while ((en + 1 <= n) && (sum < k))
		{
			sum += satisfactions[++en];
		}
		
		dp[en] = max(dp[en], maxSatisfaction + max(0LL, sum - k));
		maxSatisfaction = max(maxSatisfaction, dp[st]);
		sum -= satisfactions[st];
	}

	cout << maxSatisfaction << '\n';
}
