#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;

/*
  문제 접근 과정
  >> 문제를 읽자마자 든 생각
    1) 하이퍼튜브에 포함된 역들은 서로 거리가 1이다.
    2) 튜브가 묶음으로 주어진다.
    3) 튜브끼리 묶어서 잘 연결만 해주면 최단 거리를 구하는 BFS로 풀릴 것이라는 판단.
        여기서, 모든 튜브 내 역들 사이 거리가 1이라는 것이 BFS 최단거리로 판단하는 근거가 되었다.
    4) 그러나 N의 최대가 10만이고, 튜브의 개수  튜브 안에 포함된 역의 개수는 각각 1000이다.
        즉, 모든 노드에 대해 일일히 그래프를 만들려고 하면 안된다.
        튜브의 묶음을 잘 처리해서 BFS를 돌리면 된다고 생각했다.

    튜브를 묶어서 생각하다보니 튜브 묶음 자체를 정점처럼 여기고 BFS를 돌리는 방법이 떠올랐다.
    일단 시작점에서 출발하여 시작점이 포함된 tube를 쭉 순회하면서 큐에 넣고 탐색(이동거리 1)
    튜브와 정점 사이의 관계는 B형 문제에서 자주 쓰이는 방법처럼 서로 배열을 통해 mapping 해주었다.

    시간복잡도: O(K*M)
    알고리즘: BFS 최단 거리 탐색
    실행시간: 164ms (C++)
*/

// i번째 tube가 갈 수 있는 곳?
// => 튜브를 정점처럼 생각하는 것 (정점들의 묶음)
// => 튜브를 사용한 BFS로 최단 거리 구하기

int N, K, M;
vector<int> tubes[100001]; // 각 역과 연결된 튜브 list
vector<int> trans[1001]; // 각 튜브에 포함된 역 list
bool vstStt[100001]; // visited station
bool vstTube[1001]; // visited tube

int bfs() {
	queue<pair<int, int>> Q;
	Q.push({ 1, 1 }); // 1번에서 출발
	vstStt[1] = true;

	while (!Q.empty()) {
		int stt = Q.front().first;
		int dist = Q.front().second;
		Q.pop();

		if (stt == N) return dist; //최단 거리 반환

		// 현재 역에 연결된 모든 튜브 탐색
		for (int t : tubes[stt]) {
			if (!vstTube[t]) { // 해당 튜브를 방문하지 않은 경우
				vstTube[t] = true;

				for (int nxtStt : trans[t]) {
					if (!vstStt[nxtStt]) {
						vstStt[nxtStt] = true;
						Q.push({ nxtStt, dist+1 });
					}
				}
			}
		}
	}
	return -1; // 도달할 수 없는 경우
}

int main() {
	cin.tie(0)->ios_base::sync_with_stdio(false);

	cin >> N >> K >> M;

	for (int i = 1; i <= M; i++) {
		for (int j = 0; j < K; j++) {
			int num;
			cin >> num;

			tubes[num].push_back(i); // 역에 연결된 튜브 저장
			trans[i].push_back(num); // 튜브에 포함된 역 저장
		}
	}

	int ret = bfs();
	cout << ret;
	return 0;
}
