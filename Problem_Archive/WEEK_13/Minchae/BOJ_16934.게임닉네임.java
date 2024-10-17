/**
 * 16934 게임 닉네임
 * https://www.acmicpc.net/problem/16934
 * 
 * @author minchae
 * @date 2024. 10. 17.
 * 
 * 문제 풀이
 * 	- map과 set 이용
 *  - map에는 닉네임 등장 횟수 저장
 *  - set에는 각 닉네임마다 만들 수 있는 접두사 저장
 * 
 * 시간 복잡도
 * O(N * 문자열 길이)
 * 
 * 실행 시간
 * 708 ms
 * */

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		HashMap<String, Integer> word = new HashMap<>(); // 특정 단어가 등장한 횟수
		HashSet<String> prefix = new HashSet<>(); // 모든 단어의 접두사 저장
		
		StringBuilder sb = new StringBuilder();
		
		for (int n = 0; n < N; n++) {
			String input = br.readLine();
			
			word.put(input, word.getOrDefault(input, 0) + 1); // 단어 개수 저장
			
			boolean flag = false; // 별칭 찾았는지 확인
			String result = "";
			
			// 인덱스 0부터 문자열 길이까지 잘라봄
			for (int i = 1; i <= input.length(); i++) {
				String cut = input.substring(0, i);
				
				// 별칭을 찾지 못한 경우 && 현재 자른 문자열이 접두사 set에 없는 경우
				if (!flag && !prefix.contains(cut)) {
					result = cut;
					flag = true;
				}
				
				prefix.add(cut); // 만들 수 있는 모든 접두사 저장
			}
			
			// 접두사를 찾지 못한 경우 닉네임을 별칭으로 저장
			if (!flag) {
				result = input;
			}
			
			// 닉네임이 여러 개인 경우 개수를 함께 출력
			sb.append(result).append(word.get(input) > 1 ? word.get(input) : "").append("\n");
		}

		System.out.println(sb.toString());
	}

}
