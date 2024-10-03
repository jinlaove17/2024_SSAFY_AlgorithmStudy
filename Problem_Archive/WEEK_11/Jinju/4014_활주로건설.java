/*
  문제 접근 :
  - 특정 높이의 활주로를 건설하기 위해서는 그만큼 같은 높이가 이어져야 한다.
  - 위 조건을 만족한다음 바로 옆에 있는 블럭의 높이가 1 차이어야 한다.
  - 완전 탐색 (행, 열) 을 통해 찾자

  알고리즘 : 행, 열을 넣고 완전 탐색

  실행 시간 : 120ms
  
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
 
public class Solution {
    static int N, X;
    static int[][] grid;
 
    // 경사로 체크 함수
    public static int checkSlope(int[] row) {
        int cnt = 1; // 동일 높이 카운트
        for (int i = 1; i < N; i++) {
            if (row[i] == row[i - 1]) { // 같은 높이라면
                cnt++;
            } else if (row[i] - row[i - 1] == 1 && cnt >= X) { // 높이 1 높아지면
                cnt = 1;
            } else if (row[i - 1] - row[i] == 1 && cnt >= 0) { // 높이 1 낮아지면
                cnt = -X + 1;
            } else { // 높이 차이가 2 이상일 경우
                return 0;
            }
        }
        if (cnt >= 0) {
            return 1;
        }
        return 0;
    }
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
 
        int T = Integer.parseInt(br.readLine());
         
        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); 
            X = Integer.parseInt(st.nextToken());
             
            grid = new int[N][N];
            int ret = 0;
 
            // 행 입력 및 경사로 확인
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
                ret += checkSlope(grid[i]); // 행에 대해 경사로 확인
            }
 
            // 열에 대해 경사로 확인
            for (int i = 0; i < N; i++) {
                int[] tmp = new int[N];
                for (int j = 0; j < N; j++) {
                    tmp[j] = grid[j][i]; 
                }
                ret += checkSlope(tmp); // 열에 대해 경사로 확인
            }
            sb.append("#").append(t).append(" ").append(ret).append("\n");
        }
        System.out.print(sb.toString());
    }
}
