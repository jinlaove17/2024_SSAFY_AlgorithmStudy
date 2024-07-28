import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution
{
    static int cnt;
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            int N = Integer.parseInt(br.readLine().trim());
            int M = Integer.parseInt(br.readLine().trim());
            int[][] students = new int[N][N];
            for(int m=0; m<M; m++) {
                st = new StringTokenizer(br.readLine().trim());
                int from = Integer.parseInt(st.nextToken())-1;
                int to = Integer.parseInt(st.nextToken())-1;
                students[from][to]=-1;
                students[to][from]=1;
            }
            int result=0;
            for(int n=0; n<N; n++) {
                cnt=0;
                bfs(students,n);
                if(cnt == N-1)
                    result++;
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }
    public static void bfs(int[][] students, int n) {
        Queue<Integer> que = new LinkedList<>();
        boolean[] visit = new boolean[students.length];
        visit[n]=true;
        que.add(n);
        while(!que.isEmpty()) {
            int student = que.poll();
            for(int i=0; i<students.length; i++) {
                if(students[student][i]==1 && !visit[i]) {
                    que.add(i);
                    visit[i]=true;
                    cnt++;
                }
            }
        }
        que.clear();
        visit = new boolean[students.length];
        visit[n]=true;
        que.add(n);
        while(!que.isEmpty()) {
            int student = que.poll();
            for(int i=0; i<students.length; i++) {
                if(students[student][i]==-1 && !visit[i]) {
                    que.add(i);
                    visit[i]=true;
                    cnt++;
                }
            }
        }
    }
}