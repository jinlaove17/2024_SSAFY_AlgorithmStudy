/**
 * @date 2024. 12. 12.
 *
 * 문제 풀이
 *  - 최대 공약수 구하기
 *
 * 시간 복잡도
 *
 * 실행 시간
 * 108 ms
* */

import java.util.*;
import java.io.*;

public class BOJ_1033 {

    static int N;
    static int[] num; // 정점 저장
    static ArrayList<Integer>[] list;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        num = new int[N];
        list = new ArrayList[N];

        for (int i = 0; i < N; i++) {
            num[i] = 1;
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            long g = gcd(num[a], num[b]);

            long val1 = num[b] / g * p;
            long val2 = num[a] / g * q;

            g = gcd(val1, val2);

            visited = new boolean[N];
            dfs(a, (int) (val1 / g));

            visited = new boolean[N];
            dfs(b, (int) (val2 / g));

            // 정점 연결
            list[a].add(b);
            list[b].add(a);
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            sb.append(num[i]).append(" ");
        }

        System.out.println(sb);
    }

    // 최대 공약수 구하기
    private static long gcd(long a, long b) {
        while (b != 0) {
            long r = a % b;

            a = b;
            b = r;
        }

        return a;
    }

    // 정점에 연결된 다른 정점 방문하면서 값을 곱해줌 -> 비율을 유지하기 위함
    private static void dfs(int cur, int val) {
        visited[cur] = true;
        num[cur] *= val;

        for (int next : list[cur]) {
            if (visited[next]) {
                continue;
            }

            dfs(next, val);
        }
    }
}
