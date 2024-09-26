/*
 알고리즘: 하라는 대로 구현하면 된다 (중력 -> 회전 -> 중력 작용만 조심)

 접근 방법:
 - BFS로 연결된 블록을 탐색하고 그룹 정보를 저장
 - 점수 계산 및 최종 점수를 출력

 실행 시간: 4ms, C++

*/

#include <iostream>
#include <cstring>
#include <utility>
#include <vector>
#include <queue>
using namespace std;

int N, M; // 격자의 크기, 색상의 개수
int A[21][21]; // 검은색 블록은 -1, 무지개 블록은 0
bool visited[21][21] = {false}; // 방문체크

struct GROUP {
    int x, y; // 시작점
    int rainbow = 0; // 무지개 블록 수
    vector<pair<int, int> > xy; // 그룹 블록 좌표
};

vector<GROUP> v; // 그룹 정보 저장 벡터

int ans = 0;

// 인접 칸 구하기
int dx[4] = {-1, 1, 0, 0};
int dy[4] = {0, 0, -1, 1};

struct SEARCH {
    int y, x;
    int color;
};

queue<SEARCH> q;

// 블록 그룹 찾기
void BFS(int y, int x, int color) {
    q.push({y, x, color});
    visited[y][x] = true;

    GROUP g; // BFS로 탐색한 그룹 정보 저장
    g.x = x; g.y = y;
    g.xy.push_back({y, x});

    while (!q.empty()) {
        int y = q.front().y;
        int x = q.front().x;
        q.pop();

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 범위 체크
            if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
                continue;
            }

            // 일반 블록의 색이 모두 같아야 함
            if (!visited[ny][nx] && (A[ny][nx] == 0 || A[ny][nx] == color)) {
                visited[ny][nx] = true;
                g.xy.push_back({ny, nx});

                if (A[ny][nx] == 0) {
                    g.rainbow++;
                }
                q.push({ny, nx, color});
            }
        }
    }

    // 그룹에 속한 블록의 개수는 2보다 크거나 같아야 함
    if (g.xy.size() >= 2) {
        v.push_back(g); // 모든 그룹 블록 정보 저장
    }
}

void gravity() {
    for (int i = N - 2; i >= 0; i--) {
        for (int j = 0; j < N; j++) {
            // 빈 칸이거나 검정 블록인 경우 무시
            if (A[i][j] == -2 || A[i][j] == -1) {
                continue;
            }

            int downY = i; // 내려가면서 탐색
            while (1) {
                downY++;

                // 제일 아래 칸까지 체크
                if (downY == N) {
                    break;
                }
                // 아래 칸이 비어있지 않을 때 체크
                if (A[downY][j] != -2) {
                    break;
                }
            }
            swap(A[--downY][j], A[i][j]); // 중력으로 블록 내려주기, swap
        }
    }
}

// 맵 90도 반시계 방향 rotate
void rotate() {
    int temp[21][21] = {0};

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            temp[N - j - 1][i] = A[i][j]; // 반시계방향 저장
        }
    }

    memcpy(A, temp, sizeof(A));
}

int main() {
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(0);
    
    cin >> N >> M;

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> A[i][j];
        }
    }

    while (1) {
        memset(visited, false, sizeof(visited)); // 방문 초기화
        v.clear();

        // 크기가 가장 큰 블록 그룹 찾기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 블록 그룹에는 일반 블록이 하나 이상 포함되어야 함. 일반 블록에서 그룹 탐색
                if (!visited[i][j] && A[i][j] > 0) {
                    BFS(i, j, A[i][j]);
                }

                // 무지개 visit 초기화
                for (int n = 0; n < N; n++) {
                    for (int m = 0; m < N; m++) {
                        if (A[n][m] == 0) {
                            visited[n][m] = false;
                        }
                    }
                }
            }
        }

        // 블록 그룹 존재 x
        if (v.size() == 0) {
            break;
        }

        // 크기가 가장 큰 블록 그룹 찾기
        int maxGroup = 0;
        int maxGroupIdx = 0;
        for (int i = 0; i < v.size(); i++) {
            if (v[i].xy.size() > maxGroup) {
                maxGroup = v[i].xy.size();
                maxGroupIdx = i;
            } else if (v[i].xy.size() == maxGroup) {

                // 그러한 블록 그룹이 여러 개라면 포함된 무지개 블록의 수가 가장 많은 블록 그룹
                if (v[i].rainbow > v[maxGroupIdx].rainbow) {
                    maxGroup = v[i].xy.size();
                    maxGroupIdx = i;
                } else if (v[i].rainbow == v[maxGroupIdx].rainbow) {
                    if (v[i].y > v[maxGroupIdx].y) {
                        maxGroup = v[i].xy.size();
                        maxGroupIdx = i;
                    } else if (v[i].y == v[maxGroupIdx].y) {

                        // 여러개이면 열이 가장 큰 것을 찾는다.
                        if (v[i].x > v[maxGroupIdx].x) {
                            maxGroup = v[i].xy.size();
                            maxGroupIdx = i;
                        }
                    }
                }
            }
        }

        // 블록 그룹의 모든 블록 제거
        for (int i = 0; i < v[maxGroupIdx].xy.size(); i++) {
            int x = v[maxGroupIdx].xy[i].second;
            int y = v[maxGroupIdx].xy[i].first;

            A[y][x] = -2;
        }

        // 블록 그룹에 포함된 블록의 수를 B라고 했을 때, B^2점을 획득
        int B = v[maxGroupIdx].xy.size(); // 블록 그룹에 포함된 블록의 수를 B
        ans += B * B;

        // 중력 -> 회전 -> 중력
        gravity();
        rotate();
        gravity();
    }

    cout << ans;
    return 0;
}
