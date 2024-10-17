/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 이 문제는 대민채의 힌트를 얻지 못했으면 풀지 못했을 것입니다.
	- Node 클래스를 선언하고 가중치를 기준으로 오름차순 정렬이 가능하게 구현했습니다.
	- 이 문제의 핵심은 Binary Search였습니다. 핵심은 간선의 가중치에 대해 **이분 탐색(Binary Search)**을 활용하여 최소 가중치와 최대 가중치 범위 내에서 최적의 답을 찾아내는 것입니다.
    - 이 문제의 해결을 위해 다익스트라 알고리즘을 사용하여 현재 탐색 중인 중간값 mid를 기준으로 그 값보다 큰 가중치의 간선을 사용할 때마다 비용을 증가시키고 그 비용이 K 이하인지 판단하는 방식으로 해결할 수 있습니다.
    - 이분 탐색으로 최적의 가중치 값을 좁혀가며 공짜 간선을 K번 이하로 사용하여 목적지까지 도달할 수 있는 최소 가중치를 찾아냅니다.
시간 복잡도
    - Binary Search: 간선의 가중치 범위를 탐색할 때 최소 가중치에서 최대 가중치까지의 범위를 이분 탐색합니다. 이분 탐색의 시간 복잡도는 O(log(max_weight))입니다. (단 max_weight는 간선의 최대 가중치)
    - 다익스트라 알고리즘: 다익스트라 알고리즘은 우선순위 큐를 사용하여 최단 경로를 탐색합니다. 다익스트라 알고리즘의 시간 복잡도는 O((N + P) * log N)입니다. (N : 노드 갯수, P : 간선 갯수, Log(N) : 우선순위 큐의 삽입 및 삭제 연산)
    - 전체 시간복잡도 : O(log(max_weight) * (N + P) * log N)

실행 시간
	- 268ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, P, K;
    static ArrayList<ArrayList<Node>> list;
    static int start =0;
    static int end = Integer.MIN_VALUE;
    static int result=-1;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        list = new ArrayList<>();
        for(int i=0; i<=N; i++) {
            list.add(new ArrayList<Node>());
        }
        for(int i=0; i<P; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            list.get(n1).add(new Node(n2, cost));
            list.get(n2).add(new Node(n1, cost));
            end = Math.max(end, cost);
        }
//		for(int i=1; i<N+1; i++) {
//			ArrayList<Node> temp = list[i];
//			System.out.println(i);
//			for(Node t : temp) {
//				System.out.println(t.to+" "+t.value);
//			}
//			System.out.println();
//		}
        while(start <= end) {
            int mid = (start+end)/2;
            if(bfs(mid)) {
                result = mid;
                end= mid-1;
            }else {
                start = mid+1;
            }
        }
        System.out.println(result);
    }
    public static boolean bfs(int mid) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] distance = new int[N+1];
        //boolean[] visit = new boolean[N+1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        pq.add(new Node(1,0));
        distance[1]=0;

        while(!pq.isEmpty()) {
            Node node = pq.poll();
            int loc = node.to;
            int cost = node.value;
            if(distance[loc] < cost)
                continue;
            for(Node n : list.get(loc)) {
                int temp = cost;
                if(n.value > mid) {
                    temp++;
                }
                if(temp < distance[n.to]) {
                    distance[n.to]=temp;
                    pq.add(new Node(n.to,distance[n.to]));
                }
            }
        }
        return distance[N]<=K;
    }
    static class Node implements Comparable<Node>{
        int to;
        int value;

        public Node(int to, int value) {
            this.to=to;
            this.value=value;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return this.value-o.value;
        }
    }
}
