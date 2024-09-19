/**
 * 20187 종이접기
 * https://www.acmicpc.net/problem/20187
 * 
 * @author minchae
 * @date 2024. 9. 19.
 * */

import java.io.*;
import java.util.*;

public class Main {
    
    static int k, h;
    static char[] order;
    
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        k = Integer.parseInt(br.readLine());
        
        order = new char[2 * k];
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        for (int i = 0; i < 2 * k; i++) {
            order[i] = st.nextToken().charAt(0);
        }
        
        h = Integer.parseInt(br.readLine());

        // map 크기를 2^k로 설정
        int len = (int) Math.pow(2, k);
        map = new int[len][len];
        
        solve(0, 0, len - 1, 0, len - 1);
        
        // 결과 출력
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    // idx는 접는 명령의 순서, x1, x2는 세로 범위, y1, y2는 가로 범위
    private static void solve(int idx, int x1, int x2, int y1, int y2) {
        // 기저 조건: 모든 접기를 완료한 경우
        if (idx == 2 * k) {
            map[x1][y1] = h;
            return;
        }
        
        char dir = order[idx];
        
        if (dir == 'U') {
            solve(idx + 1, x1, (x1 + x2) / 2, y1, y2);
            
            // 위로 접는 경우, 아래쪽에 대칭적으로 값을 복사
            for (int i = (x1 + x2) / 2 + 1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
                    map[i][j] = changeDir(0, map[x1 + x2 - i][j]);
                }
            }
        } else if (dir == 'D') {
            solve(idx + 1, (x1 + x2) / 2 + 1, x2, y1, y2);
            
            // 아래쪽에 대칭적으로 값을 복사
            for (int i = x1; i <= (x1 + x2) / 2; i++) {
                for (int j = y1; j <= y2; j++) {
                    map[i][j] = changeDir(0, map[x1 + x2 - i][j]);
                }
            }
        } else if (dir == 'L') {
            solve(idx + 1, x1, x2, y1, (y1 + y2) / 2);
            
            // 왼쪽으로 접는 경우, 오른쪽에 대칭적으로 값을 복사
            for (int i = x1; i <= x2; i++) {
                for (int j = (y1 + y2) / 2 + 1; j <= y2; j++) {
                    map[i][j] = changeDir(1, map[i][y1 + y2 - j]);
                }
            }
        } else if (dir == 'R') {
            solve(idx + 1, x1, x2, (y1 + y2) / 2 + 1, y2);
            
            // 오른쪽으로 접는 경우, 왼쪽에 대칭적으로 값을 복사
            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= (y1 + y2) / 2; j++) {
                    map[i][j] = changeDir(1, map[i][y1 + y2 - j]);
                }
            }
        }
    }
    
    // type이 0이면 U, D에 따른 대칭, 1이면 L, R에 따른 대칭
    private static int changeDir(int type, int dir) {
        if (type == 0) {
            // U, D인 경우 위, 아래가 대칭
            if (dir == 0) {
            	return 2;
            } else if (dir == 1) {
            	return 3;
            } else if (dir == 2) {
            	return 0;
            } else {
            	return 1;
            }
        } else {
            // L, R인 경우 좌, 우가 대칭
            if (dir == 0) {
            	return 1;
            } else if (dir == 1) {
            	return 0;
            } else if (dir == 2) {
            	return 3;
            } else {
            	return 2;
            }
        }
    }
}
