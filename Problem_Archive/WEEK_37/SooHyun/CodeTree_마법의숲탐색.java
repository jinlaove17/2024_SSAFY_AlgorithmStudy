/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 삼성 문제의 특징 중 하나죠 저번에 이어서 이번에도 빡구현 ㅎㅎ 빵긋 까르르륽
    - 문제의 분류는 시뮬레이션입니다. 그런데 이동을 하고 또 다른 조건에 의해서 이동할 수 있는 것을 고려해야합니다.
    - Info라는 객체를 선언하고 각각의 값들은 몇번째 골렘인지,x,y, 방향을 저장합니다.
    - 각 골렘은 3가지 방향으로 이동할 수 있고 이동하는 조건도 아래 -> 왼쪽 -> 오른쪽 순서로 이루어집니다.
    - 그리고 이동이 완료된 이후에 인접해 있는 골렘으로 올라탈 수 있다는 조건이 있어 이를 해결하기위해 parent 배열을 이용해 유파를 사용했습니다.
    - 이동하는 조건에 대해서는 타이핑으로 힘드니 아침에 김수현의 그림 교실을 보여드리겠습니다.
    - 이동이 완료된 이후에 다른 골렘으로 올라탈 수 있는를 고려해야합니다. 그런데 다른 콜렘이 최대로 내려갈 수 있는 값이 현재 골레보다 작으면 굳이 갈 필요가 없겠죠. 더 큰경우에만 올라 타고 max_value의 현재 idx 값을 갱신해줍니다. 여기서 그치지 않고 parent 배열 또한 find함수를 통해 갱신해주면 다른 것이 타고 타고 타고 갈 때 일일이 다 볼 필요 업이 한 번에 조회할 수 있게 합니다.
    - 이제 result에 특정 idx의 max_value를 누적해줍니다.
    - 여기서 끝냈더니 틀렸어요 그래서 다른 거를 생각해봤어요
    - 생각해보니 특정 idx에 의해 영향을 받는 것이 있을 거 같았어요 만약 특정 idx의 max_value 보다 다른 idx이면 그 idx도 갮이 갱신될 수 있었어요
    - 그래서 Queue를 사용해 BFS 돌렸어요 이거는 제가 아침에 설명해드리겠습니다.
    - 이렇게 하면 최종적을 특정 idx가 영향 받는 다른 idx, 특정 idx에 의해 영향받는 다른 idx를 모두 갱신하고 정답을 도출할 수 있습니다.

시간 복잡도
    - 입력 처리 : O(K)
    - stimultation 함수
        1. 골렘 이동 : O(R)
        2. 인접 골렘 확인 : O(1)
        3. BFS로 영향 전파 : O(K)
    - 전체 시간복잡도 : O(K + K * R)

실행 시간
   - 150ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int R, C, K;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};

    static int[] md_x = {2,1,1};
    static int[] md_y = {0,-1,1};

    static int[] ml_x = {0,-1,1,1,2};
    static int[] ml_y = {-2,-1,-1,-2,-1};

    static int[] mr_x = {0,-1,1,1,2};
    static int[] mr_y = {2,1,1,2,1};

    static int[] e_x = {-2,-1,0,1,2,1,0,-1};
    static int[] e_y = {0,-1,-2,-1,0,1,2,1};

    static int[] max_value;
    static int[][] board;
    static int[][] exit;
    static int[] parent;
    static Info[] infos;
    static int result;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        max_value = new int[K+1];
        parent = new int[K+1];
        infos = new Info[K+1];
        reset();
        for(int i=0; i<=K; i++) {
            parent[i] = i;
        }
        result = 0;
        for(int i=1; i<=K; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int col = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken());
            infos[i] = new Info(i, 1, col,dir);
            stimultation(infos[i]);
        }
        System.out.println(result);
    }

    public static void stimultation(Info info) {
        while(true) {
            if(canMove(info, md_x, md_y)) {
                moveDown(info);
                continue;
            }
            if(canMove(info, ml_x, ml_y)) {
                moveLeft(info);
                continue;
            }
            if(canMove(info, mr_x, mr_y)) {
                moveRight(info);
                continue;
            }
            break;
        }
        if(is_Out(info)) {
            reset();
            return;
        }
        exit[info.x + dx[info.dir]][info.y + dy[info.dir]] = info.idx;
        board[info.x][info.y] = info.idx;
        for(int i=0; i<4; i++) {
            int nx = info.x+dx[i];
            int ny = info.y+dy[i];
            board[nx][ny]=info.idx;
        }

        max_value[info.idx]=info.x-1;

        int start_x = info.x+dx[info.dir];
        int start_y = info.y+dy[info.dir];
        for(int i=0; i<4; i++) {
            int nx = start_x+dx[i];
            int ny = start_y+dy[i];
            if(range(nx, ny) && board[nx][ny] !=0) {
                if(board[nx][ny] == info.idx)
                    continue;
                if(max_value[board[nx][ny]] > max_value[info.idx]) {
                    max_value[info.idx] = max_value[board[nx][ny]];
                    parent[info.idx] = find(parent[board[nx][ny]]);
                }
            }
        }
        result+=max_value[info.idx];

        boolean[] visit = new boolean[K+1];
        Queue<Integer> que = new LinkedList<>();
        visit[info.idx] = true;
        que.add(info.idx);
        while(!que.isEmpty()) {
            int current = que.poll();
            for(int i=0; i<8; i++) {
                int nx = infos[current].x + e_x[i];
                int ny = infos[current].y + e_y[i];
                if(range(nx, ny) && exit[nx][ny] !=0 && !visit[exit[nx][ny]]) {
                    int next = exit[nx][ny];
                    visit[next]=true;
                    que.add(next);
                    if(max_value[info.idx] > max_value[next]) {
                        parent[next] = find(parent[info.idx]);
                        max_value[next] = max_value[info.idx];
                    }
                }
            }

        }
    }

    public static int find(int n) {
        if(n == parent[n])
            return n;
        return parent[n] = find(parent[n]);
    }

    public static boolean canMove(Info info, int[] mx, int[] my) {
        for(int i=0; i<mx.length; i++) {
            int nx = info.x + mx[i];
            int ny = info.y + my[i];
            if(!range(nx, ny) || board[nx][ny] !=0)
                return false;
        }
        return true;
    }

    public static void moveDown(Info info) {
        info.x = info.x+1;
    }

    public static void moveLeft(Info info) {
        info.x = info.x+1;
        info.y = info.y-1;
        info.dir = (info.dir + 3) %4;
    }

    public static void moveRight(Info info) {
        info.x = info.x+1;
        info.y = info.y+1;
        info.dir = (info.dir + 1) %4;
    }

    public static void reset() {
        board = new int[R+3][C];
        exit = new int[R+3][C];
    }
    public static boolean is_Out(Info info) {
        for(int i=0; i<4; i++) {
            int nx = info.x+dx[i];
            int ny = info.y+dy[i];
            if(!rangeForest(nx, ny)) {
                return true;
            }
        }
        return false;
    }
    public static boolean range(int x, int y) {
        if(x >=0 && x <R+3 && y >=0 && y < C)
            return true;
        return false;
    }

    public static boolean rangeForest(int x, int y) {
        if(x >=3 && x <R+3 && y >=0 && y < C)
            return true;
        return false;
    }

    static class Info{
        int idx;
        int x;
        int y;
        int dir;

        public Info(int idx, int x, int y, int dir) {
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

}
