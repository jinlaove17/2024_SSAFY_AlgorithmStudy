/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제의 사용된 알고리즘은 투포인터입니다.
    - left, right로 투포인터를 명명하고 right를 계속 증가시킵니다.
    - 그리고 Map을 이용해서 Key를 문자, Value를 문자의 개수로 지정하며 right가 증가함에 따라 해당 문자의 갯수를 map에 업데이트해줍니다.
    - 그러다가 map의 크기가 N을 초과하면 현재 left가 위치한 문자에 대해서 하나의 갯수를 줄이고 만약 특정 문자의 value가 0이되면 map에서 제거하고 left를 계속 증가시킵니다.
    - left를 증가시키다가 map의 크기가 N이하가 될 시에 해당 동작을 멈추고 right-left의 길이를 계산하여 최대 길이를 갱신합니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(1)
    - go 함수 : O(M) (M은 문자열의 길이)
    - 전체 시간복잡도: O(M)

실행 시간
   - 212ms(java)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static String str;
    static int left, right;
    static int result=0;
    static Map<Character, Integer> map;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        map = new HashMap<>();
        N = Integer.parseInt(st.nextToken());
        str = br.readLine().trim();
        left=0;
        right =0;
        go();
        System.out.println(result);
    }
    public static void go() {
        while(right<str.length()) {
            char ch = str.charAt(right);
            map.put(ch, map.getOrDefault(ch, 0)+1);
            right++;
            if(map.size()>N) {
                while(true) {
                    char c = str.charAt(left);
                    map.put(c, map.get(c)-1);
                    if(map.get(c)==0) {
                        map.remove(c);
                    }
                    left++;
                    if(map.size()<=N) {
                        break;
                    }
                }
            }
            result = Math.max(result, right-left);
        }
    }

}
