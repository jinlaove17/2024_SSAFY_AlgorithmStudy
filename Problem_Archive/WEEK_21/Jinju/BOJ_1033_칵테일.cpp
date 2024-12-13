#include <iostream>
#include <vector>
#include <queue>
using namespace std;
using ll = long long;
using vi = vector<int>;
using pipii= pair<int, pair<int, int>>;
int N;
ll ret[10];
vector<pipii> graph[10];

/* 문제 접근
  처음에 a b p q 형태와, 첫 번째 TC를 보고 a node, b node 연결 형태와 p, q 비율을 활용한 간선으로 나타낼 수 있겠다고 생각했다.
  특히, 이 힌트를 얻은 이유는 TC1이 매우 친절했기 때문인데, 4번 노드를 중심으로 다 붙어있는 형태의 그래프를 금방 떠올릴 수 있게 했다.
  또한, 해당 TC를 통해 하나의 노드에 붙어있는 형태에서 공통 비율 처리를 어떻게 해야 할지? 도 힌트를 주었다고 생각한다.
   -> 최대공약수, 최소공배수 등 여러 수들의 비율을 정수로 나타낼 수 있는 방법을 떠올릴 수 있다.

  알고리즘: 비율을 그래프로 나타내어 전체 그래프 탐색 + GCD, LCM 계산을 통해 최적 정수 비율 찾기
  
  구현 순서
    1. 간선의 비율 정보(p, q)를 이용해 그래프를 인접 리스트 형태로 구성
    2. 모든 비율이 계산 가능하도록 그래프 전체의 최소공배수(totalLCM)를 기준값으로 설정
    3. BFS를 이용해 특정 기준 노드(0번 노드)에서 시작해 모든 노드의 값을 계산
    4. 계산된 노드 값들의 최대공약수(retGCD)를 구해 이를 기준으로 가장 간단한 정수 비율로 변환 (이미 LCM을 통해 충분히 커져있는 정수를 줄이고자 함)
  
  시간 복잡도: O(N * (log K)) 여기서 log K는 유클리드 호제법으로 GCD, LCM 계산 시 log(min(a, b)) 에 따르며, a, b가 아무리 커봤자... 작은 수준으로 log K는 무시해도 된다.
  
*/

// 유클리드 호제법을 사용한 GCD 계산
ll GCD(ll a, ll b){
    if (b==0) return a;
    return GCD(b, a%b);
}

ll LCM(ll a, ll b){
    return a*b/GCD(a, b);
}

int main(){
    cin.tie(0)->sync_with_stdio(0);
    cin >> N;

    ll totalLCM = 1;
    for(int i=0; i<N-1; ++i){
        int a, b, p, q;
        cin >> a >> b >> p >> q;

        graph[a].push_back({b, {p, q}});
        graph[b].push_back({a, {q, p}});
        totalLCM *= LCM(p, q);
    }

    // BFS, 0번 기준으로 시작하기
    ret[0] = totalLCM;
    queue<int> Q;
    Q.push(0);

    while(!Q.empty()){
        int now = Q.front();
        Q.pop();

        // <first, <seconde.first, second.seconde>> == <다음 노드,  <p, q>>
        for(auto nxt: graph[now]){
            if(ret[nxt.first]) continue;
            ret[nxt.first] = (ret[now] * nxt.second.second) / nxt.second.first;
            Q.push(nxt.first); // 다음 노드 넣기
        }
    }

    ll retGCD = ret[0];
    for(int i=1; i<N; ++i) retGCD = GCD(retGCD, ret[i]); //retGCD 찾기

    //마지막에 GCD로 나눠서 가장 간단한 정수 비율 찾기
    for(int i=0; i<N; ++i) cout << ret[i]/retGCD << " ";
    return 0;
}
