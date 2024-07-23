#include <iostream>
#include <cstring>
#include <vector>
#include <utility>
#include <algorithm>
 
using namespace std;
 
int n, m;
vector<pair<int, int>> adjList[402]; // <가중치, 노드>
bool isVisited[402];
 
void DFS(int depth, int cur, int sum, int& answer);
 
int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    int T = 0;
 
    cin >> T;
 
    for (int test_case = 1; test_case <= T; ++test_case)
    {
        cin >> n >> m;
 
        for (int i = 0; i < m; ++i)
        {
            int s = 0, e = 0, c = 0;
 
            cin >> s >> e >> c;
            adjList[s].emplace_back(c, e);
        }
 
        int answer = 0x3fffffff;
 
        for (int i = 1; i <= n; ++i)
        {
            isVisited[i] = true;
            DFS(0, i, 0, answer);
            memset(isVisited, false, sizeof(isVisited));
        }
 
        cout << '#' << test_case << ' ' << answer << '\n';
 
        for (int i = 1; i <= n; ++i)
        {
            adjList[i].clear();
        }
    }
}
 
void DFS(int depth, int cur, int sum, int& answer)
{
    if (depth == n)
    {
        return;
    }
 
    for (const auto& p : adjList[cur])
    {
        int w = p.first;
        int v = p.second;
 
        if (isVisited[v])
        {
            answer = min(answer, sum + w);
        }
        else
        {
            if (sum + w < answer)
            {
                isVisited[v] = true;
                DFS(depth + 1, v, sum + w, answer);
                isVisited[v] = false;
            }
        }
    }
}
