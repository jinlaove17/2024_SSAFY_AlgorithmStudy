#include <iostream>
#include <cstring>
using namespace std;

int T;
int retCnt = 0;

struct Trie {
    bool fin;
    int cnt;
    Trie* next[26];

    // 생성자
    Trie() : fin(false) {
        cnt = 0;
        memset(next, 0, sizeof(next));
    }

    // 소멸자
    ~Trie() {
        for (int i = 0; i < 26; ++i) {
            if (next[i]) delete next[i];
        }
    }

    // 트라이에 문자열 삽입
    void insert(const char* key) {
        // 문자열 끝나는 지점에 cnt 추가
        cnt += 1;

        if (*key == '\0') fin = true; //문자열 끝나는 지점 표시
        else {
            int cur = *key - 'a';

            if (next[cur] == NULL) next[cur] = new Trie(); //탐색 처음 되는 지점
            next[cur]->insert(key + 1); //다음 문자 삽입
        }
    }

    Trie* find(const char* key) {
        if (*key == '\0') {
            retCnt = cnt; // 전역변수 retCnt 갱신
            return this; //문자열 끝나는 위치 반환
        }
        int cur = *key - 'a';

        if (next[cur] == NULL) return NULL; //찾는 값이 존재하지 않을 때
        return next[cur]->find(key + 1); //다음 문자 탐색
    }
};

int main(){
    cin.tie(0);
    ios::sync_with_stdio(0);

    cin >> T;
    for(int t=1; t<=T; ++t) {
        int N;
        cin >> N;
        cout << "#" << t << " ";

        Trie* root = new Trie();
        for(int i=0; i<N; ++i) {
            int p;
            string s;
            cin >> p >> s;

            if (p == 1) { // 문자열 입력
                root->insert(s.c_str());
            }
            else { // 문자열 개수 찾기
                retCnt = 0; //전역 변수 초기화
                Trie* findS = root->find(s.c_str());
                cout << retCnt << " ";
            }
        }
        delete root;
        cout << "\n";
    }
    return 0;
}
