/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 2종류의 펄스 수열을 적용한 배열을 만든 다음, 각 배열에 대해 누적합을 계산하였다.
	  누적합을 계산하는 과정은 동시에 0번째 원소를 포함했을 때의 부분 수열의 합이 되므로 answer를 같이 갱신해 주어야 한다.
	  또한, 원소를 1개만 택하는 경우도 부분 수열의 합이 될 수 있기 때문에 이 값 또한 answer와 비교하여 갱신해 주어야 한다.
	  펄스 수열을 적용한 배열의 누적합이 완성되었다면 누적합이 가장 작은 부분과 가장 큰 부분의 차가 곧 최대 값이 된다.
	  따라서 가장 작은 값을 만났으면 인덱스만 갱신한 후 continue로 넘어가면 되고, 그 외의 경우에는 answer를 갱신해준다.
	  위 과정을 거치면 답을 도출할 수 있다.

시간 복잡도
	- n개의 원소를 순회하며 누적합을 계산하고, 각 펄스 수열을 곱한 크기가 n인 배열을 순회하며 최댓값을 구하므로
	  3 * n번의 연산이 수행된다.
	  따라서 O(n)의 시간 복잡도를 갖는다고 생각하였다.

실행 시간
	- 시간이 가장 오래 걸리는 20번 케이스에 대해 9.33ms(C++)
*/

#include <iostream>
#include <vector>

using namespace std;

const int MAX_COUNT = 500'000;

int minusPulse[MAX_COUNT + 1];
int plusPulse[MAX_COUNT + 1];

long long minusPulseAccum[MAX_COUNT + 1];
long long plusPulseAccum[MAX_COUNT + 1];

long long solution(vector<int> sequence)
{
    long long answer = 0;
    int n = static_cast<int>(sequence.size());

    minusPulseAccum[0] = minusPulse[0] = -sequence[0];
    plusPulseAccum[0] =  plusPulse[0] = sequence[0];
    answer = max(minusPulse[0], plusPulse[0]);
    
    for (int i = 1; i < n; ++i)
    {
        if (i & 1)
        {
            minusPulse[i] = sequence[i];
            plusPulse[i] = -sequence[i];
        }
        else
        {
            minusPulse[i] = -sequence[i];
            plusPulse[i] = sequence[i];
        }
        
        minusPulseAccum[i] = minusPulseAccum[i - 1] + minusPulse[i];
        plusPulseAccum[i] = plusPulseAccum[i - 1] + plusPulse[i];
        answer = max(answer, (long long)max(minusPulse[i], plusPulse[i]));
        answer = max(answer, max(minusPulseAccum[i], plusPulseAccum[i]));
    }
    
    int minMinusPulseAccumIdx = 0;

    for (int i = 1; i < n; ++i)
    {
        if (minusPulseAccum[i] < minusPulseAccum[minMinusPulseAccumIdx])
        {
            minMinusPulseAccumIdx = i;
            continue;
        }
        
        answer = max(answer, minusPulseAccum[i] - minusPulseAccum[minMinusPulseAccumIdx]);
    }
    
    int minPlusPulseAccumIdx = 0;
    
    for (int i = 1; i < n; ++i)
    {
        if (plusPulseAccum[i] < plusPulseAccum[minPlusPulseAccumIdx])
        {
            minPlusPulseAccumIdx = i;
            continue;
        }
        
        answer = max(answer, plusPulseAccum[i] - plusPulseAccum[minPlusPulseAccumIdx]);
    }
    
    return answer;
}
