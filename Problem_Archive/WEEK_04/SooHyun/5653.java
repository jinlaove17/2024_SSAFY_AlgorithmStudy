import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    static int DEAD = 0;
    static int ACTIVE=1;
    static int INACTIVE=2;
    static Queue<Cell> que = new LinkedList<>();

    static class Cell{
        int x;
        int y;
        int num;
        int time;
        int status;

        public Cell(int x, int y, int num) {
            this.x=x;
            this.y=y;
            this.num=num;
            this.time=num;
            this.status=INACTIVE;
        }

        public void chanage() {
            if(this.status==INACTIVE) {
                this.time-=1;
                if(this.time==0) {
                    this.status=ACTIVE;
                }
            }else if(this.status==ACTIVE) {
                this.time+=1;
                if(this.time==num) {
                    this.status=DEAD;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[][] map = new int[N+K+2][M+K+2];
            boolean[][] visit = new boolean[N+K+2][M+K+2];
            que.clear();
            for(int i=K/2+1; i<K/2+1+N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=K/2+1; j<K/2+1+M; j++) {
                    int num = Integer.parseInt(st.nextToken());
                    if(num !=0) {
                        map[i][j]=num;
                        visit[i][j]=true;
                        que.add(new Cell(i,j,num));
                    }
                }
            }
            int result = spread(K,map,visit);
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }

    public static int spread(int K, int[][] map, boolean[][] visit) {
        int cnt = K;
        while(cnt > 0) {
            int len = que.size();
            for(Cell c : que) {
                if(c.status == ACTIVE) {
                    for(int i=0; i<4; i++) {
                        int nx = c.x+dx[i];
                        int ny = c.y+dy[i];
                        if(visit[nx][ny])
                            continue;
                        if(map[nx][ny] < c.num)
                            map[nx][ny]=c.num;
                    }
                }
            }
            for(int l=0; l<len; l++) {
                Cell c = que.poll();
                if(c.status==ACTIVE) {
                    for(int i=0; i<4; i++) {
                        int nx = c.x+dx[i];
                        int ny = c.y+dy[i];
                        if(visit[nx][ny])
                            continue;
                        visit[nx][ny]=true;
                        que.add(new Cell(nx,ny,map[nx][ny]));
                    }
                }
                c.chanage();
                if(c.status==DEAD)
                    continue;
                que.add(c);
            }
            cnt--;
        }
        return que.size();
    }
}