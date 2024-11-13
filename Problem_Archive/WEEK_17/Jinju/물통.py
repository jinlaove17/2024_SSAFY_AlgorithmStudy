import sys
from collections import deque
input = sys.stdin.readline

# 문제 접근
# 최소 작업 수 = 최단 거리라고 생각하고 상태가 변할 때마다 큐에 넣어준다
# 소트 게임을 풀고 나서 이 문제를 보니 좀 더 직관적으로 다가왔다.
# 메모리 관리가 중요하므로 dictionary(HashMap) 을 써야겠다는 생각이 들었다.

def bfs(a, b, c, d):
    Q = deque([(0, 0)])  # 초기상태: (a 물통, b 물통)
    vst = {(0, 0): 0}    # 방문 여부 및 소요 turn 딕셔너리에 저장

    if (c, d) == (0, 0):
        return 0

    # 내부 함수 분리: 방문한 상태인지 확인하고 dict에 추가
    def add_state(ny, nx, turn):
        if (ny, nx) not in vst:
            vst[(ny, nx)] = turn + 1
            Q.append((ny, nx))

    while Q:
        y, x = Q.popleft()
        turn = vst[(y, x)]

        if (y, x) == (c, d):
            return turn

        # 가능한 상태 (채우기, 비우기, 옮기기): 방문처리 & Q에 추가
        add_state(a, x, turn)  # a 물통 채우기
        add_state(y, b, turn)  # b 물통 채우기
        add_state(0, x, turn)  # a 물통 비우기
        add_state(y, 0, turn)  # b 물통 비우기

        # a -> b로 물 옮기기
        if y+x <= b:
            add_state(0, y+x, turn)
        else:
            add_state(y-(b-x), b, turn)

        # b -> a로 물 옮기기
        if y + x <= a:
            add_state(y+x, 0, turn)
        else:
            add_state(a, x-(a-y), turn)
    return -1

A, B, C, D = map(int, input().split())
res = bfs(A, B, C, D)
print(res)
