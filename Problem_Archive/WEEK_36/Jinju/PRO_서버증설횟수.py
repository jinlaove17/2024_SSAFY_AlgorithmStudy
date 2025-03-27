def solution(players, m, k):
    res = 0
    server = [0]*24
    for hour in range(24):
        req = players[hour]//m
        if req > server[hour]:
            plus = req - server[hour]
            for i in range(k):
                if hour + i < 24:
                    server[hour+i] += plus
            res += plus
    return res
