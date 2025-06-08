import java.io.*

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, L, R, X) = br.readLine()!!.split(" ").map { it.toInt() }
    val arr = br.readLine()!!.split(" ").map { it.toInt() }.toIntArray()
    var ans = 0

    for (i in 1 until (1 shl N)) { // shl => shift left라는 의미로, 코틀린의 비트연산자
        val comb = mutableListOf<Int>() // 조합 찾는 가변 리스트 생성

        for (j in 0 until N) { // 비트마스킹으로 조합 만들기 (N <= 15라서 브루트포스 가능)
            if ((i and (1 shl j)) != 0) {
                comb.add(arr[j])
            }
        }

        if (comb.size < 2) continue
        val combSum = comb.sum()
        val combDiff = comb.maxOrNull()!! - comb.minOrNull()!!
        if (combSum in L..R && combDiff >= X) ans++
    }
    println(ans)
}
