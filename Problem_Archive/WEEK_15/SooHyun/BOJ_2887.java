/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제가 전형적인 MST 알고리즘을 활용한 문제입니다. 크루스칼과 프림 중 크루스칼을 활용해 문제를 해결했습니다.
	- 행성의 인덱스 번호와 좌표 값을 저장하기 위한 Point 객체 만듭니다.
	- 그리고 행성 간의 연결을 위해 출발 행성 인덱스 번호, 도착 행성 인덱스 번호, 그리고 가중치를 저장하는 Edge 객체를 선언하고 정렬 조건을 가중치 오름차순으로 설정합니다.
	- 처음에는 x,y,z를 최소값을 찾기 위해 이중 반복문을 돌았으나 메모리 초과라는 문제가 일어났습니다.
	- 이후 메모리 초과를 해결하기 위해 x,y,z의 차이을 구하는 것을 각각의 반복문을 돌아서 list에 모두 저장하고 정렬합니다.
	- 크루스칼 알고리즘을 통해서 정답을 도출합니다.

시간 복잡도

	- 입력 처리 및 초기화 : O(N)
	- 포인트 정렬 및 엣지 리스트 생성 : O(N*logN)
	- 엣지 리스트 정렬 : O(N*logN)
	- 크루스칼 알고리즘 수행 : O(Na(N))
	- 전체 시간 복잡도 : O(N*logN)


실행 시간
	- 1644ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

class Point{
    int idx;
    int x;
    int y;
    int z;

    public Point(int idx, int x, int y, int z) {
        this.idx = idx;
        this.x = x;
        this.y = y;
        this.z = z;
    }

}

class Edge implements Comparable<Edge>{
    int from;
    int to;
    int cost;

    public Edge(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    @Override
    public int compareTo(Edge o) {
        // TODO Auto-generated method stub
        return this.cost - o.cost;
    }
}
public class Main {
    static int N;
    static int[] parent;
    static Point[] points;
    static ArrayList<Edge> list;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        parent = new int[N];
        points = new Point[N];
        list = new ArrayList<>();
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            points[i]= new Point(i, x, y, z);
            parent[i] = i;
        }
        Arrays.sort(points, (o1, o2) -> o1.x - o2.x);
        for(int i=0; i<N-1; i++) {
            int cost = Math.abs(points[i].x - points[i+1].x);
            list.add(new Edge(points[i].idx, points[i+1].idx, cost));
        }
        Arrays.sort(points, (o1, o2) -> o1.y - o2.y);
        for(int i=0; i<N-1; i++) {
            int cost = Math.abs(points[i].y - points[i+1].y);
            list.add(new Edge(points[i].idx, points[i+1].idx, cost));
        }
        Arrays.sort(points, (o1, o2) -> o1.z - o2.z);
        for(int i=0; i<N-1; i++) {
            int cost = Math.abs(points[i].z - points[i+1].z);
            list.add(new Edge(points[i].idx, points[i+1].idx, cost));
        }
        int result = 0;
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            Edge edge = list.get(i);
            if (find(edge.from) != find(edge.to)) {
                result += edge.cost;
                union(edge.from, edge.to);
            }
        }
        System.out.println(result);
    }

    public static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if(a != b) parent[b] = a;

    }

    public static int find(int a) {
        if(parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }

}
