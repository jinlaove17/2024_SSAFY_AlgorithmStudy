/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제가 복잡해 보이지만, 새로 추가된 정점은 진입차수가 0이라는 사실을 깨달으면, 해당 정점과 연결된 정점에서 DFS 탐색을 수행해주면 된다.
      탐색을 계속 하다가 이동할 정점이 없으면 막대 모양 그래프임을 알 수 있고, 진출차수가 2인 정점이라면 8자 모양 그래프임을 알 수 있으며
      그 외에 이미 방문한 정점을 다시 방문하는 경우라면 도넛 모양 그래프임을 판단할 수 있다.
      위 과정을 거치면 답을 도출할 수 있다.

시간 복잡도
	- N개의 정점이 있을 때, DFS 탐색으로 각 정점을 한 번씩 방문하며 그래프의 특징을 파악하므로 O(N)의 시간 복잡도를 갖는다.

실행 시간
	- 가장 많은 시간이 소요된 테스트에서 266.23ms
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_VERTEX_COUNT = 1'000'000;

int maxVertexIndex; // 정점의 가장 큰 번호
int inDegreeList[MAX_VERTEX_COUNT + 1]; // 진입 차수
vector<int> adjList[MAX_VERTEX_COUNT + 1]; // 인접 리스트
bool isVisited[MAX_VERTEX_COUNT + 1]; // 방문 체크 여부

void dfs(int cur, vector<int>& answer);

vector<int> solution(vector<vector<int>> edges)
{
    vector<int> answer(4, 0);

    for (const vector<int> e : edges)
    {
        int from = e[0];
        int to = e[1];
        adjList[from].push_back(to);
        ++inDegreeList[to];
        maxVertexIndex = max(maxVertexIndex, max(from, to));
    }

    // 생성된 정점은 진입 차수가 0이면서, 진출 차수가 2이상(그래프의 수의 합은 2이상이므로)이다.
    for (int i = 1; i <= maxVertexIndex; ++i)
    {
        int outDegree = static_cast<int>(adjList[i].size());
        if ((outDegree >= 2) && (inDegreeList[i] == 0))
        {
            answer[0] = i;
            break;
        }
    }

    for (int nxt : adjList[answer[0]])
    {
        isVisited[nxt] = true;
        dfs(nxt, answer);
    }

    return answer;
}

void dfs(int cur, vector<int>& answer)
{
    int outDegree = static_cast<int>(adjList[cur].size());

    // 막대 모양 그래프의 경우, 진출 차수가 0인 정점이 존재한다.
    if (outDegree == 0)
    {
        ++answer[2];
        return;
    }
    // 8자 모양 그래프인 경우, 진출 차수가 2인 정점이 존재한다.
    else if (outDegree == 2)
    {
        ++answer[3];
        return;
    }

    for (int nxt : adjList[cur])
    {
        if (isVisited[nxt])
        {
            ++answer[1];
            return;
        }

        isVisited[nxt] = true;
        dfs(nxt, answer);
        return;
    }
}
