#include <iostream>
#include <cstring>
 
using namespace std;
using ll = long long;
 
const int MAX = 22; //상하좌우 한바퀴 체크를 위해 칸수를 늘려준다
const int DIR = 4;
const int MEM = 16;
 
int T, R, C;
bool flg = false;
int dr[4] = {0, 1, 0, -1};
int dc[4] = {1, 0, -1, 0};
char grid [MAX][MAX];
bool check[MAX][MAX][DIR][MEM];
 
void init ();
bool dfs(int nr, int nc, int nd, int nm);
 
int main(void){
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(0);
    cin >> T;
 
    for(int t=1; t <= T; t++){
        cin >> R >> C;
        init();
 
        for(int i=1; i <= R; i++){
            for(int j=1; j <= C; j++){
                cin >> grid[i][j];
                if (grid[i][j] == '@') flg = true;
            }
        }
 
        if (!flg){
            cout << "#" << t << " " << "NO" << "\n";
            continue;
        } else {
            bool ret = dfs(1, 1, 0, 0);
            cout << "#" << t << " " <<  (ret ? "YES" : "NO") << "\n";
        }
    }
    return 0;
}
 
void init(){
    flg = false;
    memset(grid, 0, sizeof(grid));
    memset(check, false, sizeof(check));
}
 
bool dfs(int nr, int nc, int nd, int nm){
    bool tmpRet = false;
 
    if (grid[nr][nc] >= '0' && grid[nr][nc] <= '9') nm = grid[nr][nc] - '0';
    else if (grid[nr][nc] == '>' || (grid[nr][nc] == '_' && nm == 0)) nd = 0;
    else if (grid[nr][nc] == 'v' || (grid[nr][nc] == '|' && nm == 0)) nd = 1;
    else if (grid[nr][nc] == '<' || grid[nr][nc] == '_') nd = 2;
    else if (grid[nr][nc] == '^' || grid[nr][nc] == '|') nd = 3;
    else if (grid[nr][nc] == '+') nm = (nm+1)%MEM;
    else if (grid[nr][nc] == '-' && nm == 0) nm = 15;
    else if (grid[nr][nc] == '-') nm -= 1;
    else if (grid[nr][nc] == '@') return true;
 
    if (check[nr][nc][nd][nm]) return false; //반복 사이클 방문 체크
    check[nr][nc][nd][nm] = true; // visited 체크
 
    int nxtr = nr + dr[nd];
    int nxtc = nc + dc[nd];
 
    //행, 열 각각 한 바퀴 돌아 다음 칸으로 진행
    if(nxtr < 1) nxtr = R;
    else if(nxtr > R) nxtr = 1;
 
    if(nxtc < 1) nxtc = C;
    else if(nxtc > C) nxtc = 1;
 
    //물음표 있을 때 백트래킹으로 가능성 체크
    if(grid[nr][nc] == '?') {
        for(int i = 0 ; i < 4; i++) {
            nxtr = nr + dr[i];
            nxtc = nc + dc[i];
 
            if(nxtr < 1) nxtr = R;
            else if(nxtr > R) nxtr = 1;
 
            if(nxtc < 1) nxtc = C;
            else if(nxtc > C) nxtc = 1;
 
            tmpRet = dfs(nxtr, nxtc,i, nm) || tmpRet;
        }
        return tmpRet;
    } else return dfs(nxtr, nxtc, nd, nm) || tmpRet;
}
