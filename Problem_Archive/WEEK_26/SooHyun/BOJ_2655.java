/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제를 읽어보고 2가지 알고리즘이 떠올렸습니다. 정렬 이후의 DFS 그리고 DP였습니다. 그러나 고민했던 부분이 쌓을 수 있는 조건이 2가지 존재한 것입니다.
    - 이에 따라 DP 알고리즘을 사용하고 밑면, 무게 중의 하나의 값으로 정렬을 하고 반복문을 돌면서 나머지 하나의 값의 조건을 확인하는 DP 알고리즘을 채택했습니다.
    - 일차적으로 밑면의 값으로 정렬을 하고 이후에 무게의 값으로 정렬을 하여 DP를 진행했습니다.
    - 이후 최종적으로 가장 높은 값이 maxHeight에 저장이 되고 반복문을 역순회하면서 maxHeight = dp[i] 조건을 만족하면 해당 인덱스 번호를 stack에 저장하고 현재 최고 높이에서 i번째 직육면체의 높이를 빼주는 방식으로 구현했습니다.
    - 이런 방법을 사용할 수 있던 가장 큰 이유는 문제의 조건을 보면 밑면의 넓이와 무게가 직육면체마다 모두 다르며 이로 인해 dp 배열 안에 같은 값이 존재하지 않는다는 것이 자명했습니다.
    - 마지막으로 stack의 값을 하나씩 빼면서 최종 답을 출력했습니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N)
    - 정렬 : O(N * logN)
    - DP를 이용한 최대 높이 계산 : O(N^2)
    - 역탐색 : O(N)
    - 전체 시간복잡도: O(N + N^2 + N + N * logN) = O(N^2)

실행 시간
   - 116ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static Brick[] list;
    static int[] dp;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        list = new Brick[N+1];
        dp = new int[N+1];
        list[0] = new Brick(0,0,0,0);
        for(int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int width = Integer.parseInt(st.nextToken());
            int height = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            list[i] = new Brick(i,width,height,weight);
        }
        Arrays.sort(list, (o1, o2)->{
            return o1.width-o2.width;
        });
        int maxHeight = dp[1];

        for(int i=1; i<=N; i++) {
            for(int j=0; j<i; j++) {
                if(list[i].weight > list[j].weight) {
                    dp[i] = Math.max(dp[i], dp[j]+list[i].height);
                }
            }
            maxHeight = Math.max(maxHeight, dp[i]);
        }
        Stack<Integer> stack = new Stack<>();
        //System.out.println(maxHeight);
        for(int i=N; i>=1; i--) {
            if(maxHeight == dp[i]) {
                stack.add(list[i].idx);
                maxHeight-=list[i].height;
            }
        }
        System.out.println(stack.size());
        while(!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    static class Brick implements Comparable<Brick>{
        int idx;
        int width;
        int height;
        int weight;

        public Brick(int idx, int width, int height, int weight) {
            this.idx=idx;
            this.width=width;
            this.height=height;
            this.weight=weight;
        }

        @Override
        public int compareTo(Brick o) {
            // TODO Auto-generated method stub
            return o.width-this.width;
        }
    }
}