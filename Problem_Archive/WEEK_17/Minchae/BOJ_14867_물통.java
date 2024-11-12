/**
 * 14867 물통
 * https://www.acmicpc.net/problem/14867
 *
 * @author minchae
 * @date 2024. 11. 11.
 *
 * 문제 풀이
 *  - 1, 2 : a, b에 물 가득 채우기
 *  - 3, 4 : a, b의 물을 모두 버림
 *  - 5, 6 : a나 b의 물을 다른 물통으로 옮기기
 *  - 물을 버리거나 옮기기 전에 해당 물통에 물이 있는지 확인(비어있는지), 채울 때는 해당 물통이 가득 차 있는지 확인
 *  - 위의 경우를 확인하고 for문으로 6가지 경우를 봄
 *  - 처음에 물통양을 set에 저장할 때 '숫자숫자'로만 저장해서 23점이 나옴
 *      -> 이렇게 저장하면 '11 2', '1 12'를 똑같은 물통으로 봐서 탐색을 제대로 할 수 없음
 *         그래서 공백을 추가해서 저장하니 100점이 나왔다...
 *
 * 시간 복잡도
 * O(a * b) -> 물통이 0부터 a, b까지 채워질 수 있기 때문
 *
 * 실행 시간
 * 1280 ms
 * */

import java.util.*;
import java.io.*;

public class Main {
	
	static class Node {
		int x;
		int y;
		int cnt;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Node(int x, int y, int cnt) {
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}
	}
	
	static int a, b, c, d;
	static HashSet<String> set;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        
        set = new HashSet<>();

        System.out.println(bfs());
	}
	
	private static int bfs() {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(0, 0));
		
		while (!q.isEmpty()) {
			Node cur = q.poll();
			
			if (cur.x == c && cur.y == d) {
				return cur.cnt;
			}
			
			
			for (int i = 1; i <= 6; i++) {
				int nx = cur.x;
				int ny = cur.y;
				
				// 물 옮길 수 있는지 확인
				if (!check(i, nx, ny)) {
					continue;
				}
				
				// 물통의 다음 상태 구함
				Node next = pourWater(i, nx, ny);
				
				StringBuilder result = new StringBuilder();
				result.append(next.x).append(next.y);
				
				// set에 포함된 경우가 아닌 경우에만 큐에 삽입
				if (!set.contains(result.toString())) {
					set.add(result.toString());
					q.add(new Node(next.x, next.y, cur.cnt + 1));
				}
			}
		}
		
		return -1;
	}
	
	// 물을 옮기기 전에 옮기거나 채울 수 있는지 확인
	private static boolean check(int type, int x, int y) {
		if (type == 1 && x == a) { // 물통 A에 물을 채울건데 이미 가득 있는 경우
			return false;
		} else if (type == 2 && y == b) { // 물통 B에 물을 채울건데 이미 가득 있는 경우
			return false;
		} else if ((type == 3 || type == 5) && x == 0) { // 물통 A를 비우거나 물통 A에서 B로 옮길 때 물통 A가 이미 비어있는 경우
			return false;
		} else if ((type == 4 || type == 6) && y == 0) { // 물통 B를 비우거나 물통 B에서 A로 옮길 때 물통 B가 이미 비어있는 경우
			return false;
		}
		
		return true;
	}
	
	// 물 옮기기
	private static Node pourWater(int type, int x, int y) {
		if (type == 1) {
			x = a;
		} else if (type == 2) {
			y = b;
		} else if (type == 3) {
			x = 0;
		} else if (type == 4) {
			y = 0;
		} else if (type == 5) {
			if (x <= b - y) {
				y += x;
				x = 0;
			} else {
				x = x - (b - y);
				y = b;
			}
		} else {
			if (y <= a - x) {
				x += y;
				y = 0;
			} else {
				y = y - (a - x);
				x = a;
			}
		}
		
		return new Node(x, y);
	}

}
