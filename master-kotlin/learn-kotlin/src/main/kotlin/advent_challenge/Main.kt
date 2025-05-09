package advent_challenge

import java.util.*

fun main() {
    val complements = list.associateBy {  2020 - it }
    println(complements)

    val pairs = list.firstNotNullOf { number ->
        val complement = complements[number]
        if(complement != null) {
            Pair(number, complement)
        }
        else null
    }

    println(pairs)
}