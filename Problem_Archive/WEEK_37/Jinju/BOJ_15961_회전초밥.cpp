/*
N: 접시 수 (회전 초밥 벨트 위 접시 수)
D: 초밥의 가짓수
K: 연속해서 먹는 접시 수
C: 쿠폰 번호 (이 초밥은 추가로 무료로 하나 더 먹을 수 있음)
arr: 회전 초밥 벨트 위에 놓인 초밥 번호
벨트는 원형이기 때문에 인덱스를 순환해야 함 (예: i + K >= N이면 처음부터 다시 시작)

사용 알고리즘: 슬라이딩 윈도우를 이용해, 길이 K짜리 구간을 한 칸씩 오른쪽으로 이동하면서 중복을 제외한 초밥 가짓수를 셈
쿠폰 초밥 번호(C)가 현재 구간에 없다면 +1 해서 최대값 갱신
arr[i]는 초밥 번호, vst[i]는 해당 초밥 번호가 몇 개 있는지 배열에 저장 */

#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
int N, D, K, C;
vector<int> arr;

int main(){
    cin.tie(0)->sync_with_stdio(0);
    cin >> N >> D >> K >> C;

    for(int i=0; i<N; ++i) {
        int tmp;
        cin >> tmp;
        arr.push_back(tmp);
    }

    vector<int> vst(D+1);
    int res = 0;
    int cnt = 0;

    for(int i=0; i<K; ++i){
        if (!vst[arr[i]]) cnt += 1;
        vst[arr[i]] += 1;
    }
    res = cnt;

    for(int left=0; left<N; ++left){
        int right=(left+K)%N;
        int front = arr[left];
        int end = arr[right];

        vst[front] -= 1;
        if(!vst[front]) cnt -= 1;

        vst[end] += 1;
        if(vst[end]==1) cnt += 1;

        if(vst[C]==0) res = max(res, cnt+1);
        else res = max(res, cnt);
        if (res==K+1) break;
    }
    cout << res << "\n";
    return 0;
}
