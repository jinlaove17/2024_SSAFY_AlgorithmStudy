/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 문제에서 주어지는 값의 범위를 살펴보면 n은 최대 10만이고, x는 최대 10억으로 굉장히 큰 값임을 알 수 있다.
	  또한 최솟값을 구해야 한다는 점에서 이분 탐색을 사용하면 되지 않을까 생각했다.
	  공정의 수를 이분 탐색의 기준으로 설정하고, 매번 공정의 수만큼 시뮬레이션을 돌리는데 사용 시간이 가장 적은 공정부터 배정이 되도록 해야하기 때문에 최소힙을 떠올리게 되었다.
	  최소힙의 원소의 개수를 md개로 유지하면서 새로운 선물들의 사용시간을 누적해가면 최종저긍로 힙에 들어있는 최댓값이 총 소요시간이 된다.
	  이 값에 따라 st, en을 적절히 조절해주면서 이분 탐색을 진행하면 문제에서 원하는 답을 도출할 수 있을 것이다.

시간 복잡도
	- 이분 탐색을 통해 1 ~ n개의 공정을 선택하는데 O(logN)의 시간이 걸리며 공정 선택 후, n개의 선물을 md개의 공정에 우선순위 큐를 통해 삽입하는 과정에서 O(NlogN)의 시간이 걸린다.
	  따라서 최종적인 시간 복잡도는 O(logN * NlogN)이 된다.

실행 시간
	- 72ms

삽질했던 내용
	- 삽집이라기 보다는, 모든 시뮬레이션을 수행한 후 최소힙에서 가장 큰 값을 찾을 때 원소의 개수가 1이 될 때까지 pop을 수행했는데 그렇게 하지말고 매번 원소를 추가할 때
	  최댓값을 저장하는 방식으로 바꾸고, 추가하려는 값이 이미 x를 넘어간 경우 break로 탈출하도록 구현하면 시간을 많이 단축할 수 있다.(200ms -> 72ms)
*/

#include <iostream>
#include <queue>

using namespace std;

const int MAX_GIFT_COUNT = 100'000;

int n, x;
int durations[MAX_GIFT_COUNT];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> x;
	for (int i = 0; i < n; ++i)
	{
		cin >> durations[i];
	}

	int answer = 0;
	int st = 1, en = n;
	while (st <= en)
	{
		int md = (st + en) / 2;
		priority_queue<long long, vector<long long>, greater<long long>> minHeap;
		for (int i = 0; i < md; ++i)
		{
			minHeap.push(durations[i]);
		}

		int maxDuration = 0;
		for (int i = md; i < n; ++i)
		{
			int duration = minHeap.top() + durations[i];
			maxDuration = max(maxDuration, duration);
			if (maxDuration > x)
			{
				break;
			}

			minHeap.push(duration);
			minHeap.pop();
		}

		if (maxDuration <= x)
		{
			answer = md;
			en = md - 1;
		}
		else
		{
			st = md + 1;
		}
	}

	cout << answer << '\n';
}
