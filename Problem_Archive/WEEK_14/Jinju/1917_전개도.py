import sys
input = sys.stdin.readline

# 상, 하, 좌, 우 이동
dx = [0, 0, -1, 1]
dy = [-1, 1, 0, 0]

# 회전 방향 복귀를 위한 상응하는 방향 (0 -> 1, 2 -> 3)
reverse_dir = [1, 0, 3, 2]

# 정육면체 회전
def dice_move(direction):
    if direction == 0:  # 상
        dice[0][1], dice[1][1], dice[2][1], dice[3][1] = dice[3][1], dice[0][1], dice[1][1], dice[2][1]
    elif direction == 1:  # 하
        dice[0][1], dice[1][1], dice[2][1], dice[3][1] = dice[1][1], dice[2][1], dice[3][1], dice[0][1]
    elif direction == 2:  # 좌
        dice[1][0], dice[1][1], dice[1][2], dice[3][1] = dice[3][1], dice[1][0], dice[1][1], dice[1][2]
    elif direction == 3:  # 우
        dice[1][0], dice[1][1], dice[1][2], dice[3][1] = dice[1][1], dice[1][2], dice[3][1], dice[1][0]


def dfs(x, y):
    cnt = 1  # 방문 수

    for dir in range(4):
        nx, ny = x + dx[dir], y + dy[dir]

        if 0 <= nx < 6 and 0 <= ny < 6 and grid[ny][nx] == 1:
            dice_move(dir)  #회전

            if not dice[1][1]:
                dice[1][1] = True
                cnt += dfs(nx, ny)

            dice_move(reverse_dir[dir])  #원복
    return cnt

for _ in range(3):
    grid = [list(map(int, input().split())) for _ in range(6)]
    dice = [[False] * 3 for _ in range(4)]  # 3x4 크기의 주사위 상태

    start_flg = False
    res = 0

    for i in range(6):
        for j in range(6):
            if grid[i][j] == 1:
                dice[1][1] = True  #시작 방문 처리
                res = dfs(j, i)
                start_flg = True
                break
        if start_flg:
            break
    print('yes' if res == 6 else 'no')
