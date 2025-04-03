# 그래프에서 출발점 A에서 도착점 B까지 총 비용이 C 이하인 경로들 중,
# 경로 상의 간선 최대 비용을 최소화하는 값 찾아야 한다

# 우선순위 큐를 사용하여 (현재까지 경로의 최대 간선 비용, 총 누적 비용, 현재 노드) 형태로 탐색
# 큐에서는 최대 간선 비용이 작은 경로를 우선적으로 탐색하며, 누적 비용이 C를 초과하지 않는 경우에만 다음 노드로 이동

# D는 각 노드까지 도달할 때의 최소 max 간선 비용을 기록하여 더 나쁜 경로는 걸러낸다.
# 이 방식은 다익스트라와 유사하지만, "누적 비용 최솟값"이 아니라
# "경로 상 간선의 최대값을 최소화"하는 것이 목표임 == 우선순위 큐 기반의 최적화 탐색

import sys
input = sys.stdin.readline
from collections import defaultdict
import heapq as hq
N, M, A, B, C = map(int, input().split())
G, D = defaultdict(list),[2000000]*(N+1)
for _ in range(M):
    u,v,w=map(int, input().split())
    G[u].append((v,w))
    G[v].append((u,w))
    
h = [(0,0,A)]

while h:
    m, s, u = hq.heappop(h)
    if u==B:
        print(m)
        sys.exit()
    for v,w in G[u]:
        t = max(m,w)
        if D[v]>t and s+w<=C:
            D[v]=t
            hq.heappush(h, (t, s+w, v))
print(-1)
