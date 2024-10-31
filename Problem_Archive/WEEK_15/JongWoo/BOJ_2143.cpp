/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에서 주어진 부 배열의 합은 누적합을 사용하여 구현하면 된다고 생각하였다.
	  원소의 개수가 최악의 경우 1,000개이므로 o(N^2) 풀이가 가능하다고 생각하여 누적합을 계산한 후 나올 수 있는 모든 부 배열의 누적합을 구하였다.
	  두 배열에 대해 위 연산을 수행했다면, 하나의 배열을 오름차순으로 정렬한 다음, 나머지 배열의 원소에 목표하는 값 T에서
	  정렬된 배열의 원소 값을 뺀 값이 존재하는지를 판단하여 존재할 경우, 개수만큼 값을 증가시키는 방식으로 구현하였다.

시간 복잡도
	- 2개의 부 배열의 합을 계산하는데 2 * (n + n^2)의 시간이 걸리며, 1개의 배열을 오름차순으로 정렬하고 나머지 배열에서
	  2번의 이분 탐색을 수행하므로 nlogn + 2 * nlogn의 시간이 걸린다.
	  따라서 최종적인 시간 복잡도는 O(n^2)이라고 생각하였다.

실행 시간
	- map을 이용한 풀이: 524ms(C++) -> 정렬을 이용한 풀이: 132ms(C++)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX_SIZE = 1'000;

int t, n, m;
int arrA[MAX_SIZE + 1];
int arrB[MAX_SIZE + 1];
int accumA[MAX_SIZE + 1];
int accumB[MAX_SIZE + 1];

int LowerBound(const vector<int>& v, int val);
int UpperBound(const vector<int>& v, int val);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> t;
	cin >> n;
	cin >> arrA[1];
	accumA[1] = arrA[1];

	vector<int> partAccumA;

	// 길이가 1인 부 배열의 합 추가
	partAccumA.reserve(n * (n + 1) / 2);
	partAccumA.push_back(arrA[1]);

	for (int i = 2; i <= n; ++i)
	{
		cin >> arrA[i];
		accumA[i] = accumA[i - 1] + arrA[i];

		// 길이가 1인 부 배열의 합 추가
		partAccumA.push_back(arrA[i]);
	}

	// 길이가 2 이상인 부 배열의 합 추가
	for (int len = 2; len <= n; ++len)
	{
		for (int startIndex = 0; startIndex + len <= n; ++startIndex)
		{
			partAccumA.push_back(accumA[startIndex + len] - accumA[startIndex]);
		}
	}

	cin >> m;
	cin >> arrB[1];
	accumB[1] = arrB[1];

	vector<int> partAccumB;

	// 길이가 1인 부 배열의 합 추가
	partAccumB.reserve(m * (m + 1) / 2);
	partAccumB.push_back(arrB[1]);

	for (int i = 2; i <= m; ++i)
	{
		cin >> arrB[i];
		accumB[i] = accumB[i - 1] + arrB[i];

		// 길이가 1인 부 배열의 합 추가
		partAccumB.push_back(arrB[i]);
	}

	// 길이가 2 이상인 부 배열의 합 추가
	for (int len = 2; len <= m; ++len)
	{
		for (int startIndex = 0; startIndex + len <= m; ++startIndex)
		{
			partAccumB.push_back(accumB[startIndex + len] - accumB[startIndex]);
		}
	}

	long long answer = 0;

	sort(partAccumA.begin(), partAccumA.end());

	for (int i = 0; i < partAccumB.size(); ++i)
	{
		answer += UpperBound(partAccumA, t - partAccumB[i]) - LowerBound(partAccumA, t - partAccumB[i]);
	}

	cout << answer << '\n';
}

int LowerBound(const vector<int>& v, int val)
{
	int st = 0, en = static_cast<int>(v.size());

	while (st < en)
	{
		int md = (st + en) / 2;

		if (v[md] >= val)
		{
			en = md;
		}
		else
		{
			st = md + 1;
		}
	}

	return st;
}

int UpperBound(const vector<int>& v, int val)
{
	int st = 0, en = static_cast<int>(v.size());

	while (st < en)
	{
		int md = (st + en) / 2;

		if (v[md] <= val)
		{
			st = md + 1;
		}
		else
		{
			en = md;
		}
	}

	return st;
}
