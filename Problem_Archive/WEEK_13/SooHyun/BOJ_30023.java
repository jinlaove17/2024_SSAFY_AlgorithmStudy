/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제 해결의 핵심 로직은 처음 전구가 각각 R, G, B인 3가지 경우를 모두 고려해서 그 뒤에 전구들을 첫 전구와 색을 맞춰 나가는 것입니다.
	- 연속한 3개의 전구의 색이 모두 바뀌는 구조이므로 전구의 갯수 -2 만큼 첫번째 전구와 같은 색깔로 맞추고 이후 모든 전구가 첫 번째 전구와 같은 경우 총 수행한 회수를 반환합니다.

시간 복잡도
	- dfs 함수 : 최악의 경우 전구의 갯수인 N이라고 생각하면 N-2 만큼의 함수를 수행해야 하므로 O(N)의 시간복잡도를 보입니다.
	- 외부 루푸 : R, G, B 3가지 경우에 대해 실행 되므로 O(3)의 시간복잡도를 보입니다.
	- 전체 시간 복잡도 : O(N * 3) => O(N)

실행 시간
	- 288ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static char[] lights;
    static int[] result = new int[3];
    static char[] colors = {'R', 'G', 'B'};
    static char[] back_up;
    static HashMap<Character, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        lights = new char[N];
        back_up = new char[N];
        init();
        String str = br.readLine().trim();
        for(int i=0; i<N; i++) {
            lights[i] = str.charAt(i);
            back_up[i] = str.charAt(i);
        }
        for(int i=0; i<3; i++) {
            char color = colors[i];
            dfs(0,0,i);
//			for(int j=0; j<N; j++) {
//				System.out.print(lights[j]+" ");
//			}
//			System.out.println();
            reset();
        }
        int answer = Integer.MAX_VALUE;
        for(int i=0; i<3; i++) {
            answer = Math.min(answer, result[i]);
        }
        if(answer == Integer.MAX_VALUE)
            answer = -1;
        System.out.println(answer);
    }

    public static void dfs(int depth, int cnt, int idx) {
        if(depth==N-2) {
            boolean flag = true;
            for(int i=0; i<N; i++) {
                if(colors[idx] != lights[i]) {
                    flag=false;
                    break;
                }
            }
            if(flag) {
                result[idx]=cnt;
            }else {
                result[idx]=Integer.MAX_VALUE;
            }
            return;
        }
        if(colors[idx] == lights[depth]) {
            dfs(depth+1, cnt, idx);
        }else {
            while(true) {
                if(colors[idx] == lights[depth])
                    break;
                for(int i=depth; i<depth+3; i++) {
                    int index = map.get(lights[i]);
                    index = (index+1)%3;
                    lights[i] = colors[index];
                }

                cnt++;

            }
            dfs(depth+1, cnt,idx);
        }
    }

    public static void init() {
        map.put('R',0);
        map.put('G',1);
        map.put('B',2);
    }

    public static void reset() {
        for(int i=0; i <N; i++) {
            lights[i] = back_up[i];
        }
    }
}
