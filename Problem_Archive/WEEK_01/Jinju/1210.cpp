#include <iostream>
#include <vector>
#include <queue>
 
using namespace std;
using ll = long long;
 
int ret;
int grid[100][100] = { 0, };
 
void bfs(int r, int c) {
    queue<pair<int, int>> Q;
    Q.push(make_pair(r, c));
 
    while (!Q.empty()) {
        int r = Q.front().first;
        int c = Q.front().second;
        Q.pop();
 
        if (r == 0) { //상단부에 도달했을 때
            ret = c;
            break;
        }
        else if (grid[r-1][c]== 1) {
            //1.위로 가는 길과 옆으로 가는 길이 동시에 있는 경우 (방향 전환 첫번째 분기점)
            //2. 옆으로 가는 길만 있는 경우 (위에 길이 나올때 까지 이동해야함...!)
            if (c - 1 >= 0 && c - 1 < 100 && grid[r][c - 1] == 1) {
                //왼쪽 이동
                int nr = r;
                int nc = c - 1;
 
                while (grid[nr - 1][nc] != 1) {
                    nc--;
                }
                Q.push(make_pair(nr-1, nc));
            }
            else if (c + 1 >= 0 && c + 1 < 100 && grid[r][c + 1] == 1) {
                //오른쪽 이동
                int nr = r;
                int nc = c + 1;
 
                while (grid[nr - 1][nc] != 1) {
                    nc++;
                }
                Q.push(make_pair(nr-1, nc));
            }
            else {
                //위로 이동
                Q.push(make_pair(r - 1, c));
            }
        }
    }
    return;
}
 
int main(void) {
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(0);
 
    for (int t = 0; t < 10; t++) {
        int test;
        cin >> test;
        int sr, sc;
 
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                int num;
                cin >> num;
                grid[i][j] = num;
                if (num == 2) {
                    sr = i;
                    sc = j;
                }
            }
        }
        bfs(sr, sc);
        cout << "#" << test << " " << ret<< "\n";
    }
 
    return 0;
}
