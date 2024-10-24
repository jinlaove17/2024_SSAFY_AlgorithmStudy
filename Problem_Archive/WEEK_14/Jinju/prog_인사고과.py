#문제 접근
#  어떤 사원의 점수 쌍 (a, b)를 기준으로 둘 다 낮으면 인센티브를 받을 수 없다는 처리를 잘 해준 후,
#  정렬을 통해 하나의 필드를 중심으로 내림차순, 다음 필드를 오름차순으로 정렬한다면 쉽게 판단할 수 있다.
#  scores 리스트에서 뒤에 나온 학생의 첫 번째 점수(a)는 항상 앞에 나온 학생의 첫 번째 점수보다 같거나 작을 것이다. (내림차순이라)
#  그러면 나중에 탐색하는 학생의 두 번째 점수(b)가 먼저 탐색하는 학생의 두 번째 점수보다 작은 경우가 단 하나라도 있다면 앞 학생의 석차가 나중 학생보다 무조건 높다

#알고리즘
#  정렬 해야한다는 생각..? 정렬을 통해 정합성을 보장하는 상황 만들기?

#실행 시간: 25번 테케 100ms 정도
#시간 복잡도: O (N log N) 정렬

def solution(scores):
    res = 0
    ta, tb = scores[0]
    target_score = ta + tb

    # 첫번째 점수에 대한 내림차순, 첫 번째 점수가 같으면 두 번째 점수에 대해서 오름차순
    scores.sort(key=lambda x: (-x[0], x[1]))
    tmp_max = 0
    
    for a, b in scores:
        if ta < a and tb < b:
            return -1
        
        if b >= tmp_max:
            tmp_max = b
            if a + b > target_score:
                res += 1
            
    return res + 1
