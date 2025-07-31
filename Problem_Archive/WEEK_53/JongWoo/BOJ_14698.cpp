/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 전기 에너지를 최소화하려면 어떻게 해야할 지 주어진 숫자들을 곱하는 순서가 중요하다는 것을 깨달았고,
	  하나씩 따져보면 값이 작은 것부터 먼저 곱해나가면 전체가 최소가 된다는 것을 깨달았다.
	  가장 작은 두 값을 빠르게 찾기 위해서는 최소힙을 사용하면 되겠다고 생각했고, 그리디한 방식으로 2개씩 꺼내 곱한 값을 다시 넣어주는 식으로
	  문제를 해결할 수 있었다.

시간 복잡도
	- T개의 테스트 케이스에 대해 최소힙에 N개의 데이터를 추가한 후 2개씩 꺼내 곱한 후 다시 삽입하므로 최종적으로 O(NlogN)의 시간 복잡도가 걸린다.

실행 시간
	- 168ms
*/

#include <iostream>
#include <queue>

using namespace std;

const long long MOD = 1e9 + 7;

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int t = 0;
	cin >> t;

	for (int tc = 1; tc <= t; ++tc)
	{
		int n = 0;
		cin >> n;

		priority_queue<long long, vector<long long>, greater<long long>> minHeap;

		for (int i = 0; i < n; ++i)
		{
			long long energy = 0;
			cin >> energy;
			minHeap.push(energy);
		}

		long long answer = 1;

		while (minHeap.size() > 1)
		{
			long long a = minHeap.top();
			minHeap.pop();

			long long b = minHeap.top();
			minHeap.pop();

			minHeap.push(a * b);

			// (A * B) % MOD = ((A % MOD) * (B % MOD)) % MOD
			answer = ((answer % MOD) * ((a * b) % MOD)) % MOD;
		}

		cout << answer << '\n';
	}
}
