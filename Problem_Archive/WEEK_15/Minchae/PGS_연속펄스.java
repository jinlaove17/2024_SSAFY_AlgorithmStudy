class Solution {
    public long solution(int[] sequence) {
        long sum1 = 0;
        long sum2 = 0;
        int pulse = 1;
        
        long min1 = 0;
        long min2 = 0;
        
        long answer = 0;
        
        for (int i = 0; i < sequence.length; i++) {
            // 1로 시작하거나 -1로 시작하는 펄스 수열을 곱한 것의 누적합
            sum1 += sequence[i] * pulse;
            sum2 += sequence[i] * (-pulse);
            
            // 최댓값 갱신 -> 현재 누적합에서 최소 누적합 값을 뺀 것과 비교 -> 최댓값 증가량을 찾을 수 있음
            // 예를 들어 산을 오를 때 가장 높은 지점을 찾는 것과 비슷 -> 가장 높은 지점에서 가장 낮은 지점을 빼면 가장 큰 차이를 구할 수 있음
            answer = Math.max(answer, Math.max(sum1 - min1, sum2 - min2));
            
            // 누적합에서 최소값 갱신
            min1 = Math.min(min1, sum1);
            min2 = Math.min(min2, sum2);
            
            pulse *= -1;
        }
        
        return answer;
    }
}
