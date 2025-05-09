package lists

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val list = List(5) { idx -> "name: ${idx}"}
    println(list)

    buildList<String>() {
       add("name")
       add("thomas")
    }.forEach { println(it) }

    val mutList = mutableListOf<Int>(1, 2, 4)

    val x = mutList.toList()

    mutList.add(5)
    mutList.add(6)

    println(mutList)
    println(x)
}