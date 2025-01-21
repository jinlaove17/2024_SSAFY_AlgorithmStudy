/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 스터디를 진행하면서 간만에 쉬어가는 타임이였습니다.
    - building이라는 이차원 배열을 선언하고 N의 갯수가 최대 100이므로 101이라는 최대값으로 모든 배뎔의 값들을 초기화합니다.
    - 이후 입력을 통해 받은 직접 연결되어 있는 것들간의 거리를 1로 초기화하고, 이후 3중 반복문을 통해 직항이 아닌 돌아서 갈 수 있는 경우를 찾아 초기화해줍니다.
    - 마지막에 2중 반복문을 통해 가능한 경우의 수를 모두 고려해 최소값을 갱신합니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N ^ 2)
    - 거리 최적화 : O(N ^ 3)
    - 최소 거리 계산 : O(N ^ 2)
    - 전체 시간복잡도: O(N ^ 3)

실행 시간
   - 192ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[][] building;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        building = new int[N+1][N+1];
        for(int i=0; i<=N; i++) {
            for(int j=0; j<=N; j++) {
                if(i==j)
                    continue;
                building[i][j]=101;
            }
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            building[a][b]=1;
            building[b][a]=1;
        }
        for(int i=1; i<=N; i++) {
            for(int j=1; j<=N; j++) {
                for(int k=1; k<=N; k++) {
                    if(building[j][k] > building[j][i]+building[i][k]) {
                        building[j][k] = building[j][i]+building[i][k];
                    }
                }
            }
        }
        int min1=0;
        int min2=0;
        int min_value = Integer.MAX_VALUE;
        for(int i=1; i<=N; i++) {
            for(int j=i+1; j<=N; j++) {
                int temp = sum(i,j);
                if(min_value > temp) {
                    min_value=temp;
                    min1=i;
                    min2=j;
                }
            }
        }
        System.out.println(min1+" "+min2+" "+(min_value*2));
    }

    public static int sum(int a, int b) {
        int sum=0;
        for(int i=1; i<=N; i++) {
            sum+=Math.min(building[a][i], building[b][i]);
        }
        return sum;
    }

}