/**
 * 2138 전구와 스위치
 * https://www.acmicpc.net/problem/2138
 *
 * @author minchae
 * @date 2025. 1. 9.
 *
 * 문제 풀이
 * - 첫 번째 스위치를 누른 경우와 누르지 않은 경우 두 가지를 나누어서 봄
 * - 만들고자 하는 전구 상태와 비교하면서 누르는 횟수 계산
 * - (i - 1), i, (i + 1) 상태를 바꾸는 걸 i, (i + 1), (i + 2)로 보기
 *
 * 시간 복잡도
 * O(N)
 *
 * 실행 시간
 * 196 ms
 **/

import java.io.*;

public class BOJ_2138 {

    static int N;
    static int[] make;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        String input1 = br.readLine();
        String input2 = br.readLine();

        int[] cur1 = new int[N]; // 첫 번째 스위치를 누르지 않은 경우
        int[] cur2 = new int[N]; // 첫 번째 스위치를 누른 경우
        make = new int[N];

        for (int i = 0; i < N; i++) {
            cur1[i] = input1.charAt(i) - '0';
            cur2[i] = input1.charAt(i) - '0';
            make[i] = input2.charAt(i) - '0';
        }

        // 첫 번째 스위치 누른 상태로 변경
        cur2[0] = 1 - cur2[0];
        cur2[1] = 1 - cur2[1];

        int answer1 = press(cur1);
        int answer2 = press(cur2);

        // 첫 번째 스위치를 눌렀기 때문에 횟수가 있는 경우 한 번 증가 시킴
        if (answer2 != -1) {
            answer2++;
        }

        if (answer1 == -1) {
            System.out.println(answer2);
        } else if (answer2 == -1) {
            System.out.println(answer1);
        } else {
            System.out.println(Math.min(answer1, answer2)); // 최솟값 출력
        }
    }

    // 전구 상태 변경하기
    private static int press(int[] cur) {
        int cnt = 0;

        for (int i = 0; i < N - 1; i++) {
            // 상태가 다른 경우에만 누르기
            if (cur[i] != make[i]) {
                cur[i] = 1 - cur[i];
                cur[i + 1] = 1 - cur[i + 1];

                if (i != N - 2) {
                    cur[i + 2] = 1 - cur[i + 2];
                }

                cnt++; // 횟수 증가
            }
        }

        return cur[N - 1] == make[N - 1] ? cnt : -1; // 전구 상태가 같을 때만 횟수 반환
    }

}
