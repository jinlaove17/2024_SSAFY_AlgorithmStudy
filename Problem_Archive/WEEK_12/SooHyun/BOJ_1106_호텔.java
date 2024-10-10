/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 확인했을 때, DP를 활용해서 풀어야겠다는 생각을 했다
	- 대부분의 DP 문제가 그럿듯이 문제를 얼마나 작은 문제로 분할하는지가 관건이였다.
	- 이 문제는 최소의 비용으로 특정 수 이상의 고객을 유치하는 것이 목표다.
    - 따라서 각 비용에 대해 유치 가능한 최대 고객 수를 고려해 DP 배열을 사용해 문제를 해결할 수 있다고 판단했다.
    - dp[i]를 i명의 고객을 유치하기 위해 필요한 최소 비용으로 정의하고 고객 수와 비용 정보를 사용해 값을 갱신했다.

시간 복잡도
	- 외부 루프문 N번을 돌고 내부 루프문은 최악의 경우 C+100만큼의 반복문을 순회한다. 따라서 시간 복잡도의 경우 O(N * (C+100))이다.

실행 시간
	- 100ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int C;
    static int N;
    static int[] cost;
    static int[] people;
    static int[] dp;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        cost = new int[N];
        people = new int[N];
        dp = new int[C+101];
        Arrays.fill(dp, 100001);
        dp[0]=0;
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            cost[i] = Integer.parseInt(st.nextToken());
            people[i] = Integer.parseInt(st.nextToken());
            for(int j=people[i]; j<C+101; j++) {
                dp[j] = Math.min(dp[j], cost[i]+dp[j-people[i]]);
            }
        }
        int result = Integer.MAX_VALUE;
        for(int i=C; i<C+101; i++) {
            result = Math.min(result, dp[i]);
        }
        System.out.println(result);
    }

}
