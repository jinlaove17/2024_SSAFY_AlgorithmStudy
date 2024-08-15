#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int MAX_VERTEX = 10'002;

int v, e, a, b;
int parents[MAX_VERTEX];
vector<int> adjList[MAX_VERTEX];

int GetSameParent();
int BFS(int st);

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int T = 0;

    cin >> T;

    for (int test_case = 1; test_case <= T; ++test_case)
    {
        cin >> v >> e >> a >> b;

        for (int i = 1; i <= v; ++i)
        {
            parents[i] = 0;
            adjList[i].clear();
        }

        for (int i = 0; i < e; ++i)
        {
            int parent = 0, child = 0;

            cin >> parent >> child;
            parents[child] = parent;
            adjList[parent].push_back(child);
        }

        int sameParent = GetSameParent();

        cout << '#' << test_case << ' ' << sameParent << ' ' << BFS(sameParent) << '\n';
    }
}

int GetSameParent()
{
    int root = 1, cur = a;
    bool isParent[MAX_VERTEX] = {};

    while (cur != root)
    {
        isParent[cur] = true;
        cur = parents[cur];
    }

    cur = b;

    while (cur != root)
    {
        if (isParent[cur])
        {
            break;
        }

        cur = parents[cur];
    }

    return cur;
}

int BFS(int st)
{
    // 간선이 부모에서 자식으로만 향하므로 isVisited로 이미 처리한 정점을 기록할 필요가 없다.(한 방향으로만 흐름)
    int ret = 0;
    queue<int> q;

    q.push(st);

    while (!q.empty())
    {
        int cur = q.front();

        q.pop();
        ++ret;

        for (int child : adjList[cur])
        {
            q.push(child);
        }
    }

    return ret;
}
