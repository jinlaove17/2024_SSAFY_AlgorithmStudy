#include <iostream>
#include <cstring>
#include <set>
using namespace std;
 
const int MAXLEN = 6;
 
int T, ret;
char grid[4][4];
int dr[4] = {1, -1, 0, 0};
int dc[4] = {0, 0, 1, -1};
set<string> num;
 
void init() {
    ret = 0;
    num.clear();
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            cin >> grid[i][j];
        }
    }
}
 
void dfs(int r, int c, int depth, string tmp) {
    if (depth == MAXLEN) {
        num.insert(tmp);
        return;
    }
 
    for (int i = 0; i < 4; i++) {
        int nr = r + dr[i];
        int nc = c + dc[i];
 
        if (nr >= 0 && nr < 4 && nc >= 0 && nc < 4) {
            dfs(nr, nc, depth + 1, tmp+grid[nr][nc]);
        }
    }
}
 
int main(void) {
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(0);
 
    cin >> T;
    for (int t = 1; t <= T; t++) {
        init();
         
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                string tmp(1, grid[i][j]);
                dfs(i, j, 0, tmp);
            }
        }
 
        ret = num.size();
 
        cout << "#" << t << " " << ret << "\n";
    }
    return 0;
}
