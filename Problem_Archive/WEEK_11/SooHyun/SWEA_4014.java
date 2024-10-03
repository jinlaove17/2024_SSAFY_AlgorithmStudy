/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 코드를 작성하고 결과를 도출하면서 느낀 점이 문제 설명이 불친절하고 문제 파악을 오해하는 경우가 많을 것이라고 생각한다.
	- 문제의 핵심은 각 열과 행에 대해 활주로를 건설할 수 없는 경우가 적어도 하나 존재 시 해당 열과 행은 활주로를 건설할 수 없다고 판단하는 것이다.
	- 또한 전부 평지라도 활주로를 만들 수 있다는 것이 문제에 직접적으로 언급되지 않았고 두 번째 테스트케이스를 통해 파악했다.
	- 행과 열을 각각 탐색하는 두 개의 함수 searchRow와 searchCol을 사용해 높이를 기준으로 조건을 만족하는지 확인하게 했습니다.

시간 복잡도
	- 각 행과 열을 한 번씩 순차적으로 탐색하므로 시간 복잡도는 O(N^2)입니다. 행과 열에서 경사로 설치를 위한 칸의 확인이 필요하고 최악의 경우 해당 열과 행의 모든 칸을 확인해야하므로 O(N)만큼의 추가적인 연산이 필요합니다.
	- 결과적으로 O(N^3)의 복잡도를 가집니다.

실행 시간
	- 136ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static int N;
    static int X;
    static int result;
    static int[][] board;
    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());
            board = new int[N][N];
            result=0;
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=0; j<N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    if(i==0) {
                        searchCol(i,j);
                    }
                    if(j==0) {
                        searchRow(i,j);
                    }
                }
            }
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }

    public static void searchCol(int i, int j) {
        int len=1;
        int height = board[i][j];
        for(int r=1; r<N; r++) {
            if(board[r][j]==height) {
                len++; // 높이가 같으면 평지 길이 증가
            }else if(board[r][j] == height+1) {
                if(len>=X) {// 다음 칸이 높아질 때 평지가 충분한지 확인
                    len=1;
                    height=board[r][j]; // 높이 갱신
                }else {
                    return; // 평지가 충분하지 않으며 해당 열 활주로 설치 불가
                }
            }else if(board[r][j]==height-1) {
                if(N-r < X)// 다음 칸이 낮아질 때 남은 칸이 X 보다 작으면 활주로 설치 불가
                    return;
                else {
                    for(int k=1; k<X; k++) { // 낮아지는 칸이 X만큼 되는지 확인하는 반복문
                        if(board[++r][j] != height-1) { // 낮아지는 칸이 X 길이가 안되면 해당 열은 활주로 설치 불가
                            return;
                        }
                    }
                    height = board[r][j]; // 높이 초기화
                    len=0; // 길이 초기화
                }
            }else {
                return; // 높이가 2이상 차이나면 해당 열 활주로 설치 불가
            }
        }
        result++; // 활주로 설치 가능하면 결과값 증가
    }

    public static void searchRow(int i, int j) { // 열에 적어놓은 것을 행의 개념으로 보면 된다.
        int len=1;
        int height = board[i][j];
        for(int c=1; c<N; c++) {
            if(board[i][c]==height) {
                len++;
            }else if(board[i][c] == height+1) {
                if(len>=X) {
                    len=1;
                    height=board[i][c];
                }else {
                    return;
                }
            }else if(board[i][c]==height-1) {
                if(N-c < X)
                    return;
                else {
                    for(int k=1; k<X; k++) {
                        if(board[i][++c] != height-1) {
                            return;
                        }
                    }
                    height = board[i][c];
                    len=0;
                }
            }else {
                return;
            }
        }
        result++;
    }
}
