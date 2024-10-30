# 문제 접근
#   펄스 수열은 1, -1 순서 반복 or -1, 1 순서 반복 두 가지 경우 밖에 없다
#   따라서 두 가지 경우를 모두 arr에 적용해서 만들어주고
#   누적합(cur_sum에 저장하면서) 사용해서 구할 수 있을 것 같다.

# 알고리즘: Kadane 알고리즘 참고, 약간 DP적인 생각이라고 보면 된다.
#             https://medium.com/@vdongbin/kadanes-algorithm-%EC%B9%B4%EB%8D%B0%EC%9D%B8-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-acbc8c279f29
# 실행시간: 최악의 TC 19번 기준 490ms, 58MB

def max_subarray_sum(arr):
    max_sum = float('-inf') #가장 큰 min 값으로 초기화
    cur_sum = 0
    start = end = s = 0  # 구간 시작, 끝 인덱스

    for i in range(len(arr)):
        cur_sum += arr[i]

        if cur_sum > max_sum:
            max_sum = cur_sum
            start = s
            end = i

        # 합이 음수가 되면 새로운 구간 시작
        if cur_sum < 0:
            cur_sum = 0
            s = i + 1
    return max_sum

def solution(sequence):
    s_len = len(sequence)
    pos = []
    neg = []

    for i in range(s_len):
        if i%2 == 0:
            pos.append(sequence[i])
            neg.append(-sequence[i])
        else:
            pos.append(-sequence[i])
            neg.append(sequence[i])

    tmp1 = max_subarray_sum(pos)
    tmp2 = max_subarray_sum(neg)
    res = max(tmp1, tmp2)
    return res
