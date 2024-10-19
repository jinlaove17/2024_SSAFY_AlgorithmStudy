/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에 문제를 읽으면서 DP 냄새를 맡았는데, 색상별로 개수가 주어지고 각 레벨에 사용된 색상의 수는 모두 같아야 하는 조건이 있어서
	  DP 테이블을 어떻게 정의하고, 점화식은 어떻게 세워야 할 지 도무지 감이 오질 않았다.
	  그래서 일단 N이 최대 10이기 때문에 각 레벨별로 될 수 있는 그룹을 일일이 주석으로 써보았다.
	  그룹이 결정됐다면, 그 경우에서 순열 값을 계산하여 누적하면 되지 않을까하는 생각이 들었다.
	  그룹을 결정하는 것은 비트 마스킹을 사용했는데, 해당 레벨에서 1가지 ~ 3가지 색상을 사용해보며 각각의 색상의 개수가 (레벨 / 선택한 색상의 개수) 이상인지
	  검사하여 조건을 만족한다면 순열의 값을 누적하고 탐색을 이어나가도록 구현하였다.
	  이때, 선택한 색상의 개수가 2일 때는 레벨의 2이 배수, 3일 때는 3의 배수여야만 균등한 분배가 가능하다.
	  또한, 순열을 나열할 때, 예를 들어 빨강 2개, 파랑 2개를 나열한다하면 같은 색은 구분하지 않으므로 4! / (2! * 2!)으로 순열 값을 계산해주어야 한다.
	  이 모든 과정을 거쳐 기저에 도달했다면, answer 값을 누적하여 답을 도출할 수 있다.
	  결과적으로 DP가 아닌 특정 조건을 만족하는 몇몇 경우에 대한 완탐을 이용한 풀이로 해결하게 되었다.

시간 복잡도
	- 매 깊이에서 1가지 색상부터 3가지 색상을 사용하는 조합을 구하므로 7번의 검사를 거치고, 개수를 만족한다면 다음 깊이로 재귀호출을 이어나간다.
	  하지만 중간 중간 현재 레벨과 선택한 색상의 개수, 각 색상의 개수 등을 확인해 걸러지는 경우가 많기 때문에 7^N보다는 훨씬 적은 시간이 걸릴 것이라 생각한다.
	  따라서 최종적인 시간 복잡도는 O(7^N)이다.

실행 시간
	- 124ms(C++)
*/

// 1: (1)
// 2: (2), (1, 1)
// 3: (3), (1, 1, 1)
// 4: (4), (2, 2)
// 5: (5)
// 6: (6), (3, 3), (2, 2, 2)
// 7: (7)
// 8: (8), (4, 4)
// 9: (9), (3, 3, 3)
// 10: (10), (5, 5)
#include <iostream>

using namespace std;

const int MAX_TREE_SIZE = 10;
const int MAX_COLOR_COUNT = 3;

int n;
int facto[MAX_TREE_SIZE + 1];
int colorCounts[MAX_COLOR_COUNT]; // 0: red, 1: green, 2: blue
long long answer;

void DFS(int level, int red, int green, int blue, long long cnt);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> colorCounts[0] >> colorCounts[1] >> colorCounts[2];
	facto[1] = 1;

	for (int i = 2; i <= n; ++i)
	{
		facto[i] = i * facto[i - 1];
	}

	DFS(1, colorCounts[0], colorCounts[1], colorCounts[2], 1);
	cout << answer << '\n';
}

void DFS(int level, int red, int green, int blue, long long cnt)
{
	if (level == n + 1)
	{
		answer += cnt;
		return;
	}

	// 빨간색 1개만 사용하는 1(001)부터 모든 색을 사용하는 7(111)까지 부분집합을 살펴본다.
	for (int bit = 1; bit < (1 << MAX_COLOR_COUNT); ++bit)
	{
		bool isUsed[MAX_COLOR_COUNT] = {};
		int selectCount = 0;

		// 사용할 색상을 선택한다.
		for (int i = 0; i < MAX_COLOR_COUNT; ++i)
		{
			if (bit & (1 << i))
			{
				isUsed[i] = true;
				++selectCount;
			}
		}
		
		// 선택한 색상의 가지수가 2개일 때, 레벨이 2의 배수가 아니라면 균등한 개수의 그룹을 만들 수 없다.
		if ((selectCount == 2) && (level % 2 != 0))
		{
			continue;
		}

		// 선택한 색상의 가지수가 3개 일때, 레벨이 3의 배수가 아니라면 균등한 개수의 그룹을 만들 수 없다.
		if ((selectCount == 3) && (level % 3 != 0))
		{
			continue;
		}

		bool isPossible = true;
		int need = level / selectCount;

		for (int i = 0; i < MAX_COLOR_COUNT; ++i)
		{
			if (!isUsed[i])
			{
				continue;
			}

			if (((i == 0) && (red < need)) || ((i == 1) && (green < need)) || ((i == 2) && (blue < need)))
			{
				isPossible = false;
				break;
			}
		}

		if (isPossible)
		{
			// 순열 값을 구할 때, 같은 색상끼리는 구분하지 않으므로 해당 경우만큼 나눠줘야 한다.
			long long curCnt = facto[level];

			for (int i = 0; i < selectCount; ++i)
			{
				curCnt /= facto[need];
			}

			DFS(level + 1, (isUsed[0]) ? red - need : red, (isUsed[1]) ? green - need : green, (isUsed[2]) ? blue - need : blue, cnt * curCnt);
		}
	}
}
