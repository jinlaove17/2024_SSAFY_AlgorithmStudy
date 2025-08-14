#include <iostream>
#include <vector>

using namespace std;

const int MAX_SCORE = 10;

int maxDiff;
int lionScore[MAX_SCORE + 1];

void dfs(int maxDepth, int depth, int n, const vector<int>& info, vector<int>& answer);
void calculateWinner(int n, const vector<int>& info, vector<int>& answer);

vector<int> solution(int n, vector<int> info)
{
    vector<int> answer(11);
    
    dfs(MAX_SCORE, 0, n, info, answer);
    if (maxDiff == 0)
    {
        answer.clear();
        answer.push_back(-1);
    }
    
    return answer;
}

void dfs(int maxDepth, int depth, int n, const vector<int>& info, vector<int>& answer)
{
    if (depth == maxDepth)
    {
        calculateWinner(n, info, answer);
        return;
    }
    
    // 현재 점수에서 라이언이 이기는 경우
    if (n >= info[depth] + 1)
    {
        lionScore[MAX_SCORE - depth] = info[depth] + 1;
        dfs(maxDepth, depth + 1, n - (info[depth] + 1), info, answer);
    }
    
    // 현재 점수에서 라이언이 비기거나 지는 경우
    lionScore[MAX_SCORE - depth] = 0;
    dfs(maxDepth, depth + 1, n, info, answer);
}

void calculateWinner(int n, const vector<int>& info, vector<int>& answer)
{
    int apeachSum = 0, lionSum = 0;
    
    for (int i = 0; i < MAX_SCORE; ++i)
    {
        if (lionScore[MAX_SCORE - i] > info[i])
        {
            lionSum += MAX_SCORE - i;
        }
        else if (info[i] > 0)
        {
            apeachSum += MAX_SCORE - i;
        }
    }
    
    int diff = lionSum - apeachSum;
    if ((diff > 0) && (diff >= maxDiff))
    {
        // 남은 화살은 0점에 몰아주기
        lionScore[0] = n;
        
        if (diff == maxDiff)
        {
            for (int i = 0; i <= MAX_SCORE; ++i)
            {
                if (answer[MAX_SCORE - i] > lionScore[i])
                {
                    return;
                }
                else if (answer[MAX_SCORE - i] < lionScore[i])
                {
                    break;
                }
                else if (answer[MAX_SCORE - i] == lionScore[i])
                {
                    continue;
                }
            }   
        }
        
        maxDiff = diff;
        
        for (int i = 0; i <= MAX_SCORE; ++i)
        {
            answer[i] = lionScore[MAX_SCORE - i];
        }
    }
}
