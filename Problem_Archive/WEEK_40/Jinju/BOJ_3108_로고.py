# 문제의 핵심
# N개의 축에 평행한 직사각형이 주어질 때, 서로 “만나는”(경계가 닿거나 겹치는) 직사각형들을 하나의 그룹으로 보는 것이 포인트
# -> 해당 판단을 유니온 파인드로 한다. (유니온 파인드를 사용하는 것을 떠올리는게 오래걸림)
# 맞닿아 있는 것을 체크하는 checkMett 함수를 따로 분리한다.

# 시간 복잡도: O(N^2)
# 사각형 배열을 이중 for문을 돌면서 체크하는 것이 제일 큰 비용 소모

import sys
input = sys.stdin.readline
N = int(input())
rec_arr = []
for _ in range(N):
    x1, y1, x2, y2 = map(int, input().split())
    rec_arr.append(((x1, y1), (x2, y2)))
parent = [i for i in range(N)]

def find_p(u):
    if parent[u] != u:
        parent[u] = find_p(parent[u])
    return parent[u]

def union_p(u, v):
    pu = find_p(u)
    pv = find_p(v)
    if pu == pv:
        return
    if pu > pv:
        pu, pv = pv, pu
    parent[pv] = pu

def checkMeet(a, b):
    (x11, y11), (x12, y12) = a
    (x21, y21), (x22, y22) = b
    if x12 < x21 or x22 < x11 or y12 < y21 or y22 < y11:
        return False
    if x11 < x21 and x22 < x12 and y11 < y21 and y22 < y12:
        return False
    if x21 < x11 and x12 < x22 and y21 < y11 and y12 < y22:
        return False
    return True

for i in range(N-1):
    for j in range(i+1, N):
        if checkMeet(rec_arr[i], rec_arr[j]):
            union_p(i, j)

grps = set(find_p(i) for i in range(N))
res = len(grps)

for (x1, y1), (x2, y2) in rec_arr:
    if (x1 == 0 or x2 == 0) and y1 <= 0 <= y2:
        res -= 1
        break

    if (y1 == 0 or y2 == 0) and x1 <= 0 <= x2:
        res -= 1
        break
print(res)
