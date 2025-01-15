/**
 * 주사위 고르기
 * https://school.programmers.co.kr/learn/courses/30/lessons/258709
 *
 * @author minchae
 * @date 2025. 1. 15.
 *
 * 문제 풀이
 * - N개의 주사위 중 (N / 2)개의 주사위를 고르는 조합
 * - (N / 2)개의 주사위를 가져갔을 때 각각 주사위의 조합을 고르기
 *   -> 경우가 워낙 많기 때문에 무조건 시간 초과 발생할 것
 *
 * - 주사위 면의 수가 중복된다는 것을 활용해서 경우의 수를 줄이기
 * - 주사위를 고르고 주사위 면을 고르고 나온 점수의 조합에서 중복을 제거하는 것
 *   -> 각 숫자가 몇 번 나왔는지 세기 (HashMap 사용)
 *   -> {1, 2, 2, 1}, {1, 1, 3, 3} 이렇게 두 개의 조합이 있을 때 (1점 2개, 2점 2개), (1점 2개, 3점 2개)
 *   -> 4번만 비교하면 됨
 *
 * 시간 복잡도
 * 조합 : O(2^N−1)
 *
 * 실행 시간
 * 818.41 ms
 */

import java.util.*;

public class SelectDice {

    static int N;

    static int[] check;
    static ArrayList<int[]> select; // 만들 수 있는 주사위 조합 저장

    public int[] solution(int[][] dice) {
        N = dice.length;

        check = new int[N / 2];
        select = new ArrayList<>();

        comb(0, 0); // 주사위 조합 구하기

        int[] answer = getDiceComb(dice);

        // 주사위 인덱스를 0부터 시작했기 때문에 1 증가시키기
        for (int i = 0; i < N / 2; i++) {
            answer[i]++;
        }

        return answer;
    }

    // (N / 2)개의 주사위 모든 조합 구하기
    private static void comb(int depth, int start) {
        if (depth == N / 2) {
            addSelectDice();
            return;
        }

        for (int i = start; i < N; i++) {
            check[depth] = i;
            comb(depth + 1, i + 1);
        }
    }

    // 선택된 주사위 배열 만들고 리스트에 넣기
    private static void addSelectDice() {
        int[] result = new int[N / 2];

        for (int i = 0; i < N / 2; i++) {
            result[i] = check[i];
        }

        select.add(result);
    }

    // 점수 빈도 수 계산
    private static HashMap<Integer, Integer> countScore(int[] combDice, int[][] dice) {
    	HashMap<Integer, Integer> result = new HashMap<>(); // 점수가 등장한 횟수 저장

        for (int i = 0; i < 6; i++) {
        	int num = dice[combDice[0]][i];
        	
            result.put(num, result.getOrDefault(num, 0) + 1);
        }

        // 이전에 나온 점수에 현재 던진 주사위의 값을 더함
        for (int i = 1; i < N / 2; i++) {
            HashMap<Integer, Integer> tmp = new HashMap<>();

            for (int prev : result.keySet()) {
                for (int j = 0; j < 6; j++) {
                    int score = prev + dice[combDice[i]][j];
                    
                    tmp.put(score, tmp.getOrDefault(score, 0) + result.get(prev));
                }
            }

            result = tmp; // 갱신
        }

        return result;
    }
    
    // A가 승리할 확률이 높은 주사위 조합 구하기
    private static int[] getDiceComb(int[][] dice) {
    	int[] result = new int[N / 2];
    	int max = 0;

        for (int i = 0; i < select.size() / 2; i++) {
            int cur = i;
            int other = select.size() - i - 1;

            // 각 주사위 조합
            int[] diceA = select.get(cur);
            int[] diceB = select.get(other);

            // 주사위 조합들의 점수 조합의 빈도 수
            HashMap<Integer, Integer> cntA = countScore(diceA, dice);
            HashMap<Integer, Integer> cntB = countScore(diceB, dice);

            int winA = 0;
            int winB = 0;

            // 경우의 수 구하기
            for (int score1 : cntA.keySet()) {
                for (int score2 : cntB.keySet()) {
                    int value = cntA.get(score1) * cntB.get(score2);

                    if (score1 > score2) {
                    	winA += value;
                    } else if (score1 < score2) {
                    	winB += value;
                    }
                }
            }
            
            // 최댓값 갱신
            if (max < winA) {
            	max = winA;
            	result = diceA;
            }
            
            if (max < winB) {
            	max = winB;
            	result = diceB;
            }
        }
        
        return result;
    }

}
