import java.util.*;

class Solution {
    static char[] operator = {'+', '-', '*'};
	static ArrayList<Long> number;
	static ArrayList<Character> oper;
	static long answer;
    
	public static long solution(String expression) {
		answer = 0;
		oper = new ArrayList<>();
		number = new ArrayList<>();
		
		String n = ""; // 수식에 있는 숫자(문자열)를 진짜 수로 바꾸기 위한 변수
        
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			
			if (c == '+' || c == '-' || c =='*') {
				number.add(Long.parseLong(n));
				oper.add(c);
				n = ""; // 다른 숫자를 넣어야하므로 초기화
			} else {
				n += c; // 숫자를 만날때마다 숫자를 추가해줌
			}
		
		}
        
		// 연산자 개수가 피연산자 개수보다 적으므로 for문을 다 돌고 난 후에 숫자리스트에 추가 해줘야함
		number.add(Long.parseLong(n));
		
		char[] priority = new char[3]; // 연산자 우선 순위를 담을 배열
		boolean[] visited = new boolean[3];
		permutation(priority, visited, 0, 3, 3);
		
        return answer;
    }
	
	// 순열 - dfs 이용
	public static void permutation(char[] priority, boolean[] visited, int depth, int n, int r) {
		if (depth == r) {
			
			// 우선순위로 수식 계산했을 때 가장 큰 값을 얻기 위해 숫자와 연산자 리스트를 복사함 -> 원본으로 하면 원본 리스트값이 이상해짐
			ArrayList<Long> copyNum = new ArrayList<>(number);
			ArrayList<Character> copyOper = new ArrayList<>(oper);
			
			// 우선순위에 맞게 계산하기
			for (char o : priority) { // 우선순위 배열에 있는 연산자 하나씩 꺼내옴
				for (int i = 0; i < copyOper.size(); i++) {
					// 현재 수식에 우선순위 배열에 있는 연산자가 있다면 계산
					if (o == copyOper.get(i)) {
						long result = 0;
						
						if (o == '+') {	
							result = copyNum.remove(i) + copyNum.remove(i);
						} else if (o == '-') {	
							result = copyNum.remove(i) - copyNum.remove(i);
						} else if (o == '*') {					
							result = copyNum.remove(i) * copyNum.remove(i);
						}
						
						copyNum.add(i, result);
						copyOper.remove(i); // 연산자를 만나는 즉시 넣어줬기 때문에 중복되는 연산자가 있을수도 있음 -> 해당 연산자를 없애줌
						i--; // --해주는 이유는 해당 연산자가 수식에 더 남아있을수도 있기 때문에 해주는 것이다.
					}
				}
			}

			answer = Math.max(answer, Math.abs(copyNum.get(0)));
			return;
		}
		
		// 우선순위가 될 수 있는 모든 순열 구함
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				visited[i] = true;
				priority[depth] = operator[i];
				
				permutation(priority, visited, depth + 1, n, r);
				visited[i] = false;
			}
		}
		
	}
}
