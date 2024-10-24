/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 공이 최대 20만개이므로, nlogn의 시간이 걸리는 알고리즘을 사용해야겠다고 생각했다.
	  가장 먼저 떠오른게 전체 공을 크기별 오름차순으로 정렬하고, 색상별로도 각 공을 크기별 오름차순으로 정렬하는 것이였다.
	  이제 크기의 누적합을 계산하여 합을 상수 시간에 구할 수 있도록 셋팅한 후,
	  1번째 공부터 순회하여 lower_bound 함수를 통해 몇 번째 크기인지 구해 누적합을 계산하고, 그 구간 내에 같은 색의 누적합은 공은 색상별로 저장한 곳에서 lower_bound 함수를 통해 구한다.
	  이제 전체 누적합에서 같은 색 공의 누적합을 빼면 답을 도출할 수 있다.

시간 복잡도
	- 우선 n개의 공을 크기 오름차순으로 정렬하고, 각 색상별로도 크기 오름차순으로 정렬을 수행하기 때문에 2 * nlogn의 시간이 걸리며,
	  1번부터 n번까지의 공을 순회하면서 전체 크기와 색상별 크기별 이진 탐색 또한 2 * nlogn의 시간이 걸린다.
	  따라서 최종적인 시간 복잡도는 O(nlogn)이 된다.

실행 시간
	- 204(ms)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX_BALL_COUNT = 200'000;

struct Ball
{
	int color;
	int size;
};

int n;
int sizes[MAX_BALL_COUNT + 1];
int accumSizes[MAX_BALL_COUNT + 1]; // 누적합
Ball balls[MAX_BALL_COUNT + 1];
vector<int> ballSizesByColor[MAX_BALL_COUNT + 1];
vector<int> accumSizesByColor[MAX_BALL_COUNT + 1];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	int maxColor = 1;

	for (int i = 1; i <= n; ++i)
	{
		cin >> balls[i].color >> balls[i].size;
		sizes[i] = balls[i].size;
		ballSizesByColor[balls[i].color].push_back(balls[i].size);
		maxColor = max(maxColor, balls[i].color);
	}

	sort(sizes + 1, sizes + 1 + n);
	accumSizes[1] = sizes[1];

	for (int i = 2; i <= n; ++i)
	{
		accumSizes[i] = accumSizes[i - 1] + sizes[i];
	}

	for (int i = 1; i <= maxColor; ++i)
	{
		if (ballSizesByColor[i].empty())
		{
			continue;
		}

		// 색상별로 공을 크기 오름차순으로 정렬한다.
		sort(ballSizesByColor[i].begin(), ballSizesByColor[i].end());

		// 색상별로 누적합을 계산한다.
		accumSizesByColor[i].resize(ballSizesByColor[i].size());
		accumSizesByColor[i][0] = ballSizesByColor[i][0];

		for (int j = 1; j < ballSizesByColor[i].size(); ++j)
		{
			accumSizesByColor[i][j] = accumSizesByColor[i][j - 1] + ballSizesByColor[i][j];
		}
	}

	for (int i = 1; i <= n; ++i)
	{
		int idx1 = lower_bound(sizes + 1, sizes + 1 + n, balls[i].size) - (sizes + 1) + 1;
		int total = accumSizes[idx1];

		if (!ballSizesByColor[balls[i].color].empty())
		{
			int idx2 = lower_bound(ballSizesByColor[balls[i].color].begin(), ballSizesByColor[balls[i].color].end(), balls[i].size) - ballSizesByColor[balls[i].color].begin();

			total -= accumSizesByColor[balls[i].color][idx2];
		}

		cout << total << '\n';
	}
}
