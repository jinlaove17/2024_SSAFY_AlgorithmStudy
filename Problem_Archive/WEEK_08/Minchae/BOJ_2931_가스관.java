/**
 * 2931 가스관
 * https://www.acmicpc.net/problem/2931
 * 
 * @author minchae
 * @date 2024. 9. 9.
 */

import java.io.*;
import java.util.*;

public class Main2 {
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static char[] type = {'|', '-', '+', '1', '2', '3', '4'};
	
	static int R, C;
	static char[][] map;
	
	static int sx, sy, ex, ey, tx, ty;
	static int cnt;
	
	static char answer;
	
	static boolean[][] visited;
	static boolean flag = false;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        
        map = new char[R][C];
        
        for (int i = 0; i < R; i++) {
        	map[i] = br.readLine().toCharArray();
        	
        	for (int j = 0; j < C; j++) {
        		if (map[i][j] == 'M') {
        			sx = i;
        			sy = j;
        		}
        		
        		if (map[i][j] != 'M' && map[i][j] != 'Z' && map[i][j] != '.') {
        			cnt++;
        		}
        	}
        }
        
        cnt++; // 지워진 블록에도 파이프가 있기 때문에 증가 시켜줌
        
        visited = new boolean[R][C];

        for (int i = 0; i < 4; i++) {
        	int nx = sx + dx[i];
        	int ny = sy + dy[i];
        	
        	// 범위 벗어나지 않고, 파이프가 있는 경우 dfs 실행
        	if (isRange(nx, ny) && map[nx][ny] != '.') {
        		dfs(0, nx, ny, i, false);
        	}
        }
        
        System.out.println((ex + 1) + " " + (ey + 1) + " " + answer);
	}
	
	private static void dfs(int depth, int x, int y, int dir, boolean use) {
		if (flag) {
			return;
		}
		
		// 모든 파이프 개수만큼 진행한 경우 -> 다 연결 가능한 것이므로 답을 찾은 것
		if (depth == cnt) {
			flag = true;
			
			// 다 연결된 경우 임시 저장 했던 게 최종 답
			ex = tx;
			ey = ty;
			
			answer = map[ex][ey];
			
			return;
		}
		
		int nd = changeDir(map[x][y], dir);
		
		if (nd == -1) {
			return;
		}
		
		int nx = x + dx[nd];
		int ny = y + dy[nd];
		
		if (!isRange(nx, ny)) {
			return;
		}
		
		if (map[nx][ny] == '.') {
			// 아직 파이프를 연결하지 않은 경우 7가지를 다 넣어봄
			if (!use) {
				for (int i = 0; i < 7; i++) {
					// 임시 저장
					tx = nx;
					ty = ny;
					
					map[nx][ny] = type[i];
					visited[nx][ny] = true;
					
					dfs(depth + 1, nx, ny, nd, true);
					
					map[nx][ny] = '.';
					visited[nx][ny] = false;
				}
			}
		} else {
			if (visited[nx][ny]) {
				// 파이프인데 이미 방문한 경우는 '+' 타입
				dfs(depth, nx, ny, nd, use);
			} else {
				visited[nx][ny] = true;
				dfs(depth + 1, nx, ny, nd, use);
				visited[nx][ny] = false;
			}
		}
	}
	
	// 방향 바꾸기
	private static int changeDir(char pipe, int dir) {
		if (pipe == '|') {
            if (dir == 0 || dir == 1) return dir;
        } else if (pipe == '-') {
            if (dir == 2 || dir == 3) return dir;
        } else if (pipe == '+') {
            return dir;
        } else if (pipe == '1') {
            if (dir == 2) return 1;
            if (dir == 0) return 3;
        } else if (pipe == '2') {
            if (dir == 1) return 3;
            if (dir == 2) return 0;
        } else if (pipe == '3') {
            if (dir == 3) return 0;
            if (dir == 1) return 2;
        } else if (pipe == '4') {
            if (dir == 3) return 1;
            if (dir == 0) return 2;
        }
        
        return -1;
	}

	private static boolean isRange(int x, int y) {
		return x >= 0 && x < R && y >= 0 && y < C;
	}
}
