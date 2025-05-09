package advent_2021

import java.io.File

val file = File("src/main/resources/day2.txt")

fun loadCommands(): List<String> {
    return file.readLines().map { it.trim() }
}

fun main() {
    val position = mutableMapOf<String, Int>( "horizontal" to 0, "vertical" to 0, "aim" to 0)
    val list = listOf<String>("forward 5", "down 5", "forward 8", "up 3", "down 8", "forward 2").map { it.split(" ") }
    val instructions = loadCommands().map { it.split(" ") }

    for((command, amountString) in instructions) {
        val expression = getExpression(command, amountString.toInt())
        expression.drive(position)
    }

    println(position)

}

interface Expression {

    fun drive(position: MutableMap<String, Int>): MutableMap<String, Int>
}

class ForwardExpression(val forward: Int) : Expression {

    override fun drive(position: MutableMap<String, Int>): MutableMap<String, Int> {
        val current = position.getOrDefault("horizontal", 0)
        val currentDepth = position.getOrDefault("vertical", 0)
        val currentAim = position.getOrDefault("aim", 0)
        position["horizontal"] = current + forward
        position["vertical"] = currentDepth + (currentAim.times(forward))
        return position
    }
}

class DownExpression(val down: Int): Expression {

    override fun drive(position: MutableMap<String, Int>): MutableMap<String, Int> {
        val currentAim = position.getOrDefault("aim", 0)
        position["aim"] = currentAim + down
        return position
    }

}

class UpExpression(val up: Int): Expression {

    override fun drive(position: MutableMap<String, Int>): MutableMap<String, Int> {
        val currentAim = position.getOrDefault("aim", 0)
        position["aim"] = currentAim -  up
        return position
    }
}

class DefualtExpression: Expression {
    override fun drive(position: MutableMap<String, Int>): MutableMap<String, Int> {
        return position
    }

}


fun getExpression(command: String, amount: Int): Expression {
    when(command) {
        "forward" -> return ForwardExpression(amount)
        "down" -> return DownExpression(amount)
        "up" -> return UpExpression(amount)
        else -> return DefualtExpression()
    }

}
