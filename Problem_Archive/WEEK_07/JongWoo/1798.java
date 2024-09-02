import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
 
class Spot {
    char type; // 'A': 공항, 'P': 관광포인트, 'H': 호텔
    int playTime; // 관광포인트일 경우, 놀이 소요시간
    int satisfaction; // 관광포인트일 경우, 만족도
}
 
public class Solution {
    static final int MAX_TIME = 540; // 하루에 이동+놀이에 소요되는 시간: 9시간(540분)
 
    static int n, m; // n: 지점 개수, m: 휴가 기간
    static int[][] adjMatrix; // 인접 행렬
    static Map<Integer, Spot> hashMap; // 각 지점의 정보 
    static boolean[] isVisited;
    static int[] path, curPath;
    static int airport, curLocation, maxSatisfaction; // 공항의 인덱스, 현재 위치, 최대 만족도
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());
 
        for (int tc = 1; tc <= t; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
 
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            adjMatrix = new int[n + 1][n + 1]; // + 1: one-base index
            hashMap = new HashMap<>();
            isVisited = new boolean[n + 1];
            path = new int[n + 1];
            curPath = new int[n + 1];
 
            for (int i = 1; i <= n; ++i) {
                hashMap.put(i, new Spot());
            }
 
            for (int i = 1; i <= n - 1; ++i) {
                st = new StringTokenizer(br.readLine().trim());
 
                for (int j = i + 1; j <= n; ++j) {
                    int cost = Integer.parseInt(st.nextToken());
 
                    adjMatrix[i][j] = adjMatrix[j][i] = cost;
                }
            }
 
            for (int i = 1; i <= n; ++i) {
                st = new StringTokenizer(br.readLine().trim());
 
                Spot spot = hashMap.get(i);
                String type = st.nextToken();
 
                spot.type = type.charAt(0);
 
                switch (type) {
                case "A": // 공항
                    airport = i;
                    break;
                case "P": // 관광포인트
                    spot.playTime = Integer.parseInt(st.nextToken());
                    spot.satisfaction = Integer.parseInt(st.nextToken());
                    break;
                }
            }
 
            curLocation = airport;
            maxSatisfaction = 0;
            dfs(0, 1, MAX_TIME, maxSatisfaction);
            sb.append("#").append(tc).append(" ").append(maxSatisfaction);
 
            if (maxSatisfaction > 0) {
                sb.append(" ");
 
                for (int i = 1; i <= n; ++i) {
                    if (path[i] == 0) {
                        break;
                    }
 
                    sb.append(path[i]).append(" ");
                }
            }
 
            sb.append("\n");
        }
 
        System.out.println(sb);
    }
 
    private static void dfs(int cnt, int day, int remainTime, int satisfaction) {
        if (day == m) {
            if (remainTime - adjMatrix[curLocation][airport] < 0) {
                return;
            }
 
            if (satisfaction > maxSatisfaction) {
                maxSatisfaction = satisfaction;
 
                for (int j = 1; j <= n; ++j) {
                    path[j] = (j <= cnt) ? curPath[j] : 0;
                }
 
                path[cnt + 1] = airport;
            }
        }
 
        Spot curSpot = hashMap.get(curLocation);
        int tmpLocation = curLocation;
 
        for (int i = 1; i <= n; ++i) {
            if ((i == curLocation) || (adjMatrix[curLocation][i] == 0) || (isVisited[i])) {
                continue;
            }
 
            Spot nextSpot = hashMap.get(i);
            int duration = adjMatrix[curLocation][i] + nextSpot.playTime;
 
            // 이동 소요 시간 + 놀이 시간이 남은 시간보다 크다면 continue
            if ((duration > remainTime) || ((duration == remainTime) && (nextSpot.type == 'P'))) {
                continue;
            }
 
            if (day < m) {
                // m일차 이전에는 공항에 갈 필요가 없다.
                switch (nextSpot.type) {
                case 'P':
                    isVisited[i] = true;
                    curLocation = i;
                    curPath[cnt + 1] = i;
                    dfs(cnt + 1, day, remainTime - duration, satisfaction + nextSpot.satisfaction);
                    isVisited[i] = false;
                    curLocation = tmpLocation;
                    break;
                case 'H':
                    curLocation = i;
                    curPath[cnt + 1] = i;
                    dfs(cnt + 1, day + 1, MAX_TIME, satisfaction);
                    curLocation = tmpLocation;
                    break;
                }
            } else {
                // m일차에는 호텔로 갈 필요가 없다.
                switch (nextSpot.type) {
                case 'P':
                    isVisited[i] = true;
                    curLocation = i;
                    curPath[cnt + 1] = i;
                    dfs(cnt + 1, day, remainTime - duration, satisfaction + nextSpot.satisfaction);
                    isVisited[i] = false;
                    curLocation = tmpLocation;
                    break;
                }
            }
        }
    }
}
