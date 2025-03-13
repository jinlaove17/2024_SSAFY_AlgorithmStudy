/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제를 풀 때 requests의 길이가 1인 경우에서 삽질을 많이 했습니다.
    - requests가 2인 경우에는 무조건 문가만 일치하면 다 지우면됩니다.
    - check라는 이차원 배열을 선언해 삭제 여부를 확인하는 용도로 선언하고 처음에는 모든 것이 삭제되지 않은 상태이므로 true를 할당해줍니다.
    - go함수를 선언해 requests가 1인 경우 삭제하는 로직을 구현했습니다.
        1. 먼저 테두리에 있는 문자들을 확인해서 제거된 경우에 que에 넣습니다.
        2. 그리고 bfs를 수행하면서 방문되지 않고 삭제된 것에 대해서 다시 que에 넣습니다. 이 과정에서 visit를 true로 할당해줍니다.
        3. bfs 이후 제거할 문자를 처리하기 위해 2중 for문을 돌면서 해당 위치의 문자가 파라미터와 일치하지 않으면 넘기고 가장자리인 경우, 주변에 하나라도 visit가 true & check가 false(삭제) 경우를 확인해서 삭제 여부를 결정합니다.
        4. flag가 최종적으로 true로 반환되면 해당 위치에 것을 삭제합니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(K * M) (K는 storage 배열의 길이, M은 storge 배열의 문자열 길이)
    - BFS : O(N * M) (N은 storage 배열의 길이, M은 storge 배열의 문자열 길이)
    - 전체 시간복잡도: O(K * M + N * M) (K는 storage 배열의 길이, M은 storge 배열의 문자열 길이)

실행 시간
   - 31.71ms(java)
*/
import java.util.*;
class Solution {
    int N, M;
    char[][] board;
    boolean[][] check;
    int[] dx = {-1,0,1,0};
    int[] dy = {0,-1,0,1};
    public int solution(String[] storage, String[] requests) {
        int answer = 0;
        N = storage.length;
        M = storage[0].length();
        board = new char[N][M];
        check = new boolean[N][M];
        for(int i=0; i<N; i++){
            String str= storage[i];
            for(int j=0; j<M; j++){
                char ch = str.charAt(j);
                board[i][j] = ch;
                check[i][j] = true;
            }
        }
        for(String request : requests){
            if(request.length()==1){
                go(request.charAt(0));
            }else{
                for(int i=0; i<N; i++){
                    for(int j=0; j<M; j++){
                        if(check[i][j] && board[i][j]==request.charAt(0)){
                            check[i][j]=false;
                        }
                    }
                }
            }
        }
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                //System.out.print(check[i][j]+" ");
                if(check[i][j])
                    answer++;
            }
            System.out.println();
        }
        return answer;
    }

    public void go(char ch){
        Queue<int[]> que = new LinkedList<>();
        boolean[][] visit = new boolean[N][M];
        for(int i=0; i<N; i++){
            if(!check[i][0]){
                visit[i][0]=true;
                que.add(new int[]{i,0});
            }
            if(!check[i][M-1]){
                visit[i][M-1]=true;
                que.add(new int[]{i,M-1});
            }
        }

        for(int j=0; j<M; j++){
            if(!check[0][j]){
                visit[0][j]=true;
                que.add(new int[]{0,j});
            }
            if(!check[N-1][j]){
                visit[N-1][j]=true;
                que.add(new int[]{N-1,j});
            }
        }
        while(!que.isEmpty()){
            int[] arr = que.poll();
            int cur_x = arr[0];
            int cur_y = arr[1];
            for(int d=0; d<4; d++){
                int nx = cur_x+dx[d];
                int ny = cur_y+dy[d];
                if(!range(nx, ny))
                    continue;
                if(!check[nx][ny] && !visit[nx][ny]){
                    visit[nx][ny]=true;
                    que.add(new int[]{nx,ny});
                }
            }
        }
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(board[i][j] != ch)
                    continue;
                boolean flag=false;
                if(i==0 || i==N-1 || j==0 || j==M-1){
                    flag=true;
                }else{
                    for(int d=0; d<4; d++){
                        int nx = i+dx[d];
                        int ny = j+dy[d];
                        if(!range(nx, ny)){
                            flag=true;
                            break;
                        }
                        if(!check[nx][ny] && visit[nx][ny]){
                            flag=true;
                            break;
                        }
                    }
                }
                if(flag)
                    check[i][j]=false;
            }
        }

    }

    public boolean range(int x, int y){
        if(x <0 || x>=N || y<0 || y>=M)
            return false;
        return true;
    }
}