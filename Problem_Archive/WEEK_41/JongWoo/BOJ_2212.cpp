/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 위치 정보를 모두 오름차순으로 정렬한 다음, 인접한 값의 차이를 최대힙에 저장하여 거리가 먼 것부터 배제해 나가는 아이디어를 떠올렸다.
    k - 1 등분을 하면 k개가 되듯 k - 1번 pop을 해줘서 최댓값을 전체 값에서 빼주면 답을 도출할 수 있다.

시간 복잡도
	- 정렬을 수행한 후, 우선순위 큐에 차이 값을 넣어 k - 1번 나눠 k개의 그룹을 만들 때까지 pop을 해주므로 NlogN + NlogN + k - 1의 시간이 걸린다. 즉, O(NlogN)의 시간 복잡도를 걸린다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <queue>
#include <algorithm>

using namespace std;

const int MAX_SENSOR_COUNT = 10'000;

int n, k;
int sensorPositions[MAX_SENSOR_COUNT];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> k;
	for (int i = 0; i < n; ++i)
	{
		cin >> sensorPositions[i];
		sensorPositions[i] += 1'000'000;
	}

	int answer = 0;

	if (k < n)
	{
		sort(sensorPositions, sensorPositions + n);

		priority_queue<int> maxHeap;
		for (int i = 1; i < n; ++i)
		{
			int diff = sensorPositions[i] - sensorPositions[i - 1];
			maxHeap.push(diff);
			answer += diff;
		}

		for (int i = 0; i < k - 1; ++i)
		{
			answer -= maxHeap.top();
			maxHeap.pop();
		}
	}

	cout << answer << '\n';
}
