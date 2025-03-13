/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 이 문제 개인적으로 재밌었습니다. 자료구조를 많이 써야되더라고요.
    - 일단 names라는 List를 선언해 N명의 사람의 이름을 넣고 사전순으로 오름차순 정렬합니다. 그리고 index라는 Map을 선언해 Key값으로 각각의 이름, Value로 각각의 인덱스 번호를 받습니다.
    - is_Root boolean 배열은 최상위 조상인지 확인하기 위함입니다.
    - list는 각각의 사람들의 자식들을 저장하기 위한 것입니다. Y에 X가 자식임을 의미해 list에 그 값들을 할당해주고 parent라는 배열은 부모의 수를 저장하기 위함이므로 X에 해당하는 인덱스 번호를 찾아 값을 하나 증가시킵니다. 그리고 is_Root 배열에서 자식인 경우 false로 할당합니다.
    - 그리고 StringBuilder를 선언한 후에 is_Root가 true 즉 최상위 부모인 경우에 StringBuilder에 추가해주고 큐에 담습니다.
    - child는 어떠한 노드의 자식들의 이름을 저장하기 위함입니다.
    - 마지막으로 bfs를 돌리면서 최상위 부모의 자식을 계속 탐방하며 parent값을 하나 감소시키는 작업을 진행하면서 parent가 0 즉 더 이상 부모가 없으면 이제 큐에 담고 그 부모에 해당하는 자식들을 child에 추가해줍니다.

시간 복잡도
    - 입력 및 초기화 단계 : O(N * logN)
        1. 입력 : O(N)
        2. 정렬 : O(N * logN)
    - 부모-자식 관계 입력 및 처리 : O(M)
    - bfs: O(N + M) (각 노드는 BFS에서 한 번씩 방문됨).
    - 전체 시간복잡도: O(N * logN + M) (N은 사람의 수, M은 부모-자식 관계의 수)

실행 시간
   - 800ms(java)
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static List<String> names;
    static Map<String, Integer> index;
    public static void main(String[] args) throws IOException {
        BufferedReader br=  new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        N = Integer.parseInt(st.nextToken());
        names = new ArrayList<>();
        index = new HashMap<>();
        int[] parent = new int[N];
        st = new StringTokenizer(br.readLine().trim());
        for(int i=0; i<N; i++){
            String name = st.nextToken();
            names.add(name);
        }
        Collections.sort(names);
        for(int i=0; i<N; i++){
            index.put(names.get(i),i);
            list.add(new ArrayList<>());
        }
        boolean[] isRoot = new boolean[N];
        Arrays.fill(isRoot,true);

        st = new StringTokenizer(br.readLine().trim());
        M = Integer.parseInt(st.nextToken());
        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine().trim());
            String p1 = st.nextToken();
            String p2 = st.nextToken();
            int idx1 = index.get(p1);
            int idx2 = index.get(p2);
            isRoot[idx1]=false;
            parent[idx1]++;
            list.get(idx2).add(p1);
        }
        int count=0;
        String parents="";
        Queue<Integer> que = new LinkedList<>();
        for(int i=0; i<N; i++){
            if(isRoot[i]){
                count++;
                parents+= names.get(i)+" ";
                que.add(i);
            }
        }
        sb.append(count+"\n");
        sb.append(parents.trim()+"\n");
        ArrayList<ArrayList<Integer>> child = new ArrayList<>();
        for(int i=0; i<N; i++){
            child.add(new ArrayList<>());
        }
        while(!que.isEmpty()){
            int now = que.poll();
            for(int i=0; i<list.get(now).size(); i++){
                String name = list.get(now).get(i);
                int next = index.get(name);
                parent[next]--;
                if(parent[next]==0){
                    child.get(now).add(next);
                    que.add(next);
                }
            }
        }
        for(int i=0; i<N; i++){
            sb.append(names.get(i)+" ");
            sb.append(child.get(i).size()+" ");
            Collections.sort(child.get(i));
            for(int j=0; j<child.get(i).size(); j++){
                sb.append(names.get(child.get(i).get(j)) +" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

}