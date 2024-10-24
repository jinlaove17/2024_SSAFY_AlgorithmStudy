/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에서 최대 공의 갯수가 200000이였기에 모든 공에 대해 모든 경우를 탐색할 경우 200000*200000이 나와 시간 초과가 나올 것이라고 생각했습니다.
	- 문제 해결에 있어 핵심 로직은 각각의 공을 탐색할 때 자기 보다 작은 경우에 대해서 공의 구분 없이 누적합을 구하고 각각의 색깔에 대해서 또한 누적합을 구합니다.
	- 해당 공보다 커지는 경우에 반복문을 빠져나와 공의 구분 없는 누적합에서 자신의 색깔에 대한 누적합을 뺴주는 방식을 채택했습니다.
	- 그 결과 한 번의 반복문을 통해 결과를 도출할 수 있었습니다.

시간 복잡도
	- 누적합 구하기 : 각각의 공에 대해 함수를 실행하고 내부에서 while문을 또 돌아가므로 최악의 경우 O(N*N)이라고 생각되지만 누적합이므로 이미 탐색이 끝난 공에 대해서 탐색을 진행하지 않고 그 다음부터 탐색을 진행하므로 O(N*N)의 시간복잡도 문제를 해결했습니다.

실행 시간
	- 2064ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static Ball[] balls;
    static int[] result;
    static int[] color;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        balls = new Ball[N];
        result = new int[N];
        color = new int[N+1];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            balls[i] = new Ball(i,c,s);
        }
        Arrays.sort(balls,(o1,o2)->{
            return o1.size-o2.size;
        });
        int idx=0;
        int sum=0;
        for(int i=0; i<N; i++) {
            Ball ball = balls[i];
            while(balls[idx].size < ball.size) {
                sum+=balls[idx].size;
                color[balls[idx].color]+=balls[idx].size;
                idx++;
            }
            result[ball.idx] = sum-color[ball.color];
        }
        for(int i=0; i<result.length; i++) {
            System.out.println(result[i]);
        }
    }
    static class Ball{
        int idx;
        int color;
        int size;

        public Ball(int idx, int color, int size) {
            this.idx=idx;
            this.color=color;
            this.size=size;
        }
    }
}
