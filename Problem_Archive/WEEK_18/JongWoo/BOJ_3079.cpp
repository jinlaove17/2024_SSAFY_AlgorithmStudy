/*
문제 접근 아이디어 및 알고리즘 판단 사유
  - 값이 굉장히 크기 때문에 이분탐색 느낌을 받았다.
    최악의 경우 10억명의 사람이 10억초가 걸리는 1개의 심사대에서 모두 심사를 받는 경우이므로 10억 * 10억의 시간이 소요될 것이다.
    따라서 이 값을 en 값으로 설정하고 걸리는 시간을 기준으로 이분탐색을 진행하며 시간을 줄여나가면 답을 도출할 수 있다.
  
시간 복잡도
  - 이분탐색이 한 번 수행될 때마다 절반씩 줄어나가므로 O(logN)의 시간 복잡도를 갖는다.
  
실행 시간
  - 44ms(C++)
*/

#include <iostream>

using namespace std;

const int MAX_TABLE_COUNT = 100'000;

int n, m;
int durations[MAX_TABLE_COUNT + 1];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int i = 0; i < n; ++i)
	{
		cin >> durations[i];
	}

	long long st = 1, en = 1e9 * 1e9;
	long long answer = en;

	while (st <= en)
	{
		long long md = (st + en) / 2;
		long long cnt = 0;

		for (int i = 0; i < n; ++i)
		{
			cnt += md / durations[i];

			if (cnt >= m)
			{
				break;
			}
		}

		if (cnt >= m)
		{
			en = md - 1;
			answer = md;
		}
		else
		{
			st = md + 1;
		}
	}

	cout << answer << '\n';
}
