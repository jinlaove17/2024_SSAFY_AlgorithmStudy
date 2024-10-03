/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 활주로가 이어지도록 하는 경우를 행, 열 단위로 일일이 따져봐야 하는 시뮬레이션 문제라고 생각하여, 매번 직전까지 연속으로 같은 높이의 개수를 계산하고 높이가 차이날 때
	  현재 높이가 직전 높이보다 2이상 차이나거나 경사로의 가로 길이(x)보다 작은 경우는 continue 하였고, 그렇지 않다면 경사로를 놓았다고 하여 cnt를 다시 1로 만들어 주었다.
	  현재 높이가 직전 높이보다 작은 경우, while문을 통해 현재 높이와 같으면서 x와 같아질 때까지 탐색을 진행하여 경사로를 놓을 수 있는지 판별하였다.
	  while문을 빠져나왔을 때 다음 높이가 더 높다면 활주로를 놓을 자리가 없으니 불가능한 경우이고, 높이가 같을 때는 cnt를 0으로 그 외에의 경우에는 1로 설정하여 탐색을 이어나갔다.

시간 복잡도
	- 행, 열 단위로 조건을 만족하는지 판별해야하므로 O(N^2)의 시간 복잡도를 갖는다.

실행 시간
	- 137ms(Java)

삽질했던 내용이나 코드
	- 처음에는 왼쪽에서 오른쪽으로 오른쪽에서 왼쪽으로 두 번 탐색하여 높이가 높아지는 경우에만 직전까지 연속으로 같은 높이의 개수와 비교하여 해결할려고 했으나
	- 높이가 2 1 1 1 2이고 경사로의 가로 길이(x)가 3일 때, 두 경우 모두 성공을 반환하지만 실제로는 한쪽에 경사로를 높으면 이후에 높을 때는 겹치기 때문에 불가능한 경우가 된다.
	  처음에 이와 같은 경우를 미처 생각하지 못하고 디버깅의 늪에 빠져 나중에 발견하게 되었다.
	- 이외에도 2 1 2 (x=1) 2 1 1 2 (x=1)과 같은 문제들도 겪었던 것 같다.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static int n, x;
    static int[][] board;
 
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());
 
        for (int tc = 1; tc <= t; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
 
            n = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            board = new int[n][n];
 
            for (int r = 0; r < n; ++r) {
                st = new StringTokenizer(br.readLine().trim());
 
                for (int c = 0; c < n; ++c) {
                    board[r][c] = Integer.parseInt(st.nextToken());
                }
            }
 
            int answer = 0;
 
            for (int r = 0; r < n; ++r) {
                boolean isPossible = true;
                int cnt = 1;
 
                for (int c = 1; c < n; ++c) {
                    if (board[r][c] > board[r][c - 1]) {
                        if ((board[r][c] - board[r][c - 1] > 1) || (cnt < x)) {
                            isPossible = false;
                            break;
                        }
 
                        cnt = 1;
                    } else if (board[r][c] < board[r][c - 1]) {
                        if (board[r][c - 1] - board[r][c] > 1) {
                            isPossible = false;
                            break;
                        }
 
                        cnt = 1;
 
                        while ((cnt < x) && (c + 1 < n) && (board[r][c] == board[r][c + 1])) {
                            ++cnt;
                            ++c;
                        }
 
                        if (cnt < x) {
                            isPossible = false;
                            break;
                        } else if (c + 1 < n) {
                            if (board[r][c] < board[r][c + 1]) {
                                isPossible = false;
                                break;
                            } else if (board[r][c] == board[r][c + 1]) {
                                cnt = 0;
                            } else {
                                cnt = 1;
                            }
                        }
                    } else {
                        ++cnt;
                    }
                }
 
                if (isPossible) {
                    ++answer;
                }
            }
 
            for (int c = 0; c < n; ++c) {
                boolean isPossible = true;
                int cnt = 1;
 
                for (int r = 1; r < n; ++r) {
                    if (board[r][c] > board[r - 1][c]) {
                        if ((board[r][c] - board[r - 1][c] > 1) || (cnt < x)) {
                            isPossible = false;
                            break;
                        }
 
                        cnt = 1;
                    } else if (board[r][c] < board[r - 1][c]) {
                        if (board[r - 1][c] - board[r][c] > 1) {
                            isPossible = false;
                            break;
                        }
 
                        cnt = 1;
 
                        while ((cnt < x) && (r + 1 < n) && (board[r][c] == board[r + 1][c])) {
                            ++cnt;
                            ++r;
                        }
 
                        if (cnt < x) {
                            isPossible = false;
                            break;
                        } else if (r + 1 < n) {
                            if (board[r][c] < board[r + 1][c]) {
                                isPossible = false;
                                break;
                            } else if (board[r][c] == board[r + 1][c]) {
                                cnt = 0;
                            } else {
                                cnt = 1;
                            }
                        }
                    } else {
                        ++cnt;
                    }
                }
 
                if (isPossible) {
                    ++answer;
                }
            }
 
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
 
        System.out.println(sb);
    }
}
