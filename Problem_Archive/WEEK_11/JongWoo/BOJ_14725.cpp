/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽으면서 이진 탐색 트리나 트라이를 사용해야겠다는 생각이 들었는데, 트라이의 경우 이전까지는 다음 노드를 문자(char)의 형태로만 관리하는 경우만 학습했기 때문에,
	  이와 같은 문자열 기반의 문제에 적용할 수 있을지 의문이 들었다.
	- 그래서 key 값을 string으로, map이나 set을 value로 적절히 활용하여 이진 탐색 트리를 활용하는 풀이를 고민해봤는데 map의 템플릿 매개변수가 map<string, map<string, map<... 으로
	  무한정 늘어나는 아이디어 밖에 생각나지 않아 트라이를 응용하는 방식으로 풀어야겠다고 생각했다.
	- 어찌됐든 그 동안은 자식을 문자(char)로 관리했기 때문에 이를 적절히 바꾸면 string에도 대응되는 트라이를 만들 수 있다고 생각하였고, 문제에서 정렬된 결과를 원하기 때문에
	  map 자료구조를 떠올리게 되어 최종적으로 자식을 map<string, TrieNode*>로 관리하는 방식을 떠올리게 되었다.
	- 이와 같이 구성하면, 현재까지의 노드에서 어떤 자식이 있는지 순서를 유지하며 O(logN)에 찾을 수 있고, 그 자식의 자식도 접근할 수 있는 자료구조가 되어 insert와 print 모두 대응할 수 있다.

시간 복잡도
	- n개의 개미가 각각 k개의 문자열을 들고 노드를 타고 내려가므로 삽입에는 O(N * KlogK)의 시간 복잡도를 갖으며,
	  모든 자식을 출력하는 경우 C개의 자식이 있을 때 O(ClogC)의 시간이 총 문자열의 개수만큼 걸린다.
	- 따라서 시간 복잡도는 O(N * K * logK)이다.

실행 시간
	- 0ms(C++)
*/

#include <iostream>
#include <string>
#include <map>

using namespace std;

int n, k;
string tmp[15];

struct TrieNode
{
	map<string, TrieNode*> children;

	void Insert(int idx)
	{
		if (idx == k)
		{
			return;
		}

		auto iter = children.find(tmp[idx]);

		if (iter == children.end())
		{
			iter = children.emplace(tmp[idx], new TrieNode()).first;
		}

		iter->second->Insert(idx + 1);
	}

	void PrintAll(int depth)
	{
		string floor(2 * depth, '-');

		for (const auto& child : children)
		{
			cout << floor << child.first << '\n';
			child.second->PrintAll(depth + 1);
		}
	}
};

TrieNode root;

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;

	for (int i = 0; i < n; ++i)
	{
		cin >> k;

		for (int j = 0; j < k; ++j)
		{
			cin >> tmp[j];
		}

		root.Insert(0);
	}

	root.PrintAll(0);
}
