# 문제 핵심
# 성곽을 표시하는 비트마스킹 처리를 잘 할 수 있는지?
# 벽 이동을
# 각 칸 (i,j)에 대해
# wall = 1부터 시작해, (s[i][j] & wall) == wall 이면 해당 방향에 벽 존재
# 아니라면 인접 칸으로 이동 가능하고, 검사 순서는 서(1), 북(2), 동(4), 남(8) 이고, num = (num << 1) 로 다음 비트로 이동

# 알고리즘: BFS + 비트마스킹
# 방의 개수와 최대 방 크기를 한 번의 BFS 순회로 계산하고, 벽 하나 제거했을 때 방 크기를 다시 BFS로 계산해 최대값 갱신

# 시간 복잡도: O(N*M) 2차원 그리드 크기 만큼

from collections import deque
import sys
input = sys.stdin.readline

dx = [0, -1, 0, 1]
dy = [-1, 0, 1, 0]

N, M = map(int, input().split())
s = [list(map(int, input().split())) for i in range(M)]
vst = [[0]*N for i in range(M)]

def bfs(x,y):
    q = deque()
    q.append((x,y))
    vst[x][y] = 1
    room = 1
      
    while q:
        x, y = q.popleft()
        wall = 1
        for i in range(4):
            nx = x + dx[i]
            ny = y + dy[i]
            if ((s[x][y] & wall) != wall): 
                if 0<=nx<M and 0<=ny<N and not vst[nx][ny]:
                    room+=1
                    vst[nx][ny] = 1
                    q.append((nx,ny))
            wall = wall*2
    return room
    
room_cnt = 0
room_max = 0
room_del = 0

for i in range(M):
    for j in range(N):
        if vst[i][j] == 0:
            room_cnt += 1
            room_max = max(room_max, bfs(i, j))

for i in range(M):
    for j in range(N):
        num = 1
        while num < 9:
            if num & s[i][j]:
                vst = [[0] * N for _ in range(M)]
                s[i][j] -= num
                room_del = max(room_del, bfs(i, j))
                s[i][j] += num
            num = (num << 1)
            
print(room_cnt)
print(room_max)
print(room_del)
