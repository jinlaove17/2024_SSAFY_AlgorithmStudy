/**
 * 2477. 차량 정비소
 * 
 * @author minchae
 * @date 2024. 8. 20.
 */

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class Solution {
	
	static class Customer implements Comparable<Customer> {
		int num;
		int arrival; // 도착 시간
		int counter; // 접수 창구번호
		
		public Customer(int num, int arrival) {
			this.num = num;
			this.arrival = arrival;
		}

		// 도착 시간 기준으로 오름 차순, 같다면 접수 창구번호 기준으로 오름차순
		@Override
		public int compareTo(Solution.Customer o) {
			if (this.arrival == o.arrival) {
				return this.counter - o.counter;
			}
			
			return this.arrival - o.arrival;
		}
	}
	
	static int N, M, K, A, B;
	static int[] a, b, time;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken()); // 접수 창구
			M = Integer.parseInt(st.nextToken()); // 정비 창구
			K = Integer.parseInt(st.nextToken()); // 고객 수
			A = Integer.parseInt(st.nextToken()); // 지갑을 두고 간 고객이 이용한 접수 창구 번호
			B = Integer.parseInt(st.nextToken()); // 지갑을 두고 간 고객이 이용한 정비 창구 번호
			
			a = new int[N];
			b = new int[M];
			time = new int[K];
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N; i++) {
				a[i] = Integer.parseInt(st.nextToken());
			}
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < M; i++) {
				b[i] = Integer.parseInt(st.nextToken());
			}
			
			st = new StringTokenizer(br.readLine());
			
			Queue<Customer> aQ = new LinkedList<>(); // 접수 창구 기다리는 고객
			
			for (int i = 0; i < K; i++) {
				int time = Integer.parseInt(st.nextToken());
				
				aQ.add(new Customer(i + 1, time)); // 고객 번호, 도착 시간 저장
			}
			
			PriorityQueue<Customer> serviceQ = new PriorityQueue<>(); // 정비 창구에 배치되기를 기다리는 고객 저장
			
			HashMap<Integer, Customer> useA = new HashMap<>(); // 접수 창구 번호, 고객 정보
			HashMap<Integer, Customer> useB = new HashMap<>(); // 정비 창구 번호, 고객 정보
			
			int cnt = 0; // 정비 완료된 고객의 수
			int curTime = 0; // 현재 시간
			int answer = 0;
			
			while (cnt < K) {
				// 접수 창구 처리 시간이 끝난 경우 비워주고 정비 창구 큐에 추가
				for (int i = 0; i < N; i++) {
					// 창구가 비어있으면 넘어감
					if (!useA.containsKey(i)) {
						continue;
					}
					
					Customer c = useA.get(i);
					
					// 처리 시간이 된 경우
					if (c.arrival + a[i] == curTime) {
						c.arrival = curTime; // 정비 창구 도착 시간 늘려줌
						useA.remove(i); // 처리 시간이 다 되었기 때문에 맵에서 삭제
						
						serviceQ.add(c); // 대기 큐에 추가
					}
				}
				
				// 빈 접수 창구에 고객 배치
				for (int i = 0; i < N; i++) {
					// 창구가 비어있지 않은 경우 넘어감
					if (useA.containsKey(i)) {
						continue;
					}
					
					// 접수 창구 대기 고객이 남은 경우 배치할 수 있는지 확인
					if (!aQ.isEmpty()) {
						Customer c = aQ.peek();
						
						// 고객이 이미 도착한 경우 배치 가능
						if (c.arrival <= curTime) {
							c.arrival = curTime;
							c.counter = (i + 1);
							
							useA.put(i, aQ.poll()); // 배치할 수 있기 때문에 큐에서 뺌
						}
					}
				}
				
				// 정비 창구 처리 시간이 끝난 경우 비워주고 cnt 증가
				for (int i = 0; i < N; i++) {
					// 창구가 비어있으면 넘어감
					if (!useB.containsKey(i)) {
						continue;
					}
					
					Customer c = useB.get(i);
					
					// 처리 시간이 된 경우
					if (c.arrival + b[i] == curTime) {
						// 지갑을 두고 간 고객과 같은 접수, 정비 창구인 경우
						if ((i + 1) == B && c.counter == A) {
							answer += c.num;
						}
						
						useB.remove(i); // 처리 시간이 다 되었기 때문에 맵에서 삭제
						cnt++;
					}
				}
				
				// 빈 정비 창구에 고객 배치
				for (int i = 0; i < M; i++) {
					// 창구가 비어있지 않은 경우 넘어감
					if (useB.containsKey(i)) {
						continue;
					}
					
					// 정비 창구 대기 고객이 남은 경우 -> 이동 시간 고려 X
					if (!serviceQ.isEmpty()) {
						Customer c = serviceQ.peek();
						c.arrival = curTime;
						
						useB.put(i, serviceQ.poll());
					}
				}
				
				curTime++; // 현재 시간 증가
			}
			
			System.out.println("#" + t + " " + (answer == 0 ? -1 : answer));
		}

	}

}
