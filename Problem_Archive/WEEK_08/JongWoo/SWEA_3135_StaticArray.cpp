#include <iostream>
#include <cstring>

using namespace std;

const int ALPHABET_COUNT = 26;
const int ROOT = 1;
const int MAX_SIZE = 10 * (int)1e5 + 2; // 문자열의 최대 길이(10) * 최대 들어올 수 있는 문자열(10^5)

int unused = 2;
int cnt[MAX_SIZE]; // 해당 노드까지의 접두사로 시작하는 단어의 수
int nxt[MAX_SIZE][ALPHABET_COUNT];

void init(void)
{
    unused = 2;
    memset(cnt, 0, sizeof(cnt));
    memset(nxt, -1, sizeof(nxt));
}

void insert(int buffer_size, char* buf)
{
    int cur = ROOT;

    for (int i = 0; i < buffer_size; ++i)
    {
        if (nxt[cur][buf[i] - 'a'] == -1)
        {
            nxt[cur][buf[i] - 'a'] = unused++;
        }

        ++cnt[cur];
        cur = nxt[cur][buf[i] - 'a'];
    }

    ++cnt[cur];
}

int query(int buffer_size, char* buf)
{
    int cur = ROOT;

    for (int i = 0; i < buffer_size; ++i)
    {
        if (nxt[cur][buf[i] - 'a'] == -1)
        {
            return 0;
        }

        cur = nxt[cur][buf[i] - 'a'];
    }

    return cnt[cur];
}
