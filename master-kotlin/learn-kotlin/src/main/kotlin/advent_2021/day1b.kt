package advent_2021

fun main() {
    val list = listOf<Int>(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
    val alphabetRange = (1..26).toList()
    val listLastIndex = list.lastIndex

    alphabetRange.forEach { println(it) }
}