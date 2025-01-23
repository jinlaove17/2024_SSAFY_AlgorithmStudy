/**
 * 17471 게리맨더링
 * https://www.acmicpc.net/problem/17471
 *
 * @author minchae
 * @date 2025. 1. 22.
 *
 * 문제 풀이
 * - 먼저 지역구 선택 -> 조합
 * - 연결되어 있는지 확인 -> BFS
 *
 * 시간 복잡도
 * 조합 : O(2^N)
 * BFS : 2 * O(V(지역 수) + E(연결 간선 수)) -> O(N^2)
 * 차이 계산 : O(N)
 * 총 시간 복잡도 : O(2^N * N^2)
 *
 * 실행 시간
 * 132 ms
 **/

import java.io.*;
import java.util.*;

public class Main2 {

	static int N;

	static int[] population;
	static ArrayList<Integer>[] list;

	static boolean[] selected;
	static int total; // 총 인구 수

	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		population = new int[N];
		list = new ArrayList[N];
		selected = new boolean[N];

		StringTokenizer st = new StringTokenizer(br.readLine());

		for (int i = 0; i < N; i++) {
			population[i] = Integer.parseInt(st.nextToken());
			total += population[i];
		}

		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<>();

			st = new StringTokenizer(br.readLine());

			int cnt = Integer.parseInt(st.nextToken());

			for (int j = 0; j < cnt; j++) {
				list[i].add(Integer.parseInt(st.nextToken()) - 1);
			}
		}

		answer = Integer.MAX_VALUE;

		comb(0);

		System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
	}

	// 조합 이용해서 선거구 두 개로 나누기
	private static void comb(int depth) {
		if (depth == N) {
			divide(); // 선거구 두 개로 나누기
			return;
		}

		selected[depth] = true;
		comb(depth + 1);

		selected[depth] = false;
		comb(depth + 1);
	}

	// 선거구 나누기
	private static void divide() {
		ArrayList<Integer> electionA = new ArrayList<>();
		ArrayList<Integer> electionB = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			if (selected[i]) {
				electionA.add(i);
			} else {
				electionB.add(i);
			}
		}

		// 두 개의 선거구 중 하나라도 비어있다면 종료
		if (electionA.isEmpty() || electionB.isEmpty()) {
			return;
		}

		// 선거구 모두 지역이 연결되어 있는 경우
		if (isConnected(electionA) && isConnected(electionB)) {
			answer = Math.min(answer, getDiff());
		}
	}

	// 선거구에 들어간 지역이 서로 연결되어 있는지 확인
	private static boolean isConnected(ArrayList<Integer> election) {
		Queue<Integer> q = new LinkedList<>();
		boolean[] visited = new boolean[N];

		int cnt = 1;
		int start = election.get(0);

		q.add(start);
		visited[start] = true;

		while (!q.isEmpty()) {
			int cur = q.poll();

			for (int next : list[cur]) {
				// 아직 방문하지 않은 경우 && 선거구에 포함되어 있는 경우
				if (!visited[next] && election.contains(next)) {
					q.add(next);
					visited[next] = true;

					cnt++;
				}
			}
		}

		return cnt == election.size();
	}

	// 두 선거구의 인구 차이 구하기
	private static int getDiff() {
		int sum = 0;

		for (int i = 0; i < N; i++) {
			if (selected[i]) {
				sum += population[i];
			}
		}

		return Math.abs(sum - (total - sum));
	}

}
