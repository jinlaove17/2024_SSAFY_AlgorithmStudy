/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 디지털 숫자로 표현할 때, 7개의 칸이 사용되는데 이를 비트 마스킹을 사용해서 표현하면 좋을 것 같다는 생각이 들었다.
	  현재 숫자와 바꿀 숫자의 비트를 XOR(^) 연산을 수행하면 다른 비트열을 구할 수 있고, 이를 이용해 변경해야 하는 횟수를 사전에 계산하도록 하였다.
	  이후 자릿수만큼 깊이 우선 탐색으로 완전 탐색을 수행하여 답을 도출할 수 있었다.

시간 복잡도
	- 매 깊이 우선 탐색에서 0부터 9까지의 살펴보며 현재 위치의 숫자를 바꾸려고 시도하기 때문에 최악의 경우, 6자리일 때 O(10^6)의 시간이 걸린다.

실행 시간
	- 40ms
*/

#include <iostream>
#include <string>

using namespace std;

int n, k, p, answer;
string x, tmp;
int numberBits[] = {
	0b1110111,
	0b0100100,
	0b1011101,
	0b1101101,
	0b0101110,
	0b1101011,
	0b1111011,
	0b0100101,
	0b1111111,
	0b1101111
};
int transCount[10][10]; // transCount[i][j]: i에서 j로 변환하는데 필요한 개수

void init();
void dfs(int depth, int cnt);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> k >> p >> x;
	init();
	dfs(0, 0);
	cout << answer - 1 << '\n'; // - 1: 하나도 바꾸지 않는 경우
}

void init()
{
	if (x.length() < k)
	{
		x = string(k - x.length(), '0') + x;
	}

	tmp = x;

	for (int i = 0; i < 9; ++i)
	{
		for (int j = i + 1; j < 10; ++j)
		{
			int diff = numberBits[i] ^ numberBits[j];
			int used = 0;
			while (diff > 0)
			{
				if (diff & 1)
				{
					++used;
				}

				diff >>= 1;
			}

			transCount[i][j] = used;
			transCount[j][i] = used;
		}
	}
}

void dfs(int depth, int cnt)
{
	if ((depth >= k) || (cnt >= p))
	{
		int cur = stoi(tmp);
		if ((1 <= cur) && (cur <= n))
		{
			++answer;
		}

		return;
	}

	int cur = x[depth] - '0';

	for (int i = 0; i < 10; ++i)
	{
		if (cnt + transCount[cur][i] > p)
		{
			continue;
		}

		tmp[depth] = i + '0';
		dfs(depth + 1, cnt + transCount[cur][i]);
		tmp[depth] = x[depth];
	}
}
