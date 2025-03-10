/**
 * 21276 계보 복원가 호석
 * https://www.acmicpc.net/problem/21276
 *
 * @author minchae
 * @date 2025. 3. 10.
 *
 * 문제 풀이
 * - 단방향 그래프 -> 자식은 조상이 있어야 함 -> 그래프로 생각하면 자식노드마다 진입 차수가 있는 것 (진입차수가 없으면 조상이다.)
 * - 그럼 노드들의 순서를 알기 위해서는 위상 정렬 사용하면 됨
 * - 각 노드마다 진입차수 계산 -> 위상정렬 사용해서 순서 알아내기
 * - 그리고 각 부모마다 자식들을 출력해야 함 -> 먼저 부모를 저장할 우선순위큐 만들기
 * - 자식은 우선순위큐배열을 만들면 됨
 * 
 * 시간 복잡도
 * 입력 : O(N + M)
 * 이름 정렬 : O(NlogN)
 * 출력 : O(MlogN)
 * 총 시간 복잡도 : O(N + MlogN)
 *
 * 실행 시간
 * 692 ms
 **/

import java.io.*;
import java.util.*;

public class Main {

	static int N, M;
	
	static HashMap<String, Integer> idxMap; // 이름으로 인덱스 찾기 (그래프와 큐에 인덱스로 저장하기 때문)
	static String[] names;
	
	static ArrayList<Integer>[] list;
	static int[] indegree;
	
	static PriorityQueue<String> parent;
	static PriorityQueue<String>[] child;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		idxMap = new HashMap<>();
		names = new String[N];
		
		list = new ArrayList[N];
		indegree = new int[N];
		
		parent = new PriorityQueue<>();
		child = new PriorityQueue[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for (int i = 0; i < N; i++) {
			list[i] = new ArrayList<>();
			child[i] = new PriorityQueue<>();
			
			String name = st.nextToken();
			
			idxMap.put(name, i);
			names[i] = name;
		}
		
		M = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			
			String X = st.nextToken(); // 자식
			String Y = st.nextToken(); // 조상
			
			list[idxMap.get(Y)].add(idxMap.get(X));
			indegree[idxMap.get(X)]++;
		}
		
		topology();
		
		// 가문의 시조 출력
		System.out.println(parent.size());
		
		while (!parent.isEmpty()) {
			System.out.print(parent.poll() + " ");
		}
		
		System.out.println();
		
		Arrays.sort(names); // 이름 사전순으로 정렬
		
		// 자식 출력
		for (int i = 0; i < N; i++) {
			System.out.print(names[i] + " "); // 부모 출력
			
			int idx = idxMap.get(names[i]);
			
			System.out.print(child[idx].size() + " ");
			
			// 자식 출력
			while (!child[idx].isEmpty()) {
				System.out.print(child[idx].poll() + " ");
			}
			System.out.println();
		}
		
	}
	
	// 위상 정렬
	private static void topology() {
		Queue<Integer> q = new LinkedList<>();
		
		for (int i = 0; i < N; i++) {
			if (indegree[i] == 0) {
				q.add(i);
				parent.add(names[i]); // 맨 처음에 진입차수가 0인 것은 가문의 시조
			}
		}
		
		while (!q.isEmpty()) {
			int cur = q.poll();
			
			for (int next : list[cur]) {
				if (--indegree[next] == 0) {
					q.add(next);
					child[cur].add(names[next]); // 자식 저장
				}
			}
		}
	}

}
