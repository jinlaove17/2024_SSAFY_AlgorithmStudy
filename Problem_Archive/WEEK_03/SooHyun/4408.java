import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            int N = Integer.parseInt(br.readLine().trim());
            int[][] room = new int[N][2];
            boolean[] visit = new boolean[N];
            for(int i=0; i<N; i++) {
                st=new StringTokenizer(br.readLine().trim());
                int r1=Integer.parseInt(st.nextToken())-1;
                int r2=Integer.parseInt(st.nextToken())-1;
                room[i][0] = Math.min(r1/2, r2/2);
                room[i][1] = Math.max(r1/2, r2/2);
            }
            Arrays.sort(room, (o1,o2)->{
                if(o1[0]==o2[0])
                    return o1[1]-o2[1];
                return o1[0]-o2[0];
            });
            int result=0;
            for(int i=0; i<N; i++) {
                if(visit[i])
                    continue;
                visit[i]=true;
                int start = room[i][0];
                int end = room[i][1];
                for(int j=0; j<N; j++) {
                    if(visit[j])
                        continue;
                    if(room[j][0] > end) {
                        visit[j]=true;
                        end = room[j][1];
                    }
                }
                result++;
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }
}