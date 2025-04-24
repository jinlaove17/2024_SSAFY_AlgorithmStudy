/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 이거 내가 냈지만 문제가 참 ㅎㅎ........
    - 문제는 DP입니다. 처음에는 1차원 DP를 사용하려고 했으나 어디서부터 어디까지가 중요하다고 판단해서 2차원 DP를 사용했습니다.
    - 처음 초기화 과정에서 길이가 2인 substring인 것 중에 'at', 'gc'인 경우에 dp[i][i+1] = 2로 초기화합니다. 그리고 그 경우 최소한 길이가 2인 것은 가능하므로 result 또한 2로 초기화합니다.
    - dp[i][i+k]는 문자열에서 인덱스 i부터 i+k까지의 짝이 맞는 쌍의 최대 길이 합입니다.
    - k는 문자열의 길이 -1부터 시작합니다. 핵심은 점화식을 이용합니다.
    - 경계 문자문 짝이 맞는 경우 즉 양 끝쌍인 경우 그 사이 구간을 활용해 값을 갱신합니다.
    - 이후 분할 정복을 통해 i에서 i+k 구간을 j로 나누고 두 조각의 점수를 합친 것과 그 자체의 부분 문자열 중 최대값을 갱신하면서 정답을 도출합니다.


시간 복잡도
    - 길이 기준 반복 : O(N) (단 N은 문자열의 길이)
        - 시작 인덱스 반복 : O(N)
        - 분할 정복 : O(N)
    - 전체 시간복잡도 : O(N ^ 2) (N은 문자열의 길이)

실행 시간
   - 196ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[][] dp;
    static int len;
    static int result=0;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        len = str.length();
        dp = new int[len+1][len+1];
        for(int i=0; i<len-1; i++) {
            String sub = str.substring(i, i+2);
            if(sub.equals("at") || sub.equals("gc")) {
                result=2;
                dp[i][i+1]=2;
            }
        }
        for(int k=2; k<len; k++) {
            for(int i=0; i<len-k; i++) {
                int temp =0;
                if(str.charAt(i) == 'a' && str.charAt(i+k) == 't') {
                    temp=2;
                }
                if(str.charAt(i) == 'g' && str.charAt(i+k) == 'c') {
                    temp=2;
                }
                dp[i][i+k] = Math.max(dp[i][i+k], dp[i+1][i+k-1] + temp);
                for(int j=i; j<i+k; j++) {
                    dp[i][i+k] = Math.max(dp[i][i+k], dp[i][j]+ dp[j+1][i+k]);
                }
                result = Math.max(result, dp[i][i+k]);
            }
        }
        System.out.println(result);
    }
}