/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에 문제를 읽고, 부분 문자열이 연속되어 있지 않아도 A, C, G, T의 조건을 만족하면 가능한 경우라고 착각을 했다.
	  그래서 조합 값을 dp로 메모이제이션 해서 풀어야 하나? 라는 생각이 들었는데 그러기엔 최악의 경우 s가 100만이므로 값이 너무 컸기 때문에
	  문제를 다시 읽어보니 연속된 문자로 구성되어야 했다.
	  연속된 문자열이라는 것을 깨닫고 투 포인터 알고리즘을 떠올릴 수 있었고, 구간의 길이가 정해져 있기 때문에 슬라이딩 윈도우 방식으로 문제를 해결할 수 있었다.
	  while 문 이후 if문에서 cnt == p 조건식을 추가하지 않아서 처음에 틀렸었다.
	  다시금 슬라이딩 윈도우와 투 포인터의 차이점을 몸소 깨달을 수 있어서 좋은 경험이었다.

시간 복잡도
	- dna의 길이 s만큼 순회하며 투포인터 방식으로 순회하므로 2 * s의 시간이 걸린다.
	  따라서 최종적인 시간 복잡도는 O(N)이다.

실행 시간
	- 8ms
*/

#include <iostream>
#include <string>

using namespace std;

const int A = 'A' - 'A';
const int C = 'C' - 'A';
const int G = 'G' - 'A';
const int T = 'T' - 'A';

int s, p;
string dna;
int freq[26];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> s >> p;
	cin >> dna;
	cin >> freq[A] >> freq[C] >> freq[G] >> freq[T];

	int answer = 0;
	int partialFreq[26] = {};
	int cnt = 0;

	for (int st = 0, en = 0; st < s; st++)
	{
		while ((en < s) && (cnt < p))
		{
			partialFreq[dna[en++] - 'A']++;
			cnt++;
		}

		if ((cnt == p) &&
			(partialFreq[A] >= freq[A]) &&
			(partialFreq[C] >= freq[C]) &&
			(partialFreq[G] >= freq[G]) &&
			(partialFreq[T] >= freq[T]))
		{
			++answer;
		}

		partialFreq[dna[st] - 'A']--;
		cnt--;
	}

	cout << answer << '\n';
}
