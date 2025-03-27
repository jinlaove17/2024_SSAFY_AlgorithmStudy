/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제 분류는 시뮬레이션입니다. 2개의 퍼즐을 입력받고 하나의 퍼즐은 회전하지 말고 고정, 나머지 하나에 대해 90, 180, 270도 회전하는 경우를 계산해 만들어줍니다.
    - 그리고 고정된 1개의 퍼즐과 또 다른 퍼즐의 원상태, 90, 180, 270 상태를 모두 비교해서 정답을 도출합니다.

시간 복잡도
    - 입력 처리 : O(N1 * M1) + O(N2 * M2)
    - 회전 처리 : O(N2 * M2)
    - 비교 : O((N1+N2) * (M1+M2))
    - 전체 시간복잡도 : O((N1+N2) * (M1+M2))

실행 시간
   - 456ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N1, M1, N2, M2;
    static int[][] puzzle1;
    static int[][] puzzle2;
    static int[][] puzzle2_90;
    static int[][] puzzle2_180;
    static int[][] puzzle2_270;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N1 = Integer.parseInt(st.nextToken());
        M1 = Integer.parseInt(st.nextToken());
        puzzle1 = new int[N1][M1];
        for(int i=0; i<N1; i++){
            String str = br.readLine().trim();
            for(int j=0; j<M1; j++){
                puzzle1[i][j] = Integer.parseInt(Character.toString(str.charAt(j)));
            }
        }
        st = new StringTokenizer(br.readLine().trim());
        N2 = Integer.parseInt(st.nextToken());
        M2 = Integer.parseInt(st.nextToken());
        puzzle2 = new int[N2][M2];
        puzzle2_90 = new int[M2][N2];
        puzzle2_180 = new int[N2][M2];
        puzzle2_270 = new int[M2][N2];
        for(int i=0; i<N2; i++){
            String str = br.readLine().trim();
            for(int j=0; j<M2; j++){
                puzzle2[i][j] = Integer.parseInt(Character.toString(str.charAt(j)));
            }
        }
        for(int i=0; i<M2; i++){
            for(int j=0; j<N2; j++){
                puzzle2_90[i][j] = puzzle2[N2-1-j][i];
            }
        }
        for(int i=0; i<N2; i++){
            for(int j=0; j<M2; j++){
                puzzle2_180[i][j] =puzzle2[N2-1-i][M2-1-j];
            }
        }
        for(int i=0; i<M2; i++){
            for(int j=0; j<N2; j++){
                puzzle2_270[i][j] = puzzle2[j][M2-1-i];
            }
        }
        int minN = Math.min(N1,N2);
        int minM = Math.min(M1, M2);

        int maxN = Math.max(N1, N2);
        int maxM = Math.max(M1, M2);

        int result = 10001;
        for(int x=-minN+1; x<maxN; x++){
            for(int y=-minM+1; y<maxM; y++){
                boolean flag = true;
                for(int i=0; i<N2; i++){
                    for(int j=0; j<M2; j++){
                        if(x+i <0 || y+j < 0 || x+i >=N1 || y+j >=M1){
                            continue;
                        }
                        if(puzzle1[x+i][y+j]==1 && puzzle2[i][j]==1){
                            flag=false;
                            break;
                        }
                    }
                    if(!flag)
                        break;
                }
                if(flag){
                    int x1 = Math.min(x,0);
                    int y1 = Math.min(y,0);
                    int x2 = Math.max(N1, N2+x);
                    int y2 = Math.max(M1, M2+y);
                    result = Math.min(result, (x2-x1)*(y2-y1));
                }
                flag = true;
                for(int i=0; i<N2; i++){
                    for(int j=0; j<M2; j++){
                        if(x+i <0 || y+j < 0 || x+i >=N1 || y+j >=M1){
                            continue;
                        }
                        if(puzzle1[x+i][y+j]==1 && puzzle2_180[i][j]==1){
                            flag=false;
                            break;
                        }
                    }
                    if(!flag)
                        break;
                }
                if(flag){
                    int x1 = Math.min(x,0);
                    int y1 = Math.min(y,0);
                    int x2 = Math.max(N1, N2+x);
                    int y2 = Math.max(M1, M2+y);
                    result = Math.min(result, (x2-x1)*(y2-y1));
                }
            }

        }

        for(int x=-minM+1; x<minM; x++){
            for(int y=-minN+1; y<maxN; y++){
                boolean flag = true;
                for(int i=0; i<M2; i++){
                    for(int j=0; j<N2; j++){
                        if(x+i <0 || y+j < 0 || x+i >=N1 || y+j >=M1){
                            continue;
                        }
                        if(puzzle1[x+i][y+j]==1 && puzzle2_90[i][j]==1){
                            flag=false;
                            break;
                        }
                    }
                    if(!flag)
                        break;
                }
                if(flag){
                    int x1 = Math.min(x,0);
                    int y1 = Math.min(y,0);
                    int x2 = Math.max(N1, M2+x);
                    int y2 = Math.max(M1, N2+y);
                    result = Math.min(result, (x2-x1)*(y2-y1));
                }
                flag = true;
                for(int i=0; i<M2; i++){
                    for(int j=0; j<N2; j++){
                        if(x+i <0 || y+j < 0 || x+i >=N1 || y+j >=M1){
                            continue;
                        }
                        if(puzzle1[x+i][y+j]==1 && puzzle2_270[i][j]==1){
                            flag=false;
                            break;
                        }
                    }
                    if(!flag)
                        break;
                }
                if(flag){
                    int x1 = Math.min(x,0);
                    int y1 = Math.min(y,0);
                    int x2 = Math.max(N1, M2+x);
                    int y2 = Math.max(M1, N2+y);
                    result = Math.min(result, (x2-x1)*(y2-y1));
                }
            }
        }
        System.out.println(result);
    }
}