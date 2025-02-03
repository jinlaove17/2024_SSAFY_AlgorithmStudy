/**
 * 등산코스 정하기
 * https://school.programmers.co.kr/learn/courses/30/lessons/118669
 *
 * @author minchae
 * @date 2025. 2. 3.
 *
 * 문제 풀이
 * - 출입구에서 봉우리까지 갔다오는 경로 -> 그냥 출입구-봉우리 경로만 확인하면 됨
 * - list에 저장할 때 출입구 또는 봉우리인 경우 단반향으로 값 저장
 * - 다익스트라 느낌으로 품. 약간 응용하는 느낌
 * - intensity 배열을 원래 다익스트라에서 dist 배열처럼 만듦
 *   그런데 intensity는 최대 중에 최소
 *   -> 큐에서 꺼낸 노드와 연결된 노드와의 거리가 intensity[연결된 노드]보다 큰 경우는 탐색 X
 * - 연결된 노드를 탐색할 때
 *   intensity[연결된 노드]와 다음 노드에 저장된 거리 중에 최댓값 구함
 *   그러고 그 최댓값을 통해서 다음에 탐색할 노드를 큐에 넣음 -> 계속 탐색
 *
 * 시간 복잡도
 * 노드 수 N, 간선 수 M -< O(MlogN)
 *
 * 실행 시간
 * 149.28 ms
 */

import java.util.*;

public class HikingCourse2 {
	
	static class Node implements Comparable<Node> {
		int e;
		int w;
		
		public Node(int e, int w) {
			this.e = e;
			this.w = w;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.w, o.w);
		}
	}
	
	static ArrayList<Node>[] list;
	static boolean[] isGate, isSummit;
	
	public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
		list = new ArrayList[n + 1];
		isGate = new boolean[n + 1];
		isSummit = new boolean[n + 1];
		
		for (int i = 1; i <= n; i++) {
			list[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < gates.length; i++) {
			isGate[gates[i]] = true;
		}
		
		for (int i = 0; i < summits.length; i++) {
			isSummit[summits[i]] = true;
		}
		
		for (int[] path : paths) {
			int s = path[0];
			int e = path[1];
			int w = path[2];
			
			if (isGate[s] || isSummit[e]) {
				list[s].add(new Node(e, w));
			} else if (isGate[e] || isSummit[s]) {
				list[e].add(new Node(s, w));
			} else {
				// 나머지는 양방향으로 저장
				list[s].add(new Node(e, w));
				list[e].add(new Node(s, w));
			}
		}
		
		return search(n, gates, summits);
	}
	
	private static int[] search(int n, int[] gates, int[] summits) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		int[] intensity = new int[n + 1];
		
		Arrays.fill(intensity, Integer.MAX_VALUE);
		
		// 먼저 출입구를 다 큐에 넣음
		for (int gate : gates) {
			pq.add(new Node(gate, 0));
			intensity[gate] = 0;
		}
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if (cur.w > intensity[cur.e]) {
				continue;
			}
			
			for (Node next : list[cur.e]) {
				// 휴식 없이 이동해야 하는 가장 긴 시간이기 때문에 최댓값 갱신
				int dist = Math.max(intensity[cur.e], next.w); // 둘 중에 최댓값 구함
				
				if (dist < intensity[next.e]) {
					intensity[next.e] = dist;
					pq.add(new Node(next.e, dist));
				}
			}
		}
		
		int minSummit = 0;
		int minIntensity = Integer.MAX_VALUE;
		
		Arrays.sort(summits);
		
		for (int summit : summits) {
			if (intensity[summit] < minIntensity) {
				minIntensity = intensity[summit];
				minSummit = summit;
			}
		}
		
		return new int[] {minSummit, minIntensity};
	}

}
