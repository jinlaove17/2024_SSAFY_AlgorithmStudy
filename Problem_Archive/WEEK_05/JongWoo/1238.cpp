#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int MAX_VERTEX = 102;

vector<int> adjList[MAX_VERTEX];

int BFS(int st);

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int t = 10;

    for (int tc = 1; tc <= t; ++tc)
    {
        for (int i = 1; i < MAX_VERTEX; ++i)
        {
            adjList[i].clear();
        }

        int e = 0, st = 0;

        cin >> e >> st;
        e /= 2;

        for (int i = 0; i < e; ++i)
        {
            int from = 0, to = 0;

            cin >> from >> to;

            bool isFound = false;

            for (int v : adjList[from])
            {
                if (v == to)
                {
                    isFound = true;
                    break;
                }
            }

            if (!isFound)
            {
                adjList[from].push_back(to);
            }
        }

        cout << '#' << tc << ' ' << BFS(st) << '\n';
    }
}

int BFS(int st)
{
    int ret = 0;
    queue<int> q;
    int dist[MAX_VERTEX] = {};

    dist[st] = 1;
    q.push(st);

    while (!q.empty())
    {
        int cur = q.front();

        q.pop();

        for (int nxt : adjList[cur])
        {
            if (dist[nxt] != 0)
            {
                continue;
            }

            dist[nxt] = dist[cur] + 1;
            q.push(nxt);
        }
    }

    for (int i = 1; i < MAX_VERTEX; ++i)
    {
        if (dist[i] == 0)
        {
            continue;
        }

        if (dist[i] >= dist[ret])
        {
            ret = i;
        }
    }

    return ret;
}
