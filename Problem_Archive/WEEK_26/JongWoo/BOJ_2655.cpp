/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 5개의 조건 중에서 사실 밑면의 넓이와 무게만 고려하면 되는데, 이 값들은 중복되지 않는다.
	  따라서 벽돌의 정보를 입력받은 후 밑면의 넓이를 기준으로 내림차순 정렬을 수행하면 조건 1개는 만족을 하게된다.
	  이제 여기서 DFS를 이용해 완전 탐색으로 무게가 현재 벽돌보다 작은 벽돌로 이동하며 높이를 누적해봤는데, 이 문제는 모든 인덱스와 개수를 출력해야 되기 때문에,
	  현재 벽돌에 오기 바로 직전의 벽돌 인덱스를 기억할 필요가 있다. 또한 매번 높이가 갱신될 때마다 이 배열을 역추적하며 값을 보관해 놓는 것은 시간초과가 발생한다.
	  그래서 고민을 하다가 완전 탐색은 아니라고 판단하고 LIS(최장 증가 부분 수열) 알고리즘을 떠올리게 되었다.
	  dp 테이블을 1 ~ i번째 벽돌을 선택할 때, 높이 합의 최댓값으로 설정하고 모든 벽돌을 순회하면서 테이블의 값을 채웠다.
	  최종적으로 가장 큰 높이를 가진 인덱스부터 이전 벽돌을 역추적 하면서 벡터에 값을 넣어주면 문제에서 원하는 답을 도출할 수 있다.

시간 복잡도
	- 최대 100개의 벽돌이 주어지므로 LIS 알고리즘을 적용하면 O(N^2)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX_BRICK_COUNT = 100;

struct Brick
{
	int index;
	int area;
	int height;
	int weight;
};

int n; // n: 벽돌의 수(<= 100)
Brick brickList[MAX_BRICK_COUNT];
int dp[MAX_BRICK_COUNT];
int prevList[MAX_BRICK_COUNT];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	cin >> n;

	for (int i = 0; i < n; ++i)
	{
		brickList[i].index = i + 1;
		prevList[i] = i;
		cin >> brickList[i].area >> brickList[i].height >> brickList[i].weight;
	}

	// 밑면의 넓이를 기준으로 내림차순으로 정렬한다.
	sort(brickList, brickList + n,
		[](const Brick& a, const Brick& b) {
			return a.area > b.area;
		});

	int maxIndex = 0;

	for (int i = 0; i < n; ++i)
	{
		dp[i] = brickList[i].height;

		for (int j = 0; j < i; ++j)
		{
			if ((brickList[j].weight > brickList[i].weight) && (dp[j] + brickList[i].height > dp[i]))
			{
				dp[i] = dp[j] + brickList[i].height;
				prevList[i] = j;
			}
		}

		if (dp[i] > dp[maxIndex])
		{
			maxIndex = i;
		}
	}

	vector<int> answer;
	int cur = maxIndex;

	while (cur != prevList[cur])
	{
		answer.push_back(brickList[cur].index);
		cur = prevList[cur];
	}
	answer.push_back(brickList[cur].index);
	
	cout << answer.size() << '\n';
	for (int index : answer)
	{
		cout << index << '\n';
	}
}
