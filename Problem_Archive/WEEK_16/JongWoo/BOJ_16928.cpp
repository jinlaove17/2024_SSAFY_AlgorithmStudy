/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 이 문제는 주사위를 한 번 굴리는 행위가 1번의 턴을 소모하는 것이므로 가중치가 없는 그래프 문제라고 생각할 수 있다.
	  따라서 가중치가 없는 그래프 문제는 아묻따 BFS를 적용하면 되기 때문에 최초 방문이 최소 거리가 된다.
	  전형적인 BFS 코드에 이동하려는 칸(nxt)이 사다리나 뱀일 경우, nxt를 movement[nxt]의 값으로 바꾸어 주고, 이미 방문한 위치일 경우 continue 하는 식을 추가하여 탐색을 진행하였다.

시간 복잡도
	- N x N의 맵을 매번 6칸씩, 탐색하므로 O(N^2)의 시간 복잡도를 갖는다고 생각하였다.
	  물론 이 문제에서는 N이 10으로 고정되어 있다.

실행 시간
	- 0ms(C++)

삽질했던 내용
	- 문제에서 최소 횟수를 구하는 것이기 때문에 뱀을 타고 낮은 번호로 내려가는 것은 반드시 피해야 한다고 생각했는데, (4 -> 75), (77 -> 65), (66, 99)와 같이 뱀과 사다리가 주어진다면,
	  뱀과 사다리를 모두 이용하는 것이 최소 횟수가 된다는 것을 알 수 있다.
	  이 부분을 생각하는 것이 조금 까다로웠다.
*/

#include <iostream>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_BOARD_SIZE = 10;
const int ARRIVAL = 100;

int n, m; // n: 사다리의 수, m: 뱀의 수
int movement[MAX_BOARD_SIZE * MAX_BOARD_SIZE + 1];
int dist[MAX_BOARD_SIZE * MAX_BOARD_SIZE + 1];

int BFS(int st);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int i = 0; i < n + m; ++i)
	{
		int from = 0, to = 0;

		cin >> from >> to;
		movement[from] = to;
	}

	cout << BFS(1) << '\n';
}

int BFS(int st)
{
	int ret = 0;
	queue<int> q;

	for (int i = 1; i <= ARRIVAL; ++i)
	{
		dist[i] = INF;
	}

	dist[st] = 0;
	q.push(st);

	while (!q.empty())
	{
		int cur = q.front();

		q.pop();

		if (cur == ARRIVAL)
		{
			ret = dist[cur];
			break;
		}

		for (int shift = 1; shift <= 6; ++shift)
		{
			int nxt = cur + shift;

			// 100번 칸을 넘어간다면 이동할 수 없다.
			if (nxt > ARRIVAL)
			{
				continue;
			}

			// 도착한 칸이 사다리면, 사다리를 타고 위로 올라가고 뱀이 있는 칸에 도착하면, 뱀을 따라서 내려가게 된다.
			if (movement[nxt] > 0)
			{
				nxt = movement[nxt];
			}

			if (dist[nxt] != INF)
			{
				continue;
			}

			dist[nxt] = dist[cur] + 1;
			q.push(nxt);
		}
	}

	return ret;
}
