package standardlibrary

fun main() {
   val shape: Shape = Square()
    println(shape.vertexCount)

    println(Pentagon().vertexCount)
}

open class Shape {
    open val vertexCount: Int = 0
    open val vertexCounts: Int = 0
}

class Square(): Shape() {
    override val vertexCount = 4
}

class Pentagon(override val vertexCounts: Int = 5): Shape()