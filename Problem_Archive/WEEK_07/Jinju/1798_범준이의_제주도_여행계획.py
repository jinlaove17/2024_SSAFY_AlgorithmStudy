# DFS 함수 정의: 남은 일수(rday), 남은 시간(rmin), 현재 점수(score), 마지막 방문 장소(last_visit)
def dfs(rday, rmin, score, last_visit): 
    flg = False

    # 방문하지 않은 지점들에 대해 반복
    for i, (m, s) in points.items():
        if vst[i]: continue  # 이미 방문한 지점이면 건너뜀

        # 남은 시간이 충분한 경우(이동 시간(dist[last_visit][i])과 필요한 시간(m)을 뺀 후 10분 이상 남아야 함)
        if rmin - m - dist[last_visit][i] >= 10:
            flg = True 
            path.append(i) 
            vst[i] = 1

            dfs(rday, rmin - m - dist[last_visit][i], score + s, i)
            
            path.pop() 
            vst[i] = 0

    # 더 이상 방문할 곳이 없는 경우
    if not flg:
        if rday > 1:  # 남은 날이 있는 경우
            for i in hotels:  # 호텔 순회
                if dist[last_visit][i] <= rmin:  # 남은 시간 안에 이동 가능한 호텔이 있으면 경로에 호텔 추가
                    path.append(i) 
                    dfs(rday-1, 540, score, i)  # 새로운 날로 DFS
                    path.pop() 
        else:  # 마지막 날
            # 공항까지 돌아갈 시간이 남아있고, 현재 점수가 최고점보다 높은 경우
            if rmin >= dist[last_visit][airport] and ret[0] < score:
                ret[0] = score  # 업데이트
                ret[1] = path[1:] + [airport]

T = int(input())

for t in range(1, T+1):
    N, M = map(int, input().split())
    dist = [[0]*(N+1) for _ in range(N+1)]

    # 장소 간 거리 입력
    for i in range(1, N):
        line = list(map(int, input().split()))
        for j, d in enumerate(line, i+1):
            dist[i][j] = dist[j][i] = d

    hotels = []  # 호텔
    points = {}  # 관광지 (시간, 점수)

    for i in range(1, N+1):
        line = input()

        if line[0] == 'P': 
            points[i] = list(map(int, line.split()[1:]))  # (시간, 점수) 정보를 저장
        elif line[0] == 'H': 
            hotels.append(i)
        else:
            airport = i

    ret = [0, []] 
    vst = [0]*(N+1)
    path = [airport]  # 경로 시작 공항
    dfs(M, 540, 0, airport)  # DFS (남은 일수 M, 남은 시간 540분, 점수 0, 마지막 방문 공항)

    print('#{} {} {}'.format(t, ret[0], ' '.join(map(str, ret[1]))))
