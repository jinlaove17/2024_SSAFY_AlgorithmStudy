#include <iostream>
using namespace std;
using ull = unsigned long long;

int T;
ull S, A, B, K;

/* 문제 접근
  수 크기가 만만치 않아서... 무조건 수학적인 계산을 이용해서 풀어야 겠다고 생각했다.
  x, y로 두고 일반항이나 규칙이 있는지 찾아보았는데, 일반항 비슷한? 걸 찾았는데 코드로 정리가 잘 안되었다.
  일반항을 코드로 옮긴 사람들 정보를 찾아보고 풀려고 했는데, 일반항을 찾은 후에 한 번 더 처리가 필요하다.

  아무래도 수 크기가 엄청 크다 보니 일반항 형태에서 계속 곱하는 형태로 power를 구하기 힘들어 보였고,
  이를 분할 정복을 통해 곱연산을 빨리 처리해주는게 핵심이었다.

  알고리즘: 수학 + 분할 정복을 이용한 곱 연산 최적화
  시간: 6ms
*/

// 분할 정복을 통한 빠른 곱 계산 
ull calPow(ull N) {
    // 기저 조건
    if(N == 0) return 1;
    if(N == 1) return 2;
    
    ull ret = calPow(N/2);
    ret = (ret*ret) % S;
    if(N%2 == 1) ret = (2 * ret) % S;
    
    return ret;
}

int main(){
    cin.tie(0)->sync_with_stdio(false);
    cin>>T;
    for(int tc=1; tc <= T; ++tc){
        cin>> A>> B>> K;
        S = A + B; //합을 이용해서 x, y 계산에 이용하면 일반항을 찾을 수 있다.
        
        ull x = (A * calPow(K)) % S;
        ull y = S - x;
        ull ret = min(x, y);
        cout << "#" << tc << " " << ret <<"\n";
    }
    return 0;
}
