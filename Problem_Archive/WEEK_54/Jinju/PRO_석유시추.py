from collections import deque, defaultdict

def solution(land):
    n = len(land)
    visited = [[False]*n for _ in range(n)]
    dr = [-1, 1, 0, 0]
    dc = [0, 0, 1, -1]

    # 1. BFS로 그룹 넘버링 + 크기 계산
    group_id = 2
    group_size = defaultdict(int)

    for i in range(n):
        for j in range(n):
            if land[i][j] == 1 and not visited[i][j]:
                visited[i][j] = True
                land[i][j] = group_id
                q = deque([(i, j)])
                cnt = 1

                while q:
                    r, c = q.popleft()
                    for d in range(4):
                        nr, nc = r + dr[d], c + dc[d]
                        if 0 <= nr < n and 0 <= nc < n and land[nr][nc] == 1 and not visited[nr][nc]:
                            visited[nr][nc] = True
                            land[nr][nc] = group_id
                            cnt += 1
                            q.append((nr, nc))

                group_size[group_id] = cnt
                group_id += 1

    # 2. 컬럼별로 max sum 계산하기
    res = 0
    for col in range(n):
        g_set = set()
        for row in range(n):
            grp = land[row][col]
            if grp > 1:
                g_set.add(grp)

        tot_oil = sum(group_size[g] for g in g_set)
        res = max(res, tot_oil)

    return res
