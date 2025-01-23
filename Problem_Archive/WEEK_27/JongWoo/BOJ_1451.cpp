/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 그리드 영역 내에서 합의 곱의 최댓값을 구하는 문제이기 때문에 누적합을 떠올릴 수 있었다.
	  그 다음에는 큰 직사각형을 부분 직사각형 3개로 나눌 수 있는 경우를 따져봤는데 총 6개의 케이스가 나온다는 사실을 깨달았다.
	  각 케이스는 2중 for문으로 영역을 나눠 구할 수 있었기 때문에 6개의 케이스에 대한 연산을 해보면 답을 도출할 수 있다.
	  주의할 점은 인덱스를 실수하기 쉽고, 합의 곱 값이 int의 범위를 벗어날 수 있다는 것이다.

시간 복잡도
	- 6개의 케이스에 대해 n * m번의 연산을 수행하므로 O(n * m)의 시간 복잡도를 갖는다.

실행 시간
	- 0ms
*/

#include <iostream>

using namespace std;

const int MAX_SIZE = 50;

int n, m;
int sum[MAX_SIZE + 1][MAX_SIZE + 1];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int r = 1; r <= n; ++r)
	{
		for (int c = 1; c <= m; ++c)
		{
			char num = 0;
			cin >> num;
			sum[r][c] = sum[r - 1][c] + sum[r][c - 1] - sum[r - 1][c - 1] + (num - '0');
		}
	}

	long long answer = 0;

	for (int i = 1; i <= m - 2; ++i)
	{
		for (int j = i + 1; j <= m - 1; ++j)
		{
			int a = sum[n][i];
			int b = sum[n][j] - sum[n][i];
			int c = sum[n][m] - sum[n][j];
			answer = max(answer, static_cast<long long>(a) * b * c);
		}
	}

	for (int i = 1; i <= n - 2; ++i)
	{
		for (int j = i + 1; j <= n - 1; ++j)
		{
			int a = sum[i][m];
			int b = sum[j][m] - sum[i][m];
			int c = sum[n][m] - sum[j][m];
			answer = max(answer, static_cast<long long>(a) * b * c);
		}
	}

	for (int i = 1; i <= n - 1; ++i)
	{
		int a = sum[i][m];

		for (int j = 1; j <= m - 1; ++j)
		{
			int b = sum[n][j] - sum[i][j];
			int c = sum[n][m] - sum[n][j] - sum[i][m] + sum[i][j];
			answer = max(answer, static_cast<long long>(a) * b * c);
		}
	}

	for (int i = 1; i <= n - 1; ++i)
	{
		int a = sum[n][m] - sum[i][m];

		for (int j = 1; j <= m - 1; ++j)
		{
			int b = sum[i][j];
			int c = sum[i][m] - sum[i][j];
			answer = max(answer, static_cast<long long>(a) * b * c);
		}
	}

	for (int i = 1; i <= m - 1; ++i)
	{
		int a = sum[n][m] - sum[n][i];

		for (int j = 1; j <= n - 1; ++j)
		{
			int b = sum[j][i];
			int c = sum[n][i] - sum[j][i];
			answer = max(answer, static_cast<long long>(a) * b * c);
		}
	}

	for (int i = 1; i <= m - 1; ++i)
	{
		int a = sum[n][i];

		for (int j = 1; j <= n - 1; ++j)
		{
			int b = sum[j][m] - sum[j][i];
			int c = sum[n][m] - sum[n][i] - sum[j][m] + sum[j][i];
			answer = max(answer, static_cast<long long>(a) * b * c);
		}
	}

	cout << answer << '\n';
}
