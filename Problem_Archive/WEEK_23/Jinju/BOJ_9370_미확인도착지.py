import sys
input = sys.stdin.readline
INF = 2000001

# 문제 접근
# 특정 간선 (g와 h 사이) 를 지나는 최단거리 + 목적지 후보군이 주어졌다.
# 기존 그래프 최단거리에서 두 가지 조건이 붙었다.
# 처음에 생각해볼 수 있는 나이브한 방법은 가능한 경우를 구하면서 주어진 두 조건을 만족하는지 보는 것인데,
# 범위가 꽤 크기 때문에 불가능한 방법이다.
# 우리가 따져봐야 하는 경우를 자세히 살펴보면
#   1. 시작점 -> G -> H -> 목적지 후보군
#   2. 시작점 -> H -> G -> 목적지 후보군
# 이렇게 표현할 수 있고, 해당 부분은 dist 배열을 각각 1번의 최단거리 함수로 구해서 캐싱하면 된다.

# 알고리즘: 다익스트라
# 시간 복잡도: O(T*ElogE) 간선 범위가 더 크고 3번의 다익스트라를 돌리므로 대략 어림잡아서 ElogE로 보았다.
# 실행 시간: Python 300ms

import heapq as hq
def dijkstra(start):
    dist = [INF for _ in range(n+1)]
    dist[start] = 0
    pq = []
    hq.heappush(pq, (0, start))

    while pq:
        di, no = hq.heappop(pq)
        if dist[no] < di:
            continue

        for i in graph[no]:
            if dist[i[1]] > i[0] + di:
                dist[i[1]] = i[0] + di
                hq.heappush(pq, (dist[i[1]], i[1]))
    return dist

T = int(input())
for _ in range(T):
    n, m, t = map(int, input().split())
    s, g, h = map(int, input().split())
    graph = [[] for _ in range(n+1)]

    for i in range(m):
        a, b, d = map(int, input().split())
        graph[a].append((d, b))
        graph[b].append((d, a))

    cand = [] # 목적지 후보
    for i in range(t):
        cand.append(int(input()))

    s_dijk = dijkstra(s) #출발지로부터 최단거리
    g_dijk = dijkstra(g)
    h_dijk = dijkstra(h)

    res = []
    for end in cand:
        # g -> h 경로
        gh = s_dijk[g] + g_dijk[h] + h_dijk[end]
        # h -> g 경로
        hg = s_dijk[h] + h_dijk[g] + g_dijk[end]
        #print(gh, hg)

        if gh == s_dijk[end] or hg == s_dijk[end]:
            res.append(end)
    res.sort()
    print(*res)
