#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;
using ll = long long;
int N, M;

/*
  문제 접근 방법
  주어진 각 반의 학생들의 힘을 정렬하여, 각 반에서 최소의 학생을 선택하면서 가능한 최소 최대-최소 차이 찾기
  각 반의 학생 힘 배열에서 가장 작은 값부터 순차적으로 우선순위 큐에 추가하면서 최소-최대 차이를 최소화
  
  코드 설명
  power 벡터에 각 반의 학생들의 힘을 입력받아 오름차순으로 정렬
  minDiff는 최소 차이를 저장하는 변수로, 1e9+1의 큰 값으로 초기화
  idx 배열은 각 반에서 현재 선택된 학생의 인덱스를 추적하여 각 반의 다음 학생 표시
  우선순위 큐 pq에는 현재 각 반에서 가장 작은 힘을 가진 학생의 값과 그 반의 인덱스를 저장
  
  큐에서 가장 작은 힘 값(curMin)을 선택하고 curMax와의 차이를 계산해 minDiff를 갱신
  이후 현재 최소 power 값의 반의 다음 학생을 큐에 추가하면서 curMax를 갱신하고, 이를 반복하여 최소 차이를 찾기

  알고리즘: 정렬, 우선순위 큐
*/

int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cin >> N >> M;

    vector<vector<int>> power(N, vector<int>(M));
    
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            cin >> power[i][j];
        }
        sort(power[i].begin(), power[i].end());
    }

    int minDiff = 1e9+1;
    int curMax = 0;

    vector<int> idx(N, 0); // idx 추적
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;

    for (int i = 0; i < N; ++i) {
        pq.push({power[i][0], i});
        curMax = max(curMax, power[i][0]);
    }

    while (true) {
        int curMin = pq.top().first;
        int curDiff = curMax - curMin;
        minDiff = min(minDiff, curDiff);

        // 가장 작은 값의 반 선택 후 다음 이동
        int minClass = pq.top().second;
        pq.pop();
        idx[minClass]++;

        // 선택한 반에서 모든 학생을 다 사용하면 종료
        if (idx[minClass] == M) break;

        // 새로운 학생 power를 우선순위 큐에 추가하고 curMax 갱신
        pq.push({power[minClass][idx[minClass]], minClass});
        curMax = max(curMax, power[minClass][idx[minClass]]);
    }

    cout << minDiff << '\n';
    return 0;
}
