/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 백준 실버 1티어를 보고 간단할 것이라고 판단해 무지성 dfs를 돌리니 시간 초과가 발생했습니다.
	- 이후 2차원 배열을 활용한 dp로 접근했습니다.
	- dp[i][j]는 i번째 곡을 j의 볼륨으로 연주할 수 있는지 여부를 나타냅니다.
	- 처음 초기 상태는 i가 0이고 초기 볼륨 S에 해당하는 dp[0][S]를 true로 설정합니다.
	- 그리고 1~N번째 곡에 대해 이전 곡의 볼륨을 기준으로 다음 곡의 볼륨을 계산합니다.
	- 만약 이전 곡의 볼륨으로 j의 볼륨을 연주할 수 있다면 dp[i][j]를 true로 설정합니다.
	- 마지막으로 i = N일 때 즉 마지막까지 연주하는 경우에 대해 M부터 0까지 순회하며 dp[N][j]가 true인 경우가 가장 큰 볼륨이므로 j를 출력합니다.

시간 복잡도
	- DP 테이블 갱신 : O(N * M)
	- 최대 볼륨 탐색 : O(M)
	- 전체 시간 복잡도 : O(N * M)

실행 시간
	- 100ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, S, M;
    static int[] volume;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        volume = new int[N+1];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=1; i<=N; i++) {
            volume[i] = Integer.parseInt(st.nextToken());
        }
        boolean[][] dp = new boolean[N+1][M+1];
        dp[0][S]=true;
        for(int i=1; i<=N; i++) {
            for(int j=0; j<=M; j++) {
                if(dp[i-1][j]) {
                    int plus = j+volume[i];
                    int minus = j-volume[i];
                    if(plus <= M) {
                        dp[i][plus]=true;
                    }
                    if(minus >=0) {
                        dp[i][minus]=true;
                    }
                }
            }
        }
        int result=-1;
        for(int i=M; i>=0; i--) {
            if(dp[N][i]) {
                result=i;
                break;
            }
        }
        System.out.println(result);

    }
}
