/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제의 사용된 알고리즘은 이분탐색입니다.
    - X의 크기가 최대 10^9이므로 무지성으로 탐색을 시작하면 안되겠죠?? X의 크기를 보고 이분탐색을 떠올릴 수 있었습니다.
    - 문제의 조건에서 i번째 선물은 i-1까지 배정된 이후 가장 사용 시간이 적은 공정 라인 중 하나에 배정이 되어야된다는 것을 보고 Info라는 객체를 선언하고 종료 시간으로 정렬이 가능한 우선 순위 큐에 저장했습니다.
    - 이분탐색 코드를 보면 초기 low=1, high=N(선물 주문의 수)로 잡았습니다. 그리고 mid값을 할당한 후에 초기 mid개 만큼의 선물은 바로 들어갈 수 있으니 우선 순위 큐에 넣어줍니다.
    - 이후 mid부터 끝까지는 우선순위 큐를 poll하고 기존 종료시간 + 선물주문의 공정시간을 더해준 값으로 갱신하고 우선순위 큐에 다시 넣어줍니다.
    - 이 과정에서 최종 종료 시간을 계속 갱신하면서 만약 최종 종료시간이 X보다 작다면 high를 mid-1로 갱신하고 아니라면 low를 mid+1로 갱신합니다.
    - 최종적으로 low를 반환하면 정답이 됩니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N)
    - binary_search 함수 : O(NlogN)
    - 전체 시간복잡도: O(NlogN)

실행 시간
   - 844ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N, X;
    static int[] arr;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        arr = new int[N];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int result = binary_search();
        System.out.println(result);
    }

    public static int binary_search() {
        int low = 1;
        int high = N;
        while(low <= high) {
            int mid = (low+high)/2;
            PriorityQueue<Info> pq = new PriorityQueue<>();
            int endTime=0;
            for(int i=0; i< mid; i++) {
                endTime = Math.max(endTime, arr[i]);
                pq.add(new Info(i, arr[i]));
            }

            for(int i=mid; i<N; i++) {
                Info info = pq.poll();
                endTime = Math.max(endTime, info.end+arr[i]);
                pq.add(new Info(info.idx, info.end+arr[i]));
            }
            if(endTime <= X) {
                high = mid-1;
            }else {
                low =mid+1;
            }
        }
        return low;
    }

    public static class Info implements Comparable<Info>{
        int idx;
        int end;

        public Info(int idx, int end) {
            this.idx=idx;
            this.end=end;
        }

        @Override
        public int compareTo(Info o) {
            // TODO Auto-generated method stub
            return this.end-o.end;
        }
    }
}
