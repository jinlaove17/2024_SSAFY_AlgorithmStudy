import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Node implements Comparable<Node>{
    public int from;
    public int to;
    public long cost;

    public Node(int from, int to, long cost) {
        this.from=from;
        this.to=to;
        this.cost=cost;
    }

    @Override
    public int compareTo(Node o) {
        // TODO Auto-generated method stub
        return Long.compare(this.cost, o.cost);
    }
}


public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            int N = Integer.parseInt(br.readLine().trim());
            long[][] arr = new long[N][2];
            st = new StringTokenizer(br.readLine().trim());
            for(int i=0; i<N; i++) {
                arr[i][0]=Long.parseLong(st.nextToken());
            }
            st = new StringTokenizer(br.readLine().trim());
            for(int i=0; i<N; i++) {
                arr[i][1]=Long.parseLong(st.nextToken());
            }
            double E = Double.parseDouble(br.readLine());
            int[] parent = new int[N];
            for(int i=0; i<N; i++) {
                parent[i]=i;
            }
            PriorityQueue<Node> pq = new PriorityQueue<>();
            for(int i=0; i<N; i++) {
                for(int j=i+1; j<N; j++) {
                    long cost = (arr[i][0]-arr[j][0])*(arr[i][0]-arr[j][0]) + (arr[i][1]-arr[j][1])*(arr[i][1]-arr[j][1]);
                    pq.add(new Node(i,j,cost));
                }
            }
            long result=0;
            int count=0;
            while(!pq.isEmpty()) {
                Node n = pq.poll();
                if(checkParent(n.from, n.to, parent))
                    continue;
                result+=n.cost;
                count+=1;
                if(count == N-1)
                    break;
            }
            System.out.println("#"+t+" "+Math.round(result*E));
        }
    }
    public static boolean checkParent(int i, int j, int[] parent) {
        int i_parent = findParent(i,parent);
        int j_parent = findParent(j,parent);
        if(i_parent != j_parent) {
            parent[j_parent]=i_parent;
            return false;
        }
        return true;
    }
    public static int findParent(int x, int[] parent) {
        if(x == parent[x]) {
            return x;
        }
        return parent[x] = findParent(parent[x], parent);
    }
}