/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 1번 비행기부터 g가 주어졌을 때 1 ~ g번 게이트 중 하나에 도킹하면서 가장 많은 비행기를 도킹하게 해야한다.
	  뭔가 그리디한 냄새가 나는데, 당연히 먼저오면 값이 큰 g번 게이트부터 도킹해야 이후에 g가 작은 비행기가 도킹을 할 수 있는 확률이 더 높기 때문에
	  매번 g번 게이트부터 1씩 줄여가면서 도킹을 시도하면 답을 구할 수 있을 것 같다.
	  그러나 게이트와 비행기의 개수가 모두 10만개이므로 매 비행기마다 1칸씩 이동하면서 비어있는 게이트를 찾는 것은 시간 초과가 날 것이다.
	  여기까지 고민을 해보니 이전에 풀었던 SWEA 15942. 외계인 침공의 풀이를 유일하게 올린 블로그에서 봤던 풀이를 적용할 수 있지 않을까하는 생각이 들었다.
	  당시에 16반의 대다수가 HSAT이었나? 시험을 보러 가고 태희쌤과 H종우, 이 세 명이서 교실에 남아 이 문제를 고민했던 기억이 난다...
	  어쨋든, 이 블로그에서는 방문체크를 상수시간에 할 수 있는 테크닉을 소개해주는데 Union-Find의 Find 알고리즘에 경로 압축을 적용하여,
	  초기 값은 자신의 인덱스와 동일한 위치로 설정하여 [1, 2, 3, 4, 5]와 같이 설정하고, 5번이 방문됐다면 이전 칸이 가리키는 곳으로 설정한다.
	  즉, [1, 2, 3, 4, 4]가 되는 것이다. 이후 5번이 다시 재방문되면 4번에 체크를 수행하고, 4번은 이전 번호인 3번으로 체크한다. [1, 2, 3, 3, 4]
	  이와 같은 방식으로 번호가 큰 게이트(g)부터 마킹을 수행하면서 가리키는 위치가 처음으로 0이 되었을 때, 더이상 도킹할 수 없는 것으로 판단할 수 있다.
	  이 테크닉을 적용할 수 있는 문제를 가져오다니 민채에게 감사의 박수를 건낸다.

시간 복잡도
	- P개의 비행기에 대해 최대 G번 경로 압축이 일어나므로, O(P * a(G))의 시간 복잡도를 갖는다고 생각한다.

실행 시간
	- 12ms
*/

#include <iostream>

using namespace std;

const int MAX_GATE_COUNT = 100'000;
const int MAX_PLANE_COUNT = 100'000;

int G, P;
int rangeList[MAX_PLANE_COUNT + 1];
int parentList[MAX_GATE_COUNT + 1];

int findPosition(int index);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	cin >> G >> P;

	for (int i = 1; i <= P; ++i)
	{
		cin >> rangeList[i];
	}

	for (int i = 1; i <= G; ++i)
	{
		parentList[i] = i;
	}

	int answer = 0;
	for (int i = 1; i <= P; ++i)
	{
		int pos = findPosition(rangeList[i]);
		if (pos == 0)
		{
			break;
		}

		parentList[pos] = findPosition(pos - 1);
		++answer;
	}

	cout << answer << '\n';
}

int findPosition(int index)
{
	if (index == parentList[index])
	{
		return index;
	}

	return parentList[index] = findPosition(parentList[index]);
}
