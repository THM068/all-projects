package patterns.decorator

abstract class SandwichDecorator(val customSandwich: Sandwich): Sandwich {

    override fun make(): String {
        return customSandwich.make()
    }
}