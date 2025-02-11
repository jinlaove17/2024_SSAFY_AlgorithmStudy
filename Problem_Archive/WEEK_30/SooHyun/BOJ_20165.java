/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제의 핵심은 시키는데로 하는거 + 어느 경우에 연쇄적으로 더 넘어질 수 있는지 판단하는 것이였습니다.
    - 알파벳에 따라 넘어지는 방향이 정해져 있기 떄문에 Map을 이용해 각 알파벳에 따른 방향을 저장합니다.
    - 초기화 이후 주어진 좌표와 방향에 따라서 넘어질 수 있는 경우를 판단하고 bfs함수를 실행합니다.
    - 도미노의 크기가 있으며 한 칸씩 옆으로 갈 수록 처음 높이를 1개씩 감소를 합니다. 이때 해당 위치에서 갱신되는 높이의 값보다 해당 위치의 도미노의 높이가 더 높아지면 추후에 더 넘어질 수도 있으므로 해당 값을 갱신해줍니다.
    - 이렇게 점차 줄어드는 높이가 0이 될 때까지 반복하면서 도미노를 넘어뜨립니다.
    - 넘어뜨린 이후 특정 위치의 도미노를 세웁니다.
    - 마지막에 이차원 반복문을 돌면서 StringBuilder에 값을 저장하고 출력하면서 정답을 도출합니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N*M)
    - bfs 함수 : 최악의 경우 끝까지 탐색을 진행하므로 O(Math.max(N,M))
    - 전체 시간복잡도: O(N*M)

실행 시간
   - 236ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M, R;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    static int[][] board;
    static boolean[][] check;
    static int answer=0;
    static Map<String, Integer> map;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        check = new boolean[N][M];
        init();
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            Arrays.fill(check[i], true);
            for(int j=0; j<M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<R; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            String d = st.nextToken();
            if(check[x][y])
                bfs(x,y,d);
            st = new StringTokenizer(br.readLine().trim());
            x = Integer.parseInt(st.nextToken())-1;
            y = Integer.parseInt(st.nextToken())-1;
            check[x][y]=true;
        }
        sb.append(answer+"\n");
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(check[i][j])
                    sb.append("S ");
                else
                    sb.append("F ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void bfs(int x, int y, String d) {
        int dir = map.get(d);
        int power = board[x][y];
        int nx=x;
        int ny=y;
        while(power >0) {
            if(check[nx][ny]) {
                check[nx][ny]=false;
                answer++;
                power = Math.max(power, board[nx][ny]);
            }
            power--;
            nx+=dx[dir];
            ny+=dy[dir];
            if(!range(nx,ny))
                break;
        }
    }

    public static void init() {
        map = new HashMap<>();
        map.put("N", 0);
        map.put("W", 1);
        map.put("S", 2);
        map.put("E", 3);
    }

    public static boolean range(int x, int y) {
        if(x<0 || x>=N || y<0 | y>=M)
            return false;
        return true;
    }
}
