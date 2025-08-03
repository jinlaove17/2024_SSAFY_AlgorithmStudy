import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static int N, M, K;
    static char[][] board;
    static int[] dx = {-1,0,1,0,-1,1,-1,1};
    static int[] dy = {0,-1,0,1,-1,-1,1,1};
    static Map<String, Integer> map;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        board = new char[N][K];
        map = new HashMap<>();
        for(int i=0; i<N; i++) {
            String str = br.readLine().trim();
            board[i] = str.toCharArray();
        }
        find();
        for(int i=0; i<K; i++) {
            String str = br.readLine().trim();
            sb.append(map.getOrDefault(str, 0)+"\n");
        }
        System.out.println(sb.toString());
    }

    public static void find() {
        for(int len =1; len <=5; len++) {
            for(int i=0; i<N; i++) {
                for(int j=0; j<M; j++) {
                    dfs(i,j,Character.toString(board[i][j]),1, len);
                }
            }
        }
    }

    public static void dfs(int x, int y, String current, int depth, int len) {
        if(depth == len) {
            map.put(current, map.getOrDefault(current, 0)+1);
            return;
        }
        for(int d=0; d<8; d++) {
            int nx = (x+dx[d] +N ) % N;
            int ny = (y+dy[d] + M) % M;
            dfs(nx, ny, current + Character.toString(board[nx][ny]), depth+1, len);
        }
    }

}
