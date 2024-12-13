#include <iostream>
#include <vector>
using namespace std;
using ll = long long;
const int MAX = 300000; // 최대 정점 수
ll comb[4][MAX+1]; // 조합 전처리 값
int N;

/* 문제 접근
  최대 30만개의 노드가 있는 트리에서 특정 트리의 개수를 세어야 한다.

  1. G Tree
  일단 ㅈ(G) tree 를 자세히 보면 생각보다 구하기 쉽다. 가운데에 한 정점을 기준으로 추가 노드가 3개 달려있으면 G 트리가 된다.
  따라서, 한 정점을 기준점으로 잡고, 그 정점에 달려있는 노드 개수가 3 이상이라면, nC3 계산을 통해 G트리를 찾으면 된다.
  여기서 주의할 점은, 조합 계산할 때 시간 위험(node 최대 30만이므로) 이 있을 수 있으므로 전처리를 통해 필요한 조합 값을 계산해놓고 가져다쓰기!

  2. D tree
  ㄷ(D) tree 를 정확히 찾는 방법이 좀 더 까다로울 수 있는데 1번에서는 정점을 기준으로 찾았으면, 2번에서는 간선을 기준으로 세는게 더 좋다.
  tree를 볼 때 정점 기준, 간선 기준으로 둘 다 볼 수 있도록 훈련시키는 문제라고 생각한다.
  만약, u에 연결된 노드 v가 있다고 할 때
    u에서 연결된 간선 개수가 2개 이상이고, v에서도 연결된 간선 개수가 2개 이상일 때만 D tree가 가능하다.
    왜 2개 이상이어야 하나면, 이미 u-v 연결 중인 간선 1개가 있으므로 이 간선을 제외하고 다른 노드에 연결된 간선이 각각 하나씩 더 있어야 한다는 의미이다.
    즉, u-v 간선을 가운데 기준으로 두었을 때 양 옆에 간선이 몇 개 달려있는지 세면 모든 ㄷ 모양을 셀 수있다.
  여기서 주의할 점은, 이 간선 Counting 구현 시, 이중 for문을 사용하면 간선 쌍이 중복 계산된다. (u-v) 기준과 (v-u) 기준
    -> 따라서, 동일한 D tree가 두 번씩 세어지므로 마지막에 2로 나눠야 함

  알고리즘: tree의 성질 파악 + 조합 전처리

  시간 복잡도: O(MAX node 개수)
    - 조합 전처리는 30만 만큼 반복문
    - G tree 탐색은 전체 트리 탐색이므로 O(N) 역시 노드 개수에 비례
    - D tree 탐색도 간선 기준으로 전체 트리 탐색이기에 O(N-1)
    따라서 MAX node 개수에 수렴한다.

*/

// 조합 전처리 함수, 우리는 nC3 만 필요하므로 파스칼 삼각형 방식을 통해 nC0 ~ nC3 까지만 저장한다.
void precomputeComb() {
    for (int r = 0; r <= 3; ++r) {
        for (int n = 0; n <= MAX; ++n) {
            if (r > n) comb[r][n] = 0;
            else if (r == 0 || r == n) comb[r][n] = 1;
            else comb[r][n] = comb[r-1][n-1] + comb[r][n-1];
        }
    }
}

int main() {
    cin.tie(0)->sync_with_stdio(0);
    cin >> N;
    vector<vector<int>> tree(N + 1);

    for (int i = 0; i < N-1; ++i) {
        int u, v;
        cin >> u >> v;
        tree[u].push_back(v);
        tree[v].push_back(u);
    }

    // 조합 전처리
    precomputeComb();
    ll gCnt = 0, dCnt = 0;

    // ㅈ(G) 모양 계산
    for (int i = 1; i <= N; ++i) {
        int degree = tree[i].size();
        if (degree >= 3) {
            gCnt += comb[3][degree];
        }
    }

    // ㄷ(D) 모양 계산
    for (int u = 1; u <= N; ++u) {
        for (int v : tree[u]) {
            if (tree[u].size() > 1 && tree[v].size() > 1) {
                dCnt += (tree[u].size()-1) * (tree[v].size()-1);
            }
        }
    }

    // 간선 쌍이 중복 계산되므로 2로 나눠줌
    dCnt /= 2;

    if (dCnt > gCnt*3) cout << "D";
    else if (dCnt < gCnt*3) cout << "G";
    else cout << "DUDUDUNGA";

    return 0;
}
