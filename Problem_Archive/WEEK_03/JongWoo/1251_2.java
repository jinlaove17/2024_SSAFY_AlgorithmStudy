import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
 
class Position {
    int x;
    int y;
}
 
class Edge {
    int v;
    int u;
    double cost;
 
    Edge(int v, int u, double cost) {
        this.v = v;
        this.u = u;
        this.cost = cost;
    }
}
 
class Solution {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
 
        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());
            Position[] positions = new Position[N];
            StringTokenizer st = new StringTokenizer(br.readLine());
 
            for (int i = 0; i < N; ++i) {
                positions[i] = new Position();
                positions[i].x = Integer.parseInt(st.nextToken());
            }
 
            st = new StringTokenizer(br.readLine());
 
            for (int i = 0; i < N; ++i) {
                positions[i].y = Integer.parseInt(st.nextToken());
            }
 
            double e = Double.parseDouble(br.readLine());
            List<Edge> edges = new ArrayList<>();
 
            for (int i = 0; i < N - 1; ++i) {
                for (int j = i + 1; j < N; ++j) {
                    double dist = Math.pow(positions[i].x - positions[j].x, 2) + Math.pow(positions[i].y - positions[j].y, 2);
 
                    edges.add(new Edge(i, j, dist));
                }
            }
 
            long answer = Kruskal(N, positions, e, edges);
 
            sb.append(String.format("#%d %d\n", test_case, answer));
        }
 
        System.out.println(sb);
    }
 
    private static long Kruskal(int N, Position[] positions, double e, List<Edge> edges) {
        int[] parents = new int[N];
 
        for (int i = 0; i < N; ++i) {
            parents[i] = i;
        }
 
        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return (int) (o1.cost - o2.cost);
            }
        });
 
        double totCost = 0.0;
        int selected = 0;
 
        for (int i = 0; i < edges.size(); ++i) {
            Edge edge = edges.get(i);
            int root1 = GetRoot(edge.v, parents);
            int root2 = GetRoot(edge.u, parents);
 
            if (root1 != root2) {
                SetUnion(root1, root2, parents);
                totCost += edge.cost;
                ++selected;
 
                if (selected == N - 1) {
                    break;
                }
            }
        }
 
        return (long) Math.round(e * totCost);
    }
 
    private static void SetUnion(int v, int u, int[] parents) {
        if (v <= u) {
            parents[u] = v;
        } else {
            parents[v] = u;
        }
    }
 
    private static int GetRoot(int v, int[] parents) {
        if (v == parents[v]) {
            return v;
        }
 
        return parents[v] = GetRoot(parents[v], parents);
    }
}
