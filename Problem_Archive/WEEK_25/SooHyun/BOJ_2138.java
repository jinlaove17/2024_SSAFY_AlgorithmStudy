/*
문제 접근 아이디어 및 알고리즘 판단 사유
   - 문제를 풀기 전에 어떤 방식으로 풀어야하는지 감이 잡히지 않았습니다.
    - 문제가 14주차 전구 상태 바꾸기와 유사하다고 판단을 해 이와 비슷한 풀이법을 고안했습니다.
    - 1번째 그리고 마지막 전구에 대해서만 스위치르 작동시키면 2개의 전구 상태가 변환하고 나머지 경우에는 3가지 전구 상태가  변화합니다.
    - 이에 초기 입력에 대해서 2가지 상황을 만들었습니다. 첫째, 첫번째 스위치를 누르지 않은 경우, 둘째, 첫번째 스위치를 누르는 경우
    - 이에 따라 반복문을 진행하는데 i번째 저구를 방문하는 경우 i번째 전구를 비교한 것이 아닌 i-1 전구를 비교해 다를 경우 스위치를 누르는 방식으로 구현했습니다.
    - 반복문을 빠져나오게 될 시에 마지막 전구를 비교해 타겟의 마지막 전구와 같은지 확인하며 정답을 도출합니다.

시간 복잡도
   - 배열 생성 및 초기화 : O(N)
    - 반복문을 통한 비교 : O(N)
    - 전체 시간복잡도: O(N)

실행 시간
   - 200ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] current1;
    static int[] current2;
    static int[] target;
    static int result1=1;
    static int result2=0;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        current1 = new int[N];
        current2 = new int[N];
        target = new int[N];
        String str = br.readLine().trim();
        for(int i=0; i<N; i++) {
            char ch = str.charAt(i);
            current1[i] = Integer.parseInt(Character.toString(ch));
            current2[i] = Integer.parseInt(Character.toString(ch));
        }
        str = br.readLine().trim();
        for(int i=0; i<N; i++) {
            char ch = str.charAt(i);
            target[i] = Integer.parseInt(Character.toString(ch));
        }
        current1[0] = 1 - current1[0];
        current1[1] = 1 - current1[1];
        for(int i=1; i<N; i++) {
            if(current1[i-1] != target[i-1]) {
                current1[i-1] = 1- current1[i-1];
                current1[i] = 1- current1[i];
                if(i!=N-1) {
                    current1[i+1] = 1- current1[i+1];
                }
                result1++;
            }
            if(current2[i-1] != target[i-1]) {
                current2[i-1] = 1- current2[i-1];
                current2[i] = 1- current2[i];
                if(i!=N-1) {
                    current2[i+1] = 1- current2[i+1];
                }
                result2++;
            }
        }
        if(current1[N-1] != target[N-1]) {
            result1 = Integer.MAX_VALUE;
        }
        if(current2[N-1] != target[N-1]) {
            result2 = Integer.MAX_VALUE;
        }
        if(result1 == Integer.MAX_VALUE && result2 == Integer.MAX_VALUE) {
            System.out.println(-1);
        }else {
            System.out.println(Math.min(result1, result2));
        }
    }

}
