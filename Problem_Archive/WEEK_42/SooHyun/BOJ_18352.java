/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 전형적인 다익스트라 알고리즘 문제입니다. Node 객체를 선언하고 도착지와 가중치를 저장합니다. 그리고 가중치로 오름차순 정렬을 가능하게 합니다.
    - 이후에는 다익스타라를 그대로 수행하면 됩니다. 방문 배열과 가중치의 최소값을 저장하기 위한 두 개의 배열을 선언하고 다익스트라 알고리즘을 짠 find함수를 수행합니다.
    - 마지막에 가중치를 저장하는 배열을 순회하면서 K와 같은 값을 list에 넣어주면서 최종적으로 답을 도출합니다.

시간 복잡도
    - 입력 처리 : O(M)
    - 다익스트라 알고리즘(find 함수) : O((N + M) * logN)
    - 전체 시간복잡도 : O((N + M) * logN)

실행 시간
   - 1164ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M, K, X;
    static int[] dijkstra;
    static boolean[] visit;
    static final int MAX = 300001;
    static List<Node>[] list;
    static List<Integer> result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        dijkstra = new int[N+1];
        visit = new boolean[N+1];
        list = new ArrayList[N+1];
        result = new ArrayList<>();
        Arrays.fill(dijkstra,MAX);
        for(int i=0; i<=N;i++){
            list[i] = new ArrayList<>();
        }
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            list[s].add(new Node(e,1));
        }
        find();
        for(int i=1; i<=N; i++){
            if(dijkstra[i] == K)
                result.add(i);
        }
        if(result.size()==0)
            System.out.println(-1);
        else{
            for(int i=0; i<result.size(); i++)
                System.out.println(result.get(i));
        }
    }

    static void find() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(X,0));
        dijkstra[X]=0;
        while(!pq.isEmpty()) {
            Node current = pq.poll();
            if(!visit[current.end])
                visit[current.end] = true;
            else continue;
            for(int i=0; i<list[current.end].size(); i++){
                Node next = list[current.end].get(i);
                if(dijkstra[next.end] > dijkstra[current.end] + next.cost) {
                    dijkstra[next.end] = dijkstra[current.end] + next.cost;
                    pq.add(new Node(next.end, dijkstra[next.end]));
                }
            }
        }
    }

    static class Node implements Comparable<Node>{
        int end;
        int cost;

        public Node(int end, int cost) {
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost-o.cost;
        }
    }
}