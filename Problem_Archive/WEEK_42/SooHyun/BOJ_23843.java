/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제를 읽었을 때 그리디 냄새가 났습니다. 그리고 시간이 2의 제곱 수로 이루어져서 맨 처음에는 이분 탐생을 활용하는 것이라고 생각했으나 단순히 해당 상황에서 넣을 수 있는 콘센트 중 먼저 끝나는데로 넣으면 풀립니다.
    - 핵심은 시간이 큰 것부터 넣어주는 것입니다.
    - 시간이 큰 것부터 넣기 위해 times라는 배열로 시간을 입력받고 오름차순으로 정렬 이후 뒤에서부터 탐색을 진행합니다.
    - arr라는 배열은 각 콘센트가 전자기기를 충전하는데 끝나는 시간을 저장합니다. i번째 전자기기를 넣기위해 arr 배열을 탐색하는데 그 중에서 가장 작은 값을 가지는 것은 가장 충전이 빨리 끝나는 콘센트라는 것을 의미합니다.
    - 해당 위치에 i번째 전자기기를 넣는 작업을 수행하면서 마지막에 arr 배열을 탐색하면서 최대 값을 찾아 정답으로 반환합니다. 최대 값이라는 것은 최종적으로 끝나는 시간을 의미합니다.

시간 복잡도
    - 입력 처리 : O(N)
    - 그리디 알고리즘 : O(N * M)
    - 전체 시간복잡도 : O(N * M)

실행 시간
   - 140ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static int[] arr;
    static int[] times;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        times = new int[N];
        arr = new int[M];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<N; i++){
            times[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(times);
        for(int i= times.length-1; i>=0; i--) {
            int current = times[i];
            int idx=0;
            for(int j=0; j<M; j++) {
                if(arr[idx] > arr[j]) {
                    idx = j;
                }
            }
            arr[idx]+=current;
        }
        int result=0;
        for(int i=0; i<arr.length; i++){
            result = Math.max(result, arr[i]);
        }
        System.out.println(result);
    }
}