## 📝 해결한 문제 목록
#### ${\textsf{\color{red}※ 문제 선정시 찾기(Ctrl + F)를 통해 이전에 풀었던 문제가 아닌지 확인해주세요!}}$
> ⭐(알슐랭 스타): 모든 문제가 유익하지만 그 중에서도 다시금 풀어보면 좋을 것 같은 문제로, 과반수의 동의를 얻어야 획득이 가능하다.

### 1주차
[1210. [S/W 문제해결 기본] 2일차 - Ladder1](https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV14ABYKADACFAYh&categoryId=AV14ABYKADACFAYh&categoryType=CODE&problemTitle=%5BS%2FW+%EB%AC%B8%EC%A0%9C%ED%95%B4%EA%B2%B0+%EA%B8%B0%EB%B3%B8%5D+2%EC%9D%BC%EC%B0%A8+-+Ladder1&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=1)
 - 사다리 게임의 도착지가 한 곳이므로 도착지 부터 출발하는게 좋다. 가로로 연속해서 연결된 사다리가 존재하지 않음이 보장되므로 left/right 중 먼저 나오는 쪽을 타고 가는 구현 문제

[5684. [Professional] 운동](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWXRxnnah2sDFAUo&categoryId=AWXRxnnah2sDFAUo&categoryType=CODE&problemTitle=&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=1&&&&&&&&&&)
 - DFS와 visited 배열을 활용하여 사이클 탐색, But 계속 탐색을 반복하면 시간 초과가 되므로 적절한 가지치기 필요(누적 dist가 커지는 순간 return)

[6731. 홍익이의 오델로 게임](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWefzFeq5P8DFAUh&categoryId=AWefzFeq5P8DFAUh&categoryType=CODE&problemTitle=&orderBy=PASS_RATE&selectCodeLang=JAVA&select-1=4&pageSize=10&pageIndex=11) ⭐
 - Board의 제한 N이 짝수라는 조건이 핵심 힌트, 해당 조건으로 인해 뒤집는 지점을 기준으로 상태가 변하는 검정 돌의 개수가 무조건 홀수라는 규칙을 발견해야 한다.
 - 문제의 주어진 사소한 조건을 가지고 핵심 규칙을 찾아내기까지의 과정, 발상 난이도가 있는 문제


### 2주차
[5643. [Professional] 키 순서](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWXQsLWKd5cDFAUo&categoryId=AWXQsLWKd5cDFAUo&categoryType=CODE&problemTitle=pro&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=1) ⭐
- 일반적으로 위상 정렬, BFS 알고리즘을 사용하여 해결할 수 있는 문제였으나 플로이드 위셜 알고리즘으로도 해결할 수 있다는 인사이트를 얻을 수 있었던 문제, 하지만 최단 경로 알고리즘 중 다익스트라로는 해결할 수 없는데 그 이유에 대해서는 다시금 생각해보자...!

[3234. 준환이의 양팔저울](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AWAe7XSKfUUDFAUw&categoryId=AWAe7XSKfUUDFAUw&categoryType=CODE&problemTitle=&orderBy=RECOMMEND_COUNT&selectCodeLang=ALL&select-1=4&pageSize=10&pageIndex=2) ⭐
- 완전 탐색을 수행하여 모든 경우를 다 따져보는 것은 불가능하기 때문에 백트래킹을 수행하면서 어떻게 효율적으로 가지치기를 할 수 있을지 고민해볼 수 있었던 문제, 이에 더해 동일 코드가 C++에서는 시간 초과가 나는 문제에 봉착하여 Java와 C++의 메모리 관리 방식 또한 공부해볼 수 있었다.

[1824. 혁진이의 프로그램 검증](https://swexpertacademy.com/main/code/problem/problemDetail.do?problemLevel=4&contestProbId=AV4yLUiKDUoDFAUx&categoryId=AV4yLUiKDUoDFAUx&categoryType=CODE&problemTitle=&orderBy=RECOMMEND_COUNT&selectCodeLang=CCPP&select-1=4&pageSize=10&pageIndex=1) ⭐
- 문제에서 주어진대로만 작성하며 시뮬레이션하면 되는 문제였다. 하지만 현재 좌표에 도달했을 때의 메모리 숫자와 방향을 고려하며 도착점에 도달이 가능한지를 따져보기 위해 4차원 배열을 사용한다는 점에서 나름 고민해볼 수 있었던 문제
