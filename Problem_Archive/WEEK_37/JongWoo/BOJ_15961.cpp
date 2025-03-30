/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽어보니 최악의 경우 N이 300만으로 굉장히 큰 값이므로, O(NlogN)이하의 알고리즘을 사용해야 된다는 생각을 하였다.
	  그리고 k개의 구간을 살펴봐야 되기 때문에 슬라이딩 윈도우나 투포인터 같은 생각을 떠올리게 되었고,
	  원형을 편하게 1차원 배열로만 만들어줘서 투포인터를 적절히 적용하면 해결이 가능할 것이라고 생각하게 되었다.
	  맨 마지막 원소에서 시작해 맨 앞에서 k - 1개의 원소를 택하는 경우까지 존재하므로, 배열을 그만큼 확장시켜주고,
	  투 포인터를 사용해 구간 내에 속한 번호의 개수를 카운팅 하며 쿠폰에 있는 초밥을 포함하는지 여부에 따라 1을 더해주었다.
	  위 과정을 거치면 문제에서 원하는 답을 도출할 수 있다.

시간 복잡도
	- 투 포인터를 사용하여 길이가 N인 배열을 탐색하므로 2 * N, 즉 O(N)의 시간 복잡도를 갖는다.

실행 시간
	- 236ms
*/

#include <iostream>

using namespace std;

const int MAX_COUNT = 3'000'000;
const int MAX_TYPE_COUNT = 3'000;

int n, d, k, c;
int arr[MAX_COUNT + MAX_TYPE_COUNT];
int freq[MAX_TYPE_COUNT + 1];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> d >> k >> c;
	for (int i = 0; i < n; ++i)
	{
		cin >> arr[i];
	}
	for (int i = n; i < n + k - 1; ++i)
	{
		arr[i] = arr[i - n];
	}

	int answer = 0, en = 0, cnt = 0;
	for (int st = 0; st < n; ++st)
	{
		while ((en < n + k - 1) && (en - st + 1 <= k))
		{
			if (++freq[arr[en++]] == 1)
			{
				++cnt;
			}
		}

		answer = max(answer, cnt + ((freq[c]) ? 0 : 1));
		if (--freq[arr[st]] == 0)
		{
			--cnt;
		}
	}

	cout << answer << '\n';
}
