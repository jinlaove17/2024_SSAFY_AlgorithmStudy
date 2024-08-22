#include <iostream>
#include <cstring>
#include <queue>

using namespace std;

int N, W, H, T;
int MIN;
int board[15][12];        // 초기 보드 상태
int tmp[5][15][12];       // 각 단계별 보드 상태
int dx[4] = {-1, 0, 1, 0};
int dy[4] = {0, 1, 0, -1};
int down[15];             // 벽돌 이동을 위한 임시 배열

struct block {
    int x, y, p;
};

queue<block> q;

// i번째 열에 구슬을 쏘는 함수
void shoot(int i, int cnt) {
    // 큐에 터져야 하는 블록을 저장
    for (int j = 0; j < H; j++) {
        if (tmp[cnt][j][i] == 0) continue;

        // 블록이 1인 경우 바로 제거, 아닌 경우 큐에 추가
        if (tmp[cnt][j][i] == 1) tmp[cnt][j][i] = 0;
        else
            q.push({ j, i, tmp[cnt][j][i] - 1 });
        break;
    }
    if (q.empty()) return;

    // 큐를 사용해 블록 파괴 및 연쇄 반응 처리
    int x, y, p;
    while (!q.empty()) {
        x = q.front().x;
        y = q.front().y;
        p = q.front().p;
        q.pop();

        tmp[cnt][x][y] = 0;

        for (int a = 0; a < 4; a++) {
            int nx = x;
            int ny = y;

            for (int b = 0; b < p; b++)  {
                nx += dx[a];
                ny += dy[a];

                // 보드 범위를 벗어나지 않으면서 파괴 가능한 블록 처리
                if (nx < 0 || ny < 0 || nx >= H || ny >= W) continue;
                if (tmp[cnt][nx][ny] <= 1)
                    tmp[cnt][nx][ny] = 0;
                else
                    q.push({ nx, ny, tmp[cnt][nx][ny] - 1 });
            }
        }
    }

    // 빈 칸 제거 (블록을 아래로 내림)
    for (int a = 0; a < W; a++) {
        memset(down, 0, sizeof(down));
        int move = 0;
        for (int b = H-1; b >= 0; b--) {
            if (tmp[cnt][b][a] == 0) {
                move++;
            }
            else if(move != 0) {
                down[b] = move;
            }
        }
        for (int b = H - 1; b >= 0; b--) {
            if (down[b] != 0) {
                tmp[cnt][b + down[b]][a] = tmp[cnt][b][a];
                tmp[cnt][b][a] = 0;
            }
        }
    }
}

// 구슬을 N번 쏘아 모든 경우를 탐색
void dfs(int cnt) {

    if (cnt == N) {
        // 남은 벽돌 개수 세기
        int ret = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (tmp[cnt][i][j] != 0)
                    ret++;
            }
        }
        // 최소 벽돌 개수 갱신
        if (MIN > ret)
            MIN = ret;
        return;
    }

    // 현재 보드 상태를 다음 단계에 복사
    memcpy(tmp[cnt + 1], tmp[cnt], sizeof(board));

    // 모든 열에 대해 구슬을 쏨
    for (int i = 0; i < W; i++)  {
        shoot(i, cnt + 1);
        dfs(cnt + 1);
        memcpy(tmp[cnt + 1], tmp[cnt], sizeof(board)); // 보드 원복
    }
}

int main(void) {
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(0);

    cin >> T;
    for (int t = 1; t <= T; t++) {

        cin >> N >> W >> H;

        memset(board, 0, sizeof(board));
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                cin >> board[i][j];
            }
        }

        // 벽돌 깨기
        MIN = W*H+1;  // 최소 값 초기화
        memcpy(tmp[0], board, sizeof(board)); // 초기 보드 상태 복사
        dfs(0);  // DFS 시작

        cout << "#" << t << " " << MIN << "\n";  // 결과 출력
    }
    return 0;
}
