/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 요즘 스터디에서 푸는 프로그래머스 문제들의 특징을 담고 있는 문제입니다. 문제의 핵심은 시키는대로 하는 것 즉 빡구현 문제입니다.
	- 빡구현을 하는데 있어서 사용한 알고리즘은 Union-Find입니다. 이를 통해 update, merge, unmerge를 구현했습니다.
	- 표의 크기가 최대 50*50이므로 칸의 갯수는 총 2500개입니다. (x, y) 좌표를 1차원으로 표현하기 위해 50*(x-1)+y로 표현하고 parent라는 배열에 root 노드를 저장합니다. 당연히 초기화 시에는 root가 자기 자신을 갖게 초기화됩니다.
    - 그리고 arr를 두면서 특정 위치에 적히는 value를 저장하고 map에는 root를 key로 value를 저장합니다.
    - 이제부터 각각의 명령어에 대해서 기술해겠습니다.
    - PRINT r c : (r,c)를 1차원으로 표현한 값의 root를 찾아서 map에 key로 존재시 value를 출력하고 없다면 "EMPTY"를 출력합니다.
    - UPDATE r c value : (r,c)를 1차원으로 표현한 값에 value를 저장하고 map에 value를 갱신합니다.
    - UPDATE value1 value2 : map을 탐색하면서 value1을 가지고 있는 key를 찾아서 value2로 갱신합니다.
    - MERGE r1 c1 r2 c2 : (r1,c1)와 (r2,c2)를 1차원으로 표현한 값의 각각의 root를 찾은 후, 만약 같은 그룹이 아닌 경우에는 합치는 작업을 진행합니다.
        - 이때 root1의 value가 존재하면 그 값을 유지하고 그렇지 않다면 root의 value를 가져와 value로 설정합니다.
        - 이후 root2의 모든 자식 노드를 root1으로 갱신합니다.
        - 마지막으로 map에서 root2의 값을 삭제하고 root1에 새로운 value를 저장합니다.
    - UNMERGE r c : (r,c)를 1차원으로 변환한 값의 root를 찾은 후, 해당 칸이 속한 그룹을 개별적인 칸으로 분리하는 작업을 수행합니다.
        - 해당 그룹의 대표 노드(root)에 값이 존재한다면 이를 보존해야 합니다.
        - 그룹에 속한 모든 칸을 자기 자신이 부모가 되도록 parent 배열을 갱신합니다.
        - map에서 기존 그룹의 root 값을 삭제하고 원래 (r,c)에 있던 값이 존재하면 해당 위치에 다시 저장합니다.

시간 복잡도
	- 초기화 : O(2500) -> O(1)
	- PRINT r c : O(1) (find 함수 O(α(2500)) -> O(1), map에서 key 찾기 O(1))
	- UPDATE r c value : O(1) (find 함수 O(α(2500)) -> O(1), map에서 key 찾기 O(1))
	- UPDATE value1 value2 : O(1) (map 탐색 O(2500) -> O(1))
	- MERGE r1 c1 r2 c2 : O(1) (find 함수 O(α(2500)) -> O(1), map에서 key 찾기 O(1))
	- UNMERGE r c : O(1) (find 함수 O(α(2500)) -> O(1), map에서 key 찾기 O(1))
    - 전체 시간복잡도: O(1) ~ O(2500)

실행 시간
    - 35.31ms(java)
*/
import java.util.*;
class Solution {
    String[][] arr = new String[51][51];
    Map<Integer, String> map;
    int[] parent;
    public String[] solution(String[] commands) {
        List<String> list = new ArrayList<>();
        map = new HashMap<>();
        parent = new int[50*50+1];
        for(int i=0; i<parent.length; i++){
            parent[i]=i;
        }
        for(String command : commands){
            String[] str = command.split(" ");
            if(str[0].equals("PRINT")){
                int x = Integer.parseInt(str[1]);
                int y = Integer.parseInt(str[2]);
                int idx = 50 * (x - 1) + y;
                int root = find(idx);
                if (map.containsKey(root)) {
                    list.add(map.get(root));
                } else {
                    list.add("EMPTY");
                }
            }else if(str[0].equals("UPDATE")){
                if(str.length==4){
                    int x = Integer.parseInt(str[1]);
                    int y = Integer.parseInt(str[2]);
                    int idx = 50*(x-1)+y;
                    arr[x][y] = str[3];
                    int root = find(idx);
                    map.put(root, str[3]);
                }else{
                    String from = str[1];
                    String to = str[2];
                    for(Integer key : map.keySet()){
                        if(map.get(key).equals(from)){
                            map.put(key, to);
                        }
                    }
                }

            }else if(str[0].equals("MERGE")){
                int x1 = Integer.parseInt(str[1]);
                int y1 = Integer.parseInt(str[2]);
                int x2 = Integer.parseInt(str[3]);
                int y2 = Integer.parseInt(str[4]);

                int idx1 = 50 * (x1 - 1) + y1;
                int idx2 = 50 * (x2 - 1) + y2;

                int root1 = find(idx1);
                int root2 = find(idx2);

                if (root1 != root2) {
                    String value;
                    if (map.containsKey(root1)) {
                        value = map.get(root1);
                    } else {
                        value = map.getOrDefault(root2, null);
                    }

                    for (int i = 1; i <= 50 * 50; i++) {
                        if (find(i) == root2) {
                            parent[i] = root1;
                        }
                    }

                    map.remove(root2);
                    if (value != null) {
                        map.put(root1, value);
                    }
                }
            }else if(str[0].equals("UNMERGE")){
                int x = Integer.parseInt(str[1]);
                int y = Integer.parseInt(str[2]);
                int idx = 50 * (x - 1) + y;
                int root = find(idx);
                String value = map.getOrDefault(root, null);

                for (int i = 1; i <= 50 * 50; i++) {
                    if (find(i) == root) {
                        parent[i] = i;
                    }
                }

                map.remove(root);
                if (value != null) {
                    map.put(idx, value);
                }
            }
        }
        String[] answer = new String[list.size()];
        for(int i=0; i<answer.length; i++){
            answer[i] = list.get(i);
        }
        return answer;
    }
    public int find(int a){
        if(a==parent[a])
            return a;
        return parent[a] = find(parent[a]);
    }
}