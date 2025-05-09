package advent_2021

import java.io.File

fun main() {
    day3_part1()
}

fun day3_part1() {
    val listGroups = MutableList(day3FileLoad().first().toCharArray().size) { mutableListOf<Int>()}
        //mutableListOf<MutableList<Int>>(mutableListOf<Int>(), mutableListOf<Int>(), mutableListOf<Int>(), mutableListOf<Int>(), mutableListOf<Int>())
    val sample = day3FileLoad()//listOf("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010")

    sample.forEach { value ->
        value.toCharArray().forEachIndexed { index, c ->
            listGroups[index].add(c.digitToInt())
        }
    }
    val groups = listGroups.map {
        it.groupBy { it == 1 }
    }

    for(model in groups) {
        val zeroSize = model.getOrDefault(false, mutableListOf<Int>()).size
        val oneSize = model.getOrDefault(true, mutableListOf<Int>()).size
        if(zeroSize < oneSize) {
            print("1")
        }
        else
            print("0")

    }
    println("-------------")
    for(model in groups) {
        val zeroSize = model.getOrDefault(false, mutableListOf<Int>()).size
        val oneSize = model.getOrDefault(true, mutableListOf<Int>()).size
        if(zeroSize > oneSize) {
            print("1")
        }
        else
            print("0")

    }


}

val day3File = File("src/main/resources/day2.txt")

fun day3FileLoad(): List<String> {
    return day3File.readLines().map { it }
}

