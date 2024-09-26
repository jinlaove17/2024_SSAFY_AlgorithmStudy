
#include<iostream>
#include<queue>
#include<vector>
#include<cstring>

using namespace std;

int T, N;
int cnt = 0;
bool vst[301][301] = { false, };
char board[301][301];
char input;

// 8방향 x, y 좌표 이동
int dx[8] = {0, 0, -1, 1, -1, 1, -1, 1};
int dy[8] = {-1, 1, 0, 0, -1, 1, 1, -1};

bool check(int x, int y) {
	for (int i = 0; i < 8; i++) {
		int nx = x + dx[i];
		int ny = y + dy[i];

		if (0 <= nx && nx < N && 0 <= ny && ny < N) {
			if (board[nx][ny] == '*') return false;
		}
	}
	return true;
}

int game() {
	int nowCnt = 0;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			if (!vst[i][j] && board[i][j] == '.' && check(i, j)) {
				nowCnt++;

				queue<pair<int, int>> Q;
				Q.push(make_pair(i, j));

				while (!Q.empty()) {
					int x = Q.front().first;
					int y = Q.front().second;
					vst[x][y] = true;
					Q.pop();
					
					for (int k = 0; k < 8; k++) {
						int nx = x + dx[k];
						int ny = y + dy[k];
						if (0 <= nx && nx < N && 0 <= ny && ny < N && !vst[nx][ny] && board[nx][ny] == '.') {
							vst[nx][ny] = true;
							if (check(nx, ny)) Q.push(make_pair(nx, ny));
						}
					}
				}
			}
		}
	}
	return nowCnt;
}

int main(void) {
	cin.tie(0);
	cout.tie(0);
	ios_base::sync_with_stdio(false);

	cin >> T;
	for (int t=0; t < T; t++) {
		
		cin >> N;
		//다시 초기화
		memset(vst, 0, sizeof(vst));
		cnt = 0;

		for (int i=0; i < N; i++){
			for (int j = 0; j < N; j++) {
				cin >> input;
				board[i][j] = input;
			}
		}
		
		cnt += game();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!vst[i][j] && board[i][j] == '.') cnt++;
			}
		}
		cout << "#" << t+1 << " " <<cnt << "\n";
	}
}
