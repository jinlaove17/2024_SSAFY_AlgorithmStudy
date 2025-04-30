/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제의 핵심은 bfs와 비트마스킹입니다.
    - 비트마스킹을 사용한 이유는 각 셀에 존재하는 벽이 1, 2, 4, 8로 표현을 했고 그것들의 합이 각 셀의 벽들을 나타냈기 떄문입니다. 이에 저는 해당 값을 4자리의 BinaryString으로 만들었습니다.
    - 이에 dx, dy 또한 서 북 동 남 각각 인덱스 값을 0, 1, 2, 3으로 표현했습니다.
    - 이제 bfs를 사용합니다. 이떄 추후 벽 하나를 제거할 시에 최대 넓이를 편하게 구하게 위해 group 2차원 배열을 선언해 각각의 군집들의 번호를 매기면서 bfs를 진행합니다.
    - bfs를 진행하면서 특정 셀 즉 4자릐 BinaryString으로 변환시킨 값의 i번째에 해당하는 문자가 0이면 벽이 없어서 방문 가능하고 1인 경우 벽이 존재해서 방문 불가능합니다.
    - 이후 벽 하나를 제거한 경우 최대 군집의 넓이를 구하기 위해 이전에 군집의 번호로 매핑한 group 배열을 활용합니다.
    - 모든 셀을 방문하고 그 위치에서 4방탐색을 진행한 결과 자신의 group과 다른 group에 해당하는 경우 즉 벽을 제거했을 때 두개의 군집이 합쳐진 것이므로 이것을 통해 최대 넓이 값을 도출합니다.

시간 복잡도
    - 입력 및 초기화 : O(N * M)
    - bfs : O(N * M)
    - find : O(N * M * 4) = O(N * M)
    - 전체 시간복잡도 : O(N * M)

실행 시간
   - 244ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int M, N;
    static String[][] board;
    static boolean[][] visit;
    static int[][] group;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    static int total_cnt=0;
    static int max1=0;
    static int max2=0;
    static int idx=1;
    static Map<Integer, Integer> map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        board = new String[N][M];
        visit = new boolean[N][M];
        group = new int[N][M];
        map = new HashMap<>();
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<M; j++){
                int n = Integer.parseInt(st.nextToken());
                board[i][j] = String.format("%4s", Integer.toBinaryString(n)).replace(' ', '0');
            }
        }
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(!visit[i][j]){
                    total_cnt++;
                    bfs(i,j);
                }
            }
        }
        find();
        System.out.println(total_cnt);
        System.out.println(max1);
        System.out.println(max2);
    }

    public static void bfs(int i, int j) {
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {i,j});
        group[i][j] = idx;
        visit[i][j] = true;
        int size=1;
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int x = arr[0];
            int y = arr[1];
            for(int d=0; d<4; d++){
                int nx = x+dx[d];
                int ny = y+dy[d];
                if(!range(nx, ny) || visit[nx][ny] || board[nx][ny].charAt(d) == '1')
                    continue;
                visit[nx][ny] = true;
                group[nx][ny] = idx;
                que.add(new int[]{nx, ny});
                size++;
            }
        }
        max1 = Math.max(max1, size);
        map.put(idx, size);
        idx++;
    }

    public static void find() {
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                int current_num = group[i][j];
                for(int d=0; d<4; d++){
                    int nx = i+dx[d];
                    int ny = j+dy[d];
                    if(!range(nx, ny))
                        continue;
                    if(current_num != group[nx][ny]) {
                        max2 = Math.max(max2, map.get(current_num) + map.get(group[nx][ny]));
                    }
                }
            }
        }
    }

    public static boolean range(int x, int y){
        if(x <0 || x>=N || y<0 || y>=M)
            return false;
        return true;
    }
}