# 문제 접근
# 내가 먹을 수 있는 공을 찾아야한다.
# 같은 색은 불가능하므로 같은 색의 공은 따로 관리해야한다.
# 나머지 다른 색의 공들은 사이즈를 비교해서 관리하면 되는데
# 이 때, 누적합을 이용하면 내 위치에서 내가 최대로 가능한 크기를 알 수 있다.
# 대신 같은 색을 처리하는 컬러 누적합도 필요하다.

# 알고리즘 : 누적 합
# 시간 복잡도 : N log N
# 실행 시간 : 40ms

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
