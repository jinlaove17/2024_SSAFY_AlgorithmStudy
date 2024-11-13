/*
※ 이 코드는 테스트 케이스만 맞는 틀린 코드입니다.
아래와 같은 반례에 대응 가능하도록 수정 혹은 폐기가 요구됩니다.
5
1 2 3 4 5
15

문제 접근 아이디어 및 알고리즘 판단 사유
  - 방의 개수 n과 금액 m이 최대 50으로 크지 않고, 2초가 주어지는 문제여서 완전 탐색으로 접근하려 했으나 실패했다.
    뭔가 동전으로 금액을 맞추는 dp 문제들과 비슷하다는 생각이 들어 dp로 접근해봤는데 값이 굉장히 큰 경우가 존재해서 자료형을 string으로 선언하였다.
    하지만 테스트 케이스만 맞고, 위 반례에 대응할 방법이 보이지 않아 이 역시도 실패한 듯하다.
*/

#include <iostream>
#include <string>

using namespace std;

const int MAX_SIZE = 10;
const int MAX_MONEY = 50;

int n, m;
int prices[MAX_SIZE];
string dp[MAX_MONEY + 1]; // dp[i]: i원을 사용하여 만들 수 있는 가장 큰 방 번호

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int i = 0; i < n; ++i)
	{
		cin >> prices[i];
		dp[prices[i]] = to_string(i);
	}

	cin >> m;

	for (int curMoney = 1; curMoney <= m; ++curMoney)
	{
		for (int i = 0; i < n; ++i)
		{
			if (curMoney >= prices[i])
			{
				if (dp[curMoney].empty())
				{
					dp[curMoney] = to_string(i);
				}
				else
				{
					string tmp;

					if (dp[curMoney - prices[i]].empty())
					{
						tmp = to_string(i);
					}
					else
					{
						if (i + '0' > dp[curMoney - prices[i]].front())
						{
							tmp = static_cast<char>(i + '0') + dp[curMoney - prices[i]];
						}
						else
						{
							tmp = dp[curMoney - prices[i]] + static_cast<char>(i + '0');
						}
					}

					if (tmp.length() > dp[curMoney].length())
					{
						dp[curMoney] = tmp;
					}
					else if (tmp.length() == dp[curMoney].length())
					{
						bool flag = false;

						for (int i = 0; i < tmp.length(); ++i)
						{
							if (tmp[i] > dp[curMoney][i])
							{
								flag = true;
								break;
							}
						}

						if (flag)
						{
							dp[curMoney] = move(tmp);
						}
					}
				}
			}
		}
	}

	cout << dp[m] << '\n';
}
