/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 각 반의 학생들을 구조체로 선언해 반과 스탯을 저장하고, 하나의 배열에 저장하여 스탯을 기준으로 오름차순 정렬을 수행하였다.
	  이후, 투 포인터를 적용해 구간 내에 반이 n개 일때, en - 1이 가리키는 최댓값과 st가 가리키는 최솟값의 차를 갱신한다.
	  이와 같이 진행하며 각 반에 속한 학생의 수와 학생이 속한 반의 개수를 갱신해주면 답을 도출할 수 있다.

시간 복잡도
	- n * m명의 학생을 정렬하는데 nlogn의 시간이 걸리며, 투 포인터를 통해 외부에서 m * n번, 모든 경우를 합쳐 내부에서 m * n번을
	  수행하므로 2m * n번 연산이 더 소요된다. 이를 더하면 nlogn + 2 * (m * n)이며, 따라서 O(m * n)의 시간 복잡도를 갖는다.

실행 시간
	- 180ms(C++)
*/

#include <iostream>
#include <algorithm>

using namespace std;

const int INF = 0x3f3f3f3f;
const int MAX_CLASS_COUNT = 1'000;
const int MAX_STUDENT_COUNT = 1'000;

struct Student
{
	int team;
	int stat;
};

int n, m;
int usedTeam[MAX_CLASS_COUNT];
Student students[MAX_CLASS_COUNT * MAX_STUDENT_COUNT];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int c = 0; c < n; ++c)
	{
		for (int i = 0; i < m; ++i)
		{
			students[m * c + i].team = c;
			cin >> students[m * c + i].stat;
		}
	}

	sort(students, students + (n * m),
		[](const Student& a, const Student& b) {
			if (a.stat == b.stat)
			{
				return a.team < b.team;
			}

			return a.stat < b.stat;
		});

	int answer = INF;
	int en = 0, tc = 0;

	for (int st = 0; st <= n * m - n; ++st)
	{
		while ((en < n * m) && (tc < n))
		{
			if (++usedTeam[students[en++].team] == 1)
			{
				++tc;
			}
		}

		if (tc == n)
		{
			answer = min(answer, students[en - 1].stat - students[st].stat);

			if (answer == 0)
			{
				break;
			}
		}

		if (--usedTeam[students[st].team] == 0)
		{
			--tc;
		}
	}

	cout << answer << '\n';
}
