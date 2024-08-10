import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
class Cell {
    int state; // 0: 죽은 상태, 1: 활성 상태, 2: 비활성 상태
    int lifePower; // 생명력
    int time; // 비활성 상태: 생성됐을 때의 시간, 활성 상태: 생성 이후 경과 시간
 
    Cell() {
        state = 2;
        lifePower = 0;
        time = 1;
    }
}
 
public class Solution {
    static final int MAX_SIZE = 352;
    static final int[] dx = { -1, 1, 0, 0 };
    static final int[] dy = { 0, 0, -1, 1 };
 
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
 
        for (int test_case = 1; test_case <= T; ++test_case) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int minX = (MAX_SIZE / 2) - (n / 2), minY = (MAX_SIZE / 2) - (m / 2);
            Cell[][] board = new Cell[MAX_SIZE][MAX_SIZE];
            int[][] delta = new int[MAX_SIZE][MAX_SIZE];
 
            for (int i = 0; i < n; ++i) {
                st = new StringTokenizer(br.readLine());
                int x = minX + i;
 
                for (int j = 0; j < m; ++j) {
                    int y = minY + j;
                    int lifePower = Integer.parseInt(st.nextToken());
 
                    if (lifePower == 0) {
                        continue;
                    }
 
                    board[x][y] = new Cell();
                    board[x][y].lifePower = lifePower;
                }
            }
 
            for (int i = 0; i < k; ++i) {
                simulate(board, delta, dx, dy);
 
                //              System.out.println("#" + i + 1);
                //
                //              for (int x = 0; x < MAX_SIZE; ++x) {
                //                  for (int y = 0; y < MAX_SIZE; ++y) {
                //                      if (board[x][y] == null) {
                //                          System.out.print(".");
                //                      } else if (board[x][y].state == 0) {
                //                          System.out.print(0);
                //                      } else {
                //                          System.out.print(board[x][y].lifePower);
                //                      }
                //                  }
                //
                //                  System.out.println();
                //              }
                //
                //              System.out.println();
            }
 
            sb.append(String.format("#%d %d\n", test_case, getCount(board)));
        }
 
        System.out.println(sb.toString());
 
    }
 
    public static void simulate(Cell[][] board, int[][] delta, int[] dx, int[] dy) {
        // 두 개 이상의 줄기 세포가 하나의 그리드 셀에 동시 번식하려고 하는 경우 더 높은 생명력 수치를 계산한다.
        for (int i = 0; i < MAX_SIZE; ++i) {
            for (int j = 0; j < MAX_SIZE; ++j) {
                delta[i][j] = 0;
            }
        }
 
        for (int i = 0; i < MAX_SIZE; ++i) {
            for (int j = 0; j < MAX_SIZE; ++j) {
                if (board[i][j] == null) {
                    continue;
                }
 
                if (board[i][j].state != 1) {
                    continue;
                }
 
                for (int d = 0; d < 4; ++d) {
                    int ni = i + dx[d];
                    int nj = j + dy[d];
 
                    if (board[ni][nj] != null) {
                        continue;
                    }
 
                    delta[ni][nj] = Math.max(delta[ni][nj], board[i][j].lifePower);
                }
            }
        }
 
        for (int i = 0; i < MAX_SIZE; ++i) {
            for (int j = 0; j < MAX_SIZE; ++j) {
 
                if (board[i][j] == null) {
                    if (delta[i][j] > 0) {
                        board[i][j] = new Cell();
                        board[i][j].lifePower = delta[i][j];
                    }
                } else {
                    switch (board[i][j].state) {
                    case 1:
                        if (board[i][j].time == board[i][j].lifePower) {
                            board[i][j].state = 0;
                        } else {
                            ++board[i][j].time;
                        }
                        break;
                    case 2:
                        if (board[i][j].time == board[i][j].lifePower) {
                            board[i][j].state = 1;
                            board[i][j].time = 1;
                        } else {
                            ++board[i][j].time;
                        }
                        break;
                    }
                }
            }
        }
    }
 
    private static int getCount(Cell[][] board) {
        int ret = 0;
 
        for (int i = 0; i < MAX_SIZE; ++i) {
            for (int j = 0; j < MAX_SIZE; ++j) {
                if (board[i][j] == null) {
                    continue;
                }
 
                if (board[i][j].state == 0) {
                    continue;
                }
 
                ++ret;
            }
        }
 
        return ret;
    }
}
