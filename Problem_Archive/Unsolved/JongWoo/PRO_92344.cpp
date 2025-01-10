/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제는 굉장히 간단하지만, 효율성을 통과하는 아이디어를 떠올리는 것이 굉장히 어려웠다.
      누적합을 사용을 하는데 [in, ex) 칸의 값에 degree를 누적하며 최종적으로 행에 대해 한 번, 열에 대해 한 번 누적합을 계산하는 아이디어는 결코 떠올릴 수 없었다.
      이번에 배운 경이로운 알고리즘인 만큼 잘 기억해 두었다가 나중에 다시 풀어봐야겠다.

시간 복잡도
	- skill을 순회하며 전처리를 하는데 최악의 경우 25만번 수행되고, N * M 크기의 보드를 순회하며 2번의 누적합을 계산 후 최종 적인 답을 도출해야 하므로 최악의 경우 300만 번의 순회가 발생한다.
      따라서 시간 복잡도는 O(L + N * M)이 된다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 27.63ms
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_SIZE = 1'000;

int shift[MAX_SIZE + 1][MAX_SIZE + 1];

int solution(vector<vector<int>> board, vector<vector<int>> skill)
{
    int answer = 0;
    int n = static_cast<int>(board.size());
    int m = static_cast<int>(board[0].size());

    for (const vector<int>& s : skill)
    {
        int type = s[0];
        int r1 = s[1], c1 = s[2];
        int r2 = s[3], c2 = s[4];
        int degree = (type == 1) ? -s[5] : s[5];

        shift[r1][c1] += degree;
        shift[r1][c2 + 1] += -degree;
        shift[r2 + 1][c1] += -degree;
        shift[r2 + 1][c2 + 1] += degree;
    }

    for (int r = 0; r < n; ++r)
    {
        for (int c = 1; c < m; ++c)
        {
            shift[r][c] += shift[r][c - 1];
        }
    }

    for (int c = 0; c < m; ++c)
    {
        for (int r = 1; r < n; ++r)
        {
            shift[r][c] += shift[r - 1][c];
        }
    }

    for (int r = 0; r < n; ++r)
    {
        for (int c = 0; c < m; ++c)
        {
            if (board[r][c] + shift[r][c] >= 1)
            {
                ++answer;
            }
        }
    }

    return answer;
}
