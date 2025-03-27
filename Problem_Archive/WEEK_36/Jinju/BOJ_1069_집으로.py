import sys
import math
input = sys.stdin.readline
x, y, d, t = map(int, input().split())
dist = math.hypot(x, y)
res = dist #default distance

if d >= dist: #d가 dist 이상인 경우
    res = min(res, t+(d-dist))
    res = min(res, 2*t)
else:
    cnt = int(dist // d)
    remain = dist-(cnt*d)
    res = min(res, cnt*t + remain)
    res = min(res, (cnt+1)*t)
print(f"{res:.10f}")
