// PQ (최대힙) 사용
// 마지막 끝나는 시간 (lastTime) 관리하는 배열을 만들고
// lastTime[idx] 가 최소인 곳에다가 time 추가하기 (PQ에서 하나씩 꺼내서)
// 마지막에는 lastTime IntArray에서 가장 늦게 끝난 시간을 출력하면 됨

import java.io.*
import java.util.*

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M) = br.readLine().split(" ").map { it.toInt() }
    val arr = br.readLine().split(" ").map { it.toInt() }
    val pq = PriorityQueue<Int>(compareByDescending { it })
    arr.forEach { pq.add(it) }
    val lastTime = IntArray(M)
    
    while(pq.isNotEmpty()) {
        val time = pq.poll()
        val idx = lastTime.withIndex().minByOrNull { it.value }!!.index
        lastTime[idx] += time
    }
    val ret = lastTime.maxOrNull()
    println(ret)
}
