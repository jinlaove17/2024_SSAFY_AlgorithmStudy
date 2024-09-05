import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution {
    static int N;
    static int M;
    static int Q;
    static char[][] board;
    static boolean[][] visit;
    static int[] dx = {-1,0,0,1};
    static int[] dy = {0,-1,1,0};
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            Q = Integer.parseInt(st.nextToken());
            board = new char[N][M];
            for(int i=0; i<N; i++) {
                String line = br.readLine().trim();
                for(int j=0; j<M; j++) {
                    board[i][j] = line.charAt(j);
                }
            }

            for(int i=0; i<Q; i++) {
                st = new StringTokenizer(br.readLine().trim());
                String command = st.nextToken();
                if(command.equals("U")) {
                    String newline = st.nextToken();
                    moveUp(newline);
                }else if(command.equals("L")) {
                    moveLeft();
                }else if(command.equals("R")) {
                    moveRight();
                }else if(command.equals("D")) {
                    delete();
                }
            }
            sb.append("#"+t+"\n");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    sb.append(board[i][j]);
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void moveUp(String newline) {
        char[] newRow = newline.toCharArray();
        boolean canInsert = true;
        for(int j=0; j<M; j++) {
            boolean flag = false;
            for(int i=0; i<N; i++) {
                if(board[i][j]=='#') {
                    flag=true;
                    break;
                }
            }
            if(!flag) {
                canInsert=false;
                break;
            }
        }
        if(!canInsert)
            return;
        for(int i=0; i<N-1; i++) {
            for(int j=0; j<M; j++) {
                board[i][j]=board[i+1][j];
            }
        }
        for(int j=0; j<M; j++) {
            board[N-1][j]=newRow[j];
        }
        apply();
    }
    public static void apply() {
        for (int j = 0; j < M; j++) {
            Stack<Character> stack = new Stack<>();
            for (int i = 0; i < N; i++) {
                if (board[i][j] != '#') {
                    stack.add(board[i][j]);
                }
            }
            for (int i = N - 1; i >= 0; i--) {
                if (stack.isEmpty()) {
                    board[i][j] = '#';
                } else {
                    board[i][j] = stack.pop();
                }
            }
        }
    }

    public static void moveLeft() {
        for (int i = 0; i < N; i++) {
            Stack<Character> stack = new Stack<>();
            for (int j = M - 1; j >= 0; j--) {
                if (board[i][j] != '#') {
                    stack.push(board[i][j]);
                }
            }
            int idx = 0;
            while (!stack.isEmpty()) {
                board[i][idx++] = stack.pop();
            }
            while (idx < M) {
                board[i][idx++] = '#';
            }
        }
    }

    public static void moveRight() {
        for (int i = 0; i < N; i++) {
            Stack<Character> stack = new Stack<>();
            for (int j = 0; j < M; j++) {
                if (board[i][j] != '#') {
                    stack.push(board[i][j]);
                }
            }
            int idx = M - 1;
            while (!stack.isEmpty()) {
                board[i][idx--] = stack.pop();
            }
            while (idx >= 0) {
                board[i][idx--] = '#';
            }
        }
    }


    public static void delete() {
        visit = new boolean[N][M];
        int maxSize=0;
        Queue<int[]> que = new LinkedList<>();
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if(board[i][j] !='#' && !visit[i][j]) {
                    Queue<int[]> temp = new LinkedList<>();
                    int size = bfs(i,j, temp);
                    if(size > maxSize) {
                        maxSize=size;
                        que = temp;
                    }else if(size==maxSize) {
                        for(int[] arr : temp) {
                            que.add(arr);
                        }
                    }
                }
            }
        }
        for(int[] arr : que) {
            board[arr[0]][arr[1]]='#';
        }
        apply();
    }

    public static int bfs(int i, int j, Queue<int[]> temp) {
        char color = board[i][j];
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {i,j});
        temp.add(new int[] {i,j});
        visit[i][j]=true;
        int size=1;
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            for(int d=0; d<4; d++) {
                int nx = arr[0]+dx[d];
                int ny = arr[1]+dy[d];
                if (nx >= 0 && nx < N && ny >= 0 && ny < M && !visit[nx][ny] && board[nx][ny] == color) {
                    que.add(new int[]{nx, ny});
                    temp.add(new int[]{nx, ny});
                    visit[nx][ny] = true;
                    size++;
                }
            }
        }
        return size;
    }
}