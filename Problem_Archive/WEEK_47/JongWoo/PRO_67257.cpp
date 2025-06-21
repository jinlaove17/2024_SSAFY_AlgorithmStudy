/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 연산자가 3종류이므로 3!가지의 경우가 나온다. expression 역시 최대 100이하인 문자열이기 때문에 일일이 따져보는게 맞다고 생각했다.
	  이때, 연산을 어떻게 처리해야 할지가 고민이었는데, 각각 피연산자와 연산자를 파싱하고 임시 벡터에 값을 복사하면서
	  시뮬레이션 하여, vector::erase 매서드를 통해 현재 우선순위의 연산자를 만나면 i번째와 i + 1번째 피연산자를 곱해서 i번째에 덮어쓰고,
	  i + 1번째 원소를 삭제하는 식으로 답을 도출할 수 있었다.

시간 복잡도
	- 3!개의 경우에 대해 매번 3개의 피연산자를 살펴보면서 파싱된 연산자의 개수만큼 순회하며 erase 매서드를 호출한다.
	  이는 연산자가 a개, 피연산자가 b개라고 할 때 경우인데 6 * 3 * a * (a + b)의 시간이 걸린다.
	  따라서 O(a^2 * b)의 시간 복잡도를 갖는다.

실행 시간
	- 가장 오래 걸린 테스트케이스에서 0.01ms
*/

#include <iostream>
#include <string>
#include <vector>

using namespace std;

const char operatorPriorities[6][3] = 
{
    { '+', '-', '*' },
    { '+', '*', '-' },
    { '-', '*', '+' },
    { '-', '+', '*' },
    { '*', '+', '-' },
    { '*', '-', '+' },
};
vector<long long> operands;
vector<char> operators;

void parseExpression(const string& expression);

long long solution(string expression)
{
    long long answer = 0;
    
    parseExpression(expression);
    
    for (int i = 0; i < 6; ++i)
    {
        vector<long long> curOperands(operands);
        vector<char> curOperators(operators);
        
        for (int j = 0; j < 3; ++j)
        {
            for (int k = 0; k < curOperators.size(); ++k)
            {
                if (curOperators[k] == operatorPriorities[i][j])
                {
                    switch (curOperators[k])
                    {
                        case '+':
                            curOperands[k + 1] += curOperands[k];
                            break;
                        case '-':
                            curOperands[k + 1] = curOperands[k] - curOperands[k + 1];
                            break;
                        case '*':
                            curOperands[k + 1] *= curOperands[k];
                            break;
                    }
                    
                    curOperators.erase(curOperators.begin() + k);
                    curOperands.erase(curOperands.begin() + k);
                    --k;
                }
            }
        }
        
        answer = max(answer, abs(curOperands[0]));
    }
    
    return answer;
}

void parseExpression(const string& expression)
{
    string tmp;
    
    for (char c : expression)
    {
        if (('0' <= c) && (c <= '9'))
        {
            tmp.push_back(c);
        }
        else
        {
            operands.push_back(stoi(tmp));
            operators.push_back(c);
            tmp.clear();
        }
    }
    
    operands.push_back(stoi(tmp));
}
