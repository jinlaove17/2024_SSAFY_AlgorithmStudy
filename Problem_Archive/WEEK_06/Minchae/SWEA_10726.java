/**
 * D3 10726. 이진수 표현
 * 33,920 kb  193 ms
 * 
 * @author minchae
 * @date 2024. 8. 26.
 * */

import java.util.*;
import java.io.*;

public class Solution {
	
	static int N, M;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			String answer = onOff() ? "ON" : "OFF";
			
			System.out.println("#" + t + " " + answer);
		}
	}
	
	private static boolean onOff() {
		for (int i = 0; i < N; i++) {
			if ((M & 1 << i) == 0) {
				return false;
			}
		}
		
		return true;
	}

}
