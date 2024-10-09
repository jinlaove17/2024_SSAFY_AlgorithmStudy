/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에는 중복 조합을 사용하고, 이미 답을 구한 경우 비용이 답보다 커지는 경우들에 대한 가지치기를 진행하여 코드를 작성했었는데, 시간 초과가 났다.
	  아무래도 최악의 경우 n개 중에 n개를 뽑는 경우가 생기기 때문에 시간 복잡도가 O(30^30)에 가깝고, 가지치기를 하더라도 불가능하다고 판단하고 DP 알고리즘을 떠올리게 되었다.

시간 복잡도
	- 최악의 경우 홍보 비용이 100이고, 고객의 수가 1인 도시가 n개 존재할 때, c가 1,000이면 외부 for문은 100,000번, 내부 for문은 30번 순회하므로 O(MAX_COST * N)의 시간 복잡도를 갖는다.

실행 시간
	- 4ms(C++) -> 0ms(C++)

개선 방법
	- 처음에 DP 코드를 작성했을 때는 답을 도출하기 위해 모든 DP 값이 설정된 후 for 문을 돌며 최초로 dp[i]의 값이 c 이상이 되는 경우 break를 했는데
	  이 부분을 DP 값이 설정될 때마다 검사해주면, 뒷부분의 DP 값은 구할 필요가 없으므로 최적화가 된다.
*/

#include <iostream>

using namespace std;

const int MAX_CITY_COUNT = 20;
const int MAX_COST = 100 * 1000; // 홍보 비용이 100이고, 고객의 수가 1인 도시 1개만 존재할 때, c가 1,000이면 100 * 1,000의 비용이 든다.

int c, n;
pair<int, int> cityInfo[MAX_CITY_COUNT]; // <홍보 비용, 고객의 수>

int dp[MAX_COST + 1]; // dp[i]: i원으로 얻을 수 있는 최대 고객의 수

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> c >> n;

	for (int i = 0; i < n; ++i)
	{
		cin >> cityInfo[i].first >> cityInfo[i].second;
	}

	for (int i = 1; i <= MAX_COST; ++i)
	{
		for (int j = 0; j < n; ++j)
		{
			if (i >= cityInfo[j].first)
			{
				dp[i] = max(dp[i], dp[i - cityInfo[j].first] + cityInfo[j].second);
			}
		}

		if (dp[i] >= c)
		{
			cout << i << '\n';
			break;
		}
	}
}
