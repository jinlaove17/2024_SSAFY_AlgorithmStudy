/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 네 최대 유량 알고리즘, 에드몬드-카프 알고리즘 ㅎㅎ... 초면입니다. 안녕하세요
    - 사실 문제를 풀다가 도저히 안풀려서 밑에 문제 유형을 보고 최대 유량 알고리즘을 찾아서 본 이후에 풀었습니다.
    - 어디서 어디로 흘렀는지를 알기 위해서 board라는 2차원 배열을 사용했고 parent 배열을 사용해 해당 노드 이전에 어디를 거쳐서 왔는지를 저장합니다.
    - 이후 BFS를 돕니다. 종료 조건으로 que가 비거나 현 위치가 END 즉 'Z'에 도달하는 경우로 놓습니다. 그리고 board[now][i]가 0보다 크다 즉 특정 지점에서 다음 지점으로 갈 수 있고 parent[i]가 -1 즉 아직 방문하지 않은 위치인 경우 que에 넣고 parent를 갱신해줍니다.
    - BFS가 끝나면 parent[END]를 체크하는데 만약 해당 값이 -1인 경우에는 이전에 들린 노드가 없다 즉 끝까지 도달하지 않은 경우이기에 break 해줍니다.
    - 그게 아니면 calculate함수를 실행하는데 역추적 개념입니다.
    - END에서 START까지 parent를 따라가면서 유량이 흐를 수 있는 최소 값을 찾습니다.
    - 다시 END에서 START까지 parent를 따라가면서 정방향 간선의 전여 용량을 줄입니다. 이는 유량을 해당 간선만큼 사용했다면 그에 그 만큼 덜 보낼 수 있다는 것입니다.
    - 역뱡향 간선 board[now][parent[now]]의 값을 늘려줍니다. 이는 유량을 사용했으니 그 만큼 다시 보낼 수 있다는 것입니다. 즉 나중에 경로를 다시 변경할 수 있는 여지를 마련하는 유량 보존의 원칙을 구현합니다.
    - 이 과정을 더 이상 경로가 없을 때까지 반복해서 결국 A에서 Z로 흘릴 수 있는 전체 유량의 최대치를 구하게 됩니다.

시간 복잡도
    - BFS : O(E) (간선의 갯수)
    - 증가 경로 최대 횟수 : O(V*E) (정점의 개수 * 간선의 개수)
    - 전체 시간복잡도 : O(V*E*E)

실행 시간
   - 112ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] board;
    static int[] parent;
    final static int START = 0;
    final static int END ='Z' - 'A';
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        //System.out.println(END);
        board = new int[2*(END+1)][2*(END+1)];
        parent = new int[2*(END+1)];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int start = charToInt(st.nextToken().charAt(0));
            int end = charToInt(st.nextToken().charAt(0));
            int cost = Integer.parseInt(st.nextToken());
            board[start][end]+=cost;
            board[end][start]+=cost;
        }
        int answer =0;
        while(true) {
            Arrays.fill(parent, -1);
            bfs(START, END);
            if(parent[END] == -1)
                break;
            answer += calculate();
        }
        System.out.println(answer);
    }

    public static void bfs(int start, int end) {
        Queue<Integer> que = new LinkedList<>();
        que.add(start);
        parent[start]= start;
        while(!que.isEmpty()) {
            int now = que.poll();
            if(now == end)
                break;
            for(int i=0; i<board[now].length; i++) {
                if(board[now][i] >0 && parent[i] ==-1) {
                    que.add(i);
                    parent[i] = now;
                }
            }
        }
    }

    public static int calculate() {
        int result = Integer.MAX_VALUE;
        int now = END;
        while(now != START) {
            result = Math.min(result, board[parent[now]][now]);
            now = parent[now];
        }
        now = END;
        while(now !=START) {
            board[parent[now]][now] -= result;
            board[now][parent[now]] += result;
            now = parent[now];
        }
        //System.out.println(result);
        return result;
    }
    public static int charToInt(char ch) {
        if(ch >='A' && ch <='Z')
            return ch - 'A';
        else if(ch >='a' && ch<='z')
            return ch - 'a'+26;
        return -1;
    }
}