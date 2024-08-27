import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static String strA, strB;
    static long[] pow10 = new long[13]; // pow10[i] = 10^i
    static long[] counts = new long[13]; // counts[i]: 4가 들어간 1 ~ i 자리수를 만드는 경우의 수
 
    public static void main(String[] args) throws Exception {
        for (int i = 0; i <= 12; ++i) {
            pow10[i] = (long) Math.pow(10, i);
        }
 
        counts[1] = 1; // 4
 
        for (int i = 2; i <= 12; ++i) {
            long total = 0;
 
            for (int j = 1; j <= i; ++j) {
                total += counts[j];
            }
 
            counts[i] = 8 * total + pow10[i - 1];
        }
 
        for (int i = 2; i <= 12; ++i) {
            counts[i] += counts[i - 1];
        }
 
        //      for (int i = 0; i <= 12; ++i) {
        //          System.out.println(counts[i]);
        //      }
 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());
 
        for (int tc = 1; tc <= t; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
 
            strA = st.nextToken();
            strB = st.nextToken();
 
            long originA = Long.parseLong(strA);
            long originB = Long.parseLong(strB);
 
            // 음수일 경우가 있으므로 절댓값을 붙힌다.
            long curA = Math.abs(originA), cntA = 0;
            int digitA = getDigit(curA);
 
            for (int i = digitA - 1; i >= 1; --i) {
                int quotient = (int) (curA / pow10[i]);
 
                if (quotient >= 4) {
                    cntA += (quotient - 1) * counts[i] + pow10[i];
                } else {
                    cntA += quotient * counts[i];
                }
 
                curA %= pow10[i];
            }
 
            if (curA >= 4) {
                ++cntA;
            }
 
            // 음수일 경우가 있으므로 절댓값을 붙힌다.
            long curB = Math.abs(originB), cntB = 0;
            int digitB = getDigit(curB);
 
            for (int i = digitB - 1; i >= 1; --i) {
                int quotient = (int) (curB / pow10[i]);
 
                if (quotient >= 4) {
                    cntB += (quotient - 1) * counts[i] + pow10[i];
                } else {
                    cntB += quotient * counts[i];
                }
 
                curB %= pow10[i];
            }
 
            if (curB >= 4) {
                ++cntB;
            }
 
            long answer = 0;
 
            if ((originA < 0) && (originB > 0)) {
                answer = (originB - cntB) + (Math.abs(originA) - cntA) - 1; // -1 : 건물에 0층 같은 것은 존재하지 않는다.
            } else if ((originA < 0) && (originB < 0)) {
                answer = (Math.abs(originA) - Math.abs(originB)) - (cntA - cntB);
            } else if ((originA > 0) && (originB > 0)) {
                answer = (originB - originA) - (cntB - cntA);
            }
 
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
 
        System.out.println(sb);
    }
 
    private static int getDigit(long num) {
        int digit = 0;
 
        while (num > 0) {
            ++digit;
            num /= 10;
        }
 
        return digit;
    }
}
