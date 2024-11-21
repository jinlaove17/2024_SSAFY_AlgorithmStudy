import sys
input = sys.stdin.readline
N, M = map(int, input().split())
num = []
for _ in range(N):
    num.append(int(input()))
# 최소 시간과 최대 시간 초기화
st, ed = 1, max(num) * M  # 최악의 경우 모든 사람이 가장 느린 심사대에서 처리
res = ed

while st <= ed:
    mid = (st + ed)//2
    total = 0

    # mid 시간 동안 각 심사대에서 처리할 수 있는 사람 수
    for time in num:
        total += mid // time

    if total >= M:
        res = mid
        ed = mid-1
    else:
        st = mid+1
print(res)
