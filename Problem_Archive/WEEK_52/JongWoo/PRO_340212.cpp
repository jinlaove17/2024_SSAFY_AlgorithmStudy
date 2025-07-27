/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 차근차근 읽어보면 게임의 로직 공식은 간단하게 작성할 수 있으며, limit의 최댓값이 10^15라는 점을 미루어 보아
	  이분 탐색으로 해결할 수 있겠다는 생각이 들었다.
	  문제에서 요구하는 숙련도의 최솟값을 대상으로 두고 이분 탐색을 진행하며 게임 공식을 적용해 값을 확인해 가면서
	  합계가 limit 이하인지에 따라 값을 갱신하고 포인터 값을 조정해 나가면 답을 도출할 수 있다.

시간 복잡도
	- 1 ~ limit 범위를 이분 탐색하기 때문에 매번 O(NlogN)의 시간 복잡도를 갖는다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 27.86ms
*/

#include <vector>

using namespace std;

int solution(vector<int> diffs, vector<int> times, long long limit)
{
    long answer = 0;
    int n = static_cast<int>(diffs.size());
    
    long long st = 1, en = limit;
    while (st <= en)
    {
        long long md = (st + en) / 2;
        long long sum = 0;
        
        for (int i = 0; i < n; ++i)
        {
            if (md >= diffs[i])
            {
                sum += times[i];
            }
            else
            {
                long long diff = diffs[i] - md;
                sum += diff * (times[i - 1] + times[i]) + times[i];
            }
        }
        
        if (sum <= limit)
        {
            answer = md;
            en = md - 1;
        }
        else
        {
            st = md + 1;
        }
    }
    
    return answer;
}
