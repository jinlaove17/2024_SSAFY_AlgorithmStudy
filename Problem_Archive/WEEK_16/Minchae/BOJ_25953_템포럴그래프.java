/**
 * 25953 템포럴 그래프
 * https://www.acmicpc.net/problem/25953
 *
 * @author minchae
 * @date 2024. 11. 5.
 *
 * 문제 풀이
 *  - 대수현이 dfs로 풀었다가 시초 났다고 하고 진주가 생각의 전환이 필요하다고 해서 한참을 고민했던 문제..
 *  - dist 배열 만듦 : [시간][해당 정점까지의 거리] -> dp 배열을 어떻게 저장할지 생각하는게 오래 걸림
 *  - 최소 거리를 구하는 것이기 때문에 dist에는 큰 값을 저장
 *  - 입력 받을 때 특정 시간에 해당 정점까지의 최소 거리 저장
 *
 * 시간 복잡도
 * O((n + m) * t)
 *
 * 실행 시간
 * 752 ms
 * */

import java.util.*;
import java.io.*;

public class Main {
	
	static final int INF = 987654321;
	
	static int n, t, m;
	static int s, e;
	
	static int[][] dist;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());

        s = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        
        dist = new int[1001][10000];
        
        for (int i = 0; i < n; i++) {
        	dist[0][i] = INF;
        }
        
        dist[0][s] = 0; // 시작점은 0
        
        for (int i = 1; i <= t; i++) {
        	// 현재 시간 거리에 이전 시간 거리 저장해둠
        	// 최소 거리가 유지되도록 함
        	for (int j = 0; j < n; j++) {
        		dist[i][j] = dist[i - 1][j];
        	}
        	
        	for (int j = 0; j < m; j++) {
        		st = new StringTokenizer(br.readLine());
        		
        		int a = Integer.parseInt(st.nextToken());
        		int b = Integer.parseInt(st.nextToken());
        		int w = Integer.parseInt(st.nextToken());
        		
        		// 현재 값과 거쳐서 오는 값을 비교해 더 작은 값 저장
        		dist[i][a] = Math.min(dist[i][a], dist[i - 1][b] + w);
        		dist[i][b] = Math.min(dist[i][b], dist[i - 1][a] + w);
        	}
        }

        System.out.println(dist[t][e] == INF ? -1 : dist[t][e]);
	}

}
