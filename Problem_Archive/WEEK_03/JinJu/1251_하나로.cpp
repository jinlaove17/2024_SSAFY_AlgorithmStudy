#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
using namespace std;
using ll = long long;

const int MAX = 1001;
int T, N;
double E;
int posX[MAX];
int posY[MAX];
vector<pair<ll, pair<int, int>>> edges;
vector<int> parent;

void init() {
	for (int i = 0; i < MAX; i++) {
		posX[i] = 0;
		posY[i] = 0;
	}
}

void input() {
	cin >> N;
	edges.clear();
	parent.clear();

	for (int i = 0; i < N; i++) {
		cin >> posX[i]; //X좌표
	}

	for (int i = 0; i < N; i++) {
		cin >> posY[i]; //Y좌표
	}

	cin >> E; //세율
}

int find_p(int me) {
	if (me == parent[me]) return me;
	else return find_p(parent[me]);
}

void unionFind(int x, int y) {
	int px = find_p(x);
	int py = find_p(y);

	if (px > py) parent[px] = py;
	else parent[py] = px;
}


int main(void) {
	cin.tie(0);
	cout.tie(0);
	ios::sync_with_stdio(0);

	cin >> T;
	for (int t = 1; t <= T; t++) {
		init();
		input();
		
		//1. 각 모든 edge를 계산하고 
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				int diffX = posX[i] - posX[j];
				int diffY = posY[i] - posY[j];

				ll distX = (ll)diffX * diffX;
				ll distY = (ll)diffY * diffY;
				ll dist = distX + distY; //제곱인 상태로 들고있기
				edges.push_back({ dist , {i, j}});
			}
		}

		//2. edge를 중심으로 Kruskal 알고리즘 적용
		sort(edges.begin(), edges.end());

		parent.resize(N);
		for (int i = 0; i < N; i++) {
			parent[i] = i; //parent 배열 초기화
		}

		ll total_cost = 0;
		for (auto edge : edges) {
			ll cost = edge.first;
			int u = edge.second.first;
			int v = edge.second.second;

			if (find_p(u) != find_p(v)) {
				unionFind(u, v);
				total_cost += cost;
			}
		}

		double ret = (double)total_cost*E;
		cout.precision(0);
		cout << "#" << t << " " << fixed << ret << '\n';

	}
	return 0;
}
