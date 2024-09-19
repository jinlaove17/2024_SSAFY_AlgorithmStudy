import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] board;
    static String[] dir;
    static int K;
    static int len;
    static int H;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        K = Integer.parseInt(br.readLine().trim());
        len = (int)Math.pow(2, K);
        board = new int[len+1][len+1];
        dir = new String[K*2];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<dir.length; i++) {
            dir[i] = st.nextToken();
//			System.out.println(dir[i]);
        }
        H = Integer.parseInt(br.readLine().trim());
        find(0,1,len,1,len);
        for(int i=1; i<=len; i++) {
            for(int j=1; j<=len; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static int change(String dir, int value) {
        int result=-1;
        if(dir.equals("U") || dir.equals("D")) {
            if(value==0) {
                result=2;
            }else if(value==2) {
                result=0;
            }else if(value==1) {
                result=3;
            }else {
                result=1;
            }
        }else {
            if(value==0) {
                result=1;
            }else if(value==1) {
                result=0;
            }else if(value==2) {
                result=3;
            }else {
                result=2;
            }
        }
        return result;
    }
    public static void find(int depth, int start_X, int end_X, int start_Y, int end_Y) {
        if(depth == 2*K) {
            if(start_X == end_X && start_Y == end_Y){
                board[start_X][start_Y] = H;
            }
        }else {
            if(dir[depth].equals("U")) {
                int mid_X = (start_X + end_X) / 2;
                find(depth + 1, start_X, mid_X, start_Y, end_Y);
                for(int i = mid_X + 1; i <= end_X; i++) {
                    for(int j = start_Y; j <= end_Y; j++) {
                        board[i][j] = change(dir[depth], board[start_X + end_X - i][j]);
                    }
                }
            }else if(dir[depth].equals("D")) {
                int mid_X = (start_X + end_X) / 2;
                find(depth + 1, mid_X + 1, end_X, start_Y, end_Y);
                for(int i = start_X; i <= mid_X; i++) {
                    for(int j = start_Y; j <= end_Y; j++) {
                        board[i][j] = change(dir[depth], board[end_X - (i - start_X)][j]);
                    }
                }
            }else if(dir[depth].equals("R")) {
                int mid_Y = (start_Y + end_Y) / 2;
                find(depth + 1, start_X, end_X, mid_Y + 1, end_Y);
                for(int i = start_X; i <= end_X; i++) {
                    for(int j = start_Y; j <= mid_Y; j++) {
                        board[i][j] = change(dir[depth], board[i][end_Y - (j - start_Y)]);
                    }
                }
            }else {
                int mid_Y = (start_Y + end_Y) / 2;
                find(depth + 1, start_X, end_X, start_Y, mid_Y);
                for(int i = start_X; i <= end_X; i++) {
                    for(int j = mid_Y + 1; j <= end_Y; j++) {
                        board[i][j] = change(dir[depth], board[i][start_Y + end_Y - j]);
                    }
                }
            }
        }
    }
}