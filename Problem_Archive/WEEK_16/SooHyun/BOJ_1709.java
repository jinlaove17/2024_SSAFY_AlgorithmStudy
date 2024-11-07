/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심은 시키는대로 하는 것입니다.
	- 낮에는 정해진 규칙에 따라 죽는 사람이 결정이 되고 밤에는 마피아가 임의로 한명을 선택해 죽입니다.
	- 그러나 밤에 마피아가 누구를 죽이느냐에 따라 다음 날 낮에 죽는 사람이 변동됩니다.
	- 따라서 dfs와 백트래킹을 사용해 낮에는 정해진 규칙에 따라 밤에는 임의이 한 명을 죽이는 것을 코드를 통해 구현했습니다.
	- 이를 통해 결과를 도출했습니다.

시간 복잡도
	- 초기화 : O(N*N)
	- DFS : 최악의 경우 N레벨까지 내려가고 point 또한 모든 N개를 탐색할 수 있으므로 O(N*N)
	- 전체 시간 복잡도 : O(N*N)


실행 시간
	- 696ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] points;
    static int[][] board;
    static boolean[] alive;
    static int num;
    static int result = 0;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        points = new int[N];
        alive = new boolean[N];
        Arrays.fill(alive, true);
        board = new int[N][N];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<N; i++) {
            points[i] = Integer.parseInt(st.nextToken());
        }
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        num = Integer.parseInt(br.readLine().trim());
        dfs(N,0);
        System.out.println(result);
    }
    public static void dfs(int cnt, int day) {
        if(!alive[num] || cnt ==1) {
            result = Math.max(result, day);
            return;
        }
        if(cnt %2==0) {
            for(int i=0; i<N; i++) {
                if(!alive[i] || i==num)
                    continue;
                for(int j=0; j<N; j++) {
                    if(!alive[j])
                        continue;
                    points[j]+=board[i][j];
                }
                alive[i]=false;
                dfs(cnt-1, day+1);
                alive[i]=true;
                for(int j=0; j<N; j++) {
                    if(!alive[j])
                        continue;
                    points[j]-=board[i][j];
                }
            }
        }else {
            int max =0;
            int idx=-1;
            for(int i=0; i<N; i++) {
                if(alive[i] && max < points[i]) {
                    max = points[i];
                    idx =i;
                }else if(alive[i] && max == points[i]) {
                    max = points[i];
                    idx = Math.min(idx, i);
                }
            }
            alive[idx]=false;
            dfs(cnt-1, day);
            alive[idx]=true;
        }
    }
}