/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 처음 읽었을 때는 무지성 dfs를 통해서 문제를 풀어볼려고 했는데 N이 최대 1000개라는 조건으로 인해 최악의 경우 2^1000이라는 말도 안되는 시간 복잡도로 인해 포기했습니다.
	- 이후 수학적으로 접근을 해 봤습니다. 만약 특정 i 번째 도달했을 때 그것을 사용하는 경우, 사용하지 않는 경우라는 2가지 경우의 수를 떠올렸습니다.
	- 만약 i 번째를 사용하게 된 경우 그 경우에 j개를 사용한다면 그 전 i-1은 사용하지 못하므로 i-2번째까지 j-1개를 선택해야합니다.
	- 만약 i 번째를 사용하지 않는 경우 그 경우에 j개를 사용한다면 그 전 i-1은 사용할 수 있으므로 i-1번째까지 j개를 선택해야합니다.
	- 그리고 입력을 받았을 때 N, K를 받게 되었을 때 N이 2*(K-1)보다 작다면 어떠한 경우에도 선택할 수 없으므로 0을 출력하게 됩니다.
	- 이에 따라 dp 2차원 배열을 선언하고 그것의 의미를 i까지 중에서 j개를 선택하는 경우의 수라고 정의하고 점화식을 세워 문제를 해결하였습니다.
	- 마지막에 dp[N][K]의 경우는 dp[N-1][K] + dp[N-3][K-1]로 구할 수 있습니다.
	- dp[N-1][K]는 N-1까지 중에서 K개를 선택하는 경우의 수입니다.
	- dp[N-3][K-1]은 N-3까지 중에서 K-1개를 선택하는 경우의 수입니다. 여기서 N-3까지 선택하는 이유는 N을 선택하게 될 시에 그 전과 맨 처음은 사용하지 못하므로 N-3이 됩니다.

시간 복잡도
    - DP 배열 초기화 : O(N)
    - DP 테이블 계산 : O(N^2)
        - 바깥 루프 : O(N) : O(N+M)
        - 안쪽 루프 : O((i+1`)/2)
    - 전체 시간 복잡도 : O(N^2)

실행 시간
	- 116ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, K;
    static final int MOD = 1000000003;
    static int[][] dp;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine().trim());
        K = Integer.parseInt(st.nextToken());
        if(N <= 2*(K-1)) {
            System.out.println(0);
            return;
        }
        dp = new int[N+1][N+1];
        for(int i=0; i<=N; i++) {
            dp[i][1]=i;
            dp[i][0]=1;
        }
        for(int i=2; i<=N; i++) {
            for(int j=1; j<= (i+1)/2; j++) {
                dp[i][j] = (dp[i-2][j-1]+dp[i-1][j])%MOD;
            }
        }

        System.out.println((dp[N-1][K] + dp[N-3][K-1]) % MOD);
    }

}
