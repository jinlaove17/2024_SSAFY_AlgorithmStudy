/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 보통 회전이 가능한 테트리스 문제가 나오면, 각 블록을 사전에 하드코딩해서 문제를 접근하는데, 이 문제의 경우 블록이 7개나 되고, 칸의 가로길이가 최대 100이기 때문에,
	  일일이 다 해보는 것은 막막하다는 느낌을 받았다.
	  그래서 규칙성을 찾아야겠다는 생각이 들었고, 결국 맞닿는 블록의 위치가 중요하는 것을 깨닫게 되었다.
	  밑면이 모두 맞닿도록 배치했을 때, 각 블록의 최하단 블록의 빈칸을 사전에 정의하였다.
	  예를 들어, ㅡ 모양의 테트리스는 최하단 블록에 빈칸이 없으므로 {0, 0, 0, 0}가 되고, ┤ 모양의 테트리스는 {1, 0}이 되는 것이다.
	  이제 3중 for문으로 회전 -> 테트리스를 놓을 수 있는 위치 -> 쌓인 블록과 비교하며 높이가 차가 빈칸 차와 같은 경우를 카운트하면 답을 도출할 수 있다. 

시간 복잡도
	- 각 블록은 회전했을 때, 나올 수 있는 경우의 수가 최소 1개에서 최대 4개까지 가능하다.
	  최악의 경우로 시간 복잡도를 생각해보면, 4번 회전 * c - 블록의 밑면 길이(놓을 수 있는 위치) * 블록의 밑면 길이(밑면 비교 횟수)이므로
	  4 * (c - l) * l가 되고, O(cl)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_COLUMN_SIZE = 100;

int c, p;
int heightList[MAX_COLUMN_SIZE];
const vector<vector<int>> blockList[7] = {
	{ { 0 }, { 0, 0, 0, 0 }, },
	{ { 0, 0 } },
	{ { 0, 0, 1 }, { 1, 0 } },
	{ { 1, 0, 0 }, { 0, 1 } },
	{ { 0, 0, 0 }, { 0, 1 }, { 1, 0, 1 }, { 1, 0 } },
	{ { 0, 0, 0 }, { 0, 0 }, { 0, 1, 1 }, { 2, 0 } },
	{ { 0, 0, 0 }, { 0, 2 }, { 1, 1, 0 }, { 0, 0 } }
};

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> c >> p;

	for (int i = 0; i < c; ++i)
	{
		cin >> heightList[i];
	}

	int answer = 0;

	for (int rot = 0; rot < blockList[p - 1].size(); ++rot)
	{
		for (int i = 0, en = c - blockList[p - 1][rot].size(); i <= en; ++i)
		{
			bool isPossible = true;

			for (int j = 1; j < blockList[p - 1][rot].size(); ++j)
			{
				if (heightList[i + j] - heightList[i + j - 1] != blockList[p - 1][rot][j] - blockList[p - 1][rot][j - 1])
				{
					isPossible = false;
					break;
				}
			}

			if (isPossible)
			{
				++answer;
			}
		}
	}

	cout << answer << '\n';
}
