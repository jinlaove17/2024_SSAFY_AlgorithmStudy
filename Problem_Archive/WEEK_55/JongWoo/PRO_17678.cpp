#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

vector<int> minuteTable;

int toMinute(const string& time);
string toTime(int minute);

string solution(int n, int t, int m, vector<string> timetable)
{
    string answer = "";
    int crewCount = static_cast<int>(timetable.size());
    
    for (int i = 0; i < crewCount; ++i)
    {
        minuteTable.push_back(toMinute(timetable[i]));
    }
    sort(minuteTable.begin(), minuteTable.end());    
    
    int curTime = 540;
    int departCount = 0;
    
    for (int i = 0; i < crewCount; )
    {
        int cnt = 0;
        while ((i < crewCount) && (cnt < m) && (minuteTable[i] <= curTime))
        {
            ++i;
            ++cnt;
        }
            
        if (++departCount == n)
        {
            if (cnt >= m)
            {
                answer = toTime(minuteTable[i - 1] - 1);
            }
            else
            {
                answer = toTime(curTime);
            }
            
            break;
        }
        
        curTime += t;
    }
    
    return answer;
}

int toMinute(const string& time)
{
    return 60 * stoi(time.substr(0, 2)) + stoi(time.substr(3));
}

string toTime(int minute)
{
    int hour = minute / 60;
    int min = minute % 60;
    
    return ((hour < 10) ? "0" : "") + to_string(minute / 60) + ':' +
           ((min < 10) ? "0" : "") + to_string(minute % 60);
}
