#include <iostream>
#include <vector>
#include <algorithm>
#include <cstring>
using namespace std;

int T, R, C;
char grid[21][21];
bool vst[26] = { false, };
int dr[] = { -1, 1, 0, 0 };
int dc[] = { 0, 0, -1, 1 };
int ret = 0;

void init() {
	ret = 0;
	memset(vst, 0, 26);
}

void dfs(int depth, int r, int c) {

	if (depth >= ret) {
		ret = max(ret, depth);
	}

	for (int i = 0; i < 4; i++) {
		int nr = r + dr[i];
		int nc = c + dc[i];
		int now = grid[nr][nc] - 'A';

		if (0 <= nr && nr < R && 0 <= nc && nc < C && !vst[now]) {
			vst[now] = true;
			dfs(depth + 1, nr, nc);
			vst[now] = false;
		}
	}

	return;
}

int main() {
	cin.tie(0);
	ios::sync_with_stdio(0);

	cin >> T;
	for (int t = 1; t <= T; t++) {
		cin >> R >> C;
		init();

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				cin >> grid[i][j];
			}
		}

		int now = grid[0][0] - 'A';
		vst[now] = true;
		dfs(1, 0, 0);

		cout << "#" << t << " " << ret << "\n";
	}
	return 0;
}
