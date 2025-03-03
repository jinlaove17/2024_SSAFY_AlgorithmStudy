/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에 문제를 읽었을 때는 dp 냄새도 조금 났었는데, n이 최대 40이라 완전 탐색을 할 경우, 2^40이라는 불가능한 시간 복잡도임을 알았음에도 가지치기로 될 것 같다는 생각이 들었다.
      그래서 맨 처음에는 b 도둑의 흔적 합을 구해 m보다 작을 경우, 그 즉시 0을 반환하고 그렇지 않으면 dfs 탐색을 통해 경우를 일일이 따져보도록 구현해보았다.
      처음에는 방문체크를 2차원으로 해서 [aSum][bSum]으로 이루어지는 경우는 다시 방문하지 않도록 구현했는데 이렇게 구현하면
      [[2, 0], [0, 2], [4, 4]]와 [[4, 4], [2, 0], [0, 2]]의 경우를 각각 따져볼 수 없게 되므로 깊이를 추가하여 총 3차원으로 방문체크를 구현하게 되었다.
      위 과정을 거쳐 문제에서 원하는 답을 도출할 수 있었다.

시간 복잡도
	- dfs 탐색으로 매번 2개의 경우를 따져보면 최악의 경우 2^40 지수 시간 복잡도가 나온다. 그럼에도 a 도둑의 합계가 이전에 구한 값보다 커지는 경우,
      현재 깊이까지 왔을 때 나머지는 모두 b 도둑이 처리가 가능한 경우, 각 도둑의 흔적을 남겨도 n, m 미만인 경우 등을 따져보면 굉장히 많은 가지치기가 가능해진다.
      또한, isVisited[aSum][bSum][depth] 3차원 배열을 선언하여,  depth 깊이까지 왔을 때, aSum, bSum이 같은 경우는 다시 방문하는 것을 방지하도록 처리하면 시간 복잡도는 문제가 되지 않는다.
      따라서 최종적인 시간 복잡도는 O(2^N)이지만, 시간안에 가능한 알고리즘이라고 할 수 있겠다.

실행 시간
	- 가장 오래걸린 테스트 케이스에서 0.27ms
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX_COUNT = 120;

const int INF = 987'654'321;
bool isVisited[MAX_COUNT + 1][MAX_COUNT + 1][40];

void dfs(const vector<vector<int>>& info, int depth, int n, int m, int aSum, int bSum, int bTotal, int& answer);

int solution(vector<vector<int>> info, int n, int m)
{
    int answer = INF;
    int bTotal = 0;

    for (const vector<int>& i : info)
    {
        bTotal += i[1];
    }

    if (m > bTotal)
    {
        answer = 0;
    }
    else
    {
        isVisited[0][0][0] = true;
        dfs(info, 0, n, m, 0, 0, bTotal, answer);
    }

    if (answer == INF)
    {
        answer = -1;
    }

    return answer;
}

void dfs(const vector<vector<int>>& info, int depth, int n, int m, int aSum, int bSum, int bTotal, int& answer)
{
    if (aSum >= answer)
    {
        return;
    }

    if ((depth == info.size()) || (m > bTotal))
    {
        answer = min(answer, aSum);
        return;
    }

    if ((aSum + info[depth][0] < n) && (!isVisited[aSum + info[depth][0]][bSum][depth]))
    {
        isVisited[aSum + info[depth][0]][bSum][depth] = true;
        dfs(info, depth + 1, n, m, aSum + info[depth][0], bSum, bTotal - info[depth][1], answer);
    }

    if ((bSum + info[depth][1] < m) && (!isVisited[aSum][bSum + info[depth][1]][depth]))
    {
        isVisited[aSum][bSum + info[depth][1]][depth] = true;
        dfs(info, depth + 1, n, m, aSum, bSum + info[depth][1], bTotal, answer);
    }
}
