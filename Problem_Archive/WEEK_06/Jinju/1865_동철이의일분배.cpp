#include <iostream>
#include <vector>
#include <algorithm>
#include <iomanip>
using namespace std;

int N;
double max_value;
vector<vector<double>> arr;
vector<bool> check;

void dfs(int idx, double answer = 100.0) {
	if (answer <= max_value) return;

	if (idx >= N) {
		max_value = answer;
		return;
	}

	// 모든 작업에 대해 재귀적으로 백트래킹
	for (int i = 0; i < N; ++i) {
		if (check[i]) {
			check[i] = false;
			dfs(idx + 1, answer * arr[idx][i]);
			check[i] = true;
		}
	}
}

int main() {
	int T;
	cin >> T;

	for (int t = 1; t <= T; ++t) {
		cin >> N;
		arr.assign(N, vector<double>(N));
		check.assign(N, true);

		// 입력을 받아 100으로 나눈 값을 저장
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				int x;
				cin >> x;
				arr[i][j] = x / 100.0;
			}
		}

		max_value = 0.0;
		dfs(0);
		cout << "#" << t << " " << fixed << setprecision(6) << max_value << "\n";
	}
	return 0;
}
