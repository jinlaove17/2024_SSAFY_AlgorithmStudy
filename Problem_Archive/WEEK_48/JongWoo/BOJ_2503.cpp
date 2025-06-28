/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 일반적인 야구 게임 룰을 적용했을 때, q개의 쿼리가 주어지고 스트라이크와 볼의 결과를 종합해 가능한 수를 구하는 문제이다.
	  세자리 밖에 되지 않기 때문에 수학적으로 고민할 것 없이 111부터 999까지, 좀더 최적화 한다면 중복을 배제하여 123부터 987까지 순회하며
	  각 값을 정답으로 가정했을 때, q개의 타겟 넘버의 결과를 모두 만족하는 수를 카운트 해주면 답을 도출할 수 있다. 

시간 복잡도
	- 111부터 999까지 889개의 숫자를 순회하며 q개의 경우를 살펴봐야 하므로 889 * N의 시간이 걸린다.
	  따라서 O(N)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>

using namespace std;

const int MAX_QUERY_COUNT = 100;

int q;
int targets[MAX_QUERY_COUNT];
int strikes[MAX_QUERY_COUNT];
int balls[MAX_QUERY_COUNT];

bool simulate(int a, int b, int c);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> q;

	for (int i = 0; i < q; ++i)
	{
		cin >> targets[i] >> strikes[i] >> balls[i];
	}

	int answer = 0;

	for (int a = 1; a <= 9; ++a)
	{
		for (int b = 1; b <= 9; ++b)
		{
			for (int c = 1; c <= 9; ++c)
			{
				if ((a == b) || (b == c) || (c == a))
				{
					continue;
				}

				if (simulate(a, b, c))
				{
					++answer;
				}
			}
		}
	}

	cout << answer << '\n';
}

bool simulate(int a, int b, int c)
{
	for (int i = 0; i < q; ++i)
	{
		int strike = 0, ball = 0;

		int first = targets[i] / 100;
		if (first == a)
		{
			if (++strike > strikes[i])
			{
				return false;
			}
		}
		else if ((first == b) || (first == c))
		{
			if (++ball > balls[i])
			{
				return false;
			}
		}

		int second = targets[i] / 10 % 10;
		if (second == b)
		{
			if (++strike > strikes[i])
			{
				return false;
			}
		}
		else if ((second == c) || (second == a))
		{
			if (++ball > balls[i])
			{
				return false;
			}
		}

		int third = targets[i] % 10;
		if (third == c)
		{
			if (++strike > strikes[i])
			{
				return false;
			}
		}
		else if ((third == a) || (third == b))
		{
			if (++ball > balls[i])
			{
				return false;
			}
		}

		if ((strike != strikes[i]) || (ball != balls[i]))
		{
			return false;
		}
	}

	return true;
}
