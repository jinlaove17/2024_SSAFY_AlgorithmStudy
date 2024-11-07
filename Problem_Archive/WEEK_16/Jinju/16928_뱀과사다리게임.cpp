#include <iostream>
#include <vector>
#include <deque>
using namespace std;

/*
  문제 접근
  1번부터 100번까지의 칸을 BFS로 탐색하면서, 각 칸을 최소 횟수로 도달하는 방법 -> BFS 최단거리
  주사위, 사다리, 뱀을 고려해서 각 칸에 도달하는 최소 횟수를 기록한다.
  
  시간복잡도: O(100)
  알고리즘: BFS
  실행시간: 0ms (C++)
*/

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(0);

    int N, M;
    cin >> N >> M;

    vector<int> board(101);
    vector<int> dist(101, 0);

    for (int i = 1; i <= 100; i++) {
        board[i] = i;
    }

    for (int i = 0; i < N + M; i++) {
        int a, b;
        cin >> a >> b;
        board[a] = b;
    }

    deque<int> Q;
    Q.push_back(1);

    while (!Q.empty() && Q.front() != 100) {
        int path = Q.front();
        Q.pop_front();

        for (int d = 1; d <= 6; d++) { // 주사위 굴리기
            if (path + d <= 100) {
                int nextPos = board[path + d];
                if (dist[nextPos] == 0) {
                    Q.push_back(nextPos);
                    dist[nextPos] = dist[path] + 1;
                }
            }
        }
    }
    cout << dist[100];
    return 0;
}
