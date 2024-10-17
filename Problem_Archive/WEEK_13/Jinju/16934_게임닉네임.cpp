/*
  문제 접근
  접두사로 게임 닉네임을 판별하는거라 Trie를 떠올림
  규칙대로 트라이를 구현해서 문자열 넣어주기

  알고리즘: Trie 자료 구조
  시간 복잡도: O(N*L)
  
*/

#include <bits/stdc++.h>
using namespace std;
int N;

class Trie {
public:
    int cnt = 0;
    Trie *node[26]{};

    Trie();
    string insert(string &str, int idx);
    void clear();
};

Trie::Trie() {
    for (auto &i: node) {
        i = nullptr;
    }
}

string Trie::insert(string &str, int idx) {
    if (idx == str.size()) {
        return (++cnt == 1 ? str : str + to_string(cnt));
    }

    if (node[str[idx] - 'a'] == nullptr) {
        node[str[idx] - 'a'] = new Trie();
        node[str[idx] - 'a']->insert(str, idx + 1);
        return str.substr(0, idx + 1);
    }

    return node[str[idx] - 'a']->insert(str, idx + 1);
}

void Trie::clear() {
    for (auto &i: node) {
        if (i != nullptr) {
            i->clear();
            delete i;
        }
    }
}

int main() {
    cin.tie(0)->ios_base::sync_with_stdio(false);
    cin >> N;

    Trie *root = new Trie();

    while (N--) {
        string str;
        cin >> str;
        cout << root->insert(str, 0) << '\n';
    }
    root->clear();
    return 0;
}
