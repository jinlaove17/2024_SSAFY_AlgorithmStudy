/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 이번주의 쉬어가는 타임, 1학기 때 A형 시험의 추억을 되살리고자 제출했습니다.
    - 문제의 핵심은 bfs 알고리즘을 통해서 각 섬들을 그룹화하는 것이 첫번째, 그 이후 그룹화된 섬들간에 또다시 bfs 탐색을 진행하면서 최단 거리를 구하는 것입니다.
    - 일반적인 bfs를 통해서 섬들을 그룹화합니다. 이때 각각의 섬들의 그룹화 시작 번호를 1부터 시작해 group이라는 2차원 배열에 그룹 번호를 저장합니다.
    - 이후 find함수를 통해서 가능한 가짓수에 대해서 즉 모든 그룹들의 경우를 따지면서 최단 거리를 갱신합니다.
    - 이를 통해 최종 답을 도축합니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N * N)
    - bfs로 섬 그룹화 : O(N ^ 2)
    - find함수로 최단 거리 구하기 : O(N ^ 4)
    - 전체 시간복잡도: O(N ^ 4) (N은 보드의 크기)

실행 시간
   - 284ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static int[][] board;
    static boolean[][] visit;
    static int[][] group;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    static int result=Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br=  new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        group = new int[N][N];
        visit = new boolean[N][N];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<N; j++){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int idx=1;
        for(int i=0; i<N; i++){
            for(int j=0; j <N; j++){
                if(board[i][j]==1 && !visit[i][j]){
                    bfs(i,j,idx);
                    idx++;
                }
            }
        }
        for(int i=0; i<group.length; i++){
            for(int j=0; j<group.length; j++){
                if(group[i][j] !=0){
                    find(i,j);
                }
            }
        }
        System.out.println(result);
    }

    public static void bfs(int x, int y, int idx){
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[]{x,y});
        visit[x][y]=true;
        group[x][y]=idx;
        while(!que.isEmpty()){
            int[] arr = que.poll();
            int cur_x = arr[0];
            int cur_y = arr[1];
            for(int i=0; i<4; i++){
                int nx = cur_x+dx[i];
                int ny = cur_y+dy[i];
                if(!range(nx,ny))
                    continue;
                if(board[nx][ny]==1 && !visit[nx][ny]){
                    visit[nx][ny]=true;
                    group[nx][ny]=idx;
                    que.add(new int[]{nx,ny});
                }
            }
        }
    }

    public static void find(int x, int y){
        Queue<int[]> que = new LinkedList<>();
        visit = new boolean[N][N];
        visit[x][y]=true;
        que.add(new int[]{x,y,0});
        int init_num = group[x][y];
        while(!que.isEmpty()){
            int[] arr = que.poll();
            int cur_x = arr[0];
            int cur_y = arr[1];
            int count = arr[2];
            if(group[cur_x][cur_y] !=0 && group[cur_x][cur_y] != init_num){
                result = Math.min(result, count-1);
            }
            if(count > result)
                return;
            for(int i=0; i<4; i++){
                int nx = cur_x+dx[i];
                int ny = cur_y+dy[i];
                if(!range(nx,ny))
                    continue;
                if(group[nx][ny] == init_num)
                    continue;
                if(visit[nx][ny])
                    continue;
                visit[nx][ny]=true;
                que.add(new int[]{nx,ny,count+1});
            }
        }
    }
    public static boolean range(int x, int y){
        if(x <0 || x>=N || y<0 || y>=N)
            return false;
        return true;
    }
}