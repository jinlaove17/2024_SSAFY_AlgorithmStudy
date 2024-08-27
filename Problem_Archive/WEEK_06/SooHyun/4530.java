import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st=new StringTokenizer(br.readLine().trim());
            long A = Long.parseLong(st.nextToken());
            long B = Long.parseLong(st.nextToken());
            long convertA = convert(A);
            long convertB = convert(B);
            long result=0;

            if(convertA <0 && convertB >0) {
                result=convertB-convertA-1;
            }else {
                result=convertB-convertA;
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }
    public static long convert(long num) {
        boolean minus = false;
        if(num<0)
            minus = true;
        long temp = Math.abs(num);
        int idx=0;
        long result = 0;
        while(temp >0) {
            int n = (int) (temp %10);
            if(n>4) {
                n--;
            }
            result += n*(long)Math.pow(9, idx++);
            temp /= 10;
        }
        if(minus) {
            result*=-1;
        }
        return result;
    }
}