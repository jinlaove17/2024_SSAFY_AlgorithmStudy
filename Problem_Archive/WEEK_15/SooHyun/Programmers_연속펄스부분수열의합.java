/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 전형적인 누적합 문제이다.
	- 입력에 대한 배열을 생성하고 이후 1- > -1 -> ..., -1- > 1 -> ... 을 곱한 2개의 배열을 생성한다.
	- 이후 누적합 로직을 적용해서 i번째 배열의 자체값과 i-1번째 누적합과 자체값을 더한 값 중 더 큰 값을 배열에 저장한다.
	- 이를 수행한 후 2개의 배열을 정렬하고 2개의 배열의 마지막 값 중 큰 값을 정답에 할당합니다.

시간 복잡도
	- 첫 번째 반복문 : O(N)
	- 두 번째 반복문 : O(N)
    - 정렬 : O(N log N)
    - 전체 시간복잡도 : O(N log N)
*/

import java.util.*;
class Solution {
    public long[] seq1;
    public long[] seq2;
    public long[] result1;
    public long[] result2;
    public long solution(int[] sequence) {
        long answer = 0;
        seq1 = new long[sequence.length];
        seq2 = new long[sequence.length];
        result1 = new long[sequence.length];
        result2 = new long[sequence.length];
        int num=1;
        for(int i=0; i<sequence.length; i++){
            seq1[i] = sequence[i]*num;
            num= -num;
            seq2[i] = sequence[i]*num;
        }
        result1[0]=seq1[0];
        result2[0]=seq2[0];
        for(int i=1; i<sequence.length; i++){
            result1[i] = Math.max(seq1[i], seq1[i]+result1[i-1]);
            result2[i] = Math.max(seq2[i], seq2[i]+result2[i-1]);
        }
        Arrays.sort(result1);
        Arrays.sort(result2);
        answer = Math.max(result1[sequence.length-1], result2[sequence.length-1]);
        return answer;
    }
}