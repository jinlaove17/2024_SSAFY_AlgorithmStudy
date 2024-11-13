/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 물을 옮기는 행동은 가중치가 존재하지 않는 그래프 문제로 생각할 수 있기 때문에 BFS 알고리즘을 사용하였다.
	  단, a, b가 최악의 경우 약 10만이기 때문에 visit 배열을 2차원으로 만드는 것은 무리이다.
	  따라서 이진 탐색 트리(set)의 템플릿 매개변수를 pair<int, int>로 설정하여 방문 체크를 수행해 logN에 수행 가능하도록 구현하였다.

시간 복잡도
	- 물통이 (0, 0)에서 시작해 (c, d)가 될 때까지 BFS 탐색을 진행하는데, 이미 방문한 상태는 방문하지 않고 최초 방문시 반복문을 빠져나간다.
	  매번 물통을 조작하는 6가지 경우를 통해 답이 나올 때까지 진행하므로 대략적인 시간 복잡도도 계산하기 어렵다고 생각했다.

실행 시간
	- 196ms(C++)
*/

#include <iostream>
#include <queue>
#include <set>

using namespace std;

struct Node
{
	int aBottle;
	int bBottle;
	int cnt;
};

int a, b, c, d;
set<pair<int, int>> isVisited;

int BFS();

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> a >> b >> c >> d;
	cout << BFS() << '\n';
}

int BFS()
{
	queue<Node> q;

	isVisited.insert(make_pair(0, 0));
	q.push(Node{ 0, 0, 0 });

	while (!q.empty())
	{
		Node cur = q.front();

		q.pop();

		if ((cur.aBottle == c) && (cur.bBottle == d))
		{
			return cur.cnt;
		}

		// 1. F(A)
		Node nxt = {};

		nxt.cnt = cur.cnt + 1;

		if (cur.aBottle < a)
		{
			nxt.aBottle = a;
			nxt.bBottle = cur.bBottle;

			if (isVisited.find(make_pair(nxt.aBottle, nxt.bBottle)) == isVisited.end())
			{
				isVisited.insert(make_pair(nxt.aBottle, nxt.bBottle));
				q.push(nxt);
			}
		}

		// 2. F(B)
		if (cur.bBottle < b)
		{
			nxt.aBottle = cur.aBottle;
			nxt.bBottle = b;

			if (isVisited.find(make_pair(nxt.aBottle, nxt.bBottle)) == isVisited.end())
			{
				isVisited.insert(make_pair(nxt.aBottle, nxt.bBottle));
				q.push(nxt);
			}
		}

		// 3. E(A)
		if (cur.aBottle > 0)
		{
			nxt.aBottle = 0;
			nxt.bBottle = cur.bBottle;

			if (isVisited.find(make_pair(nxt.aBottle, nxt.bBottle)) == isVisited.end())
			{
				isVisited.insert(make_pair(nxt.aBottle, nxt.bBottle));
				q.push(nxt);
			}
		}

		// 4. E(B)
		if (cur.bBottle > 0)
		{
			nxt.aBottle = cur.aBottle;
			nxt.bBottle = 0;

			if (isVisited.find(make_pair(nxt.aBottle, nxt.bBottle)) == isVisited.end())
			{
				isVisited.insert(make_pair(nxt.aBottle, nxt.bBottle));
				q.push(nxt);
			}
		}

		// 5. M(A, B)
		if ((cur.aBottle > 0) && (cur.bBottle < b))
		{
			int shift = min(cur.aBottle, b - cur.bBottle);

			nxt.aBottle = cur.aBottle - shift;
			nxt.bBottle = cur.bBottle + shift;

			if (isVisited.find(make_pair(nxt.aBottle, nxt.bBottle)) == isVisited.end())
			{
				isVisited.insert(make_pair(nxt.aBottle, nxt.bBottle));
				q.push(nxt);
			}
		}

		// 6. M(B, A)
		if ((cur.bBottle > 0) && (cur.aBottle < a))
		{
			int shift = min(a - cur.aBottle, cur.bBottle);

			nxt.aBottle = cur.aBottle + shift;
			nxt.bBottle = cur.bBottle - shift;

			if (isVisited.find(make_pair(nxt.aBottle, nxt.bBottle)) == isVisited.end())
			{
				isVisited.insert(make_pair(nxt.aBottle, nxt.bBottle));
				q.push(nxt);
			}
		}
	}

	return -1;
}
