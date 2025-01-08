/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 풀기 전에 저의 전문 무지성을 발휘했습니다. 주어진 범위에서 공격/회복의 경우 2차원 배열의 값을 업데이트해줬습니다.
	- 결과적으로 시간초과가 발생해서 포기했습니다. 이에 각각의 공격/회복 때 값을 업데이트하는 것이 아닌 다른 방법을 통해 최종적으로 값을 업데이트하는 방법이 있다고 생각했습니다.
	- 이에 제가 생각한 방식은 (r1, c1), (r2, c2)가 있을 때 보더라인 즉 (r1+1,c2), (r2, c1+1)을 활용하고자 했습니다.
	- 기존 초기 상태의 2차원 배열 이외에 N+1, M+1의 2차원 배열을 선언하고 각각의 공격/회복에 대한 정보를 저장했습니다.
	- 이에 대한 접근 방식은 다음과 같습니다. 공격의 경우 (r1,c1), (r2,c2)에 -degree를 하고 (r1,c2+1), (r2+1,c1)에 +degree를 해줍니다. 반대로 회복의 경우 (r1,c1), (r2,c2)에 +degree를 하고 (r1,c2+1), (r2+1,c1)에 -degree를 해줍니다.
	- 이렇게 모든 공격/회복의 경우 수의 대해 배열에 값을 저장해주고 가로, 세로 방향으로 최종적으로 각 칸에 대한 공격/회복에 대한 값의 누적합을 구하게 되면 마지막 상태에 대한 2차원 배열 값을 얻을 수 있습니다.
	- 이를 기존 초기값과 더함으로써 0보다 큰 경우를 카운트 해주면 됩니다.

시간 복잡도
	- 초기화 및 데이터 분류 : O(N*M) + O(L) (L : skill 배열의 길이) => O(N*M)
    - 배열 최종 상태 업데이트 : O(N*M)
    - 내구도 확인 : O(N*M)
    - 전체 시간복잡도: O(N*M)

실행 시간
    - 정확성 테스트 : 0.86ms(java)
    - 효율성 테스트 : 77.39ms(java)
*/
import java.util.*;
class Solution {
    int N,M;
    int[][] arr;
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        N = board.length;
        M = board[0].length;
        arr = new int[N+1][M+1];
        for(int[] s : skill){
            int type = s[0];
            int r1 = s[1];
            int c1 = s[2];
            int r2 = s[3];
            int c2 = s[4];
            int degree = s[5];
            if(type==1){
                arr[r1][c1]-=degree;
                arr[r1][c2+1]+=degree;
                arr[r2+1][c1]+=degree;
                arr[r2+1][c2+1]-=degree;
            }else{
                arr[r1][c1]+=degree;
                arr[r1][c2+1]-=degree;
                arr[r2+1][c1]-=degree;
                arr[r2+1][c2+1]+=degree;
            }
        }
        for(int i=0; i<N+1; i++){
            int sum=0;
            for(int j=0; j<M+1; j++){
                sum+=arr[i][j];
                arr[i][j]=sum;
            }
        }
        for(int j=0; j<M+1; j++){
            int sum=0;
            for(int i=0; i<N+1; i++){
                sum+=arr[i][j];
                arr[i][j]=sum;
            }
        }
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(board[i][j] + arr[i][j] >0){
                    answer++;
                }
            }
        }
        return answer;
    }
}