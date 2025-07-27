/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- N과 M이 최대 10이고, 문자열의 길이 또한 5로 작은 숫자이기 때문에 완전 탐색을 고려해볼 수 있었다.
	  신이 좋아하는 문자열은 겹칠 수 있기 때문에 한개씩 읽고 탐색을 수행하기보다 가장 긴 문자열의 길이를 저장한 후,
	  모든 문자열을 입력 받은 다음 dfs 탐색을 수행하였다.
	  이때 해쉬 셋을 사용해서 예를 들어 문자열이, abbd면 a, ab, abb, abbd를 추가하여 매 탐색 시,
	  해쉬 셋에 있는 단어인지에 따라 해당 방향으로 탐색을 이어나갈지 중단할지 가지치기를 해주었다.
	  이제 매깊이마다 해쉬 맵을 이용해서 문자열의 개수를 계산해주면 답을 도출할 수 있다.

시간 복잡도
	- N * M 맵을 순회하며, 매깊이마다 8방향으로 탐색을 수행하므로 최악의 경우 O(N * M * 8^5)의 시간이 걸린다.
	  하지만, 여기에 해쉬 셋을 통해 prefix가 없는 방향으로는 탐색을 수행하지 않도록 가지치기를 수행하기 때문에
	  O(N * M * 8^5)보다는 훨씬 적은 시간이 걸릴 것이다.

실행 시간
	- 44ms
*/

#include <iostream>
#include <string>
#include <unordered_set>
#include <unordered_map>

using namespace std;

const int MAX_BOARD_SIZE = 10;
const int MAX_STRING_COUNT = 1'000;
const int DELTA_COUNT = 8;
const int DELTA[DELTA_COUNT][2] = { { -1, -1, }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

int n, m, k;
char board[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1];
string words[MAX_STRING_COUNT];
unordered_set<string> prefixSet;
unordered_map<string, int> countMap;

void dfs(int maxDepth, int r, int c, const string& cur);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m >> k;
	for (int r = 1; r <= n; ++r)
	{
		for (int c = 1; c <= m; ++c)
		{
			cin >> board[r][c];
		}
	}

	int maxLen = 0;

	for (int i = 0; i < k; ++i)
	{
		cin >> words[i];

		string tmp;
		for (char c : words[i])
		{
			tmp.push_back(c);
			prefixSet.insert(tmp);
		}

		maxLen = max(maxLen, static_cast<int>(words[i].length()));
	}

	for (int r = 1; r <= n; ++r)
	{
		for (int c = 1; c <= m; ++c)
		{
			dfs(maxLen, r, c, string(1, board[r][c]));
		}
	}

	for (int i = 0; i < k; ++i)
	{
		cout << countMap[words[i]] << '\n';
	}
}

void dfs(int maxDepth, int r, int c, const string& cur)
{
	countMap[cur]++;

	if (cur.length() == maxDepth)
	{
		return;
	}

	for (int dir = 0; dir < DELTA_COUNT; ++dir)
	{
		int nr = r + DELTA[dir][0];
		int nc = c + DELTA[dir][1];

		if (nr < 1)
		{
			nr = n;
		}
		else if (nr > n)
		{
			nr = 1;
		}

		if (nc < 1)
		{
			nc = m;
		}
		else if (nc > m)
		{
			nc = 1;
		}

		if (prefixSet.find(cur + board[nr][nc]) != prefixSet.end())
		{
			dfs(maxDepth, nr, nc, cur + board[nr][nc]);
		}
	}
}
