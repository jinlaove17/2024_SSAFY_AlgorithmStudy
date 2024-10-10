/*
  문제 접근
  주어진 추들로 특정 구슬의 무게를 만들 수 있는지 여부를 판별해야 한다
  추 무게와 구슬 무게가 일정한 규칙이 없고, 직접 찾아봐야하므로 dp 테이블을 통해 조합을 판별하고 저장해야 한다는 생각을 떠올렸다.
  dp[i][j]: i번째 추까지 사용했을 때, j라는 무게를 만들 수 있는지 여부를 저장하는 boolean 배열으로 둔다.
  원래라면 dp 테이블에 특정한 값을 저장하는 것이 일반적인데, 이번에는 T/F 만 저장하는 경우를 배웠다.

  알고리즘: dp
  dp 테이블 채우는 방식은 다음과 같다
    1. 현재 추를 사용하지 않는 경우: dp[i][j] = dp[i-1][j]
    2. 현재 추를 더하는 경우: dp[i][j + weight[i]] = true
    3. 현재 추를 빼는 경우: dp[i][abs(j - weight[i])] = true

  실행시간: 0ms
  
*/

#include <iostream>
#include <cmath>
using namespace std;

int N, M;
int weight[31];
int marble[7];
bool dp[31][40001];

void fillDpTable() {
    dp[0][0] = true;
    
    for (int i = 1; i <= N; i++) {
        for (int j = 0; j <= 40000; j++) {
            if (!dp[i-1][j]) continue;
            dp[i][j] = true;
            
            if (abs(j - weight[i]) >= 0) dp[i][abs(j - weight[i])] = true;
            if (j + weight[i] <= 40000) dp[i][j + weight[i]] = true;
        }
    }
}

int main() {
    cin.tie(0);
    cout.tie(0);
    ios_base::sync_with_stdio(0);

    cin >> N;
    for (int i = 1; i <= N; i++) {
        cin >> weight[i];
    }

    cin >> M;
    for (int i = 0; i < M; i++) {
        cin >> marble[i];
    }

    fillDpTable();
    for (int i = 0; i < M; i++) {
        if (dp[N][marble[i]]) cout << "Y ";
        else cout << "N ";
    }
    return 0;
}
