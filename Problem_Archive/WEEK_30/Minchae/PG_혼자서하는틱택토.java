/**
 * 혼자서 하는 틱택토
 * https://school.programmers.co.kr/learn/courses/30/lessons/160585
 *
 * @author minchae
 * @date 2025. 2. 13.
 *
 * 문제 풀이
 * - 문제에 주어진대로 실수할 경우가 있는 상황을 생각하면 금방 풀림
 * 1. X의 개수가 더 많으면 실수할 가능성이 있는 것
 * 2. O의 개수가 더 많을 때는 딱 1개만 더 있어야 실수하지 않는 것
 * 3. 둘 다 이기는 경우는 실수한 것
 * 4. O로 이겼는데 개수가 같은 경우 실수 (O가 1개 더 적어야함)
 * 5. X로 이겼는데 O의 개수가 더 많은 경우 실수
 *
 * 시간 복잡도
 * O(1)
 *
 * 실행 시간
 * 0.05 ms
 */

public class TicTacToe {

	static char[][] map;

	public static void main(String[] args) {
		String[] board1 = {"O.X", ".O.", "..X"};
		String[] board2 = {"OOO", "...", "XXX"};
		String[] board3 = {"...", ".X.", "..."};
		String[] board4 = {"...", "...", "..."};

		System.out.println(solution(board1));
		System.out.println(solution(board2));
		System.out.println(solution(board3));
		System.out.println(solution(board4));
	}

	public static int solution(String[] board) {
		map = new char[3][3];

		int cntO = 0;
		int cntX = 0;

		for (int i = 0; i < 3; i++) {
			map[i] = board[i].toCharArray();

			for (int j = 0; j < 3; j++) {
				if (map[i][j] == 'O') {
					cntO++;
				} else if (map[i][j] == 'X') {
					cntX++;
				}
			}
		}

		// X의 개수가 더 많으면 실수할 가능성이 있는 것
		// O의 개수가 더 많을 때는 딱 1개만 더 있어야 실수하지 않는 것
		if (cntX > cntO || cntO - cntX > 1) {
			return 0;
		}
		
		boolean winO = check('O');
		boolean winX = check('X');
		
		// 둘 다 이기는 경우는 없음
		if (winO && winX) {
			return 0;
		}

		// O로 이겼는데 개수가 같은 경우 실수 (O가 1개 더 적어야함)
		if (winO && cntO == cntX) {
			return 0;
		}

		// X로 이겼는데 O의 개수가 더 많은 경우 실수
		if (winX && cntO > cntX) {
			return 0;
		}

		return 1;
	}

	// 가로, 세로, 대각선으로 3개가 같은 표시를 만들 수 있는지 확인
	private static boolean check(char type) {
		// 가로
		for (int i = 0; i < 3; i++) {
			if (map[i][0] == type && map[i][1] == type && map[i][2] == type) {
				return true;
			}
		}

		// 세로
		for (int i = 0; i < 3; i++) {
			if (map[0][i] == type && map[1][i] == type && map[2][i] == type) {
				return true;
			}
		}

		// 대각선
		if (map[0][0] == type && map[1][1] == type && map[2][2] == type) {
			return true;
		}

		if (map[2][0] == type && map[1][1] == type && map[0][2] == type) {
			return true;
		}

		return false;
	}

}
