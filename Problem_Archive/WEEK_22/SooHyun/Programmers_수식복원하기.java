/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제를 풀기 전에 너무 어렵게 생각을 했습니다. 그러나 가능한 가짓수가 2~9진수 총 8가지이므로 가능한 가짓수를 전부해보는 구현문제였습니다.
	- X가 포함된 것 포함되지 않은 것을 따로 저장해야하므로 2개의 ArrayList를 선언하여 저장합니다.
	- 이후 가능한 n진수를 찾는 것이 우선이라고 판단했습니다. 수식에 주어진 자릿 수 중 가장 큰 것보다 하나 큰 수부터 가능한 n진수입니다.
	- 이는 getMaxNumber라는 함수를 통해 구현하였습니다.
	- calculate함수를 통해서 가능한 n진수들에 대해서 Map을 업데이트 합니다. 이때 Map의 key는 n진수이고 value는 해당 n진수가 가능한 수식의 갯수입니다.
	- Map의 value가 know의 size와 같다면 해당 n진수가 가능합니다.
	- 이후 unknow 리스트를 돌면서 Map의 value가 know의 size와 같은 n진수로 값을 계산해서 Set에 저장합니다.
	- 만약 Set의 크기가 1이라면 해당 n진수가 정답이고 아니라면 ?로 처리합니다.

시간 복잡도
	- 초기화 및 데이터 분류
	    - init 함수 호출: O(1)
	    - expressions 배열 분류 (know와 unknow): O(E) (E : expressions 배열의 길이)
	    - 시간복잡도: O(E)
    - know 리스트에 대한 처리
        - 최대 숫자 계산(getMaxNumber) : O(K*L) (K : know 리스트의 길이, L : 표현식의 평균 길이)
        - 가능한 진법 계산(calcuate) : O(8*K*L)
        - 시간복잡도: O(K*L)
    - unknow 리스트에 대한 처리
        - 최대 숫자 계산(getMaxNumber) : O(U*L) (U : unknow 리스트의 길이, L : 표현식의 평균 길이)
        - 가능한 진법 계산(calcuate) : O(8*U*L)
        - 시간복잡도: O(U*L)
    - 전체 시간복잡도: O(E) + O(K*L) + O(U*L) = O(E + K*L + U*L)

실행 시간
	- 17.44ms(java)

*/
import java.util.*;
class Solution {
    public Map<Integer, Integer> map;
    public void init(){
        map = new HashMap<>();
        map.put(2,0);
        map.put(3,0);
        map.put(4,0);
        map.put(5,0);
        map.put(6,0);
        map.put(7,0);
        map.put(8,0);
        map.put(9,0);
    }
    public String[] solution(String[] expressions) {
        init();
        ArrayList<String> know = new ArrayList<>();
        ArrayList<String> unknow = new ArrayList<>();
        int maxNumByKnow=0;
        int minBaseByKnow=0;
        int maxNumByUnKnow=0;
        int minBaseByUnKnow=0;
        for(int i=0; i<expressions.length; i++){
            String exp = expressions[i];
            if(exp.charAt(exp.length()-1) =='X'){
                unknow.add(exp);
            }else{
                know.add(exp);
            }
        }

        for(String e : know){
            String[] arr = e.split(" ");
            int maxNum = Math.max(getMaxNumber(arr[0]), getMaxNumber(arr[2]));
            maxNum = Math.max(maxNum, getMaxNumber(arr[4]));
            maxNumByKnow = Math.max(maxNum, maxNumByKnow);
        }

        minBaseByKnow = maxNumByKnow+1;

        for(String e : know){
            String[] arr = e.split(" ");
            String op = arr[1];
            for(int i=minBaseByKnow; i<=9; i++){
                calculate(arr, op, i);
            }
        }

        for(String e : unknow){
            String[] arr = e.split(" ");
            int maxNum = Math.max(getMaxNumber(arr[0]), getMaxNumber(arr[2]));
            maxNumByUnKnow = Math.max(maxNum, maxNumByUnKnow);
        }

        minBaseByUnKnow=maxNumByUnKnow+1;

        String[] answer = new String[unknow.size()];
        int idx=0;
        for(String e : unknow){
            String[] arr = e.split(" ");
            String op = arr[1];
            Set<String> resultSet = new HashSet<>();
            for(int i=minBaseByUnKnow; i<=9; i++){
                int cnt = map.get(i);
                if(cnt == know.size()){
                    calculateSet(arr, op, i, resultSet);
                }
            }
            String str = arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " ";
            if(resultSet.size()==1){
                Iterator<String> iter = resultSet.iterator();
                if(iter.hasNext()){
                    answer[idx++] = str + iter.next();
                }

            }else{
                answer[idx++] = str + "?";
            }
        }

        return answer;
    }

    public int getMaxNumber(String number){
        int maxNum=0;
        for(int i=0; i<number.length(); i++){
            int num = Integer.parseInt(Character.toString(number.charAt(i)));
            maxNum = Math.max(num, maxNum);
        }
        return maxNum;
    }

    public boolean isValid(String number, int base){
        for(int i=0; i<number.length(); i++){
            char ch = number.charAt(i);
            int num = Integer.parseInt(Character.toString(ch));
            if(num <0 || num >=base)
                return false;
        }
        return true;
    }

    public void calculate(String[] arr, String op, int base){
        if(!isValid(arr[0], base) || !isValid(arr[2], base) || !isValid(arr[4], base)){
            return;
        }
        int num1 = Integer.parseInt(arr[0], base);
        int num2 = Integer.parseInt(arr[2], base);
        int result_num = Integer.parseInt(arr[4], base);
        int result = op.equals("+") ? num1+num2 : num1 - num2;
        if(result_num == result){
            map.put(base,map.get(base)+1);
        }
    }

    public void calculateSet(String[] arr, String op, int base, Set<String> resultSet){
        if(!isValid(arr[0], base) || !isValid(arr[2], base)){
            return;
        }
        int num1 = Integer.parseInt(arr[0], base);
        int num2 = Integer.parseInt(arr[2], base);
        int result = op.equals("+") ? num1+num2 : num1 - num2;
        resultSet.add(Integer.toString(result, base));
    }
}