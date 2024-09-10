#include <iostream>
#include <algorithm>
 
using namespace std;
 
const int MAX_SIZE = (int)1e5 + 1;
 
int n, k;
int arr[MAX_SIZE];
int diff[MAX_SIZE];
 
int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    int t = 0;
 
    cin >> t;
 
    for (int tc = 1; tc <= t; ++tc)
    {
        cin >> n >> k;
 
        for (int i = 0; i < n; ++i)
        {
            cin >> arr[i];
        }
 
        // diff[i] = arr[i + 1]과 arr[i]의 차이 값
        for (int i = 0; i < n - 1; ++i)
        {
            diff[i] = arr[i + 1] - arr[i];
        }
 
        int answer = 0;
 
        // k >= n인 경우는 모든 집에 발전소를 설치하면 되므로 길이는 0이 된다.
        // 따라서 k < n일 때만 검사한다.
        if (k < n)
        {
            // diff는 n - 1개임에 주의!
            sort(diff, diff + n - 1);
 
            for (int i = 0; i < n - k; ++i)
            {
                answer += diff[i];
            }
        }
 
        cout << '#' << tc << ' ' << answer << '\n';
    }
}
