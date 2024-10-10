/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 읽었을 때 전형적인 다익스타라 알고리즘 문제라고 판단했습니다.
	- 단 기본적인 다익스타라와 다른 점이 어떠한 선들이 연결 되었는지 출력해야하는 것이 신선했습니다.
    - ArrayList 내부에 ArrayList를 선언해 2차원 배열처럼 작동하게 했습니다. => 가 노드마다 연결이 되어 있는 간선의 갯수가 일정하지 않으므로 일반적인 배열이 아닌 ArrayList를 채택했습니다.
    - Node 클래스를 선언해서 몇 번째 노드로 가는지 그리고 그 상황에서 가중치가 얼마인지 저장합니다. 또한 정렬 조건을 가중치 오름차순으로 정렬합니다.
    - distance[n.to] > node.cost+n.cost을 통해 가중치가 작을 경우 업데이트를 진행하고 그때 도달하는 Node의 번호를 connect 배열에 저장해둡니다.
    - 최종적으로 connect가 0이 아니면 그것은 연결이 된 것을 의미하므로 출력합니다.

시간 복잡도
	- PriorityQueue와 노드 처리: 값이 add되거나 poll되는 경우 O(log V)를 가집니다.
	- 각 노드에 연결된 간선 처리: 연결된 간선을 모두 확인하며 가중치가 더 작은 경우에만 업데이트를 수행하는데 최악의 경우 모든 노드를 확인하므로 O(E)만큼 시간복잡도를 가집니다.
    - 다익스트라 알고리즘의 전체 시간 복잡도: O((V + E) * log V)

실행 시간
	- 548ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int[] distance; // 최단 거리 저장 배열
    static int[] connect; // 연결 노드 정보 저장 배열
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        distance = new int[N+1]; // 각 노드까지 최단 거리 저장 배열
        connect = new int[N+1]; // 각 노드에 도달할 때 연결 노드 저장

        Arrays.fill(distance, Integer.MAX_VALUE);//초기 세팅
        ArrayList<ArrayList<Node>> list = new ArrayList<>();
        for(int i=0; i<N+1; i++) {
            list.add(new ArrayList<>());
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            list.get(from).add(new Node(to,cost));
            list.get(to).add(new Node(from, cost));
        }
        distance[1]=0;// 시작 노드(1번)의 최단 거리를 0으로 설정
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1,0));//시작 노드부터 우선 순위 큐에 추가
        while(!pq.isEmpty()) {// 다익스트라 통해 최단 거리 계산
            Node node = pq.poll(); // 현 최단 거리가 가장 짧은 노드 추출
            if(distance[node.to] < node.cost) // 처리된 것이면 넘어감
                continue;
            for(Node n : list.get(node.to)) {
                // 새로운 경로 가중치가 기존 거리보다 짧을 때 갱신
                if(distance[n.to] > node.cost+n.cost) {
                    distance[n.to] = node.cost+n.cost;
                    connect[n.to]=node.to;
                    pq.add(new Node(n.to, distance[n.to]));
                }
            }
        }
        int count=0;
        for(int i=2; i<N+1; i++) {
            if(connect[i]==0) // connect가 0이 라는 것은 연
                continue;
            count++;
            sb.append(i+" "+connect[i]+"\n");
        }
        System.out.println(count);
        System.out.println(sb.toString());
    }
    static class Node implements Comparable<Node>{ // 간선의 정보를 저장하는 class 선언
        int to;
        int cost;

        public Node(int to, int cost) {
            this.to=to;
            this.cost=cost;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return this.cost-o.cost;
        }
    }
}
