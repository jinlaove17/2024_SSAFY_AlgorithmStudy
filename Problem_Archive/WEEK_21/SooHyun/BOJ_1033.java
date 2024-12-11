/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 풀기 위해서는 약간의 수학적인 사고와 dfs를 통한 값 갱신 과정이 필요합니다.
	- '비율은 "a b p q"와 같은 형식이고, a번 재료의 질량을 b번 재료의 질량으로 나눈 값이 p/q라는 뜻이다.' 라는 문제의 조건을 수학적으로 풀면 a : b = c : d라는 의미이다.
	- 즉 a*d = b*c가 성립한다는 것을 알 수 있습니다.
	- int amulti = (infos[b].volume*p)/gcd; , int bmulti = (infos[a].volume*q)/gcd; 2가지 변수에 값을 할당한 과정이 위에 말한 것에 해당이됩니다.
	- 이후 dfs를 통해 각 노드에 대해 값을 갱신하고, 각 노드에 대해 연결된 노드에 대해 값을 갱신하는 과정을 거치면 됩니다.

시간 복잡도
	- 입력 처리 : O(N)
	- GCD 계산 : O(N * logA)
	- DFS : O(N^2)
    - 전체 시간 복잡도 : O(N^2 + N * logA)

실행 시간
	- 120ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static Info[] infos;
    static boolean[] visit;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        infos = new Info[N];
        for(int i=0; i<N; i++) {
            infos[i] = new Info();
        }
        for(int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            int gcd = getGcd(p, q);
            int amulti = (infos[b].volume*p)/gcd;
            int bmulti = (infos[a].volume*q)/gcd;
            visit = new boolean[N];
            gcd = getGcd(amulti, bmulti);
            dfs(a, amulti/gcd);
            dfs(b, bmulti/gcd);
            infos[a].list.add(b);
            infos[b].list.add(a);
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<N; i++) {
            sb.append(infos[i].volume+" ");
        }
        System.out.println(sb.toString().trim());
    }
    static void dfs(int node, int volume) {
        visit[node]=true;
        infos[node].volume*=volume;
        for(int n : infos[node].list) {
            if(!visit[n]) {
                dfs(n, volume);
            }
        }
    }
    static class Info{
        public int volume;
        public ArrayList<Integer> list;

        public Info() {
            this.volume=1;
            this.list = new ArrayList<>();
        }
    }
    public static int getGcd(int a, int b) {
        if(b==0)
            return a;
        else
            return getGcd(b, a % b);
    }
}
