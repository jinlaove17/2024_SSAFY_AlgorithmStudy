class Solution {
    
    static int M, N, base;
    static int[][] map;
    
    public boolean solution(int[][] key, int[][] lock) {
        M = key.length;
        N = lock.length;
        base = M - 1; // 열쇠와 자물쇠가 딱 겹치는 부분부터 시작
        
        for (int i = 0; i < base + N; i++) {
            for (int j = 0; j < base + N; j++) {
                for (int k = 0; k < 4; k++) {
                    map = new int[N + base * 2][N + base * 2];
                    
                    fill(lock); // 확장한 맵에 원래 자물쇠 배열 복사
                    rotate(k, i, j, key);
                    
                    if (check()) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private void fill(int[][] lock) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i + base][j + base] = lock[i][j];
            }
        }
    }
    
    // 90, 180, 270로 키를 시계 방향 회전하고 맞추기
    private void rotate(int type, int x, int y, int[][] key) {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                if (type == 0) { // 0
                    map[i + x][j + y] += key[i][j];
                } else if (type == 1) { // 90
                    map[i + x][j + y] += key[j][M - i - 1];
                } else if (type == 2) { // 180
                    map[i + x][j + y] += key[M - i - 1][M - j - 1];
                } else { // 270
                    map[i + x][j + y] += key[M - j - 1][i];
                }
            }
        }
    }
    
    // 열 수 있는지 확인
    private boolean check() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i + base][j + base] != 1) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
