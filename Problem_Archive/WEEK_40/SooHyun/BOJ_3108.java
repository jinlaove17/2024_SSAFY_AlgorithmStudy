/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제의 핵심은 사각형들이 서로 연결이 되어 있는지 확인해야되는 것이였습니다.
    - 그래서 저는 연결되지 않은 경우를 찾았습니다.
        1. 사각형 A가 B의 왼쪽, 오른쪽, 아래, 위에 있는 경우
        2. 사각형 A가 B를 품은 경우
        3. 사각형 B가 A를 품은 경우
    - 또한 거북이가 0,0에서 시작하기에 어떤 직사각형이 0,0을 지나면 원래 구한 답에서 -1을 해줘야하는데 저는 그것을 0,0,0,0을 하나의 사각형이라고 rects에 저장한 이후에 마지막에 무조건 -1을 빼주었습니다.
    - 이후 입력을 받고 bfs를 통해서 연결된 사각형들의 군집을 구하면서 정답을 도출합니다.

시간 복잡도
    - 입력 및 초기화 : O(N)
    - bfs : O(N)
    - 전체 시간복잡도 : O(N)

실행 시간
   - 176ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static Rectangle[] rects;
    static boolean[] visit;
    static Queue<Integer> que;
    static int result =0;
    static int N;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        que = new LinkedList<>();
        N = Integer.parseInt(st.nextToken());
        rects = new Rectangle[N+1];
        visit = new boolean[N+1];
        rects[0] = new Rectangle(0, 0, 0, 0);
        for(int i=1; i<N+1; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            rects[i] = new Rectangle(x1, y1, x2, y2);
        }

        for(int i=0; i<N+1; i++) {
            if(visit[i])
                continue;
            visit[i] = true;
            que.add(i);

            while(!que.isEmpty()) {
                int cur = que.poll();

                for(int j=0; j<N+1; j++) {
                    if(cur ==j || !check(cur, j) || visit[j])
                        continue;
                    visit[j] = true;
                    que.add(j);
                }
            }
            result++;
        }
        System.out.println(result-1);
    }

    public static boolean check(int i, int j) {
        Rectangle rect1 = rects[i];
        Rectangle rect2 = rects[j];
        return range(rect1, rect2);
    }

    public static boolean range(Rectangle rect1, Rectangle rect2) {
        if((rect1.x1 < rect2.x1 && rect2.x2 < rect1.x2 && rect1.y1 < rect2.y1 && rect2.y2 < rect1.y2)
                || (rect1.x1 > rect2.x1 && rect2.x2 > rect1.x2 && rect1.y1 > rect2.y1 && rect2.y2 > rect1.y2)
                || rect1.x2 < rect2.x1 || rect1.x1 > rect2.x2 || rect1.y2 < rect2.y1 || rect1.y1 > rect2.y2) {
            return false;
        }
        return true;
    }

    public static class Rectangle {
        int x1;
        int y1;
        int x2;
        int y2;

        public Rectangle(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }
}
