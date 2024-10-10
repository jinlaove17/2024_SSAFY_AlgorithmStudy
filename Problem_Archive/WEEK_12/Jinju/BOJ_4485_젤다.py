# 문제 접근
#  잃는 금액 최소 -> 가중치가 최소가 되는 최단거리 찾기
#  이는 다익스트라로 쉽게 구할 수 있다.
#  4방탐색과 다익스트라를 변형을 통해 dist[][] 배열을 갱신하며 최단 거리를 구해주었다.
#
# 실행 시간: 108ms

import sys
import heapq as hq
input = sys.stdin.readline
INF = 1e9
dx = [-1, 1, 0, 0]
dy = [0, 0, -1, 1]
tc = 0

while True:
    n = int(input())
    tc += 1
    if n == 0:
        sys.exit()

    grid = [list(map(int, input().split())) for _ in range(n)]
    heap = []
    dist = [[INF] * n for _ in range(n)]

    dist[0][0] = grid[0][0]
    hq.heappush(heap, (grid[0][0], 0, 0))

    while heap:
        d, x, y = hq.heappop(heap)

        if x == n - 1 and y == n - 1:
            print("Problem", str(tc) + ":", d)
            break

        for i in range(4):
            nx = x + dx[i]
            ny = y + dy[i]

            if 0 <= nx < n and 0 <= ny < n:
                cost = d + grid[nx][ny]

                if dist[nx][ny] > cost:
                    dist[nx][ny] = grid[nx][ny] + d
                    hq.heappush(heap, (grid[nx][ny] + d, nx, ny))
