/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 먼저 모든 정점의 가중치 합이 0이 아니라면, 그 즉시 -1을 반환하면 된다.
      그렇지 않은 경우에는 인접한 두 정점의 가중치를 한쪽은 1을 감소시키고 다른 한쪽은 1을 증가시켜야 하는데 이는 결국 해당 정점의 값을 0으로 만들고 인접 정점으로 이동하는 가중치를 의미한다.
      따라서 진입 차수를 계산해서 차수가 낮은 정점부터, 즉 바깥쪽에 있는 정점부터 가중치를 0으로 만들면서 차수가 높으면서 가중치가 높은 정점으로 모아주어야 한다.
      매 시뮬레이션마다 가중치가 적은 정점이 이동해야 횟수가 더 적게 걸리기 때문에 이를 위해 최소힙을 떠올릴 수 있었다.
      두 가지 조건을 만족시키기 위해, 위상 정렬을 이용하되 <누적 가중치, 인덱스>를 최소힙에 추가하여 매번 인접 정점으로 이동할 때마다 누적된 가중치를 옮겨주고,
      이를 최소힙에 추가하여 매번 가중치가 적은 정점이 선택되도록 진행하면 문제에서 원하는 답을 도출할 수 있다.
      주의할 점은, 양방향 그래프이므로 가중치가 1이 됐을 때 최소힙에 추가해주어야 하며 횟수를 더할 때는 가중치에 절댓값을 해주어야 한다는 것이다.

시간 복잡도
	- 우선순위 큐를 사용하여 위상 정렬을 수행하기 때문에 ElogV의 시간 복잡도를 갖는다.

실행 시간
	- 가장 오래 걸린 테스트 케이스: 272.73ms
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int MAX_VERTEX_COUNT = 300'000;

int v, e;
vector<int> adjList[MAX_VERTEX_COUNT];
long long accumList[MAX_VERTEX_COUNT];
int inDegreeList[MAX_VERTEX_COUNT];

long long topologySort(const vector<int>& a);

long long solution(vector<int> a, vector<vector<int>> edges) {
    long long answer = -1;
    long long sum = 0;
    v = static_cast<int>(a.size());
    for (int i = 0; i < v; ++i)
    {
        sum += a[i];
        accumList[i] = a[i];
    }

    // 모든 정점의 가중치 합이 0인 경우에만 가능하다.
    if (sum == 0)
    {
        e = static_cast<int>(edges.size());
        for (int i = 0; i < e; ++i)
        {
            // 양방향 연결리스트로 그래프를 표현한다.
            int from = edges[i][0];
            int to = edges[i][1];
            adjList[from].push_back(to);
            adjList[to].push_back(from);

            // 진입차수를 계산한다.
            ++inDegreeList[from];
            ++inDegreeList[to];
        }

        answer = topologySort(a);
    }

    return answer;
}

long long topologySort(const vector<int>& a)
{
    long long ret = 0;
    priority_queue<pair<long long, int>, vector<pair<long long, int>>, greater<pair<long long, int>>> minHeap; // <accumulate, index>

    int v = static_cast<int>(a.size());
    for (int i = 0; i < v; ++i)
    {
        if (inDegreeList[i] == 1)
        {
            inDegreeList[i] = 0;
            minHeap.emplace(accumList[i], i);
        }
    }

    while (minHeap.size() > 1)
    {
        long long accum = minHeap.top().first;
        int cur = minHeap.top().second;
        minHeap.pop();

        ret += abs(accum);

        for (int nxt : adjList[cur])
        {
            accumList[nxt] += accumList[cur];

            if (--inDegreeList[nxt] == 1)
            {
                inDegreeList[nxt] = 0;
                minHeap.emplace(accumList[nxt], nxt);
            }
        }
    }

    return ret;
}
