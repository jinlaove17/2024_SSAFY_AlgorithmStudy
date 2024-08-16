#include <iostream>
#include <cstring>
#include <vector>
#include <queue>
using namespace std;

int T;
int N, M, K;
int dx[4] = {1, -1, 0, 0};
int dy[4] = {0, 0, 1, -1};

// 1. cell의 정보를 표현하는 새로운 구조체 생성
struct cell {
    int x;
    int y;
    int lifeS;
    int leftTime;
};

// 2. cell 의 생명력 수치 비교하는 compare 함수
struct compare {
    bool operator()(cell a, cell b) {
        return a.lifeS < b.lifeS;
    }
};

int main(void) {
    cin.tie(0);
    ios::sync_with_stdio(0);

    // 3. grid의 최대 확장 크기를 가늠하여 인위적으로 map 늘리기
    int grid[351][351];
    vector<cell> cells;
    priority_queue<cell, vector<cell>, compare> PQ; //더 큰 순으로 담는 PQ

    cin >> T;

    for (int t = 1; t <= T; t++) {
        // 입력 받은 후 초기화
        cin >> N >> M >> K;
        memset(grid, 0, sizeof(grid));
        cells.clear();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)  {
                // 적당히 가운데 근처부터 cell 입력 채우기
                int input;
                cin >>  input;
                grid[150+i][150+j] = input;

                if (input != 0) // cells에 좌표 넣기
                    cells.push_back({ 150 + i, 150 + j, input, input});
            }
        }

        int deadCell = 0;
        int totalLen = cells.size();

        // 4. 시간 줄이면서 isAlive 상태 체크
        while (K > 0) {
            for (int i = 0; i < totalLen; i++) {
                cells[i].leftTime--;
                if (cells[i].leftTime == -1) {
                    // 비활성화를 활성화 상태로 바꾸기 위해 PQ에 넣는다
                    PQ.push(cells[i]);
                }
                if (cells[i].leftTime == -cells[i].lifeS) {
                    // 세포를 죽은 상태로 바꾸기
                    deadCell++;
                }
            }

            while (!PQ.empty()) {
                int x = PQ.top().x;
                int y = PQ.top().y;
                int lifeScore = PQ.top().lifeS;
                PQ.pop(); //PQ에서 빼냄

                for (int i = 0; i < 4; i++)  {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if (grid[nx][ny] == 0) {
                        grid[nx][ny] = lifeScore;
                        cells.push_back({ nx,ny,lifeScore, lifeScore});
                        totalLen++;
                    }
                }
            }
            K--;
        }

        // 개수를 다시 세기 싫으므로.. totalLen 에서 dead를 빼준다
        cout << "#" << t << " " << totalLen - deadCell << "\n";
    }
    return 0;
}

// 후기
// 구조체 생성 및 전체 map 배열 크기를 최대 이상으로 늘리고, 적당히 가운데부터 채우는 테크닉을 배웠음
// 생명력 강한 cell이 이기는(먼저 배양하는) 조건을 PQ와 compare로 해결하는 아이디어를 얻음
