package standardlibrary

fun main() {
    val result = "Hello_tha".reverseAndUppercase()
    println("The result is ${result}")
}

fun String.reverseAndUppercase() = this.reversed().toUpperCase()
