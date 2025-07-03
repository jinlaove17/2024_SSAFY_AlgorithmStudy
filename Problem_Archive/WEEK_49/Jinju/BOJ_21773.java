import java.io.*;
import java.util.*;

public class Main {
    public static class Process implements Comparable<Process> {
        int id;
        int remainTime;
        int priority;
        
        public Process(int id, int remainTime, int priority){
            this.id = id;
            this.remainTime = remainTime;
            this.priority = priority;
        }
        
        @Override
        public int compareTo(Process p){
            if (this.priority == p.priority) return this.id - p.id;
            return p.priority - this.priority;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        PriorityQueue<Process> pq = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            int remainTime = Integer.parseInt(st.nextToken());
            int priority = Integer.parseInt(st.nextToken());
            pq.offer(new Process(id, remainTime, priority));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < T; i++) {
            Process cur = pq.poll();
            sb.append(cur.id).append('\n');
            int leftTime = cur.remainTime - 1;
            
            if (leftTime > 0) {
                Process newProcess = new Process(cur.id, leftTime, cur.priority-1);
                pq.offer(newProcess);
            }
        }

        System.out.print(sb);
        br.close();
    }
}
