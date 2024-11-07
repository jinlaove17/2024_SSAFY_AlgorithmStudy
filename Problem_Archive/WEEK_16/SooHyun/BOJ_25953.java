/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에 무지성으로 dfs를 돌려서 풀었더니 시간초과가 발생했다.
	- 이후 생각의 전환을 이루어 현 시간에 특정 노드를 방문하는데 드는 비용과 그 전 시간의 비용+cost를 더한 값 중 더 작은 값을 저장하는 알고리즘을 활용했습니다.
	- 즉 DP를 활용해서 특정 시점과 전 시점을 비교해 값을 갱신하는 방식을 활용했습니다.
	- 이차원 배열을 선언해 수행하는 시간 * 노드의 갯수 만큼의 크기로 할당했습니다.
	- 이후 DP 알고리즘을 적용해 t시간에 노드에 도달하는데 사용되는 비용과 t-1시간에 노드에 도달하는데 사용되는 비용 + 가중치 중 최소 값을 갱신했습니다.
	- 마지막에 t 시간에 E에 도달하는데 걸리는 값을 출력해 답을 도출했습니다.

시간 복잡도
	- 초기화 : O(T * N)
	- DP 알고리즘 : O(T *(N+M))
	    - 외부 반복문은 O(T)
	    - 각 시간마다 N개 노드 탐색 그리고 M개의 간선을 확인해 비용 갱신하므로 O(N+M)
	- 전체 시간 복잡도 : O(T *(N+M))


실행 시간
	- 896ms(java)

*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.StringTokenizer;

public class Main {
    static int N,T,M;
    static int[][] board;
    //static int start, end;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[T+1][N];
        st = new StringTokenizer(br.readLine().trim());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());
        for(int i=0; i<N; i++) {
            board[0][i] = 10000001;
        }
        board[0][start]=0;
        for(int t=1; t<=T; t++) {
            for(int i=0; i<N; i++) {
                board[t][i] = board[t-1][i];
            }
            for(int i =0; i<M ;i++) {
                st = new StringTokenizer(br.readLine().trim());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                board[t][y] = Math.min(board[t][y], board[t-1][x]+cost);
                board[t][x] = Math.min(board[t][x], board[t-1][y]+cost);
            }
        }
        if(board[T][end] >=10000001) {
            board[T][end]=-1;
        }
        System.out.println(board[T][end]);
    }

}
