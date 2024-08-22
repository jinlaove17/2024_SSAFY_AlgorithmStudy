import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution {
    static int result;
    static int[][] board;
    static int[][] backup;
    static int[] select;
    static int N;
    static int W;
    static int H;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            board = new int[H][W];
            backup = new int[H][W];
            select = new int[N];
            result = Integer.MAX_VALUE;
            for(int i=0; i<H; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=0; j<W; j++) {
                    int num=Integer.parseInt(st.nextToken());
                    board[i][j] = num;
                    backup[i][j]=num;
                }
            }
            combination(0);
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }
    public static void combination(int depth) {
        if(depth==N) {
            break_brick();
            result = Math.min(result, count());
            reset();
            return;
        }
        for(int i=0; i<W; i++) {
            select[depth]=i;
            combination(depth+1);
        }
    }
    public static void break_brick() {
        int x=0;
        int y=0;
        for(int i=0; i<N; i++) {
            for(int j=0; j<H; j++) {
                if(board[j][select[i]] !=0) {
                    x=j;
                    y=select[i];
                    break;
                }
            }
            bfs(x,y);
            move();
        }
    }

    public static void bfs(int x, int y) {
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {x,y, board[x][y]});
        board[x][y]=0;
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            for(int i=0; i<4; i++) {
                for(int j=1; j<arr[2]; j++) {
                    int nx = arr[0]+dx[i]*j;
                    int ny = arr[1]+dy[i]*j;
                    if(!range(nx,ny) || board[nx][ny] ==0)
                        continue;
                    que.add(new int[] {nx,ny, board[nx][ny]});
                    board[nx][ny]=0;
                }
            }
        }
    }

    public static boolean range(int x, int y) {
        if(x >=0 && x <H && y>=0 && y<W)
            return true;
        return false;
    }

    public static void move() {
        Stack<Integer> stack = new Stack<>();
        for (int j = 0; j < W; j++) {
            for(int i=0; i<H; i++) {
                if(board[i][j] !=0) {
                    stack.add(board[i][j]);
                }
            }
            for(int i=H-1; i>=0; i--) {
                if(stack.isEmpty()) {
                    board[i][j]=0;
                }else {
                    board[i][j]=stack.pop();
                }
            }
        }
    }

    public static int count() {
        int cnt=0;
        for(int i=0; i<H; i++) {
            for(int j=0; j<W; j++) {
                if(board[i][j] !=0)
                    cnt++;
            }
        }
        return cnt;
    }

    public static void reset() {
        for(int i=0; i<H; i++) {
            for(int j=0; j<W; j++) {
                board[i][j]=backup[i][j];
            }
        }
    }
}