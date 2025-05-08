/**
 * 20181 꿈틀꿈틀 호석 애벌레 - 효율성
 * https://www.acmicpc.net/problem/20181
 *
 * @author minchae
 * @date 2025. 5. 7.
 *
 * 문제 풀이
 * - K 범위보고 이건 이분탐색 써야되나 생각. 투포인터..?
 * - 연속된 부분 합 구하는게 아니라 겹치지 않는 여러 구간 선택 -> 먹이 먹을지 말지 결정하는 부분
 *   -> dp를 사용해 최적 해 구하기 => 1차원 배열 이용, dp[i]에 에너지 최댓값 저장
 * 
 * - 투포인터 + dp 이용 -> K이상이면 dp배열 갱신
 *
 * 시간 복잡도
 * O(N)
 *
 * 실행 시간
 * 300 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        long K = Long.parseLong(st.nextToken());

        long[] eat = new long[N + 2];
        long[] dp = new long[N + 2];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
        	eat[i] = Long.parseLong(st.nextToken());
        }

        long sum = 0;
        int start = 0;

        for (int end = 1; end <= N; end++) {
            sum += eat[end]; // 먹이 합 더하기
            dp[end] = dp[end - 1]; // 최소 만족도를 만족하지 않는 경우에는 이전 값을 그대로 사용
            
            // 최소 만족도 이상인 경우
            while (sum >= K) {
            	// 새로운 먹이를 선택했을 때와 비교
                dp[end] = Math.max(dp[end], dp[start] + sum - K);
                sum -= eat[++start];
            }
        }

        System.out.println(dp[N]);
    }
}
