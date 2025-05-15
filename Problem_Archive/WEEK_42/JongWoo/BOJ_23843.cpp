/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에 문제를 읽고나서 이분 탐색으로 해결해보려고 했었는데, 46%에서 계속 틀려서 원인을 분석해본 결과
	  이분 탐색의 값(md)이 10일 때, 콘센트의 개수(m)가 2라면, 20(10, 10)까지 감당할 수 있지만, [9, 11]일 경우 모순이 된다.
	  따라서 뭔가 그리디하게 시뮬레이션을 해야겠구나 생각하였고, 시간을 내림차순으로 정렬한 뒤 최소힙을 선언해 크기를 m으로 유지하면서
	  계속 합의 크기가 작은 콘센트에 새로운 기기를 충전하는 아이디어로 문제를 해결할 수 있었다.
	  개인적으로 코드는 간단했지만, 막상 문제를 풀려니 엄청 쉽지만은 않았다.
	  
시간 복잡도
	- 시간을 내림차순으로 정렬하는데 O(NlogN)의 시간이 걸리며, 최소힙에 값을 m개로 유지하면서 삽입/삭제를 수행하는데 O(NlogN)의 시간이 걸린다.
	  최종적으로 O(NlogN)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <queue>
#include <algorithm>

using namespace std;

const int MAX_COUNT = 10'000;

int n, m;
int times[MAX_COUNT];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;
	for (int i = 0; i < n; ++i)
	{
		cin >> times[i];
	}
	sort(times, times + n, greater<int>());

	priority_queue<int, vector<int>, greater<int>> minHeap;
	while (minHeap.size() < m)
	{
		minHeap.push(0);
	}

	for (int i = 0; i < n; ++i)
	{
		minHeap.push(minHeap.top() + times[i]);
		minHeap.pop();
	}

	while (minHeap.size() != 1)
	{
		minHeap.pop();
	}

	cout << minHeap.top() << '\n';
}
