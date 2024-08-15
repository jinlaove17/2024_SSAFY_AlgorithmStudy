#include <iostream>
#include <vector>
using namespace std;
 
int T;
int V, E, A, B;
vector<bool> vst;
vector<int> depth;
vector<int> parent;
int LCA, Tsize;
 
void dfs_depth_and_parent(vector<vector<int>>& tree, int node, int d, int p) {
    vst[node] = true;
    depth[node] = d;
    parent[node] = p;
 
    for (auto nxt : tree[node]) {
        if (!vst[nxt]) {
            dfs_depth_and_parent(tree, nxt, d + 1, node);
        }
    }
}
 
int dfs_size(vector<vector<int>>& tree, int node) {
    int subSize = 1;
 
    for (auto nxt : tree[node]) {
        if (!vst[nxt]) {
            vst[nxt] = true;
            subSize += dfs_size(tree, nxt);
            vst[nxt] = false;
        }
    }
 
    return subSize;
}
 
int main(void) {
    cin.tie(0);
    cout.tie(0);
    ios::sync_with_stdio(0);
 
    cin >> T;
 
    for (int t = 1; t <= T; t++) {
        cin >> V >> E >> A >> B;
 
        vector<vector<int>> tree(V + 1);
        vst.assign(V + 1, false);
        depth.assign(V + 1, 0);
        parent.assign(V + 1, 0);
 
        for (int i = 0; i < E; i++) {
            int p, c;
            cin >> p >> c;
            tree[p].push_back(c);
        }
 
        // 1. 단일 DFS로 각 노드의 깊이와 부모 노드 기록
        vst.assign(V + 1, false);
        dfs_depth_and_parent(tree, 1, 0, -1);
 
        // 2. 깊이 맞추기
        int da = depth[A];
        int db = depth[B];
        int pa = A;
        int pb = B;
 
        while (da > db) {
            pa = parent[pa];
            da--;
        }
        while (db > da) {
            pb = parent[pb];
            db--;
        }
 
        // 깊이 맞춘 후 공통 조상 찾기
        while (pa != pb) {
            pa = parent[pa];
            pb = parent[pb];
        }
 
        LCA = pa;
 
        // 3. 서브트리의 크기 찾기
        vst.assign(V + 1, false);
        Tsize = dfs_size(tree, LCA);
 
        cout << "#" << t << " " << LCA << " " << Tsize << "\n";
    }
    return 0;
}
