#include <iostream>
using namespace std;
const int MAX = 300001;
int N;
int parent[MAX];

// 다 연결되어있는 상태에서 하나 간선을 뺀 것이므로
// 유니온 파인드를 사용해 합쳐준다

int find(int x) {
	if (parent[x] == x)
		return x;
	return parent[x] = find(parent[x]);
}

void union_p(int x, int y) {
	x = find(x);
	y = find(y);
	if (x == y)
		return;
	parent[y] = x;
}

bool check(int x, int y) {
	x = find(x);
	y = find(y);
    
	if (x == y) return true;
    else return false;
}

int main() {
	ios::sync_with_stdio(0); 
    cin.tie(0); 
    cout.tie(0);
    cin >> N;
    
	for (int i = 0; i < MAX; i++) parent[i] = i;
    
	for (int i = 0; i < N-2; i++) {
		int u, v; 
        cin >> u >> v;
		union_p(u, v);
	}
    
	for (int i = 2; i <= N; i++) {
		if (!check(1, i)) {
			cout << 1 << " " << i;
			break;
		}
	}
    return 0;
}
