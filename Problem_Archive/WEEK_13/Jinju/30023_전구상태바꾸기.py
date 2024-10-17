# 문제 접근
#  - 3개씩 이동하면서 바꿔주면 되겠다고 생각했다.
#  - 여러 케이스를 직접 그려보았을 때, 앞에서 시작하면서 맞추든 뒤에서 시작하면서 맞추든 결국 똑같아진다
#  - 안되는 경우는 앞에서 맞추든 뒤에서부터 맞추든 계속 맞춰나가다가 마지막에 남은 3개에서 통일이 안되면 안되는 케이스이다.
#  - 대충 3개씩 잡고 움직인다 했을 때, 하나만 달라도 계속 맞춰야하므로 될 때까지 돌리는게 그냥 맞는 선택이라고 보면 되겠다. (비슷한 결론이 나는 문제가 있는데 기억이 안난다)

# 알고리즘: 그리디적인 생각과 간단하지만 귀찮은 코드 구현
# 실행 시간: 300ms

import sys
input = sys.stdin.readline

N = int(input())
S = input().rstrip()
lights = []
res = 3**N

for now in S:
    if now == "R":
        lights.append(0)
    elif now == "G":
        lights.append(1)
    elif now == "B":
        lights.append(2)



for t in range(3):
    tmp = lights.copy()
    cnt = 0
    
    for i in range(N-2):
        change = (t-tmp[i]) % 3
        cnt += change
        
        for j in range(3):
            tmp[i+j] += change
            tmp[i+j] %= 3
            
    if all (t==n for n in tmp):
        if res > cnt:
            res = cnt
            
if res!=3**N:
    print(res)
else:
    print(-1)
