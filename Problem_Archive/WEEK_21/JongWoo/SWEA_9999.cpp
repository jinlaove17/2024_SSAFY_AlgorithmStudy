/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 이 문제는 스스로 해결하지 못했다.
	  문제를 선정할 때 카테고리가 이분탐색이라는 것을 알고 있었음에도 어떻게 해결해야 할 지 도무지 감을 잡지 못했다.
	  이전까지는 거의 문제에서 구해야 하는 값을 이분탐색의 대상으로 놓거나, 이분탐색 안에 for문이 있는 문제들만 풀어서 더더욱 그랬던 것 같다.
	  참고한 풀이에 대해 설명하자면, N개의 피크 타임을 입력 받으면서, 시작 시간과 종료 시간의 차를 계산하고 전체 누적합을 계산한다.
	  광고시간이 피크 타임과 최대한 많이 겹치려면 피크 타임이 시작되는 시간에 광고도 시작되게 배치하면 되기 때문에 매 피크 타임을 순회하며 시작점에서 L초 간 광고를 진행한다.
	  각 피크 타임의 시작 시간 + 광고 시간(L)의 값을 이분탐색의 대상으로 하여 이 값보다 종료 시간이 더 큰 피크 타임을 구한다.
	  구한 인덱스 - 1까지의 누적합만큼은 광고시간과 겹친다고 할 수 있다. 이때, 현재 피크 타임의 인덱스에 따라 앞쪽 피크 타임의 값은 빼주어야 하며,
	  마지막 피크 타임에 걸치는 경우가 존재하므로 이 값까지 더한 후 최댓값을 갱신해주면 문제에서 원하는 답을 도출할 수 있다.
	  이 문제는 이분탐색을 이렇게 활용하는 발상을 떠올리는 것이 굉장히 어렵다고 생각하고, 전혀 관계가 없어보이는 누적합을 이용한다는 점 또한 난도가 있다고 생각한다.
	  반드시 다시 풀어봐야 할 문제이다.

시간 복잡도
	- 각 테스트케이스별로, N개의 피크 타임에 대해 upperBound로 이분탐색을 수행하므로 O(NlogN)의 시간복잡도를 갖는다.

실행 시간
	- 1,074ms
*/

#include <iostream>

using namespace std;

const int MAX_PEAK_COUNT = 100'000;

int L, N;
pair<int, int> peakTimeList[MAX_PEAK_COUNT];
int accList[MAX_PEAK_COUNT];

int upperBound(int target);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int T = 0;
	cin >> T;

	for (int t = 1; t <= T; ++t)
	{
		cin >> L;
		cin >> N;
		cin >> peakTimeList[0].first >> peakTimeList[0].second;
		accList[0] = peakTimeList[0].second - peakTimeList[0].first;

		for (int i = 1; i < N; ++i)
		{
			cin >> peakTimeList[i].first >> peakTimeList[i].second;
			accList[i] = accList[i - 1] + (peakTimeList[i].second - peakTimeList[i].first);
		}

		int answer = 0;

		for (int i = 0; i < N; ++i)
		{
			// i번째 피크 시간의 시작 시간부터 L분동안 광고를 진행
			int en = peakTimeList[i].first + L;

			// en보다 종료 시간이 뒤에 있는 피크 시간을 찾는다.
			int res = upperBound(en);

			// 피크 시간대에 광고가 나가는 시간
			int duration = accList[res - 1];

			// 첫 번째 피크 시간이 아니라면, 앞쪽 누적합은 빼주어야 한다.
			if (i > 0)
			{
				duration -= accList[i - 1];
			}

			// 마지막 피크 시간에 걸치는 경우
			if ((res < N) && (en > peakTimeList[res].first))
			{
				duration += (en - peakTimeList[res].first);
			}

			answer = max(answer, duration);
		}

		cout << '#' << t << ' ' << answer << '\n';
	}
}

int upperBound(int target)
{
	int res = N;
	int st = 0, en = N - 1;

	while (st <= en)
	{
		int md = st + (en - st) / 2;

		if (peakTimeList[md].second > target)
		{
			res = md;
			en = md - 1;
		}
		else
		{
			st = md + 1;
		}
	}

	return res;
}
