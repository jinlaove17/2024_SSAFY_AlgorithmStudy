/**
 * 파괴되지 않은 건물
 * https://school.programmers.co.kr/learn/courses/30/lessons/92344
 *
 * @author minchae
 * @date 2025. 1. 7.
 *
 * 문제 풀이
 * - 카카오 해설 참조
 *
 * 시간 복잡도
 * O(N * M)
 *
 * 실행 시간
 * 49.37 ms
 **/

public class Building {

    public static int solution(int[][] board, int[][] skill) {
        int N = board.length;
        int M = board[0].length;

        int[][] sum = new int[N + 1][M + 1];

        for (int[] s : skill) {
            int r1 =s[1];
            int c1 = s[2];
            int r2 = s[3];
            int c2 = s[4];
            int degree = s[5] * (s[0] == 1 ? -1 : 1);

            sum[r1][c1] += degree;
            sum[r2 + 1][c2 + 1] += degree;
            sum[r2 + 1][c1] -= degree;
            sum[r1][c2 + 1] -= degree;
        }
        
        // 누적합 구하기
        // 가로
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j < M; j++) {
				sum[i][j + 1] += sum[i][j];
			}
		}

		// 세로
		for (int j = 0; j <= M; j++) {
			for (int i = 0; i < N; i++) {
				sum[i + 1][j] += sum[i][j];
			}
		}

        int answer = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] + sum[i][j] > 0) {
                    answer++;
                }
            }
        }

        return answer;
    }

}
