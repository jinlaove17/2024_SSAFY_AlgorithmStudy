/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 전구의 색상을 연산의 편의를 위해 숫자(0, 1, 2)로 바꾸고, 모든 색을 빨간색으로, 초록색으로, 파란색으로 바꾼다는 목표를 설정한 다음에
	  해당 색이 아닌 전구 칸에 대해 3칸을 목표 색으로 바꾸는 식으로 진행하면서 최종적으로 n - 3번째 칸과 n - 2칸, n - 2칸과 n - 1칸까지 같다면 가능한 경우가 된다.
	  이러한 그리디한 로직이 가능한 이유는, 왼쪽부터가 아닌 특정 위치에서 전구의 색을 바꾸더라도 결국엔 왼쪽에 있는 전구를 목표 색으로 바꾸어야 하기 때문에
	  왼쪽부터 나아가며 전구색을 바꿔야 오른쪽에 있는 전구들에 대해 오로지 초기 상태에서 색이 변하기 때문에 최소가 될 것이라고 생각했다.

시간 복잡도
	- 3가지 색으로 N개의 전구를 3개씩 바꿔보는 시도를하므로 O(3 * 3 * N), 최종적으로 O(N)의 시간 복잡도를 갖는다.

실행 시간
	- 4ms(C++)
*/

#include <iostream>

using namespace std;

const int INF = 987'654'321;
const int MAX_BULB_COUNT = 100'000;

int n;
int bulbs[MAX_BULB_COUNT + 1];
int tmp[MAX_BULB_COUNT + 1];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int i = 0; i < n; ++i)
	{
		char color = 0;

		cin >> color;

		switch (color)
		{
		case 'R':
			bulbs[i] = 0;
			break;
		case 'G':
			bulbs[i] = 1;
			break;
		case 'B':
			bulbs[i] = 2;
			break;
		}
	}

	int answer = INF;

	// 모든 색을 target으로 맞춘다.
	for (int target = 0; target < 3; ++target)
	{
		for (int i = 0; i < n; ++i)
		{
			tmp[i] = bulbs[i];
		}

		int cnt = 0;

		for (int i = 0; i <= n - 3; ++i)
		{
			if (tmp[i] == target)
			{
				continue;
			}

			for (int j = 1; j <= 2; ++j)
			{
				if ((tmp[i] + j) % 3 == target)
				{
					tmp[i] = target;
					tmp[i + 1] = (tmp[i + 1] + j) % 3;
					tmp[i + 2] = (tmp[i + 2] + j) % 3;
					cnt += j;
					break;
				}
			}
		}

		if ((tmp[n - 3] == tmp[n - 2]) && (tmp[n - 2] == tmp[n - 1]))
		{
			answer = min(answer, cnt);
		}
	}

	cout << ((answer == INF) ? -1 : answer) << '\n';
}
