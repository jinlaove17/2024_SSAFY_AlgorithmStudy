/**
 * 22254 공정 컨설턴트 호석
 * https://www.acmicpc.net/problem/22254
 *
 * @author minchae
 * @date 2025. 3. 5.
 *
 * 문제 풀이
 * - 고냥이 풀고 와서 이거 보는데 또 내가 싫어하는 유형의 느낌이 난다.. 이분 탐색..
 * - 이런 유형은 언제 적응 될까ㅠ 너무 어렵다.
 * - 근데 또 그냥 이분 탐색이 아닌가? 라인 별로 작업을 할 수 있으니까.. 으악 이거 뭘까
 * - 아무리 생각해도 그냥 이분 탐색을 무작정 적용하는 건 아닌 것 같다. 뭐가 추가되어야 하는 게 맞는 거 같다...
 * 
 * - 일단 출력하는 게 공정라인 개수니까 이걸 탐색해야 함 -> start, end 두고 mid가 공정라인 개수가 되면 되는 걸까..?
 * - 그리고 mid로 해당 시간 안에 작업을 마무리 할 수 있는지 확인하면 되나?
 * - 만약 mid개수의 공정라인으로 작업한 시간이 X보다 크다면 (start = mid + 1), 아니면 (end = mid - 1) => 범위 좁히기
 * - 그렇다면 answer은 (mid - 1)로 갱신하면 된다.
 * 
 * - 그럼 이걸 어떻게 확인해야 할까.. 큐로 확인하면 되나?
 * - 선물이 차례대로 공정라인에 들어가는데 가장 빨리 끝나는 공정라인에 다음 작업을 투입하면 됨 -> 여기서 우선순위 큐를 활용
 * - 큐에는 선물 작업 시간이 내림차순으로 정렬되면 된다. 근데 이 선물 시간이 입력받은 선물 시간 그대로 들어간다면 답이 이상하게 나온다.
 * - 어떤 값이 들어가야 하냐면 누적된 작업 시간이 들어가야 됨 -> 그래야 어떤 라인이 일찍 끝나는지 알 수 있다.
 * - 일단 mid(공정라인)만큼 0을 추가 -> 그러고 선물 순회 -> 큐 poll 한 후에 작업 시간 누적 후 다시 큐에 삽입
 * 
 * - 다행히 내가 생각한 과정이 맞았다. 이분 탐색 문제 한 번에 푸니까 뿌듯하당..ㅎㅎ
 * 
 * 시간 복잡도
 * 이분 탐색 : O(logN)
 * 작업 시간 계산 : O(N * logN)
 * 총 시간 복잡도 : O(N * log^2N)
 *
 * 실행 시간
 * 936 ms
 **/

import java.io.*;
import java.util.*;

public class Main {

	static int N, X;
	static int[] present;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		present = new int[N];
		
		st = new StringTokenizer(br.readLine());
		
		for (int i = 0; i < N; i++) {
			present[i] = Integer.parseInt(st.nextToken());
		}
		
		int answer = 0;
		
		int start = 1; // 공정라인은 1개부터 시작
		int end = N; // 선물이 N개니까 최대 공정라인 개수는 N
		
		while (start <= end) {
			int mid = (start + end) / 2;
			
			// 범위 줄이기
			if (calculate(mid) > X) {
				start = mid + 1;
			} else {
				answer = mid; // 정답 갱신
				end = mid - 1;
			}
		}
		
		System.out.println(answer);
	}
	
	// 작업 시간 계산
	private static int calculate(int mid) {
		int result = 0;
		
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		
		while (mid-- > 0) {
			pq.add(0);
		}
		
		for (int time : present) {
			int cur = pq.poll() + time; // 시간 누적

			pq.add(cur); // 다시 큐에 삽입
			
			result = Math.max(result, cur); // 작업 시간 갱신 -> 먼저 투입됐는데 늦게 끝날 수 있기 때문에 최댓값 비교하는 것
		}
		
		return result;
	}

}
