#include <iostream>
#include <queue>
#include <algorithm>
#include <vector>

using namespace std;

int N, M, T, K, ret;
int num[1001][2]; // 고객의 접수 및 수리 창구 번호를 저장하는 배열
int sn, sm; // 문제에서 주어진 목표 접수 창구 번호와 수리 창구 번호

// 고객의 정보를 담는 구조체
struct Customer {
    int idx, from, arrivetime; // 고객의 인덱스, 도착 창구, 도착 시간
};

// 창구의 상태를 관리하는 구조체
struct Staff {
    int idx, time, remain; // 고객 인덱스, 처리 시간, 남은 처리 시간
    bool finish; // 창구가 비어있는지 여부를 나타내는 플래그
};

// 우선순위 큐에서 고객을 정렬하기 위한 비교 함수 객체
struct cmp {
    bool operator()(Customer &a, Customer &b) {
        if (a.arrivetime == b.arrivetime) {
            return a.from > b.from;
        }
        return a.arrivetime > b.arrivetime;
    }
};

Customer tmp; // 임시로 고객 정보를 저장하는 변수
Staff reception[10], repair[10]; // 각각 접수 창구와 수리 창구 상태를 관리하는 배열

int main() {
    cin.tie(0);
    cout.tie(0);
    ios_base::sync_with_stdio(0);

    cin >> T;
    for (int t = 1; t <= T; t++) {
        ret = 0;

        // 입력받기: 창구 수, 고객 수, 목표 창구 번호
        cin >> N >> M >> K >> sn >> sm;

        // 접수 창구 초기화
        for (int i = 1; i <= N; i++) {
            cin >> reception[i].time;
            reception[i].finish = true;
        }

        // 수리 창구 초기화
        for (int i = 1; i <= M; i++) {
            cin >> repair[i].time;
            repair[i].finish = true;
        }

        queue<Customer> Q; // 도착한 고객을 저장하는 큐

        // 각 고객의 도착 시간 입력
        for (int i = 1; i <= K; i++) {
            int tk;
            cin >> tk;
            tmp.arrivetime = tk;
            tmp.idx = i;
            Q.push(tmp);
        }

        priority_queue<Customer, vector<Customer>, cmp> wait; // 대기열을 관리하는 우선순위 큐

        int ct = Q.front().arrivetime; // 현재 시각 초기화
        int cnt = 0; // 처리된 고객 수

        while (cnt < K) { // 모든 고객이 처리될 때까지 반복
            // 새로운 고객이 창구에 도착했는지 확인
            while (!Q.empty()) {
                if (Q.front().arrivetime <= ct) { // 고객이 도착한 경우
                    bool avail = false;
                    for (int i = 1; i <= N; i++) { // 접수 창구를 확인
                        if (reception[i].finish) { // 빈 접수 창구에 배정
                            reception[i].finish = false;
                            reception[i].remain = reception[i].time;
                            reception[i].idx = Q.front().idx;
                            Q.pop();
                            avail = true;
                            break;
                        }
                    }
                    if (!avail) break; // 모든 접수 창구가 바쁘다면 루프 종료
                }
                else break;
            }

            // 접수 창구의 작업이 완료되었는지 확인
            for (int i = 1; i <= N; i++) {
                if (!reception[i].finish) { // 사용 중인 창구의 처리 시간 감소
                    reception[i].remain--;
                    if (reception[i].remain == 0) { // 작업 완료 시
                        int nidx = reception[i].idx;
                        num[nidx][0] = i; // 접수 창구 번호 저장
                        reception[i].finish = true;

                        tmp.arrivetime = ct;
                        tmp.from = i;
                        tmp.idx = nidx;
                        wait.push(tmp); // 대기열에 추가
                    }
                }
            }

            // 수리 창구가 비었는지 확인
            while (!wait.empty()) {
                bool avail = false;
                for (int i = 1; i <= M; i++) {
                    if (repair[i].finish) { // 빈 수리 창구에 배정
                        repair[i].finish = false;
                        repair[i].remain = repair[i].time;
                        repair[i].idx = wait.top().idx;
                        wait.pop();
                        avail = true;
                        break;
                    }
                }
                if (!avail) break; // 모든 수리 창구가 바쁘다면 루프 종료
            }

            // 수리 창구의 작업이 완료되었는지 확인
            for (int i = 1; i <= M; i++) {
                if (!repair[i].finish) { // 사용 중인 창구의 처리 시간 감소
                    repair[i].remain--;
                    if (repair[i].remain == 0) { // 작업 완료 시
                        int nidx = repair[i].idx;
                        num[nidx][1] = i; // 수리 창구 번호 저장
                        repair[i].finish = true;
                        cnt++;
                    }
                }
            }
            ct++; // 시각 증가
        }

        // 목표 창구에서 처리된 고객 합산
        for (int i = 1; i <= K; i++) {
            if (num[i][0] == sn && num[i][1] == sm)
                ret += i;
        }
        if (ret == 0) ret = -1; // 조건을 만족하는 고객이 없으면 -1
        cout << "#" << t << " " << ret << '\n';
    }

    return 0;
}
