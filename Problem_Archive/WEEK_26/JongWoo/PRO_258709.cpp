/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 제일 처음에 어떤 주사위를 선택할지에 대해 N이 최대 10밖에 안되므로 비트마스킹을 통해 5개, 5개를 나눠야겠다는 생각이 들었다.
      그 다음에는 선별된 주사위로 나올 수 있는 합들을 구하고, B에 대해서도 마찬가지로 합들을 구해준다.
      이후 하나를 정렬하고, 이분탐색으로 각 원소를 찾아보면 문제에서 원하는 답을 도출할 수 있다.

시간 복잡도
	- 주사위가 최대 10개 주어지므로, 1024(1 << 10)번 시뮬레이션을 시도하며, 이 중 비트열에 1의 개수가 n / 2개인 경우에만 DFS 탐색을 진행하게 된다.
      DFS 탐색은 n / 2가지 주사위에 대해 6면이 나올 수 있으므로 (n / 2)^6번 수행한다.
      두 번의 DFS 탐색을 통해 나올 수 있는 합을 각각 벡터에 저장한 후, 하나의 벡터를 정렬하여 나머지 벡터의 원소에 대해 lower_bound로 이분 탐색을 진행하므로 (N / 2)log(N / 2)의 시간이 걸린다.
      따라서 최종적인 시간 복잡도는 O(N^6)이 되는데, N은 최대 5(10 / 2)이하이므로 시간은 넉넉해 보인다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 124.11ms
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX_DICE_COUNT = 10;

int n;
int aDiceList[MAX_DICE_COUNT / 2];
int bDiceList[MAX_DICE_COUNT / 2];

int countOneBit(int bit);
void dfs(const vector<vector<int>>& dice, int depth, int sum, int* diceList, vector<int>& v);

vector<int> solution(vector<vector<int>> dice)
{
    n = static_cast<int>(dice.size());

    vector<int> answer(n / 2);
    int maxWinCount = -987'654'321;

    for (int brute = 1; brute < (1 << n); ++brute)
    {
        if (countOneBit(brute) != n / 2)
        {
            continue;
        }

        for (int i = 0, j = 0, k = 0; i < n; ++i)
        {
            if (brute & (1 << i))
            {
                aDiceList[j++] = i;
            }
            else
            {
                bDiceList[k++] = i;
            }
        }

        int winCount = 0;
        vector<int> v1, v2;
        dfs(dice, 0, 0, aDiceList, v1);
        dfs(dice, 0, 0, bDiceList, v2);
        sort(v2.begin(), v2.end());

        for (int num : v1)
        {
            winCount += lower_bound(v2.begin(), v2.end(), num) - v2.begin();
        }

        if (winCount > maxWinCount)
        {
            maxWinCount = winCount;

            for (int i = 0, j = 0; i < n / 2; ++i)
            {
                answer[j++] = aDiceList[i] + 1;
            }
        }
    }

    return answer;
}

int countOneBit(int bit)
{
    int cnt = 0;

    for (int i = 0; i < n; ++i)
    {
        if (bit & (1 << i))
        {
            ++cnt;
        }
    }

    return cnt;
}

void dfs(const vector<vector<int>>& dice, int depth, int sum, int* diceList, vector<int>& v)
{
    if (depth == n / 2)
    {
        v.push_back(sum);
        return;
    }

    for (int i = 0; i < 6; ++i)
    {
        dfs(dice, depth + 1, sum + dice[diceList[depth]][i], diceList, v);
    }
}
