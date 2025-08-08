#include <iostream>
#include <vector>

using namespace std;

const int MAX_KEY_SIZE = 20;

int keySize, lockSize;
int blankCount;

void rotateKey(vector<vector<int>>& key);
bool simulate(const vector<vector<int>>& key, const vector<vector<int>>& lock, int rOffset, int cOffset);

bool solution(vector<vector<int>> key, vector<vector<int>> lock)
{
    keySize = static_cast<int>(key.size());
    lockSize = static_cast<int>(lock.size());
    
    for (int r = 0; r < lockSize; ++r)
    {
        for (int c = 0; c < lockSize; ++c)
        {
            if (lock[r][c] == 0)
            {
                ++blankCount;
            }
        }
    }
    
    for (int rot = 0; rot < 4; ++rot)
    {
        for (int rOffset = -lockSize + 1; rOffset <= lockSize - 1; ++rOffset)
        {
            for (int cOffset = -lockSize + 1; cOffset <= lockSize - 1; ++cOffset)
            {
                if (simulate(key, lock, rOffset, cOffset))
                {
                    return true;
                }
            }
        }
    
        if (rot < 3)
        {
            rotateKey(key);
        }
    }
    
    return false;
}

void rotateKey(vector<vector<int>>& key)
{
    int tmp[MAX_KEY_SIZE][MAX_KEY_SIZE] = {};
    
    for (int r =  0; r < keySize; ++r)
    {
        for (int c = 0; c < keySize; ++c)
        {
            tmp[c][keySize - r - 1] = key[r][c];
        }
    }
    
    for (int r = 0; r < keySize; ++r)
    {
        for (int c = 0; c < keySize; ++c)
        {
            key[r][c] = tmp[r][c];
        }
    }
}

bool simulate(const vector<vector<int>>& key, const vector<vector<int>>& lock, int rOffset, int cOffset)
{
    int cnt = 0;
    
    for (int kr = 0; kr < keySize; ++kr)
    {
        for (int kc = 0; kc < keySize; ++kc)
        {
            if ((kr + rOffset < 0) || (kr + rOffset >= lockSize) || (kc + cOffset < 0) || (kc + cOffset >= lockSize))
            {
                continue;
            }
            
            if ((key[kr][kc] == 1) && (lock[kr + rOffset][kc + cOffset] == 1))
            {
                return false;
            }
            
            if ((key[kr][kc] == 1) && (lock[kr + rOffset][kc + cOffset] == 0))
            {
                ++cnt;
            }
        }
    }
    
    return cnt == blankCount;
}
