import java.util.*;

class Solution {
   static class Node {
		int start;
		int time;
		
		public Node(int start, int time) {
			this.start = start;
			this.time = time;
		}
	}
	
	static ArrayList<Node>[] list; // 상담 유형 별로 시간 저장
	static int[][] wait; // 멘토 수에 따른 대기 시간 저장 (상담 유형, 멘토 수)
	
	public int solution(int k, int n, int[][] reqs) {
		int len = reqs.length;
		
		list = new ArrayList[len + 1];
		
		for (int i = 0; i <= len; i++) {
			list[i] = new ArrayList<>();
		}
		
		for (int[] req : reqs) {
			list[req[2]].add(new Node(req[0], req[1]));
		}
		
		wait = new int[k + 1][n - k + 2];
		
		for (int i = 1; i <= k; i++) {
			for (int j = 1; j <= n - k + 1; j++) {
				PriorityQueue<Integer> pq = new PriorityQueue<>();
				
				for (Node node : list[i]) {
					if (pq.size() < j) { // 바로 상담 가능한 경우
						pq.add(node.start + node.time);
					} else {
						int endTime = pq.poll(); // 가장 먼저 끝나는 사람의 시간
						
						if (endTime > node.start) { // 대기 후에 상담
							wait[i][j] += endTime - node.start;
							pq.add(endTime + node.time);
						} else { // 바로 상담
							pq.add(node.start + node.time);
						}
					}
				}
			}
		}
		
        int answer = comb(1, n - k + 1, k);
        
        return answer;
    }
	
	private static int comb(int depth, int remain, int k) {
		// 상담 유형 수 넘어가면 종료
		if (depth > k) {
			return 0;
		}
		
		int result = Integer.MAX_VALUE;
		
		for (int i = 1; i <= remain; i++) {
			result = Math.min(result, comb(depth + 1, remain - i + 1, k) + wait[depth][i]);
		}
		
		return result;
	}
}
