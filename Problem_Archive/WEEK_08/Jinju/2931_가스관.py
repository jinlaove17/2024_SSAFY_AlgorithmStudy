# 전략
# 1. m에서 z까지 향하는 가스의 흐름은 유일하다
# 2. m에서 출발, z에서 출발 하여 가스의 흐름이 막히는 곳을 찾아낸다
# 3. m에서 bfs, z에서 bfs로 탐색하다 보면 가야하는 흐름이 끊기는 곳이 발견
# 4. blank를 발견하면 좌표를 저장
# 시간복잡도 : O(격자 크기 = R*C)

import sys
input = sys.stdin.readline
from collections import deque

dx = [0, 1, 0, -1]
dy = [1, 0, -1, 0]

# 파이프 별 갈 수 있는 방향
dirs = {'|': [1, 3],
        '-': [0, 2],
        '+': [0, 1, 2, 3],
        'M': [0, 1, 2, 3],
        'Z': [0, 1, 2, 3],
        '1': [0, 1],
        '2': [0, 3],
        '3': [2, 3],
        '4': [1, 2]}

def bfs(a, b, d):
    global res_x, res_y

    q = deque()
    q.append([a, b, d])
    vst[a][b] = 1

    while q:
        x, y, dir = q.popleft()
        for i in dir:
            nx = x + dx[i]
            ny = y + dy[i]

            if 0 <= nx < r and 0 <= ny < c and not vst[nx][ny]:
                if board[nx][ny] != '.':
                    vst[nx][ny] = 1
                    ndir = dirs[board[nx][ny]]

                    q.append((nx, ny, ndir))
                else:
                    if board[x][y] == 'M' or board[x][y] == 'Z':
                        continue

                    if not res_x and not res_y:
                        res_x, res_y = nx + 1, ny + 1
                    ndir = (i + 2) % 4

                    if ndir not in tmp:
                        tmp.append(ndir)


r, c = map(int, input().split())
vst = [[0 for _ in range(c)] for _ in range(r)]

# 보드 입력 및 M, Z의 xy좌표 저장
board = []
for i in range(r):
    board.append(list(input().rstrip()))

    for j in range(c):
        if board[i][j] == 'M':
            mx, my = i, j
        elif board[i][j] == 'Z':
            zx, zy = i, j

tmp = []  # 방향 저장 리스트
res_x, res_y = 0, 0  # 결과 좌표 저장

bfs(mx, my, [0, 1, 2, 3])
bfs(zx, zy, [0, 1, 2, 3])

for i in range(r):
    for j in range(c):
        if board[i][j] != '.' and not vst[i][j]:
            bfs(i, j, dirs[board[i][j]])
tmp.sort()

if len(tmp) == 4:
    print(res_x, res_y, '+')
else:
    blocks = ['|', '-', '1', '2', '3', '4']
    for block in blocks:
        if tmp == dirs[block]:
            print(res_x, res_y, block)
