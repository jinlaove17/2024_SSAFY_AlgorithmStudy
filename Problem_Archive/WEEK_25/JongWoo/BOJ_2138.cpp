/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 13주차에 풀었던 BOJ 30023. 전구 상태 바꾸기 문제와 비슷하게 그리디하지 않을까하는 생각으로 시작했다.
      하지만 이 문제는 현재 전구의 상태가 변경되면 이전과 다음 전구의 상태 또한 바뀌기 때문에 조금은 달랐다.
      이런 저런 고민을 해보다가, 다음과 같은 사실을 발견했다.

      현재 전구의 상태를 바꾸는 경우
       1. 현재 전구의 상태 변경 x, 다음 전구의 상태 변경 o
       2. 현재 전구의 상태 변경 o, 다음 전구의 상태 변경 x
      현재 전구의 상태를 바꾸지 않는 경우
       1. 현재 전구의 상태 변경 x, 다음 전구의 상태 변경 x
       2. 현재 전구의 상태 변경 o, 다음 전구의 상태 변경 o
      
      위와 같은 조건이 성립하면, 첫 번째 전구를 누르느냐 안누르냐에 따라 다음 상황이 연쇄적으로 정해질 것이다.
      0번 인덱스부터 조건을 맞추며 진행되기 때문에 고려해야 되는 대상이 줄어들고, 이는 최소로 만드는 중임을 의미한다.
      따라서 두 번의 시뮬레이션을 통해 문제에서 원하는 답을 도출할 수 있게 된다.

시간 복잡도
	- N개의 스위치를 순회하며 전구의 상태를 조작하는데 O(N)의 시간이 걸리며, 첫 번째 스위치를 누르는 경우와 누르지 않는 경우, 총 2번 수행하므로 최종적인 시간 복잡도는 O(N)이다.

실행 시간
	- 0ms
*/

#include <iostream>
#include <string>

using namespace std;

int n;
string init, target;

int simulate(bool switchFirst);
void switchBulb(string& bulbs, int index);

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> n;
    cin >> init >> target;

    int a = simulate(true);
    int b = simulate(false);

    if (a + b == -2)
    {
        cout << -1 << '\n';
    }
    else if (a == -1)
    {
        cout << b << '\n';
    }
    else if (b == -1)
    {
        cout << a << '\n';
    }
    else
    {
        cout << min(a, b) << '\n';
    }
}

int simulate(bool switchFirst)
{
    int cnt = 0;
    string cur(init);

    if (switchFirst)
    {
        switchBulb(cur, 0);
        ++cnt;
    }

    for (int i = 1; i < n; ++i)
    {
        if (cur[i - 1] != target[i - 1])
        {
            switchBulb(cur, i);
            ++cnt;
        }
    }

    if (cur != target)
    {
        cnt = -1;
    }

    return cnt;
}

void switchBulb(string& bulbs, int index)
{
    if (index > 0)
    {
        bulbs[index - 1] = ('1' - bulbs[index - 1]) + '0';
    }

    bulbs[index] = ('1' - bulbs[index]) + '0';

    if (index < n - 1)
    {
        bulbs[index + 1] = ('1' - bulbs[index + 1]) + '0';
    }
}
