/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 낮일 때는 문제에서 주어진 조건대로 유죄 지수가 가장 높은 사람을 제거하면 되지만, 밤일 때는 시민을 죽였을 때 다른 시민의 유죄 지수를 많이 높여 
	  항상 낮에 죽도록 구현을 하려고 했으나, n의 최악의 경우 16이고 8번은 확정적으로 사람이 죽기 때문에 깊이가 그렇게 깊어지지 않을 것 같아
	  DFS + 백트래킹으로 탐색을 진행하였다.
	  전체 인원의 사망 여부는 bitmasking을 사용하여 처리하였다.

시간 복잡도
	- 최악의 경우 n이 16이므로, 가장 오래 살아남는다고 가정했을 때 낮에는 8명이 추가적인 탐색 없이 각 깊이에서 사망하고
	  밤에는 생존한 8명에 대해 완전 탐색을 진행하므로 (N / 2)!의 연산이 걸릴 것이다.
	  따라서 O(N!)의 시간복잡도를 갖는다고 생각하였다.

실행 시간
	- 236ms(C++)

삽질했던 내용
	- 처음에 DFS + 백트래킹 탐색을 시도하여 밤일 때 은진이의 유죄 지수를 가장 적게 오르게 하는 시민을 죽이는 식으로 구현하였는데,
	  만약 해당 시민이 은진이를 제외한 나머지 시민들에 대한 유죄 지수가 -26이라면 오히려 은진이가 가장 많이 오르게 되는 경우가 되기 때문에 최적이 될 수 없다는 것을 깨달았다.
*/

#include <iostream>

using namespace std;

const int MAX_PLAYER_COUNT = 16;

int n, mafia, answer;
int adjMat[MAX_PLAYER_COUNT][MAX_PLAYER_COUNT];
int crimeScore[MAX_PLAYER_COUNT];
int deadState;

void Simulate(int day, int citizenCount);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int i = 0; i < n; ++i)
	{
		cin >> crimeScore[i];
	}

	for (int i = 0; i < n; ++i)
	{
		for (int j = 0; j < n; ++j)
		{
			cin >> adjMat[i][j];
		}
	}

	cin >> mafia;
	Simulate(0, n - 1);
	cout << answer << '\n';
}

void Simulate(int day, int citizenCount)
{
	if ((deadState & (1 << mafia)) || (citizenCount == 0))
	{
		answer = max(answer, day);

		return;
	}

	bool isNight = !((citizenCount + 1) & 1);

	if (isNight)
	{
		for (int i = 0; i < n; ++i)
		{
			if ((deadState & (1 << i)) || (i == mafia))
			{
				continue;
			}

			deadState |= (1 << i);

			for (int j = 0; j < n; ++j)
			{
				crimeScore[j] += adjMat[i][j];
			}

			Simulate(day + 1, citizenCount - 1);

			deadState &= ~(1 << i);

			for (int j = 0; j < n; ++j)
			{
				crimeScore[j] -= adjMat[i][j];
			}
		}
	}
	else
	{
		int removed = -1;

		for (int i = 0; i < n; ++i)
		{
			if (deadState & (1 << i))
			{
				continue;
			}

			if ((removed == -1) || (crimeScore[i] > crimeScore[removed]))
			{
				removed = i;
			}
		}

		deadState |= (1 << removed);
		Simulate(day, (removed == mafia) ? citizenCount : citizenCount - 1);
		deadState &= ~(1 << removed);
	}
}
