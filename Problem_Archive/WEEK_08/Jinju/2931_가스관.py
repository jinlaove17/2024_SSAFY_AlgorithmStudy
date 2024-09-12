import sys
input = sys.stdin.readline
from collections import deque

dx = [0, 1, 0, -1]
dy = [1, 0, -1, 0]

# 파이프 별 갈 수 있는 방향
directions = {'|' : [1, 3],
       '-' : [0, 2],
       '+' : [0, 1, 2, 3],
       'M' : [0, 1, 2, 3],
       'Z' : [0, 1, 2, 3],
       '1' : [0, 1],
       '2' : [0, 3],
       '3' : [2, 3],
       '4' : [1, 2]}

def bfs(a, b, d):
    global delx, dely
  
    q = deque()
    q.append([a, b, d])
    visited[a][b] = 1
  
    while q:
        x, y, dir = q.popleft()
        for i in dir:
            nx, ny = x + dx[i], y + dy[i]
            if 0<=nx<m and 0<=ny<n and if not visited[nx][ny]:
                if board[nx][ny] != '.':
                    visited[nx][ny] = 1
                    ndir = directions[board[nx][ny]]
                    q.append((nx, ny, ndir))
                else:
                    if board[x][y] == 'M' or board[x][y] == 'Z':
                        continue
                    if not delx and not dely:
                        delx, dely = nx + 1, ny + 1
                    ndir = (d+2) % 4
                    if ndir not in temp:
                        temp.append(ndir)



r, c = map(int, input().split())
visited = [[0 for _ in range(c)] for _ in range(r)]

# 보드 입력 및 M, Z의 xy좌표 저장
board = []
for i in range(r):
    line = list(input().strip())
    board.append(line)
    for j in range(c):
        if line[j] == 'M':
            mx, my = i, j
        elif line[j] == 'Z':
            zx, zy = i, j

temp = [] #방향 저장 리스트
delx, dely = 0, 0 #결과 좌표 저장

bfs(mx, my, [0, 1, 2, 3])
bfs(zx, zy, [0, 1, 2, 3])

for i in range(r):
    for j in range(c):
        if board[i][j] != '.' and not visited[i][j]:
            bfs(i, j, directions[board[i][j]])
temp.sort()

if len(temp) == 4:
    print(delx, dely, '+')
else:
    blocks = ['|', '-', '1', '2','3','4']
    for block in blocks:
        if temp == directions[block]:
            print(delx, dely, block)
