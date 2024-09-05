#include <iostream>
#include <algorithm>
#include <cstring>
#include <vector>
using namespace std;
const int MAX_SIZE = 30;
 
// 게임판, 방향, 업데이트할 상단 행, 행/열 크기와 방향 인덱스
char board[MAX_SIZE + 1][MAX_SIZE + 1], dir, u_board[1][MAX_SIZE + 1];
int N, M, Q, idx; // N: 행, M: 열, Q: 명령 수, idx: 방문 체크용 인덱스
int dx[] = { -1,1,0,0 }; // 상하좌우 이동을 위한 x 좌표 변화
int dy[] = { 0,0,-1,1 }; // 상하좌우 이동을 위한 y 좌표 변화
int visit[MAX_SIZE + 1][MAX_SIZE + 1]; // 방문 여부 체크 배열
 
// 주어진 좌표가 범위 안에 있는지 확인하는 함수
bool isranged(int x, int y) {
    return (0 <= x && x < N && 0 <= y && y < M);
}
 
// 블록을 아래로 떨어뜨리는 함수
void find() {
    for (int i = 0; i < M; i++) { // 열을 하나씩 확인
        for (int j = N - 1; j >= 0; j--) { // 아래에서 위로 올라가며 확인
            // 블록이 있을 때, 그 아래에 빈 공간이 있으면 떨어뜨림
            if (board[j][i] != '#' && board[j + 1][i] == '#') {
                char tmp = board[j][i]; // 현재 블록 저장
                for (int k = j + 1; k < N; k++) {
                    // 빈 공간을 찾으면 블록을 그 위치로 이동
                    if (board[k][i] != '#') {
                        board[k - 1][i] = tmp;
                        break;
                    }
                    // 끝까지 내려갔을 때도 빈 공간이면 마지막에 블록을 배치
                    if (k == N - 1 && board[k][i] == '#') {
                        board[k][i] = tmp;
                        break;
                    }
                }
                board[j][i] = '#'; // 원래 위치는 빈 공간으로 만듦
            }
        }
    }
}
 
// 'U' 명령: 맨 위 행을 새로운 행으로 업데이트하는 함수
void upfunc() {
    // 맨 위 행이 모두 차 있으면 위로 밀어내기 가능
    for (int j = 0; j < M; j++) {
        if (board[0][j] != '#') {
            return;
        }
    }
    // 모든 블록을 한 행씩 위로 이동
    for (int j = 0; j < M; j++) {
        for (int i = 1; i < N; i++) {
            board[i - 1][j] = board[i][j];
        }
        board[N - 1][j] = u_board[0][j]; // 맨 아래 행을 새로 입력한 행으로 대체
    }
}
 
// 'L' 명령: 블록을 왼쪽으로 이동시키는 함수
void left() {
    for (int i = 0; i < N; i++) { // 행을 하나씩 확인
        for (int j = 0; j < M; j++) { // 열을 왼쪽부터 확인
            // 블록이 있고, 왼쪽이 빈 공간이면 이동
            if (board[i][j] != '#' && board[i][j - 1] == '#') {
                char tmp = board[i][j]; // 현재 블록 저장
                for (int k = j - 1; k >= 0; k--) {
                    // 왼쪽 빈 공간을 찾으면 그곳으로 이동
                    if (board[i][k] != '#') {
                        board[i][k + 1] = tmp;
                        break;
                    }
                    // 맨 왼쪽 끝까지 갔을 때도 빈 공간이면 그 위치로 이동
                    if (k == 0 && board[i][k] == '#') {
                        board[i][k] = tmp;
                        break;
                    }
                }
                board[i][j] = '#'; // 원래 위치는 빈 공간으로 만듦
            }
        }
    }
}
 
// 'R' 명령: 블록을 오른쪽으로 이동시키는 함수
void right() {
    for (int i = 0; i < N; i++) { // 행을 하나씩 확인
        for (int j = M - 1; j >= 0; j--) { // 열을 오른쪽부터 확인
            // 블록이 있고, 오른쪽이 빈 공간이면 이동
            if (board[i][j] != '#' && board[i][j + 1] == '#') {
                char tmp = board[i][j]; // 현재 블록 저장
                for (int k = j + 1; k < M; k++) {
                    // 오른쪽 빈 공간을 찾으면 그곳으로 이동
                    if (board[i][k] != '#') {
                        board[i][k - 1] = tmp;
                        break;
                    }
                    // 맨 오른쪽 끝까지 갔을 때도 빈 공간이면 그 위치로 이동
                    if (k == M - 1 && board[i][k] == '#') {
                        board[i][k] = tmp;
                        break;
                    }
                }
                board[i][j] = '#'; // 원래 위치는 빈 공간으로 만듦
            }
        }
    }
}
 
// DFS로 같은 종류의 블록을 찾아 연결된 블록의 개수를 반환
int go(int x, int y, char curr) {
    int ret = 1; // 현재 블록 포함
    for (int i = 0; i < 4; i++) {
        int nx = x + dx[i]; // 상하좌우 이동
        int ny = y + dy[i];
        if (isranged(nx, ny) && visit[nx][ny] == 0 && board[nx][ny] == curr) {
            visit[nx][ny] = idx; // 방문 체크
            ret += go(nx, ny, curr); // 재귀적으로 연결된 블록 탐색
        }
    }
    return ret;
}
 
// 'D' 명령: 가장 큰 그룹의 블록을 삭제하는 함수
void del() {
    memset(visit, 0, sizeof(visit)); // 방문 배열 초기화
    int cnt = 0; // 가장 큰 그룹의 크기
    idx = 0; // 그룹 번호
    vector<int> v; // 가장 큰 그룹의 번호를 저장할 벡터
    
    // 모든 블록을 탐색하며 그룹 찾기
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            if (board[i][j] != '#' && visit[i][j] == 0) {
                visit[i][j] = ++idx; // 새로운 그룹 시작
                int ret = go(i, j, board[i][j]); // 해당 그룹의 크기
                if (cnt < ret) { // 더 큰 그룹이 나오면 업데이트
                    cnt = ret;
                    v.clear();
                    v.push_back(idx);
                }
                else if (cnt == ret) { // 같은 크기의 그룹이면 추가
                    v.push_back(idx);
                }
            }
        }
    }
    
    // 가장 큰 그룹을 삭제 (빈 공간으로 만듦)
    int len = v.size();
    for (int k = 0; k < len; k++) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (visit[i][j] == v[k]) {
                    board[i][j] = '#';
                }
            }
        }
    }
}
 
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(0);
    
    int t; // 테스트 케이스 수
    cin >> t;
    
    for (int tc = 1; tc <= t; tc++) {
        cin >> N >> M >> Q; // 행, 열, 명령 수 입력
        memset(board, 0, sizeof(board)); // 게임판 초기화
        
        // 게임판 입력
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                cin >> board[i][j];
            }
        }
        
        // 명령 처리
        while (Q--) {
            cin >> dir; // 명령 입력
            if (dir == 'U') { // 'U' 명령 처리
                memset(u_board, 0, sizeof(u_board)); // 상단 행 초기화
                for (int i = 0; i < M; i++) {
                    cin >> u_board[0][i]; // 상단 행 입력
                }
                upfunc(); // 상단 행 업데이트
            }
            else if (dir == 'L') left(); // 'L' 명령 처리
            else if (dir == 'R') right(); // 'R' 명령 처리
            else if (dir == 'D') del(); // 'D' 명령 처리
            
            find(); // 블록 떨어뜨리기
        }
        
        // 결과 출력
        cout << '#' << tc << '\n';
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                cout << board[i][j];
            }
            cout << '\n';
        }
        cout << '\n';
    }
    
    return 0;
}
