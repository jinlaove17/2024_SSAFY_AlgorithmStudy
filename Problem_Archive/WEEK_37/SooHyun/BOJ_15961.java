/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제 분류는 슬라이딩 윈도우 문제입니다. 이번 네이버였나 엠로였나 사실 가물가물한데 최근 코테 기출 문제여서 복습겸 가져왔습니다.
    - 접시의 갯수가 N의 최대 값이 3000000이고 연속해서 먹을 수 있는게 k 최대 3000이므로 단순히 이중 반복문을 돌리면 코드에 사탄이 들립니다.
    - 따라서 슬라이딩 윈도우를 활용하여 연속해서 k개를 먹는 경우의 수를 구합니다.
    - 처음에 배열에 대한 입력을 받기위해 arr를 선언하고 각각의 초밥의 갯수를 세기 위해 sushi 배열을 선언합니다.
    - 그리고 처음 쿠폰 번호는 하나 공짜로 먹을 수 있는 것이므로 sushi[c]를 1로 초기화합니다.
    - 그 후 처음 k개를 먹는 경우의 수를 구하기 위해 sushi 배열을 활용하여 sushi[arr[i]]++를 해주고 처음 먹는 것은 해당의 값이 갱시되기 전에 0이므로 0인 경우 전체 먹을 수 있는 가짓수 resul를 1 증가시킵니다.
    - 이후 슬라이딩 윈도우 방식을 통해 하나 떙기면 맨 앞에 있는 스시의 종류에 대해서 하나 감소시키고 해당 sushi의 값이 0이 되면 result를 1 감소시키고 떙겨진 것에 대해서 해당 sushi의 값을 0이면 result를 1 증가시킵니다.
    - 이를 통해 정답을 도출합니다.

시간 복잡도
    - 입력 처리 : O(N)
    - 초기 슬라이딩 윈도우 세팅 : O(k)
    - 슬라이딩 윈도우 : O(N)
    - 전체 시간복잡도 : O(N+k)

실행 시간
   - 596ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, d, k, c;
    static int[] arr;
    static int[] sushi;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        arr = new int[N];
        sushi = new int[d+1];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine().trim());
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int result = 1;
        sushi[c]=1;
        for(int i=0; i<k; i++){
            if(sushi[arr[i]] == 0){
                result++;
            }
            sushi[arr[i]]++;
        }
        int temp=result;
        for(int i=1; i<N; i++){
            int s = arr[i-1];
            sushi[s]--;
            if(sushi[s]==0){
                temp--;
            }
            int idx =` (i+k-1) %N;
            if(sushi[arr[idx]] ==0){
                temp++;
            }
            sushi[arr[idx]]++;
            result = Math.max(result,temp);
        }
        System.out.println(result);

    }

}