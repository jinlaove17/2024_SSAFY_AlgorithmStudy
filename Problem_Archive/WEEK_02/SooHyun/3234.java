import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//백트래킹을 구현할 때 배열에 대해 전역변수를 사용하게 될 시에 테스트 케이스가 많아질 수록 재사용량 또한 증가하는 경향이 존재한다.
//그에 따라 백트래킹 시 전역 변수의 상태 관리가 복잡해지고 오류 발생 가능성이 높아진다 -> 시간 초과, 메모리 사용량 초과의 주요 원인
// 백트래킹 시 전역 변수 사용을 주의하고 가능한 로컬 변수를 사용하자
// 실제로 weights, visit를 전역으로 사용할 때는 시간 초과로 문제를 통과하지 못했으나 지역변수로 전환 시 통과하였다.
class Solution
{
    static int result;
    //static int[] weights;
    //static boolean[] visit;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            int N = Integer.parseInt(br.readLine().trim());
            int[] weights= new int[N];
            boolean[] visit = new boolean[N];
            st=new StringTokenizer(br.readLine().trim());
            for(int i=0; i<N; i++) {
                weights[i] = Integer.parseInt(st.nextToken());
            }
            result=0;
            backTrack(0,0,0,N, weights, visit);
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }

    public static void backTrack(int left, int right, int count, int N, int[] weights, boolean[] visit) {
        if(count == N) {
            result++;
            return;
        }
        for(int i=0; i<N; i++) {
            if(visit[i])
                continue;
            visit[i]=true;
            backTrack(left+weights[i], right, count+1, N, weights, visit);
            if(left >= right+weights[i])
                backTrack(left,right+weights[i], count+1, N, weights, visit);
            visit[i]=false;
        }
    }
}