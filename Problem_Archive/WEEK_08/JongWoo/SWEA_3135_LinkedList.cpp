#include <iostream>
#include <cstring>

using namespace std;

const int ALPHABET_COUNT = 26;

struct TrieNode
{
	int visitCount;
	int wordCount;
	TrieNode* nextNodes[ALPHABET_COUNT];

	TrieNode()
	{
		visitCount = 0;
		wordCount = 0;

		for (int i = 0; i < ALPHABET_COUNT; ++i)
		{
			nextNodes[i] = nullptr;
		}
	}

	void Insert(char* buf)
	{
		++visitCount;

		if (*buf == NULL)
		{ 
			++wordCount;
			return;
		}

		int index = *buf - 'a';

		if (nextNodes[index] == nullptr)
		{
			nextNodes[index] = new TrieNode();
		}

		nextNodes[index]->Insert(buf + 1);
	}

	int Find(char* buf)
	{
		if (*buf == NULL)
		{
			return visitCount;
		}

		int index = *buf - 'a';

		if (nextNodes[index] == nullptr)
		{
			return 0;
		}

		return nextNodes[index]->Find(buf + 1);
	}
};

TrieNode* rootNode;

void init(void)
{
	rootNode = new TrieNode();
}

void insert(int buffer_size, char* buf)
{
	rootNode->Insert(buf);
}

int query(int buffer_size, char* buf)
{
	return rootNode->Find(buf);
}
