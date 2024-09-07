#include <iostream>
#include <vector>
#include <cstring>

using namespace std;

const int MAX_BOARD_SIZE = 25 + 1;
const int MAX_BLOCK_TYPE = 7 + 1;
const char BLOCKS[] = { '|', '-', '+', '1', '2', '3', '4' };
const int DELTA[][2] = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
const int UP = 0;
const int DOWN = 1;
const int LEFT = 2;
const int RIGHT = 3;

int w, h;
int board[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
bool isVisited[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
int dirMatrix[MAX_BLOCK_TYPE][4]; // [4]: Prev Direction

int GetInitDirection(const pair<int, int>& pos);
int DFS(int r, int c, int prevDir, pair<int, int>& removedPos);

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    // 연결할 수 없는 방향이 있으므로 우선 모든 값을 -1로 초기화한다.
    memset(dirMatrix, -1, sizeof(dirMatrix));
    dirMatrix[1][UP] = UP; dirMatrix[1][DOWN] = DOWN;
    dirMatrix[2][LEFT] = LEFT; dirMatrix[2][RIGHT] = RIGHT;
    dirMatrix[3][UP] = UP; dirMatrix[3][DOWN] = DOWN; dirMatrix[3][LEFT] = LEFT; dirMatrix[3][RIGHT] = RIGHT;
    dirMatrix[4][UP] = RIGHT; dirMatrix[4][LEFT] = DOWN;
    dirMatrix[5][DOWN] = RIGHT; dirMatrix[5][LEFT] = UP;
    dirMatrix[6][DOWN] = LEFT; dirMatrix[6][RIGHT] = UP;
    dirMatrix[7][RIGHT] = DOWN; dirMatrix[7][UP] = LEFT;
    cin >> h >> w;

    pair<int, int> st;
    int totalBlockCount = 0;

    for (int r = 1; r <= h; ++r)
    {
        for (int c = 1; c <= w; ++c)
        {
            char ch = 0;

            cin >> ch;

            switch (ch)
            {
            case '.': // 빈 칸
                board[r][c] = 0;
                break;
            case '|':
                board[r][c] = 1;
                ++totalBlockCount;
                break;
            case '-':
                board[r][c] = 2;
                ++totalBlockCount;
                break;
            case '+':
                board[r][c] = 3;
                totalBlockCount += 2; // 두 번 방문하게 되므로 2개의 블록으로 친다.
                break;
            case '1':
                board[r][c] = 4;
                ++totalBlockCount;
                break;
            case '2':
                board[r][c] = 5;
                ++totalBlockCount;
                break;
            case '3':
                board[r][c] = 6;
                ++totalBlockCount;
                break;
            case '4':
                board[r][c] = 7;
                ++totalBlockCount;
                break;
            case 'M': // 모스크바(출발지)
                board[r][c] = 8;
                st.first = r;
                st.second = c;
                break;
            case 'Z': // 자그레브(도착지)
                board[r][c] = 9;
                break;
            }
        }
    }

    // 모스크바가 하나의 블록과 인접해 있는 입력만 주어지므로, 인접한 블록을 찾아 초기 방향을 설정한다.
    int initDir = GetInitDirection(st);

    // 사라진 블록의 위치
    pair<int, int> removedPos = { -1, -1 };

    // 우선 사라진 블록을 찾기 위해 이어진 곳까지 DFS 탐색을 수행하고 출력한다.
    DFS(st.first + DELTA[initDir][0], st.second + DELTA[initDir][1], initDir, removedPos);
    cout << removedPos.first << ' ' << removedPos.second << ' ';

    // 이제 사라진 블록의 위치에 7개의 블록을 대입해보고 도착 지점에 도달할 수 있는지 검사한다.
    for (int i = 1; i <= 7; ++i)
    {
        board[removedPos.first][removedPos.second] = i;

        // i = 3일 때, 즉 사라진 블록이 '+' 모양이었다면, 해당 위치를 두 번 방문하게 되어 blockCount가 두 번 증가하므로 2를 빼주고, 그 외의 블록은 한 번 방문하게 되므로 1을 빼준다.
        int blockCount = DFS(st.first + DELTA[initDir][0], st.second + DELTA[initDir][1], initDir, removedPos) - ((i == 3) ? 2 : 1);

        if (blockCount == totalBlockCount)
        {
            cout << BLOCKS[i - 1] << '\n';
            break;
        }
    }
}

int GetInitDirection(const pair<int, int>& pos)
{
    int ret = -1;

    for (int dir = UP; dir <= RIGHT; ++dir)
    {
        int nr = pos.first + DELTA[dir][0];
        int nc = pos.second + DELTA[dir][1];

        if ((nr < 1) || (nr > h) || (nc < 1) || (nc > w))
        {
            continue;
        }

        // 빈 칸이거나, 도착점일 경우 continue
        if ((board[nr][nc] == 0) || (board[nr][nc] == 9))
        {
            continue;
        }

        ret = dir;
        break;
    }

    return ret;
}

int DFS(int r, int c, int prevDir, pair<int, int>& removedPos)
{
    // 자그레브에 도착한 경우
    if (board[r][c] == 9)
    {
        return 0;
    }
    // 사라진 블록을 찾은 경우
    else if (board[r][c] == 0)
    {
        // 초기화가 되지 않은 경우에만, removedPos에 대입한다.
        // 이 조건식이 없으면, 이후 사라진 블록의 위치에 블록을 한 개씩 대입해볼 때, 이상한 경로가 만들어 질 수 있다.
        if ((removedPos.first == -1) && (removedPos.second == -1))
        {
            removedPos.first = r;
            removedPos.second = c;
        }

        return 0;
    }

    int dir = dirMatrix[board[r][c]][prevDir];

    if (dir == -1)
    {
        return 0;
    }

    return 1 + DFS(r + DELTA[dir][0], c + DELTA[dir][1], dir, removedPos);
}
