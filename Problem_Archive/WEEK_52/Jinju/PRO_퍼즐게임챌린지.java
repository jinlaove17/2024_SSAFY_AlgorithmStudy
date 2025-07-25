import java.util.*;

// 전형적인 이분 탐색 문제
// 그치만.. 자료형(long, int) 타입 처리와 조건을 꼼꼼하게 읽는게 중요해서 조금 지저분한 문제라고 느꼈다.

class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        long st = 1, ed = Long.MAX_VALUE-1;

        while (st < ed) {
            long mid = (st+ed)/2;
            if (canSolve(diffs, times, limit, mid)) {
                ed = mid;
            } else {
                st = mid + 1;
            }
        }
        return (int) st;
    }

    public boolean canSolve(int[] diffs, int[] times, long limit, long level) {
        long curTime = 0;

        for (int i = 0; i < diffs.length; i++) {
            if (diffs[i] <= level) {
                curTime += times[i];
            } else {
                int curCnt = (int)(diffs[i]-level);
                if (i == 0) {
                    curTime += (long) curCnt*times[i] + times[i];
                } else {
                    curTime += (long) curCnt*(times[i]+times[i-1]) + times[i];
                }
            }

            if (curTime > limit) return false;
        }
        return true;
    }
}
