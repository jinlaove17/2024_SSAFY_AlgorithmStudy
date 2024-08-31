import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
 
public class Solution {
    static final int MOD = 20_171_109;
 
    static int n, a;
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());
 
        for (int tc = 1; tc <= t; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
 
            n = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
 
            long answer = 0;
            int mid = a;
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
 
            for (int i = 0; i < n; ++i) {
                st = new StringTokenizer(br.readLine().trim());
 
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
 
                if ((x > mid) && (y > mid)) {
                    minHeap.add(x);
                    minHeap.add(y);
 
                    if (minHeap.size() > maxHeap.size()) {
                        maxHeap.add(mid);
                        mid = minHeap.poll();
                    }
                } else if ((x < mid) && (y < mid)) {
                    maxHeap.add(x);
                    maxHeap.add(y);
 
                    if (maxHeap.size() > minHeap.size()) {
                        minHeap.add(mid);
                        mid = maxHeap.poll();
                    }
                } else if ((x >= mid) && (y <= mid)) {
                    maxHeap.add(y);
                    minHeap.add(x);
                } else if ((x <= mid) && (y >= mid)) {
                    maxHeap.add(x);
                    minHeap.add(y);
                }
 
                answer = (answer + mid) % MOD;
            }
 
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
 
        System.out.println(sb);
    }
}

// 중복을 허용하는 이진 탐색 트리(TreeSet)를 이용한 풀이(시간 초과)
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.util.Iterator;
// import java.util.StringTokenizer;
// import java.util.TreeSet;

// public class Solution {
// 	static final int MOD = 20_171_109;

// 	static int n, a;

// 	public static void main(String[] args) throws Exception {
// 		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
// 		StringBuilder sb = new StringBuilder();
// 		int t = Integer.parseInt(br.readLine().trim());

// 		for (int tc = 1; tc <= t; ++tc) {
// 			StringTokenizer st = new StringTokenizer(br.readLine().trim());

// 			n = Integer.parseInt(st.nextToken());
// 			a = Integer.parseInt(st.nextToken());

// 			long answer = 0;
// 			TreeSet<Integer> ts = new TreeSet<Integer>((Integer a, Integer b) -> {
// 				return (a > b) ? 1 : -1; // TreeSet에 중복을 허용하기(STL - multiset)
// 			});

// 			ts.add(a);

// 			for (int i = 0; i < n; ++i) {
// 				st = new StringTokenizer(br.readLine().trim());

// 				int x = Integer.parseInt(st.nextToken());
// 				int y = Integer.parseInt(st.nextToken());

// 				ts.add(x);
// 				ts.add(y);
// 				answer = (answer + get(ts, ts.size() / 2)) % MOD;
// 			}

// 			sb.append("#").append(tc).append(" ").append(answer).append("\n");
// 		}

// 		System.out.println(sb);
// 	}

// 	private static int get(TreeSet<Integer> ts, int index) {
// 		Iterator<Integer> iter = ts.iterator();

// 		for (int j = 0; j < index; ++j) {
// 			iter.next();
// 		}

// 		return iter.next();
// 	}
// }
