import java.util.*;
class Solution {
    public long solution(String expression) {
        long answer = Long.MIN_VALUE;
        String[][] oper = {{"+", "-", "*"},{"+", "*", "-"},{"-", "+", "*"},{"-", "*", "+"}, {"*", "-", "+"}, {"*", "+", "-"}};
        ArrayList<String> list = new ArrayList<>();
        int start=0;
        for(int i=0; i<expression.length(); i++){
            char ch = expression.charAt(i);
            if(ch == '+' || ch=='-' || ch=='*'){
                list.add(expression.substring(start, i));
                list.add(Character.toString(ch));
                start = i+1;
            }
        }
        list.add(expression.substring(start));
        for(int i=0; i<oper.length; i++){
            ArrayList<String> sub_list = new ArrayList<String>(list);
            for(int j=0; j<3; j++){
                for(int k=0; k<sub_list.size(); k++){
                    if(oper[i][j].equals(sub_list.get(k))){
                        sub_list.set(k - 1, calc(sub_list.get(k - 1), sub_list.get(k), sub_list.get(k + 1)));
                        sub_list.remove(k);
                        sub_list.remove(k);
                        k--;
                    }
                }
            }
            answer = Math.max(answer, Math.abs(Long.parseLong(sub_list.get(0))));
        }

        return answer;
    }
    public static String calc(String num1, String op, String num2){
        long n1= Long.parseLong(num1);
        long n2 = Long.parseLong(num2);
        if (op.equals("+"))
            return Long.toString(n1 + n2);
        else if (op.equals("-"))
            return Long.toString(n1 - n2);
        return Long.toString(n1 * n2);
    }
}