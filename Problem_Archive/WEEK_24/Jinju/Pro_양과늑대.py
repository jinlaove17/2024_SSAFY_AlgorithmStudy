# 문제 접근
# 그동안 방문했던 모든 곳에서 뻗어나갈 수 있는 dfs를 짜는게 관건
#  -> 모든 요소들을 반복문 돌면서 체크, 방문 처리를 이용해서 dfs 돌리기
# 알고리즘: dfs 변형
# 시간 복잡도
#   1. 현재 방문한 상태에 따라서 다음 선택지의 수가 달라진다.
#   2. 기본 이진 트리라면 O(2^16) 의 상태 공간 트리이지만, 
#    -> 늑대 조건에 따른 가지치기로 인해 상태 공간 트리 수가 많이 줄어든다.
#    -> 실제로 한 번 길이 막히면 트리 안에서 해당 길을 지나는 모든 공간의 수를 탐색할 수 없는데
#    -> 대략적으로 tree depth에 따라서, depth가 깊지 않은 곳에서 막히면 상태 공간 트리가 크게 줄어들고,
#       root를 제외한 전체 노드 수가 16개 이므로 depth가 깊지 않은 곳에서 경로가 차단될 가능성이 크다.
# 소요시간: 11ms

def solution(info, edges):
    vst = [False] * len(info)
    res = []
    
    # 모든 곳에서 뻗어나갈 수 있는 dfs를 짜는게 관건
    def dfs(cnt_sheep, cnt_wolf):
        if cnt_sheep > cnt_wolf:
            res.append(cnt_sheep)
        else: 
            return #양이 잡아먹히므로 종료
        
        for parent, child in edges:
            if vst[parent] and not vst[child]:
                vst[child] = True
                if info[child] == 0:
                    dfs(cnt_sheep+1, cnt_wolf)
                else:
                    dfs(cnt_sheep, cnt_wolf+1)
                vst[child] = False #원복
    
    vst[0] = True
    dfs(1, 0)
    return max(res)
