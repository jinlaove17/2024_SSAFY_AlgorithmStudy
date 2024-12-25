/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 주어지는 조건이 5개로 고려할 것이 많아 보이지만, 구현 상에 크게 어려운 점은 없다.
      단 매번 충돌체크를 할 때는 같은 칸에 도착한 로봇이 여러 대여도 1회만 카운트 해야한다는 점과, 이미 모든 포인트를 순회한 로봇을 제외하는 것,
      그리고 목표 포인트와 현재 위치를 이용해 이동량을 어떻게 계산해야 하는지 등의 고민이 필요했던 문제였다.

시간 복잡도
    - 최악의 경우 100대의 로봇이 100 x 100 크기의 맵에서 양 끝(ex: (1, 1)->(100, 100))으로 이동하는 경로가 최대 100개 주어지는 경우이다.
      이 경우에는 매번 시뮬레이션을 통해 100대의 충돌 체크 + 한 칸의 이동을 거쳐 약 200번의 이동을 100번 해야한다.
      따라서 O(M * X)의 시간 복잡도를 갖는다.

실행 시간
    - 가장 오래 걸린 테스트 케이스에서 18.72ms

삽질했던 내용
    - 처음에 최단 경로라는 단어와 r좌표의 이동이 c좌표의 이동보다 우선순위가 높다는 것만 보고, BFS를 써서 풀어야겠다는 생각이 들었다.
      그래서 BFS 탐색으로 사전에 routes에 있는 포인트 간의 좌표를 모두 구한 다음, 이 좌표대로 각 로봇을 움직이도록 구현하여 문제를 해결하였는데,
      마지막 포인트에 도달했을 때, 한 턴을 더 머물러야 해서 out of index가 나지 않도록 기존 로직을 바꿔야 하는데 이 과정을 구현하는 것이 지저분했다.
      추후, 2차원에서 이동의 우선순위가 정해졌을 때는 현재 위치와 목표 포인트 간의 r차이가 0이 될 때까지 r로만 이동하고 그 다음 c로만 이동하면 된다는 것을
      깨우치고 BFS가 아닌 좌표 값만 바꿔주는 방식으로 이동을 구현하여 문제를 다시 풀었다.
*/

#include <iostream>
#include <vector>
#include <cstring>

using namespace std;

const int MAX_ROBOT_COUNT = 100;
const int MAX_BOARD_SIZE = 100;

struct Robot
{
    bool isCompleted;
    int r;
    int c;
    int routeIndex;
};

int n, x, m;
Robot robotList[MAX_ROBOT_COUNT];
int visitCountList[MAX_BOARD_SIZE + 1][MAX_BOARD_SIZE + 1];

int solution(vector<vector<int>> points, vector<vector<int>> routes)
{
    int answer = 0;

    n = static_cast<int>(points.size());
    x = static_cast<int>(routes.size());
    m = static_cast<int>(routes[0].size());

    for (int i = 0; i < x; ++i)
    {
        int targetPointIndex = routes[i][0] - 1;

        robotList[i].isCompleted = false;
        robotList[i].r = points[targetPointIndex][0];
        robotList[i].c = points[targetPointIndex][1];
        robotList[i].routeIndex = 0;
    }

    int completedCount = 0;
    while (completedCount < x)
    {
        // 충돌 여부 체크
        for (int i = 0; i < x; ++i)
        {
            if (robotList[i].isCompleted)
            {
                continue;
            }

            if (++visitCountList[robotList[i].r][robotList[i].c] == 2)
            {
                ++answer;
            }
        }

        // 로봇 이동
        for (int i = 0; i < x; ++i)
        {
            if (robotList[i].isCompleted)
            {
                continue;
            }

            int targetPointIndex = routes[i][robotList[i].routeIndex] - 1;
            int tr = points[targetPointIndex][0];
            int tc = points[targetPointIndex][1];

            // 목표 지점에 도착한 경우
            if ((robotList[i].r == tr) && (robotList[i].c == tc))
            {
                if (++robotList[i].routeIndex == m)
                {
                    robotList[i].isCompleted = true;
                    ++completedCount;
                    continue;
                }
                else
                {
                    targetPointIndex = routes[i][robotList[i].routeIndex] - 1;
                    tr = points[targetPointIndex][0];
                    tc = points[targetPointIndex][1];
                }
            }

            //  r 좌표가 변하는 이동을 c 좌표가 변하는 이동보다 먼저한다.
            int dr = tr - robotList[i].r;
            if (dr != 0)
            {
                robotList[i].r += dr / abs(dr);
                continue;
            }

            int dc = tc - robotList[i].c;
            if (dc != 0)
            {
                robotList[i].c += dc / abs(dc);
                continue;
            }
        }

        memset(visitCountList, 0, sizeof(visitCountList));
    }

    return answer;
}
