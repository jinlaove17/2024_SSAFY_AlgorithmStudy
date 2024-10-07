/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 추의 개수가 최대 30개이므로 부분 집합으로 모든 경우를 따져보며 합이 구슬의 무게와 수평을 이루는 경우를 찾는것은 2^30의 시간 복잡도를 갖기 때문에 불가능하다고 생각하였다.
	  아무리 가지치기를 잘해도 10억에 도전하는 완전 탐색은 아닐 것이라는 생각이 들었지만 혹시 모른다는 미련을 남기고 DP, 이분 탐색 등의 알고리즘을 떠올리게 되었다.
	- 맨 처음에는 2차원 DP로 dp[3][1]: 1개의 수를 사용하여 3을 만드는 경우의 수, 즉 dp[i][j]를 j개의 수를 사용하여 i를 만드는 경우의 수로 정의할려고 했는데,
	  정작 구슬의 무게를 알아내는데는 크게 쓸모가 없었다.
	- 그래서 배낭 문제와 비슷하게 열을 추의 무게합(최대 30 * 500 = 15,000), 행을 추의 인덱스로 하여 n * totalWeight 크기의 2차원 배열을 선언하였다.
	- 그림판에 직접 그려보면서 행이 증가할 때마다 1번부터 i번까지의 추를 이용해서 무게 j를 만드는 경우의 수를 계산하고 점화식을 찾을 수 있었다.
	- 최종적으로 n개의 추를 사용하여 무게 w를 만들 때, 구슬이 있는 쪽과 다른 한쪽의 dp 값이 다를 경우 0보다, 같을 경우 1보다 크면 가능한 경우로 판단하여 답을 도출할 수 있었다.

시간 복잡도
	- n * totalWeight 크기의 2차원 배열을 순회하며 dp 테이블을 채우고 구슬의 개수 m * (구슬의 무게 beads[i]부터 totalWeight)번 확인을하므로
	  최종적인 시간 복잡도는 O(N^2)이다.

실행 시간
	- 0ms(C++)
*/

#include <iostream>

using namespace std;

const int MAX_WEIGHT_COUNT = 30;
const int MAX_WEIGHT = 500;
const int MAX_BEAD_COUNT = 7;
const int MAX_BEAD_WEIGHT = 40'000;

int n, m; // n: 추의 개수(30이하), m: 무게를 확인하고자 하는 구슬의 개수(7이하)
int weights[MAX_WEIGHT_COUNT + 1];
int beads[MAX_BEAD_COUNT + 1];
int dp[MAX_WEIGHT_COUNT + 1][MAX_WEIGHT * MAX_WEIGHT_COUNT + 1];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	int totalWeight = 0;

	for (int i = 1; i <= n; ++i)
	{
		cin >> weights[i];
		totalWeight += weights[i];
	}

	int sum = 0;

	for (int i = 1; i <= n; ++i)
	{
		sum += weights[i];

		for (int j = 1; j <= sum; ++j)
		{
			dp[i][j] = dp[i - 1][j];

			if (j == weights[i])
			{
				++dp[i][j];
			}
			else if (j > weights[i])
			{
				dp[i][j] += dp[i - 1][j - weights[i]];
			}
		}
	}

	cin >> m;

	for (int i = 0; i < m; ++i)
	{
		cin >> beads[i];

		if (beads[i] == totalWeight)
		{
			cout << "Y ";
		}
		else if (beads[i] > totalWeight)
		{
			cout << "N ";
		}
		else
		{
			bool isPossible = false;

			for (int w = beads[i]; w <= totalWeight; ++w)
			{
				if (w - beads[i] == w)
				{
					if (dp[n][w] >= 2)
					{
						isPossible = true;
						break;
					}
				}
				else
				{
					if ((dp[n][w - beads[i]] > 0) && (dp[n][w] > 0))
					{
						isPossible = true;
						break;
					}
				}
			}

			cout << ((isPossible) ? "Y " : "N ");
		}
	}
}
