#include <iostream>
#include <vector>
#include <unordered_set>
#include <queue>

using namespace std;

const int MAX_WIDTH = 500;
const int MAX_HEIGHT = 500;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

int n, m;
unordered_set<int> oils[MAX_WIDTH];
int oilSizes[MAX_HEIGHT * MAX_WIDTH];
bool isVisited[MAX_HEIGHT][MAX_WIDTH];

void bfs(int oilIndex, int sr, int sc, const vector<vector<int>>& land);

int solution(vector<vector<int>> land)
{
    int answer = 0;
    
    n = static_cast<int>(land.size());
    m = static_cast<int>(land[0].size());
    
    int oilIndex = 0;
    
    for (int r = 0; r < n; ++r)
    {
        for (int c = 0; c < m; ++c)
        {
            if ((land[r][c] == 0) || (isVisited[r][c]))
            {
                continue;
            }
            
            bfs(oilIndex++, r, c, land);
        }
    }
    
    for (int c = 0; c < MAX_WIDTH; ++c)
    {
        int sum = 0;
        
        for (int o : oils[c])
        {
            sum += oilSizes[o];
        }
        
        answer = max(answer, sum);
    }
    
    return answer;
}

void bfs(int oilIndex, int sr, int sc, const vector<vector<int>>& land)
{
    queue<pair<int, int>> q;
    isVisited[sr][sc] = true;
    q.emplace(sr, sc);
    oils[sc].insert(oilIndex);
    
    while (!q.empty())
    {
        int r = q.front().first;
        int c = q.front().second;
        q.pop();
        ++oilSizes[oilIndex];
        
        for (int dir = 0; dir < DELTA_COUNT; ++dir)
        {
            int nr = r + DELTA[dir][0];
            int nc = c + DELTA[dir][1];
            
            if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= m))
            {
                continue;
            }
            
            if ((land[nr][nc] == 0) || (isVisited[nr][nc]))
            {
                continue;
            }
            
            isVisited[nr][nc] = true;
            q.emplace(nr, nc);
            oils[nc].insert(oilIndex);
        }
    }
}
