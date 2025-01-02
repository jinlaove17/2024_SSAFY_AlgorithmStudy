/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심 알고리즘은 다익스트라입니다.
	- Node class를 정의하면서 목적지 좌표와 가중치를 저장합니다.
	- 그리고 목적지 i 까지의 최단 거리를 저장하기 위한 distance 배열과 최단 거리를 구할 때 i번 째 이전에 어떤 노드를 거쳤는지를 저장하기 위한 path 배열을 선언합니다.
	- 다익스트라 알고리즘을 통해 1번 노드에서 N번 노드까지의 최단 거리를 구하고 해당 상황에서 i 번째 노드를 방문하기 전에 노드 번호를 path에 저장합니다.
	- 이렇게 될 시에 최단 거리 및 최단 거리를 구할 때 거쳐온 노드를 알 수 있게 됩니다.
	- 그리고 반복문을 1에서 N를 돌면서 path 배열 즉 최단 거리 상황에서 거쳐온 노드를 지정해 그 지점을 지나지 못하게 하는 함수를 선언합니다.
	- 해당 함수는 기존 다익스트라와 유사하나 지정한 2개의 노드인 상황에서 que에 넣지 않는 것이 핵심입니다.
	- 해당 함수를 호출해 return 값으로 distance[N]을 반환하고 이를 shortPath에 저장합니다.
	- 만약 otherPath에 저장하고자 하는 값이 Integer.MAX_VALUE라면 -1을 출력하고 그렇지 않다면 shortPath에서 otherPath를 빼서 출력합니다.

시간 복잡도
    - 초기화 및 데이터 준비 : O(N+M)
        - 리스트 초기화 : O(N)
        - 입력 처리 및 간선 저장 : O(M)
    - 다익스트라 알고리즘 : O((N+M)*logN)
    - 대체 경로 찾기 : O((N+M)*logN*N)
    - 전체 시간 복잡도 : O(N+M) + O((N+M)*logN) + O((N+M)*logN*N) = O((N+M)*logN*N)

실행 시간
	- 940ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[] distance;
    static int[] path;
    static List<Node>[] list;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        list = new ArrayList[N+1];
        distance = new int[N+1];
        path = new int[N+1];
        for(int i=0; i<=N; i++) {
            list[i] = new ArrayList<>();
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            list[a].add(new Node(b,cost));
            list[b].add(new Node(a,cost));
        }
        int shortPath = dijkstra();

        int otherPath=0;
        for(int i=1; i<=N; i++) {
            int from = path[i];
            int to = i;
            otherPath = Math.max(otherPath, findOtherPath(from, to));
        }
        if(otherPath == Integer.MAX_VALUE) {
            System.out.println(-1);
        }else {
            System.out.println(otherPath-shortPath);
        }
    }

    public static int dijkstra() {
        Arrays.fill(path,-1);
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[1]=0;
        PriorityQueue<int[]> que = new PriorityQueue<>((o1,o2)->{
            return o1[1]-o2[1];
        });
        que.add(new int[] {1,0});
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int current = arr[0];
            int cost = arr[1];
            if(cost > distance[current])
                continue;
            for(Node node : list[current]) {
                int next = node.end;
                if(distance[next] > distance[current]+node.cost) {
                    distance[next] = distance[current]+node.cost;
                    que.add(new int[] {next,distance[next]});
                    path[next]=current;
                }
            }
        }
        return distance[N];
    }

    public static int findOtherPath(int p1, int p2) {
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[1]=0;
        PriorityQueue<int[]> que = new PriorityQueue<>((o1,o2)->{
            return o1[1]-o2[1];
        });
        que.add(new int[] {1,0});
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int current = arr[0];
            int cost = arr[1];
            if(cost > distance[current])
                continue;
            for(Node node : list[current]) {
                int next = node.end;
                if(current == p1 && next == p2)
                    continue;
                if(distance[next] > distance[current]+node.cost) {
                    distance[next] = distance[current]+node.cost;
                    que.add(new int[] {next,distance[next]});
                }
            }
        }
        return distance[N];
    }

    static class Node implements Comparable<Node>{
        int end;
        int cost;

        public Node(int end, int cost) {
            this.end=end;
            this.cost=cost;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return this.cost-o.cost;
        }
    }
}
