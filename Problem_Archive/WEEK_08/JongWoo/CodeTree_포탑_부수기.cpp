#include <iostream>
#include <queue>
#include <vector>

using namespace std;

const int MAX_SIZE = 10 + 1;
const int DELTA[][2] = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } }; // 우, 하, 좌, 상

struct Tower
{
	int r;
	int c;
	int attackPower;
	int lastAttackTime;
	bool isHit;
};

struct Node
{
	int r;
	int c;
	vector<pair<int, int>> path;
};

int n, m, k;
int towerCount;
Tower towers[MAX_SIZE][MAX_SIZE];

void Simulate(int t);
bool AttackByLazer(Tower& attacker, Tower& target);
void AttackByShell(Tower& attacker, Tower& target);

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m >> k;

	for (int r = 1; r <= n; ++r)
	{
		for (int c = 1; c <= m; ++c)
		{
			cin >> towers[r][c].attackPower;
			towers[r][c].r = r;
			towers[r][c].c = c;
			towers[r][c].lastAttackTime = 0;
			towers[r][c].isHit = false;

			if (towers[r][c].attackPower > 0)
			{
				++towerCount;
			}
		}
	}

	for (int t = 1; t <= k; ++t)
	{
		Simulate(t);

		//cout << "==========================" << t << "==========================\n";

		//for (int r = 1; r <= n; ++r)
		//{
		//	for (int c = 1; c <= m; ++c)
		//	{
		//		cout << towers[r][c].attackPower << ' ';
		//	}

		//	cout << '\n';
		//}

		if (towerCount <= 1)
		{
			break;
		}
	}

	int answer = 0;

	for (int r = 1; r <= n; ++r)
	{
		for (int c = 1; c <= m; ++c)
		{
			answer = max(answer, towers[r][c].attackPower);
		}
	}

	cout << answer << '\n';
}

void Simulate(int t)
{
	// 1. 공격자와 공격 대상 선정
	Tower* attacker = nullptr;
	Tower* target = nullptr;

	for (int r = 1; r <= n; ++r)
	{
		for (int c = 1; c <= m; ++c)
		{
			if (towers[r][c].attackPower == 0)
			{
				continue;
			}

			// 이번 턴에 공격 받았는지에 대한 여부를 false로 초기화한다.
			towers[r][c].isHit = false;

			if (attacker == nullptr)
			{
				attacker = &towers[r][c];
			}
			// 공격력이 가장 낮은 포탑이 가장 약한 포탑입니다.
			else if (towers[r][c].attackPower < attacker->attackPower)
			{
				attacker = &towers[r][c];
			}
			// 만약 공격력이 가장 낮은 포탑이 2개 이상이라면, 가장 최근에 공격한 포탑이 가장 약한 포탑입니다.
			else if (towers[r][c].attackPower == attacker->attackPower)
			{
				if (towers[r][c].lastAttackTime > attacker->lastAttackTime)
				{
					attacker = &towers[r][c];
				}
				// 만약 그러한 포탑이 2개 이상이라면, 각 포탑 위치의 행과 열의 합이 가장 큰 포탑이 가장 약한 포탑입니다.
				else if (towers[r][c].lastAttackTime == attacker->lastAttackTime)
				{
					if (towers[r][c].r + towers[r][c].c > attacker->r + attacker->c)
					{
						attacker = &towers[r][c];
					}
					// 만약 그러한 포탑이 2개 이상이라면, 각 포탑 위치의 열 값이 가장 큰 포탑이 가장 약한 포탑입니다.
					else if (towers[r][c].r + towers[r][c].c == attacker->r + attacker->c)
					{
						if (towers[r][c].c > attacker->c)
						{
							attacker = &towers[r][c];
						}
					}
				}
			}

			if (target == nullptr)
			{
				target = &towers[r][c];
			}
			// 공격력이 가장 높은 포탑이 가장 강한 포탑입니다.
			else if (towers[r][c].attackPower > target->attackPower)
			{
				target = &towers[r][c];
			}
			// 만약 공격력이 가장 높은 포탑이 2개 이상이라면, 공격한지 가장 오래된 포탑이 가장 강한 포탑입니다.
			else if (towers[r][c].attackPower == target->attackPower)
			{
				if (towers[r][c].lastAttackTime < target->lastAttackTime)
				{
					target = &towers[r][c];
				}
				// 만약 그러한 포탑이 2개 이상이라면, 각 포탑 위치의 행과 열의 합이 가장 작은 포탑이 가장 강한 포탑입니다.
				else if (towers[r][c].lastAttackTime == target->lastAttackTime)
				{
					if (towers[r][c].r + towers[r][c].c < target->r + target->c)
					{
						target = &towers[r][c];
					}
					// 만약 그러한 포탑이 2개 이상이라면, 각 포탑 위치의 열 값이 가장 작은 포탑이 가장 강한 포탑입니다.
					else if (towers[r][c].r + towers[r][c].c == target->r + target->c)
					{
						if (towers[r][c].c < target->c)
						{
							target = &towers[r][c];
						}
					}
				}
			}
		}
	}

	// 2. 레이저/포탄 공격
	attacker->attackPower += n + m;
	attacker->lastAttackTime = t;

	if (!AttackByLazer(*attacker, *target))
	{
		AttackByShell(*attacker, *target);
	}

	// 3. 포탑 정비
	for (int r = 1; r <= n; ++r)
	{
		for (int c = 1; c <= m; ++c)
		{
			if ((towers[r][c].attackPower == 0) || (&towers[r][c] == attacker) || (&towers[r][c] == target) || (towers[r][c].isHit))
			{
				continue;
			}

			++towers[r][c].attackPower;
		}
	}
}

bool AttackByLazer(Tower& attacker, Tower& target)
{
	queue<Node> q;
	bool isVisited[MAX_SIZE][MAX_SIZE] = {};

	isVisited[attacker.r][attacker.c] = true;
	q.push(Node{ attacker.r, attacker.c, vector<pair<int, int>>() });

	while (!q.empty())
	{
		Node cur = q.front();

		q.pop();

		if ((cur.r == target.r) && (cur.c == target.c))
		{
			int r = 0, c = 0;

			for (int i = 0, pathSize = static_cast<int>(cur.path.size()) - 1; i < pathSize; ++i)
			{
				r = cur.path[i].first;
				c = cur.path[i].second;
				towers[r][c].attackPower = max(towers[r][c].attackPower - attacker.attackPower / 2, 0);
				towers[r][c].isHit = true;

				if (towers[r][c].attackPower == 0)
				{
					--towerCount;
				}
			}

			r = cur.path.back().first;
			c = cur.path.back().second;
			towers[r][c].attackPower = max(towers[r][c].attackPower - attacker.attackPower, 0);
			towers[r][c].isHit = true;

			if (towers[r][c].attackPower == 0)
			{
				--towerCount;
			}

			return true;
		}

		for (int dir = 0; dir < 4; ++dir)
		{
			int nr = cur.r + DELTA[dir][0];
			int nc = cur.c + DELTA[dir][1];

			if (nr < 1)
			{
				nr += n;
			}
			else if (nr > n)
			{
				nr -= n;
			}

			if (nc < 1)
			{
				nc += m;
			}
			else if (nc > m)
			{
				nc -= m;
			}

			if ((towers[nr][nc].attackPower == 0) || (isVisited[nr][nc]))
			{
				continue;
			}

			vector<pair<int, int>> path(cur.path);

			path.emplace_back(nr, nc);
			isVisited[nr][nc] = true;
			q.push(Node{ nr, nc, move(path) });
		}
	}

	return false;
}

void AttackByShell(Tower& attacker, Tower& target)
{
	for (int dr = -1; dr <= 1; ++dr)
	{
		for (int dc = -1; dc <= 1; ++dc)
		{
			int nr = target.r + dr;
			int nc = target.c + dc;

			if (nr < 1)
			{
				nr += n;
			}
			else if (nr > n)
			{
				nr -= n;
			}

			if (nc < 1)
			{
				nc += m;
			}
			else if (nc > m)
			{
				nc -= m;
			}

			if (((nr == attacker.r) && (nc == attacker.c)) || (towers[nr][nc].attackPower == 0))
			{
				continue;
			}

			int attackPower = attacker.attackPower;

			if ((dr != 0) || (dc != 0))
			{
				attackPower /= 2;
			}

			towers[nr][nc].attackPower = max(towers[nr][nc].attackPower - attackPower, 0);
			towers[nr][nc].isHit = true;

			if (towers[nr][nc].attackPower == 0)
			{
				--towerCount;
			}
		}
	}
}
