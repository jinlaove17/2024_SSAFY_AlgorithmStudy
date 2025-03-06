/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제를 보고 맨 처음에는 무지성 DFS를 돌릴려고 했는데 info의 길이가 최대 40이여서 최악의 경우 2^40이라는 미친 시간복잡도를 알게되면서 제 PC한테 미안해졌습니다.
    - 일반적으로 DFS로 시간초과가 발생할 시에 보통 DP를 통해서 풀면 풀린다는 것을 많이 경험했습니다.
    - DP를 사용할 때 두 도둑(A, B)이 경찰에 붙잡히지 않으면서 A의 흔적을 최소화하는 것이 목표이므로 현재까지 남긴 B 도둑의 흔적 개수에 대한 A 도둑의 최소 흔적 값을 저장하는 DP 테이블을 설계했습니다.
    - DP 테이블 정의: dp[i][j]는 i번째 물건까지 고려했을 때 B도둑이 j개의 흔적을 남길 경우 A도둑의 최소 흔적 값입니다.
    - 최종적으로 모든 물건을 고려한 후 B도둑의 흔적이 m보다 작은 경우 중에서 A도둑의 최소 흔적 값을 구합니다.
    - 만약 A의 흔적이 경찰에 붙잡히는 최소값 n 이상이면 -1을 반환합니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(K * M) (K는 info의 길이, M은 m의 값)
    - DP : O(K * M) (K는 info의 길이, M은 m의 값)
    - 전체 시간복잡도: O(K * M) (K는 info의 길이, M은 m의 값)

실행 시간
   - 0.53ms(java)
*/
import java.util.*;
class Solution {
    int INF = 121;
    public int solution(int[][] info, int n, int m) {
        int answer = INF;
        int size = info.length;
        int[][] dp = new int[size+1][m];
        for(int i=0; i<=size; i++){
            Arrays.fill(dp[i], INF);
        }
        dp[0][0]=0;
        for(int i=1; i<=size; i++){
            int a = info[i-1][0];
            int b = info[i-1][1];
            for(int j=0; j<m; j++){
                dp[i][j] = Math.min(dp[i-1][j]+a, dp[i][j]);
                if(j+b < m){
                    dp[i][j+b] = Math.min(dp[i][j+b],dp[i-1][j]);
                }
            }
        }
        for(int j=0; j<m; j++){
            answer = Math.min(answer, dp[size][j]);
        }
        if(answer >=n)
            answer=-1;
        return answer;
    }
}