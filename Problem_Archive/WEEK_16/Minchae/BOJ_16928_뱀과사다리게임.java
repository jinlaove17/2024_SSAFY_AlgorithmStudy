/**
 * 16928 뱀과 사다리 게임
 * https://www.acmicpc.net/problem/16928
 *
 * @author minchae
 * @date 2024. 11. 5.
 *
 * 문제 풀이
 *  - bfs를 사용해 주사위를 굴려보면서 도착지에 몇 턴만에 도달할 수 있는지 확인
 *  - 큐가 빌 때까지 진행
 *      큐의 사이즈만큼 진행하면서 주사위를 굴려봄
 *      한 턴에 주사위를 1부터 6까지 굴려보기 때문에 큐의 사이즈만큼 진행하지 않으면 올바른 답을 구할 수 없음
 *      굴리다가 도착지에 도달한 경우 바로 턴 수를 반환
 *
 * 시간 복잡도
 * O(V + E) : V는 정점 수(100), E는 각 정점의 간선 수(주사위 굴리는 수 6)
 *
 * 실행 시간
 * 112 ms
 * */

import java.util.*;
import java.io.*;

public class BOJ_16928 {

    static int N, M;

    static HashMap<Integer, Integer> moveMap; // 사다리, 뱀 이동 정보
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        moveMap = new HashMap<>();
        visited = new boolean[101];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            moveMap.put(x, y);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            moveMap.put(u, v);
        }

        System.out.println(bfs());
    }

    private static int bfs() {
        Queue<Integer> q = new LinkedList<>();

        q.add(1);
        visited[1] = true;

        int result = 0; // 주사위 굴리는 횟수

        while (!q.isEmpty()) {
            result++;
            
            int size = q.size();

            // 한 턴에 주사위를 1부터 6까지 굴려보기 때문에 큐의 사이즈만큼 확인하면서 도착점에 도달하는지 확인
            for (int i = 0; i < size; i++) {
                int cur = q.poll();

                // 주사위 굴려봄
                for (int num = 1; num <= 6; num++) {
                    int next = cur + num;

                    // 도착 지점인 경우
                    if (next == 100) {
                        return result;
                    }

                    // 100이 넘어가거나 이미 방문한 경우 넘어감
                    if (next > 100 || visited[next]) {
                        continue;
                    }

                    visited[next] = true;

                    // 사다리나 뱀인 경우 이동
                    // 해당 지점에서 다른 사다리나 뱀이 나올 수 있는데
                    // 방문 처리를 하는 경우 거쳐갈 수 없기 때문에 방문 처리는 따로 해주지 않음
                    if (moveMap.containsKey(next)) {
                        next = moveMap.get(next);
                    }

                    q.add(next);
                }
            }

        }

        return result;
    }

}
