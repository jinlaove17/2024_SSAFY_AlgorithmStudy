/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 처음 문제를 봤을 때 수학적으로 접근을 하려고 했습니다. 2개의 집합의 합집합 즉 두 개의 집합의 원소를 모두 합하고 겹치는 것을 빼는 것을 생각했습니다.
    - 그렇게 될 시에 Map<String, Set<String>>을 선언해야하고 이게 너무 복잡하다고 생각을 해 다른 방법을 떠올렸습니다.(사실 이거로 했는데 시간 초과남)
    - 그래서 Union-Find 알고리즘을 사용하면 쉽게 해결할 수 있다는 것을 깨달았습니다.
    - parent, depth라는 두 개의 Map을 선언해 각각 해당 노드의 부모 노드를 저장하는 목적과 해당 노드의 자식 갯수를 저장하는 목적으로 사용됩니다.
    - 초기 입력은 빠르게 생략하고 친구 입력받는 부분을 보면 해당 친구가 parent에 존재하지 않을 때에는 자기 자신을 부모로 갖고 자식의 갯수가 자기 자신 1개로 초기화합니다.
    - 이후 union 함수를 통해 두 노드의 부모를 찾아서 다르다면 두 번째 입력 받은 것의 부모을 첫번째의 부모로 할당해줍니다.
    - 그리고 자식의 갯수는 이제 앞에 받은 것에 첫번째 자식 수 + 두번 째 자식 수로 할당을 하고 StringBuilder에 첫번째 입력 받은 자식을 출력해줍니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(T*F)
    - Union-find : O(α(N))
    - 전체 시간복잡도: O(T*F*α(N))

실행 시간
   - 784ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    static int T;
    static int F;
    static Map<String, String> parent;
    static Map<String, Integer> depth;
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        T = Integer.parseInt(st.nextToken());
        for(int t=0; t<T; t++) {
            parent = new HashMap<>();
            depth = new HashMap<>();
            st = new StringTokenizer(br.readLine().trim());
            F = Integer.parseInt(st.nextToken());
            //Set<String> set = new HashSet<>();
            for(int i=0; i<F; i++) {
                st = new StringTokenizer(br.readLine().trim());
                String p1 = st.nextToken();
                String p2 = st.nextToken();
                if(!parent.containsKey(p1)) {
                    parent.put(p1, p1);
                    depth.put(p1, 1);
                }
                if(!parent.containsKey(p2)) {
                    parent.put(p2, p2);
                    depth.put(p2, 1);
                }
                int size = union(p1,p2);
                sb.append(size+"\n");
            }
        }
        System.out.println(sb.toString());

    }

    public static int union(String p1, String p2) {
        String parent1 = find(p1);
        String parent2 = find(p2);
        if(!parent1.equals(parent2)) {
            parent.put(parent2, parent1);
            depth.put(parent1, depth.get(parent2)+depth.get(parent1));
        }
        return depth.get(parent1);
    }

    public static String find(String p) {
        if(!parent.get(p).equals(p))
            parent.put(p, find(parent.get(p)));
        return parent.get(p);
    }

}
