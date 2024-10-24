/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 어떤 사원이 다른 임의의 사원보다 두 점수가 모두 낮은 경우에만 배제가 되므로, 두 점수 중 하나를 기준으로 내림차순 정렬을 수행한다면,
	  기준으로한 점수의 값을 큰 값부터 갱신하며 진행하기 때문에 점수가 이 값과 같은 경우에는 절대로 배제될 수 없다.

	  이와 같은 방식으로 진행하다가 현재 사원의 점수 합이 완호의 점수 합보다 큰 경우는 완호가 배제되는 경우이므로 그 즉시 -1을 반환해주면 된다.
	  그렇지 않은 경우에는 해당 사원이 현재까지 갱신한 최댓값과 같은지 비교하고, 만약 작은 경우라면 두 번째 점수를 비교해서 이전 최댓값과 비교해준다.
	  이와 같이 진행하여 조건을 만족하는 경우에는 등수(answer)를 1씩 증가시켜 가면서 답을 도출하면 된다.

	  이때, 정렬 조건에서 첫 번째 점수가 같은 경우에는 두 번째 점수를 기준으로 오름차순으로 정렬을 해주어야 하는데 그 이유는
	  예를 들어, 정렬 결과가 (7, 5), (6, 6), (6, 4)인 경우 (7, 5)에서 한 번 카운팅 되고, (6, 6)에서 첫 번째 원소의 값이 작으므로, 두 번째 원소의 값을 비교하여 같으므로 한 번 카운팅 후,
	  첫 번째 값의 최댓값을 6으로 갱신한다.
	  (6, 4)에 도달했을 때 첫 번째 값이 최댓값과 같으므로 한 번 카운팅을 수행하지만, (6, 4)은 (7, 5)보다 모든 값이 작기 때문에 배제 대상이지만 카운팅이 된다.
	  따라서 두 번째 원소는 오름차순으로 정렬하여 (7, 5), (6, 4), (6, 6)으로 설정하면 위 문제를 해결하고 답을 도출할 수 있게 된다.

시간 복잡도
	- 정렬 후 n번 순회하며 조건을 판단하므로, 정렬이 가장 많은 시간을 차지하여 O(nlogn)의 시간 복잡도를 갖는다.

실행 시간
	- 25개 테스트 케이스 중 가장 오래 걸린 테스트 케이스의 실행 시간: 27.47ms(C++)
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int solution(vector<vector<int>> scores) {
    int answer = 1;
    int wanhoScore[] = { scores[0][0], scores[0][1] };
    int wanhoTotalScore = wanhoScore[0] + wanhoScore[1];
    
    sort(scores.begin(), scores.end(), [](const auto& a, const auto& b) {
        if (a[0] == b[0])
        {
            return a[1] < b[1];
        }
        
        return a[0] > b[0];
    });
    
    // for (const auto& s : scores)
    // {
    //     cout << s[0] << ' ' << s[1] << '\n';
    // }
    
    int maxAttitudeScore = scores[0][0];
    int maxColleagueScore = -1;
    
    for (int i = 0; i < scores.size(); ++i)
    {
        if ((scores[i][0] > wanhoScore[0]) && (scores[i][1] > wanhoScore[1]))
        {
            return -1;
        }
        else if (scores[i][0] + scores[i][1] > wanhoTotalScore)
        {
            if (scores[i][1] >= maxColleagueScore)
            {
                maxAttitudeScore = scores[i][0];
                maxColleagueScore = scores[i][1];
                ++answer;
            }
            else if (scores[i][0] == maxAttitudeScore)
            {
                ++answer;
            }
        }
    }
    
    return answer;
}
