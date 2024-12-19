/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 보고 나서 단순히 시키는데로 작업하면 되지 않을까 생각했으나 정답률을 보고 무지성으로 박으면 틀리겠다고 생각했습니다.
	- 문제의 핵심은 2배라고 생각을 했습니다. 그러나 A, B 중 어느 수가 큰지는 상황에 따라 달라지므로 그 부분을 고려해야 했습니다.
	- A, B를 더한 총 합은 사탕을 분배하더라도 변하지 않는 수이기에 이를 활용해야겠다고 판단했습니다.
	- 그래서 A, B 중 어느 수가 큰지를 판단하지 않고 모두 2배를 해봤습니다. 그리고 그 값들을 총합으로 나누고 난 나머지 값들이 분배 후의 A,B의 값이 된다는 것을 알게 됐습니다.
	- 주어지 K의 갑을 가지고 A, B에 2의 K 승을 곱한 이후 기존 총합으로 나눈 결과 값 중 작은 값이 결과값이 됩니다.
	- 여기서 2의 K승을 구하는 함수를 구현하는 것이 핵심인데 단순히 반복문을 통해 구현하면 시간초과가 발생할 것이라고 판단했습니다.
	- 그래서 재귀함수를 통해 구현하였습니다.
	- 구현한 재귀함수의 결과 값 즉 2의 K승을 초기 A, B에 곱한 이후 총합으로 나눈 값 중 더 작은 값을 출력함으로써 문제를 해결하였습니다.

시간 복잡도
    - 테스트 케이스 반복문: O(T)
	- 거듭제곱 계산 (재귀 함수 find_Pow) : O(logK)
    - 전체 시간복잡도: O(T * logK)

실행 시간
	- 122ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static int T;
    static long A, B;
    static long K;
    static long sum;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        T = Integer.parseInt(st.nextToken());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            A = Long.parseLong(st.nextToken());
            B = Long.parseLong(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            sum = A+B;
            long result_A = (A*find_Pow(K)) % sum;
            long result_B = (B*find_Pow(K)) % sum;
            long result = Math.min(result_A, result_B);
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString().trim());
    }

    public static long find_Pow(long k) {
        if(k==0) {
            return 1;
        }
        else if(k==1) {
            return 2;
        }
        else {
            if(k%2==0) {
                long n = find_Pow(k/2)% sum;
                return  (n*n)% sum;
            }else {
                long n = find_Pow((k-1)/2)% sum;
                return (n*n*2) % sum;
            }
        }
    }
}