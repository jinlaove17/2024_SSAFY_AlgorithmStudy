/**
 * 양과 늑대
 * https://school.programmers.co.kr/learn/courses/30/lessons/92343
 *
 * @author minchae
 * @date 2025. 1. 6.
 *
 * 문제 풀이
 *  - dfs 이용
 *  - 양의 개수와 늑대 개수 세다가 늑대 개수가 많아지면 바로 return
 *
 * 실행 시간
 * 1.53 ms
 **/

public class SheepAndWolf {

    static boolean[] visited;
    static int answer;

    public static void main(String[] args) {
        int[] info = { 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1 };
        int[][] edges = { { 0, 1 }, { 1, 2 }, { 1, 4 }, { 0, 8 }, { 8, 7 }, { 9, 10 }, { 9, 11 }, { 4, 3 }, { 6, 5 },
                { 4, 6 }, { 8, 9 } };

        System.out.println(solution(info, edges));
    }

    public static int solution(int[] info, int[][] edges) {
        visited = new boolean[info.length];

        dfs(0, 0, 0, info, edges);

        return answer;
    }

    private static void dfs(int idx, int sheep, int wolf, int[] info, int[][] edges) {
        visited[idx] = true;

        if (info[idx] == 0) {
            sheep++;
        } else {
            wolf++;
        }

        if (wolf >= sheep) {
            return;
        }

        answer = Math.max(answer, sheep);

        for (int[] edge : edges) {
            if (visited[edge[0]] && !visited[edge[1]]) {
                dfs(edge[1], sheep, wolf, info, edges);
                visited[edge[1]] = false;
            }
        }
    }

}
