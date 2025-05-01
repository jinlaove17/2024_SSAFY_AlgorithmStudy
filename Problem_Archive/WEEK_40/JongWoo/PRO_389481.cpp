/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- n의 범위가 최대 10^15로 굉장히 크기 때문에, 수학적인 규칙성을 찾아야 할 것 같다는 생각이 들었다.
	  우선 bans를 오름차순으로 정렬한 다음 이분 탐색을 진행하는 방식을 떠올리게 되었다.
	  이때, 문자열을 순서로 치환하는 s2n 함수와 순서를 문자열로 치환하는 n2s 함수를 구현하였다.
	  이분 탐색 시에는 s2n을 함수를 적용했을 때 나온 순서가 n + 앞쪽에 제거될 숫자의 개수(md)를 더한 값보다
	  작거나 같을 경우에는 범위 안에 들어오는 수이므로 cnt의 값을 md + 1로 갱신하며 영향을 미치는 수의 개수를 계산하였다.
	  위 과정을 거친 후, 찾으려는 순서 n에 영향을 미쳐 삭제되는 cnt를 더한 값만큼 밀렸을 때의 값을 n2s을 통해 계산하면 된다.


시간 복잡도
	- 정렬을 수행하는데 O(NlogN) + 이분탐색과 s2n 함수를 호출하는데 O(LlogN)의 시간이 걸리므로 최종적으로 O(NlogN)의 시간 복잡도를 갖는다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 94.96ms
*/

#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

long long s2n(const string& str); // string to number
string n2s(long long num); // number to string

string solution(long long n, vector<string> bans)
{
    string answer = "";
    
    sort(bans.begin(), bans.end(),
         [](const string& a, const string& b)
         {
             int aLen = static_cast<int>(a.length());
             int bLen = static_cast<int>(b.length());
             if (aLen == bLen)
             {
                 return a < b;
             }
             
             return aLen < bLen;
         });
    
    
    int st = 0, en = static_cast<int>(bans.size()) - 1;
    int cnt = 0;
    while (st <= en)
    {
        int md = (en - st) / 2 + st;
        long long num = s2n(bans[md]);
        
        if (num <= n + md)
        {
            cnt = md + 1;
            st = md + 1;
        }
        else
        {
            en = md - 1;
        }
    }
    
    answer = n2s(n + cnt);
    
    return answer;
}

long long s2n(const string& str)
{
    long long num = 0, base = 1;
    int len = static_cast<int>(str.length());
    
    for (int i = len - 1; i >= 0; --i)
    {
        num += base * (str[i] - 'a' + 1);
        base *= 26;
    }
    
    return num;
}

string n2s(long long num)
{  
    string str;
       
    while (num > 0)
    {
        // 1-based 보정
        --num;
        
        char ch = 'a' + (num % 26);
        str = ch + str;
        num /= 26;
    }
    
    return str;
}
