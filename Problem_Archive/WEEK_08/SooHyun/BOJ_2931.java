import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static char[][] board;
    static boolean[][] visit;
    static int R, C;
    static int result_X, result_Y;// 결과 좌표
    static int temp_X, temp_Y;// 임시 좌표 저장
    static char resultType;// 결과 파이프 타입
    static boolean flag = false;// 결과를 찾았는지 여부
    static char[] pipes = {'|', '-', '+', '1', '2', '3', '4'};// 가능한 파이프 타입
    static int count = 0;// 연결된 파이프의 수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        board = new char[R][C];
        visit = new boolean[R][C];
        int start_X = 0;
        int start_Y = 0;
        for (int i = 0; i < R; i++) {
            char[] arr = br.readLine().trim().toCharArray();
            for (int j = 0; j < C; j++) {
                board[i][j] = arr[j];
                if (board[i][j] == 'M') {// 시작점 M의 좌표
                    start_X = i;
                    start_Y = j;
                }
                //파이프가 있는 칸의 수를 카운트 ('.', 'M', 'Z' 제외)
                if (board[i][j] != '.' && board[i][j] != 'M' && board[i][j] != 'Z') {
                    count++;
                }
            }
        }
        count++; //Z 포함한 목표로 하는 파이프의 총 개수
        //4방향으로 DFS 탐색
        for (int i = 0; i < 4; i++) {
            int nx = start_X + dx[i];
            int ny = start_Y + dy[i];
            if(nx<0 || nx>=R | ny<0 ||ny>=C)
                continue;
            dfs(nx, ny, i, 0, true);
            if(flag) // 결과를 찾은 경우 루프 탈출
                break;
        }

        System.out.println((result_X + 1) + " " + (result_Y + 1) + " " + resultType);
    }

    public static void dfs(int x, int y, int direction, int cnt, boolean use) {
        if (flag) // 이미 결과를 찾았으면 종료
            return;

        // 모든 파이프를 연결한 경우 결과를 저장하고 종료
        if (cnt == count) {
            result_X=temp_X;
            result_Y=temp_Y;
            resultType=board[result_X][result_Y];
            flag = true;
            return;
        }

        // 현재 방향과 파이프 모양에 따른 다음 방향 결정
        int next_dir = change(direction,board[x][y]);
        if(next_dir==-1) // 연결이 불가능한 경우
            return;
        int nx = x+dx[next_dir];
        int ny = y+dy[next_dir];
        if(nx <0 || nx >=R || ny <0 || ny>=C)
            return;
        if(board[nx][ny] == '.') {
            if(use) { // 첫 번째 빈 칸일 때만 파이프를 삽입해 봄 (왜냐면 완성된 파이프에서 1개만 뺐기에
                for(int i=0; i<7; i++) { // 모든 가능한 파이프를 삽입
                    temp_X=nx;
                    temp_Y=ny;
                    board[nx][ny]=pipes[i];
                    visit[nx][ny]=true;
                    dfs(nx,ny,next_dir,cnt+1,false);// 재귀적으로 DFS 호출
                    board[nx][ny]='.'; // 원복
                    visit[nx][ny]=false;
                }
            }
        }else {
            if(visit[nx][ny]) {// 이미 방문한 곳은 같은 카운트로 탐색
                dfs(nx,ny,next_dir,cnt,use);
            }else {// 방문하지 않은 곳은 카운트 증가 후 탐색
                visit[nx][ny]=true;
                dfs(nx,ny,next_dir,cnt+1,use);
                visit[nx][ny]=false;
            }
        }
    }

    // 현재 파이프와 방향에 따라 다음 방향을 결정하는 메서드
    public static int change(int direction, char pipe) {
        if (pipe == '|') {
            if (direction == 0 || direction == 2) return direction; // 상하로 연결
        } else if (pipe == '-') {
            if (direction == 1 || direction == 3) return direction; // 좌우로 연결
        } else if (pipe == '+') {
            return direction; // 모든 방향으로 연결 가능
        } else if (pipe == '1') {
            if (direction == 3) return 2; // 좌에서 들어오면 하로 나감
            if (direction == 0) return 1; // 상에서 들어오면 우로 나감
        } else if (pipe == '2') {
            if (direction == 2) return 1; // 하에서 들어오면 우로 나감
            if (direction == 3) return 0; // 좌에서 들어오면 상으로 나감
        } else if (pipe == '3') {
            if (direction == 1) return 0; // 우에서 들어오면 상으로 나감
            if (direction == 2) return 3; // 하에서 들어오면 좌로 나감
        } else if (pipe == '4') {
            if (direction == 1) return 2; // 우에서 들어오면 하로 나감
            if (direction == 0) return 3; // 상에서 들어오면 좌로 나감
        }
        return -1; // 연결이 불가능한 경우
    }


}
