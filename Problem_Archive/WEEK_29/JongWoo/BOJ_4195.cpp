/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 두 이름이 주어졌을 때 그 사람이 속한 그룹을 합쳐서 총 그룹에 속한 사람의 수를 구하는 것을 보고 Union-Find 알고리즘을 떠올렸다.
	  인풋이 문자열로 주어지기 때문에 해시를 통해 적절히 정수로 매핑을 해야겠다는 생각이 들었고, 그룹의 크기를 계산하기 위해서
	  부모 배열의 초기값을 자기 자신으로 두는 것이 아니라 음수로 설정하여 Union이 될 때마다 음수를 누적하는 방식으로 구현하는 방식을 사용했다.
	  즉, 부모의 인덱스가 음수일 경우에는 최상위 노드가 되며 그 그룹에 속한 노드의 수는 절댓값을 붙인 값이 되는 것이다.
	  위와 같은 방법을 사용해 문제를 해결할 수 있었다.

시간 복잡도
	- 각 테스트 케이스에 대해서 해싱 작업을 하는데 O(1), 경로 압축을 수행한 Union-Find 알고리즘을 f번 수행하는데 O(f * α(n))의 시간이 걸린다.
	  따라서 최종적인 시간 복잡도는 O(α(n))이다.

실행 시간
	- 88ms
*/

#include <iostream>
#include <string>
#include <unordered_map>

using namespace std;

const int MAX_USER_COUNT = 2 * 100'000;

int f, nextId;
int parents[MAX_USER_COUNT];

int setUnion(int a, int b);
int getParent(int a);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int tc = 0;
	cin >> tc;

	for (int t = 1; t <= tc; ++t)
	{
		cin >> f;
		nextId = 0;

		for (int i = 0; i < MAX_USER_COUNT; ++i)
		{
			parents[i] = -1;
		}

		unordered_map<string, int> nameMapper;

		for (int i = 0; i < f; ++i)
		{
			string a, b;
			cin >> a >> b;

			if (nameMapper.find(a) == nameMapper.end())
			{
				nameMapper[a] = nextId++;
			}

			if (nameMapper.find(b) == nameMapper.end())
			{
				nameMapper[b] = nextId++;
			}

			cout << setUnion(nameMapper[a], nameMapper[b]) << '\n';
		}
	}
}

int setUnion(int a, int b)
{
	int aRoot = getParent(a);
	int bRoot = getParent(b);

	if (aRoot == bRoot)
	{
		return -parents[aRoot];
	}
	else if (aRoot > bRoot)
	{
		swap(aRoot, bRoot);
	}

	parents[aRoot] += parents[bRoot];
	parents[bRoot] = aRoot;

	return -parents[aRoot];
}

int getParent(int a)
{
	if (parents[a] < 0)
	{
		return a;
	}

	return parents[a] = getParent(parents[a]);
}
