/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제는 최대 K개를 설치할 때 수신 가능영역의 거리 합의 최소를 구하는 것입니다. 상식적으로 최대 K개 설치할 수 있다면 딱 K개를 설치하는게 수신 가능 영역의 누적합이 최소가 되겠죠.
    - 만약 N보다 K가 크다면 즉 센서의 갯수보다 설치할 수 있는 집중국이 더 많다면 각각 하나씩 새워서 수신 가능 영역의 합을 0으로 만든는 것입나.
    - 그게 아니라면 총 N개의 센서가 존재하면 N-1 개의 간격이 존재할 것입니다.
    - 즉 K개를 세운다는 것음 각 구역을 K개로 나누는 것을 의미합니다.
    - 처음에 센서의 위치를 입력받고 오름차순 정렬, 이후 간격 또한 계산한 이후 오름차순 정렬을 합니다.
    - 이후 N개 중에서 K개에 대해서는 각각 센서에 대해서 하나씩 세우고 N-1개이 간격 중 상위 K-1개의 간격을 제외한 것의 누적합을 구하면 됩니다.

시간 복잡도
    - 입력 및 간격 계산 : O(N)
    - 정렬 : O(NlogN)
    - 센서 간격 계산 : O(N)
    - 전체 시간복잡도: O(NlogN) (N은 센서의 갯수)

실행 시간
   - 160ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, K;
    static int[] station;
    static int[] diff;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine().trim());
        K = Integer.parseInt(st.nextToken());
        if(N <= K){
            System.out.println(0);
            return;
        }
        int sum=0;
        station = new int[N];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<N; i++){
            station[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(station);
        diff = new int[N-1];
        for(int i=0; i<N-1; i++){
            diff[i] = station[i+1] - station[i];
        }
        Arrays.sort(diff);
        for(int i= 0; i<N-K; i++)
            sum += diff[i];

        System.out.println(sum);
    }
}