/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 우선 n, k, req의 크기 모두 크지 않기 때문에 완전 탐색으로 각 상담원의 유형을 설정하고, 시뮬레이션을 통해 따져보면 될 것 같다고 생각했다.
	  상담원의 유형을 설정할 때는 최소 한 유형에 적어도 한 명은 배치되어야 하기 때문에 n - k명의 상담원에 대해 유형을 dfs로 설정해주었다.
	  매 탐색마다 0 ~ 배치되지 않은 상담원 수 만큼 각 유형에 배치하면서 최종적으로 남은 상담원 수가 0이 됐을 때 시뮬레이션을 진행하였다.
	  시뮬레이션은 그리디하게 각 상담원이 처리중인 참가자들을 최소힙으로 관리하여 끝나는 시각을 저장하였고,
	  매번 참가자를 순회하면서 최소힙의 크기가 상담원 수의 크기보다 작다면 끝나는 시간을 그냥 추가하고, 크거나 같다면 현재 참가자의
	  시작시간보다 끝나는 시각이 더 작은 값들을 모두 제거해준다. 그럼에도 꽉차있다면 제일 먼저 끝나는 참가자의 종료시각을 빼주고,
	  그 시각에서 현재 참가자의 소요시간만큼을 더해 큐에 추가해준다.
	  위 과정을 반복하면 답을 도출할 수 있다.

시간 복잡도
	- n - k명의 상담사의 유형을 배분하는데 매 깊이마다 0 ~ 남은 상담사 수만큼 진행하는데 O(N^2)이 걸리고, 참가자를 순회하면서 따져보는데
	  req.size() 만큼에 참가자에 대해 삽입/삭제를 수행하므로 O(NlogN)의 시간이 걸리므로 최종적으로 O(N^2 + NlogN)의 시간이 걸린다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 25.19ms
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

const int INF = 987'654'321;
const int MAX_TYPE_COUNT = 5;

int requestTypeCount[MAX_TYPE_COUNT + 1];
int mentoTypeCount[MAX_TYPE_COUNT + 1];
int selectedTypeCount;

void dfs(int depth, int k, int n, const vector<vector<int>>& reqs, int& answer);
int simulate(int n, const vector<vector<int>>& reqs);

int solution(int k, int n, vector<vector<int>> reqs)
{
    int answer = 0;
    
    for (int i = 1; i <= k; ++i)
    {
        mentoTypeCount[i]++;
    }
    
    answer = INF;
    dfs(1, k, n - k, reqs, answer);
    
    return answer;
}

void dfs(int depth, int k, int n, const vector<vector<int>>& reqs, int& answer)
{
    if (depth == k + 1)
    {
        if (n == 0)
        {
            answer = min(answer, simulate(n, reqs));
        }
        
        return;
    }
    
    for (int cnt = 0; cnt <= n; ++cnt)
    {
        mentoTypeCount[depth] += cnt;
        dfs(depth + 1, k, n - cnt, reqs, answer);
        mentoTypeCount[depth] -= cnt;
    }
}

int simulate(int n, const vector<vector<int>>& reqs)
{
    int totalWaitTime = 0;
    priority_queue<int, vector<int>, greater<int>> minHeap[MAX_TYPE_COUNT + 1];
    
    for (const vector<int>& r : reqs)
    {
        int startTime = r[0];
        int duration = r[1];
        int type = r[2];
        
        // 상담 가능한 멘토가 없는 경우
        if (minHeap[type].size() >= mentoTypeCount[type])
        {
            while ((!minHeap[type].empty()) && (minHeap[type].top() <= startTime))
            {
                minHeap[type].pop();
            }
            
            if (minHeap[type].size() >= mentoTypeCount[type])
            {
                int waitTime = minHeap[type].top() - startTime;
                totalWaitTime += waitTime;
                minHeap[type].push(minHeap[type].top() + duration);
                minHeap[type].pop();
                continue;
            }
        }
        
        minHeap[type].push(startTime + duration);
    }
    
    return totalWaitTime;
}
