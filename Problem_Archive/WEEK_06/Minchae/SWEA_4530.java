/**
 * D4 4530. 극한의 청소 작업
 * 
 * @author minchae
 * @date 2024. 8. 27.
 * */

import java.util.*;
import java.io.*;

public class Solution {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
		
			// 입력값을 012356789로 이루어져 있는 9진수로 봄
			String A = st.nextToken();
			String B = st.nextToken();
			
			long numA = convert(A);
			long numB = convert(B);
			
			long answer;
			
			if (numA < 0 && numB > 0) {
				answer = numB - numA - 1; // A가 음수이고, B가 양수이면 0층은 빼줘야하기 때문에 -1 해줌
			} else {
				answer = numB - numA;
			}
			
			System.out.println("#" + t + " " + answer);
		}
	}
	
	private static long convert(String num) {
		boolean minus = num.charAt(0) == '-';
		
		int start = minus ? 1 : 0; // 음수는 -를 뺀 인덱스까지 진행
		int idx = 0;
		long result = 0;
		
		// 뒤에서부터 진행
		for (int i = num.length() - 1; i >= start; i--) {
			int cur = num.charAt(i) - '0';
			
			// 4를 초과하는 경우 1 감소
			// 입력값이 4를 제외한 층이기 때문에 56789이면 1 감소시킴
			// 012345678로 바꾸는 것
			if (cur > 4) {
				cur -= 1;
			}
			
			// idx = 자릿수
			result += (long) (cur * Math.pow(9, idx++)); // 십진수로 변환하고 result에 더함
		}
		
		return minus ? -result : result; // 음수면 - 붙여줌
	}
}