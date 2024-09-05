import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
    static int[][] travel_time;
    static boolean[] hotel;
    static int start;
    static int[] time;
    static int[] satisfaction;
    static final int LIMIT_TIME = 540;
    static int result;
    static int N, M;
    static List<Integer> tempPath, maxSatisPath;
    static int maxSatis;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            travel_time = new int[N][N];
            hotel = new boolean[N];
            time = new int[N];
            satisfaction = new int[N];
            result = 0;
            maxSatis = Integer.MIN_VALUE;
            tempPath = new ArrayList<>();
            maxSatisPath = new ArrayList<>();

            for (int i = 0; i < N - 1; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int j = i + 1; j < N; j++) {
                    travel_time[i][j] = Integer.parseInt(st.nextToken());
                    travel_time[j][i] = travel_time[i][j];
                }
            }

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                String type = st.nextToken();
                if (type.equals("A")) {
                    start = i;
                } else if (type.equals("H")) {
                    hotel[i] = true;
                } else {
                    time[i] = Integer.parseInt(st.nextToken());
                    satisfaction[i] = Integer.parseInt(st.nextToken());
                }
            }

            search(0, start, 0, 0, new boolean[N]);

            sb.append("#"+t+" ");
            if (maxSatis == 0) {
                sb.append(0);
            } else {
                sb.append(maxSatis+" ");
                for (int idx : maxSatisPath) {
                    sb.append(idx+1+" ");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private static void search(int day, int current, int satis, int currentTime, boolean[] visited) {
        if (day == M) {
            if (satis > maxSatis) {
                maxSatis = satis;
                maxSatisPath = new ArrayList<>(tempPath);
                //maxSatisPath=tempPath;
            }
            return;
        }

        boolean flag = false;
        for (int i = 0; i < N; i++) {
            if (!visited[i] && !hotel[i] && i != start) {
                int tempTime = currentTime + travel_time[current][i] + time[i];
                if (day == M - 1) {
                    tempTime += travel_time[i][start];
                } else {
                    tempTime += nearestHotel(i);
                }

                if (tempTime > LIMIT_TIME) continue;

                flag = true;
                visited[i] = true;
                tempPath.add(i);
                search(day, i, satis + satisfaction[i], currentTime + travel_time[current][i] + time[i], visited);
                tempPath.remove(tempPath.size() - 1);
                visited[i] = false;
            }
        }

        if (!flag) {
            if (day == M - 1) {
                tempPath.add(start);
                search(day + 1, start, satis, 0, visited);
                tempPath.remove(tempPath.size() - 1);
            } else {
                for (int i = 0; i < N; i++) {
                    if (hotel[i] && currentTime + travel_time[current][i] <= LIMIT_TIME) {
                        tempPath.add(i);
                        search(day + 1, i, satis, 0, visited);
                        tempPath.remove(tempPath.size() - 1);
                    }
                }
            }
        }
    }

    private static int nearestHotel(int point) {
        int minTime = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            if (hotel[i] && travel_time[point][i] < minTime) {
                minTime = travel_time[point][i];
            }
        }
        return minTime;
    }
}