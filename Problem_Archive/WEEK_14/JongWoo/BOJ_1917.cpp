/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제는 굉장히 간단하지만, 코드로 어떻게 가능 여부를 판단할지 많은 고민을 했었다.
	  그러다가 10달전에 풀었던 백준 14499번 주사위 굴리기 문제의 풀이 방식을 응용하여, 전개도 위에 정육면체를 올리고, 전개도의 모양대로 굴렸을 때,
	  모든 면이 닿을 수 있는지를 판단하여 가능 여부를 판단하면 되지 않을까? 하는 생각을 했다.

	  초반에는 바닥 면에 닿은 숫자를 기반으로 상, 하, 좌, 우로 굴렸을 때 바닥에 오는 숫자를 6 x 4 행렬에 하드 코딩으로 담았었는데
	  요리조리 구르면 바닥에 오는 숫자는 같지만 앞, 뒤, 좌, 우의 숫자가 바뀔 수 있다는 것을 볼펜을 굴려보면서야 깨닫고 말았다.

	  이대로 다른 사람의 풀이를 참고해서 풀어야되나 생각하던 찰나 정육면체의 각 면에 인덱스를 부여하고, 각 방향으로 회전했을 때 면에 적힌 숫자를 갱신하여 전개도를 모두 탐색했을 때,
	  6면이 한 번씩 바닥에 닿은 경우 가능한 경우로 판단하는 알고리즘을 떠올릴 수 있었다.
	  물론 스터디원의 힌트를 곁들였기 때문에 온전히 스스로 푼 문제라 할 수 없겠다.

시간 복잡도
	- 3개의 테스트 케이스에 대해 매번 주위의 4방향을 살펴보며 6칸을 이동하므로 4^6의 연산이 필요하지만, 이미 방문한 곳을 방문하지 않고, 이동가능한 칸은 6칸 밖에 없으므로
	  굉장히 적은 시간이 걸릴 것으로 생각된다.
	  따라서 최종적인 시간 복잡도는 O(4^6)에 훨씬 못 미칠 것이다.

실행 시간
	- 0ms(C++)
*/

#include <iostream>

using namespace std;

const int MAX_TEST_CASE = 3;
const int MAX_BOARD_SIZE = 6;

const int BACK = 0;
const int FRONT = 1;
const int LEFT = 2;
const int RIGHT = 3;
const int TOP = 4;
const int BOTTOM = 5;

const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

int board[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
int dice[6];
bool check[6];
bool isVisited[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

void DFS(int r, int c);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	for (int tc = 1; tc <= MAX_TEST_CASE; ++tc)
	{
		int sr = -1, sc = -1;

		for (int r = 0; r < MAX_BOARD_SIZE; ++r)
		{
			for (int c = 0; c < MAX_BOARD_SIZE; ++c)
			{
				cin >> board[r][c];

				if (board[r][c] == 1)
				{
					if (sr + sc < 0)
					{
						sr = r;
						sc = c;
					}
				}

				isVisited[r][c] = false;
			}
		}

		for (int i = 0; i < 6; ++i)
		{
			dice[i] = i;
			check[i] = false;
		}

		DFS(sr, sc);

		bool isPossible = true;

		for (int i = 0; i < 6; ++i)
		{
			if (!check[i])
			{
				isPossible = false;
				break;
			}
		}

		cout << ((isPossible) ? "yes\n" : "no\n");
	}
}

void DFS(int r, int c)
{
	isVisited[r][c] = true;
	check[dice[BOTTOM]] = true;

	int tmpDice[6] = {};

	for (int i = 0; i < 6; ++i)
	{
		tmpDice[i] = dice[i];
	}

	for (int dir = 0; dir < DELTA_COUNT; ++dir)
	{
		int nr = r + DELTA[dir][0];
		int nc = c + DELTA[dir][1];

		if ((nr < 0) || (nr >= MAX_BOARD_SIZE) || (nc < 0) || (nc >= MAX_BOARD_SIZE))
		{
			continue;
		}

		if ((board[nr][nc] != 1) || (isVisited[nr][nc]))
		{
			continue;
		}

		switch (dir)
		{
		case BACK:
			dice[BOTTOM] = dice[BACK];
			dice[BACK] = dice[TOP];
			dice[TOP] = dice[FRONT];
			dice[FRONT] = tmpDice[BOTTOM];
			break;
		case FRONT:
			dice[BOTTOM] = dice[FRONT];
			dice[FRONT] = dice[TOP];
			dice[TOP] = dice[BACK];
			dice[BACK] = tmpDice[BOTTOM];
			break;
		case LEFT:
			dice[BOTTOM] = dice[LEFT];
			dice[LEFT] = dice[TOP];
			dice[TOP] = dice[RIGHT];
			dice[RIGHT] = tmpDice[BOTTOM];
			break;
		case RIGHT:
			dice[BOTTOM] = dice[RIGHT];
			dice[RIGHT] = dice[TOP];
			dice[TOP] = dice[LEFT];
			dice[LEFT] = tmpDice[BOTTOM];
			break;
		}

		DFS(nr, nc);

		for (int i = 0; i < 6; ++i)
		{
			dice[i] = tmpDice[i];
		}
	}
}
