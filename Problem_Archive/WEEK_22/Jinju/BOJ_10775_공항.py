import sys
input = sys.stdin.readline
sys.setrecursionlimit(10**5)

# 문제 접근
# 각 비행기를 가능한 게이트 중 가장 큰 번호에 배치하고, 배치할 수 없으면 중단해야 한다.
# 비행기 배치를 유니온 파인드로 진행해야겠다는 생각은 쉽게 떠올릴 수 있다.
# 비행기는 가능한 게이트 중 가장 큰 번호에 도킹해야 하므로, 항상 현재 비행기가 도킹 가능한 최대 게이트 번호를 우선적으로 고려해야 한다.
# 이 그리디적인 접근이 왜 가능한지 수학적으로 증명? 하는 생각을 할 수 있어야 한다.
# 각 비행기가 도킹할 수 있는 range가 다 다르므로, 자기가 도킹 가능한 번호 중 최대 번호에 댈 때, 다음의 도킹 가능성을 최대한 해치지 않는다.
# 즉, 비행기가 도킹 가능한 최대 게이트 번호를 선택해도, 이후 비행기들의 도킹 가능성을 줄이지 않는다는 생각이 곧 증명이다.

# 알고리즘: 유니온 파인드 + 그리디적인 생각
# 시간: 140ms

G = int(input())
P = int(input())
air = []
for _ in range(P):
    air.append(int(input()))

parent = [i for i in range(G+1)]
def find_p(me):
    if parent[me] == me:
        return me
    parent[me] = find_p(parent[me])
    return parent[me]

res = 0
for i in range(P):
    now = air[i]
    np = find_p(now)

    if np == 0:
        break
    else:
        parent[np] = parent[np-1]
        res += 1
print(res)
