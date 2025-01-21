/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 개인적으로 이번 스터디 문제 중에서 가장 해맸던 문제였습니다.
    - 문제의 핵심은 누적합이고 주어진 직사격형에서 3개의 작은 직사각형으로 쪼개는 가짓수가 몇 개인지 파악하는 것입니다.
    - 총 6가지의 경우의 수가 가능하고(이는 스터디 당일 그림으로 설명하겠다.) 이를 모두 고려해 최대값을 찾아야합니다.
    - 가장 복잡했던 부분이 인덱스를 어떻게 설정하는지 이 부분이였습니다. 누적합을 이용해 각각의 직사각형의 넓이를 구하기 위해서 작은 직사각형의 맨 위 좌상단의 점과 맨 밑 우측의 점을 인자로 보냈습니다.
    - calculate에 앞서 말한 맨 위 좌측의 x,y 그리고 맨 밑 우측의 x,y 좌표 값을 인자로 보냄으로써 넓이를 도출합니다.
    - 이렇게 6가지 경우를 모두 고려하고 최대값을 찾아 출력합니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N * M)
    - 누적합 계산 : O(N * M)
    - go 함수
        - case1 : O(N^2)
        - case2 : O(M^2)
        - case3 : O(N*M)
        - case4 : O(N*M)
        - case5 : O(N*M)
        - case6 : O(N*M)
    - 전체 시간복잡도: O(N ^ 2) or O(M ^ 2)

실행 시간
   - 112ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[][] board;
    static long[][] sum;
    static long answer=0;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        sum = new long[N][M];
        for(int i=0; i<N; i++) {
            String str = br.readLine().trim();
            for(int j=0; j<str.length(); j++) {
                char ch = str.charAt(j);
                board[i][j] = Integer.parseInt(Character.toString(ch));
            }
        }
        sum[0][0] = board[0][0];
        for(int i=1; i<N; i++) {
            sum[i][0]=sum[i-1][0]+board[i][0];
        }
        for(int j=1; j<M; j++) {
            sum[0][j]=sum[0][j-1]+board[0][j];
        }
        for(int i=1; i<N; i++) {
            for(int j=1; j<M; j++) {
                sum[i][j] = sum[i-1][j]+sum[i][j-1]-sum[i-1][j-1]+board[i][j];
            }
        }
        go();
        System.out.println(answer);
    }

    public static void go() {
        case1();
        case2();
        case3();
        case4();
        case5();
        case6();
    }
    public static void case1() {
        for(int i=0; i<N-1; i++) {
            for(int j=i+1; j<N; j++) {
                long area1 = calculate(0,0,i,M-1);
                long area2 = calculate(i+1,0,j,M-1);
                long area3 = calculate(j+1,0,N-1,M-1);
                answer = Math.max(answer, area1 * area2 * area3);
            }
        }
    }

    public static void case2() {
        for(int i=0; i<M-1; i++) {
            for(int j=i+1; j<M; j++) {
                long area1 = calculate(0,0,N-1,i);
                long area2 = calculate(0,i+1,N-1,j);
                long area3 = calculate(0,j+1,N-1,M-1);
                answer = Math.max(answer, area1 * area2 * area3);
            }
        }
    }

    public static void case3() {
        for(int i=0; i<N-1; i++) {
            for(int j=0; j<M-1; j++) {
                long area1 = calculate(0,0,i,M-1);
                long area2 = calculate(i+1,0,N-1,j);
                long area3 = calculate(i+1,j+1,N-1,M-1);
                answer = Math.max(answer, area1 * area2 * area3);
            }
        }
    }

    public static void case4() {
        for(int i=0; i<N-1; i++) {
            for(int j=0; j<M-1; j++) {
                long area1 = calculate(0,0,i,j);
                long area2 = calculate(0,j+1,i,M-1);
                long area3 = calculate(i+1,0,N-1,M-1);
                answer = Math.max(answer, area1 * area2 * area3);
            }
        }
    }

    public static void case5() {
        for(int i=0; i<N-1; i++) {
            for(int j=0; j<M-1; j++) {
                long area1 = calculate(0,0,N-1,j);
                long area2 = calculate(0,j+1,i,M-1);
                long area3 = calculate(i+1,j+1,N-1,M-1);
                answer = Math.max(answer, area1 * area2 * area3);
            }
        }
    }

    public static void case6() {
        for(int i=0; i<N-1; i++) {
            for(int j=0; j<M-1; j++) {
                long area1 = calculate(0,0,i,j);
                long area2 = calculate(i+1,0,N-1,j);
                long area3 = calculate(0,j+1,N-1,M-1);
                answer = Math.max(answer, area1 * area2 * area3);
            }
        }
    }

    public static long calculate(int x1, int y1, int x2, int y2) {
        long result=0;
        result+=sum[x2][y2];
        if(x1>0) {
            result-=sum[x1-1][y2];
        }
        if(y1>0) {
            result-=sum[x2][y1-1];
        }
        if(x1 >0 && y1>0) {
            result+=sum[x1-1][y1-1];
        }
        return result;
    }
}
