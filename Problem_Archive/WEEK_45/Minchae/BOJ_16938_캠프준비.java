/**
 * 16938 캠프 준비
 * https://www.acmicpc.net/problem/16938
 *
 * @author minchae
 * @date 2025. 6. 8.
 *
 * 문제 풀이
 * - 문제 2개 선택 -> 다 선택해보기
 *
 * 걸린 시간
 * 20분
 *
 * 실행 시간
 * 116 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static int N, L, R, X;
	static int[] A;
	
	static int answer;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        
        A = new int[N];
        
        st = new StringTokenizer(br.readLine());
        
        for (int i = 0; i < N; i++) {
        	A[i] = Integer.parseInt(st.nextToken());
        }
        
        Arrays.sort(A);

        for (int i = 0; i < N; i++) {
        	select(1, i, A[i], A[i], 0);
        }
        
        System.out.println(answer);
	}
	
	private static void select(int depth, int start, int cur, int min, int max) {
		if (depth >= 2) {
			if (cur >= L && cur <= R && max - min >= X) {
				answer++;
			}
		}
		
		for (int i = start + 1; i < N; i++) {
			select(depth + 1, i, cur + A[i], Math.min(min, A[i]), Math.max(max, A[i]));
		}
	}

}
