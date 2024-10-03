/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 저번 코드트리 문제와 마찬가지로 빡구현이다. 따라서 특별한 알고리즘 기반이 아닌 시키는 대로 하면 풀리는 문제이다.
	- 문제에서 주어진 대로 배열을 90도 회전시키고 그룹을 나눈 후 각 그룹 간의 점수를 계산하는 과정을 반복한다. 그룹을 나누는 것은 BFS를 사용하여 구현하며 각 그룹에 대해 정보를 저장한 후 점수를 계산합니다.
	- 배열의 회전은 두 가지 방식으로 이루어지며 첫 번째는 중심을 기준으로 십자 모양의 배열을 회전시키고 두 번째는 4개의 작은 부분 배열을 각각 90도 회전시킵니다.
	- 그룹을 나누는 과정에서 BFS를 사용하여 연결된 같은 숫자의 칸을 하나의 그룹으로 묶습니다. 이를 통해 각 그룹의 크기와 숫자를 기록하고 그룹 간의 경계를 기준으로 점수를 계산합니다.
	- 점수 계산 시 각 그룹의 크기와 숫자를 기반으로 인접한 다른 그룹과의 점수를 계산하여 총합을 구합니다.

시간 복잡도
	- 그룹을 나누는 BFS는 O(N^2)의 시간 복잡도를 가지며 회전 작업은 배열 전체를 순회하므로 O(N^2)의 복잡도를 가집니다. 점수 계산도 마찬가지로 배열을 순회하므로 O(N^2)입니다.
	- 따라서 총 시간 복잡도는 O(N^2)입니다.

실행 시간
	- 126ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] board; // 주어진 보드
    static int[][] group_board; // 그룹 번호를 저장하는 배열
    static boolean[][] visit; // 방문 여부를 체크하는 배열
    static int center;// 중심 좌표 -> 회전 시 사용
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};
    static int result;
    static ArrayList<Node> group_list; // 그룹의 정보를 저장하는 리스트
    static int group_num; // 그룹의 넘버링
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        center = N/2;
        board = new int[N][N];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        result=0;
        for(int i=0; i<=3; i++) {
//			for(int r=0; r<N; r++) {
//				for(int c=0; c<N; c++) {
//					System.out.print(board[r][c]+" ");
//				}
//				System.out.println();
//			}
            if(i !=0) { // 첫 번째 반복 이후에는 보드 회전
                rotate1();
                rotate2(0, 0);
                rotate2(0, center + 1);
                rotate2(center + 1, 0);
                rotate2(center + 1, center + 1);

            }
            visit = new boolean[N][N];
            group_board = new int[N][N];
            group_list = new ArrayList<>();
            group_num=0;
            for(int r=0; r<N; r++) {
                for(int c=0; c<N; c++) {
                    if(visit[r][c]) {
                        continue;
                    }
                    group_num++;
                    int cnt = grouping(r, c); // 그룹 크기 계산
                    group_list.add(new Node(r,c,cnt,board[r][c]));
                    //		System.out.println(board[r][c]+" "+cnt);
                }
            }
//			System.out.println(calculate());
//			System.out.println();
            result+=calculate();
        }
        System.out.println(result);
    }

    public static int grouping(int r, int c) {// BFS를 사용하여 그룹 구하기
        int cnt=1;
        Queue<int[]> que = new LinkedList<>();
        int num = board[r][c]; // 기준 숫자 => 4방 탐색 시 같은 숫자이면 같은 그룹
        group_board[r][c]= group_num; // 그룹 넘버링
        visit[r][c]=true;
        que.add(new int[] {r,c});

        // BFS로 같은 숫자의 그룹을 탐색
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int x = arr[0];
            int y = arr[1];
            for(int i=0; i<4; i++) {
                int nx = x+dx[i];
                int ny = y+dy[i];
                if(!range(nx,ny))
                    continue;
                if(visit[nx][ny] || board[nx][ny] !=num)
                    continue;
                visit[nx][ny]=true;
                group_board[nx][ny]=group_num;
                cnt++;
                que.add(new int[] {nx,ny});
            }
        }
        return cnt; // 그룹의 크기 반환
    }

    public static int calculate() {
        int score=0;
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                for(int d=0; d<4; d++) {
                    int nx = i+dx[d];
                    int ny = j+dy[d];
                    if(!range(nx,ny))
                        continue;
                    if(group_board[i][j] == group_board[nx][ny])// 같은 그룹이면 경계선이 아님
                        continue;

                    // 경계선이 맞으면 그룹 정보 가져오기
                    int group1 = group_board[i][j];
                    int group2 = group_board[nx][ny];
                    int cnt1 = group_list.get(group1-1).count;
                    int cnt2 = group_list.get(group2-1).count;
                    int num1 = group_list.get(group1-1).num;
                    int num2 = group_list.get(group2-1).num;
                    score+=(cnt1+cnt2)*num1*num2;
                    // ** 여기서 그룹 간의 경계선을 따로 구하지 않았는데 4방 탐색을 통해 그룹 A와 그룹 B가 n번 닿으면 해당로직에 대해서 n번의 계산이 발생하므로 따로 경계선의 갯수를 카운트하지않아도 됩니다.!!!
                }
            }
        }
        return score/2; // 중복 계산 방지를 위해 나누기 2 => 위의 반복문에서 예를 들어 그룹1과 4를 계산하고 또 그룹 4와 그룹 1을 계산하므로 중복이 발생 => 이를 해결하고자 나누기 2
    }

    public static void rotate1() {
        int[][] temp = new int[N][N];
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                temp[i][j] = board[i][j];
            }
        }

        for(int j=0; j<N; j++) {
            board[center][j] = temp[j][center];
        }

        for(int i=0; i<N; i++) {
            board[N-1-i][center] = temp[center][i];
        }
    }

    public static void rotate2(int x, int y) {
        int[][] temp = new int[center][center];

        for (int i = 0; i < center; i++) {
            for (int j = 0; j < center; j++) {
                temp[i][j] = board[y + i][x + j];
            }
        }

        for (int i = 0; i < center; i++) {
            for (int j = 0; j < center; j++) {
                board[y + j][x + center - 1 - i] = temp[i][j];
            }
        }
    }

    public static boolean range(int x, int y) {
        if(x >=0 && x<N && y>=0 && y<N)
            return true;
        return false;
    }
    static class Node{
        int x;
        int y;
        int count;
        int num;

        public Node(int x, int y, int count, int num) {
            this.x=x;
            this.y=y;
            this.count=count;
            this.num=num;
        }
    }
}
