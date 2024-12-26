#include <iostream>
#include <vector>
#include <queue>
using namespace std;
using ll = long long;
using pii = pair<int, int>;
using vi = vector<int>;

/* 문제 접근
    기존 ranks 가 주어지고, 이 ranks의 순서를 뒤바꿀 역전 간선이 주어진다.
    우선 첫번째 ranks를 기준으로 유향 그래프(DAG) 를 만들고, 역전 간선을 통해 그래프를 재구성 해보자는 마인드로 진행

    구현 순서
    1. 기존 ranks는 순서 확인용으로 두고, 이 배열을 기준으로 DAG를 만든다
    2. 해당 DAG를 가지고 M개의 주어진 명령들의 연결 관계를 역전시키면서 그래프의 상태 관찰하기
      -> inDegree == 0 인게 여러 개 있는 경우 (순서 정립 불가)
      -> Cycle이 있는 경우 (순위 정하기 Impossible)
      -> 순서 확립이 되는 경우는 결과를 출력하면 된다.

    위상정렬 판단은 DFS 방식과 BFS 방식 모두 사용 가능하나, BFS 방식이 3가지 경우 판단에 더 쉬울 것 같다고 판단했다.
    만약 DFS나 Stack으로 순서를 확인하고자 한다면,
     -> 탐색 시 넣을 수 있는 노드가 여러 개 일 때 순서 정립 불가
     -> Cycle 확인은 방문 처리 중복 체크를 통해 확인으로 가능할 것 같다.

     알고리즘: 위상 정렬
     시간 복잡도: O(N^2) (초기화, adj 간선 연결, 위상 정렬 큐에서 뽑아서 탑색하는 것들 등등 모두 포함)
     실행시간: C++ 28ms
     
*/

int T, N, M;
int ranks[501];
int inDegree[501];
bool adj[501][501];

void init(){
    for(int i=1; i<=500; ++i){
        ranks[i] = 0;
        inDegree[i] = 0;
        for(int j=1; j<=500; ++j) {
            adj[i][j] = false;
        }
    }
}

int main() {
    cin.tie(0)->sync_with_stdio(0);
    cin >> T;

    while(T--){
        cin >> N;
        init();

        for(int i=1; i<=N; ++i) cin >> ranks[i];
        for(int i=1; i<=N; ++i) {
            for(int j=1; j<i; ++j) {
                adj[ranks[j]][ranks[i]] = true;
                inDegree[ranks[i]]++;
            }
        }

        cin >> M;
        for(int i=0; i<M; ++i){
            int a, b;
            cin >> a >> b;
            if(adj[a][b]) {
                adj[a][b] = false;
                adj[b][a] = true;
                inDegree[a]++;
                inDegree[b]--;
            }
            else if(adj[b][a]) {
                adj[b][a] = false;
                adj[a][b] = true;
                inDegree[b]++;
                inDegree[a]--;
            }
        }

        queue<int> Q;
        for(int i=1; i<=N; ++i) if(inDegree[i] == 0) Q.push(i);

        vi newRanks;
        bool isPossible = true;
        bool isOneRet = true;

        for(int i=0; i<N; ++i){
            if(Q.empty()){
                isPossible = false;
                break;
            }
            int now = Q.front();
            Q.pop();

            if(Q.size() > 0){
                isOneRet = false;
            }else{
                newRanks.push_back(now);
            }

            for(int j=1; j<=500; ++j){
                if(adj[now][j])
                    if(--inDegree[j] == 0)
                        Q.push(j);
            }
        }

        if(!isPossible) cout << "IMPOSSIBLE\n";
        else if(!isOneRet) cout << "?\n";
        else {
            for(int i=0; i<N ; ++i) cout << newRanks[i] << " ";
            cout << "\n";
        }
    }
    return 0;
}
