/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 서로 다른 정점 간의 관계가 주어지고 값을 구해야 하는 것이 저번에 풀었던 칵테일 문제처럼 이 문제도 그래프로 표현하여 풀 수 있지 않을까하는 생각을 하게 되었다.
      처음에는 그리디하게 그래프의 순회가 잘 이루어지면 되지 않을까하는 생각에 가지치기를 동원한 dfs 백트래킹으로 풀이를 해봤는데 시간초과가 났다.
      그러다가 그래프 탐색을 어떻게 해야할지 고민을 엄청하다가 ti를 잘못 입력 받고 있었다는 것을 깨닫게 되었고, 이 값을 이용하여 영향력이 적은 정점부터 순회하며
      연쇄적으로 처리할 수 있지 않을까 하는 생각에 위상정렬 알고리즘을 떠올릴 수 있었다.
      작년 등수를 기준으로 등수가 낮았던 정점에서 높은 정점 방향으로 간선을 만들고, 진입차수를 계산한 후 위상정렬을 사용하여 각 정점을 순회할 때,
      등수의 변화량을 계산해주었다. 최종적으로 작년 등수 + 변화량을 map에 추가하여 중복값을 걸러내고, 최종적으로 map의 size가 정점의 수와 같다면 인덱스를 출력하는 방식으로
      답을 도출할 수 있었다. 근데 문제를 풀고보니 size가 정점의 수와 다를 때 IMPOSSIBLE을 출력했는데 ?는 깜빡했음에도 통과돼서 어리둥절했다.

시간 복잡도
	- 각 테스트케이스마다 간선이 있는 정점을 최대 N개 순회하면서 위상정렬을 수행한다.
      위상정렬은 V개의 정점을 순회하는데 각 정점마다 E개의 인접 간선을 확인하므로 O(V + E)의 시간복잡도를 갖는다.
      위상정렬이 끝나면, N개의 정점에 대해 작년의 순위에 변화량을 더한 값을 맵에 추가하여 중복을 검사한다. 이때는 NlogN의 시간이 걸린다.
      따라서 최종적인 시간 복잡도는 O(NlogN)이다.

실행 시간
	- 12ms

삽질했던 내용
    - 문제를 꼼꼼히 읽지 않아서 ti가 '작년에 i등을 한 팀의 번호'인데 '1번 팀의 작년 등수'로 입력을 받아 시도했었다.
*/

#include <iostream>
#include <vector>
#include <queue>
#include <map>

using namespace std;

const int MAX_TEAM_COUNT = 500;

int n, m;
int lastYearRankList[MAX_TEAM_COUNT + 1]; // lastYearRankList[i]: i번째 팀의 작년 등수
vector<int> adjList[MAX_TEAM_COUNT + 1];
int inDegreeList[MAX_TEAM_COUNT + 1];
int shiftList[MAX_TEAM_COUNT + 1];

void TopologySort();

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int t = 0;
    cin >> t;

    for (int tc = 1; tc <= t; ++tc)
    {
        cin >> n;

        for (int i = 1; i <= n; ++i)
        {
            int num = 0;
            cin >> num;
            lastYearRankList[num] = i;

            adjList[i].clear();
            inDegreeList[i] = 0;
            shiftList[i] = 0;
        }

        cin >> m;

        for (int i = 0; i < m; ++i)
        {
            int a = 0, b = 0;
            cin >> a >> b;

            if (lastYearRankList[a] > lastYearRankList[b])
            {
                adjList[a].push_back(b);
                ++inDegreeList[b];
            }
            else
            {
                adjList[b].push_back(a);
                ++inDegreeList[a];
            }
        }

        TopologySort();
    }
}

void TopologySort()
{
    queue<int> q;

    for (int i = 1; i <= n; ++i)
    {
        if (inDegreeList[i] == 0)
        {
            q.push(i);
        }
    }

    while (!q.empty())
    {
        int cur = q.front();
        q.pop();

        for (int nxt : adjList[cur])
        {
            --shiftList[cur];
            ++shiftList[nxt];

            if (--inDegreeList[nxt] == 0)
            {
                q.push(nxt);
            }
        }
    }

    map<int, int> m; // <Rank, Index>

    for (int i = 1; i <= n; ++i)
    {
        m.emplace(lastYearRankList[i] + shiftList[i], i);
    }

    if (m.size() == n)
    {
        for (const auto& p : m)
        {
            cout << p.second << ' ';
        }
        cout << '\n';
    }
    else
    {
        cout << "IMPOSSIBLE\n";
    }
}
