def rotate_key(key):
    # 키 90도 회전 함수
    n = len(key)
    return [[key[n-1-j][i] for j in range(n)] for i in range(n)]


def check_key(key, lock_map, start_i, start_j, key_len, lock_len):
    # lock_map 복사
    tmp = [row[:] for row in lock_map]
    
    # 1. 충돌 검사 + 합쳐서 전체 채워지는지 확인
    for x in range(key_len):
        for y in range(key_len):
            if key[x][y] == 1:
                if tmp[start_i + x][start_j + y] == 1:
                    return False
                tmp[start_i + x][start_j + y] = 1
    
    # 2. lock 영역이 전부 1인지 확인
    st = key_len
    for i in range(lock_len):
        for j in range(lock_len):
            if tmp[st+i][st+j] != 1:
                return False
    return True


def solution(key, lock):
    key_len, lock_len = len(key), len(lock)
    
    # wide 맵으로 전체 크기 넓히기
    wide = key_len * 2 + lock_len
    lock_map = [[0] * wide for _ in range(wide)]
    
    for i in range(lock_len):
        for j in range(lock_len):
            lock_map[key_len + i][key_len + j] = lock[i][j]
    
    # 키 90도씩 회전하면서 가능한 모든 위치 체크
    for _ in range(4):
        max_start = wide - key_len
        for i in range(max_start + 1):
            for j in range(max_start + 1):
                if check_key(key, lock_map, i, j, key_len, lock_len):
                    return True
        key = rotate_key(key)
    
    return False
