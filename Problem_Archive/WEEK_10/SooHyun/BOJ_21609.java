/*
문제 접근 아이디어 및 알고리즘 :
	- 전형적인 구현문제입니다.
	- 블록을 표현하기 위해 Info라는 클래스를 사용하였고 기준 행, 열을 해당 그룹에서 왼쪽 최상단의 좌표 값으로 설정했습니다.
	- Comparable을 통해 정렬 조건을 명시했습니다.
	- 블록 그룹을 구하기 위해 bfs를 사용했고 total_count,rainbow_count ,total_list , rainbow_list 라는 변수를 두어서 rainbow_~에는 무지개색 블록에 대한 것을 따로 관리했습니다.
	- 이는 모든 블록의 종류는 무지개 색을 사용할 수 있어 하나의 그룹을 구해 list에 저장하였을 때 다른 그룹 또한 무지개 색을 포함해 그룹을 만들 수 있기 때문입니다.
	- 이를 통해 최대 크기의 블록 크기를 구하기 수월하게 하였습니다.
	- 중력의 경우 이동 가능한 블록들을 모두 확인하면서 이동 조건을 만족하지 않기 전까지의 인덱스 값을 파악 후 이동한 위치와 전 위치의 값을 업데이트하는 방식을 사용했습니다.

시간 복잡도 :
	- 블록 그룹 찾기 : 각 셀에 대해 BFS를 수행하므로 최악의 경우 O(N^2)
	- 각 BFS는 최대 O(N^2) 시간
	- 결과적으로 전체 시간 복잡도는 O(N^4)

실수한 부분
    - 중력 또한 하나의 이동이라고 판단해 평소에 사용하는 Stack을 사용하고자 했습니다.
    - 일반적인 이동과 달리 어느 시점에 이동이 멈추는 것이 아닌 그것을 지나간 이후에도 이동이 가능합니다.
    - Stack을 사용할 경우 그 경우에 계속 clear를 수행하고 언제 마지막 인덱스인지 파악 또한 복잡하여 현 코드에 사용한 중력 방식을 채택했습니다.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int[][] board;
    static boolean[][] visit;
    static int result=0;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][N];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while(true) {
            List<Block> list = new ArrayList<>();
            findBlock(list);
            if(list.isEmpty())
                break;
            Collections.sort(list);
            Block block = list.get(0);
            result+=(block.total_count*block.total_count);
            for(int[] arr : block.list) {
                board[arr[0]][arr[1]]=-2;
            }
            gravity();
            rotate();
            gravity();

//			for(int i=0; i<N; i++) {
//				for(int j=0; j<N; j++) {
//					System.out.print(board[i][j]+"\t");
//				}
//				System.out.println();
//			}
//			System.out.println();
        }
        System.out.println(result);
    }

    public static void findBlock(List<Block> list) {
        visit = new boolean[N][N];
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(visit[i][j] || board[i][j] ==-1 || board[i][j] ==0 || board[i][j] ==-2)
                    continue;
                bfs(i,j,list);
            }
        }
    }

    public static void bfs(int i, int j, List<Block> list) {
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {i,j});
        visit[i][j]=true;
        int color = board[i][j];
        int rainbow_count=0;
        int total_count=1;
        List<int[]> rainbow_list = new ArrayList<>();
        List<int[]> total_list = new ArrayList<>();
        total_list.add(new int[] {i,j});
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int x = arr[0];
            int y = arr[1];
            for(int d=0; d<4; d++) {
                int nx = x+dx[d];
                int ny = y+dy[d];
                if(!range(nx,ny))
                    continue;
                if(board[nx][ny] == -1 || board[nx][ny] == -2 || visit[nx][ny])
                    continue;
                if(board[nx][ny] !=0 && board[nx][ny]!=color)
                    continue;
                visit[nx][ny]=true;
                if(board[nx][ny]==0) {
                    rainbow_count++;
                    rainbow_list.add(new int[] {nx,ny});
                }
                total_count++;
                total_list.add(new int[] {nx,ny});
                que.add(new int[] {nx,ny});
            }
        }
        if(total_list.size()<2) {
            for(int[] arr : total_list) {
                visit[arr[0]][arr[1]]=false;
            }
            return;
        }
        for(int[] arr : rainbow_list) {
            visit[arr[0]][arr[1]]=false;
        }
        Collections.sort(total_list, (o1,o2)->{
            if(o1[0]==o2[0])
                return o1[1]-o2[1];
            return o1[0]-o2[0];
        });
        int start_X=0;
        int start_Y=0;
        for(int[] arr : total_list) {
            if(board[arr[0]][arr[1]]==0)
                continue;
            start_X=arr[0];
            start_Y=arr[1];
            break;
        }
        list.add(new Block(start_X, start_Y, total_count, rainbow_count, total_list));
    }

    public static void gravity() {
        for(int j = 0; j < N; j++) {
            for(int i=N-1; i>=0; i--) {
                if(board[i][j]==-1 || board[i][j]==-2)
                    continue;
                int idx=i+1;
                while(idx < N && board[idx][j]==-2) {
                    idx++;
                }
                if(idx != i+1) {
                    board[idx-1][j]=board[i][j];
                    board[i][j]=-2;
                }
            }

        }

    }
    public static void rotate() {
        int[][] temp = new int[N][N];
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                temp[i][j] = board[j][N-1-i];
            }
        }
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                board[i][j] = temp[i][j];
            }
        }
    }
    public static boolean range(int x, int y) {
        if(x>=0 && x<N && y>=0 && y<N)
            return true;
        return false;
    }
    public static class Block implements Comparable<Block>{
        int x;
        int y;
        int total_count;
        int rainbow_count;
        List<int[]> list = new ArrayList<>();

        public Block(int x, int y, int total_count, int rainbow_count, List<int[]> list) {
            this.x=x;
            this.y=y;
            this.total_count=total_count;
            this.rainbow_count=rainbow_count;
            this.list=list;
        }

        @Override
        public int compareTo(Block o) {
            // TODO Auto-generated method stub
            if(this.total_count==o.total_count) {
                if(this.rainbow_count==o.rainbow_count) {
                    if(this.x==o.x) {
                        return o.y-this.y;
                    }
                    return o.x-this.x;
                }
                return o.rainbow_count-this.rainbow_count;
            }
            return o.total_count-this.total_count;
        }
    }
}
