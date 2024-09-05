import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;
 
public class Solution {
    static int n; // n: 행성의 개수
    static long k; // k: 함선의 개수
 
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
 
        for (int tc = 1; tc <= t; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
 
            n = Integer.parseInt(st.nextToken());
            k = Long.parseLong(st.nextToken());
 
            TreeMap<Long, Integer> tm = new TreeMap<>();
 
            st = new StringTokenizer(br.readLine().trim());
 
            long totalCount = 0;
 
            for (int i = 0; i < n; ++i) {
                long count = Long.parseLong(st.nextToken());
                Integer val = tm.get(count);
 
                if (val == null) {
                    tm.put(count, 1);
                } else {
                    tm.put(count, val + 1);
                }
 
                totalCount += count;
            }
 
            int answer = 0;
 
            // 초기 함선이 없거나, 가장 주민이 적은 행성의 주민 수보다 작은 경우 종료
            if ((k == 0) || (k < tm.firstKey())) {
                answer = -1;
            }
            // 초기 함선의 수가 모든 주민의 수보다 적은 경우만 동원이 필요하다.
            else if (k < totalCount) {
                while ((totalCount > k) && (!tm.isEmpty())) {
                    // 이진 탐색 트리에서 k보다 작거나 같은 값을 찾는다.
                    Long target = tm.floorKey(k);
 
                    // 그러한 값이 없다면 종료
                    if (target == null) {
                        answer = -1;
                        break;
                    }
 
                    // 해당 행성을 침략하고 곧바로 동원한다.
                    // 이때, 침략은 매번 행성의 인원수 만큼만 침공하므로 주민은 항상 2배가 되어 target을 한 번만 더해주면 된다.
                    // k -= target;
                    // k += 2 * target;
                    k += target;
                    totalCount -= target;
                    ++answer;
 
                    int val = tm.get(target);
 
                    if (val == 1) {
                        tm.remove(target);
                    } else {
                        tm.put(target, val - 1);
                    }
                }
            }
 
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
 
        System.out.println(sb);
    }
}
