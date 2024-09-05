import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Solution {
    static int N;
    static long K;
    static int result;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            K = Long.parseLong(st.nextToken());

            TreeMap<Long, Integer> populations = new TreeMap<>();
            PriorityQueue<Long> conquer = new PriorityQueue<>(Collections.reverseOrder());
            result = 0;

            st = new StringTokenizer(br.readLine().trim());
            long min = Long.MAX_VALUE;
            for (int i = 0; i < N; i++) {
                long population = Long.parseLong(st.nextToken());
                min = Math.min(min, population);
                populations.put(population, populations.getOrDefault(population, 0) + 1);
            }
            if(min > K) {
                sb.append("#"+t+" -1\n");
                continue;
            }
            boolean isPossible = true;

            while (!populations.isEmpty()) {
                Long next = populations.floorKey(K);
                if (next == null) {
                    if (!conquer.isEmpty()) {
                        K += conquer.poll();
                        result++;
                    } else {
                        isPossible = false;
                        break;
                    }
                } else {
                    int count = populations.get(next);
                    if (count == 1) {
                        populations.remove(next);
                    } else {
                        populations.put(next, count - 1);
                    }
                    K -= next;
                    conquer.add(next * 2);
                }
            }

            if (!isPossible) {
                sb.append("#"+t+" -1\n");
            } else {
                sb.append("#"+t+" "+result+"\n");
            }
        }

        System.out.println(sb.toString());
    }
}