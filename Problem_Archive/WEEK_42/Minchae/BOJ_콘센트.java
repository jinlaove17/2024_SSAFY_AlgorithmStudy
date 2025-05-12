/**
 * 23843 콘센트
 * https://www.acmicpc.net/problem/23843
 *
 * @author minchae
 * @date 2025. 5. 12.
 *
 * 문제 풀이
 * - 우선순위큐 이용 -> 콘센트라 생각하고 충전시간 기준으로 내림차순 정렬
 * - 충전 시간이 긴 전자기기를 먼저 충전 시킴 -> 충전이 빨리 끝나는 곳이 충전 시키면 됨
 *
 * 시간 복잡도
 * 정렬 O(NlogN)
 * pq O(NlogM)
 *
 * 실행 시간
 * 160 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static int N, M;
	static Integer[] time;
	static PriorityQueue<Integer> pq;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        time = new Integer[N];
        pq = new PriorityQueue<>();
        
        st = new StringTokenizer(br.readLine());
        
        for (int i = 0; i < N; i++) {
        	time[i] = Integer.parseInt(st.nextToken());
        }
        
        // 일단 먼저 콘센트 개수만큼 0 삽입
        for (int i = 0; i < M; i++) {
        	pq.add(0);
        }
        
        Arrays.sort(time, Collections.reverseOrder());
        
        int result = 0;
        
        for (int i = 0; i < N; i++) {
        	int sum = pq.poll() + time[i];
        	pq.add(sum);
        	
        	result = Math.max(result, sum);
        }
        
        System.out.println(result);
	}

}
