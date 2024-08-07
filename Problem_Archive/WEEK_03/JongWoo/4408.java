import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Pos {
	int begin;
	int end;
}

public class Solution {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuffer sb = new StringBuffer();
		final int MAX_SIZE = 201;
		int T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; ++test_case) {
			int N = Integer.parseInt(br.readLine());
			Pos[] positions = new Pos[N];

			for (int i = 0; i < N; ++i) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				positions[i] = new Pos();
				positions[i].begin = Integer.parseInt(st.nextToken());
				positions[i].end = Integer.parseInt(st.nextToken());
				positions[i].begin = (positions[i].begin + 1) / 2;
				positions[i].end = (positions[i].end + 1) / 2;

				if (positions[i].begin > positions[i].end) {
					swap(positions[i]);
				}
			}

			Arrays.sort(positions, (a, b) -> a.begin - b.begin);

			int cnt = 0, answer = 0;
			boolean[] isFinished = new boolean[MAX_SIZE];

			while (cnt < N) {
				boolean[] isUsed = new boolean[MAX_SIZE];

				for (int i = 0; i < N; ++i) {
					if (isFinished[i]) {
						continue;
					}

					boolean isPossible = true;

					for (int j = positions[i].begin; j <= positions[i].end; ++j) {
						if (isUsed[j]) {
							isPossible = false;
							break;
						}
					}

					if (isPossible) {
						for (int j = positions[i].begin; j <= positions[i].end; ++j) {
							isUsed[j] = true;
						}

						isFinished[i] = true;
						++cnt;
					}
				}

				++answer;
			}

			sb.append(String.format("#%d %d\n", test_case, answer));
		}

		System.out.println(sb.toString());
	}

	private static void swap(Pos pos) {
		int tmp = pos.begin;

		pos.begin = pos.end;
		pos.end = tmp;
	}
}
