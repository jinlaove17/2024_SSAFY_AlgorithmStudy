/*
	문제 접근
	map<string, Trie>를 통한 해시맵 제작
	Trie + 해시맵 사용하여 접두어를 사용하지 않고 연결리스트 처럼 붙이기

	시간 복잡도 : O(N*K)
	1. N개의 줄에 K개 만큼 입력이 들어온다
	2. 해당 입력을 모두 Trie에 삽입하는 시간이 O(N*K)
	3. Trie에 삽입한 노드들을 완전 탐색으로 출력 O(N*K)

  실행 시간 : C++, 0ms
*/

#include <bits/stdc++.h>
using namespace std;
int N;

struct TrieNode {
	map<string, TrieNode*> next;
};

class Trie {
private:
	TrieNode* root;

public:
	Trie() {
		root = new TrieNode();
	}

	void insert(const vector<string>& wordList) {
		TrieNode* node = root;
        
        // map에 없으면 삽입, wordList 입력 받은 순서대로 들어온다
		for (const auto& word : wordList) {
			if (node->next.find(word) == node->next.end()) {
				node->next[word] = new TrieNode();
			}
			node = node->next[word];
		}
	}

	void printTrie(TrieNode* node, int depth) {
		for (auto& child : node->next) {
			for (int i = 0; i < depth; ++i) {
				cout << "--";
			}

			cout << child.first << "\n";
			printTrie(child.second, depth + 1);
		}
	}

	void printRet() { printTrie(root, 0); }
};

int main(void) {
	cin.tie(0) -> ios::sync_with_stdio(0);
	cin >> N;
	Trie trie;

	for (int i = 0; i < N; ++i) {
		int len;
		cin >> len;

		vector<string> wordList(len);
		for (int j = 0; j < len; ++j) {
			cin >> wordList[j];
		}

		trie.insert(wordList);
	}
	trie.printRet();
	return 0;
}
