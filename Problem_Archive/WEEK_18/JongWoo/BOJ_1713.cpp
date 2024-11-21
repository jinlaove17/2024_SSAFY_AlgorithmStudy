/*
문제 접근 아이디어 및 알고리즘 판단 사유
  - N과 추천 횟수가 각각 20과 1,000으로 크지 않은 값이고 하라는대로 하면 될 것 같은 시뮬레이션 문제라는 생각이 들었다.
    Frame 구조체를 선언하여 현재 사진틀에 속한 학생과 추천 수, 등록 시간 등을 저장하고 추천 번호를 순회하면서
    사진틀이 남았으면 객체를 추가하고, 꽉 찼으면 기존의 값을 정렬하여 마지막에 온 값과 교체하는 식으로 시뮬레이션을 진행하였다.
    위 과정을 거치면 어렵지 않게 답을 도출할 수 있다.
  
시간 복잡도
  - 추천 번호가 n번 주어지고, 매번 frame에 있는 학생인지 확인한 다음 정렬을 수행하므로 약 O(N^2logN)의 시간이 걸린다고 생각한다.
  
실행 시간
  - 0ms(C++)
*/

#include <iostream>
#include <algorithm>

using namespace std;

const int MAX_FRAME_COUNT = 20;

struct Frame
{
	int studentNum;
	int recommandCnt;
	int created;
};

int n, m, used;
Frame frameList[MAX_FRAME_COUNT];

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int t = 0; t < m; ++t)
	{
		int recommandNum = 0;

		cin >> recommandNum;

		bool isRegistered = false;

		for (int j = 0; j < used; ++j)
		{
			// 이미 등록된 학생인 경우
			if (frameList[j].studentNum == recommandNum)
			{
				++frameList[j].recommandCnt;
				isRegistered = true;
				break;
			}
		}

		// 처음 등록한 학생인 경우
		if (!isRegistered)
		{
			if (used < n)
			{
				frameList[used].studentNum = recommandNum;
				frameList[used].recommandCnt = 1;
				frameList[used++].created = t;
			}
			else
			{
				sort(frameList, frameList + used,
					[](const Frame& a, const Frame& b)
					{
						if (a.recommandCnt == b.recommandCnt)
						{
							return a.created > b.created;
						}

						return a.recommandCnt > b.recommandCnt;
					});

				frameList[used - 1].studentNum = recommandNum;
				frameList[used - 1].recommandCnt = 1;
				frameList[used - 1].created = t;
			}
		}
	}

	sort(frameList, frameList + used,
		[](const Frame& a, const Frame& b)
		{
			return a.studentNum < b.studentNum;
		});

	for (int i = 0; i < used; ++i)
	{
		cout << frameList[i].studentNum << ' ';
	}

	cout << '\n';
}
