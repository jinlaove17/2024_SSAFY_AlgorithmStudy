/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- N,M이 최대 1000이므로 완탐을 적용하게 되면 N^M이므로 시간 초과가 발생합니다.
	- 문제의 핵심은 각 학생의 점수 배열을 정렬하고 각 배열의 최소값과 최대값의 차이를 최소화하는 것입니다.
	- 입력을 받고 나서 2차원 배열 내의 모든 배열들에 대해서 정렬을 수행합니다.
	- 그리고 학생 수만큼 크기를 갖는 인덱스 배열을 선언합니다.
	- 반복문을 진행하면서 각 인덱스 배열에 있는 값을 토대로 학생들의 inddx[i]번 째 값 중 최대와 최소값을 찾아 차이를 계산합니다.
	- 이후 최소값에 해당하는 m번 째 학생의 인덱스 배열에서 1을 증가시키면서 최소값을 그 다음 큰 수로 변경합니다.
	- 위와 같은 것을 반복적으로 수행하다 index 중 하나라도 M 이상이 되면 반복문을 종료하고 결과를 도출합니다.

시간 복잡도
	- 초기화 : O(N*M)
	- 탐색 : O(N*M)
	- 전체 시간 복잡도 : O(N*M)


실행 시간
	- 2976ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[][] student;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        student = new int[N][M];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<M; j++) {
                student[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int[] index = new int[N];
        for(int i=0; i<N; i++) {
            index[i]=0;
            Arrays.sort(student[i]);
        }
        int result=Integer.MAX_VALUE;
        while(true) {
            int min_score=student[0][index[0]];
            int max_score=student[0][index[0]];
            int min_idx=0;
            for(int i=1; i<N; i++) {
                if(student[i][index[i]] < min_score) {
                    min_score=student[i][index[i]];
                    min_idx=i;
                }
                if(student[i][index[i]] > max_score) {
                    max_score=student[i][index[i]];
                }
            }
            if(max_score - min_score < result) {
                result = max_score - min_score;
            }
            index[min_idx]++;
            if(index[min_idx] >=M)
                break;
        }
        System.out.println(result);
    }

}
