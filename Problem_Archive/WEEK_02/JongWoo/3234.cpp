#include <iostream>
 
using namespace std;
 
int n;
int arr[9];
int pow2[10];
int facto[10];
bool isVisited[9];
 
void DFS(int depth, int total, int left, int right, int& answer);
 
int main(int argc, char** argv)
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    pow2[0] = facto[0] = 1;
 
    for (int i = 1; i < 10; ++i)
    {
        pow2[i] = 2 * pow2[i - 1];
        facto[i] = i * facto[i - 1];
    }
 
    int T = 0;
 
    cin >> T;
 
    for (int test_case = 1; test_case <= T; ++test_case)
    {
        cin >> n;
         
        int total = 0;
 
        for (int i = 0; i < n; ++i)
        {
            cin >> arr[i];
            total += arr[i];
            isVisited[i] = false;
        }
 
        // 무조건 왼쪽에서 시작해야 함
        int answer = 0;
 
        for (int i = 0; i < n; ++i)
        {
            isVisited[i] = true;
            DFS(1, total, arr[i], 0, answer);
            isVisited[i] = false;
        }
 
        cout << '#' << test_case << ' ' << answer << '\n';
    }
 
    return 0;
}
 
void DFS(int depth, int total, int left, int right, int& answer)
{
    if (depth == n)
    {
        ++answer;
        return;
    }
 
    if (left >= total - left)
    {
        answer += pow2[n - depth] * facto[n - depth];
        return;
    }
 
    for (int i = 0; i < n; ++i)
    {
        if (isVisited[i])
        {
            continue;
        }
 
        isVisited[i] = true;
        DFS(depth + 1, total, left + arr[i], right, answer);
 
        if (left >= right + arr[i])
        {
            DFS(depth + 1, total, left, right + arr[i], answer);
        }
 
        isVisited[i] = false;
    }
}
