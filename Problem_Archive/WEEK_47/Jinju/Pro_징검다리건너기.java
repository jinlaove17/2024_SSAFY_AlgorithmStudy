class Solution {
    public int solution(int[] stones, int k) {
        int left = 1, right = 200_000_000, ans = 0;
        
        // 관찰 1. 가장 작은 디딤돌부터 없어진다
        // 관찰 2. result를 구하는 판정 문제로 바꿀 수 있는지?
        // 관찰 3. 숫자가 엄청 큰 데, 이분 탐색이나 매개변수 탐색으로 바꿀 수 없나??
        // 관찰 4. 바로 옆에 있는 디딤돌로만 움직여야 한다. 
        //  -> 즉 앞에 가능한 모든 디딤돌을 밟아야한다
        //  -> 물의 수위가 차오르는 것처럼, 탈출한 명수(result)를 가지고 판정 문제로 바꾼다.
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canCross(stones, k, mid)) {
                ans = mid;
                left = mid+1;
            } else {
                right = mid-1; 
            }
        }
        return ans;
    }

    // mid 명수가 건널 수 있는지 판정
    private boolean canCross(int[] stones, int k, int people) {
        int zeroCount = 0;
        for (int s : stones) {
            // people 명수 만큼 지나갔다고 가정했을 때 남은 내구도
            if (s < people) {
                if (++zeroCount >= k) {
                    // 연속으로 k개 이상 못 밟으면 실패
                    return false;
                }
            } else {
                // 카운터 리셋
                zeroCount = 0;
            }
        }
        return true;
    }
}
