/**
 * 21277 짠돌이 호석
 * https://www.acmicpc.net/problem/21277
 *
 * @author minchae
 * @date 2025. 3. 27.
 * 
 * 문제 풀이
 * - 답 보고 풀었습니다...
 * 
 * 시간 복잡도
 * O()
 * 
 * 실행 시간
 * 824 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static final int INF = Integer.MAX_VALUE;
	
    static int n, m, r, c;
    static int[][] board, puzzle;
    
    static int answer = INF;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        board = new int[200][200]; // 
        puzzle = new int[50][50]; // 격자 크기 최대 50
        
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        for (int i = 50; i < n + 50; i++) {
            String line = br.readLine();
            for (int j = 50; j < m + 50; j++) {
                board[i][j] = line.charAt(j - 50) - '0';
            }
        }
        
        st = new StringTokenizer(br.readLine());
        
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        
        for (int i = 0; i < r; i++) {
            String line = br.readLine();
            for (int j = 0; j < c; j++) {
                puzzle[i][j] = line.charAt(j) - '0';
            }
        }
        
        for (int i = 0; i <= 200; i++) {
            for (int j = 0; j <= 200; j++) {
            	// 퍼즐 놓는 위치가 범위 벗어나는 경우
                if (i + r > 200 || i + c > 200 || j + c > 200 || j + r > 200) {
                	continue;
                }
                
                // 퍼즐 배열의 범위를 벗어나는 경우
                if (i + r < 50 && j + c < 50 || n + 50 <= i && m + 50 <= j) {
                	continue;
                }
                
                // 4번 회전 시킴 (0, 90, 180, 270)
                for (int k = 0; k < 4; k++) {
                    answer = Math.min(answer, solve(i, j));
                    
                    rotate();
                    
                    int temp = r;
                    r = c;
                    c = temp;
                }
            }
        }
        
        System.out.println(answer);
    }

    // 회전
    static void rotate() {
        int[][] temp = new int[55][55];
        
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                temp[i][r - j - 1] = puzzle[j][i];
            }
        }
        
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                puzzle[i][j] = temp[i][j];
            }
        }
    }

    static int solve(int x, int y) {
    	// 두 개의 퍼즐을 맞춰봄
        for (int i = x, p = 0; i < x + r; i++, p++) {
            for (int j = y, q = 0; j < y + c; j++, q++) {
            	// 둘 다 1인 경우 겹치는 것이기 때문에 딱 맞게 합칠 수 없음
                if (board[i][j] != 0 && puzzle[p][q] != 0) {
                	return INF;
                }
            }
        }
        
        int minX = Math.min(x, 50);
        int maxX = Math.max(x + r, n + 50) - 1;
        
        int minY = Math.min(y, 50);
        int maxY = Math.max(y + c, m + 50) - 1;
        
        int height = Math.abs(minX - maxX) + 1;
        int width = Math.abs(minY - maxY) + 1;
        
        return height * width;
    }
}

