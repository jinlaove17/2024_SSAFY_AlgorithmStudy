/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 배열의 첫번째 값이 완호의 근무 태도 점수와 동료 평가 점수이므로 각각 n,m변수에 할당합니다.
	- 이후 근무 태도 점수를 기준으로 내림차순 정렬을 수행하고 만약 같을 시에 동료 평가 점수를 기준으로 오름차순 정렬을 합니다.
	- 이후 max_score를 정렬 이후 첫번째 배열의 동료 평가 점수로 할당합니다.
	- 이후 배열의 값을 순회하는데 배열을 정렬한 상태이므로 다음에 오는 값은 현재의 근무 태도 점수보다 작거나 혹은 같은 경우이다.
	- 만약 현재 배열의 동료 평가 점수가 이전의 동료 평가 점수보다 낮다면 해당 배열의 근무 태도 점수와 동료 평가 점수는 모두 이전의 근무 태도 점수와 동료 평가 점수보다 낮다는 것으므로 그 사람은 인텐티브를 받지 못하므로 근무 태도 점수와 동료 평가 점수을 -1로 할당합니다.
	- 만약 그가 아닌 경우 현재 동료 평가 점수로 max_score를 할당합니다.
	- 이후 근무 태도 점수와 동료 평가 점수의 합을 기준으로 내림차순으로 배열을 재정렬합니다.
	- 이후 n+m보다 큰 경우에 계속 answer를 증가시키면서 정답을 도출합니다.

시간 복잡도
	- 첫 번째 정렬 : Array.sort를 수행하므로 O(N log N)의 시간복잡도를 갖습니다.
	- 첫 번째 루프 : O(N)
	- 두 번째 정렬 : Array.sort를 수행하므로 O(N log N)의 시간복잡도를 갖습니다.
	- 두 번째 루프 : O(N)
	- 전체 시간복잡도 : O(N log N)

*/

import java.util.*;
class Solution {
    public int solution(int[][] scores) {
        int answer = 1;
        int n = scores[0][0];
        int m = scores[0][1];
        Arrays.sort(scores,(o1,o2)->{
            if(o1[0]==o2[0]){
                return o1[1]-o2[1];
            }
            return o2[0]-o1[0];
        });
        int max_score = scores[0][1];
        for(int i=1; i<scores.length; i++){
            if(scores[i][1]<max_score){
                if(scores[i][0] == n && scores[i][1]==m){
                    return -1;
                }
                scores[i][0]=-1;
                scores[i][1]=-1;
            }else{
                max_score=scores[i][1];
            }
        }
        Arrays.sort(scores, (o1,o2)->{
            return (o2[0]+o2[1]) - (o1[0]+o1[1]);
        });
        for(int i=0; i<scores.length; i++){
            if(scores[i][0]+scores[i][1] > n+m){
                answer++;
            }else{
                break;
            }
        }
        return answer;
    }
}