/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 이 문제는 주어진 입력값을 적절히 파싱, 진법 간의 변환, 모든 수식을 만족하는 진법을 찾기위한 비트 마스킹 등의 여러 테크닉을 연습할 수 있는 문제였다.
      특히, n진법은 0 ~ n - 1의 수로 표현되기 때문에 파싱 과정에서 가장 큰 한자리 수의 값을 찾아 해당 진법부터 9진법까지만 탐색을 수행했다.
      결과 값이 X가 아닌 수식에 대해 maxValue진법부터 9진법까지 순회하면서 수식을 만족한다면 해당 비트열에 체크를 해주도록 비트 마스킹을 수행했다.
      매 비트가 완성되면 기존 비트와 &연산을 수행해 최종적으로 모든 수식을 만족하는 진법들을 찾은 다음, 결과 값이 X인 수식에 대해 비트가 1인 진법을 적용했을 때
      수식의 값이 완성되는지를 검사하여 문제를 해결하였다.

시간 복잡도
    - 이 문제는 expressions의 크기가 최대 100이고, 각 피연산자의 수 또한 3개, 최대 99이하의 정수이기 때문에 시간은 여유롭다.
      문제를 풀 때, 수식을 피연산자와 연산자로 파싱하는 parse 함수, maxValue진법부터 9진법까지 순회하며 수식을 만족하는 비트를 만드는 calculateBit 함수,
      10진수->N진수, N진수->10진수로 변환하는 함수, 최종적으로 query 수식에 대한 답을 찾는 go 함수를 정의하여 해결하였는데 이 함수들 중 가장 많은 시간이 소요되는 함수는
      parse 함수이다. 이 함수는 expressions.size()개의 수식에 대해, 각 수식을 순회하며 값을 추출하므로 최악의 경우 100개의 수식 * 길이 12(두 자리수 + 두 자리수 = 두 자리수)의
      연산이 수행된다. 따라서 시간 복잡도는 O(N) 정도라고 생각한다.

실행 시간
    - 가장 오래 걸린 테스트 케이스의 실행 시간: 0.11ms

삽질했던 내용
    - 첫 번째는 수식의 값이 여러 진법으로 적용해도 동일하게 성립하는 값이 나올 수 있다는 것이다. 따라서 go 함수에서 ret이 empty가 아니더라도, cur와 같지 않을 때만 "?"를 반환해야 한다.
      두 번째는 expressions의 수식의 결과 값이 모두 "X"일 수 있다는 것이다. 이때는 기존의 수식에서 가능한 진법을 찾을 필요 없이 2 ~ 9진법을 모두 수행해보면 되므로 bit를 모두 1로 만든 후
      go 함수를 수행해주면 된다.
*/

#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX_EXP_COUNT = 101;

int n, maxValue;
vector<int> numList[MAX_EXP_COUNT];
char opList[MAX_EXP_COUNT] = {};
bool queryList[MAX_EXP_COUNT];

void parse(int index, const string& exp);
int calculateBit(int index);
int n2d(int num, int base);
int d2n(int num, int base);
string go(int index, int bit);

vector<string> solution(vector<string> expressions) {
    vector<string> answer;
    int bit = 0;
    n = expressions.size();
    maxValue = 2;
    for (int i = 0; i < n; ++i)
    {
        parse(i, expressions[i]);
    }

    for (int i = 0; i < n; ++i)
    {
        if (queryList[i])
        {
            continue;
        }

        if (bit == 0)
        {
            bit |= calculateBit(i);
        }
        else
        {
            bit &= calculateBit(i);
        }
    }

    // 수식의 결과 값이 모두 "X"일 때는 모든 경우를 따져보도록 bit를 모두 채운다.
    if (bit == 0)
    {
        bit = (1 << 10) - 1;
    }

    for (int i = 0; i < n; ++i)
    {
        if (!queryList[i])
        {
            continue;
        }

        string result = go(i, bit);
        answer.push_back(expressions[i].substr(0, expressions[i].length() - 1) + result);
    }

    return answer;
}

void parse(int index, const string& exp)
{
    string tmp;

    for (char c : exp)
    {
        if (c == ' ')
        {
            if (!tmp.empty())
            {
                int num = stoi(tmp);

                numList[index].push_back(num);
                tmp.clear();
            }
        }
        else if ((c == '+') || (c == '-'))
        {
            opList[index] = c;
        }
        else if (('0' <= c) && (c <= '9'))
        {
            tmp.push_back(c);
            maxValue = max(maxValue, c - '0' + 1);
        }
    }

    if (tmp.empty())
    {
        queryList[index] = true;
    }
    else
    {
        int num = stoi(tmp);

        numList[index].push_back(num);
    }
}

int calculateBit(int index)
{
    int bit = 0;

    for (int base = maxValue; base <= 9; ++base)
    {
        int a = n2d(numList[index][0], base);
        int b = n2d(numList[index][1], base);
        int c = n2d(numList[index][2], base);

        switch (opList[index])
        {
        case '+':
            if (a + b == c)
            {
                bit |= (1 << base);
            }
            break;
        case '-':
            if (a - b == c)
            {
                bit |= (1 << base);
            }
            break;
        }
    }

    return bit;
}

// n-based number to decimal number
int n2d(int num, int base)
{
    int ret = 0, mul = 1;

    while (num > 0)
    {
        ret += mul * (num % 10);
        mul *= base;
        num /= 10;
    }

    return ret;
}

// decimal number to n-based number
int d2n(int num, int base)
{
    if (num == 0)
    {
        return 0;
    }

    string ret;

    while (num > 0)
    {
        ret.push_back(static_cast<char>(num % base + '0'));
        num /= base;
    }

    reverse(ret.begin(), ret.end());
    return stoi(ret);
}

string go(int index, int bit)
{
    string ret;

    for (int base = maxValue; base <= 9; ++base)
    {
        string cur;

        if (bit & (1 << base))
        {
            int a = n2d(numList[index][0], base);
            int b = n2d(numList[index][1], base);

            switch (opList[index])
            {
            case '+':
                cur = to_string(d2n(a + b, base));
                break;
            case '-':
                cur = to_string(d2n(a - b, base));
                break;
            }

            if (ret.empty())
            {
                ret = cur;
            }
            else
            {
                if (ret != cur)
                {
                    return "?";
                }
            }
        }
    }

    return ret;
}
