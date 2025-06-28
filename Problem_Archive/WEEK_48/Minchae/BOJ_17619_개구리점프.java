/**
 * 17619 개구리 점프
 * https://www.acmicpc.net/problem/17619
 *
 * @author minchae
 * @date 2025. 6. 28.
 *
 * 문제 풀이
 * - 이동 가능한 경우 : 기준 x2 >= 비교하는 x1 만족하면 됨
 *
 * 실행 시간
 * 808 ms
 **/

import java.io.*;
import java.util.*;

class Node implements Comparable<Node> {
	int num;
	int x1;
	int x2;
	
	public Node(int num, int x1, int x2) {
		this.num = num;
		this.x1 = x1;
		this.x2 = x2;
	}

	@Override
	public int compareTo(Node o) {
		return this.x1 - o.x1;
	}
}

public class Main {
	
	static int N, Q;
	static Node[] info;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        
        info = new Node[N];
        
        for (int i = 0; i < N; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int x1 = Integer.parseInt(st.nextToken());
        	int x2 = Integer.parseInt(st.nextToken());
        	int y = Integer.parseInt(st.nextToken());
        	
        	info[i] = new Node(i, x1, x2);
        }
        
        Arrays.sort(info);
        
        int[] group = new int[N];
        
        int start = 0;
        int end = info[0].x2;
        
        for (int i = 1; i < N; i++) {
        	if (info[i].x1 <= end) {
        		end = Math.max(end, info[i].x2);
        	} else {
        		// 벗어나는 경우 그룹 번호 증가
        		start++;
        		end = info[i].x2;
        	}
        	
        	group[info[i].num] = start;
        }
        
        StringBuilder sb = new StringBuilder();
        
        while (Q-- > 0) {
        	st = new StringTokenizer(br.readLine());
        	
        	int n1 = Integer.parseInt(st.nextToken()) - 1;
        	int n2 = Integer.parseInt(st.nextToken()) - 1;
        	
        	if (group[n1] == group[n2]) {
        		sb.append(1).append("\n");
        	} else {
        		sb.append(0).append("\n");
        	}
        }

        System.out.println(sb.toString());
	}

}
