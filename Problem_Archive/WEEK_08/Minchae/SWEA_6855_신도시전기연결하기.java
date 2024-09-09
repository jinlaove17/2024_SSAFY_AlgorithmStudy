/**
 * D4 6855. 신도시 전기 연결하기
 *
 * @author minchae
 * @date 2024. 9. 8.
 */

import java.util.*;
import java.io.*;

public class Solution {
	
	static int N, K;
	
	static int[] home;
	static int[] len;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			home = new int[N];
			len = new int[N - 1]; // 각 집 사이의 거리 저장
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N; i++) {
				home[i] = Integer.parseInt(st.nextToken());
			}
			
			for (int i = 0; i < N - 1; i++) {
				len[i] = home[i + 1] - home[i];
			}
			
			Arrays.sort(len); // 거리가 짧은 순으로 정렬
			
			int answer = 0;
			
			// 발전소를 가장 먼 곳에 세우기 위해 K를 뺀 나머지까지의 거리를 더함
			// 집 사이의 거리가 가장 먼 곳에 발전소를 세우기 -> 최소 전선의 길이를 구할 수 있
			for (int i = 0; i < N - K; i++) {
				answer += len[i];
			}
			
			System.out.println("#" + t + " " + answer);
		}

	}

}
