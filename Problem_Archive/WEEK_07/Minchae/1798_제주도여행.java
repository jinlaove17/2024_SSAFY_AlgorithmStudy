/**
 * D5 1798. 범준이의 제주도 여행 계획
 * 
 * @author minchae
 * @date 2024. 9. 5.
 * */

import java.util.*;
import java.io.*;

public class Solution {
	
	static class Point {
		String type; // 타입
		int num;
		int time; // 관광 소요 시간
		int good; // 관광 만족도
		Point near; // 관광지와 가까운 호텔
		
		public Point(String type, int num) {
			this.type = type;
			this.num = num;
		}
		
		public Point(String type, int num, int time, int good) {
			this.type = type;
			this.num = num;
			this.time = time;
			this.good = good;
		}
	}

	static int N, M;
	
	static int[][] map;
	
	static ArrayList<Point> spot;
	static ArrayList<Point> hotel;
	static Point airport;
	
	static boolean[] visited;
	static Stack<Integer> path;
	
	static int maxGood;
	static ArrayList<Integer> maxPath;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			map = new int[N + 1][N + 1];
			
			for (int i = 1; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				
				for (int j = i + 1; j <= N; j++) {
					map[i][j] = map[j][i] = Integer.parseInt(st.nextToken());
				}
			}
			
			spot = new ArrayList<>();
			hotel = new ArrayList<>();
			
			for (int i = 1; i <= N; i++) {
				st = new StringTokenizer(br.readLine());
				
				String type = st.nextToken();
				
				if (type.equals("H")) {
					hotel.add(new Point(type, i));
				} else if (type.equals("P")) {
					int time = Integer.parseInt(st.nextToken());
					int good = Integer.parseInt(st.nextToken());
					
					spot.add(new Point(type, i, time, good));
				} else {
					airport = new Point(type, i);
				}
			}
		
			// 각 관광지 별로 가장 가까운 호텔 찾기
			for (Point s : spot) {
				int min = Integer.MAX_VALUE;
				
				for (Point h : hotel) {
					if (map[s.num][h.num] < min) {
						min = map[s.num][h.num];
						s.near = h;
					}
				}
			}
			
			maxGood = 0;
			
			visited = new boolean[N + 1];
			path = new Stack<>(); // 이동 경로 저장
			
			solve(0, airport, 0, 0);
			
			System.out.print("#" + t + " " + maxGood + " ");
			
			if (maxGood > 0) {
				for (int num : maxPath) {
					System.out.print(num + " ");
				}
			}
			
			System.out.println();
		}
	}
	
	private static void solve(int depth, Point start, int good, int time) {
		if (depth == M) {
			// 최대 만족도 갱신
			if (good > maxGood) {
				maxGood = good;
				maxPath = new ArrayList<>(path);
			}
			
			return;
		}
		
		boolean go = false;
		
		for (Point cur : spot) {
			if (visited[cur.num]) {
				continue;
			}
			
			// 관광지까지 들린 시간
			int sum = time + map[start.num][cur.num] + cur.time;
			
			if (depth == M - 1) {
				// 마지막 날에는 관광지 들렸다가 무조건 공항을 가야함
				sum += map[cur.num][airport.num];
			} else {
				// 이전까지는 관광지 보고 관광지와 가까운 호텔로 이동
				sum += map[cur.num][cur.near.num];
			}
			
			// 9시간을 넘어가면 해당 관광지 못감
			if (sum > 540) {
				continue;
			}
			
			go = true;
			
			sum = time + map[start.num][cur.num] + cur.time;
			
			visited[cur.num] = true;
			path.push(cur.num);
			
			solve(depth, cur, good + cur.good, sum);
			
			// 원복
			visited[cur.num] = false;
			path.pop();
		}
		
		// 아무 관광지를 못가는 경우
		if (!go) {
			if (depth == M - 1) {
				// 마지막 날은 공항으로 감
				path.push(airport.num);
				solve(depth + 1, airport, good, 0);
				path.pop();
			} else {
				// 마지막 날이 아니면 호텔로 감
				for (Point h : hotel) {
					// 9시간 이내에 도착할 수 있는 호텔로 가봄
					if (time + map[start.num][h.num] <= 540) {
						path.push(h.num);
						solve(depth + 1, h, good, 0);
						path.pop();
					}
				}
			}
		}
	}

}
