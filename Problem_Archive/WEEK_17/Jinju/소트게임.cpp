#include <iostream>
#include <unordered_set>
#include <queue>
#include <algorithm>
using namespace std;

/*
	문제 접근
	우리가 결과로 원하는 오름차순 배열을 미리 정렬해서 만든다.
	만든 후에 해당 배열과 일치하는지 확인한다.
	N의 범위가 작으므로 모든 K에 대해서 가능한지 확인할 수 있어서 처음에 DFS로 접근했으나
  문제를 잘못 이해한 것이었고, 최소 횟수를 구하는 것을 뒤늦게 이해하여 BFS와 unordered_set 사용
  여기서 map 대신 이걸 사용한 이유는 값의 존재 여부와 중복 처리만 하면 되기 때문이다.
*/

int N, K;
string target;

bool isTarget(const string &state) {
    return state == target;
}

string reverseSubstring(const string &s, int start, int k) {
    string temp = s;
    reverse(temp.begin() + start, temp.begin() + start + k);
    return temp;
}

int bfs(string &initial) {
    unordered_set<string> visited;
    queue<pair<string, int>> q;
    q.push({initial, 0});
    visited.insert(initial);

    while (!q.empty()) {
        string curr = q.front().first;
        int depth = q.front().second;
        q.pop();

        if (isTarget(curr)) {
            return depth;
        }

        for (int i = 0; i <= N - K; i++) {
            string next = reverseSubstring(curr, i, K);
            if (visited.count(next) == 0) {
                visited.insert(next);
                q.push({next, depth + 1});
            }
        }
    }
    return -1;
}

int main() {
    cin.tie(0)->ios_base::sync_with_stdio(false);
    cin >> N >> K;

    string initial(N, ' ');
    for (int i = 0; i < N; i++) {
        int num;
        cin >> num;
        initial[i] = '0' + num;
    }

    target = initial;
    sort(target.begin(), target.end());

    int result = bfs(initial);
    cout << result << "\n";
    return 0;
}
