/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제 해결에 있어 이분 탐색의 로직을 조금 응용했습니다.
	- A, B에 대해 모든 가능한 부분 합을 저장하는 List 2개 선언한 후에 크기 순으로 정렬했습니다.
	- A에 대한 부분 합의 리스트의 첫번째 값과 B에 대한 부분 합의 리스트의 마지막 값에 각각 포인터를 지정하여 만약 그 두 위치의 합이 만들고자 하는 수보다 작은 경우 left를 1개씩 증가시키고 큰 경우 right를 1개씩 감소시켰습니다.
	- 그리고 두 값의 합이 찾고하는 값과 일치하는 경우 각각의 리스트에 동일한 값의 갯수를 찾은 이후 2개를 곱함으로써 가능한 조합을 정답에 더해줍니다.
	- 이를 left가 A 부분 합 리스트의 크기보다 작고 right가 0이상인 경우까지 반복합니다.

시간 복잡도
	- 배열 A와 B의 부분 합 리스트 생성 : O(N*N + M*M)
	- 부분합 리스트 정렬 : O(N*N*logN + M*M*logM)
	- 투 포인터로 가능한 쌍 찾기 : O(N*N + M*M)
	- 전체 시간 복잡도 : O(N*N*logN + M*M*logM)

실행 시간
	- 936ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static int T;
    static int N;
    static int M;
    static int[] A;
    static ArrayList<Integer> sum_A;
    static ArrayList<Integer> sum_B;
    static int[] B;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        T = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        A = new int[N];
        sum_A = new ArrayList<>();
        st = new StringTokenizer(br.readLine().trim());
        long answer=0;
        for(int i=0; i<N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0; i<N; i++) {
            int sum=0;
            for(int j=i; j<N; j++) {
                sum+=A[j];
                sum_A.add(sum);
            }
        }
        st = new StringTokenizer(br.readLine().trim());
        M = Integer.parseInt(st.nextToken());
        B = new int[M];
        sum_B = new ArrayList<>();
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<M; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0; i<M; i++) {
            int sum=0;
            for(int j=i; j<M; j++) {
                sum+=B[j];
                sum_B.add(sum);
            }
        }
        Collections.sort(sum_A);
        Collections.sort(sum_B);
        int left=0;
        int right = sum_B.size()-1;
        while(left <sum_A.size() && right >=0) {
            int n1 = sum_A.get(left);
            int n2 = sum_B.get(right);
            int sum = n1+n2;
            if(sum==T) {
                long left_cnt=0;
                while(left <sum_A.size() && sum_A.get(left) == n1) {
                    left_cnt++;
                    left++;
                }
                long right_cnt=0;
                while(right >=00 && sum_B.get(right) == n2) {
                    right_cnt++;
                    right--;
                }
                answer+=left_cnt*right_cnt;
            }
            else if(sum>T) {
                right--;
            }else {
                left++;
            }
        }
        System.out.println(answer);
    }

}
