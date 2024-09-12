import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    static int N;
    static int K;
    static int[] house;
    static int[] distance;
    static boolean[] visit;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            house = new int[N];

            // N 개의 집은 사이 거리가 총 N-1개 존재한다.
            distance = new int[N-1];
            st = new StringTokenizer(br.readLine().trim());
            for(int i=0; i<N; i++) {
                house[i] = Integer.parseInt(st.nextToken());
            }
            for(int i=0; i<N-1; i++) {
                distance[i] = house[i+1]-house[i];
            }
            int result=0;
            Arrays.sort(distance);

            // 이 문제의 핵심이다. 주어진 K는 발전소의 갯수이며 이는 어떻게 보면 K개의 군집이 존재해야한다는 것과 같은 이야기이다.
            // 그렇게 될 시에 N-1개의 거리 중에서 K-1개의 거리는 선택되지 않는다는 것이 문제의 핵심이다.
            // 따라서 선택되는 (N-1)-(K-1)개의 거리 중 작은 순서대로 택하게 되면 문제를 해결할 수 있다.
            for(int i=0; i<N-K; i++) {
                result+=distance[i];
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }

}