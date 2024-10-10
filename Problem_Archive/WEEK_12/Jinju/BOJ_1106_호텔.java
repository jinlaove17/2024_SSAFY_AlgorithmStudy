/*
    문제 접근
    최소값을 구해야한다는 것을 보자마자 메모이제이션을 통한 dp 테이블 최소값 갱신을 떠올림
    또한, 가격이 제멋대로 주어진다는 점이 (서로 배수 관계가 아님) 더 dp가 맞다는 확신을 주었음
    대신 문제를 볼 때 가장 핵심적으로 본 부분이 있었는데 "적어도 C명 늘리기" 라는 부분임
     -> value*idx 칸에 배수 만큼 dp 값을 갱신해야 하는데, 적어도 C명을 늘려야하므로 이전 인덱스 value*(idx-1) + 1 ~ value*(idx) 칸까지 갱신해야한다.
     -> 적어도 C명 늘릴 때 최소값을 보려면, C까지만 dp 테이블을 갱신하는게 아니라 C 이상의 값도 생각해야 한다.
     -> 예를 들어서 C = 101 명이라고 하자, 그런데 10명씩 늘릴 때 1 value만 든다면 110 을 늘렸을 때 11 만큼만 소비해서 C를 채울 수 있음 (이게 최소가 될 수도 있음)
     -> 이런 경우를 세주려면 매 cost당 배수를 곱하면서 cost * n 이 C와 같거나(나누어 떨어지는 경우) 넘는 경우까지 테이블을 채워줘야함
     -> 해당 부분의 처리를 깔끔하게 하기 위해 dp 테이블의 최대 범위를 넉넉하게 2001 으로 설정해주었다.
     -> 이 부분은 2차적으로 이중 for문을 돌 때 계산이 완료된다

    문제의 제한이 1000인 것을 보아 더욱 N^2 dp가 가능하겠다는 확신만 주었다.

    알고리즘 : 메모이제이션을 통한 바텀업 dp

    시간 : 80ms
*/

import java.util.*;
import java.io.*;

public class Main {
    static int C, N;
    static int MAX = 100*1000+1;
    static int dp[] = new int[2001];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        // dp table MAX로 초기화
        for(int i=0; i<2001; i++){
            dp[i] = MAX;
        }

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int num = Integer.parseInt(st.nextToken());

            //1차적으로 입력에 대한 dp table 갱신
            fill_dp(cost, num);
        }

        //2차적으로 이중 for문을 통해 dp 값 최종 갱신
        for(int i=1; i <= 2000; i++){
            for(int j=1; j<i; j++){
                dp[i] = Math.min(dp[i], dp[i-j]+dp[j]);
            }
        }

        //C 이상부터 끝까지 돌면서 최소 찾기
        int ret = MAX;
        for(int i=C; i < 2001; i++){
            ret = Math.min(dp[i], ret);
        }
        System.out.println(ret);
    }

    public static void fill_dp(int cost, int num){
        // dp 초기화
        for(int i=1; i <= num; i++){
            dp[i] = Math.min(dp[i], cost);
        }

        int idx = 2;
        while (num*idx <= C){
            dp[num*idx] = Math.min(dp[num*idx], cost*idx);

            // 이전 인덱스부터 현재 자신의 인덱스까지 dp값 갱신
            for(int i=num*(idx-1); i <= num*idx; i++){
                dp[i] = Math.min(dp[i], cost*idx);
            }

            idx++;
        }
    }
}
