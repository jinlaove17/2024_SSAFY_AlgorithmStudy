/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 전 기업 코테에서 이렇게 나오면 바로 노트북 덮습니다. 시뮬레이션 문제인데 제약 조건이 좀 많습니다.
    - 일단 초기화를 해줍니다. 보드의 크기는 5x5이므로 5x5 배열을 만들어줘 초기화 진행, 벽면의 경우 que에 저장해서 관리해줍니다.
    - 이후 K번 만큼 시뮬레이션을 진행합니다. find 함수에서 먼저 회전을 시켜주기 위해 rotate 함수를 수행하면서 90, 180, 270 회전하는 경우를 만들어주고 그 중에서 가장 많이 제거가 되는 것을 {x,y,d} 즉 x좌표, y좌표, 회전의 경우로 반환해줍니다.
    - 이후 list 내의 {x,y} 좌표를 가지고 제거를 진행합니다. 제거를 진행할 때 bfs를 활용합니다. 이후 fill 함수를 통해 빈 공간을 채워줍니다.
    - 이후 채워진 상태에서 추가적인 삭제가 이루어질 수 있으모로 이번에는 회전 없이 bfs를 활용한 제거와 fill을 통한 채워주기를 진행합니다.
    - 이후 추가적인 삭제가 이루어나지 않을 때까지 이를 반복합니다.
    - 마지막에는 scores에 결과를 저장해주고 출력해줍니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(25 + M) = O(M) (M은 보드의 크기)
    - find 함수 : O(3 * 5 * 5 + 25) = O(1) (회전을 3번하고 5x5 배열을 탐색하며 bfs를 수행)
    - remove 함수 : O(25) = O(1) (5x5 배열을 탐색하며 bfs를 수행)
    - fill 함수 : O(25) (N은 보드의 크기, M은 보드의 크기)
    - 추가 삭제 : O(5 * 5) = O(1) (5x5 배열을 탐색하며 bfs를 수행)
    - 전체 시간복잡도 : O(M + K * (1 + 1 + 1 + 1)) = O(K) (K는 회전 횟수)

실행 시간
   - 91ms(java)
*/
import java.util.*;
import java.io.*;
public class Main {
    static int K, M,result;
    static final int SIZE=5;
    static int[][] board;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};
    static Queue<Integer> que;
    static List<Integer> scores;
    static List<int[]> list, temp;
    static boolean[][] visit;
    public static void main(String[] args) throws IOException{
        // Please write your code here.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[SIZE][SIZE];
        for(int i=0; i<SIZE; i++){
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<SIZE; j++){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        que = new LinkedList<>();
        scores = new LinkedList<>();
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<M; i++){
            que.add(Integer.parseInt(st.nextToken()));
        }
        simultaion();
        StringBuilder sb = new StringBuilder();
        for(int score : scores){
            sb.append(score+" ");
        }
        System.out.println(sb.toString().trim());
    }

    public static void simultaion(){
        for(int k=0; k<K; k++){
            result =0;
            list = new LinkedList<>();
            temp = new LinkedList<>();
            int[] arr = find();
            if(list.size()==0){
                return;
            }
            board = rotate(arr[0], arr[1], arr[2]);
            for(int[] pos : list){
                remove(pos[0], pos[1]);
            }
            fill();
            while(true){
                visit = new boolean[SIZE][SIZE];
                int count=0;
                temp.clear();
                for(int i=0; i<SIZE; i++){
                    for(int j=0; j<SIZE; j++){
                        count+=bfs(i,j,board);
                    }
                }
                if(count==0){
                    break;
                }
                visit = new boolean[SIZE][SIZE];
                for(int[] pos : temp){
                    remove(pos[0], pos[1]);
                }
                fill();
                result+=count;
            }
            scores.add(result);
        }
    }

    public static void remove(int x, int y){
        Queue<int[]> q = new LinkedList<>();
        visit = new boolean[SIZE][SIZE];
        int base = board[x][y];
        board[x][y]=0;
        visit[x][y]=true;
        q.add(new int[]{x,y});

        while(!q.isEmpty()){
            int[] pos=q.poll();
            int cur_x = pos[0];
            int cur_y = pos[1];
            for(int i=0; i<4; i++){
                int nx = cur_x+dx[i];
                int ny = cur_y+dy[i];
                if(!range(nx, ny)){
                    continue;
                }
                if(!visit[nx][ny] && board[nx][ny]==base){
                    visit[nx][ny]=true;
                    board[nx][ny]=0;
                    q.add(new int[]{nx,ny});
                }
            }
        }
    }

    public static void fill(){
        for(int j=0; j<SIZE; j++){
            for(int i=SIZE-1; i>=0; i--){
                if(board[i][j]==0){
                    if(!que.isEmpty()){
                        board[i][j] = que.poll();
                    }
                }
            }
        }
        // for(int i=0; i<SIZE; i++){
        //     for(int j=0; j<SIZE; j++){
        //         System.out.print(board[i][j]+" ");
        //     }
        //     System.out.println();
        // }
        // System.out.println();
    }

    public static int[] find(){
        int max=0;
        int x=-1;
        int y=-1;
        int d=-1;
        for(int k=0; k<3; k++){
            for(int j=1; j<SIZE-1; j++){
                for(int i=1; i<SIZE-1; i++){
                    int[][] arr = rotate(i,j,k);
                    visit = new boolean[SIZE][SIZE];
                    temp.clear();
                    int score=0;
                    for(int r=0; r<3; r++){
                        for(int c=0; c<3; c++){
                            if(!visit[r+i-1][c+j-1]){
                                score+=bfs(r+i-1,c+j-1, arr);
                            }
                        }
                    }
                    if(score>max){
                        list.clear();
                        list.addAll(temp);
                        x=i;
                        y=j;
                        d=k;
                        max = score;
                    }
                }
            }
        }
        result+=max;
        return new int[]{x,y,d};
    }

    public static int[][] rotate(int x, int y, int r){
        int[][] copy = new int[3][3];
        int[][] arr = new int[SIZE][SIZE];
        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                arr[i][j] = board[i][j];
            }
        }
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(r==0){
                    copy[i][j] = board[3-j+x-2][i+y-1];
                }else if(r==1){
                    copy[i][j] = board[3-i+x-2][3-j+y-2];
                }else{
                    copy[i][j] = board[j+x-1][3-i+y-2];
                }
            }
        }
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                arr[i+x-1][j+y-1]=copy[i][j];
            }
        }
        return arr;
    }

    public static int bfs(int x, int y, int[][] arr){
        Queue<int[]> q = new LinkedList<>();
        int count=1;
        q.add(new int[]{x,y});
        visit[x][y]=true;

        while(!q.isEmpty()){
            int[] pos = q.poll();
            int cur_x=pos[0];
            int cur_y=pos[1];
            for(int i=0; i<4; i++){
                int nx = cur_x+dx[i];
                int ny = cur_y+dy[i];
                if(!range(nx, ny)){
                    continue;
                }
                if(!visit[nx][ny] && arr[nx][ny] == arr[x][y]){
                    visit[nx][ny]=true;
                    count++;
                    q.add(new int[]{nx,ny});
                }
            }
        }
        if(count >2){
            temp.add(new int[]{x,y});
            return count;
        }
        return 0;
    }

    public static boolean range(int x, int y){
        if(x <0 || x>=SIZE || y<0 || y>=SIZE){
            return false;
        }
        return true;
    }
}

