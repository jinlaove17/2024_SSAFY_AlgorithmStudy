/**
 * 2655 가장높은탑쌓기
 * https://www.acmicpc.net/problem/2655
 *
 * @author minchae
 * @date 2025. 1. 14.
 *
 * 문제 풀이
 * - 먼저 벽돌 배열을 만들어 벽돌 넓이를 기준으로 내림차순 정렬
 * - dp[i]에 i번째 벽돌이 맨 위에 있을 때 최대 높이를 저장함
 * - 이전에 쌓인 벽돌보다 무게가 적고, 높이가 높아지는 경우에만 dp 배열 값 갱신
 *
 * 시간 복잡도
 * 정렬 : O(NlogN)
 * dp 계산 : O(N^2)
 * 탑 계산 : O(N)
 *
 * 실행 시간
 * 108 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static class Brick implements Comparable<Brick> {
		int num;
		int area;
		int height;
		int weight;
		
		public Brick(int num, int area, int height, int weight) {
			this.num = num;
			this.area = area;
			this.height = height;
			this.weight = weight;
		}
		
		// 넓이를 기준으로 내림차순 정렬
		@Override
		public int compareTo(Main.Brick o) {
			return Integer.compare(o.area, this.area);
		}
	}
	
	static int N;
	
	static Brick[] bricks;
	static int[] dp;
	static int[] path; // 역추적 할 때 사용됨

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        
        bricks = new Brick[N];
        dp = new int[N];
        path = new int[N];
        
        for (int i = 0; i < N; i++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	int area = Integer.parseInt(st.nextToken());
        	int height = Integer.parseInt(st.nextToken());
        	int weight = Integer.parseInt(st.nextToken());
        	
        	bricks[i] = new Brick(i + 1, area, height, weight);
        }
        
        Arrays.sort(bricks);
        
        // 초기값
        for (int i = 0; i < N; i++) {
        	dp[i] = bricks[i].height;
        }
        
        int max = dp[0]; // 쌓인 탑의 최대 높이 저장
        
        for (int i= 1; i < N; i++) {
        	for (int j = 0; j < i; j++) {
        		if (bricks[i].weight < bricks[j].weight) {
        			dp[i] = Math.max(dp[i], dp[j] + bricks[i].height);
        			max = Math.max(max, dp[i]);
        		}
        	}
        }
        
        List<Integer> answer = new ArrayList<>();
        
        for (int i = N - 1; i >= 0; i--) {
        	if (dp[i] == max) {
        		answer.add(bricks[i].num);
        		max -= bricks[i].height; // 최대 높이에서 현재 경로에 있는 탑의 높이를 뺌
        	}
        }
        
        System.out.println(answer.size());
        
        for (int num : answer) {
        	System.out.println(num);
        }
        
//        for (int i = 0; i < N; i++) {
//        	dp[i] = bricks[i].height;
//        	path[i] = i;
//        }
//        
//        for (int i = 1; i < N; i++) { // 현재 벽돌
//        	for (int j = 0; j < i; j++) { // 이전 벽돌
//        		if (bricks[i].weight < bricks[j].weight) {
//        			// 높이가 더 높아지는 경우에만 dp 저장하고 경로 저장
//        			if (dp[i] < dp[j] + bricks[i].height) {
//        				dp[i] = dp[j] + bricks[i].height;
//        				path[i] = j; // 탑 경로 저장
//        			}
//        		}
//        	}
//        }
//        
//        int maxIdx = 0;
//        
//        for (int i = 0; i < N; i++) {
//        	if (dp[i] > dp[maxIdx]) {
//        		maxIdx = i;
//        	}
//        }
//        
//        List<Integer> answer = new ArrayList<>();
//        
//        while (true) {
//        	answer.add(bricks[maxIdx].num); // 가장 위에 있는 벽돌부터 저장
//        	
//        	if (maxIdx == path[maxIdx]) {
//        		break;
//        	}
//        	
//        	maxIdx = path[maxIdx];
//        }
//        
//        System.out.println(answer.size());
//        
//        for (int num : answer) {
//        	System.out.println(num);
//        }
	}

}
