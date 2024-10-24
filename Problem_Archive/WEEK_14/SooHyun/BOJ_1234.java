/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 읽었을 때 dp 냄새가 강하게 났습니다. dp 문제에서 핵심은 테이블 설계인데 이부분에 가장 많은 시간을 쏟았습니다.
	- long 타입의 4차원 배열을 선언했습니다. N번째 층 / 사용된 R의 갯수 / 사용된 G의 갯수 / 사용된 B의 갯수를 의미합니다.
	- 초기에 N의 층에서 N*(N+1)/2 개의 칸을 채워야하므로 만약 주어진 전구의 총갯수가 그 보다 작다면 애당초에 만들 수 없으므로 o을 출력하고 종료시켰습니다.
	- 4차례의 반복문을 수행해 dp를 구현했습니다.
	- 초기 0층의 경우에는 무조건 가능하므로 모든 가짓수에 대해 1로 초기화했습니다.
	- 모든 층에 대해서는 1가지 색깔로 칠할 수 있는지 판단하는 작업을 수행합니다.
	- 2의 배수 층에 대해서는 추가적으로 2가지 색깔로 칠할 수 있는지 판단하는 작업을 수행합니다.
	- 2가지 색으로 칠할 수 있는 경우 해당 층에서 가능한 경우의 수는 fac(해당 층의 갯수) / ( fac(해당 층에서 색깔 1의 갯수) * fac(해당 층에서 색깔 2의 갯수))
	- 3의 배수 층에 대해서는 추가적으로 3가지 색깔로 칠할 수 있는지 판단하는 작업을 수행합니다.
	- 3가지 색으로 칠할 수 있는 경우 해당 층에서 가능한 경우의 수는 fac(해당 층의 갯수) / ( fac(해당 층에서 색깔 1의 갯수) * fac(해당 층에서 색깔 2의 갯수) * fac(해당 층에서 색깔 3의 갯수))
	- N번째 층 / R의 총 갯수 / G의 총 갯수 / B의 총 갯수에 해당하는 배열 값을 출력해 정답을 도출합니다.

시간 복잡도
	- DP 배열 계산 과정 : 4중 루프가 등장하고 N, R, G, C에 대해 각각 최대값까지 반복문이 수행되어 O(N * R * G * B)의 시간복잡도를 가집니다.
	- comb 함수 : 조합을 계산하는 factorial 함수는 재귀적으로 O(n)의 시간 복잡도를 가집니다.
	- 전체 시간복잡도 : O(N * R * G * B)

실행 시간
	- 624ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] color;
    static long[][][][] dp;
    static int total_color=0;
    static int total_board=0;;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        total_board=((N+1)*N)/2;
        color = new int[3];
        for(int i=0; i<3; i++) {
            color[i] = Integer.parseInt(st.nextToken());
            total_color+=color[i];
        }
        if(total_board> total_color) {
            System.out.println(0);
            return;
        }
        dp=new long[N+1][color[0]+1][color[1]+1][color[2]+1];
        for(int i=0; i<=N; i++) {
            for(int r=0;r<=color[0]; r++) {
                for(int g=0; g<=color[1]; g++) {
                    for(int b=0; b<=color[2]; b++) {
                        if(i==0) {
                            dp[i][r][g][b]=1;
                            continue;
                        }
                        if(r-i>=0) {
                            dp[i][r][g][b]+= dp[i-1][r-i][g][b];
                        }
                        if(g-i>=0) {
                            dp[i][r][g][b]+= dp[i-1][r][g-i][b];
                        }
                        if(b-i>=0) {
                            dp[i][r][g][b]+= dp[i-1][r][g][b-i];
                        }
                        if(i%2==0) {
                            int divByTwo = i/2;
                            if(r-divByTwo >=0 && g-divByTwo>=0) {
                                dp[i][r][g][b]+=dp[i-1][r-divByTwo][g-divByTwo][b]*comb(i,divByTwo);
                            }
                            if(r-divByTwo >=0 && b-divByTwo>=0) {
                                dp[i][r][g][b]+=dp[i-1][r-divByTwo][g][b-divByTwo]*comb(i,divByTwo);
                            }
                            if(g-divByTwo >=0 && b-divByTwo>=0) {
                                dp[i][r][g][b]+=dp[i-1][r][g-divByTwo][b-divByTwo]*comb(i,divByTwo);
                            }
                        }
                        if(i%3==0) {
                            int divByThree = i/3;
                            if(r-divByThree >=0 && g-divByThree>=0 && b-divByThree>=0) {
                                dp[i][r][g][b]+=dp[i-1][r-divByThree][g-divByThree][b-divByThree]*comb(i,divByThree)*comb(i-divByThree,divByThree);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(dp[N][color[0]][color[1]][color[2]]);

    }

    public static int factorial(int num) {
        if(num==1)
            return 1;
        return num*factorial(num-1);
    }

    public static int comb(int n, int r) {
        return factorial(n)/(factorial(r)*factorial(n-r));
    }
}
