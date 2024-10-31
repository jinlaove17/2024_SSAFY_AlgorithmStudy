#문제 접근
#  고속도로로 i, i+1가 이미 연결되어 있으므로 우리는 거슬러가는 간선을 체크해야 한다.
#  따라서 문제 입력 받을때도, 첫 번째 값이 더 큰 경우(즉, 더 큰 정점에서 더 작은 정점으로 이어지는 간선)만 필터링해서 받으면 된다.
#  뒤로 되돌아가는 경우만 고려해도 구간을 정확히 구할 수 있다는 사실을 깨달아야한다.
#  따라서, 유니온 파인드처럼 node 배열에 root를 다 저장하고, 되돌아 가는 간선만 루트 설정을 다시 해주면서 특정 값(div)로 나누어떨어지는지 체크한다.

#알고리즘: parent 배열..? 유니온 개념을 통한 정직한 그래프 탐색과 조건 check
#실행시간: 100ms


import sys
input = sys.stdin.readline
N, M = map(int, input().split())
graph = [[i+1] for i in range(N+1)]
edges = sorted(filter(lambda x: x[0] > x[1], [list(map(int, input().split())) for x in range(M)]), key=lambda x: x[0])
node = [-1 for _ in range(N+1)]

for [v, w] in edges:
    for cur in range(w, v+1):
        node[cur] = node[w] if node[w] > -1 else w

res = [1]
for div in range(2, N+1):
    if N % div != 0:
        continue
    res.append(div)
    cnt, idx = 0, 1

    while idx <= N:
        if node[idx] == -1:
            cnt += 1
            idx += 1
        else:
            target = node[idx]
            while idx <= N and node[idx] == target:
                cnt += 1
                idx += 1

        if cnt == N // div:
            cnt = 0
        elif cnt > N // div:
            res.pop()
            break
print(max(res))
