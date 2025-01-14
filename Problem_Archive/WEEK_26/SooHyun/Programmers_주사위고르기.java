/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심은 시키는대로 하는 것 즉 빡구현 문제입니다. 사용한 알고리즘은 dfs 탐색과 백트래킹입니다.
	- 주사위는 반드시 짝수 개가 주어지고 이를 반반으로 나누어야합니다. 그리고 주사위는 우리가 일반적으로 아는 1 ~ 6까지의 숫자가 아닌 랜덤한 숫자(중복 허용) 숫자이므로 이를 처리하기 위해 HashMap을 사용했습니다.
	- 주사위 갯수만큼의 HashMap 배열을 선언하고 i번쨰의 주사위에 특정 눈이 몇 개 존재하는지 저장했습니다.
    - 그 후에 dfs를 통해 주사위를 반반으로 나누는 모든 경우의 수를 탐색하였습니다.
    - dfs의 기저 조건은 depth가 len의 절반 즉 주사위가 반반으로 나눈 경우입니다. 그 전까지 check 배열을 true로 전환해 i번쨰 주사위를 사용됐다는 것을 표시했습니다.
    - 이후 기저 조건에 걸리면 두 개의 ArrayList를 선언해 A가 사용한 주사위, B가 사용한 주사위를 list에 저장하고 두 개의 HashMap을 선언하여 가지고 있는 주사위들로 나올 수 있는 모든 경우의 수를 저장하는 함수 calculateCase를 호출했습니다.
    - 이후 2개의 HashMap을 탐색하는 이중 반복문을 이용해 각각의 key값 비교를 통해 A가 이기는 경우 해당 amap의 value와 bmap의 value를 곱함으로써 경우의 수를 더해줍니다.
    - 이후 이 값이 result보다 크다면 result를 갱신하고 answer 배열에 해당 주사위 번호를 저장합니다.

시간 복잡도
	- 초기화 및 데이터 분류 : O(N * M) (N : 주사위의 개수, M : 주사위의 눈의 개수)
    - DFS 탐색 : O(2 ^ (N/2)) (N : 주사위의 개수)
    - calculateCase 함수 : O(M ^ (N/2)) (N : 주사위의 개수, M : 주사위의 눈의 개수)
    - 두 HashMap 비교 : O(K * P) (K : amap의 key 개수, P : bmap의 key 개수)
    - 전체 시간복잡도: O(2 ^ (N/2) * (M ^ (N/2) + K * P))

실행 시간
    - 정확성 테스트 : 963.98ms(java)
*/
import java.util.*;
class Solution {
    int len;
    int result=0;
    boolean[] check;
    int[] answer;
    HashMap<Integer, Integer>[] map;
    public int[] solution(int[][] dice) {
        len = dice.length;
        answer = new int[len/2];
        check = new boolean[len];
        map = new HashMap[len+1];
        for(int i=0; i<=len; i++){
            map[i] = new HashMap<>();
        }
        for(int i=0; i<dice.length; i++){
            for(int j=0; j<dice[i].length; j++){
                map[i].put(dice[i][j], map[i].getOrDefault(dice[i][j],0)+1);
            }
        }
        dfs(0,0, dice);
        return answer;
    }
    public void dfs(int idx, int depth, int[][] dice){
        if(depth == len/2){
            ArrayList<Integer> alist = new ArrayList<>();
            ArrayList<Integer> blist = new ArrayList<>();
            for(int i=0; i<len; i++){
                if(check[i]){
                    alist.add(i);
                }else{
                    blist.add(i);
                }
            }
            HashMap<Integer, Integer> amap = new HashMap<>();
            calculateCase(0, 0, alist, amap, dice);

            HashMap<Integer, Integer> bmap = new HashMap<>();
            calculateCase(0, 0, blist, bmap, dice);
            int awin=0;
            for(Integer akey : amap.keySet()){
                int anum = akey;
                for(Integer bkey : bmap.keySet()){
                    int bnum = bkey;
                    if(anum > bnum){
                        awin += amap.get(anum) * bmap.get(bnum);
                    }
                }
            }
            if(awin > result){
                result = awin;
                for(int i=0; i<answer.length; i++){
                    answer[i] = alist.get(i)+1;
                }
            }
            return;
        }
        for(int i=idx; i<len; i++){
            check[i]=true;
            dfs(i+1, depth+1, dice);
            check[i]=false;
        }
    }

    public void calculateCase(int depth, int sum, ArrayList<Integer> list, HashMap<Integer, Integer> map, int[][] dice){
        if(depth == list.size()){
            if(map.containsKey(sum))
                map.put(sum, map.get(sum)+1);
            else
                map.put(sum,1);
            return;
        }
        int idx = list.get(depth);
        for(int j=0; j<dice[idx].length; j++){
            int num = dice[idx][j];
            calculateCase(depth+1, sum+num, list, map, dice);
        }
    }
}