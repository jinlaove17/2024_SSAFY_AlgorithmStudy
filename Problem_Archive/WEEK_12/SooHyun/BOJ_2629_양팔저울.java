/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 전형적인 백트래킹 문제인데, 중요 포인트는 메모리 초과 혹은 시간 초과를 일어나지 않게 하는 것이다.
	- 초기에 해당 추를 같은 방향, 반대 방향, 사용하지 않은 3가지 경우에 대해서 재귀 호출을 통해서 호출을 했다.
	- 그 경우 3^N 만큼 시간복잡도가 생기는데 N의 경우 500의 경우이므로 최악의 경우 3^500이 발생해 시간초과 혹은 메모리 초과가 발생했다.
	- 이를 해결하고자 이차원 배열을 사용해 같은 무게가 존재할 수 있는 경우 같은 갯수로 같은 무게가 나오면 뒤는 확인할 필요가 없어 기저 조건을 설정했습니다.
	- 여기서 possible이라는 2차원 배열을 두어서 n개의 조합으로 나올 수 있는 무게를 true로 설정했습니다.
	- 이후 depth == N인 경우 종료하는 기저 조건을 추가해 문제를 해껼했습니다.

시간 복잡도
	- 백트래킹 함수 dfs() 호출: 추를 같은 쪽에 위치, 반대 쪽에 위치, 사용하지 않은 3가지 경우의 수가 존재하고 N개의 추가 존재하므로 O(3 ^ N)만큼의 시간복잡도로 보입니다.
	- 그러나 possible 배열을 통해 같은 갯수에서 같은 무게인 경우 종료하는 기저 조건에 의해 3 ^ N 모두 확인할 필요가 없어진다.


실행 시간
	- 108ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] weight;
    static int M;
    static int[] check;
    static boolean[][] possible;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        weight = new int[N];
        possible = new boolean[31][40001];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<N; i++) {
            weight[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0,0);
        st = new StringTokenizer(br.readLine().trim());
        M = Integer.parseInt(st.nextToken());
        check = new int[M];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<M; i++) {
            check[i] = Integer.parseInt(st.nextToken());
            if(possible[N][check[i]]) { // check[i] 무게가 확인할 수 있는 경우 이차원 배열의 값이 true일 것이므로
                sb.append("Y ");
            }else {
                sb.append("N ");
            }
        }
        System.out.println(sb.toString().trim());
    }
    public static void dfs(int depth, int sum) {
        if(sum > 500*30 || possible[depth][sum]) // 추가 가장 많이 주어지고 모든 추가 최대 무게일 때 500*30인데 확인하는 추의 경우 40000이라고 해서 500*30의 경우에는 어차피 확인 못하고, 조합이 어떻든 해당 무게를 만든 경우 굳이 확인할 필요 없어서 return
            return;
        possible[depth][sum]=true; // 현재까지 무게를 확인한다
        if(depth == N) {
            return;
        }
        dfs(depth+1, sum+weight[depth]); // depth에 해당하는 추를 같은 쪽에 놓은 경우

        dfs(depth+1, sum); // depth에 해당하는 추를 사용하지 않은 경우

        dfs(depth+1, Math.abs(sum-weight[depth]));// depth에 해당하는 추를 반대 쪽에 놓은 경우
    }
}
