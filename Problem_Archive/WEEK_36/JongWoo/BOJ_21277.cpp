/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 첫 번째 퍼즐을 중앙에 깔아두고, 두 번째 퍼즐을 회전해 가면서 맞춰보는 시뮬레이션 과정을 통해 답을 도출할 수 있다.
	  주의할 점은 인덱스가 홀수일 때와 짝수일 때를 고려해야 하며, 배치했을 때 사각형의 min, max 위치 값을 계산하는 것이
	  조금 어려웠던 것 같다.

시간 복잡도
	- 총 4개(0, 90, 180, 270)의 회전을 고려하며 n1 * m1 크기의 첫 번째 퍼즐의 각 칸에서 n2 * m2 크기의 두 번째 퍼즐을
	  맞춰보므로 O(4 * n1 * m1 * m1 * m2), 즉 O(N^4)의 시간 복잡도를 갖는다. 그러나 N이 최대 50이므로 충분히 가능하다.

실행 시간
	- 64ms
*/

#include <iostream>

using namespace std;

const int MAX_BOARD_SIZE = 50;

int n1, m1, n2, m2;
char board1[3 * MAX_BOARD_SIZE][3 * MAX_BOARD_SIZE];
char board2[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

void rotate(int& n, int& m, char board[][MAX_BOARD_SIZE]);
bool check(int sr, int sc);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n1 >> m1;
	for (int r = 3 * MAX_BOARD_SIZE / 2 - n1 / 2, rEnd = r + n1; r < rEnd; ++r)
	{
		for (int c = 3 * MAX_BOARD_SIZE / 2 - m1 / 2, cEnd = c + m1; c < cEnd; ++c)
		{
			cin >> board1[r][c];
		}
	}

	cin >> n2 >> m2;
	for (int r = 0; r < n2; ++r)
	{
		for (int c = 0; c < m2; ++c)
		{
			cin >> board2[r][c];
		}
	}

	int answer = 987'654'321;

	for (int rot = 0; rot < 4; ++rot)
	{
		for (int r = 0, rEnd = 3 * MAX_BOARD_SIZE / 2 + n1 / 2 - ((n1 & 1) ? 0 : 1) + n2; r < rEnd; ++r)
		{
			for (int c = 0, cEnd = 3 * MAX_BOARD_SIZE / 2 + m1 / 2 - ((m1 & 1) ? 0 : 1) + m2; c < cEnd; ++c)
			{
				if (check(r, c))
				{
					int minR = min(r, 3 * MAX_BOARD_SIZE / 2 - n1 / 2);
					int minC = min(c, 3 * MAX_BOARD_SIZE / 2 - m1 / 2);
					int maxR = max(3 * MAX_BOARD_SIZE / 2 + n1 / 2 - ((n1 & 1) ? 0 : 1), r + n2 - 1);
					int maxC = max(3 * MAX_BOARD_SIZE / 2 + m1 / 2 - ((m1 & 1) ? 0 : 1), c + m2 - 1);
					int area = (maxR - minR + 1) * (maxC - minC + 1);

					answer = min(answer, area);
				}
			}
		}

		if (rot < 3)
		{
			rotate(n2, m2, board2);
		}
	}

	cout << answer << '\n';
}

void rotate(int& n, int& m, char board[][MAX_BOARD_SIZE])
{
	char temp[MAX_BOARD_SIZE][MAX_BOARD_SIZE] = {};

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < m; ++c)
		{
			temp[c][n - r - 1] = board[r][c];
			board[r][c] = '\0';
		}
	}

	swap(n, m);

	for (int r = 0; r < n; ++r)
	{
		for (int c = 0; c < m; ++c)
		{
			board[r][c] = temp[r][c];
		}
	}
}

bool check(int sr, int sc)
{
	for (int r = 0; r < n2; ++r)
	{
		for (int c = 0; c < m2; ++c)
		{
			if (board1[sr + r][sc + c] == '1' && board2[r][c] == '1')
			{
				return false;
			}
		}
	}

	return true;
}
