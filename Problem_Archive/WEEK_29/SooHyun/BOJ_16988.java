/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 해당 문제를 풀 때 사용한 알고리즘은 가능한 가짓수를 모두 수행해보는 브루트 포스입니다.
    - 바둑판의 최대 크기가 20*20이므로 최대 가능한 가짓수는 400C2 = 79800가지로 충분히 브루트 포스로 해결할 수 있습니다.
    - 이를 위해 놓을 수 있는 위치 즉 값이 0인 것들의 좌표를 list에 저장합니다.
    - 이후 이중 반복문을 수행하면서 2가지를 선택하는 경우의 수에 모두 backtrack 함수를 수행합니다.
    - 선택한 2개의 좌표는 바둑알을 놓은 것이므로 1로 할당하고 다시 모든 바둑판을 돌면서 2인 경우에 find 함수를 수행합니다.
    - 상대의 돌을 먹는 경우는 만들어지는 군집에 대해 4방 탐색을 수행할 때 0이 존재하면 안됩니다. 0이 존재한다는 것은 모두 둘러싸져있다는 것이 아니므로 먹을 수 없습니다.
    - 만약 0이 존재하면 flag를 false로 바꾸고 마지막에 count를 반환할 때 flag flase인 경우 0을 반환, true인 경우 count를 반환합니다.
    - 모든 군집에 대해 find 함수를 수행하고 최대값을 갱신하면서 정답을 도출할 수 있씁니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N*M)
    - 브루트 포스 : O((N*M)C2)
    - backtrack 함수 : O(N*M)
    - find 함수 : O(N*M)
    - 전체 시간복잡도: O((N*M)C2 * N*M * N*M) => O(N^3 * M^3)

실행 시간
   - 444ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[][] board;
    static List<int[]> list;
    static int answer=0;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        list = new ArrayList<>();

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                if(board[i][j]==0) {
                    list.add(new int[] {i,j});
                }
            }
        }
        for(int i=0; i<list.size()-1; i++) {
            for(int j=i+1; j<list.size(); j++) {
                int[] arr1 = list.get(i);
                int[] arr2 = list.get(j);
                backtrack(arr1[0],arr1[1],arr2[0],arr2[1]);
            }
        }
        System.out.println(answer);
    }
    public static void backtrack(int x1, int y1, int x2, int y2) {
        board[x1][y1]=1;
        board[x2][y2]=1;
        boolean[][] visit = new boolean[N][M];
        int temp=0;
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(board[i][j]==2 && !visit[i][j]) {
                    temp+=find(i,j,visit);
                }
            }
        }
        answer = Math.max(answer, temp);
        board[x1][y1]=0;
        board[x2][y2]=0;
    }
    public static int find(int x, int y,boolean[][] visit) {
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {x,y});
        int count=0;
        visit[x][y]=true;
        boolean flag=true;
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int current_x = arr[0];
            int current_y = arr[1];
            count++;
            for(int i=0; i<4; i++) {
                int nx = current_x+dx[i];
                int ny = current_y+dy[i];
                if(!range(nx,ny))
                    continue;
                if(board[nx][ny]==0)
                    flag=false;
                else if(board[nx][ny]==2 && !visit[nx][ny]) {
                    que.add(new int[] {nx,ny});
                    visit[nx][ny]=true;
                }
            }
        }
        if(!flag)
            return 0;
        return count;
    }

    public static boolean range(int x, int y) {
        if(x <0 || x >= N || y<0 || y>=M)
            return false;
        return true;
    }

}
