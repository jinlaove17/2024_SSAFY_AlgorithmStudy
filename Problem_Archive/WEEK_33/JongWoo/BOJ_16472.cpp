/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽어보면 전형적인 투 포인터 문제라는 감이 올 것이다.
	  두 개의 포인터 사이에 포함된 알파벳의 빈도수를 저장하고, 몇개의 알파벳이 포함됐는지를 계산한 후 포인터를 옮겨주면서 값을 계산해주면 문제에서 원하는 답을 도출할 수 있다.

시간 복잡도
	- 문자열의 길이 n만큼 반복을 수행하면서, 두 개의 포인터로 조건을 따져봐야 하므로, 2 * n의 시간이 걸린다.
	  따라서 최종적인 시간 복잡도는 O(N)이 된다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <string>

using namespace std;

int n;
string str;

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;
	cin >> str;

	int answer = 0;
	int freq[26] = {};
	int cnt = 0;
	int len = static_cast<int>(str.length());
	int en = 0;

	for (int st = 0; st < len; ++st)
	{
		while (en < len)
		{
			// 처음 포함되는 알파벳인 경우
			if (freq[str[en] - 'a'] == 0)
			{
				// 이 알파벳을 추가했을 때, 종류가 n개를 넘어가면 break
				if (cnt + 1 > n)
				{
					break;
				}

				// 그렇지 않으면 알파벳 종류의 개수를 1증가
				++cnt;
			}

			// 포함된 알파벳의 빈도수와 en 포인터 1씩 증가
			++freq[str[en++] - 'a'];
		}

		answer = max(answer, en - st);

		// st 포인터가 1증가할 것이므로, st가 가리키는 알파벳의 빈도수 1감소
		// 이때, 빈도수가 0이 되면 알파벳 종류의 개수도 1감소
		if (--freq[str[st] - 'a'] == 0)
		{
			--cnt;
		}
	}

	cout << answer << '\n';
}
