import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            boolean flag=true;
            for(int i=0; i<N; i++) {
                if(M==0 || M %2 ==0) {
                    flag=false;
                }
                M/=2;
            }
            if(!flag) {
                sb.append("#"+t+" "+"OFF"+"\n");
            }else {
                sb.append("#"+t+" "+"ON"+"\n");
            }
        }
        System.out.println(sb.toString());
    }

}