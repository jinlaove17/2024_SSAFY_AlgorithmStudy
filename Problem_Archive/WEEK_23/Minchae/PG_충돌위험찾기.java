/**
 * 충돌위험 찾기
 * https://school.programmers.co.kr/learn/courses/30/lessons/340211
 * 
 * @author minchae
 * @date 2024. 12. 23.
 *
 * 문제 풀이
 *  - 문제에서 하라는대로 하고 결과 찾기
 *  - 큐 배열을 만들어서 각 로봇이 움직이는 경로 배열을 만듦
 *  - 로봇이 충돌하는 것을 확인할 때는
 *   	-> HashMap을 사용 -> 그런데 객체를 비교하려면 equals, hashcode를 override 해야함
 *
 * 시간 복잡도
 * O(n * m)
 *
 * 실행 시간
 * 211.70 ms
 **/

import java.util.*;

public class FindCollision {
	
	static class Pair {
		int x;
		int y;
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
				
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
				
			Pair pair = (Pair) obj;
			return x == pair.x && y == pair.y;
		}

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
	}
	
	static int n, answer;
	static Queue<Pair>[] robots;

	public static void main(String[] args) {
		int[][] points = {{3, 2}, {6, 4}, {4, 7}, {1, 4}};
		int[][] routes = {{4, 2}, {1, 3}, {2, 4}};

		System.out.println(solution(points, routes));
	}
	
	public static int solution(int[][] points, int[][] routes) {
        n = routes.length;
        
        robots = new LinkedList[n];
        
        for (int i = 0; i < n; i++) {
        	robots[i] = new LinkedList<>();
        }
        
        move(points, routes);
        
        countCollisions();
        
        return answer;
    }
	
	private static void move(int[][] points, int[][] routes) {
		for (int i = 0; i < n; i++) {
			// 로봇 시작 위치
			int[] route = routes[i];
			
			int x = points[route[0] - 1][0];
			int y = points[route[0] - 1][1];
			
			robots[i].add(new Pair(x, y));
			
			// 로봇 이동 위치 기록
			for (int j = 1; j < route.length; j++) {
				int nx = points[route[j] - 1][0];
				int ny = points[route[j] - 1][1];
				
				// r부터 먼저 움직임
				while (x != nx) {
					x += nx > x ? 1 : -1;
					robots[i].add(new Pair(x, y));
				}
				
				while (y != ny) {
					y += ny > y ? 1 : -1;
					robots[i].add(new Pair(x, y));
				}
			}
		}
	}
	
	private static void countCollisions() {
		HashMap<Pair, Integer> cntMap = new HashMap<>();
		
		while (true) {
			boolean finish = true; // 모든 로봇이 다 이동했는지 확인
			
			for (Queue<Pair> robot : robots) {
				if (!robot.isEmpty()) {
					finish = false;
					
					Pair cur = robot.poll();
					cntMap.put(cur, cntMap.getOrDefault(cur, 0) + 1); // 충돌 로봇 개수 확인
				}
			}
			
			// 해당 시간에 로봇이 충돌하는지 확인
			for (int value : cntMap.values()) {
				if (value > 1) {
					answer++;
				}
			}
			
			cntMap.clear();
			
			if (finish) {
				break;
			}
		}
	}

}
