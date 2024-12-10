/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에서 주어진 7가지 패턴에 대해 각각의 패턴에 대해 조건을 만족하는지 확인하면 됩니다. 즉 시키는데로 하라는 것입니다.
	- 각 패턴과 해당 패턴에서 회전하는 모든 경우에 대해 조건을 만족하는지 확인하는 함수를 만들어서 패턴에 대해 확인하면 됩니다.
	- 여기서 공통적인 부분을 추출하는데 이는 맨 밑단에서 높이가 변하는 것이 없는 평평한 경우에 비교하는 것을 하나의 함수로 빼 코드의 가독성을 높였습니다. 이 부분이 check 함수입니다.
	- 그리고 find함수를 선언하고 모든 경우에 대해서 가능한지를 확인하는 것을 추가함으로써 정답을 도출했습니다.

시간 복잡도
	- find() 함수 : O(C)
	- check() : O(len) => 주어진 문제에서 len은 최대 4이므로 이는 상수이므로 O(1)
	- 전체 시간 복잡도 : O(C)

실행 시간
	- 104ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] height;
    static int C,P;
    static int answer=0;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        C = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        height = new int[C];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<C; i++) {
            height[i] = Integer.parseInt(st.nextToken());
        }
        find();
        System.out.println(answer);
    }
    public static void find() {
        if(P==1) {
            answer+=C;
            for(int i=0; i<=C-4; i++) {
                answer+=check(i,4);
            }
        }else if(P==2) {
            for(int i=0; i<=C-2; i++) {
                answer+=check(i,2);
            }
        }else if(P==3) {
            for(int i=0; i<=C-3; i++) {
                if(height[i] == height[i+1] && height[i+1] == height[i+2]-1) {
                    answer++;
                }
            }
            for(int i=0; i<=C-2; i++) {
                if(height[i] == height[i+1]+1) {
                    answer++;
                }
            }
        }else if(P==4) {
            for(int i=0; i<=C-3; i++) {
                if(height[i]-1 == height[i+1] && height[i+1] == height[i+2]) {
                    answer++;
                }
            }
            for(int i=0; i<=C-2; i++) {
                if(height[i] == height[i+1]-1) {
                    answer++;
                }
            }
        }else if(P==5) {
            for(int i=0; i<=C-3; i++) {
                answer+=check(i,3);
            }
            for(int i=0; i<=C-2; i++) {
                if(height[i]==height[i+1]-1) {
                    answer++;
                }
            }
            for(int i=0; i<=C-3; i++) {
                if(height[i]==height[i+1]+1 && height[i+2]==height[i+1]+1) {
                    answer++;
                }
            }
            for(int i=0; i<=C-2; i++) {
                if(height[i]==height[i+1]+1) {
                    answer++;
                }
            }
        }else if(P==6) {
            for(int i=0; i<=C-3; i++) {
                answer+=check(i,3);
            }
            for(int i=0; i<=C-2; i++) {
                answer+=check(i,2);
            }
            for(int i=0; i<=C-3; i++) {
                if(height[i]+1 == height[i+1] && height[i+1] == height[i+2]) {
                    answer++;
                }
            }
            for(int i=0; i<=C-2; i++) {
                if(height[i]==height[i+1]+2) {
                    answer++;
                }
            }
        }else {
            for(int i=0; i<=C-3; i++) {
                answer+=check(i,3);
            }
            for(int i=0; i<=C-2; i++) {
                answer+=check(i,2);
            }
            for(int i=0; i<=C-3; i++) {
                if(height[i] == height[i+1] && height[i+1] == height[i+2]+1) {
                    answer++;
                }
            }
            for(int i=0; i<=C-2; i++) {
                if(height[i]==height[i+1]-2) {
                    answer++;
                }
            }
        }
    }

    public static int check(int start, int len) {
        int h = height[start];
        for(int i=start+1; i<start+len; i++) {
            if(h != height[i])
                return 0;
        }
        return 1;
    }
}
