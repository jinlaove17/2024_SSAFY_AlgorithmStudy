/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 각 통나무를 x1을 기준으로 오름차순 정렬한 후, 통나무 별로 인접한 통나무로 이동이 가능한지 검사한다.
	  이때 y가 같으면 점프가 불가능하다는 조건과, 이동 가능한 통나무가 여러개 일 수 있다는 사실을 명심해야 한다.
	  이동이 가능하다면 하나의 집합으로 본다는 생각을 해서 Union-Find 알고리즘을 적용하면 풀 수 있을 것 같다는 생각을 하게 되었다.
	  Union-Find 알고리즘을 parents 배열을 집합의 크기로 설정하기 위해 초기 값을 -1로 설정해 주었다.
	  여기까지 생각했다면 전형적인 경로 압축을 적용한 서로소 집합 알고리즘을 적용한 후 각 쿼리에 대해 같은 집합인지 확인해주면 답을 도출할 수 있을 것이다.

시간 복잡도
	- 정렬 후, 각 통나무별로 앞에 있는 통나무의 x2 값과 뒤에 있는 x1 값을 비교하며 경로 압축을 적용한 서로소 집합을 만드는 데
	  O(NlogN + a(N))의 시간이 걸릴 것이다.

실행 시간
	- 76ms
*/

#include <iostream>
#include <algorithm>

using namespace std;

const int MAX_LOG_COUNT = 100'000;

struct Log {
	int num;
	int x1;
	int x2;
	int y;

	bool operator <(const Log& rhs) const
	{
		if (x1 == rhs.x1)
		{
			return x2 < rhs.x2;
		}

		return x1 < rhs.x1;
	}
};

int n, q;
Log logs[MAX_LOG_COUNT];
int parents[MAX_LOG_COUNT];

bool mergeSet(int a, int b);
int findSet(int a);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> q;
	for (int i = 0; i < n; ++i)
	{
		logs[i].num = i + 1;
		cin >> logs[i].x1 >> logs[i].x2 >> logs[i].y;
	}

	sort(logs, logs + n);

	for (int i = 1; i <= n; ++i)
	{
		parents[i] = -1;
	}

	for (int i = 0; i < n - 1; ++i)
	{
		for (int j = i + 1; j < n; ++j)
		{
			if (logs[j].y == logs[i].y)
			{
				continue;
			}

			if (logs[j].x1 <= logs[i].x2)
			{
				mergeSet(logs[i].num, logs[j].num);
			}
			else
			{
				break;
			}
		}
	}

	for (int i = 0; i < q; ++i)
	{
		int a = 0, b = 0;
		cin >> a >> b;
		
		int aRoot = findSet(a);
		int bRoot = findSet(b);
		cout << ((aRoot == bRoot) ? 1 : 0) << '\n';
	}
}

bool mergeSet(int a, int b)
{
	int aRoot = findSet(a);
	int bRoot = findSet(b);
	if (aRoot == bRoot)
	{
		return false;
	}

	if (parents[aRoot] <= parents[bRoot])
	{
		parents[aRoot] += parents[bRoot];
		parents[bRoot] = aRoot;
	}
	else
	{
		parents[bRoot] += parents[aRoot];
		parents[aRoot] = bRoot;
	}

	return true;
}

int findSet(int a)
{
	if (parents[a] < 0)
	{
		return a;
	}

	return parents[a] = findSet(parents[a]);
}
