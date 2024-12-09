/**
 * 3019 테트리스
 * https://www.acmicpc.net/problem/3019
 * 
 * @author minchae
 * @date 2024. 12. 9.
 * 
 * 문제 풀이
 * 	- 처음에 블록을 다 회전시켜 보면서 들어갈 수 있는지 확인해야 하는 건지 고민함
 * 		근데 뭔가 그렇게 하나하나 하기에 코드가 너무 길어질 것 같았음
 * 		그래서 다른 방법 생각함
 *  - 블록마다 띄워야 하는 높이의 개수를 미리 저장하고 높이 차를 이용해 경우의 수를 구하면 되겠다고 생각
 *  - 반복문을 통해 높이차가 같은지 확인하면 됨
 *  
 *  
 * 시간복잡도
 * O(C * len)
 *
 * 실행 시간
 * 104 ms
 */

import java.io.*;
import java.util.*;

public class Main {
	
	static String[][] blocks = {
			{"0", "0000"},
			{"00"},
			{"001", "10"},
			{"100", "01"},
			{"000", "01", "101", "10"},
			{"000", "00", "011", "20"},
			{"000", "02", "110", "00"}
	};
	
	static int C, P;
	static int[] h;
	
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		C = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken()) - 1;
		
		h = new int[C];
		
		st = new StringTokenizer(br.readLine());
		
		for (int i = 0; i < C; i++) {
			h[i] = Integer.parseInt(st.nextToken());
		}

		for (String block : blocks[P]) {
			check(block);
		}
		
		System.out.println(answer);
	}
	
	private static void check(String block) {
		int len = block.length();
		
		// 블록 길이만큼 검사
		for (int i = 0; i <= C - len; i++) {
			boolean flag = true;
			int diff = h[i] - block.charAt(0) - '0'; // 높이 차이 구함
					
			for (int j = 1; j < len; j++) {
				// 높이가 다르다면 더이상 확인할 필요 없음
				if (diff != h[i + j] - block.charAt(j) - '0') {
					flag = false;
					break;
				}
			}
			
			if (flag) {
				 answer++;
			}
		}
	}

}
