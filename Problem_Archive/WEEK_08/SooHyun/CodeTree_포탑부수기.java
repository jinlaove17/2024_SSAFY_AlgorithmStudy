import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static int[] boundary_x = {0, 1, 0, -1, 1, 1, -1, -1};
    static int[] boundary_y = {1, 0, -1, 0, 1, -1, 1, -1};
    static int N, M, K;
    static PriorityQueue<Info> que1 = new PriorityQueue<>(); // 공격자 선정을 위한 우선순위 큐
    static PriorityQueue<Info> que2 = new PriorityQueue<>(Collections.reverseOrder()); // 공격 대상 선정을 위한 우선순위 큐
    static int[][] board; // 현재 필드의 power 저장을 위한 배열
    static boolean[][] visit; // 공격자 -> 타겟까지 레이저 공격 시 방문 체크 위한 배열
    static boolean[][] is_attack; // 공격자 -> 타겟까지 레이저 공격 시 경로에 포함되었는지 유무 위한 배열
    static boolean possible; // 레이저 공격이 가능한지 확인 위한 flag 역할
    static int[][] times; // 공격 시간 저장을 위한 배열
    static Set<Point> attackedTowers = new HashSet<>(); // 레이저 공격 시 그 사이 경로 값 저장을 위한 set
    static Info attacker; // 공격자
    static Info target; // 타겟
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        board = new int[N][M];
        visit = new boolean[N][M];
        is_attack = new boolean[N][M];
        times = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int j = 0; j < M; j++) {
                int attack = Integer.parseInt(st.nextToken());
                board[i][j] = attack;
                if (attack != 0) { // 0이 아니면 공격자와 타켓 중 하나가 될 가능이 존재
                    que1.add(new Info(i, j, attack));
                    que2.add(new Info(i, j, attack));
                }
            }
        }

        for (int i = 1; i <= K; i++) {
            if(que1.size()==1) // 주어진 종료 조건
                break;
            possible = false;
            for(int j=0; j<N; j++) {
                Arrays.fill(visit[j], false);
                Arrays.fill(is_attack[j], false);
            }

            attacker = que1.poll(); // 공격자 선정
            target = que2.poll(); // 타겟 선정
            attackedTowers.clear();
            attacker.attack += (N + M);
            board[attacker.x][attacker.y]+=(N+M);
            visit[attacker.x][attacker.y] = true;

            laser_attack(attacker, target);

            if (!possible) {// 레이저 공격 불가 시 폭탄 공격
                attackedTowers.clear();
                bomb_attack(attacker, target);
            }

            repair_towers(); // 공격받지 않는 것 수리
            visit[attacker.x][attacker.y] = false;
            times[attacker.x][attacker.y]=i;

            // 현 코드의 핵심
            // 한 번의 공격 이후 모든 큐들을 초기화 시키고
            // 만들어 놓은 board, times의 값을 토대로 다시 큐에 새로운 값들을 삽입함으로써
            // K번의 반복동안 좀 더 직관적으로 공격자, 타겟 선정 가능
            que1.clear();
            que2.clear();
            int attack_x=attacker.x;
            int attack_y=attacker.y;
            for(int x=0; x<N; x++) {
                for(int y=0; y<M; y++) {

                    int attack = board[x][y];
                    Info info = new Info(x,y,attack);
                    info.time=times[x][y];
                    //System.out.print(board[x][y]+" : "+times[x][y]+"\t");
                    if(board[x][y]==0)
                        continue;
                    que1.add(info);
                    que2.add(info);

                }

            }
        }

        System.out.println(que2.peek().attack);// 타겟 중에서 가장 앞이 power가 가장 크므로 답 도출
    }

    static void laser_attack(Info attacker, Info target) {
        // 공격자 -> 타겟까지의 최단 경로를 탐색하기 위해 BFS
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Integer> distanceMap = new HashMap<>(); // 공격자로부터 거리 저장 위한 map
        Map<Point, Point> prevMap = new HashMap<>(); // 레이저 공격 가능 시 역추적을 위한 map

        Point startPoint = new Point(attacker.x, attacker.y);
        Point targetPoint = new Point(target.x, target.y);

        queue.add(startPoint);
        distanceMap.put(startPoint, 0);
        prevMap.put(startPoint, null);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int current_X = current.x;
            int current_Y = current.y;
            int distance = distanceMap.get(current);

            if (current.equals(targetPoint)) { // 레이저 공격이 가능한 경우
                possible = true;
                traceBackPath(current, prevMap); // 역추적
                applyLaserDamage(attacker, target); // 레이저 공격
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nx = current_X + dx[i];
                int ny = current_Y + dy[i];

                if (nx < 0) nx = N - 1;
                else if (nx >= N) nx = 0;
                if (ny < 0) ny = M - 1;
                else if (ny >= M) ny = 0;
                Point nextPoint = new Point(nx, ny);

                if (visit[nx][ny] || board[nx][ny] == 0) continue;

                visit[nx][ny] = true;
                distanceMap.put(nextPoint, distance + 1); // 해당 Point까지의 거리 저장
                prevMap.put(nextPoint, current); // 전의 Point 값 저장
                queue.add(nextPoint);
            }
        }
    }

    static void traceBackPath(Point target, Map<Point, Point> prevMap) {
        // 공격자 -> 타겟까지 가는데 중간에 존재하는 경로를 저장하기 위한 함수
        Point current = target;
        while (prevMap.get(current) != null) {
            Point prev = prevMap.get(current);

            if(prevMap.get(prev)==null)
                break;
            attackedTowers.add(prev);//중간의 경로 값들을 저장

            current = prev;
        }
    }

    static void applyLaserDamage(Info attacker, Info target) {
        int damage = attacker.attack;
        board[target.x][target.y] -= damage;
        is_attack[target.x][target.y]=true;
        if (board[target.x][target.y] <= 0) board[target.x][target.y] = 0;

        for (Point p : attackedTowers) {
            is_attack[p.x][p.y]=true;
            board[p.x][p.y] -= (damage / 2);
            if (board[p.x][p.y] <= 0) board[p.x][p.y] = 0;
        }
    }

    static void bomb_attack(Info attacker, Info target) {
        int damage = attacker.attack;
        board[target.x][target.y] -= damage;
        if (board[target.x][target.y] <= 0) board[target.x][target.y] = 0;
        attackedTowers.add(new Point(target.x, target.y));

        for (int i = 0; i < 8; i++) {
            int nx = target.x + boundary_x[i];
            int ny = target.y + boundary_y[i];

            if (nx < 0) nx = N - 1;
            else if (nx >= N) nx = 0;
            if (ny < 0) ny = M - 1;
            else if (ny >= M) ny = 0;
            if(nx == attacker.x && ny == attacker.y)
                continue;
            if (board[nx][ny] == 0) continue;
            board[nx][ny] -= (damage / 2);
            is_attack[nx][ny]=true;
            if (board[nx][ny] <= 0) board[nx][ny] = 0;
            attackedTowers.add(new Point(nx, ny));
        }
    }

    static void repair_towers() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if((attacker.x==i && attacker.y==j) || (target.x==i && target.y==j))
                    continue;
                if (board[i][j] > 0 && !is_attack[i][j]) { // 아직 살아있고 공격받지 않은 것에 대해서 회복
                    board[i][j]++;
                }
            }
        }
    }

    static class Info implements Comparable<Info> {
        int x, y, attack, time;


        public Info(int x, int y, int attack) {
            this.x = x;
            this.y = y;
            this.attack = attack;
            this.time = 0;
        }


        @Override
        public int compareTo(Info o) {
            if (this.attack == o.attack) {
                if (this.time == o.time) {
                    if ((this.x + this.y) == (o.x + o.y)) {
                        return o.y - this.y;
                    }
                    return (o.x + o.y) - (this.x + this.y);
                }
                return o.time - this.time;
            }
            return this.attack - o.attack;
        }
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

    }
}