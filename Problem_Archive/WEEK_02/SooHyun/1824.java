import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//이 문제 같은 경우는 단순 구현이기에 요구사항에 따라 코드를 작성했으나 런타임 에러가 발생했습니다.
// 원인을 찾을 수 없어 솔직히 다른 사람의 풀이와 gpt를 참고하였습니다.
// 결과적으로 전역변수가 원인이였습니ㅏ. 초기 구현 시 함수인자 값으로 메모리에 내재된 값을 전달하지 않고 전역변수로 그 값을 관리했습니다.
// 그러나 제귀호출이 깊어질수록 전역변수 호출이 증가하여 제한된 메모리의 양을 넘어서고 올바르게 관리되지 않아 런타임 에러를 일으키는 것을 알게 되었습니다.
// 이를 통해 재귀호출 과정에서 전역변수 사용 시 조심스럽게 사용해야될 것 같습니다.
// => 지역변수로 관리 시 재귀 호출마다 독립된 메로리 상태를 유지해 호출의 깊어져도 정확히 관리되며 예상치 못한 동작을 피할 수 있습니다.
class Solution1824 {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static boolean check = false;
    static boolean[][][][] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int t = 1; t <= T; t++) {
            check = false;
            st = new StringTokenizer(br.readLine().trim());
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            char[][] map = new char[R][C];
            boolean flag = false;
            for (int r = 0; r < R; r++) {
                map[r] = br.readLine().trim().toCharArray();
                for (int c = 0; c < C; c++) {
                    if (map[r][c] == '@') {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                sb.append("#").append(t).append(" NO\n");
                continue;
            }
            visited = new boolean[R][C][4][16];
            move(0, 0, 1, 0,R, C, map);
            if (check) {
                sb.append("#").append(t).append(" YES\n");
            } else {
                sb.append("#").append(t).append(" NO\n");
            }
        }
        System.out.print(sb.toString());
    }

    public static void move(int x, int y, int dir, int mem, int R, int C, char[][] map) {
        if (check) return;

        if (map[x][y] == '@') {
            check = true;
            return;
        }

        if (visited[x][y][dir][mem]) return;
        visited[x][y][dir][mem] = true;

        int nd = dir;
        int nm = mem;

        if (map[x][y] == '<') {
            nd = 3;
        } else if (map[x][y] == '>') {
            nd = 1;
        } else if (map[x][y] == '^') {
            nd = 0;
        } else if (map[x][y] == 'v') {
            nd = 2;
        } else if (map[x][y] == '_') {
            nd = (mem == 0 ? 1 : 3);
        } else if (map[x][y] == '|') {
            nd = (mem == 0 ? 2 : 0);
        } else if (map[x][y] == '+') {
            nm = (mem + 1) % 16;
        } else if (map[x][y] == '-') {
            nm = (mem + 15) % 16;
        } else if (map[x][y] >= '0' && map[x][y] <= '9') {
            nm = map[x][y] - '0';
        }

        int nx, ny;
        if (map[x][y] == '?') {
            for (int i = 0; i < 4; i++) {
                nx = (x + dx[i] + R) % R;
                ny = (y + dy[i] + C) % C;
                move(nx, ny, i, nm, R, C, map);
            }
        } else {
            nx = (x + dx[nd] + R) % R;
            ny = (y + dy[nd] + C) % C;
            move(nx, ny, nd, nm, R, C, map);
        }
    }
}
