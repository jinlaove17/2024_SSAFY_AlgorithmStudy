/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 제가 좋아하는 문제의 유형입니다. 시키는 대로 구현하는 문제입니다.
    - 0~9까지의 숫자를 표현하기 위한 배열 display를 만들어놓고, 현재 X 값에서 최소 1번, 최대 P번의 변환을 통해서 1 ~ N까지의 숫자를 만들 수 있는지 확인합니다.
    - find함수 내부에 check 함수를 통해 가능한 가짓수에 대해서 count를 증가시킵니다.
    - 1 ~ N까지의 숫자를 만들 수 있는지 확인하는 check 함수는 두 배열을 비교하면서 변환 횟수를 계산합니다.
    - 이를 위해 target 숫자의 배열을 만들고 arr 배열과 비교하면서 변환 횟수를 계산합니다.
    - 변환 횟수가 P를 넘어가면 false를 반환하고, 그렇지 않으면 true를 반환합니다.
    - 이를 통해 정답을 도출합니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(K)
    - find 함수 : O(N)
    - check 함수 : O(K)
    - calculate 함수 : O(7) 즉 O(1)
    - 전체 시간복잡도: O(N * K)

실행 시간
   - 228ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, K, P, X;
    static int[][] display= {
            {1,1,1,0,1,1,1},
            {0,0,1,0,0,0,1},
            {0,1,1,1,1,1,0},
            {0,1,1,1,0,1,1},
            {1,0,1,1,0,0,1},
            {1,1,0,1,0,1,1},
            {1,1,0,1,1,1,1},
            {0,1,1,0,0,0,1},
            {1,1,1,1,1,1,1},
            {1,1,1,1,0,1,1}
    };
    static int[] arr;
    static int count=0;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        arr = new int[K];
        int num = X;
        for(int i=K-1; i>=0; i--) {
            arr[i] = num%10;
            num=num/10;
        }
        find();
        System.out.println(count);
    }
    public static void find() {
        for(int i=1; i<=N; i++) {
            if(i==X)
                continue;
            if(check(i)) {
                count++;
            }
        }
    }
    public static boolean check(int num) {
        int[] target = new int[K];
        for(int i=K-1; i>=0; i--) {
            target[i] = num%10;
            num=num/10;
        }
        int temp=0;
        for(int i=0; i<K; i++) {
            temp+=calculate(arr[i], target[i]);
            if(temp > P)
                return false;
        }
        return true;
    }
    public static int calculate(int from, int to) {
        int result=0;
        for(int i=0; i<7; i++) {
            if (display[from][i] != display[to][i]) {
                result++;
            }
        }
        return result;
    }
}
