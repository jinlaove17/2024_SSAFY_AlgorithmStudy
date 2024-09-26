/*
문제 접근 아이디어 및 알고리즘 :
	- 모든 빈칸을 순회하며 4방향 탐색을 진행하고 블록을 만나거나 경계선을 만나기 전까지 진행방향을 이어나갑니다.
	- 따라서 모든 빈칸 0에 대해서 시뮬레이션을 진행하고 블랙홀 혹은 시작점 복기 전까지의 경계선 혹은 블록의 부딪히는 횟수를 카운트해서 이를 갱신합니다.

시간 복잡도 : O(N^4)
	- 모든 빈 칸(N^2)에 대해 4방 탐색을 진행하므로 최악의 경우 총 O(N^2*4), 즉 O(N^2)
	- 각 시뮬레이션에서 핀볼이 게임판을 최대 한 번씩 이동하며 이는 O(N^2)
	- => 결과적으로 O(N^4)

실수한 부분 : 방향이 정해지면 블록을 만나거나 경계선을 만나는 경우가 아닌 이상 진행 방향이 동일하게 되는데 이를 고려하지 않고 이동 후에도 지속적으로 4방향 탐색을 진행했습니다.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Solution {
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};
    static int N;
    static int[][] board;
    static HashMap<Integer, ArrayList<int[]>> wormholes;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            N = Integer.parseInt(br.readLine().trim());
            board = new int[N][N];
            wormholes = new HashMap<>();
            for(int i=6; i<=10; i++) {
                wormholes.put(i, new ArrayList<>());
            }
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=0; j<N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    if(board[i][j] >=6 && board[i][j] <=10) {
                        wormholes.get(board[i][j]).add(new int[]{i, j});
                    }
                }
            }
            int max_score = 0;
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    if(board[i][j] != 0) continue;
                    for(int d=0; d<4; d++) {
                        int score = simulate(i, j, d);
                        if(score > max_score) {
                            max_score = score;
                        }
                    }
                }
            }
            sb.append("#"+t+" "+max_score+"\n");
        }
        System.out.print(sb);
    }

    public static int simulate(int start_x, int start_y, int direction) {
        int score = 0;
        int x = start_x;
        int y = start_y;
        int dir = direction;
        while(true) {
            x += dx[dir];
            y += dy[dir];

            if(x < 0 || x >= N || y < 0 || y >= N) {
                score++;
                dir = reverseDir(dir);
                continue;
            }

            int current = board[x][y];

            if((x == start_x && y == start_y) || current == -1) {
                break;
            }

            if(current >=6 && current <=10) {
                ArrayList<int[]> positions = wormholes.get(current);

                if(positions.get(0)[0] == x && positions.get(0)[1] == y) {
                    x = positions.get(1)[0];
                    y = positions.get(1)[1];
                }
                else {
                    x = positions.get(0)[0];
                    y = positions.get(0)[1];
                }
                continue;
            }

            if(current == 0) {
                continue;
            }

            if(current >=1 && current <=5) {
                score++;
                dir = changeDir(dir,x,y);
                continue;
            }
        }
        return score;
    }

    public static int reverseDir(int d) {
        return (d + 2) % 4;
    }

    public static int changeDir(int d, int x, int y) {
        int type = board[x][y];
        int changeDir=-1;
        if(type==1) {
            if(d==0) {
                changeDir=2;
            }else if(d==1) {
                changeDir=3;
            }else if(d==2) {
                changeDir=1;
            }else {
                changeDir=0;
            }
        }else if(type==2) {
            if(d==0) {
                changeDir=1;
            }else if(d==1) {
                changeDir=3;
            }else if(d==2) {
                changeDir=0;
            }else {
                changeDir=2;
            }
        }else if(type==3) {
            if(d==0) {
                changeDir=3;
            }else if(d==1) {
                changeDir=2;
            }else if(d==2) {
                changeDir=0;
            }else {
                changeDir=1;
            }
        }else if(type==4) {
            if(d==0) {
                changeDir=2;
            }else if(d==1) {
                changeDir=0;
            }else if(d==2) {
                changeDir=3;
            }else {
                changeDir=1;
            }
        }else if(type==5) {
            if(d==0) {
                changeDir=2;
            }else if(d==1) {
                changeDir=3;
            }else if(d==2) {
                changeDir=0;
            }else {
                changeDir=1;
            }
        }
        return changeDir;
    }
}

