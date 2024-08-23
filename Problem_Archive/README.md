## 📝 해결한 문제 목록
#### ${\textsf{\color{red}※ 문제 선정시 찾기(Ctrl + F)를 통해 이전에 풀었던 문제가 아닌지 확인해주세요!}}$
> 스터디 시간에 하지 못했던 말, 덧붙이면 좋은 말, 문제에 대한 코멘트 등을 자유롭게 남겨주세요.

> ⭐(알슐랭 스타): 모든 문제가 유익하지만 그 중에서도 다시금 풀어보면 좋을 것 같은 문제로, 과반수의 동의를 얻어야 획득이 가능해요.

- 종우: ⭐⭐
- 진주: ⭐
- 수현: ⭐⭐⭐⭐
- 민채: ⭐

### <hr>1주차
[1210. [S/W 문제해결 기본] 2일차 - Ladder1](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV14ABYKADACFAYh&categoryId=AV14ABYKADACFAYh&categoryType=CODE&problemTitle=%5BS%2FW+%EB%AC%B8%EC%A0%9C%ED%95%B4%EA%B2%B0+%EA%B8%B0%EB%B3%B8%5D+2%EC%9D%BC%EC%B0%A8+-+Ladder1&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=1)
 - 사다리 게임의 도착지가 한 곳이므로 도착지 부터 출발하는게 좋다. 가로로 연속해서 연결된 사다리가 존재하지 않음이 보장되므로 left/right 중 먼저 나오는 쪽을 타고 가는 구현 문제

[5684. [Professional] 운동](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWXRxnnah2sDFAUo&categoryId=AWXRxnnah2sDFAUo&categoryType=CODE&problemTitle=&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=1&&&&&&&&&&)
 - DFS와 visited 배열을 활용하여 사이클 탐색, But 계속 탐색을 반복하면 시간 초과가 되므로 적절한 가지치기 필요(누적 dist가 커지는 순간 return)

[6731. 홍익이의 오델로 게임](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWefzFeq5P8DFAUh&categoryId=AWefzFeq5P8DFAUh&categoryType=CODE&problemTitle=&orderBy=PASS_RATE&selectCodeLang=JAVA&select-1=4&pageSize=10&pageIndex=11) ⭐
 - Board의 제한 N이 짝수라는 조건이 핵심 힌트, 해당 조건으로 인해 뒤집는 지점을 기준으로 상태가 변하는 검정 돌의 개수가 무조건 홀수라는 규칙을 발견해야 한다.
 - 문제의 주어진 사소한 조건을 가지고 핵심 규칙을 찾아내기까지의 과정, 발상 난이도가 있는 문제


### <hr>2주차
[5643. [Professional] 키 순서](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWXQsLWKd5cDFAUo&categoryId=AWXQsLWKd5cDFAUo&categoryType=CODE&problemTitle=pro&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=1) ⭐
- 일반적으로 위상 정렬, BFS 알고리즘을 사용하여 해결할 수 있는 문제였으나 플로이드 위셜 알고리즘으로도 해결할 수 있다는 인사이트를 얻을 수 있었던 문제, 하지만 최단 경로 알고리즘 중 다익스트라로는 해결할 수 없다.
- 플로이드 워셜은 O(N^3)의 시간 복잡도로 전체 정점을 돌면서 연결 여부를 완전 탐색 해주는데 반해, 다익스트라는 그리디(우선순위 큐)를 사용하여 내가 현재 있는 정점에서 갈 수 있는 가장 빠른 경로를 선택한다.
- 이렇게 최선의 경로만 선택해서 진행하다보면 비효율적인 루트에 속한 일부 정점들에 대해 방문 처리가 안 될 수도 있다. => 즉, 키 순서에 관한 모든 사람의 수를 체크할 수 없어지기 떄문에 불가능하다.

[3234. 준환이의 양팔저울](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWAe7XSKfUUDFAUw&categoryId=AWAe7XSKfUUDFAUw&categoryType=CODE&problemTitle=&orderBy=RECOMMEND_COUNT&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=2) ⭐
- 완전 탐색을 수행하여 모든 경우를 다 따져보는 것은 불가능하기 때문에 백트래킹을 수행하면서 어떻게 효율적으로 가지치기를 할 수 있을지 고민해볼 수 있었던 문제, 이에 더해 동일 코드가 C++에서는 시간 초과가 나는 문제에 봉착하여 Java와 C++의 메모리 관리 방식 또한 공부해볼 수 있었다.
- Java에서는 지역 변수를 통한 최적화를 하면 적당한 가지치기 안에 돌지만, C++로 통과하기 위해서는 하나의 핵심 아이디어를 더 추가해줘야 한다.
- right에 추를 어떤 순서로 올려도 결과에 영향을 미치지 않은 경우가 나오면 팩토리얼을 사용한 순열 계산을 통해 경우의 수를 직접 구해줄 수 있다. (이 방법으로 가지치기 한 번 더 수행)

[1824. 혁진이의 프로그램 검증](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AV4yLUiKDUoDFAUx&categoryId=AV4yLUiKDUoDFAUx&categoryType=CODE&problemTitle=&orderBy=RECOMMEND_COUNT&selectCodeLang=CCPP&select-1=4&pageSize=10&pageIndex=1) ⭐
- 문제에서 주어진대로만 작성하며 시뮬레이션하면 되는 문제였다. 하지만 현재 좌표에 도달했을 때의 메모리 숫자와 방향을 고려하며 도착점에 도달이 가능한지를 따져보기 위해 4차원 배열을 사용한다는 점에서 나름 고민해볼 수 있었던 문제
- 특별히 4차원 배열을 사용한 이유가 사이클 처리 때문임을 알아야 한다.
- 주어진 조건이 x, y 좌표 뿐만 아니라 메모리와 방향도 있기 때문에 단순 방문 처리로는 사이클 처리가 불가능하다. 4개 조건이 모두 같은 상태로 재방문할 때 사이클을 빠져나갈 수 없다.
- '?' 좌표는 4방향으로 모두 움직일 수 있기 때문에, 4방향에 대한 모든 탈출 가능성을 백트래킹으로 체크하는 것도 중요했다.


### <hr>3주차
[1251. [S/W 문제해결 응용] 4일차 - 하나로](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV15StKqAQkCFAYD&categoryId=AV15StKqAQkCFAYD&categoryType=CODE&problemTitle=%ED%95%98%EB%82%98%EB%A1%9C&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=1)
- 최소 비용으로 모든 섬을 연결해야 하는 MST 문제였다. 입력 값으로 간선은 주어지지 않고 정점의 위치(x, y)가 주어지기 때문에 두 점 사이의 거리를 비용으로 하는 간선을 직접 만들어야 한다. 이 경우 노드가 N개일 때 N(N - 1)/2개의 간선이 만들어지는데 간선 기반인 크루스칼 알고리즘과 정점 기반인 프림 알고리즘은 각각 700ms, 526ms의 실행 시간이 걸렸다.
- 정점의 위치 좌표로 간선의 가중치를 직접 계산해줘야 하기 때문에 간선 위주의 풀이(Kruskal)와 정점 위주의 풀이(Prim) 중 처리가 더 편한 것 vs 시간이 더 빠른 것을 개인의 선택에 맡긴 문제였다. 

[4408. 자기 방으로 돌아가기](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWNcJ2sapZMDFAV8&categoryId=AWNcJ2sapZMDFAV8&categoryType=CODE&problemTitle=&orderBy=RECOMMEND_COUNT&selectCodeLang=CCPP&select-1=4&pageSize=10&pageIndex=2)
- 각 학생들의 출발점과 도착점이 주어질 때, 매번 이동 시 동선이 겹치지 않으면서 이동할 경우 걸리는 최단 시간을 구하는 문제였다. 주어진 그림에서 홀수 방은 위쪽에 짝수 방은 아래쪽에 배치되어 있는데 사실 이 문제는 구간이 겹치는지만 확인하면 되므로 위/아래에 대응되는 방을 같은 구간으로 처리해도 상관 없다. 중요한 점은 그림에서는 항상 방 번호가 작은 방에서 큰 방으로 이동하는 것처럼 주어지지만 방 번호가 큰 방에서 작은 방으로 이동하는 경우도 존재한다는 점을 고려해야 한다는 것이다. 이에따라 도착점이 큰 학생을 기준으로 하는 최대 힙을 사용하는 알고리즘은 적용할 수 없었다.
- 출발점과 도착점을 하나의 구간(세그먼트) 로 이해하는 것이 핵심 아이디어였다.
- 해당 문제는 출발지점이 도착지점보다 낮은 인덱스 값을 갖는다라는 조건이 존재하지 않아 임의로 두 개의 입력 값 중 작은 것을 출발지점으로 큰 것을 도착지점으로 간주해 배열에 저장했습니다. 학생들의 출발 지점을 기준으로 오름차순으로 정렬하되 출발 지점 값이 같게 되면 도착 지점을 기준으로 오름차순으로 정렬을 했습니다. 한 학생의 도착 지점보다 출발 지점이 먼 학생은 동시에 이동을 할 수 있으며 이 때 도착 지점은 두 학생 중 더 먼 도착 지점으로 업데이트를 해줍니다. 이를 통해 한 번에 이동할 수 있는 학생을 파악한 후 boolean 배열에 true로 설정해 해당 학생은 지나갔다는 것을 표시해준뒤 반복문을 수행하게 되면 문제를 풀 수 있었습니다.
- 추가적으로 방 길이만큼 정수형 배열을 선언한 뒤 한 학생의 출발 지점과 도착 지점 사이의 인덱스들을 모두 +1씩 수행해줍니다. 학생의 수만큼 해당 작업을 반복해주고 설정해준 정수형 배열에서 최대값 = 그 지점을 무조건 지나는 학생 수이며 이는 동시에 발생할 수 없는 것이며 해당 배열에 적힌 횟수만큼의 시간이 흘려야된다는 것을 의미하며 정답으로 도출됩니다.

[2819. 격자판의 숫자 이어 붙이기](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AV7I5fgqEogDFAXB&categoryId=AV7I5fgqEogDFAXB&categoryType=CODE&problemTitle=&orderBy=RECOMMEND_COUNT&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=1)
- 전형적인 완전 탐색 문제였다. DFS를 이용하여 완전 탐색을 진행했으며 문제에서 주어진 칸을 재방문해도 상관 없다고 했으므로 이를 고려하여 재귀 함수를 구현하면 된다. 현재까지 지나온 칸의 번호를 String이 아닌 bit masking을 이용해서 풀어보자.
- 수의 조합이 중복될 수 있으므로 set을 사용하면 된다.


### <hr>4주차
[5653. [모의 SW 역량테스트] 줄기세포배양](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRJ8EKe48DFAUo&categoryId=AWXRJ8EKe48DFAUo&categoryType=CODE&problemTitle=%EB%AA%A8%EC%9D%98&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=1) ⭐
- 세포가 무한히 증식하기 때문에 맵의 크기에 제한이 없다고 나오지만 최악의 경우 가장 끝에서 K가 300일 때 증식이 일어나면 최대 150만큼 커질 수 있다는 것을 알 수 있다. 적당한 맵의 크기를 설정한 후 주어진 조건에 따라 세포의 증식을 구현할 때 생명력이 높은 세포부터 증식하는 과정을 우선순위 큐, 변화량을 저장하는 2차원 배열 등을 사용하여 다양하게 구현해볼 수 있었다.
- Map 크기의 가능한 max를 계산하여 설정해주는 아이디어와 Cell의 상태를 표현하기 위해 특별한 구조체(class)를 활용하여 탐색하는 아이디어를 배웠다.
- 삼성 공채 시험에서 출제되는 유형과 비슷한 문제로 문제에 주어진 조건 그대로를 구현하면 되는 문제였다. 세포가 증식할 때의 최대 크기를 정해준 다음 구현을 하는 것이 포인트였다.
- 대부분의 배열 문제를 풀 때 주어진 N, M의 크기에 맞는 배열을 생성해 문제 풀이를 진행했었습니다. 해당 문제를 통해 주어진 N,M이 아닌 문제의 조건을 파악해 가능한 최대의 사이즈로 배열을 생성하고 각 줄에 입력값들이 배열의 어느 위치에 저장될 것인지 고려하는 것이 포인트였다고 생각합니다. 이후 조건에 맞는 Class를 구성해 PriorityQueue를 활용해 하나의 자리에 큰 값이 저장된다는 것을 구현할 수 있었습니다.

[1248. [S/W 문제해결 응용] 3일차 - 공통조상](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=5&contestProbId=AV15PTkqAPYCFAYD&categoryId=AV15PTkqAPYCFAYD&categoryType=CODE&problemTitle=&orderBy=INQUERY_COUNT&selectCodeLang=ALL&select-1=5&pageSize=10&pageIndex=1)
- 주어진 두 노드를 시작 정점으로 하여 첫 번째 노드에서 시작해 루트 노드 사이의 모든 노드를 flag 배열에 저장한 다음, 두 번째 노드에서 루트 노드로 진행하면서 flag인 정점을 찾았다면 그 노드가 공통 조상 노드임을 알 수 있고, 그 노드에서 BFS 탐색을 통해 서브 트리를 구할 수 있었다.
- LCA를 구하는 과정 중 잘 알려진 웰노운 방식을 알아두는 것이 중요하다.
   - 서로 깊이가 다른 노드의 깊이를 똑같이 맞춰준다
   - 최소 공통 조상 노드를 찾을 때 까지 같은 간격으로 트리를 올라가면 된다.
   - 이 때 한 칸 씩 올라가는 쉬운 방법과 희소 배열을 이용하여 이진 거리만큼 건너 뛰며 빠르게 올라가는 방법이 있다.

[9282. 초콜릿과 건포도](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AW9j-qfacIEDFAUY&categoryId=AW9j-qfacIEDFAUY&categoryType=CODE&problemTitle=&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=6) ⭐
- 최솟값을 찾기위해 모든 경우를 따져보며 초콜릿을 자르는 것은 앞에서 앞서 잘랐던 모양이 또 만들어 질 수 있기 때문에 중복이 발생해 시간 초과가 발생한다. 따라서 모든 경우를 살펴보되, 그렇게 잘랐을 때의 값을 메모리제이션 기법을 이용해 저장해 두고 사용해야 한다. 또한, 잘랐을 때 합을 매번 구할 필요 없이 2차원 부분합을 사용하였다. 따라서 재귀를 이용한 완전 탐색 + 메모리제이션 + 부분합 등 3개의 알고리즘이 사용되는 응용이 필요한 문제였다.
- 2차원 누적합의 값을 4차원 dp table에 저장하여 메모이제이션 하는 것이 중요한 아이디어였다. 처음에 2차원 누적합을 보고 2차원 dp로 처리하려고 하면 방문처리나 메모이제이션 된 값을 다시 찾으려고 할 때 과정이 꼬일 수 있다. 이를 쉽게 해주는 아이디어가 4차원 dp였다.

### <hr>5주차
[1238. [S/W 문제해결 기본] 10일차 - Contact](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AV15B1cKAKwCFAYD&categoryId=AV15B1cKAKwCFAYD&categoryType=CODE&problemTitle=&orderBy=RECOMMEND_COUNT&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=1)
- 최단거리를 보장하는 BFS 알고리즘을 사용하여 시작 정점에서 각 정점까지의 거리를 구하고, 1번 정점부터 100번 정점까지 순회하며 최댓값을 갖는 번호를 저장하면 되는 BFS 연습 문제였다.

[2477. [모의 SW 역량테스트] 차량 정비소](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV6c6bgaIuoDFAXy&categoryId=AV6c6bgaIuoDFAXy&categoryType=CODE&problemTitle=%EB%AA%A8%EC%9D%98&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=2)
- 주어진 조건이 많아서 복잡하지만, 차근 차근 읽어보며 주어진 조건을 구현하면 되는 구현 문제였다. 접수 창구의 경우 여러 고객이 기다릴 때 고객 번호가 낮은 순서대로 처리하는데 도착 순서가 고객 번호에 비례하기 때문에 일반 큐를 사용하여 처리해주면 되고, 정비 창구는 도착 순서, 작은 창구 번호 이용 고객 순으로 우선순위 큐를 사용하여 처리해주면 된다. 주의할 점은 처리가 끝나는 시간에 입장이 되므로 항상 창구를 비우고 새로운 고객을 추가하는 순으로 처리해야 한다는 점이다.

[5656. [모의 SW 역량테스트] 벽돌 깨기](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRQm6qfL0DFAUo&categoryId=AWXRQm6qfL0DFAUo&categoryType=CODE&problemTitle=%EB%AA%A8%EC%9D%98&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=1) ⭐
- 구슬을 어느 열에 N번 떨어뜨려야 가장 많이 제거할 수 있을까? 그리디한 발상이 떠오르지 않는가? 최악의 경우 12 X 15 판에 4번이다. 완전 탐색으로 가자. 매번 판의 상태를 백업하고 BFS로 벽돌을 제거하고, 남은 벽돌을 아래로 내려야 하는 빡구현 + BFS 문제였다. 디버깅에 늪에 빠졌다면 시간이 굉장히 오래 걸릴 것.

[2105. [모의 SW 역량테스트] 디저트 카페](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5VwAr6APYDFAWu&categoryId=AV5VwAr6APYDFAWu&categoryType=CODE&problemTitle=%EB%AA%A8%EC%9D%98&orderBy=PASS_RATE&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=1) ⭐
- N x N 크기의 판에서 대각선 방향으로 이동하며 사각형의 경로로 가장 많은 디저트를 먹을 수 있는 경우를 구하는 문제였다. 이 문제도 최악의 경우 20 x 20이므로 완탐으로 무작정 덤벼보자. 사각형을 만들기 위해서는 방향을 3번 바꿔야 한다는 아이디어만 떠올릴 수 있다면, 나머지는 당신의 구현력을 믿어라.
