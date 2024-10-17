/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에 접두사(Prefix)라는 단어를 보고 트라이 알고리즘을 떠올릴 수 있었다.
	  일반적인 트라이 알고리즘에 해시를 사용하여 해결이 가능했다.
	  새로운 노드를 만든 경우는 곧, 다른 닉네임의 접두사가 될 수 없음을 의미하므로 isFound를 true로 바꿔주고 해당 노드를 생성하고 이동한다.
	  매번 isFound가 false일 경우에는 별칭에 문자를 한 개씩 추가해주며 인덱스가 문자열의 길이가 될 때까지 진행해 나간다.
	  최종적으로 해당 닉네임의 해시 값을 1 증가시키고, 만약 2이상이라면 별칭에 숫자를 덧붙이는 방식으로 진행하면 답을 도출할 수 있다.

시간 복잡도
	- 최악의 경우 가입한 유저의 수(N)가 10만이고, 각 닉네임의 길이(L)가 10인 경우 100만 번의 연산을 수행하게 되므로 최종적인 시간 복잡도는 O(L * N)이다.

실행 시간
	- 112ms(C++)
*/

#include <iostream>
#include <string>
#include <unordered_map>

using namespace std;

struct TrieNode;

const int ALPHABET_COUNT = 26;

int n;
TrieNode* root;
string alias;
bool isFound;
unordered_map<string, int> m; // <닉네임, 개수>

struct TrieNode
{
	TrieNode* nextNodes[ALPHABET_COUNT];

	TrieNode()
	{
		for (int i = 0; i < ALPHABET_COUNT; ++i)
		{
			nextNodes[i] = nullptr;
		}
	}

	void Insert(const string& nickname, int cur)
	{
		if (cur == nickname.length())
		{
			if (++m[nickname] >= 2)
			{
				alias += to_string(m[nickname]);
			}

			return;
		}

		if (!isFound)
		{
			alias.push_back(nickname[cur]);
		}

		int idx = nickname[cur] - 'a';

		if (nextNodes[idx] == nullptr)
		{
			nextNodes[idx] = new TrieNode();
			isFound = true;
		}

		nextNodes[idx]->Insert(nickname, cur + 1);
	}
};

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n;
	root = new TrieNode();

	for (int i = 0; i < n; ++i)
	{
		string nickname;

		cin >> nickname;
		root->Insert(nickname, 0);
		cout << alias << '\n';
		alias.clear();
		isFound = false;
	}
}
