import sys
input = sys.stdin.readline
K = int(input())
fold = list(input().split())
fold = reversed(fold)
hole = int(input())
pos = [[hole]] #hole 위치 배열에 넣기

#1. 종이 접힌 순서를 역순으로 순회하는 것
#2. 각 위치의 점이 접기(가로/세로)를 기준으로 어디와 매핑되는지 확인

LR = [1, 0, 3, 2]
DU = [2, 3, 0, 1]
x, y = 1, 1 #초기 크기

for turn in fold: #역순으로 점점 펼치는 것
    tmp = [[-1]*x for _ in range(y)]

    for i in range(y):
        for j in range(x):
            if turn == 'L' or turn == 'R':
                tmp[i][j] = LR[pos[i][x-j-1]]
            else:
                tmp[i][j] = DU[pos[y-i-1][j]]

    if turn == 'L':
        for i in range(y):
            pos[i] += tmp[i] #기존 pos에 tmp 배열을 오른쪽에 붙이기
        x *= 2
    elif turn == 'R':
        for i in range(y):
            pos[i] = tmp[i] + pos[i] #기존 pos에 tmp 배열을 왼쪽에 붙이기
        x *= 2
    elif turn == 'U': #기존 pos에 tmp 배열을 아래에 붙이기
        pos += tmp
        y *= 2
    else: #기존 pos에 tmp 배열을 위에 붙이기
        pos = tmp + pos
        y *= 2

for p in pos:
    print(*p)
