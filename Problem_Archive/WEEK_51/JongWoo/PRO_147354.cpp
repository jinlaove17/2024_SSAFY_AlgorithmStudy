/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 문제에서 요구하는 것을 차근차근 해나가면 답을 쉽게 구할 수 있는 구현 문제였다.
      정렬 기준, 인덱스에 주의하고, 문제를 꼼꼼히 읽어 Si의 개념만 파악하면 쉽게 답을 도출할 수 있다.

시간 복잡도
	- 정렬을 하는데 가장 많은 시간이 소요되므로 O(NlogN)의 시간 복잡도를 갖는다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 6.56ms
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int solution(vector<vector<int>> data, int col, int row_begin, int row_end)
{
    // 2. 테이블의 튜플을 col번째 컬럼의 값을 기준으로 오름차순 정렬을 하되, 만약 그 값이 동일하면 기본키인 첫 번째 컬럼의 값을 기준으로 내림차순 정렬합니다.
    sort(data.begin(), data.end(), [col](const auto& a, const auto& b) {
        int colA = a[col - 1];
        int colB = b[col - 1];
        
        if (colA == colB)
        {
            int pkA = a[0];
            int pkB = b[0];
            
            return pkA > pkB;
        }
        
        return colA < colB;
    });
    
    // 3. 정렬된 데이터에서 S_i를 i 번째 행의 튜플에 대해 각 컬럼의 값을 i 로 나눈 나머지들의 합으로 정의합니다.
    // 4. row_begin ≤ i ≤ row_end 인 모든 S_i를 누적하여 bitwise XOR 한 값을 해시 값으로서 반환합니다.
    int answer = 0;
    
    for (int i = row_begin - 1; i <= row_end - 1; ++i)
    {
        int sum = 0;
        
        for (int num : data[i])
        {
            sum += num % (i + 1);
        }
        
        answer ^= sum;
    }
 
    return answer;
}
