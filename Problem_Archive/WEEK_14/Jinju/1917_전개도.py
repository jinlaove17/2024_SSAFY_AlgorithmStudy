# 문제 접근
# 정육면체를 돌릴 때 절대적인 위치로 매핑해줘야 한다
# 처음에는 그림을 그려가면서 상대 위치를 찾았는데 불가능했다.
# 상대적인 위치를 찾다보면 꼬이기 때문에 절대적 위치로 면을 판단해줄 기준을 세워야 한다
# 해당 기준을 3×4 배열을 따로 관리하여 주사위 굴렸을 때 매핑하는 과정을 반복한다.

# 알고리즘 : 위치 매핑 아이디어를 이용한 시뮬레이션 구현(dfs와 방문 처리 이용)
# 시간 복잡도 : 전 칸 방문..?
# 실행 시간 : 40ms

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
