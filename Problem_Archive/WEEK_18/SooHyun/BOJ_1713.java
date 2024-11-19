/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제의 핵심은 시키는대로 구현하는 것입니다.
	- 문제 해결을 위해 Studenet를 선언하고 idx : 몇 번째로 들어왔는지,  num : 학생 번호, recommend : 추천 횟수 3가지 속성을 가집니다.
	- List<Student>를 선언하고 만약 list의 사이즈가 N보다 작은 경우에는 추가합니다. 단 중복된 학생 번호가 있는 경우에는 해당 학생의 추천 횟수를 증가시킵니다.
	- 만약 list의 사이즈가 N보다 큰 경우에는 주어진 정렬 조건에 맞춰 Comparable 인터페이스의 compareTo를 오버라이딩하여 정렬합니다.
	- 이후 StringBuilder를 이용하여 list의 학생 번호를 출력합니다.

시간 복잡도
	- 리스트 검색 : O(N)
	- 리스트 정렬 : O(N * log N)
	- 전체 시간 복잡도 : O(M * N * logN)

실행 시간
	- 136ms(java)

*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine().trim());
        M = Integer.parseInt(st.nextToken());
        List<Studenet> list = new ArrayList<>();
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<M; i++) {
            int num = Integer.parseInt(st.nextToken());
            if(list.size() <N) {
                boolean flag=true;
                for(Studenet s : list) {
                    if(s.num == num) {
                        s.recommend++;
                        flag=false;
                        break;
                    }
                }
                if(flag) {
                    list.add(new Studenet(i,num, 1));
                }
            }else {
                Collections.sort(list);
                boolean flag=true;
                for(Studenet s : list) {
                    if(s.num == num) {
                        s.recommend++;
                        flag=false;
                        break;
                    }
                }
                if(flag) {
                    list.remove(0);
                    list.add(new Studenet(i,num, 1));
                }
            }
        }
        Collections.sort(list, (o1,o2)->{
            return o1.num-o2.num;
        });
        StringBuilder sb = new StringBuilder();
        for(Studenet s : list) {
            sb.append(s.num+" ");
        }
        System.out.println(sb.toString());
    }
    static class Studenet implements Comparable<Studenet>{
        int idx;
        int num;
        int recommend;

        public Studenet(int idx, int num, int recommend) {
            this.idx=idx;
            this.num=num;
            this.recommend=recommend;
        }

        @Override
        public int compareTo(Studenet o) {
            // TODO Auto-generated method stub
            if(this.recommend==o.recommend) {
                return this.idx-o.idx;
            }
            return this.recommend-o.recommend;
        }
    }
}
