/**
 * 1079 마피아
 * https://www.acmicpc.net/problem/1079
 *
 * @author minchae
 * @date 2024. 11. 6.
 *
 * 문제 풀이
 *  - 모든 경우를 다 봐야 함
 *  - 낮과 밤을 구분해서 사람을 죽임
 *      -> 마피아만 남았거나 마피아가 죽은 경우 재귀 호출 종료
 *
 * 시간 복잡도
 * O(N!)
 *
 * 실행 시간
 * 576 ms
 * */

import java.util.*;
import java.io.*;

public class BOJ_1709 {

    static int N;

    static int[] guilty; // 참가자 유죄 지수
    static int[][] R; // 참가자 간의 반응
    static boolean[] dead; // 죽었는지 확인

    static int mafia;
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        guilty = new int[N];
        R = new int[N][N];
        dead = new boolean[N];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            guilty[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                R[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        mafia = Integer.parseInt(br.readLine());

        play(0, N);

        System.out.println(answer);
    }

    // 게임 진행 (진행된 날의 수, 남은 사람 수)
    private static void play(int day, int cnt) {
        // 마피아가 죽었거나 마피아만 남은 경우
        if (dead[mafia] || cnt == 1) {
            answer = Math.max(answer, day);
            return;
        }

        if (cnt % 2 == 0) {
            // 남은 사람 짝수 : 밤
            // 마피아가 죽일 사람 고름
            for (int i = 0; i < N; i++) {
                if (dead[i] || i == mafia) {
                    continue;
                }

                dead[i] = true; // 죽임

                // 나머지 사람 유죄 지수 증가
                for (int j = 0; j < N; j++) {
                    if (dead[j]) {
                        continue;
                    }

                    guilty[j] += R[i][j];
                }

                play(day + 1, cnt - 1); // 밤이니까 날짜 증가시키고 사람은 죽었으니까 감소시킴

                // 유죄 지수 원복
                for (int j = 0; j < N; j++) {
                    if (dead[j]) {
                        continue;
                    }

                    guilty[j] -= R[i][j];
                }

                dead[i] = false; // 원복
            }
            
        } else {
            // 남은 사람 홀수 : 낮
            // 현재 게임에 남아있는 사람 중에 유죄 지수가 가장 높은 사람을 죽인다.
            // 이 경우 유죄 지수는 바뀌지 않는다.

            int idx = 0; // 죽일 사람 번호
            int max = 0; // 유죄 지수 최댓값

            for (int i = 0; i < N; i++) {
                if (dead[i]) {
                    continue;
                }

                // 번호가 가장 작은 사람이 죽기 위해 클 때만 갱신
                if (max < guilty[i]) {
                    max = guilty[i];
                    idx = i;
                }
            }

            dead[idx] = true;
            play(day, cnt - 1); // 낮이니까 날짜는 증가시키지 않음
            dead[idx] = false; // 원복
        }
    }

}
