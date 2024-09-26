/*
    알고리즘:
    BFS (너비 우선 탐색)를 사용하여 주어진 그리드에서 시작점과 도착점 간의 최단 경로 찾기
    각 위치에서 거울의 최소 개수를 세기 위해 방향 고려, 거울을 사용할 때와 사용하지 않을 때를 구분하여 처리 (3차원 배열)
    
    접근 방법:
    1. 3차원 배열을 사용하여 방향별로 방문 여부와 거울 사용 개수를 기록
    2. BFS 탐색, 가능한 방향으로 이동하면서 거울 사용 개수 업데이트

*/

#include <bits/stdc++.h>
using namespace std;
using pi = pair<int, int>;

int W, H;
int dx[4] = {1,0,-1,0}; // 동, 서, 남, 북 방향
int dy[4] = {0,1,0,-1};

char grid[101][101]; // 그리드
int check[101][101][4]; // 방문 체크 (x, y, 방향)
int raser[101][101][4]; // 각 위치에서 방향별 거울 최소 개수
pi start, finish;

// BFS 탐색 함수
void bfs(pi pos){
    queue<pair<pi, int>> Q; // 위치, 방향 순서로 저장
    Q.push({pos, -1}); // -1으로 방향 없는 상태 초기화

    for(int i = 0; i < 4; i++){
        check[pos.first][pos.second][i] = 1; // 시작점 방문 처리
        raser[pos.first][pos.second][i] = 0; // 시작점에서 거울 사용 개수 초기화
    }

    while(!Q.empty()){
        int x = Q.front().first.first;
        int y = Q.front().first.second;
        int d = Q.front().second;
        Q.pop();

        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 그리드 범위 체크
            if(nx >= 1 && nx <= H && ny >= 1 && ny <= W){
                if(grid[nx][ny] == '*') continue; // 벽 처리

                if(d == i){ // 같은 방향으로 이동
                    if(check[nx][ny][i] == 0){
                        raser[nx][ny][i] = raser[x][y][d]; // 거울 사용하지 않음
                        check[nx][ny][i] = 1;
                        Q.push({{nx,ny}, i});
                    } else {
                        if(raser[nx][ny][i] > raser[x][y][d]){
                            raser[nx][ny][i] = raser[x][y][d]; // 기존보다 더 적은 거울 사용
                            Q.push({{nx, ny},i});
                        }
                    }
                } else { // 방향 변경
                    if(check[nx][ny][i] == 0){
                        raser[nx][ny][i] = raser[x][y][d] + 1; // 거울 사용 추가
                        check[nx][ny][i] = 1;
                        Q.push({{nx,ny}, i});
                    } else {
                        if(raser[nx][ny][i] > raser[x][y][d] + 1){
                            raser[nx][ny][i] = raser[x][y][d] + 1; // 기존보다 더 적은 거울 사용
                            Q.push({{nx,ny},i});
                        }
                    }
                }    
            }
        }
    }
}

int main() {
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(0);

    cin >> W >> H;
    for(int i= 1; i <= H; i++){
        for(int j = 1; j <= W; j++){
            cin >> grid[i][j];

            if(grid[i][j] == 'C' && start.first == 0) start = {i,j}; // 시작점 찾기
            else if(grid[i][j] == 'C') finish = {i,j}; // 도착점 찾기
        }
    }
    
    int MIN = 100000001; // 최소 거울 사용 개수 초기화
    bfs(start); // BFS 탐색

    // 도착점에서 방향별 최소 거울 사용 개수 계산
    for(int i = 0; i < 4; i++){
        if(check[finish.first][finish.second][i] == 0) continue;
        MIN = min(MIN, raser[finish.first][finish.second][i]);
    }
    
    cout << MIN-1 << "\n"; // 결과 출력 (거울 사용 개수 - 1)
    return 0;
}
