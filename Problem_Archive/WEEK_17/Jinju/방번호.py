import sys
input = sys.stdin.readline

# 처음에는 only DP로 풀어보려고 했는데 틀려서 무엇을 놓쳤나 고민하고 있었다.
# dp 초기화를 잘뮷해서 갱신되어야 할 값이 갱신이 안되고 있었던 것 같다.

# N: 사용할 수 있는 숫자의 개수
N = int(input())

# value: 각 숫자의 비용 리스트
value = list(map(int, input().split()))

# M: 사용할 수 있는 총 금액
M = int(input())

# dp[i]: 금액 i로 만들 수 있는 최대 숫자 (-1로 초기화하여 아직 사용하지 않은 금액 표시)
dp = [-1 for _ in range(51)]

# 초기 DP 테이블 설정
# 사용 가능한 각 숫자에 대해 해당 숫자만 사용해 만들 수 있는 최대 숫자를 초기화
for i in range(N):
    if M - value[i] >= 0:
        dp[M - value[i]] = max(dp[M - value[i]], i)

# 역순으로 순회하면서 DP 테이블을 갱신해 더 큰 숫자를 구성
for i in range(50, -1, -1):
    if dp[i] != -1:  # dp[i]에 값이 있다면
        main_number = str(dp[i])  # 현재 숫자를 문자열로 저장하여 새 숫자 조합 시작
        for j in range(N):  # 각 숫자에 대해 시도
            if i - value[j] >= 0:  # 사용 가능한 금액 내에서 새로운 숫자 구성 가능
                new_number = main_number + str(j)  # 현재 숫자에 j를 추가한 새 숫자 생성
                dp[i - value[j]] = max(dp[i - value[j]], int(new_number))  # 새 숫자로 DP 갱신

# dp 테이블에서 가장 큰 값을 결과로 출력
ans = max(dp)
print(ans)
