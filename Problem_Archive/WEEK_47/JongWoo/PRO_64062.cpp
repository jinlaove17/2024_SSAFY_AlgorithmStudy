/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에는 슬라이딩 윈도우 방식으로 k개씩 쪼개서 확인해야겠다는 생각이 들었는데, 어떻게 따져봐야할지 도통 감이 오지 않았다.
	  그래서, 돌의 내구도는 최대 2억이고, 개수는 20만으로 굉장히 큰 값이라는 것에 착안하여 이분 탐색은 어떨지 고민을 해봤다.
	  어짜피 가까운 돌부터 모두 밟아야 하기 때문에 건널 수 있는 프렌드의 수를 기준으로 이분 탐색을 수행하고,
	  연속으로 k개를 건너지 못하는 지점이 나오면 지날 수 있는 프렌드의 수를 줄이는 식으로 문제의 답을 도출할 수 있었다.

시간 복잡도
	- 최대 돌의 내구도인 2억명의 프렌즈가 이동 가능하며, 이를 이분 탐색으로 범위를 좁히면서 n개의 돌에 대해 따져봐야 하므로,
	  내구도를 m이라고 했을 때 O(MlogN)의 시간 복잡도를 갖는다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 4.52ms
*/

#include <iostream>
#include <vector>

using namespace std;

bool check(const vector<int>& stones, int k, int md);

int solution(vector<int> stones, int k) {
    int answer = 0;
    
    int st = 1, en = 0;
    for (int stone : stones)
    {
        en = max(en, stone);
    }
    
    while (st <= en)
    {
        int md = (en - st) / 2 + st;
        if (check(stones, k, md))
        {
            answer = md;
            st = md + 1;
        }
        else
        {
            en = md - 1;
        }
    }
    
    return answer;
}

bool check(const vector<int>& stones, int k, int md)
{
    // 연속으로 건너지 못한 돌의 개수
    int failCount = 0;
    
    for (int stone : stones)
    {
        if (stone < md)
        {
            if (++failCount >= k)
            {
                return false;
            }
        }
        else
        {
            failCount = 0;
        }
    }
    
    return true;
}
