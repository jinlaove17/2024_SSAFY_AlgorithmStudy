/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 읽고, 예시를 봤을 때 하나의 열 혹은 행에서 4개의 연속적인 1이 나오고 해당 열 혹은 행을 기준으로 양 옆에 각각 하나의 1이 존재하게 된 경우에 만들어진다고 판단을 했습니다.
	- 그렇게 했더니, 제출 후 1% 채점 시점에서 틀렸습니다. 라는 문구가 나왔습니다.
	- 문제 해결에 있어서 핵심은 정육면체 각 면에 대해 절대적인 위치입니다.
	- 처음 0에 해당하는 밑면을 1로 설정을 해 해당 위치는 전개도와 닿았다는 것을 표시했습니다.
	- 0 = 밑면, 1 = 앞면, 2 = 오른쪽 옆면, 3 = 뒷면, 4 = 왼쪽 옆면, 5 = 윗면으로 설정을 한 후에 상하좌우로 각각 회전이 되었을 때 해당 위치가 어디로 이동하는지 파악했습니다.
	- 이동한 위치를 시점에서 그 전이 어디 면인지를 저장을 한 후에 그것을 다시 dice 배열에 할당을 했습니다.
	- 이를 통해 최종적으로 dice 배열의 값을 확인해 1이 아닌 즉 닿지 않은 면이 적어도 하나 존재 시 해당 전개도는 올바른 정육면체 전개도가 아님을 도출했습니다.

시간 복잡도
	- dfs 함수 : 시작 좌표를 기준으로 4방향 탐색을 진행합니다. 최악의 경우 전개도 내의 모든 셀을 탐색하므로 O(N*N)의 시간복잡도를 가집니다.
	- changeDice, resetDice 함수 : 상태 변경 연산은 상수 시간 O(1)을 보입니다.
	- 외부 루프 : 총 3번의 수행 O(3) 즉 O(1)의 시간복잡도를 가집니다.
	- 전체 시간복잡도 : O(N*N)

실행 시간
	- 108ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    //sides : 0 = 밑면, 1 = 앞면, 2 = 오른쪽 옆면, 3 = 뒷면, 4 = 왼쪽 옆면, 5 = 윗면
    //dir : 0 = 아래, 1 = 오른쪽, 2 = 위쪽, 3 = 왼쪽
    static int[] dice;
    static boolean[][] visit;
    static final int SIDE=6;
    static final int T=3;
    static int[][] board;
    static int[] dx = {1,0,-1,0};
    static int[] dy = {0,1,0,-1};
    static int start_X=-1;
    static int start_Y=-1;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for(int t=0; t<T; t++) {
            board = new int[SIDE][SIDE];
            dice = new int[SIDE];
            visit = new boolean[SIDE][SIDE];
            for(int i=0; i<SIDE; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=0; j<SIDE; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            for(int i=0; i<SIDE; i++) {
                for(int j=0; j<SIDE; j++) {
                    if(board[i][j]==1) {
                        start_X=i;
                        start_Y=j;
                        break;
                    }
                }
            }
            dfs(start_X, start_Y);
            boolean flag = true;
            for(int i=0; i<6; i++) {
                if(dice[i]!=1) {
                    flag=false;
                    break;
                }
            }
            if(flag) {
                sb.append("yes\n");
            }else {
                sb.append("no\n");
            }
        }
        System.out.println(sb.toString());
    }

    public static void dfs(int x, int y) {
        int[] temp = new int[6];
        visit[x][y]=true;
        dice[0]=1;

        for(int d=0; d<4; d++) {
            int nx = x+dx[d];
            int ny = y+dy[d];
            if(!range(nx, ny) || visit[nx][ny])
                continue;
            if(board[nx][ny]==1) {
                changeDice(d,temp);
                dfs(nx,ny);
                resetDice(d, temp);
            }
        }

    }

    public static void changeDice(int dir,int[] temp) {
        if(dir==0) {
            down(temp);
        }else if(dir==1) {
            right(temp);
        }else if(dir==2) {
            up(temp);
        }else if(dir==3) {
            left(temp);
        }
        for(int i=0; i<SIDE; i++) {
            dice[i]=temp[i];
        }
    }

    public static void down(int[] temp) {
        temp[0]=dice[1];
        temp[1]=dice[5];
        temp[2]=dice[2];
        temp[3]=dice[0];
        temp[4]=dice[4];
        temp[5]=dice[3];
    }

    public static void right(int[] temp) {
        temp[0]=dice[2];
        temp[1]=dice[1];
        temp[2]=dice[5];
        temp[3]=dice[3];
        temp[4]=dice[0];
        temp[5]=dice[4];
    }

    public static void up(int[] temp) {
        temp[0]=dice[3];
        temp[1]=dice[0];
        temp[2]=dice[2];
        temp[3]=dice[5];
        temp[4]=dice[4];
        temp[5]=dice[1];
    }

    public static void left(int[] temp) {
        temp[0]=dice[4];
        temp[1]=dice[1];
        temp[2]=dice[0];
        temp[3]=dice[3];
        temp[4]=dice[5];
        temp[5]=dice[2];
    }
    public static void resetDice(int dir,int[] temp) {
        if(dir==0) {
            up(temp);
        }else if(dir==1) {
            left(temp);
        }else if(dir==2) {
            down(temp);
        }else if(dir==3) {
            right(temp);
        }
        for(int i=0; i<SIDE; i++) {
            dice[i]=temp[i];
        }
    }
    public static boolean range(int x, int y) {
        if(x >=0 && x <SIDE && y>=0 && y<SIDE)
            return true;
        return false;
    }

}
