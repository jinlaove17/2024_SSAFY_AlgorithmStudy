/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 봤을 때 문제 이해가 되지 않아서 고전했습니다.
	- N이 9이고 예를 들어 덩어리를 (1,5,7) / (2, 3, 4) / (6, 8, 9) 로 나눈다고 해도 앞에 있는 것에서 뒷 번호로 이동하는 경로가 고속도로로 반드시 존재합니다.
	- 이에 따라 연속된 것들이 얼마나 많이 쪼개질 수 있는지가 문제 해결에 핵심이였습니다.
	- 주어진 N이하의 수 중에서 N % i ==0 인것에 대해서만 똑같은 갯수로 나누어질 수 있다는 것을 캐치했습니다.
	- 작은 수부터 반복문을 순회해 만약 해당 덩어리에 속한 숫자들 중에서 그 보다 작은 것에 도달하는 일반 도로가 없는 경우를 찾고 break를 수행합니다.
	- 그렇게 나온 수는 덩어리에 속하는 도시의 갯수이므로 전체 덩어리 갯수는 N / answer가 되어 답을 구합니다.

시간 복잡도

	- 기본 도로 설정 루프 : O(N)
	- 사용자 지정 도로 설정 루프 : O(M)
	- 덩어리 분할을 위한 주요 로직 : O(N*logN)
	    - 외부 루프 : O(N)
	    - 내부 루프 : O(N/i)
	- 전체 시간 복잡도 : O(N*N + M)


실행 시간
	- 240ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static boolean[] visit;
    static int[][] road;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        visit = new boolean[N+1];
        road = new int[N+1][N+1];
        for(int i=1; i<N; i++) {
            road[i][i+1]=1;
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            road[n1][n2]=1;
        }
        int answer =0;
        for(int i=1; i<=N; i++) {
            if((double)N/i != N/i)
                continue;
            int plus = N/i;
            int idx=1;
            boolean flag=true;
            //System.out.println(i+"개 씩 끊기");
            for(int j=1; j<=plus; j++) {
                int cnt=1;
                int start =idx;
                int end = idx+i-1;
                //System.out.print("시작 : "+start+" 끝 : "+end+" [");
                for(int k=start; k<=end; k++) {
                    for(int l=1; l<start; l++) {
                        if(road[k][l]==1) {
                            flag=false;
                            break;
                        }
                    }
                }
                if(flag) {
                    answer =i;
                }

                while(cnt<=i) {
                    //System.out.print(idx+" ");
                    idx++;
                    cnt++;
                }
                //System.out.println("]");
            }
            if(flag)
                break;
            //System.out.println(flag);
        }
        System.out.println(N/answer);
    }

}
