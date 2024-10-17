# 문제 접근
# 1. 사이클인 그래프 찾기
# 2. 사이클인 그래프로부터 각 노드 도달거리 탐색하기
# -> 정직하게 그래프 탐색을 구현했다.
# -> 간선의 개수와 정점의 개수가 같은 연결 그래프는 사이클을 정확히 하나 갖기 때문에 여러 사이클을 생각할 필요가 없다.

# 알고리즘 : DFS를 통한 사이클 탐지 + BFS를 통한 거리 계산
# 시간 복잡도
#   DFS : O(N^2) 사이클 탐색하면서 전체 정점과 간선을 체크해주는데, path 배열을 도는 for문을 매 호출마다 부르고 있으므로 O(N^2)에 수렴
#   BFS : O(N+E)
#   최종적으로 O(N^2)

# 실행 시간 : 900ms

import sys
input = sys.stdin.readline
sys.setrecursionlimit(10**5)
MAX = 3001

N = int(input())
graph = [[] for _ in range(N+1)]
cycle = [MAX] * (N+1) # 사이클까지 각 노드 도달 거리
vst = [False]*(N+1)

for i in range(N):
    a, b = map(int, input().split())
    graph[a].append(b)
    graph[b].append(a)

flg = 0
def find_cycle(path, cnt): # 지나온 노드 path, 깊이
    global flg
    if flg: # 발견한 경우에 리턴
        return

    # 현재 노드(path의 마지막 노드)와 연결된 노드 탐색
    for i in graph[path[-1]]:
        if i == path[0] and cnt >= 2: # 사이클인 경우 종료
            for j in path:
                cycle[j] = 0 # j 노드는 사이클에 속하는 노드
            flg = 1
            return

        if not vst[i]:
            vst[i] = True
            find_cycle(path + [i], cnt + 1)
            vst[i] = False

for i in range(1, N+1):
    vst[i] = 1
    find_cycle([i], 0)
    vst[i] = 0

# 사이클에 속하는 노드 BFS 돌리기
from collections import deque
Q = deque()

for i in range(1, N+1):
    if cycle[i] == 0:
        Q.append((i, 0))

while Q:
    now, dist = Q.popleft()
    for v in graph[now]:
        if cycle[v] == MAX:
            cycle[v] = dist+1
            Q.append((v, dist+1))

print(*cycle[1:])
