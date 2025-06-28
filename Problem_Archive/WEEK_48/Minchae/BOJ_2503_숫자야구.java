/**
 * 2503 숫자 야구
 * https://www.acmicpc.net/problem/2503
 *
 * @author minchae
 * @date 2025. 6. 28.
 *
 * 문제 풀이
 * - 다 비교하기
 *
 * 푼 시간
 * 11:00 ~ 11:34 (34분)
 *
 * 실행 시간
 * 116 ms
 **/

import java.io.*;
import java.util.*;

class Node {
	String num;
	int strike;
	int ball;
	
	public Node(String num, int strike, int ball) {
		this.num = num;
		this.strike = strike;
		this.ball = ball;
	}
	
	public boolean check(String target) {
		int strikeCnt = 0;
		int ballCnt = 0;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (target.charAt(i) == this.num.charAt(j)) {
					if (i == j) {
						strikeCnt++;
					}
					
					if (i != j) {
						ballCnt++;
					}
				}
			}
		}
		
		return strikeCnt == this.strike && ballCnt == this.ball;
	}
}

public class Main {
	
	static int N;
	static Node[] info;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        
        info = new Node[N];
        
        for (int i = 0; i < N; i++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	String num = st.nextToken();
        	int strike = Integer.parseInt(st.nextToken());
        	int ball = Integer.parseInt(st.nextToken());
        	
        	info[i] = new Node(num, strike, ball);
        }
        
        int answer = 0;
        
        for (int i = 1; i < 10; i++) {
        	for (int j = 1; j < 10; j++) {
        		if (i == j) {
        			continue;
        		}
        		
        		for (int k = 1; k < 10; k++) {
        			if (i == k || j == k) {
        				continue;
        			}
        			
        			String target = String.valueOf(i * 100 + j * 10 + k);
        			
        			boolean check = true;
        			
        			// 비교하다가 맞지 않는 정보 나오면 바로 종료
        			for (Node question : info) {
        			    if (!question.check(target)) {
        			    	check = false;
        			        break;
        			    }
        			}
        			
        			// 그대로 true인 경우에만 카운트
        			if (check) {
        				answer++;
        			}
        		}
        	}
        }

        System.out.println(answer);
	}

}
