#include <iostream>
#include <vector>
using namespace std;
using pii = pair<int, int>;
const int MAX = 110;
int C, P;
int ret = 0;
int grid[MAX+1][MAX+1];

/* 문제 접근
  블록들의 개수가 적은 것을 보고 블록 모양을 일일히 구현해야 겠다는 생각이 들었다.
  특히, 블록을 회전 할 수 있는 경우가 생기므로 회전 가능성도 고려해야 한다.
  -> 회전 개수가 많을 것 같이 느껴질 수도 있지만, 의외로 회전해도 그대로인 블록이 있거나 회전 경우의 수가 적은 블록이 꽤 있다.
  -> 회전 경우까지 블록 모양에 구현해주면 우리가 체크해야 하는 것은 두 가지로 간단해진다.

  check 1. 블록을 해당 위치에 놓았을 때 다른 칸이랑 겹치는지 (못 놓는 곳)
  check 2. 블록을 놓은 후에 블록과 블록 or 블록과 바닥 사이에 빈 칸이 생기는지

  이 두가지를 체크하는 함수를 따로 만들어야겠다고 생각했다.

  사용 알고리즘: 구현
  시간 복잡도: 브루트포스, 범위가 작아서 애매

  checkBlock 함수:
   1. 블록이 격자 범위를 벗어나거나 다른 블록과 겹치는지 확인
   2. 블록을 임시로 놓아본 뒤, 블록 아래 칸들이 모두 차 있는지(중력 조건)를 확인
   3. 두 조건을 모두 만족하면 해당 위치에 블록을 놓을 수 있다고 판단

  중력 조건 체크:
   - 블록 아래에 빈 공간이 있는 경우, 블록이 떠 있는 상태라서 불가능
   - 임시로 블록을 놓은 뒤, 해당 칸 바로 아래부터 바닥까지 모든 칸 채워져 있는지 확인
   - 조건을 만족하지 않으면 블록 상태 원복
*/

struct block {
    vector<vector<pii>> pos;
};

block blocks[8];

void initBlocks() {
    blocks[1].pos.push_back({{0, 0}, {1, 0}, {2, 0}, {3, 0}});
    blocks[1].pos.push_back({{0, 0}, {0, 1}, {0, 2}, {0, 3}});

    blocks[2].pos.push_back({{0, 0}, {0, 1}, {1, 0}, {1, 1}});

    blocks[3].pos.push_back({{0, 1}, {0, 2}, {1, 0}, {1, 1}});
    blocks[3].pos.push_back({{0, 0}, {1, 0}, {1, 1}, {2, 1}});

    blocks[4].pos.push_back({{0, 0}, {0, 1}, {1, 1}, {1, 2}});
    blocks[4].pos.push_back({{0, 1}, {1, 0}, {1, 1}, {2, 0}});

    blocks[5].pos.push_back({{0, 1}, {1, 0}, {1, 1}, {1, 2}});
    blocks[5].pos.push_back({{0, 0}, {1, 0}, {2, 0}, {1, 1}});
    blocks[5].pos.push_back({{0, 0}, {0, 1}, {0, 2}, {1, 1}});
    blocks[5].pos.push_back({{0, 1}, {1, 1}, {2, 1}, {1, 0}});

    blocks[6].pos.push_back({{0, 2}, {1, 0}, {1, 1}, {1, 2}});
    blocks[6].pos.push_back({{0, 0}, {1, 0}, {2, 0}, {2, 1}});
    blocks[6].pos.push_back({{0, 0}, {0, 1}, {0, 2}, {1, 0}});
    blocks[6].pos.push_back({{0, 0}, {0, 1}, {1, 1}, {2, 1}});

    blocks[7].pos.push_back({{0, 0}, {1, 0}, {1, 1}, {1, 2}});
    blocks[7].pos.push_back({{0, 0}, {0, 1}, {1, 0}, {2, 0}});
    blocks[7].pos.push_back({{0, 0}, {0, 1}, {0, 2}, {1, 2}});
    blocks[7].pos.push_back({{0, 1}, {1, 1}, {2, 1}, {2, 0}});
}

void restore(const vector<pii>& pos) {
    for (auto nowp : pos) grid[nowp.first][nowp.second] = 0;
}

bool checkRange(int r, int c) {
    return (0 <= r && r <= MAX && 0 <= c && c < C);
}

bool checkBlock(int r, int c, const vector<pii>& pos) {
    vector<pii> tmp;

    for (auto nowp : pos) {
        int nr = r + nowp.first;
        int nc = c + nowp.second;

        if (!checkRange(nr, nc)) return false;
        if (grid[nr][nc] == 1) return false;
        tmp.push_back({nr, nc});
    }

    for (auto tmpP : tmp) grid[tmpP.first][tmpP.second] = 1;

    for (auto tmpP : tmp) {
        for (int nr = tmpP.first + 1; nr <= MAX; nr++) {
            if (grid[nr][tmpP.second] != 1) {
                restore(tmp);
                return false;
            }
        }
    }
    restore(tmp);
    return true;
}

int cntCases(const vector<pii>& pos) {
    int cnt = 0;
    for (int i = 0; i <= MAX; ++i) {
        for (int j = 0; j < C; ++j) {
            if (checkBlock(i, j, pos)) cnt++;
        }
    }
    return cnt;
}

int main() {
    cin.tie(0)->sync_with_stdio(false);

    initBlocks(); // 초기화 함수 호출 추가
    cin >> C >> P;

    for (int i = 0; i < C; ++i) {
        int H;
        cin >> H;
        for (int j = MAX; j > MAX - H; --j) {
            grid[j][i] = 1;
        }
    }

    for (const auto& b : blocks[P].pos) {
        ret += cntCases(b);
    }
    cout << ret;
    return 0;
}
