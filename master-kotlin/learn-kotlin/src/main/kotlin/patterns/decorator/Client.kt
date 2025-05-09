package patterns.decorator

fun main() {
    val simpleSandwich = SimpleSandwich()
    val sandwich = DressingDecorator(MeatDecorator(simpleSandwich))

    println(sandwich.make())
}