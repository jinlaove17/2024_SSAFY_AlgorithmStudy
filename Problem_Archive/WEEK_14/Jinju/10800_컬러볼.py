#

import sys
input = sys.stdin.readline
N = int(input())
balls = []

# 각 공의 정보를 (크기, 색상, 인덱스)로 저장
for i in range(N):
    c, s = map(int, input().split())
    balls.append((s, c, i))

# size 순으로 정렬
balls.sort(key=lambda x: x[0])

pre_s = 0
color_sum = [0]*(N+1)
res = [0]*N
idx = 0

for i in range(N):
    while balls[idx][0] < balls[i][0]:
        pre_s += balls[idx][0]
        color_sum[balls[idx][1]] += balls[idx][0]
        idx += 1

    res[balls[i][2]] = pre_s - color_sum[balls[i][1]]

for r in res:
    print(r)
