/**
 * 1327 소트 게임
 * https://www.acmicpc.net/problem/1327
 *
 * @author minchae
 * @date 2024. 11. 11.
 *
 * 문제 풀이
 *  - 모든 경우를 다 보면 됨
 *  - 초기 순열과 정답 순열 만들어놓음
 *  - 클래스를 하나 만들어 순열과 그 순열을 만들 때 선택하는 수의 개수를 저장
 *  - bfs 탐색 시작
 *      for문을 N번 돌면서 K만큼 뒤집을 수 있는지 확인
 *      뒤집을 수 있다면 시작점을 i로 하고 (K + i) 숫자를 뒤집음
 *      만들어진 문자열이 Set에 포함되어 있지 않다면 큐에 삽입하고 Set에도 추가
 *  - 큐에서 꺼냈을 때 오름차순으로 정렬된 경우 cnt 반환
 *
 * 시간 복잡도
 * 문자열 뒤집기 O((N - K) * K)
 * 최악의 경우 N!만큼 확인 -> O(N! * (N - K) * K)
 *
 * 실행 시간
 * 716 ms
 * */

import java.util.*;
import java.io.*;

public class BOJ_1327 {

    static class Node {
        String data;
        int cnt;

        public Node(String data, int cnt) {
            this.data = data;
            this.cnt = cnt;
        }
    }

    static int N, K;
    static char[] nums;
    static String sortNum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        nums = new char[N];

        nums = br.readLine().replace(" ", "").toCharArray();

        String numStr = new String(nums);

        Arrays.sort(nums);
        sortNum = new String(nums);

        System.out.println(bfs(numStr));
    }

    private static int bfs(String origin) {
        Queue<Node> q = new LinkedList<>();
        HashSet<String> set = new HashSet<>(); // 중복 확인

        q.add(new Node(origin, 0));
        set.add(origin);

        while (!q.isEmpty()) {
            Node cur = q.poll();

            if (cur.data.equals(sortNum)) {
                return cur.cnt;
            }

            // 문자열 뒤집기
            for (int i = 0; i < N; i++) {
                // K개 연속으로 뒤집을 수 없는 경우
                if (K + i > N) {
                    break;
                }

                String start = cur.data.substring(0, i);
                String mid = cur.data.substring(i, K + i);
                String end = cur.data.substring(K + i);

                String reverse = new StringBuilder(mid).reverse().toString();

                String result = start + reverse + end;

                // 이전에 나왔던 순열이 아닌 경우에만 큐에 삽입
                if (!set.contains(result)) {
                    q.add(new Node(result, cur.cnt + 1));
                    set.add(result);
                }
            }
        }

        return -1;
    }

}
