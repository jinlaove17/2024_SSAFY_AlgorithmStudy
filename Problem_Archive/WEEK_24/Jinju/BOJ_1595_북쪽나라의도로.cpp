#include <iostream>
#include <cstring>
#include <vector>
using namespace std;
using pii = pair<int, int>;

/* 문제 접근
    특정 도시를 두 번 이상 지나지 않고서 임의의 두 도시를 이동하는 경로가 유일하다.
    모든 도시는 다른 모든 도시로 이동할 수 있다. => tree 구조

    트리의 노드가 최대 10000개 있을 때, 가장 거리가 먼 두 도시간의 거리 찾기
    -> 트리의 지름을 찾는 문제
    -> well-known
        1. 한 점을 기준으로 dfs 로 가장 깊은 곳(먼 거리)까지 내려감
        2. 그 점에서 출발해서 가장 먼 점을 찾는다

    알고리즘: tree dfs
    시간 복잡도: O(N)
    소요 시간: 0 ms
 */

vector<vector<pii>> tree(10001);
bool vst[10001];
int ret = 0;
int edgeNode = 0;

void dfs(int node, int wSum) {
    if(wSum >= ret){
        ret = wSum;
        edgeNode = node;
    }

    for(pii nxt: tree[node]) {
        int nxtNode = nxt.first;
        int nxtWeight = nxt.second;
        if(!vst[nxtNode]) {
            vst[nxtNode] = true;
            dfs(nxtNode, wSum + nxtWeight);
            vst[nxtNode] = false;
        }
    }
}

int main() {
    cin.tie(0)->sync_with_stdio(0);
    int u, v, w, root = -1;

    while(cin >> u >> v >> w) {
        root = u; // 임의의 시작 정점
        tree[u].emplace_back(v, w);
        tree[v].emplace_back(u, w);
    }

    if(root == -1) { //입력이 empty 인 경우
        cout << ret << "\n";
    }
    else {
        vst[root] = true;
        dfs(root, 0);
        int st = edgeNode;

        // 다음 ed를 찾기 위한 초기화
        memset(vst, false, sizeof(vst));
        ret = 0;
        vst[st] = true;
        dfs(st, 0);

        cout << ret << "\n";
    }
    return 0;
}
