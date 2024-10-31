#문제 접근
#  부 배열의 합을 구하려면? 누적합으로 부분 배열(구간)의 누적합 값을 다 저장해야겠다고 생각했다.
#  N과 M이 최대 1000이므로 최대 O(N^2) 까지 미리 처리하여 가능한 부분 누적합 값을 다 저장해줄 수 있다고 판단했다.

#  누적합 배열과 양 끝 점을 기준으로 구간을 이용하고, 해당 값을 반대 arr(B)에서 찾아주면 된다.
#  이 때, 값의 크기가 매우 크기 때문에 이분 탐색을 이용해서 구해야한다.
#  이분 탐색을 배열 안에서 구해야한다는 생각이 들자마자 편하게 구하는 방법을 떠올림 ->  C++ or Python

#알고리즘: 누적합, 이분 탐색
#실행시간: 460ms

import sys
input = sys.stdin.readline

t = int(input())
n = int(input())
n_arr = list(map(int, input().split()))
m = int(input())
m_arr = list(map(int, input().split()))

# 누적 합 arr 만들기
pre_n = []
pre_m = []

for i in range(n):
    now = n_arr[i]
    pre_n.append(now)
    for j in range(i+1, n):
        now += n_arr[j]
        pre_n.append(now)

for i in range(m):
    now = m_arr[i]
    pre_m.append(now)
    for j in range(i+1, m):
        now += m_arr[j]
        pre_m.append(now)

pre_n.sort()
pre_m.sort()
res = 0

import bisect as bs
for i in range(len(pre_n)):
    left = bs.bisect_left(pre_m, t-pre_n[i])
    right = bs.bisect_right(pre_m, t-pre_n[i])
    res += right - left
print(res)
