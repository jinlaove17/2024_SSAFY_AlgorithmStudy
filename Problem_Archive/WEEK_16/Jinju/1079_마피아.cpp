#include <bits/stdc++.h>
using namespace std;

/*
  문제 접근 과정
  처음에 마피아 유죄 지수를 표현하는 2차원 배열과 유죄 지수 update 하는 부분이
  그래프의 가중치를 추가하는 느낌이 들어서 그래프 탐색느낌으로 머릿속에 그려보았다.
  만약, 참가자 i가 죽는다면 다른 사람의 유죄 지수는 R[i][j] 만큼 더해진다.
  즉, 죽은 사람을 node라고 치면 그 node에 달린 간선의 가중치만큼 유죄 지수가 더해지는 것이다.

  해당 과정을 생각하면서 임의로 몇 사람을 선택하여 유죄 지수를 갱신하는 과정을 뇌내 시뮬레이션 해보았다.
  이 과정을 통해 중요한 판단을 2가지 내릴 수 있었다.
    1) 그래프 최단 경로로 생각하면 안된다.
      -> 유죄 지수를 통해 투표로 죽는 경우(낮)는 유죄 지수가 max인 사람이 죽는 것이다.
      -> 유죄 지수가 크거나 작거나 상관 없이, max 값보다 작거나 같게만 유지하면 되는 것이다. (같을때는 은진이 번호가 물론 작도록)
      -> 결론: 유죄 지수를 최소로 유지하는 그리디적 접근은 옳지 않다.
    2) 유죄 지수를 적어도 max보다는 작거나 같게 유지하는 경우를 탐색하기 위해서는 node 선택 순서도 중요하다.
      -> 즉, 선택 순서도 중요하기 때문에 모든 탐색 경우를 다 따져봐야 한다는 결론을 내렸다.
      -> 이 때, 작은 N의 범위가 브루트포스라는 확신을 주었고 재귀 함수를 통한 전체 탐색으로 풀었다.

  시간복잡도: 최악의 경우 모든 경우를 탐색하나 낮과 밤의 선택 경우가 다르기 때문에 상태 공간 트리가 달라진다.
              이는 N = 16이라는 애매한 숫자에서 확신을 얻을 수 있다. 
              보통 O(N!) 의 풀이면 N이 10이하인 경우가 대부분이다.
              N = 16을 본 순간, 상태 공간 트리를 N!이 아니라 유의미하게 줄이는 방법이 있음을 떠올려야 한다.
              
              밤의 재귀 호출: 남아있는 모든 사람 중 1명을 고르므로 남은 명수만큼 상태 공간 트리의 분기 생성
              낮의 재귀 호출: maxSinner만 죽이면 되므로 상태 공간 트리의 분기 없이 재귀 진행
              -> 낮과 밤이 번갈아가면서 진행되기 때문에 상태 공간 트리가 한정된다.
              -> 최악의 경우 O((N/2)!) = O(8!) 에 근접한다.
              
  알고리즘: 재귀를 통한 전체 경우의 수 탐색(bruteforce)
  실행시간: 280ms (C++)
  
*/

int N, eunjin;
int sins[17];
int R[17][17];
int ret = -1;
bool die[17];
bool flg = false;

void updateSins(int i, int num, int& maxSinnerIdx) {
    int maxSinPoint = -21e8;
    for (int j = 0; j < N; j++) {
        if (die[j]) continue; // 이미 죽은 사람 제외
        sins[j] += R[i][j] * num;

        if (sins[j] > maxSinPoint) {
            maxSinnerIdx = j;
            maxSinPoint = sins[j];
        }
    }
}

void playGame(int left, int turn, int maxSinnerIdx) {
    if (flg) return;

    if (die[eunjin] || left == 1) {
        ret = max(ret, turn);
        if (left == 1 && die[eunjin]) flg = true;
        return;
    }

    if (left % 2 == 0) { // 밤
        for (int i = 0; i < N; i++) {
            if (die[i] || i == eunjin) continue;

            int tmp[17], maxSinnerIdxNext = 0;
            copy(sins, sins + N, tmp); // sins 배열 복사
            die[i] = true; // 선택된 인물 죽음 표시

            updateSins(i, 1, maxSinnerIdxNext);
            playGame(left - 1, turn + 1, maxSinnerIdxNext);
            if (flg) return;

            copy(tmp, tmp + N, sins); // 상태 복구
            die[i] = false;
        }
    }
    else { // 낮
        die[maxSinnerIdx] = true;
        playGame(left - 1, turn, maxSinnerIdx);
        die[maxSinnerIdx] = false;
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(0);

    cin >> N;
    for (int i = 0; i < N; i++) cin >> sins[i];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++) cin >> R[i][j];
    cin >> eunjin;

    if (N == 1) ret = 0;
    else {
        int maxSinnerIdx = 0, maxSinPoint = -1e9;
        for (int i = 0; i < N; i++) { // 초기 최대 죄값 찾기
            if (sins[i] > maxSinPoint) {
                maxSinPoint = sins[i];
                maxSinnerIdx = i;
            }
        }

        if (N % 2 == 0) playGame(N, 0, maxSinnerIdx); // 밤 시작
        else playGame(N, 0, maxSinnerIdx); // 낮 시작
    }
    cout << ret;
    return 0;
}
