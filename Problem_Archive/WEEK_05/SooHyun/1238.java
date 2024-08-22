import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
    static final int T=10;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for(int t=1; t<=T; t++) {
            boolean[] visit = new boolean[101];
            boolean[][] contact = new boolean[101][101];
            st = new StringTokenizer(br.readLine().trim());
            int len = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine().trim());
            for(int i=0; i<len/2; i++) {
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                contact[x][y]=true;
            }
            ArrayList<Contact> list = new ArrayList<>();
            Queue<Contact> que = new LinkedList<>();
            que.add(new Contact(start,0));
            visit[start]=true;
            list.add(new Contact(start,0));
            while(!que.isEmpty()) {
                Contact c = que.poll();
                for(int i=1; i<=100; i++) {
                    if(contact[c.num][i] && !visit[i]) {
                        que.add(new Contact(i,c.index+1));
                        visit[i]=true;
                        list.add(new Contact(i,c.index+1));
                    }
                }
            }
            Collections.sort(list);
            sb.append("#"+t+" "+list.get(0).num+"\n");
        }
        System.out.println(sb.toString());
    }

    public static class Contact implements Comparable<Contact>{
        int num;
        int index;

        public Contact(int num, int index) {
            this.num=num;
            this.index=index;
        }

        @Override
        public int compareTo(Contact o) {
            // TODO Auto-generated method stub
            if(this.index==o.index) {
                return o.num-this.num;
            }
            return o.index-this.index;
        }
    }

}