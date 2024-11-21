import sys
input = sys.stdin.readline

N = int(input())
R = int(input())
arr = list(map(int, input().split()))
photo = dict()

# 문제 접근: 조건에 따른 시뮬레이션
# dictionary 에 [추천수, 순서] 형태로 저장한다.
# i번째 숫자가 있으면 추천수 증가
# 숫자가 없으면 액자의 크기를 확인한다.
# 액자 자리가 다 찼는지 아닌지 여부를 판단한 후,
# 액자가 꽉찼으면 (1. 가장 적은 추천수, 2. 가장 오래된 순서) 를 삭제하고 새로운 입력을 넣는다.

for i in range(R):
    if arr[i] in photo:
        photo[arr[i]][0] += 1
    else:
        if len(photo) < N:
            photo[arr[i]] = [1, i]
        else:
            tmp = sorted(photo.items(), key=lambda x: (x[1][0], x[1][1]))
            target = tmp[0][0]
            del photo[target]
            photo[arr[i]] = [1, i]

res = sorted(photo.keys())
print(*res)
            
