#include <cstring>
 
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
 
Trie* root;
 
void init(void) {
    root = new Trie();
}
 
void insert(int buffer_size, char *buf) {
    root->insert(buf);
}
 
int query(int buffer_size, char *buf) {
    retCnt = 0; //전역 변수 초기화
    Trie* findS = root->find(buf);
     
    return retCnt;
}
