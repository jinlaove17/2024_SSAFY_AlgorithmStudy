/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 처음에 문제를 읽고 그리디하게 풀어햐 하나? 라는 생각과 함께 우선순위 큐를 활용해야 될 것 같다는 생각이 들었다.
	  하지만, 최악의 경우 프로세스가 10만개가 되기 때문에 선택되지 않은 프로세스의 우선순위를 올려주기 위해 매번 순회하는 것을
	  어떻게 처리해야할지 고민을 하다가 항상 우선순위가 높은 프로세스는 O(1)에 찾을 수 있고 바로 다음 프로세스와만
	  비교하면 되기 때문에 그냥 선택된 프로세스의 우선순위를 낮추자라는 결론을 내릴 수 있었다.
	  이때, 우선순위는 1 ~ 100만 사이의 값이기 때문에 우선순위를 낮추면 음수가 될 수 있다.
	  따라서 모든 프로세스의 우선순위에 100만을 더해주고 계산을 시작하였다.
	  그래서 t초 동안 최상단 프로세스와 다음 프로세스의 우선순위 차와 최상단 프로세스의 실행시간을 비교하여 더 적은 값을 택하고
	  해당 프로세스의 아이디를 출력하는 식으로 문제를 해결할 수 있었다.

시간 복잡도
	- 최대힙에 n개의 원소를 추가하는데 O(NlogN)의 시간이 걸리며, T초 동안 선택된 프로세스를 출력하는데
	  O(TlogN)의 걸리므로 최종적으로 O(NlogN + TlogN)의 시간 복잡도를 갖는다.

실행 시간
	- 256ms
*/

#include <iostream>
#include <queue>

using namespace std;

const int MAX_PRIORITY = 1e6;

struct Process
{
	int id;
	int duration;
	int priority;

	bool operator <(const Process& rhs) const
	{
		if (priority == rhs.priority)
		{
			return id > rhs.id;
		}

		return priority < rhs.priority;
	}
};

int t, n;

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> t >> n;

	priority_queue<Process> maxHeap;

	for (int i = 0; i < n; ++i)
	{
		Process process;
		cin >> process.id >> process.duration >> process.priority;
		process.priority += MAX_PRIORITY;
		maxHeap.push(process);
	}

	for (int i = 0; i < t;)
	{
		Process cur = maxHeap.top();
		maxHeap.pop();

		if (maxHeap.empty())
		{
			while (i < t)
			{
				cout << cur.id << '\n';
				++i;
			}
		}
		else
		{
			int diff = min(cur.duration, max(cur.priority - maxHeap.top().priority, 1));
			diff = min(diff, t - i);

			for (int j = 0; j < diff; ++j)
			{
				cout << cur.id << '\n';
				--cur.priority;
				--cur.duration;
				++i;
			}

			if (cur.duration > 0)
			{
				maxHeap.push(cur);
			}
		}
	}
}
