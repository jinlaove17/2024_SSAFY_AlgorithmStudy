/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- n * m 크기의 맵을 순회하면서 'X'가 아닌 칸을 시작점으로 bfs 탐색을 수행하며 해당 그룹에 속한 점수를 모두 구하면 된다.
    만약 모든 순회가 끝났을 때 answer 배열이 비어있다면 모든 칸이 'X'인 것이므로 -1을 추가해주고, 그렇지 않다면 오름차순으로 정렬을 수행해준다.
    위 과정을 거치면 답을 도출할 수 있다.

시간 복잡도
	- n * m 크기의 맵을 순회하면서 bfs 탐색을 진행하므로 O(N^2)의 시간이 걸린다.

실행 시간
	- 가장 오래 걸린 테스트 케이스에서 0.74ms
*/

#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

const int MAX_MAP_SIZE = 100;
const int DELTA_COUNT = 4;
const int DELTA[DELTA_COUNT][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

bool isVisited[MAX_MAP_SIZE][MAX_MAP_SIZE];

void bfs(const vector<string>& maps, int n, int m, int sr, int sc, vector<int>& answer);

vector<int> solution(vector<string> maps)
{
    vector<int> answer;
    int n = static_cast<int>(maps.size());
    int m = static_cast<int>(maps[0].size());
    
    for (int r = 0; r < n; ++r)
    {
        for (int c = 0; c < m; ++c)
        {
            if ((maps[r][c] != 'X') && (!isVisited[r][c]))
            {
                bfs(maps, n, m, r, c, answer);
            }
        }
    }
    
    if (answer.empty())
    {
        answer.push_back(-1);
    }
    else
    {
        sort(answer.begin(), answer.end());
    }
    
    return answer;
}

void bfs(const vector<string>& maps, int n, int m, int sr, int sc, vector<int>& answer)
{
    int sum = 0;
    queue<pair<int, int>> q;
    isVisited[sr][sc] = true;
    q.emplace(sr, sc);
    
    while (!q.empty())
    {
        int r = q.front().first;
        int c = q.front().second;
        q.pop();
        sum += maps[r][c] - '0';
        
        for (int dir = 0; dir < DELTA_COUNT; ++dir)
        {
            int nr = r + DELTA[dir][0];
            int nc = c + DELTA[dir][1];
            if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= m))
            {
                continue;
            }
            
            if ((maps[nr][nc] == 'X') || (isVisited[nr][nc]))
            {
                continue;
            }
            
            isVisited[nr][nc] = true;
            q.emplace(nr, nc);
        }
    }
    
    answer.push_back(sum);
}
