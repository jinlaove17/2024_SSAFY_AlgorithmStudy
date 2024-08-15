import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int[] node = new int[V+1];
            ArrayList<ArrayList<Integer>> list = new ArrayList<>();
            for(int v=0; v<=V; v++) {
                node[v]=v;
                list.add(new ArrayList<>());
            }
            st = new StringTokenizer(br.readLine().trim());
            for(int e=0; e<E; e++) {
                int parent = Integer.parseInt(st.nextToken());
                int child = Integer.parseInt(st.nextToken());
                node[child] = parent;
                list.get(parent).add(child);
            }
            ArrayList<Integer> n1_parent = new ArrayList<>();
            int current_node = n1;
            while(current_node !=1) {
                n1_parent.add(node[current_node]);
                current_node=node[current_node];
            }
            current_node=n2;
            int common=0;
            while(true) {
                boolean flag = false;
                for(int i=0; i<n1_parent.size(); i++) {
                    if(n1_parent.get(i) == current_node) {
                        common = current_node;
                        flag=true;
                        break;
                    }
                }
                if(flag)
                    break;
                current_node=node[current_node];
            }
            int result=1;
            Queue<Integer> que = new LinkedList<>();
            que.add(common);
            while(!que.isEmpty()) {
                int n = que.poll();
                for(int i=0; i<list.get(n).size() ;i++) {
                    que.add(list.get(n).get(i));
                    result++;
                }
            }
            sb.append("#"+t+" "+common+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }
}