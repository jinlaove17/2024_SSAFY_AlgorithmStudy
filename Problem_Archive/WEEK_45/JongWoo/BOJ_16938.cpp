/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- n이 최대 15로 크지 않은 값이기 때문에 비트마스킹을 통해 표현이 가능하며, 각 비트를 문제의 인덱스로 설정하여
	  비트가 1인 문제들을 선별해 조건을 따져보면서 답을 도출할 수 있었다.
	  
시간 복잡도
	- n이 최대 15이므로 비트마스킹으로 각 문제를 완전 탐색하므로 2^n번의 반복문이 수행되고, 선택된 문제를 판별하기 위해 n만큼 반복이 수행된다.
	  최종적으로 O(N * 2^N)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>

using namespace std;

const int MAX_PROBLEM_COUNT = 15;

int n, l, r, x;
int arr[MAX_PROBLEM_COUNT];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> l >> r >> x;

	for (int i = 0; i < n; ++i)
	{
		cin >> arr[i];
	}

	int answer = 0;

	for (int i = 3; i < (1 << n); ++i)
	{
		int sum = 0, cnt = 0;
		int maxLevel = 0, minLevel = 1e6 + 1;

		for (int j = 0; j < n; ++j)
		{
			if (i & (1 << j))
			{
				sum += arr[j];
				maxLevel = max(maxLevel, arr[j]);
				minLevel = min(minLevel, arr[j]);
				++cnt;
			}
		}

		if ((cnt >= 2) && (l <= sum) && (sum <= r) && (maxLevel - minLevel >= x))
		{
			++answer;
		}
	}

	cout << answer << '\n';
}
