/**
 * 16472 고냥이
 * https://www.acmicpc.net/problem/16472
 *
 * @author minchae
 * @date 2025. 3. 4.
 *
 * 문제 풀이
 * - 오랜만에 풀려고 하니까 너무 어렵다..
 * - 문제 보고 쉽네 라고 생각했는데 문자열 길이 보고 그냥 풀면 안되겠다고 생각
 * - 뭘로 풀어야 될까 생각하다.. 투포인터.. 내가 제일 싫어하는 문제 유형인걸 깨달았다.
 * 
 * - 일단 각 알파벳이 나온 개수도 저장해야 하지 않을까.. 생각해서 맵 선언 -> <Character, Integer>
 * - 그럼 맵의 크기는 알파벳 종류 개수가 됨
 * - 그리고 나는 일단 문자열을 다 쪼개서 char로 선언
 * - for문을 통해 단어를 다 확인 -> i를 기준으로 확인
 * - 맵에 횟수 저장 -> 그런데 저장하고 맵의 크기가 N을 초과한다면 start를 조정(증가)
 * - 초과하는 경우에는 등장 횟수 감소시키는데 이때 주의할 점 -> 만약 횟수가 0이 된다면 맵에서 아예 삭제해야 함 -> 맵의 크기를 종류로 보기 때문
 *
 * 시간 복잡도
 * O(N)
 *
 * 실행 시간
 * 196 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		char[] word = br.readLine().toCharArray();
		
		int answer = 0;
		int start = 0;
		
		HashMap<Character, Integer> alpha = new HashMap<>(); // 알파벳 등장 횟수 저장
		
		for (int i = 0; i < word.length; i++) {
			alpha.put(word[i], alpha.getOrDefault(word[i], 0) + 1); // 등장 횟수 저장
			
			// 맵의 사이즈(종류)가 N을 초과하는 경우 시작점 증가
			if (alpha.size() > N) {
				int count = alpha.get(word[start]);
				
				alpha.put(word[start], count - 1); // 등장 횟수 감소
				
				// 등장 횟수가 1이어서 감소했을 때 0이 되는 경우에는 맵에서 삭제 -> 종류 감소
				if (count == 1) {
					alpha.remove(word[start]);
				}
				
				start++;
			}
			
			answer = Math.max(answer, i - start + 1);
		}
		
		System.out.println(answer);
	}

}
