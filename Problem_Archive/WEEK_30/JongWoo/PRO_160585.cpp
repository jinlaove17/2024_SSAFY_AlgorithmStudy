/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 맵이 최대 3x3이므로 백트래킹을 이용한 완전 탐색으로 답을 찾아야겠다는 생각이 들었다.
      그래서 사전에 O의 위치와 X의 위치를 벡터에 각각 저장한 후 DFS 탐색에서 번갈아가며 빈 칸에 놓는 방식으로 탐색을 진행하였다.
      게임이 끝났는지 매번 검사하며 최종적으로 깊이가 말의 개수가 될 때까지 진행이 됐다면 가능한 경우로 판단할 수 있다.

시간 복잡도
	- 최악의 경우 O가 5개, X가 4개 존재하므로 O(5! * 4!)의 시간 복잡도를 갖는다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 0.16ms
*/

#include <iostream>
#include <string>
#include <vector>

using namespace std;

const int MAX_BOARD_SIZE = 3;

bool isFound = false;
char curBoard[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
vector<pair<int, int>> oPos, xPos;
int maxDepth;

void dfs(int depth, char turn);
bool checkGameover();

int solution(vector<string> board) {
    for (int i = 0; i < MAX_BOARD_SIZE; ++i)
    {
        for (int j = 0; j < MAX_BOARD_SIZE; ++j)
        {
            switch (board[i][j])
            {
            case 'O':
                oPos.emplace_back(i, j);
                break;
            case 'X':
                xPos.emplace_back(i, j);
                break;
            }

            curBoard[i][j] = '.';
        }
    }

    int diff = oPos.size() - xPos.size();

    if ((0 <= diff) && (diff <= 1))
    {
        maxDepth = oPos.size() + xPos.size();
        dfs(0, 'O');
    }

    return isFound ? 1 : 0;
}

void dfs(int depth, char turn)
{
    if (isFound)
    {
        return;
    }

    if (checkGameover())
    {
        if (depth == maxDepth)
        {
            isFound = true;
        }

        return;
    }

    if (depth == maxDepth)
    {
        isFound = true;
        return;
    }

    switch (turn)
    {
    case 'O':
        for (int i = 0; i < oPos.size(); ++i)
        {
            if (curBoard[oPos[i].first][oPos[i].second] == '.')
            {
                curBoard[oPos[i].first][oPos[i].second] = 'O';
                dfs(depth + 1, 'X');
                curBoard[oPos[i].first][oPos[i].second] = '.';
            }
        }
        break;
    case 'X':
        for (int i = 0; i < xPos.size(); ++i)
        {
            if (curBoard[xPos[i].first][xPos[i].second] == '.')
            {
                curBoard[xPos[i].first][xPos[i].second] = 'X';
                dfs(depth + 1, 'O');
                curBoard[xPos[i].first][xPos[i].second] = '.';
            }
        }
        break;
    }
}

bool checkGameover()
{
    for (int i = 0; i < MAX_BOARD_SIZE; ++i)
    {
        if (curBoard[i][0] == '.')
        {
            continue;
        }

        if ((curBoard[i][0] == curBoard[i][1]) && (curBoard[i][1] == curBoard[i][2]))
        {
            return true;
        }
    }

    for (int i = 0; i < MAX_BOARD_SIZE; ++i)
    {
        if (curBoard[0][i] == '.')
        {
            continue;
        }

        if ((curBoard[0][i] == curBoard[1][i]) && (curBoard[1][i] == curBoard[2][i]))
        {
            return true;
        }
    }

    if (curBoard[1][1] == '.')
    {
        return false;
    }

    if (((curBoard[0][0] == curBoard[1][1]) && (curBoard[1][1] == curBoard[2][2])) ||
        ((curBoard[0][2] == curBoard[1][1]) && (curBoard[1][1] == curBoard[2][0])))
    {
        return true;
    }

    return false;
}
