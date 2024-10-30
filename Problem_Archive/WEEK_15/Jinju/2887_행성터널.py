import sys
input = sys.stdin.readline
sys.setrecursionlimit(10**7) #원래 10^6 보다 크면 되는데 좀 더 넉넉하게 잡기

# 문제 접근
#   거리 구하는 공식이 특이했기 때문에 min(x차이, y차이, z차이)
#   해당 필드를 기준으로 정렬해볼까? 하는 생각이 먼저 들었다.
#   만약, 정렬하게 된다면 내 위치를 기준으로 절댓값 계산하면 그게 최소 차이 아닐까?

#   1. 그러면 x, y, z 필드를 기준으로 각각 정렬해본다
#   2. 필드 값의 차이와 해당 간선을 이루는 정점 (u, v)를 같이 저장한다.
#   3. 크루스칼 식으로 합치기?

# 알고리즘: 각 x, y, z 필드를 기준으로 한 정렬을 통해 간선 최소화 한 후 크루스칼 돌리기
# 실행시간: 1100ms

N = int(input())
pos = []

for i in range(N):
    x, y, z = map(int, input().split())
    pos.append((i, x, y, z)) #idx, x, y, z 필드 순으로 저장

pos_x = sorted(pos, key=lambda x: x[1])
pos_y = sorted(pos, key=lambda x: x[2])
pos_z = sorted(pos, key=lambda x: x[3])

edges = []

for i in range(N-1):
    wx = abs(pos_x[i][1] - pos_x[i+1][1])
    wy = abs(pos_y[i][2] - pos_y[i+1][2])
    wz = abs(pos_z[i][3] - pos_z[i+1][3])

    # 가중치, node1, node2 순으로 저장
    edges.append((wx, pos_x[i][0], pos_x[i+1][0]))
    edges.append((wy, pos_y[i][0], pos_y[i+1][0]))
    edges.append((wz, pos_z[i][0], pos_z[i+1][0]))

edges.sort() #가중치 기준으로 정렬

parent = [i for i in range(N)]

def find_p(me):
    if parent[me] == me:
        return me
    else:
        parent[me] = find_p(parent[me])
        return parent[me]

def union(u, v):
    pu = find_p(u)
    pv = find_p(v)

    if pu > pv:
        pu, pv = pv, pu

    parent[pv] = pu

min_cost = 0
tree_size = 0

for w, u, v in edges:
    if tree_size == N:
        break

    pu = find_p(u)
    pv = find_p(v)

    if pu == pv:
        continue
    else:
        union(u, v)
        tree_size += 1
        min_cost += w

print(min_cost)
