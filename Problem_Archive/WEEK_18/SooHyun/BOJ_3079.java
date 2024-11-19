/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- N이 최대 100000이고 M이 최대 1000000000이므로 이차원 배열 혹은 완탐을 통해서는 시간 초과가 발생할 것으로 판단했습니다.
	- 가장 시간이 오래 걸리는 경우는 심사가 걸리는 시간이 가장 긴 심사대에 상근이와 친구들 모두 그곳에서 받는 경우입니다.
	- 따라서 시간의 최소값은 T의 최소값인 1이고 최대 값은 M*max(심사대의 시간)으로 설정할 수 있습니다.
	- 이후 이분 탐생을 통해서 mid 즉 (min+max)/2이 값으로 sum+=(long)mid/t를 통해 mid 시간에 각 심사대에서 처리할 수 있는 인원 수를 계산합니다.
	- 그 값이 M 이상인경우 그곳보다 더 짧은 시간에 모두가 심사를 받을 수 있다는 것이므로 max = mid-1로 설정하고 answer = mid로 설정해 가능한 정답의 가짓수를 설정합니다.
	- 그렇지 않은 경우 min = mid+1로 설정해 더 긴 시간에 수행하는 경우를 탐색합니다.

시간 복잡도
	- 입력 처리 및 정렬 : O(N * log N)
	- 이분 탐색 : O(log(M×max_time)) (단 max_time은 time 배열의 최대값)
	- 전체 시간 복잡도 : O(N * log N + log(M×max_time))

실행 시간
	- 464ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] time;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        time = new int[N];

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            time[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(time);
        long max = (long)time[time.length-1]*M;
        long min=1;
        long answer=max;
        while(min <=max) {
            long mid = (min+max)/2;
            long sum=0;
            for(int t : time) {
                sum+=(long)mid/t;
                if(sum >M)
                    break;
            }
            if(sum >=M) {
                answer=mid;
                max = mid-1;
            }else {
                min=mid+1;
            }
        }
        System.out.println(answer);
    }

}
