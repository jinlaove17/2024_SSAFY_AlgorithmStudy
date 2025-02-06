/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 주어진 paths를 통해 그래프를 인접 리스트 방식으로 표현하고, 출입구와 산봉우리를 O(1)에 판별하기 위해서,
      bool 배열을 2개 선언하여 각각 출입구와 산봉우리인 인덱스에 값을 true로 설정해주었다.
      각 지점에 도달했을 때의 최소 intensity를 minIntensityList라는 배열을 선언해 초기값을 INF로 설정해준 다음,
      1번부터 n번 지점까지 순회하며 출입구인 지점에 대해 minIntensityList를 0으로 만들고 DFS 탐색을 수행하였다.
      매 탐색은 게이트가 아니면서 minIntensity 값이 현재 지점까지 이동하는데의 걸린 intensity의 최댓값보다 크거나
      해당 지점으로 이동하는 가중치보다 큰 경우에만 인접 지점으로 이동할 수 있도록 구현하였다.
      이 과정을 반복하면서 산봉우리에 도착했다면 기존에 저장한 값과 비교해 값을 갱신하여 최솟값을 구해 답을 도출할 수 있었다.

시간 복잡도
	- n개의 지점 중 게이트인 지점에서 dfs 탐색을 수행하여 산봉우리를 만날 때까지 진행하므로 O(N^2)의 시간 복잡도를 갖지만,
      게이트가 아닌 지점에 대해, minIntensityList의 값이 더 큰 경우에만 이동하므로 많은 경우가 생략 될 것이다. 

실행 시간
	- 가장 오래 걸린 테스트 케이스에서 174.85ms
*/

#include <iostream>
#include <vector>

using namespace std;

const int INF = 10'000'001;
const int MAX_SPOT_COUNT = 50'000;

int minIntensityList[MAX_SPOT_COUNT + 1];
vector<pair<int, int>> adjList[MAX_SPOT_COUNT + 1];
bool isGate[MAX_SPOT_COUNT + 1];
bool isSummit[MAX_SPOT_COUNT + 1];

void dfs(int cur, vector<int>& answer);

vector<int> solution(int n, vector<vector<int>> paths, vector<int> gates, vector<int> summits) {
    vector<int> answer(2);

    for (const vector<int>& p : paths)
    {
        int from = p[0];
        int to = p[1];
        int weight = p[2];
        adjList[from].emplace_back(weight, to);
        adjList[to].emplace_back(weight, from);
    }

    for (int g : gates)
    {
        isGate[g] = true;
    }

    for (int s : summits)
    {
        isSummit[s] = true;
    }

    answer[0] = MAX_SPOT_COUNT + 1;
    answer[1] = INF;

    for (int i = 1; i <= n; ++i)
    {
        minIntensityList[i] = INF;
    }

    for (int g : gates)
    {
        minIntensityList[g] = 0;
        dfs(g, answer);
    }

    return answer;
}

void dfs(int cur, vector<int>& answer)
{
    if (minIntensityList[cur] > answer[1])
    {
        return;
    }

    if (isSummit[cur])
    {
        if ((answer[1] > minIntensityList[cur]) || ((answer[0] > cur) && (answer[1] == minIntensityList[cur])))
        {
            answer[0] = cur;
            answer[1] = minIntensityList[cur];
        }

        return;
    }

    for (const auto& p : adjList[cur])
    {
        int weight = p.first;
        int nxt = p.second;

        if ((isGate[nxt]) || (minIntensityList[cur] >= minIntensityList[nxt]) || (weight >= minIntensityList[nxt]))
        {
            continue;
        }

        minIntensityList[nxt] = max(minIntensityList[cur], weight);
        dfs(nxt, answer);
    }
}
