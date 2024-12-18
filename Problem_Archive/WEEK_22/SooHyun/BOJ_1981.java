/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 처음 읽었을 때 배열의 최댓값과 최솟값의 차이를 최소화하면서 출발점(0,0)에서 도착점(N-1,N-1)까지 도달하는 경로가 있는지 확인하는 문제라고 생각했습니다.
	- 배열의 숫자 중에서 특정 최솟값(start)과 최댓값(end)을 설정하고, 이 범위 안의 숫자들만 이동할 수 있는 조건에서 BFS 탐색을 통해 경로를 확인해야 한다고 판단했습니다.
	- 단순히 모든 가능한 범위를 일일이 탐색하면 시간 복잡도가 너무 커질 것이므로, 이진 탐색(binary search)을 활용하여 최적의 범위를 찾아가는 방식으로 해결했습니다.
	- 이진 탐색을 통해 **최소 차이(mid)**를 결정하고, BFS를 활용해 출발점에서 도착점까지 경로가 있는지 확인합니다.
	- BFS는 특정 최솟값(start)과 최댓값(end) 범위 내에 있는 숫자만을 이동 가능하게 하며, 방문 여부를 체크하여 최적의 경로를 탐색합니다.

시간 복잡도
	- 이진 탐색: : O(log(max_num - min_num))
	- BFS 탐색 : O(N^2)
    - 전체 시간복잡도: O((log(max_num - min_num)) * N^2)

실행 시간
	- 480ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] board;
    static int min_num=Integer.MAX_VALUE;
    static int max_num=Integer.MIN_VALUE;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        board = new int[N][N];

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<N; j++) {
                int num = Integer.parseInt(st.nextToken());
                board[i][j] = num;
                min_num = Math.min(min_num, num);
                max_num = Math.max(max_num, num);
            }
        }
        System.out.println(binary_search());
    }
    public static int binary_search() {
        int left = 0;
        int right = max_num-min_num;
        int result=201;
        while(left <= right) {
            int mid = (left+right)/2;
            boolean flag=false;
            for(int i=min_num; i<=max_num-mid; i++) {
                int start =i;
                int end = i+mid;
                if(board[0][0]>=start && board[0][0]<=end && bfs(start, end)) {
                    flag=true;
                    break;
                }
            }
            if(flag) {
                right=mid-1;
                result = Math.min(result, mid);
            }else {
                left = mid+1;
            }
        }
        return result;
    }

    public static boolean bfs(int start, int end) {
        Queue<int[]> que = new LinkedList<>();
        boolean[][] visit = new boolean[N][N];
        que.add(new int[] {0,0});
        visit[0][0]=true;
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int x=arr[0];
            int y=arr[1];
            if(x==N-1 && y==N-1) {
                return true;
            }
            for(int i=0; i<4; i++) {
                int nx = x+dx[i];
                int ny = y+dy[i];
                if(nx <0 || nx >=N || ny <0 || ny >=N || visit[nx][ny] || board[nx][ny] < start || board[nx][ny] > end)
                    continue;
                que.add(new int[] {nx,ny});
                visit[nx][ny]=true;
            }
        }
        return false;
    }
}
