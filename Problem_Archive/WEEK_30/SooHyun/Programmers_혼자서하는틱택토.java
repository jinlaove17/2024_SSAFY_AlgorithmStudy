/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 해당 문제의 핵심은 불가능한 상황의 케이스를 파악하는 것입니다.
	- 불가능한 상황의 케이스는 다음과 같습니다.
        1. O가 이긴 경우에 X가 이기는 경우 => 즉 O가 빙고를 만드는데 X의 빙고가 존재하는 경우
        2. O의 말 갯수보다 X의 말 갯수가 많은 경우
        3. O의 말의 갯수와 X의 말의 갯수의 차이 즉 O의 말 갯수 - X의 말 갯수 > 1인 경우
        4. O만 빙고를 만들었는데 O의 말의 갯수와 X의 말의 갯수가 같은 경우
        4. X만 빙고를 만들었는데 O의 말의 갯수와 X의 말의 갯수가 같지 않은 경우
	- 이에 따라 불가능한 상황의 케이스를 파악하고 해당 사항에 걸리지 않으면 가능한 경우입니다.

시간 복잡도
	- 초기화 : O(9) -> O(1)
	- clear 함수 : O(1)
    - 전체 시간복잡도: O(1)

실행 시간
    - 0.05ms(java)
*/
import java.util.*;
class Solution {
    char[][] map;
    int cnt_O=0;;
    int cnt_X=0;
    boolean flag1;
    boolean flag2;
    public int solution(String[] board) {
        map = new char[3][3];
        for(int i=0; i<3; i++){
            String b = board[i];
            for(int j=0; j<3; j++){
                map[i][j] = b.charAt(j);
                if(map[i][j]=='O'){
                    cnt_O++;
                }
                if(map[i][j]=='X'){
                    cnt_X++;
                }
            }
        }
        if(cnt_O ==0 && cnt_X==0)
            return 1;
        if(cnt_O < cnt_X || cnt_O - cnt_X >1)
            return 0;
        flag1 = clear('O');
        flag2 = clear('X');
        if(flag1 && flag2)
            return 0;
        if(flag1){
            if(cnt_O==cnt_X)
                return 0;
        }
        if(flag2){
            if(cnt_O != cnt_X)
                return 0;
        }
        return 1;
    }
    public boolean clear(char ch){
        for(int i=0; i<3; i++){
            if(map[i][0] == ch && map[i][1] == ch && map[i][2] == ch)
                return true;
            if(map[0][i] == ch && map[1][i] == ch && map[2][i] == ch)
                return true;
        }
        if(map[0][0] == ch && map[1][1] == ch && map[2][2] == ch)
            return true;
        if(map[0][2] == ch && map[1][1] == ch && map[2][0] == ch)
            return true;
        return false;
    }
}