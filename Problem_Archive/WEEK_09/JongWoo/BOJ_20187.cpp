/*
문제 접근 아이디어 및 알고리즘 판단 사유
   - 최종적으로 접은 상태이자 가장 작은 크기인 1 x 1에서 h번 위치에 구멍을 뚫는 것을 시작으로 종이를 점차 펼쳐나가는 식으로 해결해야겠다는 생각이 들었다.
   - 왜냐하면 가장 작은 크기에서 종이를 반대로 펼치면 최초에 구멍이 뚫린 위치에 구멍이 난 위치를 파악하기 쉽기 때문이다.
   - 예를 들어, 구멍이 뚫린 위치가 0번이라면, 'U', 'D' 명령어에 대해 종이를 펼쳤을 때 2번이 구멍의 위치가 된다.
   - 또 구멍이 뚫린 위치가 2번이라면, 'L', 'R' 명령에 대해 종이를 펼쳤을 때 3번이 구멍의 위치가 된다.
   - 위와 같은 규칙성을 찾았다면, 기준점의 위치를 갱신하고 종이의 크기를 2배씩 키워가면서 값을 저장해주면 된다.

시간 복잡도
   - 명령어의 횟수: 2 * k번
   - 현재 종이의 크기(점차 커짐: 1 x 1, 1 x 2, ... , n * n): rowSize * colSize번
   - 따라서 O((2 * k) * n^2)의 시간 복잡도를 갖는다.

실행 시간
   - 4ms(C++)

삽질했던 내용이나 코드
   - 처음에 배열의 인덱스와 관련해서 적절한 중앙의 위치를 찾는 것과 펼쳤을 때 어느 위치부터 시작해야 할지에 대해 계산 실수가 있어서 답을 도출하지 못했었다.
*/

#include <iostream>

using namespace std;

const int MAX_SIZE = 2 * (1 << 8) + 1;
const int MAX_COMMAND = 16;

int k, n, h;
int board[MAX_SIZE][MAX_SIZE];
char commands[MAX_COMMAND];

void PrintBoard(int r, int c, int rowSize, int colSize, bool line);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> k;

	for (int i = 0; i < 2 * k; ++i)
	{
		cin >> commands[i];

		// 크기가 1 x 1인 종이에서 역으로 진행할 것이므로, 명령어를 반대로 만든다.
		switch (commands[i])
		{
		case 'U':
			commands[i] = 'D';
			break;
		case 'D':
			commands[i] = 'U';
			break;
		case 'L':
			commands[i] = 'R';
			break;
		case 'R':
			commands[i] = 'L';
			break;
		}
	}

	cin >> h;

	// 한 변의 길이(n)는 2^k이다.
	n = 1;

	for (int i = 0; i < k; ++i)
	{
		n *= 2;
	}

	// board 배열의 정중앙에서 1 x 1 크기의 종이로 시작한다.
	int r = n, c = n;
	int rowSize = 1, colSize = 1;

	// 현재 위치의 h번 위치에 구멍을 뚫는다.
	board[r][c] = h;
	
	// 역순으로 명령어를 실행한다.
	for (int i = 2 * k - 1; i >= 0; --i)
	{
		switch (commands[i])
		{
		case 'U':
			for (int i = 0; i < rowSize; ++i)
			{
				for (int j = 0; j < colSize; ++j)
				{
					switch (board[r + i][c + j])
					{
					case 0:
						board[r - 1 - i][c + j] = 2;
						break;
					case 1:
						board[r - 1 - i][c + j] = 3;
						break;
					case 2:
						board[r - 1 - i][c + j] = 0;
						break;
					case 3:
						board[r - 1 - i][c + j] = 1;
						break;
					}
				}
			}

			r -= rowSize;
			rowSize *= 2;
			break;
		case 'D':
			for (int i = 0; i < rowSize; ++i)
			{
				for (int j = 0; j < colSize; ++j)
				{
					switch (board[r + i][c + j])
					{
					case 0:
						board[r + 2 * rowSize - 1 - i][c + j] = 2;
						break;
					case 1:
						board[r + 2 * rowSize - 1 - i][c + j] = 3;
						break;
					case 2:
						board[r + 2 * rowSize - 1 - i][c + j] = 0;
						break;
					case 3:
						board[r + 2 * rowSize - 1 - i][c + j] = 1;
						break;
					}
				}
			}

			rowSize *= 2;
			break;
		case 'L':
			for (int i = 0; i < rowSize; ++i)
			{
				for (int j = 0; j < colSize; ++j)
				{
					switch (board[r + i][c + j])
					{
					case 0:
						board[r + i][c - 1 - j] = 1;
						break;
					case 1:
						board[r + i][c - 1 - j] = 0;
						break;
					case 2:
						board[r + i][c - 1 - j] = 3;
						break;
					case 3:
						board[r + i][c - 1 - j] = 2;
						break;
					}
				}
			}

			c -= colSize;
			colSize *= 2;
			break;
		case 'R':
			for (int i = 0; i < rowSize; ++i)
			{
				for (int j = 0; j < colSize; ++j)
				{
					switch (board[r + i][c + j])
					{
					case 0:
						board[r + i][c + 2 * colSize - 1 - j] = 1;
						break;
					case 1:
						board[r + i][c + 2 * colSize - 1 - j] = 0;
						break;
					case 2:
						board[r + i][c + 2 * colSize - 1 - j] = 3;
						break;
					case 3:
						board[r + i][c + 2 * colSize - 1 - j] = 2;
						break;
					}
				}
			}

			colSize *= 2;
			break;
		}

		//PrintBoard(r, c, rowSize, colSize, true);
	}

	PrintBoard(r, c, rowSize, colSize, false);
}

void PrintBoard(int r, int c, int rowSize, int colSize, bool line)
{
	if (line)
	{
		cout << "============================================\n";
	}

	for (int i = 0; i < rowSize; ++i)
	{
		for (int j = 0; j < colSize; ++j)
		{
			cout << board[r + i][c + j] << ' ';
		}

		cout << '\n';
	}
}
