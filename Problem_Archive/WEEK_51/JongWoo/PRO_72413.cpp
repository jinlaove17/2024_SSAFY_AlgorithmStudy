/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에는 dfs로 경우를 따져보면서 값을 메모이제이션 하는 방식과 다익스트라를 변형해서 해결해야 되나 하는 생각이 들었는데, 단순히 최단거리만을 구하는 것이 아니었고,
    메모이제이션도 각 지점에 최솟값으로 도착하는게 최종적인 최솟값이 되는 것은 아니었기 때문에 다른 방법을 고민하다가 n이 최대 200이라는 점에서 플로이드 워셜로 해결이 되지 않을까 생각했다.
    모든 정점 간에 최단거리를 구하고, 시작 정점 s에서 다른 정점으로 같이 이동해보면서 그 정점에서 a, b까지의 거리를 더한 값이 최소인 지점을 찾으면 답을 도출할 수 있다.

시간 복잡도
	- 플로이드 워셜 알고리즘을 사용하여 O(N^3)의 시간이 걸린다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 7.27ms
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_SPOT_COUNT = 200;
const int INF = 123'456'789;

int adjMat[MAX_SPOT_COUNT + 1][MAX_SPOT_COUNT + 1];

int solution(int n, int s, int a, int b, vector<vector<int>> fares)
{
    for (int i = 1; i <= MAX_SPOT_COUNT; ++i)
    {
        for (int j = 1; j <= MAX_SPOT_COUNT; ++j)
        {
            if (i == j)
            {
                continue;
            }
            
            adjMat[i][j] = INF;
        }
    }
    
    for (const vector<int> f : fares)
    {
        int from = f[0];
        int to = f[1];
        int dist = f[2];
        adjMat[from][to] = dist;
        adjMat[to][from] = dist;
    }
    
    for (int k = 1; k <= n; ++k)
    {
        for (int i = 1; i <= n; ++i)
        {
            for (int j = 1; j <= n; ++j)
            {
                adjMat[i][j] = min(adjMat[i][j], adjMat[i][k] + adjMat[k][j]);
            }
        }
    }
    
    int answer = adjMat[s][a] + adjMat[s][b];
    
    for (int i = 1; i <= n; ++i)
    {
        answer = min(answer, adjMat[s][i] + adjMat[i][a] + adjMat[i][b]);
    }
    
    return answer;
}
