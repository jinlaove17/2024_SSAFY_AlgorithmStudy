/**
 * 4195 친구 네트워크
 * https://www.acmicpc.net/problem/4195
 *
 * @author minchae
 * @date 2025. 2. 3.
 *
 * 문제 풀이
 * - 친구 네트워크 -> 친구 관계만으로 이동 가능 -> union-find 떠오름
 * - 근데 친구 이름이 String으로 주어지는데 이걸 인덱스로 어떻게 표현할지 고민
 * - 해시맵으로 인덱스를 저장하면 좋을 것 같다고 생각 HashMap<이름, 인덱스>로 저장
 * - 부모 배열말고 친구 수 저장하는 배열을 하나 더 만들기
 *   더 작은 숫자를 부모로 하고 친구 수를 옮기기
 *
 * 시간 복잡도
 * O(F * α(N))
 *
 * 실행 시간
 * 1316 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static HashMap<String, Integer> friend;
	
	static int[] parent;
	static int[] cnt;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		while (T-- > 0) {
			int F = Integer.parseInt(br.readLine());
			
			// 인덱스가 계속 늘어나니까 2배로 설정 -> F개 주어지는데 친구가 2명씩 주어지기 때문
			parent = new int[F * 2];
			cnt = new int[F * 2];
			friend = new HashMap<>();
			
			for (int i = 0; i < F * 2; i++) {
				parent[i] = i;
				cnt[i] = 1;
			}
			
			int idx = 0;
			
			for (int i = 0; i < F; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				String a = st.nextToken();
				String b = st.nextToken();
				
				// key가 없을 때만 맵에 넣기
				friend.putIfAbsent(a, idx++);
				friend.putIfAbsent(b, idx++);
				
				int cnt = union(friend.get(a), friend.get(b));
				System.out.println(cnt);
			}
		}
	}
	
	private static int find(int x) {
		if (x == parent[x]) {
			return x;
		}
		
		return parent[x] = find(parent[x]);
	}
	
	private static int union(int x, int y) {
		int rootX = find(x);
		int rootY = find(y);
		
		// 두 개의 부모가 같은 경우 바로 친구 수 반환
		if (rootX == rootY) {
			return cnt[rootX];
		}
		
		if (rootX < rootY) {
			parent[rootY] = rootX;
			return cnt[rootX] += cnt[rootY]; // 더 작은 쪽에 친구 수 합치기
		}
		
		parent[rootX] = rootY;
		return cnt[rootY] += cnt[rootX];
	}

}
