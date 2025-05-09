package advent_challenge

import java.io.File

fun main() {
    val nums = listOf<Int>(1,2,4,4)

    var c = 0
    val f = File("src/main/resources/day2.txt")
    f.forEachLine { line ->
       val passwordList = line.split(" ")
       val password = passwordList[2]
       val letterOccurs = passwordList[0].split("-").map { it.toInt() }
       val letter = passwordList[1].replace(":","")



       val count = password.toCharArray().filter {
           it.toChar().equals(letter.toCharArray()[0].toChar())
       }.count()

//        if(count >= letterOccurs[0] && count <= letterOccurs[1] )  {
//           c++
//       }
        val pair = Pair(password.toCharArray().elementAtOrNull(letterOccurs[0] - 1)?.equals(letter.toCharArray()[0].toChar()),
       password.toCharArray().elementAtOrNull(letterOccurs[1] - 1)?.equals(letter.toCharArray()[0].toChar()) )



//

        if(map["${pair.first}-${pair.second}"] == true) {
            c++
        }

    }
    println(c)
}

val map = mapOf<String,Boolean>(
    "true-false" to  true,
    "false-true" to true,
    "true-true"  to false,
    "false-false" to false
)