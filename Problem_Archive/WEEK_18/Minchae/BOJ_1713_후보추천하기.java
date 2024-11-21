/**
 * 1713 후보 추천하기
 * https://www.acmicpc.net/problem/1713
 * 
 * @author minchae
 * @date 2024. 11. 21.
 * 
 * 문제 풀이
 *  - 우선순위큐 이용
 *    추천 횟수를 증가시킬 때 큐에서 삭제한 다음 다시 넣어야 함 -> 우선순위큐는 삽입/삭제 시에만 정렬이 일어나기 때문
 *  - 나머지는 문제에 있는대로 구현하면 됨
 *  
 * 시간복잡도
 * O(M * (N + logN))
 *
 * 실행 시간
 * 136 ms
 */

import java.io.*;
import java.util.*;

public class Main2 {
	
	static class Student implements Comparable<Student> {
		int num;
		int time; // 사진이 게시된 시간
		int cnt; // 추천받은 횟수
		
		public Student(int num, int time, int cnt) {
			this.num = num;
			this.time = time;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Student o) {
			if (this.cnt == o.cnt) {
				return Integer.compare(this.time, o.time);
			}
			
			return Integer.compare(this.cnt, o.cnt);
		}
	}
	
	static int N, M;
	static PriorityQueue<Student> pq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		pq = new PriorityQueue<>();
		
		StringTokenizer st = new StringTokenizer(br.readLine());

		outer: for (int i = 0; i < M; i++) {
			int num = Integer.parseInt(st.nextToken());
			
			for (Student cur : pq) {
				// 이미 사진이 게시되어 있는 경우 추천 횟수만 증가시킴
				if (cur.num == num) {
					pq.remove(cur);
					cur.cnt++;
					pq.add(cur);
					
					continue outer;
				}
			}
			
			// 비어있는 사진틀이 없는 경우
			if (pq.size() == N) {
				pq.poll();
			}
			
			// 새로운 사진 게시
			pq.add(new Student(num, i, 1));
		}
		
		ArrayList<Student> result = new ArrayList<>(pq);
		Collections.sort(result, (o1, o2) -> o1.num - o2.num);
		
		StringBuilder sb = new StringBuilder();
		
		for (Student cur : result) {
			sb.append(cur.num + " ");
		}
		
		System.out.println(sb.toString());
	}

}
