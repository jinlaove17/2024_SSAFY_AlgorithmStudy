import sys
from collections import deque
input = sys.stdin.readline
MIN = 201
MAX = -1
dr = [-1, 1, 0 ,0]
dc = [0, 0, -1, 1]

# 문제 접근
# 배열 안에서 상, 하, 좌, 우 이동이 필요하므로 그래프 탐색을 기반으로 경로를 어떻게 파악할지 생각했다.
# 결국 배열의 모든 칸을 계속 이동할 수 있는데, 그 중에서 경로 숫자의 최대-최소 차이를 줄이려면 이 값에 대한 판단이 필요하다고 생각했다.
# 최대-최소 범위를 이분 탐색을 통해 체크해주면서 BFS를 계속 돌려서 T/F 가능 여부를 확인한다.
# 사실 BFS 탐색을 돌리더라도 우리는 최단거리를 체크하는게 아니라 가능한 좌표는 다 넣어줄 수 있으므로 O(N*N)이 보장된다는 생각을 하기 어려운데,
# 이를 이분 탐색을 통해 Q에 넣는 범위를 줄여주면서 상, 하, 좌, 우 이동하는 느낌이다.
# 또한, 최적화도 필요한데, 바로 방문 처리의 위치이다. 
# Q에 넣기 전에 방문 처리를 미리 해서 다음 반복문이나 이동에서 같은 좌표가 Q에 담기지 않게 하는 것이다.
# 보통의 BFS처럼 Q에서 꺼낼 때 방문 처리를 하면 TLE이나 MLE이 난다.

# 알고리즘: 이분 탐색을 통한 최대-최소 차이 판별 문제 (판별의 T/F를 상하좌우 이동을하면서 해당 range 값만 넣어주고 도착할 수 있냐 or 없냐로 판단)
# 시간: 220ms

N = int(input())
grid = []

for _ in range(N):
    line = list(map(int, input().split()))
    for n in line:
        MIN = min(MIN, n)
        MAX = max(MAX, n)
    grid.append(line)

s, e = grid[0][0], grid[N-1][N-1]

# 이분 탐색을 위한 check 함수
def check_mid(mid): 
    for i in range(MIN, MAX + 1):
        if not (i <= s <= i + mid and i <= e <= i + mid):
            continue

        if bfs(i, i + mid):  # 간격을 bfs 함수로 넘기기
            return True
    return False

def bfs(st, ed):
    vst = [[False] * N for _ in range(N)]
    vst[0][0] = True
    queue = deque([(0, 0)])
    
    while queue:
        r, c = queue.popleft()
        
        if r == N-1 and c == N-1:  # 도착
            return True

        for d in range(4):
            nr, nc = r + dr[d], c + dc[d]
            if 0 <= nr < N and 0 <= nc < N and not vst[nr][nc]:
                if st <= grid[nr][nc] <= ed:  # 범위 내에 있는 값만 큐에 추가
                    vst[nr][nc] = True
                    queue.append((nr, nc))

    return False

l, r = 0, MAX
res = 0

# binary search
while l <= r:
    mid = (l + r) // 2
    if check_mid(mid):
        res = mid
        r = mid - 1
    else:
        l = mid + 1
print(res)
