/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음 문제를 읽었을 때 물통 a의 용량이 b의 용량보다 작은 것을 파악하고 수학적으로 접근하려고 했습니다.
	- b를 a로 나눈 몫과 나머지를 이용해서 구하려고 했으나 너무나 많은 상황을 고려해야 했고 생각지 못한 곳에서 반례 또한 발생할 것이라고 판단했습니다.
	- 대민채의 힌트를 통해서 주어진 행동들을 모두 탐색하면서 최소 횟수를 찾아야 한다는 것을 알게 되었습니다.
	- A를 가득 채우기 -> B를 가득 채우기 -> A를 비우기 -> B를 비우기 -> A에서 B로 옮기기 -> B에서 A로 옮기기 순으로 bfs 탐색을 진행합니다.
	- 그 후 A와 B의 현재 물의 상태를 문자열로 만듭니다. 단 여기서 사이에 _를 넣었습니다. 만약 _로 구분하지 않는다면 ex) A :12, B : 3 => 123 / A :1, B : 23 => 123 즉 다른 상태에 대해 같은 문자열이 만들어집니다.
    - 이후 만들어진 문자열을 set에 넣어서 중복을 방지하고 C와 D와 같아지면 결과를 반환합니다.
    - 만약 결과가 나오지 않는다면 -1을 반환합니다.

시간 복잡도
	- BFS : 최악의 경우 O(A*B) => A 물통에 넣을 수 있는 용량 A, B 물통에 넣을 수 있는 용량 B
	- HashSet을 통한 중복 검사: O(1)
	- 전체 시간 복잡도 : O(A*B)


실행 시간
	- 1164ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static int A, B, C, D;
    static Set<String> set;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        set = new HashSet<>();
        int result=bfs();
        System.out.println(result);
    }

    public static int bfs() {
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {0,0,0});
        set.add("00");
        while(!que.isEmpty()) {
            int[] arr = que.poll();
            int current_A=arr[0];
            int current_B=arr[1];
            int count=arr[2];
            if(current_A == C && current_B==D) {
                return count;
            }
            for(int i=0; i<6; i++) {
                int next_A =current_A;
                int next_B = current_B;

                if(i==0) {
                    if(next_A==A) {
                        continue;
                    }
                    next_A=A;
                }else if(i==1) {
                    if(next_B==B) {
                        continue;
                    }
                    next_B=B;
                }else if(i==2) {
                    if(next_A==0) {
                        continue;
                    }
                    next_A=0;
                }else if(i==3) {
                    if(next_B==0) {
                        continue;
                    }
                    next_B=0;
                }else if(i==4) {
                    if(next_A==0)
                        continue;
                    if(next_B+next_A >B) {
                        int sub=next_B+next_A-B;
                        next_B=B;
                        next_A=sub;
                    }else {
                        next_B+=next_A;
                        next_A=0;
                    }
                }else {
                    if(next_B==0)
                        continue;
                    if(next_A+next_B >A) {
                        int sub=next_A+next_B-A;
                        next_A=A;
                        next_B=sub;
                    }else {
                        next_A+=next_B;
                        next_B=0;
                    }
                }
                String str = next_A+"_"+next_B;
                if(set.contains(str))
                    continue;
                set.add(str);
                que.add(new int[] {next_A, next_B, count+1});
            }
        }
        return -1;
    }

}
