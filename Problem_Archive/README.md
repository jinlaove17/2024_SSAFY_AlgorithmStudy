## 📝 해결한 문제 목록
#### ${\textsf{\color{red}※ 문제 선정시 찾기(Ctrl + F)를 통해 이전에 풀었던 문제가 아닌지 확인해주세요!}}$
> 스터디 시간에 하지 못했던 말, 덧붙이면 좋은 말, 문제에 대한 코멘트 등을 자유롭게 남겨주세요.

> ⭐(알슐랭 스타): 모든 문제가 유익하지만 그 중에서도 다시금 풀어보면 좋을 것 같은 문제로, 과반수의 동의를 얻어야 획득이 가능해요.<br>
> ❤️(좋아요): 알슐랭 스타에는 들지 못했지만 개인적으로 괜찮았던 문제로, 언제든 누구나 선정할 수 있어요.

- 종우: ⭐⭐⭐⭐⭐⭐
- 진주: ⭐⭐⭐⭐⭐
- 수현: ⭐⭐⭐⭐⭐⭐⭐⭐
- 민채: ⭐⭐⭐⭐

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

[1248. [S/W 문제해결 응용] 3일차 - 공통조상](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=5&contestProbId=AV15PTkqAPYCFAYD&categoryId=AV15PTkqAPYCFAYD&categoryType=CODE&problemTitle=&orderBy=INQUERY_COUNT&selectCodeLang=ALL&select-1=5&pageSize=10&pageIndex=1) 💗
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
- BFS 탐색 시 넘버링하는 순간이 중요했다. 큐에 넣을 때와 큐에서 빼낼 때의 차이를 잘 구분하고 넘버링을 해야하는 문제였다.

[2477. [모의 SW 역량테스트] 차량 정비소](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV6c6bgaIuoDFAXy&categoryId=AV6c6bgaIuoDFAXy&categoryType=CODE&problemTitle=%EB%AA%A8%EC%9D%98&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=2)
- 주어진 조건이 많아서 복잡하지만, 차근 차근 읽어보며 주어진 조건을 구현하면 되는 구현 문제였다. 접수 창구의 경우 여러 고객이 기다릴 때 고객 번호가 낮은 순서대로 처리하는데 도착 순서가 고객 번호에 비례하기 때문에 일반 큐를 사용하여 처리해주면 되고, 정비 창구는 도착 순서, 작은 창구 번호 이용 고객 순으로 우선순위 큐를 사용하여 처리해주면 된다. 주의할 점은 처리가 끝나는 시간에 입장이 되므로 항상 창구를 비우고 새로운 고객을 추가하는 순으로 처리해야 한다는 점이다.
- 고객과 정비소 창구의 상태를 잘 표현하기 위해서 구조체 (클래스) 선언이 필요했던 문제였다. 필요한 필드들을 적절하게 선언한 후에 구현을 통해 잘 처리해주고, Q와 PQ를 사용해 대기열을 잘 관리하면 되는 문제이다.

[5656. [모의 SW 역량테스트] 벽돌 깨기](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRQm6qfL0DFAUo&categoryId=AWXRQm6qfL0DFAUo&categoryType=CODE&problemTitle=%EB%AA%A8%EC%9D%98&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=1) ⭐
- 구슬을 어느 열에 N번 떨어뜨려야 가장 많이 제거할 수 있을까? 그리디한 발상이 떠오르지 않는가? 최악의 경우 12 X 15 판에 4번이다. 완전 탐색으로 가자. 매번 판의 상태를 백업하고 BFS로 벽돌을 제거하고, 남은 벽돌을 아래로 내려야 하는 빡구현 + BFS 문제였다. 디버깅에 늪에 빠졌다면 시간이 굉장히 오래 걸릴 것.

[2105. [모의 SW 역량테스트] 디저트 카페](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5VwAr6APYDFAWu&categoryId=AV5VwAr6APYDFAWu&categoryType=CODE&problemTitle=%EB%AA%A8%EC%9D%98&orderBy=PASS_RATE&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=1) ⭐
- N x N 크기의 판에서 대각선 방향으로 이동하며 사각형의 경로로 가장 많은 디저트를 먹을 수 있는 경우를 구하는 문제였다. 이 문제도 최악의 경우 20 x 20이므로 완탐으로 무작정 덤벼보자. 사각형을 만들기 위해서는 방향을 3번 바꿔야 한다는 아이디어만 떠올릴 수 있다면, 나머지는 당신의 구현력을 믿어라.
- 사각형을 이루는 대각선의 경로로 이동할 때, 한 방향(시계 방향 or 반시계 방향)으로 이동하고, turn 하는 경우를 잘 세어주는게 포인트였다.

### <hr>6주차
> 분명 시간은 누구에게나 공평하게 흐른다.<br>
하지만 그와 나의 시간은 그 농도가 너무나도 달랐다.<br><br>
\- 드라마 <[이태원 클라쓰](https://www.youtube.com/watch?v=oLrAMT38prw)> 中 -<br>

[4530. 극한의 청소 작업](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWO6cgzKOIEDFAWw&categoryId=AWO6cgzKOIEDFAWw&categoryType=CODE&problemTitle=&orderBy=RECOMMEND_COUNT&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=4) ❤️
- 음... 일단 나에게는 어려웠다. 수의 범위가 최소 -999,999,999,999부터 최대 999,999,999,999까지로 굉장히 크지만, 이를 통해 아! 자리수를 통해서 무언가 계산을 해야겠구나 아이디어를 떠올릴 수 있다. 해당 범위 내에서 4가 들어간 수의 개수를 어떻게 계산할 수 있을까? 일단 공책을 펴고 4, 14, 24, 34, 40 ~ 49, ... 4가 들어간 숫자들을 적어보고 규칙성을 한 번 찾아보자. 규칙성을 발견해 수의 개수를 계산할 수 있다면 a, b의 부호에 따라 분기하여 적절히 처리해주자. 사실 이 풀이보다는 9진법을 사용해 해결하는 아이디어가 더 중요했던 것 같다.
 
[10726. 이진수 표현](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AXRSXf_a9qsDFAXS) 💗
- 마지막 N개의 비트가 1인지 확인하기 위해 반복문으로 뒤에서부터 1로 채워진 비트를 만들어 & 연산을 하는 생각이 들었는가? 당신은 이것을 보면 뒷목을 잡게 될 것이다. `(1 << N) - 1`
- 사실은 and 연산 한 번으로 끝나는 깔끔한 기본 문제이다.

[7699. 수지의 수지 맞는 여행](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWqUzj0arpkDFARG&categoryId=AWqUzj0arpkDFARG&categoryType=CODE&problemTitle=&orderBy=PASS_RATE&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=4)
- DFS를 연습하기에 좋았던 기본 문제였다. 그런데 제출했는데 1,000ms 이상의 실행시간이 나왔다면, A부터 Z까지 알파벳의 개수가 몇 개인지 세보고 이를 이용해 가지치기를 해보자. 실행 시간이 1/10배가 될 것이다.

[1865. 동철이의 일 분배](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5LuHfqDz8DFAXc) ❤️
- 최악의 경우 N은 16이고, 순열로 푼다면 `O(16!)`의 시간 복잡도를 갖기 때문에 도저히 불가능해 보인다. 과연 그럴까? 당신의 가지치기 실력을 믿고 순열로 도전해보자. 그렇게 문제를 풀었다면, 이제 전혀 생각치도 못했던 `DP`를 활용해 다시 풀어보자. 개인적으로 `DP`를 사용해 꼭 다시 풀어봐야 할 문제라고 생각한다.
- MCMF 를 사용할 수 있는 대표적인 문제이다. (추후에 심화 알고리즘 세션을 따로 가져서 공부해보면 좋을 것 같다.)

### <hr>7주차
> 돈이 없어서 졌다, 과외를 못해서 대학을 못 갔다, 몸이 아파서 졌다.<br>
모두가 같은 환경일 수가 없고 각자가 가진 무기 가지고 싸우는 건데...<br>
핑계대기 시작하면 똑같은 상황에서 또 집니다.<br><br>
\- 드라마 <[스토브리그](https://youtu.be/V8963uVBrE8?si=_2N5PI2I9RNCM2fD&t=540)> 中 -<br>

[3000. 중간값 구하기](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV-fO0s6ARoDFAXT&)
- 문제에서 알려주듯이 최소 힙과 최대 힙을 이용하여 현재 값보다 크면 최소 힙에, 작으면 최대 힙에 넣으면서 두 힙의 사이즈를 맞춰주며 중간 값을 유지하면 되는 문제였다.

[4740. 밍이의 블록게임](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWSNgjbKzmQDFAUr)
- 블럭을 상, 하, 좌, 우로 밀거나 연속되면서 크기가 큰 블럭들을 제거하는 등, 주어진 조건대로 처리하면 되는 시뮬레이션 문제였다. 최대 크기를 가진 블럭 그룹들의 좌표를 어떻게 저장할 것인지에 대해 스터디원들이 각자 다양한 자료구조를 사용한 것을 살펴볼 수 있었다.

[1798. 범준이의 제주도 여행 계획](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV4x9oyaCR8DFAUx) ⭐
- 초반에 문제를 풀 때 여행이 가능할 경우에 만족도와 경로를 출력해야 하고 m은 최대 5까지 주어지므로 한 번의 DFS 탐색으로 해결할 수 있을지 의문이 들었다. 매개변수를 잘 활용해 일차와 사용 시간을 잘 고려하며 불필요한 탐색을 하지 않도록 구현해야 한다는 점에서 조금은 까다로운 완전 탐색 문제였다고 생각한다.

[15942. 외계인 침공](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AYVgWGZKOroDFAQK)
- 문제를 선정했던 날짜를 기준으로 정답률이 10.97%였던 괴랄한 문제였다. 문제 설명은 굉장히 짧지만 그리디한 발상을 떠올리고 반례를 증명하는 과정이 굉장히 어려웠던 것 같다. TreeMap에 최대 힙을 사용하는 풀이의 경우 29개의 케이스 중 28개가 맞게 되는데 이에 대한 반례는 아직까지도 찾지 못했다. 어찌됐든 구글에 이 문제를 검색하면 단 1개의 해설만 있다. 이 블로그를 참고해서 기존 코드에 조건식을 추가하여 어찌저찌 문제는 해결했지만 반례를 좀 알고 싶다...

### <hr>8주차
> 네가 대수롭지 않게 받아들이면 남들도 대수롭지 않게 생각해.<br>
네가 심각하게 받아들이면 남들도 심각하게 생각하고.<br>
모든 일이 그래.<br>
항상 네가 먼저야.<br>
옛날 일... 아무것도 아니야.<br>
네가 아무 것도 아니라고 생각하면 아무 것도 아니야.<br><br>
\- 드라마 <[나의 아저씨](https://www.youtube.com/watch?v=DEWcNUsVxX8)> 中 -<br>

[[SWEA] 6855. 신도시 전기 연결하기](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWhUBBUqQO0DFAW_) ❤️
- 개인적으로 `n개의 집을 k개의 그룹으로 나누려면, n - 1개의 연결선 중 k - 1개를 제거해야 한다.`는 발상이 어려웠던 문제이다. 처음에 문제와 테스트 케이스를 이해하는데도 시간이 좀 걸렸고, 이분 탐색으로 해결해야겠다는 삽질까지 했었던 문제였다.
  
[[SWEA] 3135. 홍준이의 사전놀이](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV_6pTXqsXUDFAWS) ⭐
- `트라이(Trie)` 알고리즘에 대해 공부해볼 수 있었던 문제였다. 정적 배열, 연결 리스트 기반의 트라이를 만들어 해결해보자. 단, 해당 단어로 시작하는 문자의 개수를 구하기 위해서는 아주 조금의 변형이 필요하다.

[[CodeTree] 포탑 부수기](https://www.codetree.ai/training-field/frequent-problems/problems/destroy-the-turret/description?page=1&pageSize=20&name=%ED%8F%AC%ED%83%91) ⭐
- 문제가 굉장히 길고 복잡해 보이지만, 그만큼 주어지는 정보 또한 많기 때문에 구현은 굉장히 직관적으로 할 수 있는 문제였다. 레이저 공격에 성공할 경우, 경로를 어떻게 저장할지 스터디원들의 다양한 풀이를 볼 수 있었던 구현력을 기르기 좋은 문제!

[[BOJ] 2931. 가스관](https://www.acmicpc.net/problem/2931) ⭐
- 전에는 시간 제한이 있어서 해결하지 못했지만, 시간 제한이 없는 환경에서 복수할 기회가 찾아왔다. 그래프 탐색으로 없어진 타일의 위치를 찾은 다음, '+' 타일이 조금 골치 아프겠지만, 7개의 타일을 대입해보며 경로가 이어지는지 찾아보자.

### <hr>9주차
> 지하철 첫 차에는 최선을 다해 살아가는 우리의 부모님들이 계셨다.<br>
이들 앞에서 나는 감히 최선을 다해 살았다고 말할 수 있을까...<br>
내 죽음이 참을 수 없이 부끄러웠다.<br><br>
\- 드라마 <이재, 곧 죽습니다> 中 -<br>

[[BOJ] 20187. 종이접기](https://www.acmicpc.net/problem/20187) 💗
- 종이를 접어가면서 시뮬레이션을 하는 것이 아니라, 모두 접힌 1 x 1 크기의 사각형에서 역으로 펼치는 발상으로 문제를 해결할 수 있었다. 종이를 상, 하, 좌, 우로 펼칠 때마다 처음에 구멍이 뚫린 위치에 따라 규칙성이 발생하므로 인덱스의 범위를 벗어나지 않도록 적절히 배열의 한 가운데에서 시작해 종이를 펼쳐나가면 된다.
- Python의 장점인 동적 배열을 이용하면 배열을 상, 하, 좌, 우로 붙이면서 직관적으로 종이를 펴는 과정을 구현할 수 있다.
  
[SWEA] [PRO] 성적 조회 💗
- B형 대비 문제로 자료구조를 적절히 사용하면 그렇게 어렵지 않게 해결할 수 있었던 문제였다. `이진 탐색 트리`인 set(TreeSet)과 map(TreeMap)의 사용법과 동작방식을 적절히 익혀두면 앞으로 큰 도움이 될 것이라 생각한다. 또한 템플릿(제네릭) 매개변수에 또 다른 자료구조를 사용하면 구현이 어려운 기능을 손쉽게 구현할 수 있다는 사실을 염두해두자!
- C++으로 풀 때, 컨테이너와 이터레이터를 다루는 것을 연습할 수 있는 문제이다. 해당 구현이 쉬워질 때 까지 연습하기를 추천한다.

### <hr>10주차
> ***김동식:***<br>
 &emsp;&emsp;당신 실패하지 않았어.<br>
 &emsp;&emsp;합격하고 입사하고 나서 보니까 성공이 아니라 그냥 문을 하나 연 것 같은 느낌이더라고.<br>
 &emsp;&emsp;어쩌면 우리는 성공과 실패가 아니라 죽을 때까지 다가오는 문만 열어가면서 살아가는게 아닐까 싶어.<br><br>
> ***장그래:***<br>
 &emsp;&emsp;그럼 성공은요?<br><br>
> ***김동식:***<br>
 &emsp;&emsp;자기가 그 순간에 어떤 의미를 부여하느냐에 달린 문제가 아닐까?<br><br>
\- 드라마 <[미생](https://www.youtube.com/watch?v=WGOh-YkIVFk)> 中 -<br>

[[SWEA] 5650. [모의 SW 역량테스트] 핀볼 게임](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRF8s6ezEDFAUo) ⭐
- 문제에서 시작점에 도달하지 않고 사이클이 발생하는 경우나 블록에 맞고 밖으로 나가는 경우 등의 조건을 명확히 명시해주지 않아서 삽질을 했던 문제였다. 하지만 차근차근 인덱스의 범위를 고려하고 블록의 입력 방향에 따른 출력 방향 등을 정해주고 시뮬레이션을 진행하면 답을 도출할 수 있을 것이다. 또한 모든 위치에서 탐색을 4방향으로 진행하지 않고, 같은 결과를 내는 시작점은 중복 처리를 통해 최적화를 할 수 있다.

[[BOJ] 6087. 레이저 통신](https://www.acmicpc.net/problem/6087) ❤️
- 시작점에서 도착점으로 최단거리로 도달하는 BFS 탐색을 통해 각 칸에 어떤 방향에서 들어왔을 때의 거울 개수를 저장하면서 탐색을 진행한 다음, 최종적인 위치에서 구한 값의 최솟값을 구하면 되는 문제였다. BFS가 아닌 다익스트라로 다시 풀어보자.

[[SWEA] 1868. 파핑파핑 지뢰찾기](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5LwsHaD1MDFAXc)
- 이 문제도 전형적인 BFS 탐색을 통해 해결이 가능하다. 다만, 인접칸의 인접 지뢰 개수가 0인 경우에만 큐에 추가하고 방문체크를 하며 탐색을 진행해야 한다는 점이 핵심이다.

[[BOJ] 21609. 상어 중학교](https://www.acmicpc.net/problem/21609) ⭐
- BFS 탐색을 통해 우선순위에 따른 블록 그룹 찾기, 배열 회전, 장애물이 있을 때의 블록 내리기 등 시뮬레이션을 연습하기에 굉장히 좋은 문제였다.
- 다양한 구현을 경험할 수 있어서 다채로운 문제이자 구현 연습을 하기에 적합하므로 추천한다.

### <hr>11주차
> 죽고 싶은 와중에 죽지 마라,<br>
당신은 괜찮은 사람이다. 파이팅해라!<br>
그렇게 응원해주는 사람이 있다는 것만으로..<br>
숨이 쉬어져..<br><br>
\- 드라마 <[나의 아저씨](https://www.youtube.com/watch?v=p_QuK8kAjSg)> 中 -

[[SWEA] 4014. [모의 SW 역량테스트] 활주로 건설](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWIeW7FakkUDFAVH) ❤️
- 행과 열 단위로 검사하며 시뮬레이션 하는 문제였다. 높이가 높아졌을 때, 낮아졌을 때 각각 어떻게 처리할 것인지 스터디원들의 다양한 풀이를 살펴볼 수 있었다. 이 문제에서 주의할 점은 x가 1일 때, 높이가 2 1 1 2 혹은 2 1 2와 같이 주어졌을 때 겹치지 않게 설치하면서 불가능한 경우를 찾아내야 한다는 점이다.

[[BOJ] 14725. 개미굴](https://www.acmicpc.net/problem/14725) ⭐
- 단순히 26개의 알파벳 문자를 O(1)에 접근하는 기본적인 트라이를 학습했다면, 이 문제처럼 자식의 순서를 유지하며 문자열로 갖는 트라이의 응용 문제를 풀어보자. 코드는 짧게 해결되더라도 `이진 탐색 트리`를 이용해 자식을 관리해야겠다는 발상과 어떤 자료구조를 사용할지 각 자료구조의 특성을 고민해볼 수 있었던 문제이다.
- 트라이에 단순히 char만 담긴다고 생각하지 말고, 다양한 자료 구조를 활용하여 String이나 숫자도 가능할 지 생각해볼 수 있는 기회였다.

[[SWEA] 4301. 콩 많이 심기](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWLv-yZah48DFAVV)
- 두 점 사이의 거리 공식이 주어지며 수학적으로 해결해야 할 것 같지만, 거리가 2가 되는 경우는 유일하다는 점에서 어렵지 않게 해결 할 수 있었던 문제이다. 하지만 조금만 응용해서 거리가 3:4:5와 같은 삼각비의 형태를 이루는 경우라면 대각선을 고려해야하기 때문에 까다로워질 수도 있는 문제였다.

[[CodeTree] 예술성](https://www.codetree.ai/training-field/frequent-problems/problems/artistry/description?page=4&pageSize=5) ⭐
- 각 그룹을 선별하고 그룹 간에 맞닿은 변의 개수 계산, 십자 회전, 4개의 정사각형 부분 회전 등 단순히 `구현`하면 되는 문제이다. 문제가 지저분하지 않기 때문에 좀 만 고민하면 직관적으로 풀 수 있는 좋은 연습문제였다.
- 4개의 정사각형을 부분 회전할 때 분할 정복의 아이디어를 사용하면 깔끔하게 구현할 수 있다.

### <hr>12주차
> 누가 그럽디다.<br>
포기하는 순간 핑곗거리를 찾게 되고<br>
할 수 있다는 생각하는 순간에 방법을 찾는다고.<br><br>
\- 드라마 <[낭만닥터 김사부 2](https://youtu.be/4aoLQme9dW4?si=F-fFOJfhHRKkq8PY&t=251)> 中 -

[[BOJ] 4485. 녹색 옷 입은 애가 젤다지?](https://www.acmicpc.net/problem/4485)
- 가중치가 1인 그래프 문제에서 최단거리는 아묻따 BFS이지만, 이 문제에서는 더 적은 비용으로 이미 방문한 칸을 재방문 할 수 있기 때문에 bool이 아닌, 최소 비용을 저장하는 int 배열을 사용해 해결하면 되는 기본적인 BFS 연습문제였다.

[[BOJ] 2211. 네트워크 복구](https://www.acmicpc.net/problem/2211)
- 다익스트라 알고리즘에 경로 복원 아이디어를 적용하여 해결 가능한 문제였다.

[[BOJ] 2629. 양팔저울](https://www.acmicpc.net/problem/2629) ⭐
- 추가 최대 30개로, 완전 탐색을 불가능하다고 생각해 DP 알고리즘으로 배낭 문제처럼 값 직접 그려 채워나가면서 해결할 수 있었다. 스터디원의 다른 풀이로 시간 초과로 불가능하다고 생각한 백트래킹 알고리즘이 있었는데, 다시금 살펴 봐야겠다...

[[BOJ] 1106. 호텔](https://www.acmicpc.net/problem/1106) ❤️
- N이 최대 20이라 백트래킹을 통해 답의 후보보다 비용이 커질 경우 탐색을 종료하는 조건을 추가해 시도했는데 시간 초과가 발생했다. 최종적으로 DP로 해결했는데 스터디원들의 서로 다른 DP[i]에 대한 정의를 살펴볼 수 있었던 문제였다.

### <hr>13주차
> ***오차장:***<br>
 &emsp;&emsp;은지 그 친구한테 자기 개발서에서나 나올 법한 얘기를 매일 같이 해줬어.<br>
 &emsp;&emsp;야간 대학을 가라, 꿈을 꿔라, 노력은 배신하지 않는다, 열심히 하면 길이 보일거다.<br><br>
> ***선차장:***<br>
 &emsp;&emsp;똑똑한 친구였으니까요...<br><br>
> ***오차장:***<br>
 &emsp;&emsp;내가 주제 넘게만 굴지 않았어도 지금쯤 살아...<br>
 &emsp;&emsp;편안하게 회사 다니다가 좋은 남자 만나서 시집 가서 잘 살고 있었겠지, 좋은 애니까.<br><br>
 &emsp;&emsp;그 친구가 웃으면서 그랬어.<br>
 &emsp;&emsp;대리님 감사합니다. 이렇게만 하면 되는거죠.<br><br>
 &emsp;&emsp;똑같은 얘기를... 장그래 녀석이 묻더군...<br>
 &emsp;&emsp;안된다고 했어.<br><br>
> ***선차장:***<br>
 &emsp;&emsp;차장님...<br><br>
> ***오차장:***<br>
 &emsp;&emsp;은지 때보다 더 어려운 시대잖아.<br>
 &emsp;&emsp;대책 없는 희망이 무책임한 위로가 무슨 소용이야.<br><br>
> ***선차장:***<br>
 &emsp;&emsp;전 그 대책 없는 희망, 무책임한 위로 한마디 못 건네는 세상이란게 더 무섭네요.<br>
 &emsp;&emsp;대책 없는 그 말 한마디라도 절실한 사람들이 많으니까요.<br><br>
> ***오차장:***<br>
 &emsp;&emsp;그래도, 안돼.<br><br>
> \- 드라마 <[미생](https://youtu.be/LtnO12bafAY?si=bwxmp_BVVlO5jZF9&t=350)> 中 -<br>

[[BOJ] 16934. 게임 닉네임](https://www.acmicpc.net/problem/16934)
- 전형적인 트라이 자료구조에 해시와 새로운 노드를 생성할 때 flag를 바꿔주는 등의 처리를 통해 해결할 수 있었던 트라이 연습문제.

[[BOJ] 1800. 인터넷 설치](https://www.acmicpc.net/problem/1800) ⭐
- 다익스트라+이분탐색? 다익스트라+2차원 방문체크? 현시점 문제보관소에 있는 문제 중 TOP3안에 드는 굉장히 어려운 문제였다. 스스로 해결하지 못해 리뷰할 것도 없다. 다시 풀어보자...

[[BOJ] 16947. 서울 지하철 2호선](https://www.acmicpc.net/problem/16947) ❤️
- DFS 탐색을 이용한 사이클 탐색 + BFS 탐색을 이용한 거리 계산, 2가지를 동시에 적용하여 해결할 수 있는 DFS&BFS 연습 문제로, 사이클을 탐지하는 스터디원들의 다양한 풀이를 살펴볼 수 있었다.

[[BOJ] 30023. 전구 상태 바꾸기](https://www.acmicpc.net/problem/30023)
- 쉽게 생각해서 앞에서부터 3칸씩 살펴보며 모든 전구를 빨강, 초록, 파랑으로 만들겠다는 목표를 가지고 살펴보면 10만개의 전구를 O(N)의 시간 복잡도로 확인할 수 있다. 하지만 이런 그리디한 문제는 왜 그게 되는지에 대한 고민이 필요해 보인다.

### <hr>14주차
> 지금 한 번!<br>
지금만 한 번!<br>
마지막으로 한 번!<br>
또, 또 한 번!<br>
순간엔 편하겠지.<br>
근데 말이야.<br>
그 한 번들로 사람은 변하는거야.<br><br>
\- 드라마 <[이태원 클라쓰](https://www.youtube.com/watch?v=VA8i54OyRos)> 中 -<br>

[[BOJ] 1234. 크리스마스 트리](https://www.acmicpc.net/problem/1234) ⭐
- 사실 레벨이 최대 10이고, 주어진 조건을 만족하도록 각 레벨을 그룹으로 나누면 경우가 그렇게 많지 않다는 것을 알 수 있다. 그렇기 때문에 그룹을 나눴다면 그 그룹을 어떤 색으로 구성할 것인지에 대한 순열 값을 누적하여 기저에서 답을 누적하는 식으로 해결할 수 있었다. 이외에도 4차원 DP를 활용한 풀이를 살펴볼 수 있었다.

[[BOJ] 1917. 정육면체 전개도](https://www.acmicpc.net/problem/1917) ❤️
- 백준 14499번 주사위 굴리기 문제의 아이디어를 적용하여 전개도 위에서 정육면체를 굴렸을 때, 모든 면이 한 번씩 닿았는지를 확인하는 발상이 어려운 문제였다. 이 생각만 한다면 회전 방향에 따른 면의 인덱스를 고려하여 답을 도출할 수 있다.

[[BOJ] 10800. 컬러볼](https://www.acmicpc.net/problem/10800) ❤️
- 전체 공을 크기순으로 정렬하고, 누적합을 계산하고, 색상 별로도 크기순으로 정렬하고, 누적합을 계산한 다음 첫 번째 공부터 이진 탐색을 통해 전체에서 몇 번째인지를 계산하고, 색상 별에서는 몇 번째인지를 계산하여 두 누적합을 빼는 방식으로 해결할 수 있었던 문제이다. 이 풀이는 메모리를 많이 사용하는 풀이인데 이외 달리 3개의 배열만을 사용하여 누적합을 사전에 구하는 것이 아니라 진행하면서 누적하는 풀이를 살펴볼 수 있었다.

[[Programmers] 인사고과](https://school.programmers.co.kr/learn/courses/30/lessons/152995) ❤️
- 정렬을 적절히 활용하면 되지만, 그리디한 발상이 필요하기 때문에 생각보다는 쉽게 풀리지 않았던 문제였다. 첫 번째 점수를 기준으로 내림차순 정렬을 하고 최댓값을 비교하여 더 작을 경우 두 번째 최댓값과 비교하면서 2개의 최댓값을 갱신하며 배제되는 사람을 판별한다. 물론 이 과정에서 두 번째 점수는 오름차순 정렬을 수행해야 올바르게 답을 도출할 수 있다.

### <hr>15주차
> 사사로운 일들이 잔물결처럼 밀려와도 그것은 잔물결일 뿐 모두들 그러하듯 견딜 수 있다.<br>
\- 드라마 <치즈인더트랩> 中 -<br>

[[BOJ] 2887. 행성 터널](https://www.acmicpc.net/problem/2887)
- 전형적인 MST 문제들을 풀어왔는가? 그렇다면 3차원 공간에서의 MST와 붙어보자. 3차원이라고 별다를 것 같진 않아 보인다. 그러나 행성이 최대 10만개이므로 간선을 어떻게 만들지 고민하는 과정이 핵심인 문제이다. min(|x1-x2|, |y1-y2|, |z1-z2|)라는 조건을 눈여겨보면 행성을 각 속성(x, y, z)별로 정렬을 한 후 인접한 행성과에 간선을 형성하면 된다는 것을 알 수 있을 것이다.

[[BOJ] 2143. 두 배열의 합](https://www.acmicpc.net/problem/2143)
- 부 배열은 전체 배열 중 연속적으로 존재하는 부분 배열이기 때문에 누적합을 쉽게 떠올릴 수 있다. n, m이 최대 1,000이므로 O(N^2) 알고리즘까지 고려해볼만 하다. 따라서 모든 경우의 누적합을 계산한 다음, 한쪽을 순회하면서 다른 한쪽에 t - accum[i]의 값이 존재하는지를 이분 탐색으로 찾으면 된다. 이 과정에서 정렬과 이진 탐색 트리를 사용할 수 있는데 경사트리를 방지하기 위해 트리의 밸런스를 맞추기 위한 내부 연산 때문에 이진 탐색 트리가 정렬보다 좀 더 오래 걸린다는 것을 살펴볼 수 있는 문제였다.

[[BOJ] 1304. 지역](https://www.acmicpc.net/problem/1304) ⭐
- 이 문제는 고속도로로 인해 앞 노드에서는 뒷노드를 반드시 이동할 수 있다는 것을 깨닫고, 그에 따라 같은 그룹에 속하는 도시는 연속으로 배치되어야 한다는 것을 깨닫는 것이 핵심이 문제였다. 따라서 번호가 작은 도시에서 큰 도시로 가는 간선은 의미가 없고, 큰 번호에서 작은 번호로 이동하는 간선에 집중하여 이동 시 같은 그룹인지를 검사하면 답을 도출해낼 수 있다.

[[Programmers] 연속 펄스 부분 수열의 합](https://school.programmers.co.kr/learn/courses/30/lessons/161988)
- 펄스 수열은 2종류이기 때문에 2개의 배열을 구성한 후 각각 누적합을 만들면 어렵지 않게 답을 도출할 수 있는 문제였다.

### <hr>16주차
> ***조이서:***<br>
 &emsp;&emsp;가끔 그런 생각해요.<br>
 &emsp;&emsp;살아서 뭐하나.<br>
 &emsp;&emsp;인생이란게 그렇잖아요... 뻔하고.<br><br>
 > ***박새로이:***<br>
 &emsp;&emsp;뭔 소리야.<br><br>
> ***조이서:***<br>
 &emsp;&emsp;언젠가 늙어 죽는 백 년도 안되는 짧은 인생...<br>
 &emsp;&emsp;어떻게든 잘 살아보겠다고 아등바등.<br>
 &emsp;&emsp;차라리 안 태어났으면 좋았을텐데... 귀찮아.<br><br>
 > ***박새로이:***<br>
 &emsp;&emsp;그렇게 귀찮으면... 죽어.<br><br>
 > ***조이서:***<br>
 &emsp;&emsp;네?!<br><br>
 > ***박새로이:***<br>
 &emsp;&emsp;헛똑똑이네...<br>
 &emsp;&emsp;자기가 무슨 신이라도 된 마냥.<br><br>
 &emsp;&emsp;난 항상 일이 끝나면 이 거리를 달려.<br><br>
 &emsp;&emsp;내일도 일어나면 가게 문을 열고 오늘이랑 똑같이 일을 하겠지... 계획대로.<br>
 &emsp;&emsp;반복적인 일상 같지만, 사실 내일 무슨 일이 일어날진 아무도 몰라.<br><br>
 &emsp;&emsp;대뜸 시비를 걸었던 승권이는 지금 단밤에서 홀을 봐주고 있고,<br>
 &emsp;&emsp;가게 영업정지 시킨 네가, 지금 우리 가게 매니저야.<br><br>
 &emsp;&emsp;뻔한 날은 단 하루도 없었어.<br><br>
 &emsp;&emsp;지금껏 힘든 날도... 슬픈 날도 많았지만...<br>
 &emsp;&emsp;살다보면 가끔 그렇게 재밌는 일이 벌어지곤 해.<br>
 &emsp;&emsp;네가 온 이후론 더 그러네...<br>
 &emsp;&emsp;가슴 뛰는 하루하루야.<br><br>
 &emsp;&emsp;혹시 알아? 살다보면 네 그 지겨운 일상에도 가슴 뛰는 일들이 생길지...<br><br>
\- 드라마 <[이태원 클라쓰](https://www.youtube.com/shorts/1OMCT4c9FCA)> 中 -<br>

[[BOJ] 25953. 템포럴 그래프](https://www.acmicpc.net/problem/25953) ⭐
- 정점과 각 시간마다 가중치가 다른 간선이 주어질 때, 시간이 오름차순이 되도록 간선을 선택해 시작 정점에서 끝 정점까지의 최단거리를 구하는 문제였다. 나는 이전에 풀었던 인터넷 설치의 풀이 방법을 떠올려서 다익스트라 + 2차원 방문체크 방식으로 문제를 해결하였고, 다이나믹 프로그래밍으로 해결한 스터디원의 풀이도 살펴볼 수 있었다. 매번 전형적이게 다익스트라만 사용하는 문제가 아닌 응용이 필요했던 문제였다.

[[BOJ] 1079. 마피아](https://www.acmicpc.net/problem/1079)
- 현실에서도 재밌게 할 수 있는 마피아를 프로그래밍 문제로 풀어봤다. 참가의 수 n이 최대 16이기 때문에 가지치기를 잘하면 완전 탐색이 될 것 같다는 생각이 든다. 게다가 낮에는 여러 경우를 따져보는 것이 아니라 유죄 지수가 가장 높은 사람이 사망하기 때문에 밤에만 은진이가 누구를 살해할 것인지가 관건이 된다. 처음에는 은진이의 유죄 지수를 가장 적게 오르게 하는 사람부터 죽여나가면 되지 않을까? 하는 생각이 들었었는데 예를 들어 은진이의 유죄 지수가 가장 적게 오르는 사람 A를 죽였을 때 3이 올랐지만, 살아있는 나머지 사람들은 유죄 지수에 음수가 더해지면 은진이는 가장 적게 올랐음에도 유죄 지수가 가장 많이 오른 경우가 되는 것이기 때문에 그리디한 생각으로 최적화할 것이 아니라 완전 탐색을 수행해도 충분히 답을 도출할 수 있다.

[[BOJ] 5214. 환승](https://www.acmicpc.net/problem/5214) ⭐
- 이 문제는 설명이 굉장히 짧기 때문에 테스트 케이스를 보면서 이해해야 할 것이다. 같은 튜브에 속한 역들과 연결 된 더미 노드를 만든다는 생각, 한 튜브를 하나의 정점으로 본다는 생각 등 스터디원들의 다양한 풀이 등 단순하게 전형적인 BFS로만 해결하는 것이 아닌 발상이 중요한 문제였다.

[[BOJ] 16928. 뱀과 사다리 게임](https://www.acmicpc.net/problem/16928)
- 이 문제는 이동에 가중치가 별도로 존재하지 않기 때문에 최초 방문이 최단 거리임을 알 수 있다. 따라서 아묻따 BFS를 사용하되, 뱀을 타는 것이 반드시 도착점에 도달하는 시간을 더 걸리게 하는 것은 아니다라는 것의 유의하자. 또한, 뱀과 사다리를 1차원에 입력 받을 때, 뱀이나 사다리와 연결되지 않은 칸은 자기 자신의 번호를 갖도록 하는 아이디어도 기억해두자.

### <hr>17주차
> ***백이진:***<br>
 &emsp;&emsp;덕분에 웃었지.<br>
 &emsp;&emsp;웃고 나니까 면접 떨어진 것도 별거 아닌 거 같고, 이해 되던데.<br>
 &emsp;&emsp;비극을 희극으로 만드는 거.<br>
 &emsp;&emsp;넌 어쩌다 그런 생각을 해냈어?<br><br>
 > ***나희도:***<br>
 &emsp;&emsp;경기에서 맨날 졌으니까.<br>
 &emsp;&emsp;맨날 진다고 매일이 비극일 순 없잖아.<br>
 &emsp;&emsp;웃고나면 잊기 쉬워져.<br>
 &emsp;&emsp;잊어야 다음이 있어.<br><br>
\- 드라마 <[스물다섯 스물하나](https://www.youtube.com/shorts/aedkJdBgOY0)> 中 -<br>

[[BOJ] 2461. 대표 선수](https://www.acmicpc.net/problem/2461) ❤️
- 각 반별로 학생들을 성적순으로 정렬한 다음, 각 반별로 학생을 하나씩 최소 힙에 담으면서 최댓값을 계산하고 매번 큐에서 한개씩 빼가면서 최댓값과 최솟값의 차이를 갱신한 후 빠진 반의 다음 학생을 추가해 나가는 풀이와, 하나의 배열에 반은 상관 없이 모든 학생들을 추가하고 오름차순으로 정렬한다음 투 포인터 방식으로 구간 내의 N개의 반이 속했다면 en이 가리키는 학생과 st가 가리키는 학생의 성적차를 구해 답을 갱신해 나가는 두 가지 풀이를 살펴볼 수 있었다.

[[BOJ] 1082. 방번호](https://www.acmicpc.net/problem/1082) ⭐
- 이 문제는 최대 50자리의 숫자가 나올 수 있기 때문에 long 자료형으로도 불가능하므로 문자열을 활용해야 한다. dp를 사용한 풀이, 그리디를 사용한 두 가지 풀이를 살펴볼 수 있었는데 꽤나 난이도가 있었던 문제라고 생각하기 때문에 다시금 풀어보며 이해해야 할 것 같다.

[[BOJ] 14867. 물통](https://www.acmicpc.net/problem/14867)
- 물통의 크기가 최대 10만이기 때문에 방문 체크를 위해 100,000 x 100,000 크기의 2차원 배열을 선언하는 것은 불가능하다. 처음에는 그리디하게 물을 옮기는 방법이 있지 않을까 생각했지만, 경우가 너무 방대하기 때문에 완전 탐색을 떠올렸고, 가중치가 없기 때문에 BFS를 사용했다. 그럼 도대체 방문 체크는 어떻게 할 것인가? 그게 이 문제의 핵심이자 전부이다.

[[BOJ] 1327. 소트 게임](https://www.acmicpc.net/problem/1327)
- 배열이 주어질 때, 길이 k만큼의 부분 배열을 뒤집으며 전체 배열이 오름차순이 되게 하는 최소 횟수를 구하는 문제였다. 이 역시도 매 시도에 가중치가 존재하지 않기 때문에 BFS를 사용하여 해결할 수 있다. 그럼 최대 길이가 N이 되는 배열의 방문체크를 어떻게 할 것인가? 이 문제도 그게 핵심이라 생각하고 배열을 뒤집거나 부분 배열을 만드는 내장 함수는 어떤 것들이 있는지 연습해 보기 좋은 문제라고 생각한다.

### <hr>18주차
> 누가 그러더라..<br>
세상에서 제일 폭력적인 말이 남자답다, 여자답다, 엄마답다, 의사답다, 학생답다.<br>
뭐 이런 말들이라고...<br>
그냥... 다 처음 살아보는 인생이라서 서툰 건데...<br>
그래서 안쓰러운 건데...<br>
그래서 실수 좀 해도 되는 건데...<br><br>
\- 드라마 <[괜찮아 사랑이야](https://youtu.be/3SvJRBfmU3Y?si=kflmAu6nKvmcDI2R&t=260)> 中 -<br>

[[BOJ] 3097. 입국심사](https://www.acmicpc.net/problem/3079)
- 문제만 읽었을 때는 그리디한 느낌이 들 수도 있지만, 범위가 10의 9제곱으로 굉장히 크고, 최솟값을 구하는 문제이기 때문에 이분탐색을 떠올리면 어렵지 않게 풀 수 있다. 이분탐색이라고 생각하는 것이 중요했던 문제

[[BOJ] 17352. 여러분의 다리가 되어 드리겠습니다! ](https://www.acmicpc.net/problem/17352)
- 30만 개의 정점을 갖는 트리에서 1개의 간선을 제거하여 2개의 트리로 나뉠 때, 모든 정점 쌍 간에 이동이 가능하도록 연결해야 하는 두 정점을 찾는 문제였다. 2가지 풀이가 나왔었는데, 1번 정점을 기준으로 하여 DFS 탐색을 수행한 다음, 방문하지 않은 정점과 1번 정점을 연결하는 방법과 Union-Find 알고리즘을 사용하여 1번 정점과 다른 그룹이 나오는 최초 정점을 연결하는 방법이 있었다. 두 번째 방법으로 해결해야 골드 5의 위상이라 생각된다.

[[BOJ] 1495. 기타리스트](https://www.acmicpc.net/problem/1495) ❤️
- 처음에는 DP 테이블을 dp[2][51]과 같이 정의하여 dp[i][j]를 j번째 곡을 +로 연주했을 때(0)와 -로 연주했을 때(1)의 최댓값을 저장하도록 구현했는데, 매번 최댓값을 취하는 방식은, 모든 연주를 할 수 없는 경우가 발생할 수 있기 때문에 답을 도출하지 못하는 경우가 생긴다는 문제가 있었다. 따라서 DP 테이블을 dp[51][1001]로 정의하여 dp[i][j]를 시작 볼륨부터 시작해 i번째 곡을 볼륨 j로 칠 수 있는지로 설정하여 볼륨별로 연주가 가능한지를 모두 따져본 다음, dp[n][i]가 1인 값 중 i가 최대가 되는 경우를 택해 문제를 해결할 수 있었다. 이와 같은 유형의 DP문제는 많이 접하지 못했기 때문에 다시금 풀어보면 좋을 것 같다.

[[BOJ] 1713. 후보 추천하기](https://www.acmicpc.net/problem/1713)
- 문제에서 주어진 대로 자료형을 정의하고, 정렬을 적절히 활용하면서 시뮬레이션 하면 쉽게 해결할 수 있는 문제였다. 눈여겨볼 점은 Java 풀이의 경우, C++과 달리 PriorityQueu를 순회할 수 있기 때문에 정렬이 아닌 풀이가 가능했다는 점이다.
