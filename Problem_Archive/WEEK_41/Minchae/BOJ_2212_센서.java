/**
 * 2212 센서
 * https://www.acmicpc.net/problem/2212
 *
 * @author minchae
 * @date 2025. 5. 7.
 *
 * 문제 풀이
 * - 센서가 나열되어 있을 때 이 센서를 K개의 영역으로 나누는 것
 * - 일단 센서가 있는 곳을 오름차순 정렬 -> 예제 1, 3, 6, 6, 7, 9
 *   K가 2니까 저 6개를 2개로 나누면 된다 -> 나눴을 때 각 구간의 가장 큰 값에서 가장 작은 값을 뺀 걸 더하면 수신 가능 영역의 합이다.
 * - 더한 합 중에서 최솟값을 찾으면 된다.
 *
 * - 먼저 입력받은 값에서 각 센서 사이의 거리를 구함 -> 그 중에서 가장 길이가 긴 센서를 기준으로 자르면 된다.
 *   K개의 영역으로 나누려면 (K - 1)개의 사이 간격이 나오게 됨
 *   -> 각 센서들의 거리를 구했을 때 가장 긴 거리를 제외하고 나머지 길이를 더하면 그게 최소 (K만큼 기지국 세우니까 (N - K)를 빼야함)
 *
 * 시간 복잡도
 * 정렬 O(NlogN)
 * 센서 사이 거리 구하고 거리 더하는 거는 O(N), O(N - K)
 *
 * 실행 시간
 * 168 ms
 **/

import java.io.*;
import java.util.*;

public class BOJ_2212 {

    static int N, K;
    static int[] sensor;
    static int[] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        sensor = new int[N];
        dist = new int[N - 1];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            sensor[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(sensor);

        for (int i = 0; i < N - 1; i++) {
            dist[i] += sensor[i + 1] - sensor[i];
        }

        Arrays.sort(dist);

        int answer = 0;

        // 기지국을 K개 세울 수 있으니까 (N - K)까지만 진행 -> 가장 긴 길이를 제외
        for (int i = 0; i < N - K; i++) {
            answer += dist[i];
        }

        System.out.println(answer);
    }
}
