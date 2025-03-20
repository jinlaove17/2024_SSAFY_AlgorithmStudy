/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제의 핵심 알고리즘은 다익스트라입니다.
    - dijkstra라는 3차원 배열을 저장해 좌표 뿐만아니라 k번째 이동에 대한 값 또한 고려하게 했습니다.
    - 처음에는 dijkstra 배열의 모든 값을 Integer.MAX_VALUE로 초기화해줍니다.
    - 이후 find 함수를 실행시킵니다. 시작점이 0, 0 이므로 시작점과 1번째 이동이므로 상,하로 이동만이 가능합니다.
    - dijkstra 배열의 좌표 + k번째 이동까지 고려해서 이미 저장되어 있는 값이 더 작은 경우에는 continue를 통해 넘어갑니다.
    - 이때 방향에 따라서 이동할 수 있는 가짓수가 달라지므로 go함수에서 이동하는 방향에 대한 값도 인자로 던져주고 이동할 수 있는 경우에 대해서만 pq에 넣어줍니다.
    - 최초로 도착지에 도달하는 경우가 우선 순위 큐에 의해 최소 값이므로 값을 반환하고 그렇지 않다면 즉 목적지에 도달하지 못하면 -1을 반환합니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N * M)
    - 다익스트라 알고리즘 : O(N * M * log(N*M))
    - 전체 시간복잡도: O(N * M * log(N*M)) (N은 보드의 크기, M은 보드의 크기)

실행 시간
   - 192ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int start_X, start_Y;
    static int end_X, end_Y;
    static int[][] map;
    static int[][][] dijkstra;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static PriorityQueue<Node> pq;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine().trim());
        start_X = Integer.parseInt(st.nextToken())-1;
        start_Y = Integer.parseInt(st.nextToken())-1;
        end_X = Integer.parseInt(st.nextToken())-1;
        end_Y = Integer.parseInt(st.nextToken())-1;
        map = new int[N][M];
        dijkstra = new int[N][M][3];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for(int j=0; j<M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                Arrays.fill(dijkstra[i][j], Integer.MAX_VALUE);
            }
        }
        int result = find();
        System.out.println(result);

    }

    public static int find() {
        pq = new PriorityQueue<>();
        pq.add(new Node(start_X,start_Y,1,0));
        dijkstra[start_X][start_Y][1]=0;
        while(!pq.isEmpty()) {
            Node node = pq.poll();
            if(dijkstra[node.x][node.y][node.k%3] < node.cost)
                continue;
            if(node.x == end_X && node.y==end_Y)
                return node.cost;
            if(node.k %3 ==0) {
                for(int i=0; i<4; i++) {
                    go(i,node);
                }
            }else if(node.k % 3 ==1) {
                for(int i=0; i<2; i++) {
                    go(i,node);
                }
            }else {
                for(int i=2; i<4; i++) {
                    go(i,node);
                }
            }

        }
        return -1;
    }

    public static void go(int i, Node node) {
        int nx = node.x+dx[i];
        int ny = node.y+dy[i];
        if(!range(nx,ny))
            return;
        if(map[nx][ny]==-1)
            return;
        if(dijkstra[nx][ny][(node.k+1) %3] <=node.cost+map[nx][ny])
            return;
        dijkstra[nx][ny][(node.k+1) %3]= node.cost+map[nx][ny];
        pq.add(new Node(nx,ny,node.k+1, node.cost+map[nx][ny]));

    }
    public static boolean range(int x, int y) {
        if(x <0 || x>=N || y<0 || y>=M)
            return false;
        return true;
    }

    public static class Node implements Comparable<Node>{
        int x;
        int y;
        int k;
        int cost;

        public Node(int x, int y, int k, int cost) {
            this.x=x;
            this.y=y;
            this.k=k;
            this.cost=cost;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return this.cost-o.cost;
        }
    }
}
