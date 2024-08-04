import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Position {
	int x;
	int y;
}

class Edge {
	int v;
	double cost;

	Edge(int v, double cost) {
		this.v = v;
		this.cost = cost;
	}
}

class Solution {
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; test_case++) {
			int N = Integer.parseInt(br.readLine());
			Position[] positions = new Position[N];
			StringTokenizer st = new StringTokenizer(br.readLine());
			List<ArrayList<Edge>> adjList = new ArrayList<ArrayList<Edge>>();

			for (int i = 0; i < N; ++i) {
				positions[i] = new Position();
				positions[i].x = Integer.parseInt(st.nextToken());
				adjList.add(new ArrayList<Edge>());
			}

			st = new StringTokenizer(br.readLine());

			for (int i = 0; i < N; ++i) {
				positions[i].y = Integer.parseInt(st.nextToken());
			}

			double e = Double.parseDouble(br.readLine());

			for (int i = 0; i < N - 1; ++i) {
				for (int j = i + 1; j < N; ++j) {
					double dist = Math.pow(positions[i].x - positions[j].x, 2) + Math.pow(positions[i].y - positions[j].y, 2);

					adjList.get(i).add(new Edge(j, dist));
					adjList.get(j).add(new Edge(i, dist));
				}
			}

			long answer = Prim(N, e, adjList);

			sb.append(String.format("#%d %d\n", test_case, answer));
		}

		System.out.println(sb);
	}

	private static long Prim(int N, double e, List<ArrayList<Edge>> adjList) {
		Queue<Edge> minHeap = new PriorityQueue<>(new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				return (int) (o1.cost - o2.cost);
			}
		});
		boolean[] isSelected = new boolean[N];
		int cnt = 0;
		double totCost = 0.0;

		// 0번 정점에서 시작
		minHeap.add(new Edge(0, 0));

		while (!minHeap.isEmpty()) {
			Edge cur = minHeap.poll();

			if (isSelected[cur.v]) {
				continue;
			}

			isSelected[cur.v] = true;
			totCost += cur.cost;
			++cnt;

			if (cnt == N) {
				break;
			}

			for (Edge edge : adjList.get(cur.v)) {
				if (isSelected[edge.v]) {
					continue;
				}

				minHeap.add(edge);
			}
		}

		return (long) (Math.round(e * totCost));
	}
}
