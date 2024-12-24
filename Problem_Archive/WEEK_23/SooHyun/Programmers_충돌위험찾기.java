/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심은 시키는데로 하는 것입니다.
    - 충돌위험이 발생하는지를 확인하기 위해서 각각 로봇이 목적지로 가는 경로를 추적해야합니다. 경로를 저장하기 위해 ArrayList 배열을 활용했습니다.
    - 주어진 조건에서 최단 경로가 여러 가지일 경우 r좌표가 변하는 이동을 c 좌표가 변하는 이동보다 먼저한다는 조건으로 인하여 목적지까지의 경로가 1개로 정해집니다.
    - 목적지까지의 경로를 추적해 이를 ArrayList에 저장한 이후에 각각의 시간에 충돌이 발생하는지 확인하기 위해 Map을 활용했습니다.
    - Map의 Key 값은 r좌표_c좌표로 구성했고 value 값으로 해당 위치에 존재하는 로봇의 갯수로 세팅했습니다.
    - 이를 통해 value값이 1개보다 커지게 되면 해당 시간대에 특정 좌표에 존재하는 로봇의 갯수가 2개 이상으로 충돌일 발생하는 것을 확인하며 정답을 도출합니다.

시간 복잡도
	- 각 로봇의 경로 추적 및 저장
	    - 외부 루프 : O(R) (R : routes 배열의 길이)
	    - 내부 루프 : O(Li) (Li : routes 배열의 각 원소의 길이)
	    - 경로 계산 및 저장 : 최악의 경우 전체 N개의 좌표를 모두 포함 O(N^2)
	    - 시간복잡도: O(R*N^2)

    - 충돌 여부 확인
        - 외부 루프 : O(T) (T : 최대 시간)
        - 내부 루프 : O(R) (R : routes 배열의 길이)
        - 시간복잡도: O(T*R)

    - 전체 시간복잡도: O(R*N^2) + O(T*R) = O(R*N^2 + T*R)

실행 시간
	- 347.18ms(java)

*/
import java.util.*;
class Solution {
    public ArrayList<int[]>[] list;
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;
        list = new ArrayList[routes.length];
        for(int i=0; i<routes.length; i++){
            list[i] = new ArrayList<>();
        }
        for(int i =0; i<routes.length; i++){
            for(int j=0; j<routes[i].length; j++){
                if(j==0){
                    int idx = routes[i][j]-1;
                    list[i].add(new int[]{points[idx][0], points[idx][1]});
                }else{
                    int prev = routes[i][j-1]-1;
                    int target = routes[i][j]-1;
                    int prev_x = points[prev][0];
                    int prev_y = points[prev][1];
                    int target_x = points[target][0];
                    int target_y = points[target][1];
                    while(prev_x != target_x){
                        if(prev_x < target_x){
                            prev_x++;
                        }else if(prev_x > target_x){
                            prev_x--;
                        }
                        list[i].add(new int[]{prev_x, prev_y});
                    }
                    while(prev_y != target_y){
                        if(prev_y < target_y){
                            prev_y++;
                        }else if(prev_y > target_y){
                            prev_y--;
                        }
                        list[i].add(new int[]{prev_x, prev_y});
                    }
                }
            }
        }
        int max_time=0;
        for(int i=0; i<list.length; i++){
            max_time = Math.max(max_time, list[i].size());
        }
        for(int i=0; i<max_time; i++){
            Map<String, Integer> map = new HashMap<>();
            int time=i;
            for(int j=0; j<list.length; j++){
                if(time < list[j].size()){
                    String position = list[j].get(i)[0]+"_"+list[j].get(i)[1];
                    map.put(position, map.getOrDefault(position, 0)+1);
                }
            }
            for(String key : map.keySet()){
                if(map.get(key) >1){
                    answer++;
                }
            }
        }
        return answer;
    }
}