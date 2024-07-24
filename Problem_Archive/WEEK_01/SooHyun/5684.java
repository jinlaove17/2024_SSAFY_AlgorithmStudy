import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution
{
    static int result;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            result = Integer.MAX_VALUE;
            st = new StringTokenizer(br.readLine().trim());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int[][] map = new int[N][N];
            for(int i=0; i<M; i++) {
                st = new StringTokenizer(br.readLine().trim());
                int s = Integer.parseInt(st.nextToken()) - 1;
                int e = Integer.parseInt(st.nextToken()) - 1;
                int c = Integer.parseInt(st.nextToken());
                map[s][e]=c;
            }
            for(int i=0; i<N; i++) {
                bfs(i,N,map);
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }
    public static void bfs(int i, int N, int[][] map) {
        boolean[] visit = new boolean[N];
        Queue<int[]> que = new LinkedList<>();
        //visit[i]=true;
        que.add(new int[] {i,0});
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            for(int j=0; j<N; j++) {
                if(map[arr[0]][j]==0 || visit[j])
                    continue;
                visit[j]=true;
                que.add(new int[] {j, arr[1]+map[arr[0]][j]});
                if(i==j)
                    result = Math.min(result, arr[1]+map[arr[0]][j]);
            }
        }
    }
}