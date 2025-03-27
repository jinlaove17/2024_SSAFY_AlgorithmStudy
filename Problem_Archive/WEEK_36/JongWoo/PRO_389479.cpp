/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 읽고 예시로 주어지는 표를 살펴보니 우선순위 큐를 이용한 구현 or 이분 탐색을 사용해 풀어야 될 것 같다고 생각했다.
      최소힙에는 해당 노드가 반납되는 시간과 추가된 서버의 개수를 받아 추가하고, 매 시간마다 최소힙의 최상단을 살펴보면서
      노드를 제거해주었다. 이 과정을 통해 증설된 서버의 수를 관리하면서 조건을 따져보면 답을 도출할 수 있다.

시간 복잡도
	- 24개의 players 배열을 순회하며 최악의 경우 매번 최소힙에 추가해야 하므로 O(NlogN)의 시간 복잡도를 갖는다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 0.01ms
*/

#include <iostream>
#include <vector>
#include <queue>
#include <cmath>

using namespace std;

int solution(vector<int> players, int m, int k)
{
    int answer = 0;
    int n = static_cast<int>(players.size());
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> minHeap;
    int totalServerCnt = 0;

    for (int t = 0; t < n; ++t)
    {
        if (!minHeap.empty())
        {
            int endTime = minHeap.top().first;
            if (endTime == t)
            {
                int serverCnt = minHeap.top().second;
                totalServerCnt -= serverCnt;
                minHeap.pop();
            }
        }

        if (players[t] < m)
        {
            continue;
        }

        int diff = m * totalServerCnt - players[t];

        // 서버 증설이 필요한 경우
        if (diff < 0)
        {
            int serverCnt = static_cast<int>(static_cast<double>(abs(diff)) / m);
            minHeap.emplace(t + k, serverCnt);
            totalServerCnt += serverCnt;
            answer += serverCnt;
        }
    }

    return answer;
}
