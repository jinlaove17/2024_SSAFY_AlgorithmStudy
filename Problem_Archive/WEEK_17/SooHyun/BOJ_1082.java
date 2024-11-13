/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 가지고 있는 M으로 만들 수 있는 가장 큰 수를 만들기 위해서는 두 가지 조건이 만족되어야 합니다.
	- 첫째, 자리수가 길어야 한다. 둘째, 자리수가 가장 긴 상황에서 큰 자리 수에 위치한 숫자가 커야합니다.
	- 자리수를 가장 길게 만들 수 있는 방법은 가장 싼 가격의 숫자를 가장 많이 사용하는 경우가 됩니다.
	- 이후 가장 큰 자리 수부터 그 상황에서 사용할 수 있는 가장 큰 수로 교체하는 것입니다.
	- 이를 위해 Info라는 객체를 선언하고 List를 선언하고 입력을 받은 이후 가격이 싼 순으로 정렬합니다.
	- 그리고 가장 싼 가격의 숫자를 계속적으로 사용합니다. 단 가장 큰 자리수에 가장 싼 것을 이용하는 경우 그 인덱스 값이 0이면 안됩니다. 0인 경우 그 다음으로 가격이 싼 숫자의 인덱스를 사용합니다.
	- 이를 통해 만들 수 있는 가장 긴 자리수의 숫자를 만듭니다.
	- 이후 가장 큰 자리수부터 남아 있는 M과 해당 자리수의 가격을 더한 값보다 작거나 같고 현 자리수의 인덱스보다 가장 큰 인덱스를 갖춘 수로 교체합니다.
	- 결과적으로 그리디 알고리즘을 통해 답을 도출합니다.

시간 복잡도
	- 초기화 : 입력을 처리하고 list와 answer 리스트를 초기화 그리고 정렬 작업에 대한 시간 복잡도는 O(N*logN)입니다.
	- 남은 금액 M으로 가능한 최대 길이의 숫자를 생성은 O(M/최소비용)입니다. 단 최소비용이 최악의 경우 1이므로 최악의 시간복잡도는 O(M)입니다.
	- 자리 수 교체 : 각 자리수에서 가능한 교체를 탐색하는 이중 루프를 돌고 최악의 경우 O(answer의 길이 * N)의 시간복잡도인데 앞서 말한 것과 같이 최대의 자리수는 M이므로 O(M * N)
	- 전체 시간 복잡도 : O(N*logN + M + M*N) = O(M*N)


실행 시간
	- 104ms(java)

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static List<Info> list;
    static List<Info> answer;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        list = new ArrayList<>();
        answer = new ArrayList<>();
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<N; i++) {
            int cost = Integer.parseInt(st.nextToken());
            list.add(new Info(i,cost));
        }
        st = new StringTokenizer(br.readLine().trim());
        M = Integer.parseInt(st.nextToken());
        Collections.sort(list);

        if(N==1) {
            sb.append(0);
        }
        else {
            find();
        }
        System.out.println(sb.toString().trim());
    }
    public static void find() {
        if(list.get(0).num==0) {
            if(list.get(1).cost > M) {
                sb.append(0);
                return;
            }
            answer.add(new Info(list.get(1).num, list.get(1).cost));
            M-=list.get(1).cost;
        }else {
            answer.add(new Info(list.get(0).num, list.get(0).cost));
            M-=list.get(0).cost;
        }
        while(true) {
            if(M>=list.get(0).cost) {
                answer.add(new Info(list.get(0).num, list.get(0).cost));
                M-=list.get(0).cost;
            }else {
                break;
            }
        }
        for(int i=0; i<answer.size(); i++) {
            for(int j=0; j<N; j++) {
                if(M+answer.get(i).cost >= list.get(j).cost && answer.get(i).num < list.get(j).num) {
                    M+=(answer.get(i).cost-list.get(j).cost);
                    answer.set(i, list.get(j));
                }
            }
        }
        for(Info info : answer)
            sb.append(info.num);
    }

    static class Info implements Comparable<Info>{
        int num;
        int cost;

        public Info(int num, int cost) {
            this.num=num;
            this.cost=cost;
        }

        @Override
        public int compareTo(Info o) {
            // TODO Auto-generated method stub
            return this.cost-o.cost;
        }
    }

}
