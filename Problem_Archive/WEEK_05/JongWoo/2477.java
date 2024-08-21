import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Customer {
	int number;
	int arriveTime;
	int usedRecept;
}

public class Solution {
	static int n, m, k, targetRecept, targetRepair;
	static int[] receptTimes, repairTimes;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= t; ++tc) {
			StringTokenizer st = new StringTokenizer(br.readLine().trim());

			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			targetRecept = Integer.parseInt(st.nextToken());
			targetRepair = Integer.parseInt(st.nextToken());
			receptTimes = new int[n];
			repairTimes = new int[m];
			st = new StringTokenizer(br.readLine().trim());

			for (int i = 0; i < n; ++i) {
				receptTimes[i] = Integer.parseInt(st.nextToken());
			}

			st = new StringTokenizer(br.readLine().trim());

			for (int i = 0; i < m; ++i) {
				repairTimes[i] = Integer.parseInt(st.nextToken());
			}

			// 접수 창구 큐
			// ① 여러 고객이 기다리고 있는 경우 고객번호가 낮은 순서대로 우선 접수한다. ---> 고객번호가 낮으면 도착 시간도 빠름
			// ② 빈 창구가 여러 곳인 경우 접수 창구번호가 작은 곳으로 간다.
			Queue<Customer> receptQ = new LinkedList<>();

			st = new StringTokenizer(br.readLine().trim());

			for (int i = 0; i < k; ++i) {
				Customer customer = new Customer();

				customer.number = i + 1;
				customer.arriveTime = Integer.parseInt(st.nextToken());
				receptQ.add(customer);
			}

			// 정비 창구 우선순위 큐
			// ① 먼저 기다리는 고객이 우선한다.
			// ② 두 명 이상의 고객들이 접수 창구에서 동시에 접수를 완료하고 정비 창구로 이동한 경우, 이용했던 접수 창구번호가 작은 고객이 우선한다.
			// ③ 빈 창구가 여러 곳인 경우 정비 창구번호가 작은 곳으로 간다.
			PriorityQueue<Customer> repairPQ = new PriorityQueue<>((Customer c1, Customer c2) -> {
				if (c1.arriveTime == c2.arriveTime) {
					return Integer.compare(c1.usedRecept, c2.usedRecept);
				}

				return Integer.compare(c1.arriveTime, c2.arriveTime);
			});

			// 창구의 사용 여부(null이면 빈 곳)
			Customer[] receptDesks = new Customer[n];
			Customer[] repairDesks = new Customer[m];
			int time = 0, cnt = 0, answer = 0;

			while (cnt < k) {
				// 창구를 먼저 비워야 해당 시간에 동시에 들어갈 수 있다.
				// 처리 시간이 다 되었다면 해당 칸을 비운다.
				for (int i = 0; i < n; ++i) {
					if (receptDesks[i] != null) {
						// 시간이 다됐다면 방을 비운다. (현재 시간 - 도착 시간 = 처리 시간)
						if (time - receptDesks[i].arriveTime == receptTimes[i]) {
							receptDesks[i].arriveTime = time;
							repairPQ.add(receptDesks[i]);
							receptDesks[i] = null;
						}
					}
				}

				// 빈 창구가 있다면 큐에서 꺼내 추가한다.
				for (int i = 0; i < n; ++i) {
					if (receptDesks[i] == null) {
						if (!receptQ.isEmpty()) {
							Customer customer = receptQ.peek();

							if (customer.arriveTime <= time) {
								customer.arriveTime = time;
								customer.usedRecept = i + 1;
								receptDesks[i] = receptQ.poll();
							}
						}
					}
				}

				// 처리 시간이 다 되었다면 해당 칸을 비운다.
				for (int i = 0; i < m; ++i) {
					if (repairDesks[i] != null) {
						// 시간이 다됐다면 방을 비운다. (현재 시간 - 도착 시간 = 처리 시간)
						if (time - repairDesks[i].arriveTime == repairTimes[i]) {
							// targetRecept와 targetRepair인지 확인한다.
							if ((i + 1 == targetRepair) && (repairDesks[i].usedRecept == targetRecept)) {
								answer += repairDesks[i].number;
							}

							++cnt;
							repairDesks[i] = null;
						}
					}
				}

				// 빈 창구가 있다면 큐에서 꺼내 추가한다.
				for (int i = 0; i < m; ++i) {
					if (repairDesks[i] == null) {
						if (!repairPQ.isEmpty()) {
							repairDesks[i] = repairPQ.poll();
							repairDesks[i].arriveTime = time;
						}
					}
				}

				++time;
			}

			if (answer == 0) {
				answer = -1;
			}

			sb.append(String.format("#%d %d\n", tc, answer));
		}

		System.out.println(sb);
	}
}
