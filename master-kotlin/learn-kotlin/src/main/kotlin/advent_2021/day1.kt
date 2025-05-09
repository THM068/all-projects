package advent_2021

import java.io.File
import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
     val array = listOf<Int>(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
     val queue = ArrayDeque<Int>(load())

     var numOfIncreases = 0
     var currentElemOptional = Optional.ofNullable(queue.removeFirstOrNull())

     while(currentElemOptional.isPresent) {
         var nextElemOptional = Optional.ofNullable(queue.removeFirstOrNull())
         if(nextElemOptional.isPresent && currentElemOptional.get() < nextElemOptional.get()) {
             numOfIncreases++
         }
         currentElemOptional = nextElemOptional
     }

     println(numOfIncreases)

    println("-----------------------")
    println(part2(load()))
}

val f = File("src/main/resources/day2.txt")

fun load(): List<Int> {
   return f.readLines().map { it.toInt() }
}

fun part2(input: List<Int>) : Int{
    return input.windowed(3).windowed(2).count {
        (a, b) -> a.sum() < b.sum()
    }
}
